package edge;

import exception.IllegalEdgeParamsException;
import vertex.Actor;
import vertex.Vertex;

import java.util.List;
import java.util.Objects;

/**
 * Describe the interaction among actors if they acted in the same movie.
 *
 * @author Zhu Mingyan
 */
public class SameMovieHyperEdge extends HyperEdge {
    public SameMovieHyperEdge(String label, double weight) throws IllegalEdgeParamsException {
        super(label, weight);

    }

    /**
     * Fill vertices in SameMovieHyperEdge and all vertex in vertices should be an instance of Actor.
     *
     * @param vertices
     * @return true if add vertices successfully, otherwise false.
     */
    @Override
    public boolean addVertices(List<Vertex> vertices) throws IllegalEdgeParamsException {
        boolean answer = true;
//        super.checkRep();
        for (Vertex vertex : vertices) {
            answer = answer && vertex instanceof Actor;
        }
        return answer && super.addVertices(vertices);
    }

    @Override
    public String toString() {
        return "SameMovieHyperEdge" + super.toString().replace("HyperEdge", "");
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof SameMovieHyperEdge && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), "SameMovieHyperEdge");
    }
}
