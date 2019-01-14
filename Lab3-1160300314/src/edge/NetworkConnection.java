package edge;

import exception.IllegalEdgeParamsException;
import vertex.NetworkEquipment;
import vertex.Vertex;

import java.util.List;
import java.util.Objects;

/**
 * Describe the network connection between two network equipments in network.
 * And the weight of the edge indicates the bandwidth of the network connection.
 *
 * @author Zhu Mingyan
 */
public class NetworkConnection extends UndirectedEdge {
    public NetworkConnection(String label, double weight) throws IllegalEdgeParamsException {
        super(label, weight);
        if(weight <= 0){
            throw new IllegalEdgeParamsException("Weight of Network Connection is less than 0!");
        }
    }

    @Override
    protected void checkRep() throws IllegalEdgeParamsException {
        assert this.getWeight() > 0;
        super.checkRep();
    }

    /**
     * Fill vertices of network connection and vertices should consist of two different network equipments.
     *
     * @param vertices
     * @return true if add vertices successfully, otherwise false
     */
    @Override
    public boolean addVertices(List<Vertex> vertices) throws IllegalEdgeParamsException {
        assert vertices.size() == 2 && !(vertices.get(0).equals(vertices.get(1)));
        // The loop of Network Connection is not valid.
        boolean answer = true;
        for (Vertex vertex : vertices) {
            answer = answer && vertex instanceof NetworkEquipment;
        }
        return answer && super.addVertices(vertices);
    }

    @Override
    public String toString() {
        return "NetworkConnection" + super.toString().replace("UndirectedEdge", "");
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof NetworkConnection && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), "NetworkConnection");
    }
}
