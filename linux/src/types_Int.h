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

namespace types {
    class _jIntObject : public _jobject {
    };
    typedef _jIntObject *jIntObject;

    namespace Int {
        void inline initialiseRefs(JNIEnv *env, jclass clazz){Java_jackAudio4Java_types_Int_initialiseRefs(env, clazz);}
        void setValue(JNIEnv *env, jIntObject IntContainer, jint value);
    }
}



