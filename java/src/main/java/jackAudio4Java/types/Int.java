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
 * <p>
 * Note: the `java.lang.Integer` class is not suitable for this purpose, as it is immutable.
 */
public class Int {
  /**
   * Registers JNI specific identities.
   **/
  private static native void registerIdsN();

  static {
    NativeManager.checkNative();
    registerIdsN();
  }

  /**
   * Build an Int-container holding an initial value of 0.
   */
  public Int() {
    this(0);
  }

  /**
   * Build an Int-container holding the given initial value.
   */
  public Int(int v) {
    value = v;
  }

  /**
   * The value that this container holds.
   */
  public int value;
}
