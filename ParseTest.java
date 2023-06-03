import static org.junit.Assert.assertEquals;

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
  public void testValidSingleExpression() {
    String expected = "";

    REcompile.main(new String[] {"a|b"});

    assertEquals(expected, outContent.toString().trim());
  }
}