package graph;

import edge.Edge;
import edge.HyperEdge;
import edge.MovieActorRelation;
import edge.MovieDirectorRelation;
import edge.SameMovieHyperEdge;
import exception.InputFileAgainException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import vertex.Actor;
import vertex.Director;
import vertex.Movie;
import vertex.Vertex;

/**
 * Describe a graph which is multi mode graph and simple graph with hyper edges.
 *
 * @author Zhu Mingyan
 */
public class MovieGraph extends ConcreteGraph<Vertex, Edge> {

  /*
  AF: Represents a graph of movie Graph with Actor, Director and Movie as Vertex and
      MovieActorRelation, MovieDirectorRelation and SameMovieHyperEdge as Edge.
  RI: vertexSet that the collections of all vertices in graph should not be null
      edgeSet that the collections of all edges in graph should not be null
      Label(name) of graph should be non-null and non-empty String.
      All vertices should be instances of Actor, Director or Movie.
      All edges should be instances of MovieActorRelation, MovieDirectorRelation or
      SameMovieHyperEdge and there is no loop in edges.
  Safety for Rep Exposure:
      All fields are modified by key word private, clients can not access the field outside class.
      And there is no other setter function except addVertex, addEdge, removeEdge and removeVertex.
      Anywhere needed get the mutable fields is defencive by defencive copy
   */
  public MovieGraph(String name) {
    super(name);
    super.checkRep();
  }

  @Override
  protected void checkRep() {
    super.checkRep();
    Set<Edge> edgeSet = this.edges();
    for (Edge edge : edgeSet) {
      if (!(edge instanceof HyperEdge)) {
        List<Vertex> source = new ArrayList<>(edge.sourceVertices());
        List<Vertex> target = new ArrayList<>(edge.targetVertices());
        assert source.size() == 2;
        assert target.size() == 2;
        assert !source.get(0).equals(target.get(1));
      }
    }
  }

  @Override
  public boolean addVertex(Vertex vertex) {
    boolean answer = (vertex instanceof Movie || vertex instanceof Actor
        || vertex instanceof Director) && super.addVertex(vertex);
    return answer;
  }

  @Override
  public boolean addEdge(Edge edge) throws InputFileAgainException {
    boolean answer = (edge instanceof MovieActorRelation || edge instanceof MovieDirectorRelation
        || edge instanceof SameMovieHyperEdge) && super.addEdge(edge);
    //    checkRep();
    return answer;
  }

  @Override
  public String toString() {
    return "MovieGraph" + super.toString().replace("ConcreteGraph", "");
  }
}
