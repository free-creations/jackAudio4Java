package jackAudio4Java;

import jackAudio4Java.types.Int;
import org.junit.Before;
import org.junit.Test;

import java.util.logging.Level;

import static com.google.common.truth.Truth.assertThat;

public class JackTest {

  @Before
  public void setUp() {
    Jack.server().setLoggingLevel(Level.ALL);
  }

  /**
   * The function {@link Jack#getJackVersion(Int, Int, Int, Int)}
   * shall return the version of the jack library.
   * Note: the version of jack2 installed with
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
}