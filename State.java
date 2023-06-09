/**
 * Represents a single state in a
 * finite state machine
 */
public class State {
  public Character symbol;
  public int next1;
  public int next2;

  public State (Character symbol, int next1, int next2) {
    this.symbol = symbol;
    this.next1 = next1;
    this.next2 = next2;
  }
}
