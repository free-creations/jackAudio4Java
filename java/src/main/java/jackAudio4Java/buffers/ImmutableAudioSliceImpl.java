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


import java.nio.FloatBuffer;

/**
  *
 */
class ImmutableAudioSliceImpl implements ImmutableAudioSlice, InternalAudioSlice {

  private FloatBuffer buffer;
  private final Manager manager;

  ImmutableAudioSliceImpl(FloatBuffer buffer, Manager manager) {
    this.buffer = buffer;
    this.manager = manager;
  }


  @Override
  public int length() {
    return buffer.limit();
  }


  @Override
  public float get(int index) {
    return buffer.get(index);
  }

  @Override
  public MutableAudioSlice mutableCopy() {
    return manager.makeMakeMutableAudioSlice(this);
  }

  @Override
  public long validThru() {
    return 0;
  }

  @Override
  public void expire() {
    manager.recycle(buffer);
    buffer = null;

  }

  @Override
  public boolean isExpired() {
    return buffer == null;
  }

  @Override
  public FloatBuffer accessInternalBuffer() {
    return buffer;
  }
}
