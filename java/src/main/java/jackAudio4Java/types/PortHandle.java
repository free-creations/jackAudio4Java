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

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

/**
 * PortHandle stores a _native address_ to a port.
 *
 * A _native address_ points to a real physical memory location, such a piece of data should
 * never be accessed outside this package. Therefore, this public-class hides the reference
 * in a protected variable.
 */
public class PortHandle {
  /**
   * The native address of this port.
   * <p>
   * Only the class InternalPortHandle
   * shall have write access to this item.
   * <p>
   *   Note: there may be several handle objects representing the same physical port.
   * </p>
   */
  protected volatile long reference;


  protected PortHandle(long reference) {
    this.reference = reference;
  }



  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof PortHandle)) return false;
    PortHandle handle = (PortHandle) o;
    return reference == handle.reference;
  }

  @Override
  public int hashCode() {
    return Objects.hash(reference);
  }

  /**
   * A port handle is considered valid, if it is not referencing a null pointer.
   *
   * @toDo note, there is no guarantee that a non-null reference still points to an existing port.
   *       A once valid port-reference might have been unregistered for example. Does Jack
   *       crash when we query for the  port-name of such a reference? In the future object oriented
   *       interface, we might better use jack_port_id_t (received through the port registration callback)
   *       to identify the port- object.
   * @return true if the handle may reference an existing port.
   */
  public boolean isValid() {
    return (reference != 0);
  }
}