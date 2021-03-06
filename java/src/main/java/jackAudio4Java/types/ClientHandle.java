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
package jackAudio4Java.types;

/**
 * ClientHandle is an opaque type.  It stores a reference to this client.
 */
public class ClientHandle {

  /**
   * Client-handles should not be created by any object other than the Jack object.
   */
  protected ClientHandle() {
  }

  /**
   * The native address of this client. Only the class InternalClientHandle
   * shall have access to this item.
   */
  protected volatile long reference;

  /**
   * A client handle is considered valid, if it is not referencing a null pointer.
   * @return true if the handle is referencing an existing client.
   */
  public boolean isValid() {
    return (reference != 0);
  }
}

