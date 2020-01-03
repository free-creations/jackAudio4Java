package jackAudio4Java;

import jackAudio4Java.types.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.logging.Level;

import static com.google.common.truth.Truth.assertThat;
import static jackAudio4Java.types.PortFlag.*;
import static jackAudio4Java.types.PortType.defaultAudio;

/**
 * These unit-tests check the functions that need an open connection to the
 * the Jack - audio - server. Or, in other words, these are verifications that rely on the existence of a _valid
 * client handle_.
 */
public class Jack_Level_1_Tests {
  private static ClientHandle client;
  private static final String clientName = "JackClientÜÄÖéè"; // use a name with diacritics

  /**
   * Initialize the tests and register a new client with the jack server.
   */
  @BeforeClass
  public static void initialize() {
    Jack.server().setLoggingLevel(Level.ALL);

    OpenStatus returnStatus = new OpenStatus();
    client = Jack.server().clientOpen(clientName, null, returnStatus, null);
    assertThat(client).isNotNull();

    assertThat(returnStatus.hasFailure()).isFalse();
  }

  /**
   * Shut down the tests and close the client.
   */
  @AfterClass
  public static void shutdown() {
    int error = Jack.server().clientClose(client);
    assertThat(error).isEqualTo(0);
  }


  /**
   * The function `Jack.getClientName` shall return the name given in above initialize() method.
   */
  @Test
  public void getClientName() {
    assertThat(Jack.server().getClientName(client)).isEqualTo(clientName);
  }

  /**
   * A client shall be able to register and unregister a port.
   */
  @Test
  public void portRegisterUnregister() {
    final String portName = "testPort";
    PortHandle port = Jack.server().portRegister(client, portName, defaultAudio(), setOf(isInput), 0);
    assertThat(port).isNotNull();
    assertThat(port.isValid()).isTrue();

    int error = Jack.server().portUnregister(client, port);
    assertThat(error).isEqualTo(0);
    assertThat(port.isValid()).isFalse();
  }

  /**
   * When trying to use an invalid port handle, the system should react reasonably.
   * We'll demonstrate that, by using a port-handle of a defunct port.
   */
  @Test
  public void accessInvalidPorts() {
    final String portName = "testPort_2";
    // create a new handle
    PortHandle port = Jack.server().portRegister(client, portName, defaultAudio(), setOf(isInput), 0);
    // kill the port
    Jack.server().portUnregister(client, port);
    // try to do something with the handle pointing to the now non-existent port
    assertThat(Jack.server().portShortName(port)).isEqualTo("invalid-port");
  }

  /**
   * When trying to use an invalid port handle, the system should react reasonably.
   * We'll evaluate that, by using a port-handle of a defunct port.
   */
  @Test
  public void accessInvalidPorts_2() {
    final String portName = "testPort_3";
    // create a first port handle
    PortHandle handle_1 =  Jack.server().portRegister(client, portName, defaultAudio(), setOf(isInput), 0);
    // create a second handle pointing to the same port
    PortHandle handle_2 = Jack.server().portByName(client, clientName+":"+portName);
    // kill the port using handle_1
    Jack.server().portUnregister(client, handle_1);
    // now handle_1 is invalid but unfortunately handle_2 is not.
    assertThat(handle_1.isValid()).isFalse();
    assertThat(handle_2.isValid()).isTrue();

    // try to do something with the handle_2 pointing to the now non-existent port
    assertThat(Jack.server().portShortName(handle_2)).isEqualTo("testPort_3");
  }
  /**
   * A client can be activated and can be deactivated.
   */
  @Test
  public void activateDeactivate() {
    int error;
    error = Jack.server().activate(client);
    assertThat(error).isEqualTo(0);

    error = Jack.server().deactivate(client);
    assertThat(error).isEqualTo(0);

  }

  /**
   * A client shall be able to register and activate a __ProcessListener__.
   */
  @Test
  public void registerAndRunProcessListener() throws InterruptedException {
    int error;
    TestProcessListener testProcessListener = new TestProcessListener();
    assertThat(testProcessListener.count).isEqualTo(0);

    // when registering a valid ProcessListener, the error-code shall be zero
    error = Jack.server().registerProcessListener(client, testProcessListener);
    assertThat(error).isEqualTo(0);

    // activate and let it run for some seconds
    error = Jack.server().activate(client);
    assertThat(error).isEqualTo(0);

    Thread.sleep(2000); // now run for two seconds

    error = Jack.server().deactivate(client);
    assertThat(error).isEqualTo(0);

    Thread.sleep(50); // let it cool down... (remember, our test bed is not really thread save)

    // verify, that the process Listener has been called several times during the activation.
    assertThat(testProcessListener.count).isGreaterThan(10);

  }



  /**
   * The sample rate for a client should be within a plausible range of
   * 4 to 96 thousand samples per second.
   */
  @Test
  public void getSampleRate() {
    int sampleRate = Jack.server().getSampleRate(client);
    assertThat(sampleRate).isAtLeast(4000);
    assertThat(sampleRate).isAtMost(96000);
    System.out.println("--- sample rate " + sampleRate);
  }

  /**
   * There should be at least one physical output port in the driver backend.
   */
  @Test
  public void getPorts() {
    String[] portNames =
            Jack.server().getPorts(client, null, defaultAudio().toString(),
                    PortFlag.setOf(isPhysical, isOutput));
    assertThat(portNames).isNotNull();
    assertThat(portNames.length).isAtLeast(1);
    for (String name : portNames) {
      System.out.println("--- port-name " + name);
    }
  }

  /**
   * If searching for a port whose name match "impossible", no port should be found.
   */
  @Test
  public void getPortsImpossible() {
    String[] portNames = Jack.server().getPorts(client, "impossible", null, PortFlag.emptySet());
    assertThat(portNames).isNotNull();
    assertThat(portNames.length).isEqualTo(0);
  }

  /**
   * A ProcessListener that simply counts the number of times,
   * the `onProcess` function has been called.
   */
  private static class TestProcessListener implements ProcessListener {
    /**
     * The counter.
     * <p>
     * Please note, that for the sake of simplicity, no provision have been made to ensure
     * thread safety.
     */
    long count = 0;

    @Override
    public int onProcess(int nframes) {
      count++;
      return 0;
    }
  }


}