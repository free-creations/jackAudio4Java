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

import jackAudio4Java.NativeManager;

/**
 * Container of an Integer value.
 * Permits to pass integers by reference, as is often used in C programming.
 * Note: the java.lang.Integer class is not suitable, as this class is immutable.
 */
public class Int {
  /**
   * Registers JNI specific identities.
   **/
  private static native void registerIDs();

  static {
    NativeManager.checkNative();
    registerIDs();
  }

  public Int() {
    this(0);
  }

  public Int(int v) {
    value = v;
  }

  /**
   * The value of this container.
   */
  public int value;
}
