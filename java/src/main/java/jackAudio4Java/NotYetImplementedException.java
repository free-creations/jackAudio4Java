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

/**
 * Thrown to indicate that a block of code has not been implemented.
 */
public class NotYetImplementedException extends UnsupportedOperationException
{
  /**
   * @deprecated Deprecated to remind me to implement the corresponding code
   *             before releasing the software.
   */
  @Deprecated
  public NotYetImplementedException()
  {
  }

  /**
   * @deprecated Deprecated to remind me to implement the corresponding code
   *             before releasing the software.
   */
  @Deprecated
  public NotYetImplementedException(String message)
  {
    super(message);
  }
}