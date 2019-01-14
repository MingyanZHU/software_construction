package exception;

import factory.LoggerFactory;
import org.apache.log4j.Logger;
import org.junit.Test;

public class DuplicateLabelsExceptionTest {

  private static final Logger LOGGER = LoggerFactory.createLogger(
      DuplicateLabelsExceptionTest.class);

  @Test
  public void testException() {
    try {
      throw new DuplicateLabelsException("Test DuplicateLabels Exception");
    } catch (DuplicateLabelsException e) {
      LOGGER.debug(e.getMessage());
    }

    try {
      throw new DuplicateLabelsException(new Exception("Test DuplicateLabels Exception throws"));
    } catch (DuplicateLabelsException e) {
      LOGGER.debug(e.getMessage());
    }

    try {
      throw new DuplicateLabelsException();
    } catch (DuplicateLabelsException e) {
      LOGGER.debug(e.getMessage());
    }
  }
}