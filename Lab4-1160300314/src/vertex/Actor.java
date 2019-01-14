package vertex;

import exception.IllegalVertexParamsException;
import java.util.Objects;

/**
 * Describe the Actor with his/her name, age and gender.
 *
 * @author Zhu Mingyan
 */
public class Actor extends CastMember {

  public Actor(String label) throws IllegalVertexParamsException {
    super(label);
    super.checkRep();
  }

  @Override
  public String toString() {
    return "Actor{"
        + "name='" + getLabel() + '\''
        + ", age=" + getAge() + ", gender='" + getGender() + '\'' + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Actor)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Actor actor = (Actor) o;
    return getAge() == actor.getAge() && getGender().equals(actor.getGender());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), "Actor");
  }
}
