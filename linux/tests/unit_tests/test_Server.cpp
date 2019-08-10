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
using namespace jnimock;


TEST(Server, jack_get_version) {

    NiceMock<JNIEnvMock> jniEnvMock;

    jobject major_ptr = nullptr;
    jobject minor_ptr = nullptr;
    jobject micro_ptr = nullptr;
    jobject proto_ptr = nullptr;

    Java_jackAudio4Java_Server__1jack_1get_1version(&jniEnvMock, nullptr, major_ptr, minor_ptr, micro_ptr, proto_ptr);

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

    // verify what Java_jackAudio4Java_Server__1jni_1get_1version returns as version.
    jint version = Java_jackAudio4Java_Server__1jni_1get_1version(&jniEnvMock, clazz);
    EXPECT_EQ(version, JNI_VERSION_1_6);
}