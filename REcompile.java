import java.util.Arrays;

public class REcompile {
  private static char[] p;
  private static int i;

  private static final String OPERATORS = ".*+?|()\\[]!\0";

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

    i = 0;

    // Attempt to parse pattern as valid regex
    expression();
  }

  public static void expression() throws Exception {
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

    System.out.println("E -> T");
  }

  public static void alternation() throws Exception {
    System.out.println("A -> |E");
    i++;
    expression();
  }

  public static void term() throws Exception {
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

  public static void factor() throws Exception {
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

      expression();
    }
    // Check for raised precedence
    else if (p[i] == '(') {
      System.out.println("F -> (E");
      i++;

      expression();

      System.out.println("F -> (E)");

      // Validate closing bracket
      if (p[i] != ')') {
        throw new Exception("Invalid pattern - expected bracket in column " + (i + 1));
      }

      i++;
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
