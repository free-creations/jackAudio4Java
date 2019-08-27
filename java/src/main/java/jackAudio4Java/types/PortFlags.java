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

public enum PortFlags {

  /**
   * if JackPortIsInput is set, then the port can receive
   * data.
   */
  JackPortIsInput(0x1),

  /**
   * if JackPortIsOutput is set, then data can be read from
   * the port.
   */
  JackPortIsOutput(0x2),

  /**
   * if JackPortIsPhysical is set, then the port corresponds
   * to some kind of physical I/O connector.
   */
  JackPortIsPhysical(0x4),

  /**
   * if JackPortCanMonitor is set, then a call to
   * jack_port_request_monitor() makes sense.
   * <p>
   * Precisely what this means is dependent on the client. A typical
   * result of it being called with TRUE as the second argument is
   * that data that would be available from an output port (with
   * JackPortIsPhysical set) is sent to a physical output connector
   * as well, so that it can be heard/seen/whatever.
   * <p>
   * Clients that do not control physical interfaces
   * should never create ports with this bit set.
   */
  JackPortCanMonitor(0x8),

  /**
   * JackPortIsTerminal means:
   * <p>
   * for an input port: the data received by the port
   * will not be passed on or made
   * available at any other port
   * <p>
   * for an output port: the data available at the port
   * does not originate from any other port
   * <p>
   * Audio synthesizers, I/O hardware interface clients, HDR
   * systems are examples of clients that would set this flag for
   * their ports.
   */
  JackPortIsTerminal(0x10);

  private final long bits;

  PortFlags(long bits) {
    this.bits = bits;
  }

  public long getBits() {
    return bits;
  }
}
