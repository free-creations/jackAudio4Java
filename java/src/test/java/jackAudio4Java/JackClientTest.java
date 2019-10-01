package jackAudio4Java;

import jackAudio4Java.types.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import static com.google.common.truth.Truth.assertThat;

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


}