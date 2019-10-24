/*
 * Copyright 2019 Harald Postner.
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
package jackAudio4Java.buffers;

import jackAudio4Java.buffers.AudioSlice;

import java.nio.FloatBuffer;

/**
 * This is an internal interface that should not be used be clients.
 * It gives access to the hidden internal resources of a slice.
 */
public interface InternalAudioSlice extends AudioSlice {

  FloatBuffer accessInternalBuffer();

}
