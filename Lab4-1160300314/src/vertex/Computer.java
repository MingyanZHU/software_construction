package vertex;

import exception.IllegalVertexParamsException;
import java.util.Objects;

/**
 * Represents the Computers in network.
 *
 * @author Zhu Mingyan
 */
public class Computer extends NetworkEquipment {

  public Computer(String label) throws IllegalVertexParamsException {
    super(label);
    super.checkRep();
  }

  @Override
  public String toString() {
    return "Computer{" + "hostname='" + getLabel() + '\'' + ", IP='" + getIp() + '\'' + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Computer)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Computer computer = (Computer) o;
    return getIp().equals(computer.getIp());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), "Computer");
  }
}
