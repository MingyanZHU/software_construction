package exception;

import org.apache.log4j.Logger;
import org.junit.Test;

public class IllegalVertexParamsExceptionTest {

  private static final Logger LOGGER = Logger.getLogger(IllegalVertexParamsException.class);

  @Test
  public void testException() {
    try {
      throw new IllegalVertexParamsException("Test Exception");
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }

    try {
      throw new IllegalVertexParamsException(new Exception("Test Exception"));
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }

    try {
      throw new IllegalVertexParamsException();
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }
  }
}