package jackAudio4Java;

import jackAudio4Java.types.Int;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ServerTest {
  @Before
  public void setUp() {
    // @ToDo remove hardcoded library name
    System.load("/home/harald/workspace/jackAudio4Java/linux/cmake-build-debug/src/libjackaudio4java_lib_0.0.0.so");
  }


  @Test
  public void jack_get_version() {
    Int major_ptr = new Int(-1);
    Int minor_ptr = new Int(-1);
    Int micro_ptr = new Int(-1);
    Int proto_ptr = new Int(-1);

    Server.jack_get_version(major_ptr, minor_ptr, micro_ptr, proto_ptr);

    assertTrue(major_ptr.value >= 0);
    assertTrue(minor_ptr.value >= 0);
    assertTrue(micro_ptr.value >= 0);
    assertTrue(proto_ptr.value >= 0);
  }

  @Test
  public void jni_get_version() {
    int version = Server.jni_get_version();
    assertTrue(version >= 0);
  }
}