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
import java.io.IOException;

public class NativeManager {

  private static final String libraryName = "jackaudio4java_0.0-snapshot";
  private static volatile boolean libLoaded=false;
  private static final Object loadLock = new Object();


  private static boolean loadLibrary() {
    JNILoader loader = new JNILoader();
    try {
      loader.extractLibs("/native", libraryName);
    } catch (IOException e) {
      throw new RuntimeException("Cannot load native library", e);
    }
    System.loadLibrary(libraryName);
    return true;
  }

  public static boolean checkNative() {
    boolean result = libLoaded;
    if (!result) {
      synchronized (loadLock) {
        result = libLoaded;
        if (!result)
        libLoaded = result = loadLibrary();
      }
    }
    return result;
  }
}
