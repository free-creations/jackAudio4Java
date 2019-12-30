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

import jackAudio4Java.utilities.NotYetImplementedException;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Manager {

  /**
   * Create a direct float buffer of given length.
   *
   * @param length the number float elements provided by the buffer.
   * @return a direct float buffer.
   */
  private static FloatBuffer makeDirectFloatBuffer(int length) {
    ByteBuffer newByteBuffer = ByteBuffer.allocateDirect(length * Float.SIZE / Byte.SIZE);
    newByteBuffer.order(ByteOrder.nativeOrder()); // see https://bugs.openjdk.java.net/browse/JDK-5043362
    FloatBuffer newFloatBuffer = newByteBuffer.asFloatBuffer();
    newFloatBuffer.clear();
    return newFloatBuffer;
  }

  protected Manager(){

  }

  MutableAudioSlice makeMakeMutableAudioSlice(ImmutableAudioSliceImpl immutableAudioSlice) {
    throw new NotYetImplementedException();
  }

  void recycle(FloatBuffer buffer) {
    throw new NotYetImplementedException();
  }
}
