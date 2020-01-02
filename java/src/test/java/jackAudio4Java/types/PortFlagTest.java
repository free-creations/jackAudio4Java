package jackAudio4Java.types;

import org.junit.Test;

import java.util.Set;

import static com.google.common.truth.Truth.assertThat;
import static jackAudio4Java.types.PortFlag.*;


public class PortFlagTest {

  @Test
  public void getBits() {
     assertThat(isInput.getBits()).isEqualTo(1);
  }



  @Test
  public void setToLong() {
    Set<PortFlag> flags = PortFlag.setOf(isPhysical, isTerminal);
    assertThat(PortFlag.setToLong(flags)).isEqualTo(20);
  }

}