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

/**
 * The Valid options for opening an external client.
 */
public enum OpenOption {


  /**
   * Do not automatically start the JACK server when it is not
   * already running.  This option is always selected if
   * `JACK_NO_START_SERVER` is defined in the calling _process
   * environment_.
   */
  NoStartServer(0x01),

  /**
   * Use the exact client name requested.  Otherwise, JACK
   * automatically generates a unique one, if needed.
   */
  UseExactName(0x02),

  /**
   * Open with optional `serverName` parameter.
   * @deprecated this is handled automatically in
   * {@link jackAudio4Java.Jack#clientOpen(String, Set, OpenStatus, String)} )}
   */
  ServerName(0x04),
  /**
   * Pass a SessionID- Token, this allows the session-manager to identify the client again.
   */
  SessionID(0x20);

  OpenOption(int i) {

  }



}
