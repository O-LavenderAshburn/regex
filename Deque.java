
import java.util.*;

/**
 *Class to Queue possible next states and pop possible current states 
 * 
 * @version 1.0.0
 * 
 */

public class Deque {

    //Deque stack and que
    private ArrayList<Integer> possibleCurrentStates = new ArrayList<Integer>();
    private ArrayList<Integer> possibleNextStates = new ArrayList<Integer>();
    private int[] visited; 
    

    //initiliase with possible current state 0
    public Deque(int numStates){

        possibleCurrentStates.add(0);

        //intitlize visited array 
        visited = new int[numStates];
        Arrays.fill(visited,0);

    }

    /**
     * Pop the current state off the stack
     * @return  state number of the possible current state 
     */
    public int pop(){

        //check if the possible current states is empty
        if(possibleCurrentStates.size() == 0){
            setPossibleCurrentStates();
        }

        int stateNum = possibleCurrentStates.get(0);
        possibleCurrentStates.remove(0);

        return stateNum;
    }


   /**
    * Queues the next possible state
    * @param n1 state index 1
    * @param n2 state index 2
    */
    public void queue(int n1, int n2){

        //add both next possible states if they have not been visited 
        if(visited[n1] == 0){
            possibleNextStates.add(n1);
        }
        if(visited[n2] == 0){
            possibleNextStates.add(n2);
        }

    }

    /**
     * Queues the next possible state if it has not been visited 
     * @param n1 state index
     */
    public void queue(int n1){

        //check if it has been visited 
        if(visited[n1] == 0){

            possibleNextStates.add(n1);

        }
    }

    /**
     * copy all possibe next states to possible current states and remove all old possible next states
     */
    private void setPossibleCurrentStates(){
        
        //copy possible next states to possible current states 
        Collections.copy(possibleNextStates,possibleCurrentStates);

        //clear possible next states
        possibleNextStates.removeAll(possibleNextStates);
        
    }


}
