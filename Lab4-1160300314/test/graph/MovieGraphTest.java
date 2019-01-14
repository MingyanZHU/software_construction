package graph;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import edge.Edge;
import edge.HyperEdge;
import exception.InputFileAgainException;
import factory.EdgeFactory;
import factory.GraphFactory;
import factory.HyperEdgeFactory;
import factory.VertexFactory;
import java.io.IOException;
import java.util.Arrays;
import org.junit.Test;
import vertex.Vertex;

public class MovieGraphTest {

  /*
  Test strategy:
  Because there are test enough in Concrete Graph test, so there we
  only test override methods.
   */
  @Test
  public void testMovieGraph() {
    Graph graph = null;
    try {
      graph = GraphFactory.createGraph("src/txt/inputMovieGraph.txt");
      assertEquals(6, graph.vertices().size());
      assertEquals(6, graph.edges().size());

      Vertex movie1 = VertexFactory
          .createVertex("TheShawshanRedemption", "Movie", new String[]{"1994", "USA", "9.3"});
      Vertex movie2 = VertexFactory
          .createVertex("TheGreenMile", "Movie", new String[]{"1999", "USA", "8.5"});

      Vertex actor1 = VertexFactory.createVertex("MorganFreeman", "Actor", new String[]{"M", "81"});
      Vertex actor2 = VertexFactory.createVertex("TimRobbins", "Actor", new String[]{"M", "60"});
      Vertex actor3 = VertexFactory.createVertex("TomHanks", "Actor", new String[]{"M", "62"});

      Vertex director = VertexFactory
          .createVertex("FrankDarabont", "Director", new String[]{"M", "59"});

      final Edge srfd = EdgeFactory
          .createEdge("SRFD", "MovieDirectorRelation", -1, Arrays.asList(movie1, director));
      final Edge gmfd = EdgeFactory
          .createEdge("GMFD", "MovieDirectorRelation", -1, Arrays.asList(movie2, director));
      final Edge srmf = EdgeFactory
          .createEdge("SRMF", "MovieActorRelation", 1, Arrays.asList(movie1, actor2));
      final Edge srtr = EdgeFactory
          .createEdge("SRTR", "MovieActorRelation", 2, Arrays.asList(movie1, actor1));
      final Edge gmth = EdgeFactory
          .createEdge("GMTH", "MovieActorRelation", 1, Arrays.asList(movie2, actor3));

      final HyperEdge hyperEdge = HyperEdgeFactory
          .createEdge("ActorInSR", "SameMovieHyperEdge", Arrays.asList(actor1, actor2));

      assertTrue(graph.vertices().contains(movie1));
      assertTrue(graph.vertices().contains(actor1));
      assertTrue(graph.edges().contains(srfd));
      assertTrue(graph.edges().contains(gmfd));
      assertTrue(graph.edges().contains(srtr));
      assertTrue(graph.edges().contains(srmf));
      assertTrue(graph.edges().contains(gmth));
      assertTrue(graph.edges().contains(hyperEdge));

      assertFalse(graph.addEdge(srfd));
      assertFalse(graph.addVertex(actor1));

      assertEquals(3, graph.targets(movie1).size());
      assertEquals(3, graph.sources(movie1).size());

      assertThat(graph.toString(), containsString(actor3.toString()));
      assertThat(graph.toString(), containsString(gmfd.toString()));
    } catch (IOException | InputFileAgainException e) {
      e.printStackTrace();
    }

  }
}
