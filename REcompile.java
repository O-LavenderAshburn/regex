import java.util.ArrayList;

/**
 * Accepts a regex pattern from the command line
 * and compiles it into a finite state machine, 
 * a description of which is then outputted to standard
 * output if the pattern was valid.
 * 
 * Created By Luke Finlayson, 1557835
 */
public class REcompile {
  // A list of regex operators - and thus illegal characters otherwise
  private static final String OPERATORS = ".*+?|()\\[]!\0";
  
  // The pattern and pointer to current character in pattern
  private static char[] p;
  private static int i;

  private static ArrayList<State> states;
  private static int next;

  public static void main(String[] args) {
    // Ensure the pattern was passed as an argument
    if (args.length != 1) {
      System.err.println("Usage: java REcompile [expression]");
      return;
    }

    // Convert expression to char array for more convenient index access
    char[] pattern = args[0].toCharArray();

    // Include a null byte to simplify error checks
    p = new char[pattern.length + 1];
    System.arraycopy(pattern, 0, p, 0, pattern.length);
    p[pattern.length] = '\0';

    // Initialise pointers
    i = 0;
    next = 1;
    states = new ArrayList<>();
    states.add(null);

    // Attempt to parse pattern as valid regex
    try {
      int start = expression();
      
      // Add the 0th state and output state table
      states.set(0, new State(Symbols.BRANCH, start, start));
      states.add(new State(Symbols.BRANCH, 0, 0));

      // Output the description of the FSM
      for (State state : states) {
        System.out.println(state.symbol + ", " + state.next1 + ", " + state.next2);
      }
    }
    catch (Exception e) {
      System.err.println(e.getMessage());
    }
  }

  /**
   * Search for a valid regex expression
   * 
   * E → C
   * E → C|E
   */
  private static int expression() throws Exception {
    int savedNext = next;
    int start = concatenation();

    // Check for an alternation
    if (p[i] == '|') {
      i++;

      // Reserve a space for the branch state (to avoid having to loop through the first branch)
      int endA = next;
      next++;
      State branch = new State(Symbols.BRANCH, start, 0);
      states.add(branch);

      // Create the second branch and add the branch machine
      int startB = expression();
      branch.next2 = startB;

      updateEnd(savedNext, endA, next);
      start = endA;
    }

    return start;
  }

  /**
   * Search for valid regex concatenation
   *
   * C → T
   * C → TC
   */
  private static int concatenation() throws Exception {
    int savedNext = next;
    int startA = term();

    // Check for more concatenation
    if (p[i] != ')' && p[i] != '\0' && p[i] != '|') {
      int endA = next;
      int startB = concatenation();

      updateEnd(savedNext, endA, startB);
    }

    return startA;
  }

  /**
   * Search for a valid regex term
   * 
   * T → F
   * T → F*
   * T → F+
   * T → F?
   */
  private static int term() throws Exception {
    int savedNext = next;
    int start = factor();

    // Check for repetition operators
    if (p[i] == '*' || p[i] == '+' || p[i] == '?') {
      // Insert a branch state for the factor to loop back to
      next++;
      states.add(new State(Symbols.BRANCH, start, next));

      // Adjust for certain operators
      if (p[i] != '+') start = next - 1;
      if (p[i] == '?') updateEnd(savedNext, next - 1, next);

      i++;
    }

    return start;
  }

  /**
   * Search for a valid regex factor
   * 
   * F → λ
   * F → \Ω
   * F → !F
   * F → (E)
   * F → [abc]
   * F → .
   */
  private static int factor() throws Exception {
    int start = next;

    // Check for valid literal matches
    if (!OPERATORS.contains("" + p[i])) {
      next++;
      states.add(new State(p[i], next, next));
      i++;
    }
    // Check for escape codes
    else if (p[i] == '\\') {
      i++;

      // Validate escape symbol
      if (OPERATORS.contains("" + p[i])) {
        next++;
        states.add(new State(p[i], next, next));
        i++;
      }
      else {
        error("Invalid escape sequence");
      }
    }
    // Check for NOT operators
    else if (p[i] == '!') {
      i++;

      // Wrap the factor in a NOT gate
      next++;
      State enterNotState = new State(Symbols.NOT_IN, 0, 0);
      states.add(enterNotState);

      int middle = factor();
      next++;
      states.add(new State(Symbols.NOT_OUT, next, next));
      
      enterNotState.next1 = middle;
      enterNotState.next2 = middle;
    }
    // Check for raised precedence
    else if (p[i] == '(') {
      i++;

      // Start is no longer 'next' but the start of the first state in the expression
      start = expression();

      // Validate closing bracket
      if (p[i] != ')') {
        error("Invalid pattern - expected )");
      }

      i++;
    }
    // Check for alternation ([abc...n])
    else if (p[i] == '[') {
      int s = i;
      ArrayList<Character> symbols = new ArrayList<>();

      // Loop through to validate alternation (and get the symbol set)
      while (p[i] != '\0') {
        i++;

        // Allow operators in position 1 of the alternation 
        if (s != i - 1) {
          if (p[i] == '[') {
            error("Invalid symbol [");
          }
  
          if (p[i] == ']') {
            break;
          }
        }

        // Otherwise,
        symbols.add(p[i]);
      }

      // If we have reached the end of the pattern,
      // then the expression was invalid
      if (p[i] == '\0') {
        error("Invalid pattern - expected ]");
      }
      
      // Otherwise add the alternation derived from the symbol set
      int j = 0;
      int end = next + symbols.size() * 2;
      for (Character symbol : symbols) {
        // Last symbol doesn't need to branch
        if (j < symbols.size() - 1) {
          next++;
          states.add(new State(Symbols.BRANCH, next, next + 1));
        }

        states.add(new State(symbol, end, end));

        next++;
        j++;
      }

      i++;
    }
    // Check for wildcards
    else if (p[i] == '.') {
      next++;
      states.add(new State(Symbols.WILDCARD, next, next));
      i++;
    }
    // Catch unhandled operators
    else if (p[i] != '\0') {
      error("Unhandled operator <" + p[i] + ">");
    }

    return start;
  }

  /**
   * Loop through a given range of states and update the tail nodes to
   * point to the given target
   * 
   * @param start The index of the state to start from
   * @param currentEnd The current state pointed to by the tail states
   * @param targetEnd The target state to point the tails to
   */
  private static void updateEnd(int start, int currentEnd, int targetEnd) {
    if (currentEnd == targetEnd) return;

    for (int j = start; j < currentEnd; j++) {
      State current = states.get(j);

      if (current.next1 == currentEnd) {
        current.next1 = targetEnd;
      }
      if (current.next2 == currentEnd) {
        current.next2 = targetEnd;
      }
    }
  }

  private static void error(String message) throws Exception {
    throw new Exception(message + " in column " + (i + 1));
  }
}
