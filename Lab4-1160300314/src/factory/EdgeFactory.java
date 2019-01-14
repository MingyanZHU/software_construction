package factory;

import edge.CommentTie;
import edge.Edge;
import edge.ForwardTie;
import edge.FriendTie;
import edge.MovieActorRelation;
import edge.MovieDirectorRelation;
import edge.NetworkConnection;
import edge.WordNeighborhood;
import exception.InputFileAgainException;
import java.util.List;
import vertex.Vertex;

/**
 * A factory that used to create instances of edges.
 *
 * @author Zhu Mingyan
 */
public class EdgeFactory {

  /**
   * Create an instance of determined type with all params provided.
   *
   * @param label the label string of instance which is non-null and non-empty string
   * @param type the type of this instance you want to which should be in all types we have been
   *        defined.
   * @param weight the weight of this edge instance if this edge is unweighted edge weight should be
   *        -1 otherwise should be above 0.
   * @param vertices all vertices should in this edge. If this edge is directed, the former should
   *        be the source vertex and the latter should be the destination.
   *        If this edge is a loop, vertices should have exactly one vertex.
   * @return an instance of the determined type and provided params
   */
  public static Edge createEdge(String label, String type, double weight, List<Vertex> vertices)
      throws InputFileAgainException {
    Edge edge;
    if (type.equals("WordNeighborhood")) {
      edge = new WordNeighborhood(label, weight);
    } else if (type.equals("NetworkConnection")) {
      edge = new NetworkConnection(label, weight);
    } else if (type.equals("MovieDirectorRelation")) {
      edge = new MovieDirectorRelation(label, weight);
    } else if (type.equals("MovieActorRelation")) {
      edge = new MovieActorRelation(label, weight);
    } else if (type.equals("FriendTie")) {
      edge = new FriendTie(label, weight);
    } else if (type.equals("ForwardTie")) {
      edge = new ForwardTie(label, weight);
    } else if (type.equals("CommentTie")) {
      edge = new CommentTie(label, weight);
    } else {
      edge = null;
    }
    assert edge != null;
    edge.addVertices(vertices);
    return edge;
  }
}
