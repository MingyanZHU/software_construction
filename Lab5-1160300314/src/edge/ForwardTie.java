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
 * Describe the interaction between two persons if the former had forwarded Weibo of the latter.
 * And the weight of the edge indicates how often the two interact.
 *
 * @author Zhu Mingyan
 */
public class ForwardTie extends DirectedEdge {

  /**
   * Constructor of ForwardTie with label and weight of it.
   *
   * @param label non-null string
   * @param weight requires weight > 0 && weight <= 1
   * @throws InputFileAgainException when weight <=0 or weight > 1
   */
  public ForwardTie(String label, double weight) throws InputFileAgainException {
    super(label, weight);
    if (weight <= 0 || weight > 1) {
      throw new IllegalEdgeParamsException("Weight of Forward Connection is less than 0!");
    }
  }

  @Override
  protected void checkRep() throws InputFileAgainException {
    //        assert this.getWeight() > 0;
    super.checkRep();
  }

  /**
   * Fill in ForwardTie and the size of vertices should be 2. All vertices should be an instance of
   * Person. ForwardTie should not be a loop.
   *
   * @return true if add vertices successfully, otherwise false
   */
  @Override
  public boolean addVertices(List<Vertex> vertexList) throws InputFileAgainException {
    if (vertexList == null) {
      throw new IllegalVertexParamsException("Add vertices into ForwardTie with null vertices!");
    }
    if (vertexList.size() != 2) {
      throw new IllegalVertexParamsException(
          "Add vertices into ForwardTie with " + vertexList.size() + " vertices");
    }
    //        assert vertices.size() == 2;
    boolean answer = true;
    for (Vertex vertex : vertexList) {
      answer = answer && vertex instanceof Person;
      if (!answer) {
        throw new IllegalVertexTypeException(
            "Add " + (vertex == null ? "null" : vertex.getClass().getSimpleName())
                + " vertex into ForwardTie!");
      }
    }
    return answer && super.addVertices(vertexList);
  }

  @Override
  public String toString() {
    return "ForwardTie" + super.toString().replace("DirectedEdge", "");
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof ForwardTie)) {
      return false;
    }
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), "ForwardTie");
  }
}
