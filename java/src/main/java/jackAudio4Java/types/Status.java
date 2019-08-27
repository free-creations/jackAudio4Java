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

public class Status {
  public int statusBits = 0;

  /**
   * Overall operation failed.
   */
  public boolean hasFailure() {
    return (0x01 & statusBits) != 0;
  }

  /**
   * The operation contained an invalid or unsupported option.
   */
  public boolean hasInvalidOption() {
    return (0x02 & statusBits) != 0;
  }

  /**
   * The desired client name was not unique.  With the @ref
   * JackUseExactName option this situation is fatal.  Otherwise,
   * the name was modified by appending a dash and a two-digit
   * number in the range "-01" to "-99".  The
   * jack_get_client_name() function will return the exact string
   * that was used.  If the specified @a client_name plus these
   * extra characters would be too long, the open fails instead.
   */
  public boolean hasNameNotUnique() {
    return (0x04 & statusBits) != 0;
  }

  /**
   * The JACK server was started as a result of this operation.
   * Otherwise, it was running already.  In either case the caller
   * is now connected to jackd, so there is no race condition.
   * When the server shuts down, the client will find out.
   */
  public boolean hasServerStarted() {
    return (0x08 & statusBits) != 0;
  }

  /**
   * Unable to connect to the JACK server.
   */
  public boolean hasServerFailed() {
    return (0x10 & statusBits) != 0;
  }

  /**
   * Communication error with the JACK server.
   */
  public boolean hasServerError() {
    return (0x20 & statusBits) != 0;
  }

  /**
   * Requested client does not exist.
   */
  public boolean hasNoSuchClient() {
    return (0x40 & statusBits) != 0;
  }

  /**
   * Unable to load internal client
   */
  public boolean hasLoadFailure() {
    return (0x80 & statusBits) != 0;
  }

  /**
   * Unable to initialize client
   */
  public boolean hasInitFailure() {
    return (0x100 & statusBits) != 0;
  }

  /**
   * Unable to access shared memory
   */
  public boolean hasShmFailure() {
    return (0x200 & statusBits) != 0;
  }

  /**
   * Client's protocol version does not match
   */
  public boolean hasVersionError() {
    return (0x400 & statusBits) != 0;
  }

  /**
   * Backend error
   */
  public boolean hasBackendError() {
    return (0x800 & statusBits) != 0;
  }

  /**
   * Client zombified failure
   */
  public boolean hasClientZombie() {
    return (0x1000 & statusBits) != 0;
  }


}
