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
 * The ProcessCallback interface should be implemented by any class
 * whose instances are intended to be called by the Jack server
 * whenever there is work be done.
 */
public interface ProcessListener {
  /**
   * Prototype for the client supplied function that is called
   * by the engine anytime there is work to be done.
   * <p>
   * ```
   * nframes == jack_get_buffer_size()
   * nframes == pow(2,x)
   * ```
   * The code supplied in this function must be suitable for real-time
   * execution.  That means that it cannot call functions that might
   * block for a long time. This includes malloc, free, printf,
   * pthread_mutex_lock, sleep, wait, poll, select, pthread_join,
   * pthread_cond_wait, etc, etc. See
   * http://jackit.sourceforge.net/docs/design/design.html#SECTION00411000000000000000
   * for more information.
   *
   * @param nframes number of frames to process
   * @param arg     pointer to a client supplied structure
   * @return zero on success, non-zero on error
   */
   int process(int nframes, Object arg);
}
