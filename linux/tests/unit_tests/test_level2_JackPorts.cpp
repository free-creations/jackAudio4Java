/*
 * File: test_JackClient.cpp
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
#include <exception>
#include <jack/jack.h>
#include "spdlog/spdlog.h"

#include "gtest/gtest.h"
#include "gmock/gmock.h"
#include "jnimock/jnimock.h"
#include "jni_headers/jackAudio4Java_Jack.h"

#include "Jack.h"

using testing::Return;
using testing::NiceMock; // Note: "NiceMock" does not nag with useless warnings.
using testing::_;
using testing::Ge;
using testing::StrEq;
using testing::AtLeast;


using namespace jnimock;

struct _jmethodID {
    // jni.h only defines a forward reference.
    // We complete the definition with an empty struct here.
    // This will enable us to create empty objects of type _jmethodID.
};

class TestLevel_2_Ports : public ::testing::Test {
protected:
    const char *clientName = "Level_2_Tests";
    const char *inputPortName = "input_port";
    const char *outputPortName = "output_port";
    jlong clientHandle = 0;
    jlong inputPortHandle = 0;
    jlong outputPortHandle = 0;

    void SetUp() override {
        Jack::setLoggingLevel(0);

        const char *server_name = nullptr;
        jack_options_t options = JackNullOption;
        jack_status_t status;
        clientHandle = (jlong) jack_client_open(clientName, options, &status, server_name);
        EXPECT_NE(clientHandle, 0);
        if (status & JackFailure) {
            throw std::runtime_error("Failed to open Jack Client.");
        } else {
            spdlog::info("Jack client opened.");
        }

        inputPortHandle = (long) jack_port_register((jack_client_t *) clientHandle, inputPortName,
                                                    JACK_DEFAULT_AUDIO_TYPE,
                                                    JackPortIsInput, 0);
        outputPortHandle = (long) jack_port_register((jack_client_t *) clientHandle, outputPortName,
                                                     JACK_DEFAULT_AUDIO_TYPE,
                                                     JackPortIsOutput, 0);
    }

    void TearDown() override {
        int failure = jack_client_close((jack_client_t *) clientHandle);

        if (failure) {
            throw std::runtime_error("Failed to close Jack Client.");
        } else {
            spdlog::info("Jack client closed.");
        }
    }
};

/**
 * Function `Java_jackAudio4Java_Jack_portNameN` shall return the client and port names given
 * in above  SetUp() method.
 */
TEST_F(TestLevel_2_Ports, getPortName) {

    NiceMock<JNIEnvMock> jniEnvMock;
    // Verify that the client name is what we have set in the "SetUp".
    EXPECT_CALL(jniEnvMock, NewStringUTF(StrEq("Level_2_Tests:input_port")))
            .Times(1);

    // here we go...
    jstring portNameJava = Java_jackAudio4Java_Jack_portNameN(&jniEnvMock, nullptr, inputPortHandle);
}

/**
 * Function `Java_jackAudio4Java_Jack_portShortNameN` shall return the  port name given
 * in above  SetUp() method.
 */
TEST_F(TestLevel_2_Ports, getPortShortName) {

    NiceMock<JNIEnvMock> jniEnvMock;
    // Verify that the client name is what we have set in the "SetUp".
    EXPECT_CALL(jniEnvMock, NewStringUTF(StrEq(inputPortName)))
            .Times(1);

    // here we go...
    jstring portNameJava = Java_jackAudio4Java_Jack_portShortNameN(&jniEnvMock, nullptr, inputPortHandle);
}

