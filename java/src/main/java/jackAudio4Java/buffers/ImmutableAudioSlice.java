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


/**
 * An immutable sequence of audio samples.
 * <p>
 * Its length is the length of one audio cycle.
 */
public interface ImmutableAudioSlice extends AudioSlice{
  /**
   * Returns the number of audio samples in this slice.
   *
   * @return the number of audio samples in this slice.
   */
  int length();


  /**
   * Absolute get method. Reads the float at the given index.
   *
   * @param index The index from which the float will be read
   * @return The audio sample at the given index
   * @throws IndexOutOfBoundsException If index is negative or not smaller than the buffer's limit
   */
  float get(int index);

  /**
   * Returns a writable copy of this slice.
   * @return a writable copy of this slice.
   */
  MutableAudioSlice mutableCopy();
}
