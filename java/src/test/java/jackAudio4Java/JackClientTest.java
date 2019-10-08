package jackAudio4Java;

import jackAudio4Java.types.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.logging.Level;

import static com.google.common.truth.Truth.assertThat;
import static jackAudio4Java.types.PortFlag.isOutput;
import static jackAudio4Java.types.PortFlag.isPhysical;

/**
 * These unit-tests check the functions that need an open connection to the
 * the Jack - audio - server. Or in other words, checks that need valid
 * client handle.
 */
public class JackClientTest {
  private static ClientHandle client;
  private static final String clientName = "JackClientÜÄÖéè";

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
    PortHandle port = Jack.server().portRegister(client, portName, PortType.defaultAudio(), new PortFlag[]{PortFlag.isInput}, 0);
    assertThat(port).isNotNull();
    assertThat(port.isValid()).isTrue();

    int error = Jack.server().portUnregister(client, port);
    assertThat(error).isEqualTo(0);
    assertThat(port.isValid()).isFalse();
  }

  /**
   * A client can be activated and can can be deactivated.
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
   * A client shall be able to register a ProcessListener.
   */
  @Test
  public void registerAndRunProcessListener() throws InterruptedException {
    int error;
    TestProcessListener testProcessListener = new TestProcessListener();
    assertThat(testProcessListener.count).isEqualTo(0);

    // when registering a valid ProcessListener, the error-code shall be zero
    error = Jack.server().registerProcessListener(client, testProcessListener);
    assertThat(error).isEqualTo(0);

    // activate and let it run for a second
    error = Jack.server().activate(client);
    assertThat(error).isEqualTo(0);

    Thread.sleep(2000); // now run for two seconds

    error = Jack.server().deactivate(client);
    assertThat(error).isEqualTo(0);

    Thread.sleep(50); // let it cool down...

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
  }

  /**
   * There should be at least one physical output port in the driver backend.
   */
  @Test
  public void getPorts() {
    String[] portNames = Jack.server().getPorts(client, null,PortType.defaultAudio().toString(), new PortFlag[]{isPhysical, isOutput} );
    assertThat(portNames).isNotNull();
    assertThat(portNames.length).isAtLeast(1);
    for(String name: portNames){
      System.out.println("--- port-name "+name);
    }
  }

  /**
   * If searching for a port whose name match "impossible", no port should be found.
   */
  @Test
  public void getPortsImpossible() {
    String[] portNames = Jack.server().getPorts(client, "impossible",null, null );
    assertThat(portNames).isNotNull();
    assertThat(portNames.length).isEqualTo(0);
  }

  private static class TestProcessListener implements ProcessListener {
    long count = 0;

    @Override
    public int onProcess(int nframes) {
      count++;
      return 0;
    }
  }



  }