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

import com.github.fommil.jni.JniLoader;
import java.util.logging.Logger;

import static java.util.logging.Level.SEVERE;

public class NativeManager {

  private static final Logger jniLogger = Logger.getLogger(JniLoader.class.getName());

  private static final String libraryName = "libjackAudio4Java-0.1-SNAPSHOT.so";
  private static volatile boolean libLoaded = false;
  private static final Object loadLock = new Object();


  private static String libName(){
    return "native/"+libraryName;
  }

  private static void loadLibrary() {
    jniLogger.setLevel(SEVERE);
    JniLoader.load(libName());
  }

  public static void checkNative() {
    if (libLoaded) return;

    synchronized (loadLock) {
      if (libLoaded) return;
      loadLibrary();
      libLoaded = true;
    }
  }

}

