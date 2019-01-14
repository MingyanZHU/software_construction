package vertex;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import exception.InputFileAgainException;
import factory.LoggerFactory;
import factory.VertexFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import state.Active;
import state.Deactive;
import state.Locked;

public class PersonTest {

  /*
  Test Strategy
  Partition for inputs of fillVertexInfo(args)
      args: null, illegal args with wrong number params, exact number of params but params is wrong
              legal args.
  Partition for inputs of lock()
      from different state: Locked, Active, or Deactive state.
  Partition for inputs of unlock()
      from different state: Locked, Active, or Deactive state
  Partition for inputs of active()
      from different state: Locked, Active, or Deactive state
  Partition for inputs of deactive()
      from different state: Locked, Active, or Deactive state
  Partition for inputs of restore()
      there are more than one states before, there is only one state
  Partition for inputs of restore(i)
      i: larger than mementos, less than mementos number, 1, 0
  Partition for outputMementos()
      do not add other memento, add some other mementos
   */
  private static final Logger LOGGER = LoggerFactory.createLogger(PersonTest.class);
  private static final String type = "Person";

  @Test
  public void testPersonFillVertexInfo() {
    String label = "SmartRainy";
    String gender = "M";
    String genderW = "m";
    int age = 40;

    String[] args = null;
    String[] args1 = {gender};
    String[] args2 = {genderW, String.valueOf(age)};
    String[] args3 = {gender, String.valueOf(age)};

    try {
      Person person = (Person) VertexFactory.createVertex(label, type, args);
      assertEquals(gender, person.getGender());
      assertEquals(age, person.getAge());
    } catch (InputFileAgainException e) {
      LOGGER.error("Test Person fillVertex with a null args!", e);
    }

    try {
      Person person = new Person(label);
      person.fillVertexInfo(args1);

      assertEquals(gender, person.getGender());
      assertEquals(age, person.getAge());
    } catch (InputFileAgainException e) {
      LOGGER.error("Test Person fillVertexInfo with a wrong number of params!", e);
    }

    try {
      Person person = new Person(label);
      person.fillVertexInfo(args2);

      assertEquals(gender, person.getGender());
      assertEquals(age, person.getAge());
    } catch (InputFileAgainException e) {
      LOGGER.error("Test Person fillVertexInfo with a correct number but illegal params!", e);
    }

    try {
      Person person = (Person) VertexFactory.createVertex(label, type, args3);

      assertEquals(gender, person.getGender());
      assertEquals(age, person.getAge());
    } catch (InputFileAgainException e) {
      LOGGER.error("Test Person fillVertex with a legal args array!", e);
    }

  }

  @Test
  public void testPerson() {
    String label = "Perter";
    String gender = "M";
    int age = 20;
    String[] args = {gender, String.valueOf(age)};

    try {
      Person person = new Person(label);
      person.fillVertexInfo(args);

      assertEquals(gender, person.getGender());
      assertEquals(age, person.getAge());
    } catch (InputFileAgainException e) {
      LOGGER.error("Test Person Constructor with legal label!", e);
    }
  }

  @Test
  public void testOverride() {
    String label = "Perter";
    String gender = "M";
    int age = 20;
    String[] args = {gender, String.valueOf(age)};

    try {
      Person person1 = new Person(label);
      Person person2 = new Person(label);

      person1.fillVertexInfo(args);
      person2.fillVertexInfo(args);

      assertTrue(person1.equals(person1));
      assertTrue(person1.equals(person2));
      assertTrue(person2.equals(person1));

      assertTrue(person1.hashCode() == person2.hashCode());

      assertThat(person1.toString(), containsString("name='" + person1.getLabel() + '\''
          + ", gender='" + gender + '\'' + ", age=" + age));

    } catch (InputFileAgainException e) {
      LOGGER.error("Test Person with normal and legal args!");
    }
  }

  @Test
  public void testFactory() {
    String label = "Perter";
    String gender = "M";
    int age = 20;
    String type = "Person";
    String[] args = {gender, String.valueOf(age)};

    try {
      Person person = null;
      person = (Person) VertexFactory.createVertex(label, type, args);
      assertEquals(gender, person.getGender());
      assertEquals(age, person.getAge());
    } catch (InputFileAgainException e) {
      LOGGER.error("Test Person factory with a legal params array!", e);
    }
  }

  @Test
  public void testLock() {
    String label = "Perter";
    String gender = "M";
    int age = 20;
    String[] args = {gender, String.valueOf(age)};

    try {
      Person person = new Person(label);
      person.fillVertexInfo(args);

      assertEquals(Active.instance, person.getState());
      person.lock(); // lock() from Active
      assertEquals(Locked.instance, person.getState());

      person.lock(); // lock() from Locked
      assertEquals(Locked.instance, person.getState());

      person.unlock();
      assertEquals(Active.instance, person.getState());
      person.deactive();
      assertEquals(Deactive.instance, person.getState());
      person.lock();  // lock() from Deactive
      assertEquals(Locked.instance, person.getState());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testUnlock() {
    String label = "Perter";
    String gender = "M";
    int age = 20;
    String[] args = {gender, String.valueOf(age)};

    try {
      Person person = new Person(label);
      person.fillVertexInfo(args);

      assertEquals(Active.instance, person.getState());
      person.unlock(); // unlock() from Active
      assertEquals(Active.instance, person.getState());

      person.lock();
      assertEquals(Locked.instance, person.getState());
      person.unlock(); // unlock() from Locked
      assertEquals(Active.instance, person.getState());

      person.deactive();
      assertEquals(Deactive.instance, person.getState());
      person.unlock();    // unlock() from Deactive
      assertEquals(Deactive.instance, person.getState());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testActive() {
    String label = "Perter";
    String gender = "M";
    int age = 20;
    String[] args = {gender, String.valueOf(age)};

    try {
      Person person = new Person(label);
      person.fillVertexInfo(args);

      assertEquals(Active.instance, person.getState());
      person.active(); // active() from Active
      assertEquals(Active.instance, person.getState());

      person.deactive();
      assertEquals(Deactive.instance, person.getState());
      person.active(); // active() from Deactive
      assertEquals(Active.instance, person.getState());

      person.lock();
      assertEquals(Locked.instance, person.getState());
      person.active(); // active() from Locked
      assertEquals(Locked.instance, person.getState());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testDeactive() {
    String label = "Perter";
    String gender = "M";
    int age = 20;
    String[] args = {gender, String.valueOf(age)};

    try {
      Person person = new Person(label);
      person.fillVertexInfo(args);

      assertEquals(Active.instance, person.getState());
      person.deactive();
      assertEquals(Deactive.instance, person.getState());

      person.deactive();
      assertEquals(Deactive.instance, person.getState());

      person.lock();
      assertEquals(Locked.instance, person.getState());
      person.deactive();
      assertEquals(Locked.instance, person.getState());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testRestore() {
    String label = "Perter";
    String gender = "M";
    int age = 20;
    String[] args = {gender, String.valueOf(age)};

    try {
      Person person = new Person(label);
      person.fillVertexInfo(args);

      assertEquals(Active.instance, person.getState());
      assertEquals(Active.instance, person.restore()); // restore with any other mementos
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Person person = new Person(label);
      person.fillVertexInfo(args);

      assertEquals(Active.instance, person.getState());

      person.deactive();
      person.lock();

      assertEquals(Locked.instance, person.restore());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testRestoreWithParamI() {
    String label = "Perter";
    String gender = "M";
    int age = 20;
    String[] args = {gender, String.valueOf(age)};

    try {
      Person person = new Person(label);
      person.fillVertexInfo(args);

      assertEquals(Active.instance, person.getState());
      assertEquals(Active.instance, person.restore(1)); // restore(1)
      assertEquals(Active.instance, person.restore(0)); // restore(0)
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Person person = new Person(label);
      person.fillVertexInfo(args);

      assertEquals(Active.instance, person.getState());
      person.deactive();
      person.active();
      person.deactive();
      person.lock();

      assertEquals(Deactive.instance, person.restore(2)); // restore with legal param i
      assertEquals(Deactive.instance,
          person.restore(1000)); // restore with param i larger than number of mementos
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testOutputMementos() {
    String label = "Perter";
    String gender = "M";
    int age = 20;
    String[] args = {gender, String.valueOf(age)};

    try {
      Person person = new Person(label);
      person.fillVertexInfo(args);

      assertEquals(Active.instance, person.getState());
      assertThat(person.outputMementos(), containsString(Active.instance.toString()));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Person person = new Person(label);
      person.fillVertexInfo(args);

      assertEquals(Active.instance, person.getState());
      person.deactive();
      person.active();
      person.lock();
      person.unlock();
      assertThat(person.outputMementos(), containsString(Active.instance.toString()));
      assertThat(person.outputMementos(), containsString(Deactive.instance.toString()));
      assertThat(person.outputMementos(), containsString(Locked.instance.toString()));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}
