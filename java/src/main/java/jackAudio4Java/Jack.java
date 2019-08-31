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

import java.util.Set;

/**
 * use it like this:
 * ```
 * Int major = new Int(-1);
 * Int minor = new Int(-1);
 * Int micro = new Int(-1);
 * Int proto = new Int(-1);
 * Jack.server().jack_get_version(major, minor, micro, proto);
 * return String.format("%d.%d.%d-%d",major.value, minor.value, micro.value, proto.value);
 * ```
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

  private static class ReadableClientHandle extends ClientHandle {
    /**
     * The native address of this client.
     */
    long reference;
  }

  /**
   * Get the version of the _Native Java-method_ interface.
   * <p>
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
   * </p>
   *
   * @return the version of the Java-Native-Interface
   * @see <a href="https://docs.oracle.com/en/java/javase/12/docs/specs/jni/functions.html#version-constants">
   * Java Native Interface Specification</a>
   */
  public int getJniVersion() {
    return getJniVersionN();
  }

  private native static int getJniVersionN();

  // jack.h - line 51

  /**
   * Get the version of the JACK-server in form of several numbers.
   *
   * @param majorRef Integer- container receiving major version of JACK.
   * @param minorRef Integer- container receiving minor version of JACK.
   * @param microRef Integer- container receiving micro version of JACK.
   * @param protoRef Integer- container receiving protocol version of JACK.
   */
  public void getJackVersion(
      Int majorRef,
      Int minorRef,
      Int microRef,
      Int protoRef) {
    getJackVersionN(majorRef, minorRef, microRef, protoRef);
  }

  private native static void getJackVersionN(
      Int majorRef,
      Int minorRef,
      Int microRef,
      Int protoRef);

  // jack.h - line 83

  /**
   * Open an external client session with a JACK server.
   *
   * With this interface, clients may choose which of several servers to connect, and control
   * whether and how to start the server automatically, if it was not
   * already running.  There is also an option for JACK to generate a
   * unique client name, when necessary.
   *
   * @param clientName   a name for this client.
   *                     The name scope is local to each server.
   *                     The name length shall be of at most that given by {@link #clientNameSize()}.
   *                     The server might modify this name to create a unique variant,
   *                     unless forbidden by the option {@link OpenOption#UseExactName}
   *                     set in the `openOptions` parameter.
   * @param openOptions  a set of options, requesting JACK how to open the client in a specific way.
   *                     (May be `null`)
   * @param returnStatus a container-object for JACK to return information
   *                     from the open operation. See {@link OpenStatus}-class.
   * @param serverName   selects from among several possible concurrent server instances.
   *                     Server names are unique to each user.  If unspecified,
   *                     JACK will use a default a "default", unless
   *                     `JACK_DEFAULT_SERVER` is defined in the process environment.
   * @return an opaque client handle (if successful).  If this is `null`, the
   * open operation failed, the `returnStatus` includes  JackFailure and the
   * caller is not a JACK client.
   */
  public ClientHandle clientOpen(String clientName,
                                 Set<OpenOption> openOptions,
                                 OpenStatus returnStatus,
                                 String serverName) {
    throw new NotYetImplementedException();
  }

  private native static long clientOpenN(String clientName,
                                         int openOptions,
                                         Int returnStatus,
                                         String serverName);

  /**
   * Open an external client session with a JACK server.
   * <p>
   * Same as {@link #clientOpen(String, Set, OpenStatus, String)}
   * except the serverName is left unspecified.
   */
  public ClientHandle clientOpen(String clientName,
                                 OpenOption options,
                                 OpenStatus returnStatus) {
    throw new NotYetImplementedException();
  }


  // jack.h - line 128

  /**
   * Disconnects an external client from a JACK server.
   *
   * @return 0 on success, otherwise a non-zero error code
   */
  public int clientClose(ClientHandle client) {
    throw new NotYetImplementedException();
  }

  // jack.h - 136

  /**
   * The maximum number of characters in a JACK client name.
   *
   * @return the maximum number of characters in a JACK client name. This value is a constant.
   */
  public int clientNameSize() {
    //remove one char accounting for the final NULL character.
    throw new NotYetImplementedException();
  }

  // jack.h - 141

  /**
   * The actual name of this client.
   *
   * @return the actual client name.  This is useful when {@link OpenOption#UseExactName}
   * is not specified on open and {@link OpenStatus#hasNameNotUnique()}
   * status was returned.  In that case, the actual
   * name will differ from the `clientName` requested.
   */
  public String getClientName(ClientHandle clientHandle) {
    //remove one char accounting for the final NULL character.
    throw new NotYetImplementedException();
  }

  // jack.h - 204

  /**
   * Tell the Jack server that the program is ready to start processing
   * audio.
   *
   * @return 0 on success, otherwise a non-zero error code
   */
  public int activate(ClientHandle client) {
    throw new NotYetImplementedException();
  }

  // jack.h - line 212

  /**
   * Tell the Jack server to remove this client from the process
   * graph.  Also, disconnect all ports belonging to it, since inactive
   * clients have no port connections.
   *
   * @return 0 on success, otherwise a non-zero error code
   */
  public int deactivate(ClientHandle client) {
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
   * on.  It should be called before {@link #activate(ClientHandle)} .
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
   * @param client           an opaque handle representing this client.
   * @param shutdownListener the listener that will be called on shutdown.
   * @param arg              an arbitrary object that will handed to the shutdownListener on shutdown.
   */
  public void registerShutdownListener(ClientHandle client,
                                       ShutdownListener shutdownListener, Object arg) {
    throw new NotYetImplementedException();

  }


  // jack.h line 377

  /**
   * Tell the Jack server to call {@link ProcessListener#process(int, Object)}
   * whenever there is work be done.
   * <p>
   * The parameter {@code arg } will be passed
   * as second argument (mind the risk of race conditions by making {@code arg } immutable).
   * <p>
   * <p>
   * NOTE: this function cannot be called while the client is activated
   * (after {@link #activate(ClientHandle)} has been called.)
   *
   * @param client          an opaque handle representing this client.
   * @param processListener the listener that will be called for each process cycle.
   * @param arg             an arbitrary (preferably immutable ) object
   *                        that will handed to the processListener on each process cycle.
   * @return 0 on success, otherwise a non-zero error code.
   */
  public int registerProcessListener(ClientHandle client,
                                     ProcessListener processListener,
                                     Object arg) {
    throw new NotYetImplementedException();
  }

  // jack.h - line 711

  /**
   * Create a new port for the client. This is an object used for moving
   * data of any type in or out of the client.  Ports may be connected
   * in various ways.
   * <p>
   * Each port has a _short name_.  The port's _full name_ contains the name
   * of the client concatenated with a colon (:) followed by its _short name_.
   * The {@link #portNameSize()} is the maximum length of this _full name_.
   * Exceeding that will cause the port registration to fail and return `null`.
   * <p>
   * The `portName` must be unique among all ports owned by this client.
   * If the name is not unique, the registration will fail.
   * <p>
   * All ports have a _type_, defined by the parameter `portType`.
   *
   * @param client     pointer to JACK client structure.
   * @param portName   non-empty _short name_ for the new port (not
   *                   including the leading `client_name:`). Must be unique.
   * @param portType   the port-type.  See {@link PortType}
   * @param flags      a set of {@link PortFlags}.
   * @param bufferSize must be non-zero if this is not a built-in
   *                   port type (see {@link PortType}).  Otherwise, it is ignored.
   * @return a port handle on success, otherwise `null`.
   */
  public PortHandle registerPort(ClientHandle client,
                                 String portName,
                                 PortType portType,
                                 Set<PortFlags> flags,
                                 long bufferSize) {
    throw new NotYetImplementedException();
  }

  private native static long registerPortN(long client,
                                           String portName,
                                           String portType,
                                           long flags,
                                           long bufferSize);

  // jack.h - 975

  /**
   * Establish a connection between two ports.
   * <p>
   * When a connection exists, data written to the _source port_ will
   * be available to be read at the _destination port_.
   * <p>
   * - The port types must be identical.
   * - The @ref JackPortFlags of the __source_port__ must include @ref
   * JackPortIsOutput.
   * - The @ref JackPortFlags of the __destination_port__ must include @ref JackPortIsInput.
   *
   * @return 0 on success, EEXIST if the connection is already made,
   * otherwise a non-zero error code
   */
  public int connect(ClientHandle client,
                     String sourcePort,
                     String destinationPort) {
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
  // jack.h - line 1024

  /**
   * The maximum number of characters for a port's full name contains the owning client name concatenated
   * with a colon (`:`) followed by its short name.
   *
   * @return the maximum number of characters in a full JACK port name.
   * This value is a constant.
   */
  public int portNameSize() {
    // subtract one to account for the final NULL character.
    throw new NotYetImplementedException();
  }

  private static native int portNameSizeN();

  // jack.h line 1034

  /**
   * The maximum number of characters in a JACK port type name.
   *
   * @return the maximum number of characters in a JACK port type name
   * including the final NULL character.  This value is a constant.
   */
  public int portTypeSize() {
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
   * @see #portNameSize ,
   * @see #portTypeSize
   */
  public String[] get_ports(ClientHandle client,
                            String port_name_pattern,
                            String type_name_pattern,
                            long flags) {
    throw new NotYetImplementedException();
  }
}
