package edge;

import exception.IllegalEdgeParamsException;
import exception.IllegalVertexParamsException;
import exception.IllegalVertexTypeException;
import exception.InputFileAgainException;
import java.util.List;
import java.util.Objects;
import vertex.NetworkEquipment;
import vertex.Vertex;

/**
 * Describe the network connection between two network equipments in network. And the weight of the
 * edge indicates the bandwidth of the network connection.
 *
 * @author Zhu Mingyan
 */
public class NetworkConnection extends UndirectedEdge {

  /**
   * Edge in Network Topology which represents a connection between two equipments in network.
   * @param label non-null string
   * @param weight requires weight > 0
   * @throws InputFileAgainException if weight <= 0
   */
  public NetworkConnection(String label, double weight) throws InputFileAgainException {
    super(label, weight);
    if (weight <= 0) {
      throw new IllegalEdgeParamsException("Weight of Network Connection is less than 0!");
    }
  }

  @Override
  protected void checkRep() throws InputFileAgainException {
    //        assert this.getWeight() > 0;
    super.checkRep();
  }

  /**
   * Fill vertices of network connection and vertices should consist of two different network
   * equipments.
   *
   * @return true if add vertices successfully, otherwise false
   */
  @Override
  public boolean addVertices(List<Vertex> vertexList) throws InputFileAgainException {
    if (vertexList == null) {
      throw new IllegalVertexParamsException(
          "Fill vertices into NetworkConnection with null vertices");
    }
    if (vertexList.size() != 2) {
      throw new IllegalVertexParamsException(
          "Fill vertices into NetworkConnection with " + vertexList.size() + " params!");
    }
    if (vertexList.get(0).equals(vertexList.get(1))) {
      throw new IllegalVertexParamsException("NetworkConnection can not be a loop!");
    }
    //        assert vertices.size() == 2 && !(vertices.get(0).equals(vertices.get(1)));
    // The loop of Network Connection is not valid.
    boolean answer = true;
    for (Vertex vertex : vertexList) {
      answer = answer && vertex instanceof NetworkEquipment;
      if (!answer) {
        throw new IllegalVertexTypeException("Can not add " + (vertex == null ? "null" :
            vertex.getClass().getSimpleName()) + " into NetworkConnection!");
      }
    }
    return answer && super.addVertices(vertexList);
  }

  @Override
  public String toString() {
    return "NetworkConnection" + super.toString().replace("UndirectedEdge", "");
  }

  @Override
  public boolean equals(Object o) {
    return o instanceof NetworkConnection && super.equals(o);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), "NetworkConnection");
  }
}
