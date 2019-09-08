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
#include "jni_headers/jackAudio4Java_Jack.h"

#include "Jack.h"

using testing::Return;
using testing::NiceMock; // Note: "NiceMock" does not nag with useless warnings.
using testing::_;
using testing::Ge;


using namespace jnimock;

class JackTest : public ::testing::Test {
protected:
    void SetUp() override {
        Jack::setLoggingLevel(0);
    }

    // void TearDown() override {}
};

/**
 * Function `Server::jack_get_version` shall push the _jack library_ version into the
 * `Int` objects provided by the caller.
 */
TEST_F(JackTest, jack_get_version) {
    NiceMock<JNIEnvMock> jniEnvMock;
    types::_jIntObject major;
    types::_jIntObject minor;
    types::_jIntObject micro;
    types::_jIntObject proto;

    // Unfortunately, in Ubuntu Xenial, the function `jack_get_version` is broken.
    // All version elements are returned as zero.
    // So we can only test that the returned values are not negative.
    EXPECT_CALL(jniEnvMock, SetIntField(&major, _, Ge(0)))
            .Times(1);
    EXPECT_CALL(jniEnvMock, SetIntField(&minor, _, Ge(0)))
            .Times(1);
    EXPECT_CALL(jniEnvMock, SetIntField(&micro, _, Ge(0)))
            .Times(1);
    EXPECT_CALL(jniEnvMock, SetIntField(&proto, _, Ge(0)))
            .Times(1);

    // here we go...
    Jack::jack_get_version(&jniEnvMock, &major, &minor, &micro, &proto);

}
/**
 * The version returned by function `Sever.jni_get_version` shall be the version given by `jniEnv.GetVersion`.
 */
TEST_F(JackTest, jni_get_version) {

    NiceMock<JNIEnvMock> jniEnvMock;
    jclass clazz = nullptr;

    // make the jniEnvMock return JNI_VERSION_1_6 as its version
    ON_CALL(jniEnvMock, GetVersion())
            .WillByDefault(Return(JNI_VERSION_1_6));

    // here we go...
    jint version = Jack::jni_get_version(&jniEnvMock);
    EXPECT_EQ(version, JNI_VERSION_1_6);
}

/**
 * The value returned by Jack_portNameSize should be greater than zero.
 */
TEST_F(JackTest, portNameSize) {
    int portNameSize = Java_jackAudio4Java_Jack_portNameSizeN(nullptr, nullptr);
    EXPECT_GT(portNameSize, 0);
}

/**
 * The value returned by Jack_portTypeSize should be greater than zero.
 */
TEST_F(JackTest, portTypeSize) {
    int portTypeSize = Java_jackAudio4Java_Jack_portTypeSizeN(nullptr, nullptr);
    EXPECT_GT(portTypeSize, 0);
}
/**
 * The value returned by Jack_clientNameSize should be greater than zero.
 */
TEST_F(JackTest, clientNameSize) {
    int clientNameSize = Java_jackAudio4Java_Jack_clientNameSizeN(nullptr, nullptr);
    EXPECT_GT(clientNameSize, 0);
}

/**
 * When attempting to close a NULL client, an error code should be returned.
 * Note: an attempt to call "clientClose" with any invalid value other than
 * zero will result in a segment fault!
 */
TEST_F(JackTest, clientClose) {
    long nullClient = 0;
    int error = Java_jackAudio4Java_Jack_clientCloseN(nullptr, nullptr, nullClient);
    EXPECT_NE(error, 0);
}