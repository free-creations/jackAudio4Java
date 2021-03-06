package jackAudio4Java;

import jackAudio4Java.types.PortFlag;
import jackAudio4Java.types.PortHandle;
import jackAudio4Java.types.PortType;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static com.google.common.truth.Truth.assertThat;
import static jackAudio4Java.types.PortFlag.isPhysical;

public class InternalPortHandleTest {

  private PortHandle candidate;
  private Set<PortFlag> flags = PortFlag.setOf(isPhysical);

  @Before
  public void setUp() throws Exception {
    candidate = new InternalPortHandle(123);
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