package exception;

import org.apache.log4j.Logger;
import org.junit.Test;

public class IllegalVertexTypeExceptionTest {

  private static final Logger LOGGER = Logger.getLogger(IllegalVertexTypeException.class);

  @Test
  public void testException() {
    try {
      throw new IllegalVertexTypeException("Test Exception");
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }

    try {
      throw new IllegalVertexTypeException(new Exception("Test Exception"));
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }

    try {
      throw new IllegalVertexTypeException();
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }
  }
}