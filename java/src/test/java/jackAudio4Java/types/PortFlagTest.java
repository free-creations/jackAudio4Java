package jackAudio4Java.types;

import org.junit.Test;
import static com.google.common.truth.Truth.assertThat;
import static jackAudio4Java.types.PortFlag.*;


public class PortFlagTest {

  @Test
  public void getBits() {
     assertThat(isInput.getBits()).isEqualTo(1);
  }

  @Test
  public void arrayContains() {
    PortFlag[] flags = {isInput, isPhysical};
    assertThat(PortFlag.arrayContains(flags, isInput)).isTrue();
    assertThat(PortFlag.arrayContains(flags, isOutput)).isFalse();
  }

  @Test
  public void arrayToLong() {
    PortFlag[] flags = {isPhysical, isTerminal};
    assertThat(PortFlag.arrayToLong(flags)).isEqualTo(20);
  }


  @Test
  public void arrayClone() {
    PortFlag[] flags = {isPhysical, isTerminal};
    PortFlag[] clone = PortFlag.arrayClone(flags);
    assertThat(clone).isEqualTo(flags);
  }


}