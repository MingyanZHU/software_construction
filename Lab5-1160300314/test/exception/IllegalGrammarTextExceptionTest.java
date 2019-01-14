package exception;

import org.apache.log4j.Logger;
import org.junit.Test;

public class IllegalGrammarTextExceptionTest {

  private static final Logger LOGGER = Logger.getLogger(IllegalGrammarTextException.class);

  @Test
  public void testException() {
    try {
      throw new IllegalGrammarTextException("Test Exception");
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }

    try {
      throw new IllegalGrammarTextException(new Exception("Test Exception"));
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }

    try {
      throw new IllegalGrammarTextException();
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }
  }
}