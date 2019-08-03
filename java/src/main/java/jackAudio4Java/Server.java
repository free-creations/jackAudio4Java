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

import jackAudio4Java.types.Int;
import org.apache.commons.lang3.Validate;

public class Server {
  /**
   * Call this function to get the version of the JACK-server in form of several numbers.
   *
   * @param major_ptr Integer object receiving major version of JACK.
   * @param minor_ptr Integer object receiving minor version of JACK.
   * @param micro_ptr Integer object receiving micro version of JACK.
   * @param proto_ptr Integer object receiving protocol version of JACK.
   */
  public static void jack_get_version(
      Int major_ptr,
      Int minor_ptr,
      Int micro_ptr,
      Int proto_ptr) {
    Validate.notNull(major_ptr, "major_ptr shall not be null");
    Validate.notNull(minor_ptr, "minor_ptr shall not be null");
    Validate.notNull(micro_ptr, "micro_ptr shall not be null");
    Validate.notNull(proto_ptr, "proto_ptr shall not be null");
    _jack_get_version(major_ptr, minor_ptr, micro_ptr, proto_ptr);
  }

  private native static void _jack_get_version(
      Int major_ptr,
      Int minor_ptr,
      Int micro_ptr,
      Int proto_ptr);

  /**
   * Call this function to get the version of the JNI-DLL in form of a single integer.
   *
   * @return the version of the Java-Native-Interface
   */
  public static int jni_get_version() {
    return _jni_get_version();
  }

  private native static int _jni_get_version();
}
