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
#include "jni_headers/jackAudio4Java_Server.h"

TEST(Example, Equals) {
    EXPECT_EQ(4711, XXXJava_jackAudio4Java_Server_test(4711));
}

TEST(Server_get_version, Equals) {
    EXPECT_EQ(4711, XXXJava_jackAudio4Java_Server_test(4711));
}