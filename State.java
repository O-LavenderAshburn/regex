public class State {
  private StateType type;
  private Character symbol;
  private int next1;
  private int next2;

  /**
   * Create a new state from a predetermined state type
   * @param type The type of state
   * @param symbol The literal associated with this state (if applicable)
   * @param next1 One of the next possible states
   * @param next2 The other next possible state
   */
  public State(StateType type, Character symbol, int next1, int next2) {
    this.type = type;
    this.symbol = symbol;
    this.next1 = next1;
    this.next1 = next2;
  }

  public State(char symbol, int next1, int next2) {
    // Determine the type of state from the given symbol
    switch (symbol) {
      case 'ɑ':
        this.type = StateType.WILDCARD;
        break;
      case 'γ':
        this.type = StateType.BRANCH;
        break;
      case 'φ':
        this.type = StateType.NOT_IN;
        break;
      case 'σ':
        this.type = StateType.NOT_OUT;
        break;
      default:
        this.type = StateType.LITERAL;
        this.symbol = symbol;
    }

    this.next1 = next1;
    this.next2 = next2;
  }

  /**
   * Update the next possible states to go to
   * from this state
   * 
   * @param next1 One of the next possible states
   * @param next2 The other next possible state
   */
  public void setNextStates(int next1, int next2) {
    this.next1 = next1;
    this.next2 = next2;
  }
}
