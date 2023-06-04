
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
     * Queues up the next possible state
     */
    public void queue(int n1, int n2){

        //if not a branching state
        if(n1 == n2){

            if(visited[n1] == 0){

                possibleNextStates.add(n1);
                return;

            }

        }
        //add both next possible states if they havent been visited 
        if(visited[n1] == 0){
            possibleNextStates.add(n1);
        }
        if(visited[n2] == 0){
            possibleNextStates.add(n2);
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
