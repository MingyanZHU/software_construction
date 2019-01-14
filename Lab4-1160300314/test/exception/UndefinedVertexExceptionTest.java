package exception;

import static org.junit.Assert.*;

import org.apache.log4j.Logger;
import org.junit.Test;

public class UndefinedVertexExceptionTest {

  private static final Logger LOGGER = Logger.getLogger(UndefinedVertexException.class);

  @Test
  public void testException() {
    try {
      throw new UndefinedVertexException("Test Exception");
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }

    try {
      throw new UndefinedVertexException(new Exception("Test Exception"));
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }

    try {
      throw new UndefinedVertexException();
    } catch (Exception e) {
      LOGGER.debug(e.getMessage());
    }
  }
}