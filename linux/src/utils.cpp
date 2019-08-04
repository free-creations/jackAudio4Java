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
#include "utils.h"

/**
 * Utilities
 * =========
 *
 */

/** @ToDo make this a class with static functions */
void Int_push_value(JNIEnv * env, const jint &value, jobject container ){
    if (!container) return; // if null, do nothing.
    if (!env) return;       // if null, do nothing.
}
