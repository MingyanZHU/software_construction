package application;

import application.guihelper.GraphGui;
import edge.Edge;
import edge.WordNeighborhood;
import exception.InputFileAgainException;
import graph.Graph;
import vertex.Vertex;
import vertex.Word;

/**
 * Create a GUI visualization of GraphPoet.
 *
 * @author Zhu Mingyan
 */
class GraphPoetAppGui extends GraphGui {

  GraphPoetAppGui(Graph graph) {
    super(graph);
  }

  /**
   * Add Vertex in GraphPoet.
   *
   * @param vertex its type should be Word Class we have defined before and is non-null
   * @return true if adding successfully, otherwise false
   */
  @Override
  public boolean addVertexInGraph(Vertex vertex) {
    return vertex instanceof Word && super.addVertexInGraph(vertex);
  }

  /**
   * Add edge in GraphPoet.
   *
   * @param edge its type should be WordNeighborhood Class we have defined and is non-null
   * @return true if we add successfully, otherwise false
   */
  @Override
  public boolean addEdgeInGraph(Edge edge) throws InputFileAgainException {
    return edge instanceof WordNeighborhood && super.addEdgeInGraph(edge);
  }
}
