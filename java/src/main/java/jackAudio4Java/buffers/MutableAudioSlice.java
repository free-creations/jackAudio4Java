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


public interface MutableAudioSlice extends ImmutableAudioSlice, AudioSlice {


  /**
   * Absolute put method. Reads the float at the given index.
   *
   * Writes the given float into this buffer at the given index.
   *
   * @param index The index from which the float will be read
   * @param value The value of the audio sample to be written
   * @throws IndexOutOfBoundsException If index is negative or not smaller than the buffer's limit
   */
  void put(int index, float value);


  /**
   * Relative put method
   *
   * Writes the given float into this buffer at the current position, and then increments the position.
   *
   * @param value The value of the audio sample to be written
   */
  void put(float value);

  /**
   * Returns this slice as immutable data which can safely be exchanged among threads.
   * @return an immutable version of this slice.
   */
  ImmutableAudioSlice asImmutable();
}