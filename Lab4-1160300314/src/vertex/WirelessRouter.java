package vertex;

import exception.IllegalVertexParamsException;
import java.util.Objects;

/**
 * Represents the wireless  routers in network.
 *
 * @author Zhu Mingyan
 */
public class WirelessRouter extends NetworkEquipment {

  public WirelessRouter(String label) throws IllegalVertexParamsException {
    super(label);
    super.checkRep();
  }

  @Override
  public String toString() {
    return "WirelessRouter{" + "hostname='" + getLabel() + '\''
        + ", IP='" + getIp() + '\'' + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof WirelessRouter)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    WirelessRouter router = (WirelessRouter) o;
    return this.getIp().equals(router.getIp());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), "WirelessRouter");
  }
}
