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

import java.util.Set;

public enum PortFlag {

  /**
   * if `isInput` is set, then the port can receive
   * data.
   */
  isInput(0x1),

  /**
   * if `isOutput` is set, then data can be read from
   * the port.
   */
  isOutput(0x2),

  /**
   * If `isPhysical` is set, then the port corresponds
   * to some kind of physical I/O connector.
   */
  isPhysical(0x4),

  /**
   * If the `canMonitor` flag is set, then a call to
   * {@link jackAudio4Java.Jack#portRequestMonitor(PortHandle, boolean)} makes sense.
   *
   * Precisely what this means is dependent on the client. A typical
   * result of it being called with TRUE as the second argument is
   * that data that would be available from an output port (with
   * {@link PortFlag#isPhysical} set) is sent to a physical output connector
   * as well, so that it can be heard/seen/whatever.
   *
   * Clients that do not control physical interfaces
   * should never create ports with this bit set.
   */
  canMonitor(0x8),

  /**
   * isTerminal means that the port is at the boundary to the outside world.
   *
   * - for an __input port__: the data received by the port
   *   will not be passed on or made
   *   available at any other port
   * - for an __output port__: the data available at the port
   *   does not originate from any other port
   *
   * Audio synthesizers, I/O hardware interface clients, HDR
   * systems are examples of clients that would set this flag for
   * their ports.
   */
  isTerminal(0x10);

  private final long bits;

  PortFlag(long bits) {
    this.bits = bits;
  }

  public long getBits() {
    return bits;
  }

  /**
   * Builds a single Integer resulting by  OR-ing together the integer values of the flags in the given set.
   * @param flags a set of port flags
   * @return an Integer resulting by  OR-ing together the integer values of the flags in the given set.
   */
  public static long setToLong(Set<PortFlag> flags) {
    long result = 0;
    for(PortFlag flag: flags ){
      result = result | flag.getBits();
    }
    return result;
  }

  /**
   * Builds a single Integer resulting by  OR-ing together the integer values of the flags in the given set.
   * @param flags a set of port flags
   * @return an Integer resulting by  OR-ing together the integer values of the flags in the given set.
   */
  public static long arrayToLong(PortFlag[] flags) {
    long result = 0;
    for(PortFlag flag: flags ){
      result = result | flag.getBits();
    }
    return result;
  }
}
