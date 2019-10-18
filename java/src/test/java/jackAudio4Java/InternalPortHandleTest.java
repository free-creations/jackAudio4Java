package jackAudio4Java;

import jackAudio4Java.types.PortFlag;
import jackAudio4Java.types.PortHandle;
import jackAudio4Java.types.PortType;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

public class InternalPortHandleTest {

  private PortHandle candidate;
  private PortFlag[] flags = {PortFlag.isPhysical};

  @Before
  public void setUp() throws Exception {
    candidate = new InternalPortHandle(123, "testPort", PortType.defaultAudio(), flags);
  }

  @Test
  public void getName() {
    assertThat(candidate.getName()).isEqualTo("testPort");
  }

  @Test
  public void getType() {
    assertThat(candidate.getType()).isEqualTo(PortType.defaultAudio());
  }

  @Test
  public void getFlags() {
    assertThat(candidate.getFlags()).isEqualTo(flags);
  }

  @Test
  public void isValid() {
    assertThat(candidate.isValid()).isTrue();

  }

  @Test
  public void getReference() {
    assertThat(((InternalPortHandle) candidate).getReference()).isEqualTo(123);
  }

  @Test
  public void invalidate() {
    ((InternalPortHandle) candidate).invalidate();
    assertThat(candidate.isValid()).isFalse();

  }


}