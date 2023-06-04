
import java.util.*;
public class Deque {

    //Deque stack and que
    private ArrayList<Integer> posCurrentStates = new ArrayList<Integer>();
    private ArrayList<Integer> posNextStates = new ArrayList<Integer>();


    //initiliase with possible current state 0
    public Deque(){

        posCurrentStates.add(0);

    }

    /**
     * Pop the current state off the stack
     * @return  state number of the possible current state 
     */
    public int pop(){

        //check if the possible current states is empty
        if(posCurrentStates.size() == 0){

            //push next states on the stack
            posCurrentStates = posNextStates;
        }

        int stateNum = posCurrentStates.get(0);
        posCurrentStates.remove(0);

        return stateNum;
    }
    



}
