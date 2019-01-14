package vertex;

import exception.ContinueRunningException;
import exception.IllegalParamsNumberException;
import exception.IllegalStateOpeationException;
import exception.IllegalVertexParamsException;
import exception.InputFileAgainException;
import factory.LoggerFactory;
import java.util.Objects;
import memento.Caretaker;
import memento.Memento;
import org.apache.log4j.Logger;
import state.Active;
import state.State;
import state.UserState;

/**
 * Describe a person with his/her name, the gender and age of him/her.
 *
 * @author Zhu Mingyan
 */
public class Person extends Vertex {

  /*
  AF: Represents an Person node of Social Network.
  RI: The gender of any Person instance is 'M' or 'F'.
      The age of any Person instance is an integer above 0.
  Safety for Rep Exposure:
      The other fields except label of Class Person are all modified by key word private.
      Clients only access by getter function and no other setter function except fillVertexInfo
      which is strictly limited.
   */
  private String gender = "M";
  private int age = 18;
  private UserState state = Active.instance;
  private Caretaker caretaker = new Caretaker();
  private static final Logger LOGGER = LoggerFactory.createLogger(Person.class);

  /**
   * Constructor of Person with string label.
   *
   * @param label non-null string
   * @throws IllegalVertexParamsException if label is non-null or empty string.
   */
  public Person(String label) throws IllegalVertexParamsException {
    super(label);
    super.checkRep();
    save();
  }

  @Override
  protected void checkRep() throws IllegalVertexParamsException {
    super.checkRep();
    if (gender == null || (!gender.equals("M") && !gender.equals("F")) || age <= 0) {
      throw new IllegalVertexParamsException("Params of Person is wrong!");
    }
    assert gender.equals("M") || gender.equals("F");
    //     Gender of Person instance is 'M' or 'F'.
    assert age > 0;
  }

  /**
   * Fill Person vertex information with an array of strings with two strings, which are gender and
   * age in order.
   *
   * @param args String array
   */
  @Override
  public void fillVertexInfo(String[] args) throws InputFileAgainException {
    if (args == null) {
      throw new IllegalParamsNumberException("Fill Vertex in Social netword with a null args!");
    }
    if (args.length != 2) {
      throw new IllegalParamsNumberException(
          "Fill Vertex in Social network with " + args.length + " param(s)");
    }
    this.gender = args[0];
    //this.age = Integer.valueOf(args[1]);
    this.age = Integer.parseInt(args[1]);
    checkRep();
  }

  /**
   * Get the person gender.
   *
   * @return 'M' or 'F'
   */
  public String getGender() {
    return gender;
  }

  /**
   * Get the age of the person.
   *
   * @return an integer describing its age.
   */
  public int getAge() {
    return age;
  }

  @Override
  public String toString() {
    return "Person{" + "name='" + getLabel() + '\'' + ", gender='" + gender + '\''
        + ", age=" + age + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Person)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Person person = (Person) o;
    return age == person.age
        && gender.equals(person.gender);
  }

  @Override
  public int hashCode() {

    return Objects.hash(super.hashCode(), gender, age, "Person");
  }

  public void lock() {
    this.state = state.lock();
    this.save();
  }

  public void unlock() {
    this.state = state.unlock();
    this.save();
  }

  /**
   * Change state of Person from active to deactive.
   */
  public void deactive() {
    try {
      this.state = state.deactive();
      this.save();
    } catch (IllegalStateOpeationException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  /**
   * Change state of Person from deactive to active.
   */
  public void active() {
    try {
      this.state = state.active();
      this.save();
    } catch (IllegalStateOpeationException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  public State getState() {
    return state;
  }

  /**
   * Save state and time stamp in memento.
   */
  public void save() {
    caretaker.save(new Memento<>(this.state));
  }

  /**
   * Restore to the latest state.
   *
   * @return the latest state.
   */
  public State restore() {
    try {
      this.state = (UserState) this.caretaker.restore().getState();
    } catch (ContinueRunningException e) {
      LOGGER.error(e.getMessage(), e);
    }
    return this.state;
  }

  /**
   * Restore to the i-th latest state.
   *
   * @param i the i-th
   * @return the i-th latest state.
   */
  public State restore(int i) {
    try {
      this.state = (UserState) this.caretaker.restore(i).getState();
    } catch (ContinueRunningException e) {
      LOGGER.error(e.getMessage(), e);
    }
    return this.state;
  }

  public String outputMementos() {
    return this.caretaker.toString();
  }
}
