/**
*State object to keep track of the symbol and possible next states
*
*/
public class State {
    private char symbol;
    public int next1;
    public int next2;
    
    /**
    *Set State symbol,and next possible states
    */
    public State(char symbol, int next1, int next2 ) {
        
        this.symbol = symbol;
        this.next1 = next1;
        this.next2 = next2;
    }
    
    public char getType(){
        return this.symbol;
    }
}
