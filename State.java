public class State {
    private char[] symbol;
    private int n1;
    private int n2;
    
    public State(char[] sym,int next1,int next2 ){
        
        this.symbol = sym;
        this.n1 = next1;
        this.n2 = next2;
    }
    
    public char[] getType(){

        return this.symbol;
    }

    /**
     * Get the next1 State 
     * @return next state 1
     */
    public int next1(){

        return this.n1;
    }

   /**
     * Get the next2 State 
     * @return next state 2
     */
    public int next2(){

        return this.n2;
    }
}
