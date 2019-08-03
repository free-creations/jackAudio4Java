/*
 * File: server.cpp
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
#include <jni.h>
#include "jni_headers/jackAudio4Java_Server.h"

#ifndef JNI_VERSION_1_2
#error "Needs Java version 1.2 or higher.\n"
#endif


JNIEXPORT void JNICALL Java_jackAudio4Java_Server_jack_1get_1version_1n
        (JNIEnv *, jclass, jobject, jobject, jobject, jobject) {

}



JNIEXPORT int JNICALL XXXJava_jackAudio4Java_Server_test(int variable) {
    return variable;
}