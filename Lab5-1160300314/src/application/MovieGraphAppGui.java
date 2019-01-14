package application;

import application.guihelper.GraphGui;
import edge.Edge;
import edge.MovieActorRelation;
import edge.MovieDirectorRelation;
import edge.SameMovieHyperEdge;
import exception.InputFileAgainException;
import graph.Graph;
import vertex.Actor;
import vertex.Director;
import vertex.Movie;
import vertex.Vertex;

/**
 * Create a GUI visualization of MovieGraph.
 *
 * @author Zhu Mingyan
 */
class MovieGraphAppGui extends GraphGui {

  MovieGraphAppGui(Graph graph) {
    super(graph);
  }

  /**
   * Add vertex into MovieGraph.
   *
   * @param vertex its type should be CastMember or Movie Class we have defined before and is
   *        non-null
   * @return true if we add it successfully, otherwise false
   */
  @Override
  public boolean addVertexInGraph(Vertex vertex) {
    return (vertex instanceof Movie || vertex instanceof Actor
        || vertex instanceof Director) && super.addVertexInGraph(vertex);
  }

  /**
   * Add edge into MovieGraph.
   *
   * @param edge its type should be SameMovieHyperEdge, MovieDirectorRelation and MovieActorRelation
   *        we have defined and is non-null
   * @return true if we add it successfully, otherwise false
   */
  @Override
  public boolean addEdgeInGraph(Edge edge) throws InputFileAgainException {
    return (edge instanceof SameMovieHyperEdge || edge instanceof MovieDirectorRelation
        || edge instanceof MovieActorRelation) && super.addEdgeInGraph(edge);
  }
}
