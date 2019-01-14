package application;

import application.guihelper.GraphGui;
import edge.CommentTie;
import edge.Edge;
import edge.ForwardTie;
import edge.FriendTie;
import exception.InputFileAgainException;
import graph.Graph;
import vertex.Person;
import vertex.Vertex;

/**
 * Create a GUI visualization of SocialNetwork.
 *
 * @author Zhu Mingyan
 */
class SocialNetworkAppGui extends GraphGui {

  SocialNetworkAppGui(Graph graph) {
    super(graph);
  }

  /**
   * Add vertex into SocialNetwork visualization.
   *
   * @param vertex its type should be Person Class we have defined before and is non-null
   * @return true if we add it successfully, otherwise false
   */
  @Override
  public boolean addVertexInGraph(Vertex vertex) {
    return vertex instanceof Person && super.addVertexInGraph(vertex);
  }

  /**
   * Add edge into SocialNetwork visualization.
   *
   * @param edge its type should be CommentTie, ForwardTie or FriendTie Class we have defined and is
   *        non-null
   * @return true if we add it successfully, otherwise false
   */
  @Override
  public boolean addEdgeInGraph(Edge edge) throws InputFileAgainException {
    return (edge instanceof CommentTie || edge instanceof FriendTie
        || edge instanceof ForwardTie) && super.addEdgeInGraph(edge);
  }
}
