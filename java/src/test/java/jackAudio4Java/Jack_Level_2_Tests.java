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
 * the Jack - audio - server and registered ports.
 */
public class Jack_Level_2_Tests {
  private static ClientHandle client;
  private static final String clientName = "Jack_Level_2_Tests";

  private static final String inputPortName = "inputPort";
  private static final String outputPortName = "outputPort";

  private static PortHandle inputPortHandle = null;
  private static PortHandle outputPortHandle = null;

  /**
   * Initialize the tests and register a new client with the jack server.
   */
  @BeforeClass
  public static void initialize() {
    Jack.server().setLoggingLevel(Level.ALL);

    // open a client
    OpenStatus returnStatus = new OpenStatus();
    client = Jack.server().clientOpen(clientName, null, returnStatus, null);
    assertThat(client).isNotNull();
    assertThat(returnStatus.hasFailure()).isFalse();

    //open an input and an output port.
    inputPortHandle = Jack.server().portRegister(client, inputPortName, PortType.defaultAudio(), new PortFlag[]{PortFlag.isInput}, 0);
    assertThat(inputPortHandle).isNotNull();
    assertThat(inputPortHandle.isValid()).isTrue();
    
    outputPortHandle = Jack.server().portRegister(client, outputPortName, PortType.defaultAudio(), new PortFlag[]{PortFlag.isOutput}, 0);
    assertThat(outputPortHandle).isNotNull();
    assertThat(outputPortHandle.isValid()).isTrue();
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

}