/*
 * File: types_Int.h
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

#include "jni_headers/jackAudio4Java_types_Int.h"
#include <atomic>

namespace types {
    /**
     * A pseudo class that stands for the Java class `jackAudio4Java.types.Int`.
     */
    class _jIntObject : public _jobject {
    };

    /**
     * A JNI reference to a Java object of class `jackAudio4Java.types.Int`.
     */
    typedef _jIntObject *jIntObject;

    /**
     * The `Int` class encapsulates the native part of the Java class `jackAudio4Java.types.Int`.
     */
    class Int {
    public:
        static std::atomic<jfieldID> value_ID;

        static void inline registerIDs(JNIEnv *env, jclass clazz) {
            Java_jackAudio4Java_types_Int_registerIDs(env, clazz);
        }

        static void setValue(JNIEnv *env, jIntObject IntContainer, jint value);
    };
}



