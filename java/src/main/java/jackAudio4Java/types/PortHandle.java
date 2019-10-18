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
 * PortHandle is an opaque type.  It stores a reference to a port.
 */
public class PortHandle {
  /**
   * The native address of this port.
   * <p>
   * Only the class InternalPortHandle
   * shall have access to this item.
   */
  protected long reference;

  private final String name;
  private final PortType type;
  private final PortFlag[] flags;

  protected PortHandle(long reference, String portName, PortType portType, PortFlag[] portFlags) {
    this.reference = reference;
    this.name = portName;
    this.type = portType;
    this.flags = portFlags;
  }

  /**
   * Returns the name given when the port was created.
   *
   * @return the name given when the port was created.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the type of this port.
   *
   * @return the type of this port.
   */
  public PortType getType() {
    return type;
  }

  /**
   * Returns the flags given when the port was created.
   *
   * @return the flags given when the port was created.
   */
  public PortFlag[] getFlags() {
    return flags;
  }


  /**
   * A port handle is considered valid, if it is not referencing a null pointer.
   *
   * @return true if the handle is referencing an existing port.
   */
  public boolean isValid() {
    return (reference != 0);
  }
}