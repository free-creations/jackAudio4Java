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

/**
 * Container of an Integer value.
 * Permits to pass integers by reference, as is often used in C programming.
 */
public class Int {
  /**
   * The native class initializer will cache access information, for later use in C++.
   * This follows the recommendations given by IBM here:
   * https://www.ibm.com/developerworks/library/j-jni/index.html#notc
   **/
  private static native void initialiseRefs();


  static {
    initialiseRefs();
  }

  public int value;
}
