package exception;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

public class IllegalEdgeTypeExceptionTest {

  private static final Logger LOGGER = Logger.getLogger(IllegalEdgeTypeException.class);

  @Test
  public void testException() {
    try {
      throw new IllegalEdgeTypeException("Test Exception");
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }

    try {
      throw new IllegalEdgeTypeException(new Exception("Test Exception"));
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }

    try {
      throw new IllegalEdgeTypeException();
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }
  }
}