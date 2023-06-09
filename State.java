public class State {
    private char symbol;
    public int next1;
    public int next2;
    
    public State(char symbol, int next1, int next2 ) {
        
        this.symbol = symbol;
        this.next1 = next1;
        this.next2 = next2;
    }
    
    public char getSymbol(){
        return this.symbol;
    }
}
