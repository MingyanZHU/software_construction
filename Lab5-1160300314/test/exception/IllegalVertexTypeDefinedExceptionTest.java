package exception;

import org.apache.log4j.Logger;
import org.junit.Test;

public class IllegalVertexTypeDefinedExceptionTest {

  private static final Logger LOGGER = Logger.getLogger(IllegalVertexTypeDefinedException.class);

  @Test
  public void testException() {
    try {
      throw new IllegalVertexTypeDefinedException("Test Exception");
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }

    try {
      throw new IllegalVertexTypeDefinedException(new Exception("Test Exception"));
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }

    try {
      throw new IllegalVertexTypeDefinedException();
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }
  }
}