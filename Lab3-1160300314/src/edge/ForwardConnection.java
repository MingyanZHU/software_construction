package edge;

import exception.IllegalEdgeParamsException;
import exception.IllegalVertexParamsException;
import exception.IllegalVertexTypeException;
import exception.InputFileAgainException;
import vertex.Person;
import vertex.Vertex;

import java.util.List;
import java.util.Objects;

/***
 * Describe the interaction between two persons if the former had forwarded Weibo of the latter.
 * And the weight of the edge indicates how often the two interact.
 *
 * @author Zhu Mingyan
 */
public class ForwardConnection extends DirectedEdge {
    public ForwardConnection(String label, double weight) throws IllegalEdgeParamsException {
        super(label, weight);
        if (weight <= 0 || weight > 1) {
            throw new IllegalEdgeParamsException("Weight of Forward Connection is less than 0!");
        }
    }

    @Override
    protected void checkRep() throws IllegalEdgeParamsException {
//        assert this.getWeight() > 0;
        super.checkRep();
    }

    /**
     * Fill in ForwardConnection and the size of vertices should be 2. All vertices should be an instance of Person.
     * ForwardConnection should not be a loop.
     *
     * @param vertices
     * @return true if add vertices successfully, otherwise false
     */
    @Override
    public boolean addVertices(List<Vertex> vertices) throws InputFileAgainException {
        if (vertices == null) {
            throw new IllegalVertexParamsException("Add vertices into ForwardConnection with null vertices!");
        }
        if (vertices.size() != 2) {
            throw new IllegalVertexParamsException("Add vertices into ForwardConnection with " + vertices.size() + " vertices");
        }
//        assert vertices.size() == 2;
        boolean answer = true;
        for (Vertex vertex : vertices) {
            answer = answer && vertex instanceof Person;
            if (!answer) {
                throw new IllegalVertexTypeException("Add " + (vertex == null ? "null" : vertex.getClass().getSimpleName()) + " vertex into ForwardConnection!");
            }
        }
        return answer && super.addVertices(vertices);
    }

    @Override
    public String toString() {
        return "ForwardConnection" + super.toString().replace("DirectedEdge", "");
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ForwardConnection)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), "ForwardConnection");
    }
}
