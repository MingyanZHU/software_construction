package edge;

import exception.IllegalEdgeParamsException;
import exception.InputFileAgainException;
import vertex.Person;
import vertex.Vertex;

import java.util.List;
import java.util.Objects;

/***
 * Describe the interaction between two persons if the former had commented Weibo of the latter.
 * And the weight of the edge indicates how often the two interact.
 *
 * @author Zhu Mingyan
 */
public class CommentConnection extends DirectedEdge {
    public CommentConnection(String label, double weight) throws IllegalEdgeParamsException {
        super(label, weight);
        if (weight <= 0) {
            throw new IllegalEdgeParamsException("Weight of Comment Connection is less than 0!");
        }
    }

    @Override
    protected void checkRep() throws IllegalEdgeParamsException {
        assert this.getWeight() > 0;
        super.checkRep();
    }

    /**
     * Fill in CommentConnection and the size of vertices should be 2. All vertices should be an instance of Person.
     * CommentConnection should not be a loop.
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
        return "CommentConnection" + super.toString().replace("DirectedEdge", "");
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CommentConnection)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), "CommentConnection");
    }
}
