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


import jackAudio4Java.types.PortFlag;
import jackAudio4Java.types.PortHandle;
import jackAudio4Java.types.PortType;

import java.util.Set;

/**
 * Instances of this class shall not be created by any object other than {@link Jack}.
 */
class InternalPortHandle extends PortHandle {

  InternalPortHandle(long reference, String portName, PortType portType, Set<PortFlag> portFlags) {
    super ( reference,  portName,  portType,  portFlags);
  }



  long getReference() {
    return reference;
  }

  /**
   * Call this function when closing the port.
   */
  void invalidate() {
    reference = 0;
  }
}

