package jackAudio4Java.types;

import jackAudio4Java.Jack;

import java.util.Objects;

/**
 * An Enum like data structure for describing _port types_.
 * <p>
 * Currently predefined _port types_ for _Audio_ and _Midi_
 * are built into the JACK API.
 * <p>
 * Only ports of the same type can be connected.
 */
public class PortType {

  private final String portTypeName;
  static final String _32BitFloatMonoAudio = "32 bit float mono audio";
  static final String _8BitRawMidi = "8 bit raw midi";

  private PortType(String portTypeName) {
    this.portTypeName = portTypeName;
  }

  /**
   * Use `PortType.defaultAudio()` to describe an audio port.
   *
   * @return the built-in __Audio__ port-type.
   */
  public static PortType defaultAudio() {
    return new PortType(_32BitFloatMonoAudio);
  }

  /**
   * Use `PortType.defaultMidi()` to describe a midi port.
   *
   * @return the built-in __Midi__ port-type.
   */
  public static PortType defaultMidi() {
    return new PortType(_8BitRawMidi);
  }

  /**
   * Use `PortType.custom(portTypeName)` to describe a customer supplied port type.
   * <p>
   * Note: because of this custom type we cannot define a regular java Enum here.
   *
   * @param portTypeName The name of this port Type. The {@link   Jack#portTypeSize()} is
   *                     the maximum length of this full name.  Exceeding that will cause the port
   *                     registration to fail.
   * @return the built-in __Midi__ port-type.
   */
  public static PortType custom(String portTypeName) {
    return new PortType(portTypeName);
  }

  @Override
  public String toString() {
    return portTypeName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PortType portType = (PortType) o;
    return portTypeName.equals(portType.portTypeName);
  }

  @Override
  public int hashCode() {
    return Objects.hash(portTypeName);
  }
}
