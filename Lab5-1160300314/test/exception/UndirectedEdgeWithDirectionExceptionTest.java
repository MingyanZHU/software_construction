package exception;

import org.apache.log4j.Logger;
import org.junit.Test;

public class UndirectedEdgeWithDirectionExceptionTest {

  private static final Logger LOGGER = Logger.getLogger(UndirectedEdgeWithDirectionException.class);

  @Test
  public void testException() {
    try {
      throw new UndirectedEdgeWithDirectionException("Test Exception");
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }

    try {
      throw new UndirectedEdgeWithDirectionException();
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }
  }
}