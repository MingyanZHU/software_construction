package vertex;

import exception.IllegalVertexParamsException;
import java.util.Objects;

/**
 * Describe a director with his/her name, age and gender.
 *
 * @author Zhu Mingyan
 */
public class Director extends CastMember {

  public Director(String label) throws IllegalVertexParamsException {
    super(label);
    super.checkRep();
  }

  @Override
  public String toString() {
    return "Director{" + "name='" + getLabel() + '\'' + ", age=" + getAge()
        + ", gender='" + getGender() + '\'' + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Director)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Director director = (Director) o;
    return getAge() == director.getAge()
        && getGender().equals(director.getGender());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), "Director");
  }
}
