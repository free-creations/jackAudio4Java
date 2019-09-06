/*
 * File: jackAudio4Java_types_Int.cpp
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
 * This unit implements the native methods of the Java class: `jackAudio4Java.types.Int`
 *
 */

#include <jni.h>
#include "types_Int.h"

#include "spdlog/spdlog.h"

#ifndef JNI_VERSION_1_2
#error "Needs Java version 1.2 or higher.\n"
#endif

/*
 * This namespace implements the native functions of the Java class `jackAudio4Java.types.Int`.
 */
namespace types {

    /**
     * The cached field ID of the Java field `jackAudio4Java_types_Int.value`.
     *
     * Note: the IDs returned for a given class don't change for the lifetime of the JVM process.
     * Thus we do not have to worry about race conditions or different environments having different classIDs.
     */
    std::atomic<jfieldID> Int::value_ID;

    /**
    * Sets the `value` field.
    * @param env pointer to the JNI environment.
    * @param IntContainer JNI- reference to the Int container object.
    * @param value the new value.
    */
    void Int::setValue(JNIEnv *env, jIntObject IntContainer, jint value) {

        if (IntContainer) env->SetIntField(IntContainer, value_ID, value);
    }
}

/**
 * The native class initializer will cache access information, for later use in C++.
 * This follows the recommendations given by IBM here:
 * https://www.ibm.com/developerworks/library/j-jni/index.html#notc
 * @param env pointer to the JNI environment.
 * @param clazz JNI reference to the Java- class of the Int container.
 */
JNIEXPORT void JNICALL Java_jackAudio4Java_types_Int_registerIdsN(JNIEnv *env, jclass clazz) {
    SPDLOG_TRACE("Java_jackAudio4Java_types_Int_registerIdsN");

    // cache field IDs.
    // Note: we shall not cache the clazz. It might vary over the lifetime of the JVM process.
    types::Int::value_ID = env->GetFieldID(clazz, "value", "I");
    if (!types::Int::value_ID) {
        SPDLOG_CRITICAL("class `jackAudio4Java_types_Int` has no `value` field");
        env->FatalError("JNI Fatal Error - class `jackAudio4Java_types_Int` has no `value` field");
    }
}
