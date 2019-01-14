package edge;

import exception.IllegalVertexTypeException;
import exception.InputFileAgainException;
import java.util.List;
import java.util.Objects;
import vertex.Actor;
import vertex.Vertex;

/**
 * Describe the interaction among actors if they acted in the same movie.
 *
 * @author Zhu Mingyan
 */
public class SameMovieHyperEdge extends HyperEdge {

  public SameMovieHyperEdge(String label, double weight) throws InputFileAgainException {
    super(label, weight);

  }

  /**
   * Fill vertices in SameMovieHyperEdge and all vertex in vertices should be an instance of Actor.
   *
   * @return true if add vertices successfully, otherwise false.
   */
  @Override
  public boolean addVertices(List<Vertex> vertices) throws InputFileAgainException {
    boolean answer;
    //super.checkRep();
    if (vertices == null) {
      throw new IllegalVertexTypeException("Add vertices into SameMovieHyperEdge "
          + "with null vertices");
    }
    for (Vertex vertex : vertices) {
      answer = vertex instanceof Actor;
      if (!answer) {
        throw new IllegalVertexTypeException("Add " + (vertex == null ? "null"
            : vertex.getClass().getSimpleName()) + " into SameMovieHyperEdge");
      }
    }
    return super.addVertices(vertices);
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
