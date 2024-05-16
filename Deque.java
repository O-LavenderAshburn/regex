import java.util.*;

/**
 * Class to Queue possible next states and pop possible current states 
 * 
 * Created by Oscar Ashburn, [ID]
 * @author Oscar Ashburn
 * @version 1.0.0
 */
public class Deque {
  // Deque stack and queue
  private ArrayList<Integer> possibleCurrentStates = new ArrayList<Integer>();
  private ArrayList<Integer> possibleNextStates = new ArrayList<Integer>();
  private int[] visited; 
  
  // Initialise with possible current state 0
  public Deque(int numStates){
    possibleCurrentStates.add(0);

    // Initialise visited array 
    visited = new int[numStates];
    Arrays.fill(visited,0);
  }

  /**
   * Pop the current state off the stack
   * @return  state number of the possible current state 
   */
  public int pop(){
    // Check if the possible current states is empty
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
    queue(n1);
    queue(n2);
  }

  /**
   * Queues the next possible state if it has not been visited 
   * @param n1 state index
   */
  public void queue(int n1){
    // Check if it has been visited 
    possibleNextStates.add(n1);
  }

  /**
   * Push possible current states onto the stack
   * @param n1 next 1
   * @param n2 next 2 
   */
  public void push(int n1, int n2){
    // Push onto the stack
    push(n1);
    push(n2);
  }

  /**
   *  Push possible current state onto the stack
   * @param n1 next state
   */
  public void push(int n1){
    //check if visited
    if (visited[n1]== 0) {
      possibleCurrentStates.add(0, n1);
      visited[n1]=1;
    }
  }

  /**
   * Copy all possible next states to possible current states and remove all old possible next states
   */
  public void deque(){
    // Copy possible next states to possible current states 
    possibleCurrentStates = possibleNextStates;
    // Clear possible next states
    possibleNextStates = new ArrayList<Integer>();
  }

   /**
   *  Reset visited 
   */
  public void resetVisited(){
    Arrays.fill(visited,0);
  }

   /**
   *  Check if stack is empty
   */
  public Boolean emptyStack(){
    if (possibleCurrentStates.size() == 0){
      return  true;
    }
    return false;
  }
}
