package exception;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

public class LackVerticesHyperEdgeExceptionTest {

  private static final Logger LOGGER = Logger.getLogger(LackVerticesHyperEdgeException.class);

  @Test
  public void testException() {
    try {
      throw new LackVerticesHyperEdgeException("Test Exception");
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }

    try {
      throw new LackVerticesHyperEdgeException(new Exception("Test Exception"));
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }

    try {
      throw new LackVerticesHyperEdgeException();
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }
  }
}