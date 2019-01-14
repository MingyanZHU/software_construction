package edge;

import exception.IllegalEdgeParamsException;
import exception.InputFileAgainException;
import vertex.Person;
import vertex.Vertex;

import java.util.List;
import java.util.Objects;

/**
 * Describe the interaction between two persons if the former followed the latter.
 * And the weight of the edge indicates how often the two interact.
 *
 * @author Zhu Mingyan
 */
public class FriendConnection extends DirectedEdge {
    public FriendConnection(String label, double weight) throws IllegalEdgeParamsException {
        super(label, weight);
        if(weight <= 0){
            throw new IllegalEdgeParamsException("Weight of Friend Connection is less than 0!");
        }
    }

    @Override
    protected void checkRep() throws IllegalEdgeParamsException {
        super.checkRep();
        assert this.getWeight() > 0;
    }

    /**
     * Fill in FriendConnection and the size of vertices should be 2. All vertices should be an instance of Person.
     * FriendConnection should not be a loop.
     *
     * @param vertices
     * @return true if add vertices successfully, otherwise false
     */
    @Override
    public boolean addVertices(List<Vertex> vertices) throws InputFileAgainException {
        assert vertices.size() == 2;
        boolean answer = true;
        for (Vertex vertex : vertices) {
            answer = answer && vertex instanceof Person;
        }
        return answer && super.addVertices(vertices);
    }

    @Override
    public String toString() {
        return "FriendConnection" + super.toString().replace("DirectedEdge", "");
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof FriendConnection)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), "FriendConnection");
    }
}
