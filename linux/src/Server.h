/*
 * File: Server.h
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
#pragma once

#include "jni_headers/jackAudio4Java_Jack.h"
#include "types_Int.h"


/**
 * The `Server` class encapsulates the native part of the Java class `jackAudio4Java.Server`.
 *
 * The javah names like `Java_jackAudio4Java_Server__1jni_1get_1version` are aliased to
 * more intelligible names like `Server::jni_get_version`.
 *
 */
class Server {
public:

    static void inline
    jack_get_version(JNIEnv *env,
                     types::jIntObject majorRef,
                     types::jIntObject minorRef,
                     types::jIntObject microRef,
                     types::jIntObject protoRef) {
        Java_jackAudio4Java_Jack_getJackVersionN(env, nullptr, majorRef, minorRef, microRef, protoRef);
    }

    static jint inline
    jni_get_version(JNIEnv *env) {
        return Java_jackAudio4Java_Jack_getJniVersionN(env, nullptr);
    }
};
