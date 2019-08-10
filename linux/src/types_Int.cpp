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


#ifndef JNI_VERSION_1_2
#error "Needs Java version 1.2 or higher.\n"
#endif

/* map the names chosen by the java-header tool to our names */
JNIEXPORT void JNICALL Java_jackAudio4Java_types_Int_initialiseRefs(JNIEnv *env, jclass clazz){
    types::Int::initialiseRefs(env, clazz);
}


/**
 * The cached field ID of the Java field `jackAudio4Java_types_Int.value`.
 *
 * Note: the IDs returned for a given class don't change for the lifetime of the JVM process.
 * Thus we do not have to worry about race conditions or different environments having different classIDs.
 */
static jfieldID Java_jackAudio4Java_types_Int_value_ID = nullptr;

/**
 * The native class initializer will cache access information, for later use in C++.
 * This follows the recommendations given by IBM here:
 * https://www.ibm.com/developerworks/library/j-jni/index.html#notc
 **/


/*
 * This namespace implements the native functions of the Java class `jackAudio4Java.types.Int`.
 */
namespace types{
    namespace Int {
        /**
         *
         * @param env
         * @param clazz
         */
        void initialiseRefs(JNIEnv *env, jclass clazz) {
            // cache the field ID.
            // Note: we shall not cache the clazz. It might vary over the lifetime of the JVM process.
            Java_jackAudio4Java_types_Int_value_ID = env->GetFieldID(clazz, "value", "I");
            if (!Java_jackAudio4Java_types_Int_value_ID)
                env->FatalError("JNI Fatal Error - class `jackAudio4Java_types_Int` has no `value` field");
        }


        /**
        *
        * @param env
        * @param IntContainer
        * @param value
        */
        void setValue(JNIEnv *env, jIntObject IntContainer, jint value) {
            env->SetIntField(IntContainer, Java_jackAudio4Java_types_Int_value_ID, value);
        }

    }
}