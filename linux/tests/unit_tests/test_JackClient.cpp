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


using namespace jnimock;

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
        int failure = jack_client_close((jack_client_t *)clientHandle);

        if (failure ) {
            throw std::runtime_error("Failed to close Jack Client.");
        } else {
            SPDLOG_TRACE("Jack client closed.");
        }
    }
};

/**
 * Function `Server::jack_get_version` shall push the _jack library_ version into the
 * `Int` objects provided by the caller.
 */
TEST_F(JackTestClient, jack_getClientName) {


    NiceMock<JNIEnvMock> jniEnvMock;



    // Verify that the client name is what we have set in the "SetUp".
    EXPECT_CALL(jniEnvMock, NewStringUTF(StrEq(client_name)))
            .Times(1);

    // here we go...
    jstring clientNameJava =  Java_jackAudio4Java_Jack_getClientNameN (&jniEnvMock, nullptr, clientHandle);

}
