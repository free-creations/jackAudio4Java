/*
 * File: test_utils.cpp
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
#include "types_Int.h"
#include "jni.h"

using testing::Return;
using testing::StrEq;
using ::testing::_;
using namespace jnimock;
using ::testing::NiceMock; // Note: "NiceMock" does not nag with useless warnings.

struct _jfieldID {
}; // jni.h only defines a forward reference. So we complete the definition with an empty struct here. Thus we will be
// able to create empty objects of type _jfieldID.

/**
 * When the `value` field of the Int container cannot be accessed, pushValue shall throw Fatal Exception into the JVM.
 */
TEST(types_Int, pushValue_initialiseRefs) {
    NiceMock<JNIEnvMock> jniEnvMock;
    _jclass clazz;

    // Make the jniEnvMock.GetFieldID return a null address
    ON_CALL(jniEnvMock, GetFieldID(_, _, _))
            .WillByDefault(Return(nullptr));

    // so we expect fatal error to be thrown
    EXPECT_CALL(jniEnvMock, FatalError(_))
            .Times(1);

    Java_jackAudio4Java_types_Int_initialiseRefs(&jniEnvMock, &clazz);
}
/**
 * When the container can be accessed, pushValue shall set the field through a call to env.SetIntField
 *
TEST(UtilInt, pushValue_success) {
    NiceMock<JNIEnvMock> jniEnvMock; // NiceMock avoids "Uninteresting mock function call" warnings.
    _jobject container;
    _jfieldID fid;
    _jclass clazz;
    jint value = 4711;

    // make the jniEnvMock behave as if class and field were OK.
    ON_CALL(jniEnvMock, GetObjectClass(&container))
            .WillByDefault(Return(&clazz));
    ON_CALL(jniEnvMock, GetFieldID(&clazz, StrEq("value"), StrEq("I")))
            .WillByDefault(Return(&fid));

    // check that the value is really pushed...
    EXPECT_CALL(jniEnvMock, SetIntField(&container, &fid, value))
            .Times(1);

    Int::pushValue(&jniEnvMock, value, &container);
}

*/
