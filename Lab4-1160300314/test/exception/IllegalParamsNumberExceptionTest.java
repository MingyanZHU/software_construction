package exception;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

public class IllegalParamsNumberExceptionTest {

  private static final Logger LOGGER = Logger.getLogger(IllegalParamsNumberException.class);

  @Test
  public void testException() {
    try {
      throw new IllegalParamsNumberException("Test Exception");
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }

    try {
      throw new IllegalParamsNumberException(new Exception("Test Exception"));
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }

    try {
      throw new IllegalParamsNumberException();
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }
  }
}