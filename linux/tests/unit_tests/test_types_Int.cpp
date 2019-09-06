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
#include "Jack.h"
#include "types_Int.h"
#include "jni.h"

using testing::Return;
using testing::StrEq;
using testing::_;
using testing::NiceMock; // Note: "NiceMock" does not nag with useless warnings.

using namespace jnimock;
using namespace types;




struct _jfieldID {
    // jni.h only defines a forward reference.
    // We complete the definition with an empty struct here.
    // This will enable us to create empty objects of type _jfieldID.
};

class types_IntTest : public ::testing::Test {
protected:
    void SetUp() override {
        Jack::setLoggingLevel(0);
    }

    void TearDown() override {}
 };

/**
 * When the no fieldID can be found, 'registerIDs' shall throw a Fatal Exception into the JVM.
 */
TEST_F(types_IntTest, registerIDs_badField) {
    NiceMock<JNIEnvMock> jniEnvMock;
    _jclass clazz;

    // Make the `jniEnvMock.GetFieldID` return a null address. That means "fieldID not found".
    ON_CALL(jniEnvMock, GetFieldID(_, _, _))
            .WillByDefault(Return(nullptr));

    // so we expect fatal error to be thrown
    EXPECT_CALL(jniEnvMock, FatalError(_))
            .Times(1);

    // and here we go...
    Int::registerIDs(&jniEnvMock, &clazz);
}

/**
 * When the `fieldID` is OK, we expect `registerIDs` to store this `fieldID` into `types::Int::value_ID`
 */
TEST_F(types_IntTest, registerIDs) {
    NiceMock<JNIEnvMock> jniEnvMock;
    _jfieldID fieldID;
    _jclass clazz;

    // Make the jniEnvMock.GetFieldID return the address of fieldID
    ON_CALL(jniEnvMock, GetFieldID(_, _, _))
            .WillByDefault(Return(&fieldID));

    // here we go
    Int::registerIDs(&jniEnvMock, &clazz);

    // we expect that  `types::Int::value_ID` is now set to `&fieldID`
    EXPECT_EQ(&fieldID, Int::value_ID);
}

/**
 * Function `setValue` shall push a new value into the `value` field of the `Int` object.
 */
TEST_F(types_IntTest, setValue) {
    NiceMock<JNIEnvMock> jniEnvMock;
    _jIntObject intObject;
    jint newValue = 4711;

    // we expect the new value to be pushed onto the Int objects `value` field.
    EXPECT_CALL(jniEnvMock, SetIntField(&intObject, _, newValue))
            .Times(1);

    // here we go
    Int::setValue(&jniEnvMock, &intObject, newValue);
}

