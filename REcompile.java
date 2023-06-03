public class REcompile {
  private static char[] p;
  private static int i;
  private static boolean error;

  private static final String OPERATORS = ".*+?|()\\[]!";

  public static void main(String[] args) {
    // Ensure the expression was passed as an argument
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

    i = 0;
    error = false;

    expression();

    if (error) {
      System.out.println("Invalid regex");
    }
  }

  public static void expression() {
    term();

    // Otherwise check for alternations
    if (p[i] == '|') {
      alternation();
    }

    // We have reached the end of the pattern successfully
    if (p[i] == '\0') {
      return;
    }

    expression();
  }

  public static void alternation() {
    i++;
    expression();
  }

  public static void term() {
    factor();

    // Check for operators
    if (p[i] == '*') {
      i++;
    }
    else if (p[i] == '+') {
      i++;
    }
    else if (p[i] == '?') {
      i++;
    }
  }

  public static void factor() {
    // Check for valid literal matches
    if (!OPERATORS.contains("" + p[i])) {
      i++;
    }
    else if (p[i] == '\\') {
      i++;

      if (OPERATORS.contains("" + p[i])) {
        i++;
      }
      else {
        error = true;
      }
    }
    else if (p[i] == '!') {
      i++;
      expression();
    }
    else if (p[i] == '[') {
      i++;

      while (p[i] != ']') {
        if (p[i] == '\0') {
          error = true;
          break;
        }

        i++;
      }
    }
    else if (p[i] == '.') {
      i++;
    }
    else {
      error = true;
    }
  }
}
