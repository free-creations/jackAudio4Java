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

#include "utils.h"
#include "jni.h"

using testing::Return;
using testing::StrEq;
using ::testing::_;
using namespace jnimock;
using ::testing::NiceMock;

struct _jfieldID {
}; // jni.h only defines a forward reference. So we complete the definition with an empty struct here. Thus we will be
// able to allocate objects of type _jfieldID.

/**
 * When the `value` field of the Int container cannot be accessed, pushValue shall throw an exception.
 */
TEST(UtilInt, pushValue_InvalidField) {
    NiceMock<JNIEnvMock> jniEnvMock; // Note: "NiceMock" does not nag with "Uninteresting mock function call" warnings.
    _jobject container;
    jint value = 4711;

    // By default, jniEnvMock.GetFieldID will return a null address

    EXPECT_THROW(Int::pushValue(&jniEnvMock, value, &container), std::invalid_argument);


}
/**
 * When the container can be accessed, pushValue shall set the field through a call to env.SetIntField
 */
TEST(UtilInt, pushValue_success) {
    NiceMock<JNIEnvMock> jniEnvMock; // NiceMock avoids "Uninteresting mock function call" warnings.
    _jobject container;
    _jfieldID fid;
    _jclass clazz;
    jint value = 4711;

    // make the jniEnvMock behave as if class and field were OK.
    ON_CALL(jniEnvMock, GetObjectClass(&container))
            .WillByDefault(Return(&clazz));
    ON_CALL(jniEnvMock, GetFieldID(&clazz,StrEq("value"), StrEq("I")))
            .WillByDefault(Return(&fid));

    // check that the value is really pushed...
    EXPECT_CALL(jniEnvMock, SetIntField(&container, &fid, value))
            .Times(1);

    Int::pushValue(&jniEnvMock, value, &container);
}


