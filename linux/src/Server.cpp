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

/**
 * This unit implements the native methods of the Java class: `jackAudio4Java.Server`
 *
 */
#include <jni.h>
#include <jack/jack.h>
#include "Server.h"




#ifndef JNI_VERSION_1_2
#error "Needs Java version 1.2 or higher.\n"
#endif
using namespace std;




/**
 * Function `private native static void _jack_get_version(Int majorRef, Int minorRef, Int microRef,Int protoRef);`
 *
 * Call this function to get the version of the JACK-server in form of several numbers.
 *
 * @param majorRef Integer- container receiving major version of JACK.
 * @param minorRef Integer- container receiving minor version of JACK.
 * @param microRef Integer- container receiving micro version of JACK.
 * @param protoRef Integer- container receiving protocol version of JACK.
 */
JNIEXPORT void JNICALL Java_jackAudio4Java_Jack__1jack_1get_1version
        (JNIEnv *env, jclass, jobject majorRef, jobject minorRef, jobject microRef, jobject protoRef) {

    int majorVal = -1;
    int minorVal = -1;
    int microVal = -1;
    int protoVal = -1;

    jack_get_version(&majorVal, &minorVal, &microVal, &protoVal);

    if (majorRef) types::Int::setValue(env, (types::jIntObject) majorRef, majorVal);
    if (minorRef) types::Int::setValue(env, (types::jIntObject) minorRef, minorVal);
    if (microRef) types::Int::setValue(env, (types::jIntObject) microRef, microVal);
    if (protoRef) types::Int::setValue(env, (types::jIntObject) protoRef, protoVal);
}

/**
 * Call this function to get the version of the native Java-method-interface.
 * <p>
 *   The major version number is in the higher 16 bits and the minor version number is in the lower 16 bits.
 *   At the time of writing, the following constants were defined:
 * </p>
 * <p><ul>
 * <li> JNI_VERSION_1_1 0x00010001
 * <li> JNI_VERSION_1_2 0x00010002
 * <li> JNI_VERSION_1_4 0x00010004
 * <li> JNI_VERSION_1_6 0x00010006
 * <li> JNI_VERSION_1_8 0x00010008
 * <li> JNI_VERSION_9   0x00090000
 * <li> JNI_VERSION_10  0x000a0000
 * </ul></p>
 * @see <a href="https://docs.oracle.com/en/java/javase/12/docs/specs/jni/functions.html#version-constants">
 *   Java Native Interface Specification</a>
 * @return the version of the Java-Native-Interface
 */
JNIEXPORT jint JNICALL Java_jackAudio4Java_Jack__1jni_1get_1version
        (JNIEnv *env, jclass) {
    return env->GetVersion();
}