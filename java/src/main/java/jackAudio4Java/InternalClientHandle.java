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
package jackAudio4Java;

import jackAudio4Java.types.ClientHandle;

/**
 * Instances of this class shall not be created by any object other than {@link Jack}.
 */
class InternalClientHandle extends ClientHandle {

  InternalClientHandle(long reference) {
    this.reference = reference;
  }

  long getReference() {
    return reference;
  }

  /**
   * Call this function when closing the client.
   */
  void invalidate() {
    reference = 0;
  }

  /**
   * Attempt to cast the given handle into {@link InternalClientHandle) and
   * extract its refeference.
   * @param handle
   * @return the internal reference or zero if the given object is not a valid
   * InternalClientHandle.
   */
  static long getReferenceFrom(ClientHandle handle){
    if(!handle.isValid()){
      return 0;
    }
    if (!(handle instanceof InternalClientHandle)){
      return 0;
    }
    return ((InternalClientHandle) handle).getReference();
  }
}

