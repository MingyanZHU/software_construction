package edge;

import exception.IllegalEdgeParamsException;
import exception.IllegalParamsNumberException;
import exception.InputFileAgainException;
import java.util.List;
import java.util.Objects;
import vertex.Actor;
import vertex.Movie;
import vertex.Vertex;

/**
 * Describe the interaction between actors and movies if actor has been in the movie. The weight of
 * edge is the order of characters.
 *
 * @author Zhu Mingyan
 */
public class MovieActorRelation extends UndirectedEdge {

  /**
   * Edge in Movie Graph which means that the actor has been played in this film.
   *
   * @param label non-string label
   * @param weight requires weight > 0
   * @throws InputFileAgainException if weight <= 0
   */
  public MovieActorRelation(String label, double weight) throws InputFileAgainException {
    super(label, weight);
    if (weight <= 0) {
      throw new IllegalEdgeParamsException("Weight of Movie Actor Relation is less than 0!");
    }
  }

  @Override
  protected void checkRep() throws InputFileAgainException {
    //    assert this.getWeight() > 0;
    super.checkRep();
  }

  /**
   * Fill vertices in MovieActorRelation and one of vertices should be an instance of Class Actor
   * and the other one should be an instance of Class Movie.
   *
   * @return true if add vertices successfully, otherwise false
   */
  @Override
  public boolean addVertices(List<Vertex> vertices) throws InputFileAgainException {
    if (vertices == null) {
      throw new IllegalEdgeParamsException("Add vertices into MovieActorRelation with null args");
    }
    if (vertices.size() != 2) {
      throw new IllegalParamsNumberException("Add vertices into MovieActorRelation with "
          + vertices.size() + " params");
    }
    //    assert vertices.size() == 2 && (
    //        (vertices.get(0) instanceof Actor && vertices.get(1) instanceof Movie)
    //            || (vertices.get(0) instanceof Movie && vertices.get(1) instanceof Actor));
    if (!((vertices.get(0) instanceof Actor && vertices.get(1) instanceof Movie)
        || (vertices.get(0) instanceof Movie && vertices.get(1) instanceof Actor))) {
      throw new IllegalEdgeParamsException("Add vertices into MovieActorRelation with "
          + vertices.get(0).getClass().getSimpleName() + " and "
          + vertices.get(1).getClass().getSimpleName());
    }
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
