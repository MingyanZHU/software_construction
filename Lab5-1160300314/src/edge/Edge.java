package edge;

import exception.IllegalEdgeParamsException;
import exception.InputFileAgainException;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import vertex.Vertex;

/**
 * Describe the edge of graph with its label and weight.
 *
 * @author Zhu Mingyan
 */
public abstract class Edge {

  /*
  AF: Represents an edge of graph with label and weight.
  RI: label of edge is non-null and non-empty string consisting of [A-Za-z_0-9] and is unique.
      weight of edge is -1 or a decimal above 0.
  Safety for Rep Exposure:
      All fields in edge are modified by key word private and final.
      Clients can not change them except Constructor of edge.
   */
  private final String label;
  private final double weight;

  /**
   * Get an instance of edge with its label and weight.
   */
  public Edge(String label, double weight) throws IllegalEdgeParamsException {
    this.label = label;
    this.weight = weight;
    if (label == null) {
      throw new IllegalEdgeParamsException("Label of edge should not be null!");
    }
    if (label.length() == 0) {
      throw new IllegalEdgeParamsException("Label of edge should not be empty string");
    }
    Pattern pattern = Pattern.compile("^[\\w]+$");
    Matcher matcher = pattern.matcher(label);
    if (!matcher.matches()) {
      throw new IllegalEdgeParamsException(label + " should not be label of edge "
          + "because it not consists of [\\w]");
    }
  }

  protected void checkRep() throws InputFileAgainException {

    //        assert label != null && label.length() != 0;
    //        assert matcher.matches();
    if (Math.abs(weight + 1) >= 0.00001
        && Double.doubleToLongBits(weight) <= Double.doubleToLongBits(0)) {
      throw new IllegalEdgeParamsException(weight + " of edge is illegal");
    }
  }

  /**
   * Get label of edge.
   *
   * @return label string
   */
  public String getLabel() {
    return label;
  }

  /**
   * Get the weight of this edge.
   *
   * @return a decimal of -1 if this is an edge without weights or above 0
   */
  public double getWeight() {
    return weight;
  }

  /**
   * Fill the vertices of edge. If it is a directed edge, list of vertices should consist source
   * vertex and destination vertex in order. If it is a undirected edge, list of vertices should
   * consist with exactly two vertices in any order or only one vertex only if it's a loop. If it is
   * a hyper edge, lise of vertices should consist with one vertex at least or more than one
   * vertices in any order.
   *
   * @return true if add vertices successfully, otherwise false
   */
  public abstract boolean addVertices(List<Vertex> vertices) throws InputFileAgainException;

  /**
   * Determine if there is this vertex in this edge.
   *
   * @param v the vertex need to determine
   * @return true if this vertex in this edge, otherwise false
   */
  public abstract boolean containVertex(Vertex v);

  /**
   * Get all vertices in this edge.
   *
   * @return a set consists of all vertices in this edge
   */
  public abstract Set<Vertex> vertices();

  /**
   * Get all the source vertices of this edge.
   *
   * @return a set consists of all source vertices of this edge
   */
  public abstract Set<Vertex> sourceVertices();

  /**
   * Get all the destination(target) vertices of this edge.
   *
   * @return a set consists of all destination(target) vertices of this edge
   */
  public abstract Set<Vertex> targetVertices();

  // 抽象类Edge所有的具体子类均重写了toString()方法
  //    @Override
  //    public String toString() {
  //        return "Edge{" +
  //                "label='" + label + '\'' +
  //                ", weight=" + weight +
  //                '}';
  //    }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Edge)) {
      return false;
    }
    Edge edge = (Edge) o;
    return Double.compare(edge.weight, weight) == 0 && label.equals(edge.label);
  }

  @Override
  public int hashCode() {

    return Objects.hash(label, weight);
  }
}
