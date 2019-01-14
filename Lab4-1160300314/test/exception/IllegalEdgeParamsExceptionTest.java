package exception;

import factory.LoggerFactory;
import org.apache.log4j.Logger;
import org.junit.Test;

public class IllegalEdgeParamsExceptionTest {

  private static final Logger LOGGER = LoggerFactory.createLogger(
      IllegalEdgeParamsExceptionTest.class);

  @Test
  public void testException() {
    try {
      throw new IllegalEdgeParamsException("Test IllegalEdgeParamsException");
    } catch (IllegalEdgeParamsException e) {
      LOGGER.debug(e.getMessage());
    }

    try {
      throw new IllegalEdgeParamsException(new Exception("Test IllegalEdgeParamsException"));
    } catch (IllegalEdgeParamsException e) {
      LOGGER.debug(e.getMessage());
    }

    try {
      throw new IllegalEdgeParamsException();
    } catch (IllegalEdgeParamsException e) {
      LOGGER.debug(e.getMessage());
    }
  }
}