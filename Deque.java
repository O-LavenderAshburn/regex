import java.util.*;

/**
 * Class to Queue possible next states and pop possible current states 
 * 
 * Created by Oscar Ashburn, 1582735
 * 
 * @version 1.0.0
 */
public class Deque {
  // Deque stack and queue
  private ArrayList<DequeNode> possibleCurrentStates = new ArrayList<>();
  private ArrayList<DequeNode> possibleNextStates = new ArrayList<>();
  private int[] visited;
  
  // Initialise with possible current state 0
  public Deque(int numStates){
    // Initialise visited array 
    visited = new int[numStates];
    Arrays.fill(visited,0);
  }

  /**
   * Pop the current state off the stack
   * @return  state number of the possible current state 
   */
  public DequeNode pop(){
    // Check if the possible current states is empty
    DequeNode stateNum = possibleCurrentStates.get(0);
    possibleCurrentStates.remove(0);

    return stateNum;
  }

   /**
  * Queues the next possible state
  * @param n1 state index 1
  * @param n2 state index 2
  */
  public void queue(int next1, int next2, int notCount){
    queue(next1, notCount);
    queue(next2, notCount);
  }

  /**
   * Queues the next possible state if it has not been visited 
   * @param n1 state index
   */
  public void queue(int index, int notCount) {
    // Check if it has been visited
    possibleNextStates.add(new DequeNode(index, notCount));
  }

  /**
   * Push possible current states onto the stack
   * @param n1 next 1
   * @param n2 next 2 
   */
  public void push(int next1, int next2, int notCount) {
    // Push onto the stack
    push(next1, notCount);
    push(next2, notCount);
  }

  /**
   *  Push possible current state onto the stack
   * @param n1 next state
   */
  public void push(int index, int notCount) {
    // Only push onto the stack if it has been visited
    if (visited[index] == 0) {
      possibleCurrentStates.add(0, new DequeNode(index, notCount));
      visited[index] = 1;
    }
  }

  /**
   * Copy all possible next states to possible current states and remove all old possible next states
   */
  public void deque(){
    // Copy possible next states to possible current states 
    possibleCurrentStates = possibleNextStates;
    // Clear possible next states
    possibleNextStates = new ArrayList<>();
  }
  
  public void resetVisited(){
    Arrays.fill(visited,0);
  }

  public boolean emptyStack(){
    return possibleCurrentStates.size() == 0;
  }

  public boolean isEmpty() {
    return possibleCurrentStates.isEmpty() && possibleNextStates.isEmpty();
  }

  public String size() {
    return "s: " + possibleCurrentStates.size() + ", q: " + possibleNextStates.size();
  }

  public String queueToString() {
    String result = "[ ";

    for (DequeNode node : possibleNextStates) {
      result += node.getIndex() + ", " + node.getNotCount() + " | ";
    }

    return result + "]";
  }
}
