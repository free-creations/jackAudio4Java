package jackAudio4Java;

import jackAudio4Java.types.Int;
import org.junit.Test;

import static org.junit.Assert.*;

public class ServerTest {

  @Test
  public void jack_get_version() {
    Int major_ptr = new Int();
    Int minor_ptr = new Int();
    Int micro_ptr = new Int();
    Int proto_ptr = new Int();
    Server.jack_get_version(major_ptr, minor_ptr, micro_ptr, proto_ptr);
    assertTrue(major_ptr.value > 0);
    assertTrue(minor_ptr.value > 0);
    assertTrue(micro_ptr.value > 0);
    assertTrue(proto_ptr.value > 0);
  }
}