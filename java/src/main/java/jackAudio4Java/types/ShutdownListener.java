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

public interface ShutdownListener {
  /**
   * Prototype for the client supplied function that is called
   * whenever jackd is shutdown.
   * <p>
   * The function must be written as if it were an asynchonrous POSIX
   * signal- handler --- use only async-safe functions, and remember that it
   * is executed from another thread.  A typical function might
   * set a flag or write to a pipe so that the rest of the
   * application knows that the JACK client thread has shut
   * down.
   * <p>
   * Warning: the function {@link jackAudio4Java.Jack#clientClose(ClientHandle)} cannot be
   * safely used inside the shutdown callback and has to be called outside of
   * the callback context.
   * <p>
   * Further, the native documentation says:
   * <p>
   * > Note that after server shutdown,
   * > the client pointer is __not__ deallocated by libjack,
   * > the application is responsible to properly use jack_client_close()
   * > to release client ressources.
   * <p>
   * The above note is (probably) irrelevant for _jackAudio4Java_, because
   * in Java, the arg-Object is not passed to the server, but is managed
   * by the Java engine. But nevertheless, be aware of race conditions.
   *
   * @param arg pointer to a client supplied structure
   */
  public void onShutdown(Object arg);
}
