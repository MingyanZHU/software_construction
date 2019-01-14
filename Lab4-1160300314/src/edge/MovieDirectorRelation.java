package edge;

import exception.IllegalEdgeParamsException;
import exception.InputFileAgainException;
import java.util.List;
import java.util.Objects;
import vertex.Director;
import vertex.Movie;
import vertex.Vertex;

/**
 * Describe the interaction between directors and movies if director has directed the movie.
 *
 * @author Zhu Mingyan
 */
public class MovieDirectorRelation extends UndirectedEdge {

  /**
   * Edge in Movie Graph which means that the director directed the film.
   *
   * @param label non-null string
   * @param weight should be -1
   * @throws InputFileAgainException if weight is not equals -1.
   */
  public MovieDirectorRelation(String label, double weight) throws InputFileAgainException {
    super(label, weight);
    // if (Math.abs(weight + 1) >= 0.0001) {
    // throw new IllegalEdgeParamsException("Weight of Movie Director Relation is not equal to -1!"
    // + " and is " + weight);
    // }
  }

  /**
   * Fill vertices of MovieDirectorRelation and one of vertices should be movie instance and the
   * other one should be Director instance.
   *
   * @return true if add vertices successfully, otherwise false
   * @throws InputFileAgainException if vertices == null or number of vertex in vertices is not
   *         equals 2 or there is not a Director and Movie.
   */
  @Override
  public boolean addVertices(List<Vertex> vertices) throws InputFileAgainException {
    if (vertices == null) {
      throw new IllegalEdgeParamsException("Add vertices into MovieDirectorRelation with"
          + " null vertices");
    }
    if (vertices.size() != 2) {
      throw new IllegalEdgeParamsException("Add vertices into MovieDirectorRelation with "
          + vertices.size() + " params");
    }
    if (!((vertices.get(0) instanceof Director && vertices.get(1) instanceof Movie)
        || (vertices.get(0) instanceof Movie && vertices.get(1) instanceof Director))) {
      throw new IllegalEdgeParamsException("Add vertices into MovieDirectorRelation with "
          + vertices.get(0).getClass().getSimpleName() + " and "
          + vertices.get(1).getClass().getSimpleName());
    }
    //    assert vertices.size() == 2 && (
    //        (vertices.get(0) instanceof Director && vertices.get(1) instanceof Movie)
    //            || (vertices.get(0) instanceof Movie && vertices.get(1) instanceof Director));
    return super.addVertices(vertices);
  }

  @Override
  protected void checkRep() throws InputFileAgainException {
    // if (Math.abs(this.getWeight() + 1) > 0.00001) {
    // throw new IllegalEdgeParamsException("MovieDirectorRelation is non-weight edge and can not "
    // + " has " + this.getWeight() + " weight");
    // }
    //    assert Double.doubleToLongBits(getWeight()) == Double.doubleToLongBits(-1);
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
