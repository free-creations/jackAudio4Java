package jackAudio4Java;

import jackAudio4Java.types.*;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.logging.Level;

import static com.google.common.truth.Truth.assertThat;

/**
 * These unit-tests check whatever can be verified without having an open connection
 * to the Jack - audio - server.
 */
public class JackBasicTest {

  @BeforeClass
  public static void initialize() {
    Jack.server().setLoggingLevel(Level.ALL);
  }

  /**
   * The function {@link Jack#getJackVersion(Int, Int, Int, Int)}
   * shall return the version of the jack library.
   * Note: the function `jack_get_version` of jack2 installed with
   * Ubuntu Xenial is broken. It always returns 0,0,0.
   */
  @Test
  public void getJackVersion() {
    Int major_ptr = new Int(-1);
    Int minor_ptr = new Int(-1);
    Int micro_ptr = new Int(-1);
    Int proto_ptr = new Int(-1);

    Jack.server().getJackVersion(major_ptr, minor_ptr, micro_ptr, proto_ptr);

    assertThat(major_ptr.value).isAtLeast(0);
    assertThat(minor_ptr.value).isAtLeast(0);
    assertThat(micro_ptr.value).isAtLeast(0);
    assertThat(proto_ptr.value).isAtLeast(0);
  }

  /**
   * The function {@link Jack#getJackVersion(Int, Int, Int, Int)}
   * shall not crash if - instead of an Int container -
   * a `null` pointer is given.
   */
  @Test
  public void getJackVersion_null_ptr() {
    Int major_ptr = new Int(-1);
    Int minor_ptr = new Int(-1);

    Jack.server().getJackVersion(major_ptr, minor_ptr, null, null);
    assertThat(major_ptr.value).isAtLeast(0);
    assertThat(minor_ptr.value).isAtLeast(0);
    ;
  }

  /**
   * The function {@link Jack#getJniVersion()}
   * shall return a valid JNI version.
   */
  @Test
  public void getJniVersion() {
    int version = Jack.server().getJniVersion();
    assertThat(version).isAtLeast(0);
  }

  /**
   * The function {@link Jack#clientNameSize()}
   * shall return a reasonable string size (something larger than 8)
   */
  @Test
  public void clientNameSize() {
    int size = Jack.server().clientNameSize();
    assertThat(size).isAtLeast(8);
  }

  /**
   * The function {@link Jack#portNameSize()}
   * shall return a reasonable string size (something larger than 8)
   */
  @Test
  public void portNameSize() {
    int size = Jack.server().portNameSize();
    assertThat(size).isAtLeast(8);
  }

  /**
   * The function {@link Jack#portTypeSize()}
   * shall return a reasonable string size (something larger than 8)
   */
  @Test
  public void portTypeSize() {
    int size = Jack.server().portTypeSize();
    assertThat(size).isAtLeast(8);
  }

  /**
   * Calling the function {@link Jack#clientClose(ClientHandle)} ()}
   * with an invalid clientHandle should return an error code.
   * Unfortunately this works only with a NULL value, any other
   * invalid value will crash the whole JVM.
   */
  @Test
  public void invalidClientClose() {
    InternalClientHandle NullClient = new InternalClientHandle(0);
    int error = Jack.server().clientClose(NullClient);
    assertThat(error).isNotEqualTo(0);
  }

  /**
   * Check whether it is possible to open - and immediately after - to close a client.
   * @throws InterruptedException we'll sleep for a short while, an exception should never be thrown.
   */
  @Test
  public void ClientOpenClose() throws InterruptedException {


    OpenStatus returnStatus = new OpenStatus();

    ClientHandle client = Jack.server().clientOpen("JackBasicTest", new OpenOption[]{}, returnStatus, null);
    assertThat(client).isNotNull();
    assertThat(client.isValid()).isTrue();

    assertThat(returnStatus.hasFailure()).isFalse();

    Thread.sleep(100);

    int error = Jack.server().clientClose(client);
    assertThat(error).isEqualTo(0);
    assertThat(client.isValid()).isFalse();
  }

  /**
   * A client shall be able to register and use a __ShutdownListener__.
   *
   * We will open a new client , register the listener than ask the operator to close the JACK connection kit
   * (through qjackctl) The operator shall verfy that he sees the message "... Hooray! The onShutdown was called".
   *
   * @throws InterruptedException we'll sleep for a short while, this exception should never be thrown.
   */
  @Test
  public void ClientRegisterShutdownListener() throws InterruptedException {


    OpenStatus returnStatus = new OpenStatus();

    ClientHandle client = Jack.server().clientOpen("ShutdownListenerTest", new OpenOption[]{}, returnStatus, null);
    assertThat(client).isNotNull();
    assertThat(client.isValid()).isTrue();
    assertThat(returnStatus.hasFailure()).isFalse();

    TestShutdownListener testShutdownListener = new TestShutdownListener();
    assertThat(testShutdownListener.count).isEqualTo(0);

    Jack.server().registerShutdownListener(client, testShutdownListener);

    System.out.println("Please close the Jack server within the next 5 seconds....");
    Thread.sleep(5000);

    if(testShutdownListener.count == 0 ){
      Jack.server().clientClose(client);
    }

  }

  @Test(expected = java.util.regex.PatternSyntaxException.class)
  public void throwWrongPattern() {
     Jack.verifyPattern("*1");
  }

  @Test
  public void passGoodPattern() {
    Jack.verifyPattern("1*");
    Jack.verifyPattern(null);

  }

  /**
   * A ShutdownListener that simply counts the number of times,
   * the `onShutdown` function has been called.
   */
  private static class TestShutdownListener implements ShutdownListener {
    /**
     * The counter.
     * <p>
     * Please note, that for the sake of simplicity, no provision have been made to ensure
     * thread safety.
     */
    long count = 0;

    @Override
    public void onShutdown() {
      System.out.println("... Hooray! The onShutdown was called.");
      count++;
    }
  }

}