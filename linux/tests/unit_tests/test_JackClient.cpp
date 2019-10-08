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

class JackTestClient : public ::testing::Test {
protected:
    const char *client_name = "Nervensäge";
    jlong clientHandle = 0;

    void SetUp() override {
        Jack::setLoggingLevel(0);

        const char *server_name = nullptr;
        jack_options_t options = JackNullOption;
        jack_status_t status;
        clientHandle = (jlong) jack_client_open(client_name, options, &status, server_name);
        EXPECT_NE(clientHandle, 0);
        if (status & JackFailure) {
            throw std::runtime_error("Failed to open Jack Client.");
        } else {
            spdlog::info("Jack client opened.");
        }

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
 * Function `Java_jackAudio4Java_Jack_getClientNameN` shall return the client name given
 * in above  SetUp() method.
 */
TEST_F(JackTestClient, getClientName) {


    NiceMock<JNIEnvMock> jniEnvMock;
    // Verify that the client name is what we have set in the "SetUp".
    EXPECT_CALL(jniEnvMock, NewStringUTF(StrEq(client_name)))
            .Times(1);

    // here we go...
    jstring clientNameJava = Java_jackAudio4Java_Jack_getClientNameN(&jniEnvMock, nullptr, clientHandle);
}




/**
 * A client can register and unregister a port.
 */
TEST_F(JackTestClient, portRegister_Unregister) {
    NiceMock<JNIEnvMock> jniEnvMock;

    _jstring portNameJ; // the "java version" of the port name.
    const char *portNameN = "InputPort"; // the "native version" of the port name.

    _jstring portType; // the "java version" of the port type.
    const char *portTypeN = JACK_DEFAULT_AUDIO_TYPE; // the "native version" of the port type.

    jlong portFlags = JackPortIsInput;
    jlong bufferSize = 0;

    // make the jniEnvMock return portNameN for portNameJ
    ON_CALL(jniEnvMock, GetStringUTFChars(&portNameJ, nullptr))
            .WillByDefault(Return(portNameN));

    // make the jniEnvMock return portTypeN for portTypeJ
    ON_CALL(jniEnvMock, GetStringUTFChars(&portType, nullptr))
            .WillByDefault(Return(portTypeN));

    // here we go...
    auto portHandle = Java_jackAudio4Java_Jack_portRegisterN(&jniEnvMock,
                                                             nullptr,
                                                             clientHandle,
                                                             &portNameJ,
                                                             &portType,
                                                             portFlags,
                                                             bufferSize);
    EXPECT_NE(portHandle, 0);

    // ... and remove this port again.
    auto error = Java_jackAudio4Java_Jack_portUnregisterN(&jniEnvMock, nullptr, clientHandle, portHandle);
    EXPECT_EQ(error, 0);
}

/**
 * A client can register a ProcessListener.
 */
TEST_F(JackTestClient, registerProcessListener) {
    NiceMock<JNIEnvMock> jniEnvMock;
    _jobject newListener;
    _jmethodID processListener_onProcess;

    // make the jniEnvMock return a non null value for GetMethodID
    ON_CALL(jniEnvMock, GetMethodID(_, _, _))
            .WillByDefault(Return(&processListener_onProcess));

    jint error = Java_jackAudio4Java_Jack_registerProcessListenerN(&jniEnvMock, nullptr, clientHandle, &newListener);
    EXPECT_EQ(error, 0);
}

/**
 * A client can register a ProcessListener.
 */
TEST_F(JackTestClient, activateDeactivate) {
    jint error;
    error = Java_jackAudio4Java_Jack_activateN(nullptr, nullptr, clientHandle);
    EXPECT_EQ(error, 0);

    error = Java_jackAudio4Java_Jack_deactivateN(nullptr, nullptr, clientHandle);
    EXPECT_EQ(error, 0);
}
/**
 * The sample rate for a client should be within a plausible range of
 * 4 to 96 thousand samples per second.
 */
TEST_F(JackTestClient, getSampleRate) {
    jint sampleRate = Java_jackAudio4Java_Jack_getSampleRateN(nullptr, nullptr, clientHandle);
    EXPECT_GE(sampleRate, 4000);
    EXPECT_LE(sampleRate, 96000);
}

/**
  * There should be at least one physical output port in the driver backend.
  */
TEST_F(JackTestClient, getPorts) {
    NiceMock<JNIEnvMock> jniEnvMock;

    EXPECT_CALL(jniEnvMock, SetObjectArrayElement(_, _, _))
            .Times(AtLeast(1));

    Java_jackAudio4Java_Jack_getPortsN(&jniEnvMock, nullptr, clientHandle, nullptr, nullptr, 0);
}

/**
  * If searching for a port whose name match "impossible", no port should be found.
  */
TEST_F(JackTestClient, getPortsImpossible) {
    NiceMock<JNIEnvMock> jniEnvMock;
    _jstring portNamePatternJ; // the "java version" of the port name pattern.
    const char *portNamePatternN = "impossibleRubbish"; // the "native version" of the port name pattern.


    // make the jniEnvMock return portNameN for portNameJ
    ON_CALL(jniEnvMock, GetStringUTFChars(&portNamePatternJ, nullptr))
            .WillByDefault(Return(portNamePatternN));

    auto result = Java_jackAudio4Java_Jack_getPortsN(&jniEnvMock, nullptr, clientHandle, &portNamePatternJ, nullptr, 0);
    EXPECT_EQ(result, nullptr);
}