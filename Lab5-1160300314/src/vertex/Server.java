package vertex;

import exception.IllegalVertexParamsException;
import java.util.Objects;

/**
 * Represents the servers in network.
 *
 * @author Zhu Mingyan
 */
public class Server extends NetworkEquipment {

  public Server(String label) throws IllegalVertexParamsException {
    super(label);
    super.checkRep();
  }

  @Override
  public String toString() {
    return "Server{" + "hostname='" + getLabel() + '\''
        + ", IP='" + getIp() + '\'' + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Server)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    Server server = (Server) o;
    return getIp().equals(server.getIp());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), "Server");
  }
}
