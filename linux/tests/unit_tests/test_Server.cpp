/*
 * File: test_server.cpp
 *
 *
 * Copyright 2019 Harald Postner <Harald at free_creations.de>.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

#include "gtest/gtest.h"
#include "gmock/gmock.h"
#include "jnimock/jnimock.h"

#include "Server.h"

using testing::Return;
using testing::NiceMock; // Note: "NiceMock" does not nag with useless warnings.
using testing::_;
using testing::Ge;


using namespace jnimock;

/**
 * Function `Server::jack_get_version` shall push the _jack library_ version into the
 * `Int` objects provided by the caller.
 */
TEST(Server, jack_get_version) {
    NiceMock<JNIEnvMock> jniEnvMock;
    types::_jIntObject major;
    types::_jIntObject minor;
    types::_jIntObject micro;
    types::_jIntObject proto;


    // major version greater or equal to 1
    EXPECT_CALL(jniEnvMock, SetIntField(&major, _, Ge(1)))
            .Times(1);
    // minor version not negative
    EXPECT_CALL(jniEnvMock, SetIntField(&minor, _, Ge(0)))
            .Times(1);
    // micro version not negative
    EXPECT_CALL(jniEnvMock, SetIntField(&micro, _, Ge(0)))
            .Times(1);
    // protocol version not negative
    EXPECT_CALL(jniEnvMock, SetIntField(&proto, _, Ge(0)))
            .Times(1);

    // here we go...
    Server::jack_get_version(&jniEnvMock, &major, &minor, &micro, &proto);

}
/**
 * The version returned by function `Sever.jni_get_version` shall be the version given by `jniEnv.GetVersion`.
 */
TEST(Server, jni_get_version) {

    NiceMock<JNIEnvMock> jniEnvMock;
    jclass clazz = nullptr;

    // make the jniEnvMock return JNI_VERSION_1_6 as its version
    ON_CALL(jniEnvMock, GetVersion())
            .WillByDefault(Return(JNI_VERSION_1_6));

    // here we go...
    jint version = Server::jni_get_version(&jniEnvMock);
    EXPECT_EQ(version, JNI_VERSION_1_6);
}