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

import jackAudio4Java.types.*;

import java.io.IOException;
import java.util.Set;

/**
 *
 */
public class Jack {

  private static volatile Jack instance;
  private static final Object creationLock = new Object();

  private Jack() {
    NativeManager.checkNative();
  }

  public static Jack server() {
    Jack result = instance;
    if (result == null) {
      synchronized (creationLock) {
        result = instance;
        if (result == null)
          instance = result = new Jack();
      }
    }
    return result;
  }


  /**
   * Initialize the system by loading the necessary __native libraries__ from the resource bundle.
   *
   * @throws IOException When no Library matching the Operating System and processor architecture
   *                     of the the runtime platform could be found.
   */
  private static void initialize() throws IOException {

  }


  /**
   * Get the version of the native Java-method interface.
   * <p>
   * The major version number is in the higher 16 bits and the minor version number is in the lower 16 bits.
   * <p>
   * At the time of writing, the following constants were defined:
   * <p>
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
  public int jni_get_version() {
    return _jni_get_version();
  }

  private native static int _jni_get_version();

  // jack.h - line 51

  /**
   * Get the version of the JACK-server in form of several numbers.
   *
   * @param majorRef Integer- container receiving major version of JACK.
   * @param minorRef Integer- container receiving minor version of JACK.
   * @param microRef Integer- container receiving micro version of JACK.
   * @param protoRef Integer- container receiving protocol version of JACK.
   */
  public void jack_get_version(
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

  // jack.h - line 83

  /**
   * Open an external client session with a JACK server.  This interface
   * is more complex but more powerful than jack_client_new().  With it,
   * clients may choose which of several servers to connect, and control
   * whether and how to start the server automatically, if it was not
   * already running.  There is also an option for JACK to generate a
   * unique client name, when necessary.
   *
   * @param client_name     of at most jack_client_name_size() characters.
   *                        The name scope is local to each server.  Unless forbidden by the
   * @param options         formed by OR-ing together @ref JackOptions bits.
   *                        Only the @ref JackOpenOptions bits are allowed.
   * @param statusContainer (if non-NULL) an address for JACK to return
   *                        information from the open operation.  This status word is formed by
   *                        OR-ing together the relevant @ref JackStatus bits.
   *
   *
   *                        <b>Optional parameters:</b> depending on corresponding [@a options
   *                        bits] additional parameters may follow @a status (in this order).
   * @return Opaque client handle if successful.  If this is NULL, the
   * open operation failed, @a *status includes @ref JackFailure and the
   * caller is not a JACK client.
   * @ref JackUseExactName option, the server will modify this name to
   * create a unique variant, if needed.
   * @arg [@ref JackServerName] <em>(char *) server_name</em> selects
   * from among several possible concurrent server instances.  Server
   * names are unique to each user.  If unspecified, use "default"
   * unless \$JACK_DEFAULT_SERVER is defined in the process environment.
   */
  ClientHandle client_open(String client_name,
                           OpenOptions options,
                           Status statusContainer) {
    throw new NotYetImplementedException();
  }

  ClientHandle client_open(String client_name,
                           OpenOptions options,
                           Status statusContainer,
                           String ServerName) {
    throw new NotYetImplementedException();
  }

  // jack.h - line 128

  /**
   * Disconnects an external client from a JACK server.
   *
   * @return 0 on success, otherwise a non-zero error code
   */
  int client_close(ClientHandle client) {
    throw new NotYetImplementedException();
  }

  // jack.h - 204

  /**
   * Tell the Jack server that the program is ready to start processing
   * audio.
   *
   * @return 0 on success, otherwise a non-zero error code
   */
  int activate(ClientHandle client) {
    throw new NotYetImplementedException();
  }

  // jack.h - line 316

  /**
   * Register a function (and argument) to be called if and when the
   * JACK server shuts down the client thread.  The function must
   * be written as if it were an asynchonrous POSIX signal
   * handler --- use only async-safe functions, and remember that it
   * is executed from another thread.  A typical function might
   * set a flag or write to a pipe so that the rest of the
   * application knows that the JACK client thread has shut
   * down.
   * <p>
   * NOTE: clients do not need to call this.  It exists only
   * to help more complex clients understand what is going
   * on.  It should be called before jack_client_activate().
   * <p>
   * NOTE: if a client calls this AND jack_on_info_shutdown(), then
   * in case of a client thread shutdown, the callback
   * passed to this function will not be called, and the one passed to
   * jack_on_info_shutdown() will.
   * <p>
   * NOTE: application should typically signal another thread to correctly
   * finish cleanup, that is by calling "jack_client_close"
   * (since "jack_client_close" cannot be called directly in the context
   * of the thread that calls the shutdown callback).
   *
   * @param client            pointer to JACK client structure.
   * @param shutdown_callback The jack_shutdown function pointer.
   * @param arg               The arguments for the jack_shutdown function.
   */
  void on_shutdown(ClientHandle client,
                   ShutdownListener shutdown_callback, Object arg) {
    throw new NotYetImplementedException();

  }


  // jack.h line 377

  /**
   * Tell the Jack server to call @a process_callback whenever there is
   * work be done, passing @a arg as the second argument.
   * <p>
   * The code in the supplied function must be suitable for real-time
   * execution.  That means that it cannot call functions that might
   * block for a long time. This includes malloc, free, printf,
   * pthread_mutex_lock, sleep, wait, poll, select, pthread_join,
   * pthread_cond_wait, etc, etc. See
   * http://jackit.sourceforge.net/docs/design/design.html#SECTION00411000000000000000
   * for more information.
   * <p>
   * NOTE: this function cannot be called while the client is activated
   * (after jack_activate has been called.)
   *
   * @return 0 on success, otherwise a non-zero error code.
   */
  int set_process_callback(ClientHandle client,
                           ProcessListener process_callback,
                           Object arg) {
    throw new NotYetImplementedException();
  }

  // jack.h - line 711

  /**
   * Create a new port for the client. This is an object used for moving
   * data of any type in or out of the client.  Ports may be connected
   * in various ways.
   * <p>
   * Each port has a short name.  The port's full name contains the name
   * of the client concatenated with a colon (:) followed by its short
   * name.  The jack_port_name_size() is the maximum length of this full
   * name.  Exceeding that will cause the port registration to fail and
   * return NULL.
   * <p>
   * The __port_name__ must be unique among all ports owned by this client.
   * If the name is not unique, the registration will fail.
   * <p>
   * All ports have a type, which may be any non-NULL and non-zero
   * length string, passed as an argument.  Some port types are built
   * into the JACK API, currently only JACK_DEFAULT_AUDIO_TYPE.
   *
   * @param client      pointer to JACK client structure.
   * @param port_name   non-empty short name for the new port (not
   *                    including the leading `client_name:`). Must be unique.
   * @param port_type   port type name.  If longer than
   *                    jack_port_type_size(), only that many characters are significant.
   * @param flags       @ref JackPortFlags bit mask.
   * @param buffer_size must be non-zero if this is not a built-in @a
   *                    port_type.  Otherwise, it is ignored.
   * @return jack_port_t pointer on success, otherwise NULL.
   */
  public PortHandle port_register(ClientHandle client,
                                  String port_name,
                                  PortType port_type,
                                  Set<PortFlags> flags,
                                  long buffer_size) {
    throw new NotYetImplementedException();
  }

  private native static long _port_register(long client,
                                            String port_name,
                                            String port_type,
                                            long flags,
                                            long buffer_size);

  // jack.h - 975

  /**
   * Establish a connection between two ports.
   * <p>
   * When a connection exists, data written to the source port will
   * be available to be read at the destination port.
   * <p>
   * - The port types must be identical.
   * - The @ref JackPortFlags of the __source_port__ must include @ref
   * JackPortIsOutput.
   * - The @ref JackPortFlags of the __destination_port__ must include @ref JackPortIsInput.
   *
   * @return 0 on success, EEXIST if the connection is already made,
   * otherwise a non-zero error code
   */
  int connect(ClientHandle client,
              String source_port,
              String destination_port) {
    throw new NotYetImplementedException();
  }

  // jack.h - line 755

  /**
   * This returns a pointer to the memory area associated with the
   * specified port. For an output port, it will be a memory area
   * that can be written to; for an input port, it will be an area
   * containing the data from the port's connection(s), or
   * zero-filled. if there are multiple inbound connections, the data
   * will be mixed appropriately.
   * <p>
   * FOR OUTPUT PORTS ONLY : DEPRECATED in Jack 2.0 !!
   * ---------------------------------------------------
   * You may cache the value returned, but only between calls to
   * your "blocksize" callback. For this reason alone, you should
   * either never cache the return value or ensure you have
   * a "blocksize" callback and be sure to invalidate the cached
   * address from there.
   * <p>
   * Caching output ports is DEPRECATED in Jack 2.0, due to some new optimization (like "pipelining").
   * Port buffers have to be retrieved in each callback for proper functionning.
   */
  public Object port_get_buffer(PortHandle port, int sampleFrameCount) {
    throw new NotYetImplementedException();
  }

  /**
   * The maximum number of characters for a port's full name contains the owning client name concatenated
   * with a colon (:) followed by its short name and a NULL
   * character.
   *
   * @return the maximum number of characters in a full JACK port name
   * including the final NULL character.  This value is a constant.
   */
  int port_name_size() {
    throw new NotYetImplementedException();
  }


  // jack.h - line 1265

  /**
   * Looking up ports / PortSearching
   *
   * @param port_name_pattern A regular expression used to select
   *                          ports by name.  If NULL or of zero length, no selection based
   *                          on name will be carried out.
   * @param type_name_pattern A regular expression used to select
   *                          ports by type.  If NULL or of zero length, no selection based
   *                          on type will be carried out.
   * @param flags             A value used to select ports by their flags.
   *                          If zero, no selection based on flags will be carried out.
   * @return an array of ports that match the specified
   * arguments.  (The JNI lib is responsible for calling jack_free() any
   * non-NULL returned value.)
   * @see jack_port_name_size(), jack_port_type_size()
   */
  String[] get_ports(ClientHandle client,
                     String port_name_pattern,
                     String type_name_pattern,
                     long flags) {
    throw new NotYetImplementedException();
  }
}
