package edge;

import exception.IllegalEdgeParamsException;
import exception.IllegalParamsNumberException;
import exception.InputFileAgainException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import vertex.Vertex;

/**
 * Describe the hyper edge in graph.
 *
 * @author Zhu Mingyan
 */
public class HyperEdge extends Edge {

  /*
  AI: A subset of set of vertices in graph.
  RI: Set of hyper edge is non-empty and all vertices in this set is also non-null.
      The weight of hyper edge is -1 because it is an edge without weight.
  Safety for Rep Exposure:
      Set of vertices in hyper edge is modified by private and clients can not access change them
      except the addVertices which is a strictly limited function. And all getter function to get
      the vertex set using defensive copy for safety.
   */
  private Set<Vertex> vertexSet;

  /**
   * Constructor of HyperEdge with label and -1 weight.
   *
   * @param label non-null string
   * @param weight -1
   * @throws InputFileAgainException if weight is not equals -1.
   */
  public HyperEdge(String label, double weight) throws InputFileAgainException {
    super(label, weight);
    //    if (Math.abs(weight + 1) >= 0.0001) {
    //      throw new IllegalEdgeParamsException("Weight of HyperEdge is not equals -1");
    //    }
    super.checkRep();
  }

  @Override
  protected void checkRep() throws InputFileAgainException {
    super.checkRep();
    //        assert Double.doubleToLongBits(this.getWeight()) == Double.doubleToLongBits(-1);
    if (Double.doubleToLongBits(this.getWeight()) != Double.doubleToLongBits(-1)) {
      throw new IllegalEdgeParamsException("Weight of HyperEdge is not equals to -1!");
    }
    for (Vertex vertex : vertexSet) {
      //      assert vertex != null;
      if (vertex == null) {
        throw new IllegalEdgeParamsException("Some vertex in hyper edge is null!");
      }
    }
  }

  @Override
  public boolean addVertices(List<Vertex> vertices) throws InputFileAgainException {
    if (vertices == null) {
      throw new IllegalParamsNumberException("Add vertices into hyper edge with null vertices");
    }
    //    assert vertices.size() > 1;
    if (vertices.size() <= 1) {
      throw new IllegalParamsNumberException("Add vertices into hyper edge with "
          + vertices.size() + " params");
    }
    if (vertexSet != null
        && vertices.size() == vertexSet.size()
        && vertexSet.containsAll(vertices)) {
      return false;
    }
    vertexSet = new HashSet<>(vertices);
    //    checkRep();
    return true;
  }

  @Override
  public boolean containVertex(Vertex v) {
    return vertexSet.contains(v);
  }

  @Override
  public Set<Vertex> vertices() {
    return new HashSet<>(vertexSet);
  }

  @Override
  public Set<Vertex> sourceVertices() {
    return new HashSet<>();
  }

  @Override
  public Set<Vertex> targetVertices() {
    return new HashSet<>();
  }

  public boolean removeVertex(Vertex vertex) {
    return vertexSet.remove(vertex);
  }

  @Override
  public String toString() {
    return "HyperEdge{" + "label='" + getLabel() + '\'' + ", vertices=" + vertexSet + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof HyperEdge)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    HyperEdge hyperEdge = (HyperEdge) o;
    return vertexSet.size() == hyperEdge.vertexSet.size()
        && vertexSet.containsAll(hyperEdge.vertexSet);
  }

  @Override
  public int hashCode() {

    return Objects.hash(super.hashCode(), vertexSet);
  }
}
