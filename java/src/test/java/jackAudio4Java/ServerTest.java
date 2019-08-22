package jackAudio4Java;

import jackAudio4Java.types.Int;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ServerTest {


  /**
   * The function Server.jack_get_version shall return the version of the jack library.
   */
  @Test
  public void jack_get_version() {
    Int major_ptr = new Int(-1);
    Int minor_ptr = new Int(-1);
    Int micro_ptr = new Int(-1);
    Int proto_ptr = new Int(-1);

    Server.server().jack_get_version(major_ptr, minor_ptr, micro_ptr, proto_ptr);

    assertTrue(major_ptr.value >= 0);
    assertTrue(minor_ptr.value >= 0);
    assertTrue(micro_ptr.value >= 0);
    assertTrue(proto_ptr.value >= 0);
  }

  /**
   * The function Server.jack_get_version shall not crash if, instead of an Int container,
   * a `null` pointer is given.
   */
  @Test
  public void jack_get_version_null_ptr() {
    Int major_ptr = new Int(-1);
    Int minor_ptr = new Int(-1);

    Server.server().jack_get_version(major_ptr, minor_ptr, null, null);

    assertTrue(major_ptr.value >= 0);
    assertTrue(minor_ptr.value >= 0);
  }

  @Test
  public void jni_get_version() {
    int version = Server.server().jni_get_version();
    assertTrue(version >= 0);
  }
}