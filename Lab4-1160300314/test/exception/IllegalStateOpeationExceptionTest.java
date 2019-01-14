package exception;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

public class IllegalStateOpeationExceptionTest {

  private static final Logger LOGGER = Logger.getLogger(IllegalStateOpeationException.class);

  @Test
  public void testException() {
    try {
      throw new IllegalStateOpeationException("Test Exception");
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }

    try {
      throw new IllegalStateOpeationException(new Exception("Test Exception"));
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }

    try {
      throw new IllegalStateOpeationException();
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }
  }
}