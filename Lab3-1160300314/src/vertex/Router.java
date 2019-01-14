package vertex;

import exception.IllegalVertexParamsException;

import java.util.Objects;

/**
 * Represents the routers in network
 *
 * @author Zhu Mingyan
 */
public class Router extends NetworkEquipment {
    public Router(String label) throws IllegalVertexParamsException {
        super(label);
        super.checkRep();
    }

    @Override
    public String toString() {
        return "Router{" +
                "hostname='" + getLabel() + '\'' +
                ", IP='" + getIP() + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Router)) return false;
        if (!super.equals(o)) return false;
        Router router = (Router) o;
        return this.getIP().equals(router.getIP());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), "Router");
    }
}
