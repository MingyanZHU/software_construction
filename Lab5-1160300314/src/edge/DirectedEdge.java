package edge;

import exception.IllegalEdgeParamsException;
import exception.IllegalVertexParamsException;
import exception.InputFileAgainException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import vertex.Vertex;

/**
 * Describe the directed edge of graph.
 *
 * @author Zhu Mingyan
 */
public class DirectedEdge extends Edge {

  /*
  AF: Represents the directed edge in graph from source to destination
  RI: source vertex is non-null and an instance of Vertex.
      destination vertex in non-null and an instance of Vertex
  Safety for Rep exposure:
      All fields except label and weight of its super class are modified
      by private. And there is no other function except addVertices can change
      its source and destination.
   */
  private Vertex source;
  private Vertex destination;
  private Set<Vertex> sourceSet;
  private Set<Vertex> destinationSet;
  private Set<Vertex> vertices = new HashSet<>();

  public DirectedEdge(String label, double weight) throws InputFileAgainException {
    super(label, weight);
    super.checkRep();
  }

  @Override
  protected void checkRep() throws InputFileAgainException {
    super.checkRep();
    if (source == null || destination == null) {
      throw new IllegalEdgeParamsException("Some vertex in directed edge is null!");
    }
    //        assert source != null;
    //        assert destination != null;
  }


  @Override
  public boolean addVertices(List<Vertex> vertexList) throws InputFileAgainException {
    //        assert vertices.size() == 2 || vertices.size() == 1;
    // If the directed edge is a loop the size of vertices will be one.
    if (vertexList == null) {
      throw new IllegalVertexParamsException("Vertices of directed edge is null!");
    }
    if (vertexList.size() != 1 && vertexList.size() != 2) {
      throw new IllegalVertexParamsException(
          "Number of vertices in directed edge is illegal with " + vertexList.size()
              + " vertices!");
    }
    if (vertexList.size() == 2) {
      if (source != null && destination != null
          && source.equals(vertexList.get(0)) && destination.equals(vertexList.get(1))) {
        return false;
      }
      if (vertexList.get(0) == null || vertexList.get(1) == null) {
        throw new IllegalVertexParamsException("vertices consists of null!");
      }
      source = vertexList.get(0);
      destination = vertexList.get(1);
      //      try {
      //        source = vertices.get(0).clone();
      //        destination = vertices.get(1).clone();
      //      } catch (CloneNotSupportedException e) {
      //        e.printStackTrace();
      //      }
    } else {
      if (source != null && destination != null
          && source.equals(vertexList.get(0)) && destination.equals(vertexList.get(0))) {
        return false;
      }
      if (vertexList.get(0) == null) {
        throw new IllegalVertexParamsException("Vertices consists of null!");
      }
      source = destination = vertexList.get(0);
      //      try {
      //        source = destination = vertices.get(0).clone();
      //      } catch (CloneNotSupportedException e) {
      //        e.printStackTrace();
      //      }
    }
    //    checkRep();
    sourceSet = Collections.singleton(source);
    destinationSet = Collections.singleton(destination);
    vertices.add(source);
    vertices.add(destination);
    return true;
  }

  @Override
  public boolean containVertex(Vertex v) {
    return source.equals(v) || destination.equals(v);
  }

  @Override
  public Set<Vertex> vertices() {
    //    Set<Vertex> answerSet = new HashSet<>();
    //    answerSet.add(source);
    //    answerSet.add(destination);
    //    return answerSet;
    return vertices;
  }

  @Override
  public Set<Vertex> sourceVertices() {
    //    Set<Vertex> answerSet = new HashSet<>();
    //    answerSet.add(source);
    //    return answerSet;
    return sourceSet;
  }

  @Override
  public Set<Vertex> targetVertices() {
    //    Set<Vertex> answerSet = new HashSet<>();
    //    answerSet.add(destination);
    //    return answerSet;
    return destinationSet;
  }

  @Override
  public String toString() {
    return "DirectedEdge{" + "label='" + getLabel() + '\'' + ", weight=" + getWeight()
        + ", source=" + source + ", destination=" + destination + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof DirectedEdge)) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    DirectedEdge that = (DirectedEdge) o;
    return source.equals(that.source) && destination.equals(that.destination);
  }

  @Override
  public int hashCode() {

    return Objects.hash(super.hashCode(), source, destination);
  }
}
