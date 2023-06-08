public class State {
  public StateType type;
  public Character symbol;
  public int next1;
  public int next2;

  public State (StateType type, Character symbol, int next1, int next2) {
    this.type = type;
    this.symbol = symbol;
    this.next1 = next1;
    this.next2 = next2;
  }
}
