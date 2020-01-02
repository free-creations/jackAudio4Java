package jackAudio4Java;

import jackAudio4Java.types.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.logging.Level;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import static jackAudio4Java.types.PortFlag.*;

/**
 * These unit-tests check the functions that need an open connection to the
 * the Jack - audio - server and registered ports.
 */
public class Jack_Level_2_Tests {
  private static ClientHandle client;
  private static final String clientName = "Jack_Level_2_Tests";

  private static final String inputPortName = "inputPort";
  private static final String outputPortName = "outputPort";

  private static String[] capturePorts; // the names of physical outputs, we might try to connect.

  private static PortHandle inputPortHandle = null;
  private static PortHandle outputPortHandle = null;

  /**
   * Initialize the tests
   * 1. register  a new client with the jack server.
   * 2. create some own ports
   * 3. query for
   *
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
    inputPortHandle = Jack.server().portRegister(client, inputPortName, PortType.defaultAudio(), PortFlag.setOf(isInput), 0);
    assertThat(inputPortHandle).isNotNull();
    assertThat(inputPortHandle.isValid()).isTrue();
    
    outputPortHandle = Jack.server().portRegister(client, outputPortName, PortType.defaultAudio(), PortFlag.setOf(isOutput), 0);
    assertThat(outputPortHandle).isNotNull();
    assertThat(outputPortHandle.isValid()).isTrue();

    // query for physical output ports
    capturePorts = Jack.server().getPorts(client,null, null, PortFlag.setOf(isPhysical ,isOutput));
    assertWithMessage("No audio Outputs available to test connections.").that(capturePorts).isNotEmpty();

    // Tell the JACK server that we are ready to roll.  Our
    //  process() callback will start running now.
    TestProcessListener testProcessListener = new TestProcessListener();
    error = Jack.server().registerProcessListener(client, testProcessListener);
    assertThat(error).isEqualTo(0);
    error = Jack.server().activate(client);
    assertThat(error).isEqualTo(0);
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
    assertThat(Jack.server().portName(inputPortHandle)).isEqualTo(clientName+":"+inputPortName);
    assertThat(Jack.server().portName(outputPortHandle)).isEqualTo(clientName+":"+outputPortName);
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