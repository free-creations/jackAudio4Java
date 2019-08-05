/*
 * File: utils.cpp
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
#include <stdexcept>
#include "utils.h"

/**
 * Utilities
 * =========
 *
 */

/**
 * Pushes an integer value into the container referenced by the parameter `container`.
 *
 * @param env pointer to the Java environment.
 * @param value the value that should be pushed into the Int container.
 * @param container pointer to a Java object of type `jackAudio4Java.types.Int`.
 *
 * @throws invalid_argument if the container could not be accessed.
 */
void Int::pushValue(JNIEnv * env, const jint &value, jobject container ){
    if (!container) return; // no container => do nothing.
    if (!env) return;       // no java-environment => do nothing.

    // Get a reference to the to the 'value' field in the container.
    jclass clazz = env->GetObjectClass(container);
    jfieldID fid = env->GetFieldID(clazz,"value", "I");
    if(!fid) throw std::invalid_argument( "class does not have an integer field called 'value'." );

    // push the given value into the field
    env->SetIntField(container,fid, value);
}
