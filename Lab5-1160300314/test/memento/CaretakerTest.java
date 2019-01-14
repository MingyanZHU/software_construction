package memento;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import exception.ContinueRunningException;
import factory.LoggerFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import state.Close;
import state.Open;

public class CaretakerTest {

  private static final Logger LOGGER = LoggerFactory.createLogger(CaretakerTest.class);
  /*
  Test strategy
  Partition for inputs of restore()
      there are more than one mementos before, there is only one memento, empty mementos
  Partition for inputs of save(memento)
      memento: null, normal mementos
   */

  @Test
  public void testSave() {
    Caretaker caretaker = new Caretaker();

    caretaker.save(null);

    assertEquals("", caretaker.toString());

    caretaker.save(new Memento(Open.instance));

    assertThat(caretaker.toString(), containsString(Open.instance.toString()));
  }

  @Test
  public void restore() {
    Caretaker caretaker = new Caretaker();

    try {
      assertNull(caretaker.restore());
    } catch (ContinueRunningException e) {
      LOGGER.error(e.getMessage(), e);
    }

    caretaker.save(new Memento(Open.instance));
    caretaker.save(new Memento(Close.instance));

    try {
      assertEquals(Close.instance, caretaker.restore().getState());
    } catch (ContinueRunningException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

}