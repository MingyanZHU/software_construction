package edge;

import exception.IllegalEdgeParamsException;
import vertex.Actor;
import vertex.Movie;
import vertex.Vertex;

import java.util.List;
import java.util.Objects;

/**
 * Describe the interaction between actors and movies if actor has been in the movie.
 * The weight of edge is the order of characters.
 *
 * @author Zhu Mingyan
 */
public class MovieActorRelation extends UndirectedEdge {
    public MovieActorRelation(String label, double weight) throws IllegalEdgeParamsException {
        super(label, weight);
        if (weight <= 0) {
            throw new IllegalEdgeParamsException("Weight of Movie Actor Relation is less than 0!");
        }
    }

    @Override
    protected void checkRep() throws IllegalEdgeParamsException {
        assert this.getWeight() > 0;
        super.checkRep();
    }

    /**
     * Fill vertices in MovieActorRelation and one of vertices should be an instance of Class Actor and the
     * other one should be an instance of Class Movie
     *
     * @param vertices
     * @return true if add vertices successfully, otherwise false
     */
    @Override
    public boolean addVertices(List<Vertex> vertices) throws IllegalEdgeParamsException {
        assert vertices.size() == 2 && ((vertices.get(0) instanceof Actor && vertices.get(1) instanceof Movie)
                || (vertices.get(0) instanceof Movie && vertices.get(1) instanceof Actor));
        return super.addVertices(vertices);
    }

    @Override
    public String toString() {
        return "MovieActorRelation" + super.toString().replace("UndirectedEdge", "");
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof MovieActorRelation && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), "MovieActorRelation");
    }
}
