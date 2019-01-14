package edge;

import exception.IllegalEdgeParamsException;
import exception.IllegalVertexParamsException;
import exception.IllegalVertexTypeException;
import exception.InputFileAgainException;
import java.util.List;
import java.util.Objects;
import vertex.Person;
import vertex.Vertex;

/**
 * Describe the interaction between two persons if the former followed the latter. And the weight of
 * the edge indicates how often the two interact.
 *
 * @author Zhu Mingyan
 */
public class FriendTie extends DirectedEdge {

  /**
   * Constructor of FriendTie with label and weight of it.
   *
   * @param label non-null string
   * @param weight requires weight > 0 && weight <= 1
   * @throws InputFileAgainException when weight <=0 or weight > 1
   */

  public FriendTie(String label, double weight) throws InputFileAgainException {
    super(label, weight);
    if (weight <= 0 || weight > 1) {
      throw new IllegalEdgeParamsException("Weight of Friend Connection is less than 0!");
    }
  }

  @Override
  protected void checkRep() throws InputFileAgainException {
    super.checkRep();
    //        assert this.getWeight() > 0;
  }

  /**
   * Fill in FriendTie and the size of vertices should be 2. All vertices should be an instance of
   * Person. FriendTie should not be a loop.
   *
   * @return true if add vertices successfully, otherwise false
   */
  @Override
  public boolean addVertices(List<Vertex> vertexList) throws InputFileAgainException {
    if (vertexList == null) {
      throw new IllegalVertexParamsException("Add vertices int FriendTie with null vertices");
    }
    if (vertexList.size() != 2) {
      throw new IllegalVertexParamsException(
          "Add vertices into FriendTie with " + vertexList.size() + " params!");
    }
    //        assert vertices.size() == 2;
    boolean answer = true;
    for (Vertex vertex : vertexList) {
      answer = answer && vertex instanceof Person;
      if (!answer) {
        throw new IllegalVertexTypeException(
            "Add " + (vertex == null ? "null" : vertex.getClass().getSimpleName())
                + " into FriendTie");
      }
    }
    return answer && super.addVertices(vertexList);
  }

  @Override
  public String toString() {
    return "FriendTie" + super.toString().replace("DirectedEdge", "");
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof FriendTie)) {
      return false;
    }
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), "FriendTie");
  }
}
