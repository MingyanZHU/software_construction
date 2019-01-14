package edge;

import exception.IllegalEdgeParamsException;
import vertex.Director;
import vertex.Movie;
import vertex.Vertex;

import java.util.List;
import java.util.Objects;

/**
 * Describe the interaction between directors and movies if director has directed the movie.
 *
 * @author Zhu Mingyan
 */
public class MovieDirectorRelation extends UndirectedEdge {
    public MovieDirectorRelation(String label, double weight) throws IllegalEdgeParamsException {
        super(label, weight);
        if (Math.abs(weight + 1) >= 0.0001) {
            throw new IllegalEdgeParamsException("Weight of Movie Director Relation is not equal to -1!");
        }
    }

    /**
     * Fill vertices of MovieDirectorRelation and one of vertices should be movie instance and the
     * other one should be Director instance.
     *
     * @param vertices
     * @return true if add vertices successfully, otherwise false
     */
    @Override
    public boolean addVertices(List<Vertex> vertices) throws IllegalEdgeParamsException {
        assert vertices.size() == 2 && ((vertices.get(0) instanceof Director && vertices.get(1) instanceof Movie)
                || (vertices.get(0) instanceof Movie && vertices.get(1) instanceof Director));
        return super.addVertices(vertices);
    }

    @Override
    protected void checkRep() throws IllegalEdgeParamsException {
        assert Double.doubleToLongBits(getWeight()) == Double.doubleToLongBits(-1);
        super.checkRep();
    }

    @Override
    public String toString() {
        return "MovieDirectorRelation" + super.toString().replace("UndirectedEdge", "");
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MovieDirectorRelation && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), "MovieDirectorRelation");
    }
}
