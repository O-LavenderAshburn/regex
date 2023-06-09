import java.io.*;
import java.util.ArrayList;

/**
 * Reads a description of a finite state machine
 * from standard input and uses it to search for
 * the pattern described by the FSM in a given text
 * file.
 * 
 * Created by Oscar Ashburn, 1582735
 */
public class REsearch {
  // Deque to keep track of possible current and next states
  private static Deque deque;
  private static ArrayList<State> states = new ArrayList<State>();
  private static int endState;

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

        String[] nextStates = line.substring(2).split(",");
        int n1 = Integer.parseInt(nextStates[0]);
        int n2 = Integer.parseInt(nextStates[1]);

        // Create state and add to list of states
        State newState = new State(symbol, n1, n2);
        states.add(newState);

        line = reader.readLine();
      }

      reader.close();
    }
    catch (IOException e) {
      System.err.println(e);
    }

    // Save the index of the end state
    endState = states.size() - 1;

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

          mark++;
        }

        // Get the next line to process and increment lineNumber and reset mark
        line = lineReader.readLine();
        mark = 0;
      }

      lineReader.close();
    }
    catch (FileNotFoundException e) {
      System.err.println("Error: " + filename + " not found.");
    }
    catch (IOException e) {
      System.err.println(e);
    }
  }

  /**
   * Search through a given string using the stored
   * FSM pattern.
   * 
   * @param str The string to search through
   * @return True if the pattern was found
   */
  public static Boolean search(String str) {
    // Initialise the deque with the start state
    deque = new Deque(states.size());
    deque.push(0, 0);

    // Set pointer
    int pointer = 0;
    str += "\0";
    char[] chars = str.toCharArray();
    char current;
    
    DequeNode node;
    State state;

    // Loop through each character in the string
    while (pointer < chars.length && !deque.isEmpty()) {
      current = chars[pointer];

      while (!deque.emptyStack()) {   // Loop until the stack is empty
        // Pop one of the current possible states off the stack
        node = deque.pop();
        state = states.get(node.getIndex());

        // Process the current state
        switch (state.getSymbol()) {
          case Symbols.BRANCH:
            deque.push(state.next1, state.next2, node.getNotCount());
            break;
          case Symbols.NOT_IN:
            deque.push(state.next1, node.getNotCount() + 1);
            break;
          case Symbols.NOT_OUT:
            deque.push(state.next1, node.getNotCount() - 1);
            break;
          case Symbols.WILDCARD:
            if (!node.isNot()) {
              deque.queue(state.next1, node.getNotCount());
            }
            break;
          default:
            if (!node.isNot() && state.getSymbol() == current) {
              deque.queue(state.next1, node.getNotCount());
            }
            else if (node.isNot() && state.getSymbol() != current) {
              deque.queue(state.next1, node.getNotCount());
            }
            else {
              continue;
            };
        }

        // Check if any of the correct paths lead to the end
        if (state.next1 == endState || state.next2 == endState) {
          return true;
        }
      }

      // Move onto the next character
      deque.deque();
      pointer++;
    }

    // If we reached the end of the string without a match, there is no match
    return false;
  }
}
