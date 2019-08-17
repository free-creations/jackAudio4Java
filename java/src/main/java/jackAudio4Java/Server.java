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

import com.shankyank.jniloader.JNILoader;
import jackAudio4Java.types.Int;

import java.io.IOException;

public class Server {

  private static final String libraryName = "jackaudio4java_0.0-snapshot";
  private static final Object initializeLock = new Object();

  public enum State {
    UNINITIALIZED,
    OK,
    CRASHED
  }

  private static State initializationState = State.UNINITIALIZED;

  public static State getInitializationState() {
    synchronized (initializeLock) {
      return initializationState;
    }
  }


  /**
   * Initialize the system by loading the necessary __native libraries__ from the resource bundle.
   *
   * @throws IOException When no Library matching the Operating System and processor architecture
   * of the the runtime platform could be found.
   */
  public static void initialize() throws IOException {
    synchronized (initializeLock) {
      // already initialized? Then just return.
      if (initializationState == State.OK) return;

      // Lets try to initialise. A crash might happen.
      initializationState = State.CRASHED;
      JNILoader loader = new JNILoader();
      loader.extractLibs("/native", libraryName);
      System.loadLibrary(libraryName);
      // OK it worked...
      initializationState = State.OK;
    }
  }

  /**
   * Get the version of the JACK-server in form of several numbers.
   *
   * @param majorRef Integer- container receiving major version of JACK.
   * @param minorRef Integer- container receiving minor version of JACK.
   * @param microRef Integer- container receiving micro version of JACK.
   * @param protoRef Integer- container receiving protocol version of JACK.
   */
  public static void jack_get_version(
      Int majorRef,
      Int minorRef,
      Int microRef,
      Int protoRef) {
    _jack_get_version(majorRef, minorRef, microRef, protoRef);
  }

  private native static void _jack_get_version(
      Int majorRef,
      Int minorRef,
      Int microRef,
      Int protoRef);

  /**
   * Get the version of the native Java-method interface.
   *
   * The major version number is in the higher 16 bits and the minor version number is in the lower 16 bits.
   *
   * At the time of writing, the following constants were defined:
   *
   * - `JNI_VERSION_1_1 0x00010001`
   * - `JNI_VERSION_1_2 0x00010002`
   * - `JNI_VERSION_1_4 0x00010004`
   * - `JNI_VERSION_1_6 0x00010006`
   * - `JNI_VERSION_1_8 0x00010008`
   * - `JNI_VERSION_9   0x00090000`
   * - `JNI_VERSION_10  0x000a0000`
   *
   * @return the version of the Java-Native-Interface
   * @see <a href="https://docs.oracle.com/en/java/javase/12/docs/specs/jni/functions.html#version-constants">
   * Java Native Interface Specification</a>
   */
  public static int jni_get_version() {
    return _jni_get_version();
  }

  private native static int _jni_get_version();
}
