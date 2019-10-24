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

import jackAudio4Java.buffers.InternalAudioSlice;
import jackAudio4Java.buffers.MutableAudioSlice;
import jackAudio4Java.types.*;

import java.nio.FloatBuffer;
import java.util.logging.Level;
import java.util.regex.Pattern;

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
        if (!isAvailable())
          throw new RuntimeException("Jack cannot be run on this installation.");
        result = instance;
        if (result == null)
          instance = result = new Jack();
      }
    }
    return result;
  }

  private final static int NATIVE_LEVEL_TRACE = 0;
  private final static int NATIVE_LEVEL_DEBUG = 1;
  private final static int NATIVE_LEVEL_INFO = 2;
  private final static int NATIVE_LEVEL_WARN = 3;
  private final static int NATIVE_LEVEL_ERROR = 4;
  private final static int NATIVE_LEVEL_CRITICAL = 5;
  private final static int NATIVE_LEVEL_OFF = 6;

  /**
   * Translate the logging level as used by the java.util.logging (JUL)
   * library to those used be the "spdlog" library used in the native part.
   *
   * @param javaLevel the level used in java.util.logging (JUL).
   * @return the corresponding level used in the native part.
   */
  private int julToNativeLogginglevel(Level javaLevel) {
    if (javaLevel.intValue() <= Level.FINE.intValue()) return NATIVE_LEVEL_TRACE;
    if (javaLevel.intValue() <= Level.CONFIG.intValue()) return NATIVE_LEVEL_DEBUG;
    if (javaLevel.intValue() <= Level.INFO.intValue()) return NATIVE_LEVEL_INFO;
    if (javaLevel.intValue() <= Level.WARNING.intValue()) return NATIVE_LEVEL_WARN;
    if (javaLevel.intValue() <= Level.SEVERE.intValue()) return NATIVE_LEVEL_ERROR;
    return NATIVE_LEVEL_OFF;

  }

  /**
   * Check whether Jack is installed and works on this machine.
   * @return true if Jack probably works.
   */
  public static boolean isAvailable(){
    return true;
  }

  /**
   * Explain why Jack cannot be used with this box.
   * @return a String that can be used in error messages etc.
   */
  public static String reasonForUnavailability(){
    return "";
  }

  /**
   * Set the level of logging that will be used to control logging output.
   *
   * @param level the level as defined in java.util.logging.Level
   */
  public void setLoggingLevel(Level level) {
    setLoggingLevelN(julToNativeLogginglevel(level));
  }

  private native static void setLoggingLevelN(int level);


  /**
   * Get the version of the _Native Java-method_ interface.
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
   * <p>
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
   *                     Server names are unique to each user.  If unspecified (i.e. `null`),
   *                     JACK will use a default, unless
   *                     `JACK_DEFAULT_SERVER` is defined in the process environment.
   * @return an opaque client handle (if successful).  If this is `null`, the
   * open operation failed, the `returnStatus` includes  JackFailure and the
   * caller is not a JACK client.
   */
  public ClientHandle clientOpen(String clientName,
                                 OpenOption[] openOptions,
                                 OpenStatus returnStatus,
                                 String serverName) {
    int openOptionsN = OpenOption.arrayToInt(openOptions);
    Int returnStatusN = new Int();

    long hClient = clientOpenN(clientName, openOptionsN, returnStatusN, serverName);

    if (returnStatus != null) {
      returnStatus.statusBits = returnStatusN.value;
    }
    if (hClient == 0) return null;

    return new InternalClientHandle(hClient);
  }

  private native static long clientOpenN(String clientName,
                                         int openOptions,
                                         Int returnStatus,
                                         String serverName);


  // jack.h - line 128

  /**
   * Disconnects an external client from a JACK server.
   *
   * @return 0 on success, otherwise a non-zero error code
   */
  public int clientClose(ClientHandle client) {
    if (client == null) return -1;
    if (!client.isValid()) return -1;
    InternalClientHandle internalClientHandle = (InternalClientHandle) client;
    int error = clientCloseN(internalClientHandle.getReference());
    internalClientHandle.invalidate();
    return error;
  }

  private native static int clientCloseN(long clientHandle);

  // jack.h - 136

  /**
   * The maximum number of characters in a JACK client name.
   *
   * @return the maximum number of characters in a JACK client name. This value is a constant.
   */
  public int clientNameSize() {
    //remove one char accounting for the final NULL character.
    return clientNameSizeN() - 1;
  }

  private native static int clientNameSizeN();

  // jack.h - 141

  /**
   * The actual name of this client.
   * <p>
   * This is useful when {@link OpenOption#UseExactName}
   * is not specified on open and {@link OpenStatus#hasNameNotUnique()}
   * status was returned.  In that case, the actual
   * name will differ from the `clientName` requested.
   *
   * @return the actual client name.
   */
  public String getClientName(ClientHandle client) {
    if (client == null) return null;
    InternalClientHandle internalClientHandle = (InternalClientHandle) client;
    return getClientNameN(internalClientHandle.getReference());
  }

  private native static String getClientNameN(long clientHandle);


  // jack.h - 204

  /**
   * Tell the Jack server that the program is ready to start processing.
   *
   * @return 0 on success, otherwise a non-zero error code
   */
  public int activate(ClientHandle client) {
    if (client == null) return -1;
    InternalClientHandle internalClientHandle = (InternalClientHandle) client;
    return activateN(internalClientHandle.getReference());
  }

  private native static int activateN(long clientHandle);

  // jack.h - line 212

  /**
   * Tell the Jack server to remove this client from the process
   * graph.  Also, disconnect all ports belonging to it, since inactive
   * clients have no port connections.
   *
   * @return 0 on success, otherwise a non-zero error code
   */
  public int deactivate(ClientHandle client) {
    if (client == null) return -1;
    InternalClientHandle internalClientHandle = (InternalClientHandle) client;
    return deactivateN(internalClientHandle.getReference());
  }

  private native static int deactivateN(long clientHandle);

  // jack.h - line 316

  /**
   * Register a function (and optionally an argument) to be called if and when the
   * JACK server shuts down the client thread.
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
   * Tell the Jack server to call {@link ProcessListener#onProcess(int)}
   * whenever there is work be done.
   * <p>
   * (Note: The native function `jack_register_process_listener` has an additional parameter `arg`.
   * This parameter is not represented in the java interface because of the risk of race conditions).
   * <p>
   * NOTE: this function cannot be called while the client is active
   * (after {@link #activate(ClientHandle)} has been called.)
   *
   * @param client          an opaque handle representing this client.
   * @param processListener the listener that will be called for each process cycle.
   * @return 0 on success, otherwise a non-zero error code.
   */
  public int registerProcessListener(ClientHandle client,
                                     ProcessListener processListener) {
    if (client == null) return -1;
    if (!client.isValid()) return -1;
    long clientHandleN = ((InternalClientHandle) client).getReference();
    return registerProcessListenerN(clientHandleN, processListener);
  }

  private native static int registerProcessListenerN(long client, ProcessListener processListener);

  // jack.h - line 668

  /**
   * Get the sample rate of the jack system, as set by the user when
   * jackd was started.
   *
   * @return the sample rate of the jack system in samples per second.
   */
  public int getSampleRate(ClientHandle client) {

    long clientHandleN = InternalClientHandle.getReferenceFrom(client);
    if (clientHandleN == 0) return 0;
    return getSampleRateN(clientHandleN);
  }

  private native static int getSampleRateN(long client);

  // jack.h - line 711

  /**
   * Create a new port for the client.
   * <p>
   * This is an object used for moving data of any type in or out of the client.
   * Ports may be connected in various ways.
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
   * @param client     an opaque handle representing this client.
   * @param portName   non-empty _short name_ for the new port (shall not
   *                   include the leading `client_name:`). Must be unique.
   * @param portType   the port-type.  See {@link PortType}
   * @param portFlags  an array of flags see:  {@link PortFlag}.
   * @param bufferSize must be non-zero if this is not a built-in
   *                   port type (see {@link PortType}).  Otherwise, it is ignored.
   * @return a port handle on success, otherwise `null`.
   */
  public PortHandle portRegister(ClientHandle client,
                                 String portName,
                                 PortType portType,
                                 PortFlag[] portFlags,
                                 long bufferSize) {
    long portFlagsN = PortFlag.arrayToLong(portFlags);
    long clientHandleN = ((InternalClientHandle) client).getReference();

    long portHandleN = portRegisterN(clientHandleN, portName, portType.toString(), portFlagsN, bufferSize);

    if (portHandleN == 0) {
      return null;
    }
    return new InternalPortHandle(portHandleN, portName, portType, portFlags);
  }

  private native static long portRegisterN(long client,
                                           String portName,
                                           String portType,
                                           long flags,
                                           long bufferSize);
  // jack.h -line 747

  /**
   * Remove the port from the client, disconnecting any existing
   * connections.
   *
   * @param client an opaque handle representing this client.
   * @param port   an opaque handle representing the port.
   * @return 0 on success, otherwise a non-zero error code
   */
  public int portUnregister(ClientHandle client, PortHandle port) {
    if (client == null) return -1;
    if (!client.isValid()) return -1;
    if (port == null) return -1;
    if (!port.isValid()) return -1;

    long clientHandleN = ((InternalClientHandle) client).getReference();
    InternalPortHandle internalPortHandle = (InternalPortHandle) port;
    long portHandleN = internalPortHandle.getReference();


    int error = portUnregisterN(clientHandleN, portHandleN);
    internalPortHandle.invalidate();
    return error;
  }

  private native static int portUnregisterN(long client, long port);

  // jack.h -line 944

  /**
   * Switch input monitoring on or off.
   * <p>
   * If  {@link PortFlag#canMonitor}  is set for this _port_, turn input
   * monitoring on or off.  Otherwise, do nothing.
   *
   * @param portHandle an opaque handle representing a port.
   * @param on         if `true`, turn monitoring on. If `false`, turn monitoring off.
   * @return unknown
   */
  public int portRequestMonitor(PortHandle portHandle, boolean on) {
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
  public Object portGetBuffer(PortHandle port, int sampleFrameCount) {
    throw new NotYetImplementedException();
  }

  /**
   * Receive audio data from an input port.
   * <p>
   * The port must have been created as a defaultAudio port with the flag {@link PortFlag#isInput}.
   *
   * @param port           an opaque handle representing a inputPort.
   * @param inputContainer a client supplied container that will be filled with the received data.
   * @return 0 on success, otherwise a non-zero error code.
   */
  public int portGetAudioData(PortHandle port, MutableAudioSlice inputContainer) {
    if (port == null) return -1;
    if (!port.isValid()) return -1;
    if (!port.getType().equals(PortType.defaultAudio()))
      throw new RuntimeException(port.getName() + " is not an audio port");
    if (!PortFlag.arrayContains(port.getFlags(), PortFlag.isInput))
      throw new RuntimeException(port.getName() + " is not an input port");
    InternalPortHandle internalPortHandle = (InternalPortHandle) port;
    long portHandleN = internalPortHandle.getReference();

    if (inputContainer == null) return -1;
    InternalAudioSlice slice = (InternalAudioSlice) inputContainer;
    final FloatBuffer internalBuffer = slice.accessInternalBuffer();
    if (!internalBuffer.isDirect()) return -1;

    return portGetAudioDataN(portHandleN, internalBuffer);
  }

  private native static int portGetAudioDataN(long portHandleN, FloatBuffer internalBuffer);

  /**
   * Send data over an output port.
   * <p>
   * The port must have been created with the flag {@link PortFlag#isOutput}.
   *
   * @param outputPort an opaque handle representing a outputPort.
   * @param output     a container for data that shall be send over this outputPort.
   * @return 0 on success, otherwise a non-zero error code.
   */
  public int portSendAudioData(PortHandle outputPort, float[] output) {
    throw new NotYetImplementedException();
  }

  /**
   * Tentative replacement for the portGetBuffer function.
   *
   * @param port         an opaque handle representing a port.
   * @param receivedData a container for data that this has received (can be null for pure output ports).
   * @param sendingData  a container for data that shall be send over this port (can be null for pure input ports).
   * @return 0 on success, otherwise a non-zero error code.
   */
  public int portExchangeByteData(PortHandle port, byte[] receivedData, byte[] sendingData) {
    throw new NotYetImplementedException();
  }


  // jack.h - 975

  /**
   * Establish a connection between two ports.
   * <p>
   * When a connection exists, data written to the __source port__ will
   * be available to be read at the __destination port__.
   * <p>
   * - The _port-types_ must be identical.
   * - The  {@link PortFlag} of the __source_port__ must include {@link PortFlag#isOutput}.
   * - The  {@link PortFlag}  of the __destination_port__ must include {@link PortFlag#isInput}.
   *
   * @return 0 on success, EEXIST if the connection is already made,
   * otherwise a non-zero error code
   */
  public int connect(ClientHandle client,
                     String sourcePort,
                     String destinationPort) {
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
    return portNameSizeN() - 1;
  }

  private static native int portNameSizeN();

  // jack.h line 1034

  /**
   * The maximum number of characters in a JACK port type name.
   *
   * @return the maximum number of characters in a JACK port type name.
   * This value is a constant.
   */
  public int portTypeSize() {
    // subtract one to account for the final NULL character.
    return portTypeSizeN() - 1;
  }

  private static native int portTypeSizeN();

  // jack.h - line 1265

  /**
   * Look up ports - search ports.
   *
   * @param client          A valid client handle.
   * @param portNamePattern A regular expression used to select ports by name.
   *                        If `null` or of zero length, no selection based
   *                        on name will be carried out.
   * @param typeNamePattern A regular expression used to select ports by type.
   *                        If `null` or of zero length, no selection based
   *                        on type will be carried out.
   * @param portFlags       An array of  {@link PortFlag}
   *                        used to select ports by their flags (only ports that fulfill
   *                        all given flags are selected).
   *                        If `null`, no selection based on flags will be carried out.
   * @return an array of port-names that match the specified
   * arguments. If no match is found, an empty array will be returned.
   */
  public String[] getPorts(ClientHandle client,
                           String portNamePattern,
                           String typeNamePattern,
                           PortFlag[] portFlags) {
    String[] empty = new String[]{};

    long clientHandleN = InternalClientHandle.getReferenceFrom(client);
    if (clientHandleN == 0) return empty;

    verifyPattern(portNamePattern);
    verifyPattern(typeNamePattern);

    long portFlagsN = PortFlag.arrayToLong(portFlags);
    String[] result = getPortsN(clientHandleN, portNamePattern, typeNamePattern, portFlagsN);
    if (result == null) return empty;

    return result;
  }

  /**
   * The Jack-DLL might crash with a SIGSEGV (0xb) when confronted with a invalid regex pattern
   * like "*1".
   * This function verifies the given pattern and throws java.util.regex.PatternSyntaxException
   * if the pattern is invalid.
   *
   * @param regex the regex pattern to be tested.
   * @throws java.util.regex.PatternSyntaxException if the pattern is invalid.
   */
  static void verifyPattern(String regex) {
    if (regex != null) {
      final Pattern ignore = Pattern.compile(regex);
    }
  }

  private static native String[] getPortsN(long client,
                                           String portNamePattern,
                                           String typeNamePattern,
                                           long flags);
}
