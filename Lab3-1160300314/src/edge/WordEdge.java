package edge;

import exception.IllegalEdgeParamsException;
import exception.IllegalVertexParamsException;
import exception.IllegalVertexTypeException;
import exception.InputFileAgainException;
import vertex.Vertex;
import vertex.Word;

import java.util.List;
import java.util.Objects;

/**
 * Describe an interaction between two words if the former word is next to the latter word in a text
 *
 * @author Zhu Mingyan
 */
public class WordEdge extends DirectedEdge {
    public WordEdge(String label, double weight) throws IllegalEdgeParamsException {
        super(label, weight);
        if (weight <= 0) {
            throw new IllegalEdgeParamsException("Weight of WordEdge is less than 0");
        }
    }

    @Override
    protected void checkRep() throws IllegalEdgeParamsException {
//        assert this.getWeight() > 0;
        super.checkRep();
    }

    /**
     * Add vertices in wordEdge. All vertex in list should be an instance of Word.
     *
     * @param vertices
     * @return true if add vertices successfully, otherwise false
     * TODO @throws
     */
    @Override
    public boolean addVertices(List<Vertex> vertices) throws InputFileAgainException {
        if (vertices == null) {
            throw new IllegalVertexParamsException("Add vertices into WordEdge with null vertices!");
        }
        if (vertices.size() != 2 && vertices.size() != 1) {
            throw new IllegalVertexParamsException("Add vertices into WordEdge with " + vertices.size() + " vertices");
        }
        boolean answer = true;
        for (Vertex vertex : vertices) {
            answer = answer && vertex instanceof Word;
            if (!answer) {
                throw new IllegalVertexTypeException("Add " + (vertex == null ? "null" : vertex.getClass().getSimpleName())
                        + " vertex into WordEdge!");
            }
        }
        return answer && super.addVertices(vertices);
    }

    @Override
    public String toString() {
        return "WordEdge" + super.toString().replace("DirectedEdge", "");
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof WordEdge)) return false;
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), "WordEdge");
    }
}
