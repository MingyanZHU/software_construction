package factory;

import edge.HyperEdge;
import edge.SameMovieHyperEdge;
import exception.InputFileAgainException;
import java.util.List;
import vertex.Vertex;

/**
 * A factory that created instances of hyper edges.
 *
 * @author Zhu Mingyan
 */
public class HyperEdgeFactory {

  /**
   * Create an instance of determined type and provided params.
   *
   * @param label the label string of this instance which is non-null and non-empty string
   * @param type type of this instance which is also in all hyper edge types we have been defined
   * @param vertices vertices of this hyper edge which should consist of at least one vertices
   * @return an instance in determined type with all params provided.
   */
  public static HyperEdge createEdge(String label, String type, List<Vertex> vertices)
      throws InputFileAgainException {
    HyperEdge hyperEdge;
    if (type.equals("SameMovieHyperEdge")) {
      hyperEdge = new SameMovieHyperEdge(label, -1);
    } else {
      hyperEdge = null;
    }
    assert hyperEdge != null;
    hyperEdge.addVertices(vertices);
    return hyperEdge;
  }
}
