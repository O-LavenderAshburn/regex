import java.util.ArrayList;
import java.util.Arrays;

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

  public static void main(String[] args) throws Exception {
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

    System.out.println("Parsing expression: " + Arrays.toString(p));

    // Initialise pointer
    i = 0;

    // Attempt to parse pattern as valid regex
    expression();
  }

  /**
   * Search for a valid regex expression
   * 
   * E → T
   * E → TA
   * E → TE
   */
  private static void expression() throws Exception {
    System.out.println("E -> T...");

    term();

    // Otherwise check for alternations
    if (p[i] == '|') {
      System.out.println("E -> TA");
      alternation();
    }
    else if (p[i] != '\0' && p[i] != ')') {
      System.out.println("E -> TE");
      expression();
    }
    else {
      System.out.println("E -> T");
    }
  }

  /**
   * Search for a valid regex alternation
   * 
   * A → |E
   */
  private static void alternation() throws Exception {
    System.out.println("A -> |...");

    i++;
    expression();

    System.out.println("A -> |E");
  }

  /**
   * Search for a valid regex term
   * 
   * T → F
   * T → F*
   * T → F+
   * T → F?
   */
  private static void term() throws Exception {
    System.out.println("T -> F...");

    factor();

    // Check for operators
    if (p[i] == '*') {
      System.out.println("T -> F*");
      i++;
    }
    else if (p[i] == '+') {
      System.out.println("T -> F+");
      i++;
    }
    else if (p[i] == '?') {
      System.out.println("T -> F?");
      i++;
    }
    else {
      System.out.println("T -> F");
    }
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
  private static void factor() throws Exception {
    // Check for valid literal matches
    if (!OPERATORS.contains("" + p[i])) {
      System.out.println("F -> λ: " + p[i]);
      i++;
    }
    // Check for escape codes
    else if (p[i] == '\\') {
      System.out.println("F -> \\?");
      i++;

      // Validate escape symbol
      if (OPERATORS.contains("" + p[i])) {
        System.out.println("F -> \\Ω");
        i++;
      }
      else {
        throw new Exception("Invalid escape sequence in column " + (i + 1));
      }
    }
    // Check for NOT operators
    else if (p[i] == '!') {
      System.out.println("F -> !E");
      i++;

      factor();
    }
    // Check for raised precedence
    else if (p[i] == '(') {
      System.out.println("F -> (E");
      i++;

      // Start is no longer 'next' but the start of the first state in the expression
      expression();

      System.out.println("F -> (E)");

      // Validate closing bracket
      if (p[i] != ')') {
        throw new Exception("Invalid pattern - expected ) in column " + (i + 1));
      }

      i++;
    }
    // Check for alternation ([abc...n])
    else if (p[i] == '[') {
      int start = i;

      while (p[i] != '\0') {
        i++;

        // Allow operators in position 1 of the alternation 
        if (start != i - 1) {
          if (p[i] == '[') {
            throw new Exception("Invalid symbol [ in column " + (i + 1));
          }
  
          if (p[i] == ']') {
            break;
          }
        }
      }

      // If we have reached the end of the pattern,
      // then the expression was invalid
      if (p[i] == '\0') {
        throw new Exception("Invalid pattern - expected ] in column " + (i + 1));
      }
      else {
        i++;
      }
    }
    // Check for wildcards
    else if (p[i] == '.') {
      System.out.println("F -> .");
      i++;
    }
    // Catch unhandled operators
    else if (p[i] != '\0') {
      throw new Exception("Unhandled operator <" + p[i] + "> in column " + (i + 1));
    }
  }
}
