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
 * The Level 2 unit-tests regroup all the tests that need an open connection to the
 * the Jack - audio - server and live ports.
 */
public class Jack_Level_2_Tests {
  private static ClientHandle client;
  private static final String clientName = "Jack_Level_2_Tests";

  private static final String inputPortName = "inputPort";
  private static final String outputPortName = "outputPort";

  private static final String inputPortFullName = clientName + ":" + inputPortName;
  private static final String outputPortFullName = clientName + ":" + outputPortName;

  private static PortHandle inputPortHandle = null;
  private static PortHandle outputPortHandle = null;

  /**
   * Initialize the tests:
   * 1. Register  a new client with the jack server.
   * 2. Create some own ports.
   * 3. Start the callback cycles.
   * Now we are ready for the tests.
   */
  @BeforeClass
  public static void initialize() {
    int error;

    Jack.server().setLoggingLevel(Level.ALL);

    // open a client
    OpenStatus returnStatus = new OpenStatus();
    client = Jack.server().clientOpen(clientName, null, returnStatus, null);
    assertThat(client).isNotNull();
    assertThat(returnStatus.hasFailure()).isFalse();

    //open a new input and a new output port.
    inputPortHandle = Jack.server().portRegister(client, inputPortName, defaultAudio(), setOf(isInput), 0);
    assertThat(inputPortHandle).isNotNull();
    assertThat(inputPortHandle.isValid()).isTrue();

    outputPortHandle = Jack.server().portRegister(client, outputPortName, defaultAudio(), setOf(isOutput), 0);
    assertThat(outputPortHandle).isNotNull();
    assertThat(outputPortHandle.isValid()).isTrue();

    // Start the  process() callbacks.
    TestProcessListener testProcessListener = new TestProcessListener();
    error = Jack.server().registerProcessListener(client, testProcessListener);
    assertThat(error).isEqualTo(0);
    error = Jack.server().activate(client);
    assertThat(error).isEqualTo(0);
    // Now we are ready to roll.
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
   * The function `Jack.portName` shall return the full name of the port.
   */
  @Test
  public void getPortName() {
    assertThat(Jack.server().portName(inputPortHandle)).isEqualTo(inputPortFullName);
    assertThat(Jack.server().portName(outputPortHandle)).isEqualTo(outputPortFullName);
  }

  /**
   * The function `Jack.portShortName` shall return the short name of the port.
   */
  @Test
  public void getPortShortName() {
    assertThat(Jack.server().portShortName(inputPortHandle)).isEqualTo(inputPortName);
    assertThat(Jack.server().portShortName(outputPortHandle)).isEqualTo(outputPortName);
  }

  /**
   * The function `Jack.portByName` shall return a valid handle for an existing port.
   */
  @Test
  public void getPortByName() {
    PortHandle handle = Jack.server().portByName(client, inputPortFullName);
    assertThat(handle).isEqualTo(inputPortHandle);
  }
  /**
   * The function `Jack.portByName` shall return an invalid handle for a nonexistent port.
   */
  @Test
  public void getPortByInvalidName() {
    PortHandle handle = Jack.server().portByName(client, "nonexistent-port");
    assertThat(handle.isValid()).isFalse();
  }
  /**
   * It shall be possible to connect a systems capture-port to the clients input-port created in above initialization.
   */
  @Test
  public void connectPort() throws InterruptedException {
    int error = Jack.server().connect(client, outputPortFullName, inputPortFullName);
    assertThat(error).isEqualTo(0);
    // Thread.sleep(10000); // uncomment, if you want to see the connection in QjackCtl
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