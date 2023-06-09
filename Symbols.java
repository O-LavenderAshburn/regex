/**
 * Includes code friendly references to 
 * the non-alphanumeric characters used
 * throughout this regex implementation.
 */
public final class Symbols {
  // Restrict instantiation
  private Symbols() {}
  
  // Reserved symbols for FSM table state identifiers
  public static final char BRANCH = 'γ';
  public static final char WILDCARD = 'α';
  public static final char NOT_IN = 'φ';
  public static final char NOT_OUT = 'σ';
}
