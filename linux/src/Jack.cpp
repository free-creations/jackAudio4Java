/*
 * File: server.cpp
 *
 *
 * Copyright 2019 Harald Postner <Harald at free_creations.de>.
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

/**
 * This unit implements the native methods of the Java class: `jackAudio4Java.Jack`
 *
 */
#include <jni.h>
#include <thread>
#include <jack/jack.h>
#include "Jack.h"


#ifndef JNI_VERSION_1_2
#error "Needs Java version 1.2 or higher.\n"
#endif
using namespace std;


#include "spdlog/spdlog.h"

/**
 * Set the level of logging that will be used to control logging output.
 * @param level the level as defined in src/3rdparty/spdlog/common.h
 */
JNIEXPORT void JNICALL Java_jackAudio4Java_Jack_setLoggingLevelN
        (JNIEnv *, jclass, jint level) {
    SPDLOG_TRACE("Java_jackAudio4Java_Jack_setLoggingLevelN");
    switch (level) {
        case jackAudio4Java_Jack_NATIVE_LEVEL_TRACE:
            spdlog::set_level(spdlog::level::trace);
            SPDLOG_INFO("Native Log-level now set to \"trace\"");
            break;
        case jackAudio4Java_Jack_NATIVE_LEVEL_DEBUG:
            spdlog::set_level(spdlog::level::debug);
            SPDLOG_INFO("Native Log-level now set to \"debug\"");
            break;
        case jackAudio4Java_Jack_NATIVE_LEVEL_INFO:
            spdlog::set_level(spdlog::level::info);
            SPDLOG_INFO("Native Log-level now set to \"info\"");
            break;
        case jackAudio4Java_Jack_NATIVE_LEVEL_WARN:
            SPDLOG_INFO("Native Log-level now set to \"warn\"");
            spdlog::set_level(spdlog::level::warn);
            break;
        case jackAudio4Java_Jack_NATIVE_LEVEL_ERROR:
            SPDLOG_INFO("Native Log-level now set to \"err\"");
            spdlog::set_level(spdlog::level::err);
            break;
        case jackAudio4Java_Jack_NATIVE_LEVEL_CRITICAL:
            SPDLOG_INFO("Native Log-level now set to \"critical\"");
            spdlog::set_level(spdlog::level::critical);
            break;
        case jackAudio4Java_Jack_NATIVE_LEVEL_OFF:
            SPDLOG_INFO("Native Log-level now set to \"off\"");
            spdlog::set_level(spdlog::level::off);
            break;
        default:
            SPDLOG_INFO("Native Log-level now set to \"trace\"");
            spdlog::set_level(spdlog::level::trace);

    }
}


/**
 * Function `private native static void _jack_get_version(Int majorRef, Int minorRef, Int microRef,Int protoRef);`
 *
 * Call this function to get the version of the JACK-server in form of several numbers.
 *
 * @param majorRef Integer- container receiving major version of JACK.
 * @param minorRef Integer- container receiving minor version of JACK.
 * @param microRef Integer- container receiving micro version of JACK.
 * @param protoRef Integer- container receiving protocol version of JACK.
 */
JNIEXPORT void JNICALL Java_jackAudio4Java_Jack_getJackVersionN
        (JNIEnv *env, jclass, jobject majorRef, jobject minorRef, jobject microRef, jobject protoRef) {

    SPDLOG_TRACE("Java_jackAudio4Java_Jack_getJackVersionN");

    int majorVal = -1;
    int minorVal = -1;
    int microVal = -1;
    int protoVal = -1;

    jack_get_version(&majorVal, &minorVal, &microVal, &protoVal);

    if (majorRef) types::Int::setValue(env, (types::jIntObject) majorRef, majorVal);
    if (minorRef) types::Int::setValue(env, (types::jIntObject) minorRef, minorVal);
    if (microRef) types::Int::setValue(env, (types::jIntObject) microRef, microVal);
    if (protoRef) types::Int::setValue(env, (types::jIntObject) protoRef, protoVal);
}

/**
 * Call this function to get the version of the native Java-method-interface.
 * <p>
 *   The major version number is in the higher 16 bits and the minor version number is in the lower 16 bits.
 *   At the time of writing, the following constants were defined:
 * </p>
 * <p><ul>
 * <li> JNI_VERSION_1_1 0x00010001
 * <li> JNI_VERSION_1_2 0x00010002
 * <li> JNI_VERSION_1_4 0x00010004
 * <li> JNI_VERSION_1_6 0x00010006
 * <li> JNI_VERSION_1_8 0x00010008
 * <li> JNI_VERSION_9   0x00090000
 * <li> JNI_VERSION_10  0x000a0000
 * </ul></p>
 * @see <a href="https://docs.oracle.com/en/java/javase/12/docs/specs/jni/functions.html#version-constants">
 *   Java Native Interface Specification</a>
 * @return the version of the Java-Native-Interface
 */
JNIEXPORT jint JNICALL Java_jackAudio4Java_Jack_getJniVersionN
        (JNIEnv *env, jclass) {
    SPDLOG_TRACE("Java_jackAudio4Java_Jack_getJniVersionN");
    return env->GetVersion();
}

/**
 * The maximum number of characters for a port's full name contains the owning client name concatenated
 * with a colon (`:`) followed by its short name.
 *
 * @return the maximum number of characters in a full JACK port name.
 * This value is a constant.
 */
JNIEXPORT jint JNICALL Java_jackAudio4Java_Jack_portNameSizeN
        (JNIEnv *, jclass) {
    SPDLOG_TRACE("Java_jackAudio4Java_Jack_portNameSizeN");
    return jack_port_name_size();
}

/**
 * The maximum number of characters in a JACK port type name.
 *
 * @return the maximum number of characters in a JACK port type name.
 * This value is a constant.
 */
JNIEXPORT jint JNICALL Java_jackAudio4Java_Jack_portTypeSizeN
        (JNIEnv *, jclass) {
    SPDLOG_TRACE("Java_jackAudio4Java_Jack_portTypeSizeN");
    return jack_port_type_size();
}

/**
 * The maximum number of characters in a JACK client name.
 *
 * @return the maximum number of characters in a JACK client name. This value is a constant.
 */
JNIEXPORT jint JNICALL Java_jackAudio4Java_Jack_clientNameSizeN
        (JNIEnv *, jclass) {
    SPDLOG_TRACE("Java_jackAudio4Java_Jack_clientNameSizeN");
    return jack_client_name_size();
}

/**
 * Disconnects an external client from a JACK server.
 *
 * @return 0 on success, otherwise a non-zero error code
 */
JNIEXPORT jint JNICALL Java_jackAudio4Java_Jack_clientCloseN
        (JNIEnv *, jclass, jlong clientHandle) {
    SPDLOG_TRACE("Java_jackAudio4Java_Jack_clientCloseN");
    return jack_client_close((jack_client_t *) clientHandle);
}

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
 * @param returnStatus an INT container-object for JACK to return information
 *                     from the open operation. See {@link OpenStatus}-class.
 * @param serverName   selects from among several possible concurrent server instances.
 *                     Server names are unique to each user.  If unspecified (i.e. `null`),
 *                     JACK will use a default, unless
 *                     `JACK_DEFAULT_SERVER` is defined in the process environment.
 * @return an opaque client handle (if successful).  If this is `null`, the
 * open operation failed, the `returnStatus` includes  JackFailure and the
 * caller is not a JACK client.
 */
JNIEXPORT jlong JNICALL Java_jackAudio4Java_Jack_clientOpenN
        (JNIEnv *env, jclass, jstring clientName, jint openOptions, jobject returnStatus, jstring serverName) {

    SPDLOG_TRACE("Java_jackAudio4Java_Jack_clientOpenN");

    // transform clientName from Java-string to UTF-8 Native string.
    const char *clientNameN = nullptr;
    if (clientName) clientNameN = env->GetStringUTFChars(clientName, nullptr);

    // cast openOptions from "jint" to "jack_options_t" type.
    auto openOptionsN = (jack_options_t) openOptions;

    // transform serverName from Java-string to UTF-8 Native string.
    const char *serverNameN = nullptr;
    if (serverName)serverNameN = env->GetStringUTFChars(serverName, nullptr);

    // allocate space where Jack will write the return status.
    jack_status_t returnStatusN;

    // and here we go...
    auto clientHandle = jack_client_open(clientNameN, openOptionsN, &returnStatusN, serverNameN);

    // free resources
    if (serverNameN) env->ReleaseStringUTFChars(serverName, serverNameN);
    if (clientNameN) env->ReleaseStringUTFChars(clientName, clientNameN);

    // push the return status into the given container
    if (returnStatus) types::Int::setValue(env, (types::jIntObject) returnStatus, returnStatusN);

    return (jlong) clientHandle;
}

/**
  * The actual name of this client.
  *
  * This is useful when {@link OpenOption#UseExactName}
  * is not specified on open and {@link OpenStatus#hasNameNotUnique()}
  * status was returned.  In that case, the actual
  * name will differ from the `clientName` requested.
  *
  * @return the actual client name.
  */
JNIEXPORT jstring JNICALL Java_jackAudio4Java_Jack_getClientNameN
        (JNIEnv *env, jclass, jlong clientHandle) {
    SPDLOG_TRACE("Java_jackAudio4Java_Jack_getClientNameN");

    char *clientName = jack_get_client_name((jack_client_t *) clientHandle);
    return env->NewStringUTF(clientName);
}


/**
 * Create a new port for the client.
 *
 * This is an object used for moving data of any type in or out of the client.
 * Ports may be connected in various ways.
 *
 * Each port has a _short name_.  The port's _full name_ contains the name
 * of the client concatenated with a colon (:) followed by its _short name_.
 * The {@link #portNameSize()} is the maximum length of this _full name_.
 * Exceeding that will cause the port registration to fail and return `null`.
 *
 * The `portName` must be unique among all ports owned by this client.
 * If the name is not unique, the registration will fail.
 *
 * All ports have a _type_, defined by the parameter `portType`.
 *
 * Class:     jackAudio4Java_Jack
 * Method:    portRegisterN
 * Signature: (JLjava/lang/String;Ljava/lang/String;JJ)J
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
JNIEXPORT jlong JNICALL Java_jackAudio4Java_Jack_portRegisterN
        (JNIEnv * env, jclass, jlong client, jstring portName, jstring portType, jlong portFlags, jlong bufferSize) {
    SPDLOG_TRACE("Java_jackAudio4Java_Jack_portRegisterN");

    // transform portName and portType from Java-string to UTF-8 Native string.
    const char *portNameN = nullptr;
    if (portName) portNameN = env->GetStringUTFChars(portName, nullptr);
    const char *portTypeN = nullptr;
    if (portType) portTypeN = env->GetStringUTFChars(portType, nullptr);

    // and here we go...
    auto portHandle = jack_port_register(reinterpret_cast<jack_client_t *>(client), portNameN, portTypeN, portFlags, bufferSize);

    return reinterpret_cast<jlong> (portHandle);
}

/**
 * Remove the port from the client, disconnecting any existing
 * connections.
 *
 * Class:     jackAudio4Java_Jack
 * Method:    portUnregisterN
 * Signature: (JJ)I
 * 
 * @param client an opaque handle representing this client.
 * @param port an opaque handle representing the port.
 * @return 0 on success, otherwise a non-zero error code
 */
JNIEXPORT jint JNICALL Java_jackAudio4Java_Jack_portUnregisterN
        (JNIEnv *, jclass, jlong client, jlong port) {
    SPDLOG_TRACE("Java_jackAudio4Java_Jack_portUnregisterN");
    return jack_port_unregister(reinterpret_cast<jack_client_t *> (client), reinterpret_cast<jack_port_t *> (port));
}


/**
 * Pointer to the java `onProcess` callback.
 * It is allocated in procedure
 * `Java_jackAudio4Java_Jack_registerProcessListenerN`.
 */
static jmethodID  processListener_onProcess = nullptr;
/**
 * Pointer to the java process listener object.
 * It is allocated in procedure
 * `Java_jackAudio4Java_Jack_registerProcessListenerN`.
 */
static jobject  processListener = nullptr;
/**
 * Pointer to the java virtual machine. It is allocated in procedure
 * `Java_jackAudio4Java_Jack_registerProcessListenerN`.
 */
static JavaVM * jvm = nullptr;
/**
 * Pointer to the java-Environment belonging
 * to the JACK callback thread.
 * It is cached in the first call of procedure
 * `getJNIEnvForThread`.
 */
static JNIEnv * jackCallbackJNIEnv = nullptr;
/**
 * Identity of the thread executing the JACK callbacks.
 * It is determined in the first call of procedure
 * `getJNIEnvForThread`.
 */
static thread::id jackCallbackThreadId;

/**
 * Attaching a thread to the Java virtual machine is very costly.
 *
 * Doing this in each audio cycle would cause XRuns even with 256 Frames/Period.
 * Therefore we cache the JNIEnv on the first invocation of processCallback.
 *
 * @return the JNI-Environment pointer for the current thread.
 */
JNIEnv * getJNIEnvForCallbackThread(){

    if (this_thread::get_id() == jackCallbackThreadId){
        return jackCallbackJNIEnv;
    }
    SPDLOG_TRACE("getJNIEnvForCallbackThread: new thread.");
    if(jvm == nullptr){
        SPDLOG_ERROR("jvm is NULL");
        return nullptr;
    }
    JNIEnv * env;
    jint success = jvm->AttachCurrentThread((void**) &env, NULL);
    if(success != JNI_OK){
        SPDLOG_ERROR("Could not attach to the current thread");
        return nullptr;
    }
    jackCallbackJNIEnv = env;
    jackCallbackThreadId = this_thread::get_id();
    return jackCallbackJNIEnv;
}

/**
  * The process callback for this JACK application is called in a
  * special realtime thread once for each audio cycle.
  * The native callback delegates to the "onProcess" method
  * defined in the java "ProcessListener" object.
  * @param nframes number of frames to process
  * @return zero on success, non-zero on error
  */
int processCallback(jack_nframes_t nframes, void *) {

    if (processListener_onProcess == nullptr){
        // java callback routine not set...
        return -1;
    }

    JNIEnv * env = getJNIEnvForCallbackThread();
    jint result;
    if(env) {
        // here we go... now we call the Java implementation of the processCallback.
        result = env->CallIntMethod(processListener, processListener_onProcess, nframes);
    }else{
        result = -1;
        SPDLOG_ERROR("Could not attach to the current thread");
    }

    return result;
}


/**
 * Tell the Jack server to call {@link ProcessListener#onProcess(int)}
 * whenever there is work be done.
 * <p>
 * <p>
 * NOTE: this function cannot be called while the client is active
 * (after {@link #activate(ClientHandle)} has been called.)
 *
 * @param client          an opaque handle representing this client.
 * @param processListener the listener that will be called for each process cycle.
 * @return 0 on success, otherwise a non-zero error code.
 *
 * Class:     jackAudio4Java_Jack
 * Method:    registerProcessListenerN
 * Signature: (JLjackAudio4Java/types/ProcessListener;)I
 */
JNIEXPORT jint JNICALL Java_jackAudio4Java_Jack_registerProcessListenerN
        (JNIEnv * env, jclass, jlong client, jobject newListener){
    SPDLOG_TRACE("Java_jackAudio4Java_Jack_registerProcessListenerN");
    if (newListener == nullptr) {
        SPDLOG_WARN("process callbacks now switched off.");
        processListener_onProcess = nullptr;
        return -1;
    }

    // cache a pointer to the Java machine, for use in the "getJNIEnvForCallbackThread" routine.
    env->GetJavaVM(&jvm);

    // pin the process Listener Object, so it will not be garbage collected.
    processListener = env->NewGlobalRef(newListener);

    // cache the method identifier, for use in the "getJNIEnvForCallbackThread" routine.
    jclass clazz = env->GetObjectClass(processListener);
    processListener_onProcess = env->GetMethodID(clazz, "onProcess", "(I)I" );
    if(processListener_onProcess == nullptr){
        SPDLOG_ERROR("Could not register the Process Listener.");
        return -1;
    }
    return jack_set_process_callback(reinterpret_cast<jack_client_t *>(client), processCallback, nullptr);

}

/**
 * Tell the Jack server that the program is ready to start processing.
 *
 * @return 0 on success, otherwise a non-zero error code
 *
 * Class:     jackAudio4Java_Jack
 * Method:    activateN
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_jackAudio4Java_Jack_activateN
        (JNIEnv *, jclass, jlong client){
    SPDLOG_TRACE("Java_jackAudio4Java_Jack_activateN");
    return jack_activate(reinterpret_cast<jack_client_t *>(client));
}

/**
 * Tell the Jack server to remove this client from the process
 * graph.  Also, disconnect all ports belonging to it, since inactive
 * clients have no port connections.
 *
 * @return 0 on success, otherwise a non-zero error code
 *
 * Class:     jackAudio4Java_Jack
 * Method:    deactivateN
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_jackAudio4Java_Jack_deactivateN
        (JNIEnv *, jclass, jlong client){
    SPDLOG_TRACE("Java_jackAudio4Java_Jack_deactivateN");
    return jack_deactivate(reinterpret_cast<jack_client_t *>(client));
}