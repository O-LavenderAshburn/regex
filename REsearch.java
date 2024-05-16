import java.io.*;
import java.util.ArrayList;

/**
 * Regex pattern searcher,Searches pattern with the output generated from REcompile
 * 
 * Created by Oscar Ashburn, [ID]
 * @author Oscar Ashburn
 * @version 1.0.0
 */
public class REsearch {
  // Deque to keep track of possible current and next states
  private static Deque deque;
  private static ArrayList<State> states = new ArrayList<State>();
  private static int currentStatePos;
  private static State currentState;

  public static void main(String[] args) {
    // Pointers and marks
    int mark = 0;
    Boolean success;
    String currentLine;

    // Print usage and exit
    if (args.length < 1) {
      System.out.println("Usage: ");
      return;
    }

    // File to search
    String filename = args[0];

    // Open input stream to read FSM from standard input
    InputStreamReader isr = new InputStreamReader(System.in);
    BufferedReader reader = new BufferedReader(isr);

    // Read in the description of the finite state machine
    try {
      String line = reader.readLine();

      while (line != null) {
        // Extract the state information from the line
        char symbol = line.charAt(0);

        //array of next states
        String[] nextStates = line.substring(2).split(",");
        //next states
        int n1 = Integer.parseInt(nextStates[0]);
        int n2 = Integer.parseInt(nextStates[1]);

        // Create state and add to list of states
        State newState = new State(symbol, n1, n2);
        states.add(newState);
        //read another line
        line = reader.readLine();
      }
      
      //close reader
      reader.close();
    }
    catch (IOException e) {
      System.err.println(e);
    }

    try {
      
      // Open the file containing the strings to search
      FileReader fr = new FileReader(filename);
      BufferedReader lineReader = new BufferedReader(fr);

      // Read the first line
      String line = lineReader.readLine();

      // While we have lines to process
      while (line != null) {
        // While we still have a sub-string to search
        while (mark < line.length()) {
          // Shorten the current line to search
          currentLine = line.substring(mark);

          // Search the current line
          success = search(currentLine);
          
          if (success == true) {
            System.out.println(line);
            break;
          }
          //increment mark
          mark++;
        }

        // Get the next line to process and increment lineNumber and reset mark
        line = lineReader.readLine();
        mark = 0;
      }
      //close reader
      lineReader.close();
    }
      //handle file not found exceptions
    catch (FileNotFoundException e) {
      System.err.println("Error: " + filename + " not found.");
    }
    //handle IO exceptions
    catch (IOException e) {
      System.err.println(e);
    }
  }

  /**
  *
  *@param string Phrase to search for
  */
  public static Boolean search(String string) { 
    
    // Create new deque
    currentStatePos = 0;
    deque = new Deque(states.size());

    // Set pointer
    int pointer = 0;
    char[] chars = string.toCharArray();
    char currentChar = chars[pointer];

    // Get the start state of the machine
    currentState = states.get(currentStatePos);

    while (true) {
      if (pointer != chars.length) {
        // If we have consumed all our input without getting to the final state
        if (pointer > chars.length) {
          break;
        }

        // set the current pointer
        currentChar = chars[pointer];
      }
      
      while (true) {
        if (currentState.getType() == Symbols.BRANCH) {
          deque.push(currentState.next1, currentState.next2);
          
          if (deque.emptyStack()) {
            if (isEmpty()) {
              return false;
            }

            // If we get back to state 0
            if (currentState.next1 == 0) {
              return true;
            }
            break;
          }

          currentStatePos = deque.pop();
          currentState = states.get(currentStatePos);
        }
        else {
          // pop of the stack
          if (deque.emptyStack()) {
            if (isEmpty()) return false;

            // If we get back to state 0
            if (currentState.next1 == 0) {
              return true;
            }
            // exit and increment pointer
            break;
          }

          currentStatePos = deque.pop();
          currentState = states.get(currentStatePos);

        }

        if (!(currentState.getType() == Symbols.BRANCH)) {
          checkMatching(currentState, currentChar);
        }

        // If we get back to state 0
        if (currentState.next1 == 0) {
          return true;
        }
      }

      pointer++;
    }

    return false;
  }

  /**
   * Check if we are matching a character
   * 
   * @param state   the current state
   * @param pointer the character we are currently pointing at
   */
  public static void checkMatching(State state, char current) {
    char symbol = state.getType();

    if (symbol == Symbols.WILDCARD) {
      deque.queue(state.next1, state.next2);
    } else if (symbol == current) {
      deque.queue(state.next1, state.next2);
    }
  }

  public static Boolean isEmpty() {
    // Deque next states onto the stack and reset visited
    deque.deque();
    deque.resetVisited();

    // If empty stack after deque
    if (deque.emptyStack()) {
      return true;
    }

    currentStatePos = deque.pop();
    currentState = states.get(currentStatePos);

    return false;
  }
}
