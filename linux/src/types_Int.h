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
#ifndef JACKAUDIO4JAVA_TYPES_INT_H
#define JACKAUDIO4JAVA_TYPES_INT_H

#include "jni_headers/jackAudio4Java_types_Int.h"


class _jIntObject : public _jobject {
};

typedef _jIntObject *jIntObject;


/*
 * Class:     jackAudio4Java_types_Int
 * Method:    setValue
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_jackAudio4Java_types_Int_setValue
        (JNIEnv *, jIntObject, jint);

#endif //JACKAUDIO4JAVA_TYPES_INT_H
