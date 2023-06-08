import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ParseTest {
  private final PrintStream originalOut = System.out;
  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  
  @Before
  public void setup() {
    System.setOut(new PrintStream(outContent));
  }

  @After
  public void reset() {
    System.setOut(originalOut);
  }
  
  @Test
  public void testValidExpressions() {
    String[] expressions = new String[] {
      "",
      "abc",
      ".",
      "a*",
      "a+",
      "a?",
      "a|b",
      "(a)",
      "!a",
      "a.b",
      "a*bb",
      "abcsdfasdfiuashdfj",
      "(a*|b+d.\\+)*d",
      "a*|b",
      "a|b*cd",
      "[abc]",
      "[[abc]",
      "[]abc]",
      "r|!(a?)+\\.(b|(t*)d)",
      "a*b*b+b?|bb+as[abc]",
      "dfj(ld*)|kj|(df|g*|p?|hjfgo)|u(i*(s|d))"
    };

    for (String expression : expressions) {
      assertDoesNotThrow(() -> REcompile.main(new String[] { expression }));
    }
  }

  @Test
  public void testInvalidExpressions() {
    String[] expressions = new String[] {
      "|b",
      "(ab(d)",
      ")(a)(",
      ".\\a",
      "*dbs",
      "+bd.",
      "[abc",
      "ab]",
      "[abc]]",
      "[ab[c]"
    };

    for (String expression : expressions) {
      assertThrows(Exception.class, () -> {
        REcompile.main(new String[] { expression });
      });
    }
  }
}
