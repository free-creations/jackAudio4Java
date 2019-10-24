package jackAudio4Java.buffers;

public interface AudioSlice {

  /**
   *
   * @return The cycle number for which this slice is guaranteed to remain valid. If the returned value is
   * zero, no guarantee is given.
   */
  long validThru();

  /**
   * Free all resources hold by this slice.
   * Once a slice has expired, it is a kind of zombie that cannot be accessed anymore.
   */
  void expire();

  /**
   *
   * @return true if this slice is expired.
   */
  boolean isExpired();
}
