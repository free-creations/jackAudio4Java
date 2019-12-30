package jackAudio4Java.utilities;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Used to suppress SpotBugs (former FindBugs) warnings.
 * see also https://stackoverflow.com/questions/1829904/is-there-a-way-to-ignore-a-single-findbugs-warning
 */
@Retention(RetentionPolicy.CLASS)
public @interface SuppressFBWarnings  {
  /**
   * The set of SpotBugs warnings that are to be suppressed in
   * annotated element. The value can be a bug category, kind or pattern.
   *
   */
  String[] value() default {};

  /**
   * Optional documentation of the reason why the warning is suppressed
   */
  String justification() default "";
}