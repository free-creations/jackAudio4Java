package jackAudio4Java.types;

public class PortType {

  private final String description;

  PortType(String description) {
    this.description = description;
  }

  public static PortType defaultAudioType(){return new PortType("32 bit float mono audio");}
  public static PortType defaultMidiType(){return new PortType("8 bit raw midi");}
  public static PortType other(String description){return new PortType(description);}

  @Override
  public String toString() {
    return description;
  }
}
