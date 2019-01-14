package graph;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import edge.Edge;
import exception.InputFileAgainException;
import factory.EdgeFactory;
import factory.GraphFactory;
import factory.LoggerFactory;
import factory.VertexFactory;
import helper.GraphMetrics;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;
import org.apache.log4j.Logger;
import org.junit.Test;
import vertex.Vertex;
import vertex.Word;

public class SocialNetworkTest {

  private static final Logger LOGGER = LoggerFactory.createLogger(SocialNetworkTest.class);

  @Test
  public void testSocialNetworkTest() {
    /*
  Test strategy:
  Because there are test enough in Concrete Graph test, so there we
  only test override methods.
   */
    Graph graph = null;
    try {
      graph = GraphFactory.createGraph("src/txt/inputSocialNetwork.txt");
      Vertex kevin = VertexFactory.createVertex("Kevin", "Person", new String[]{"M", "19"});
      Vertex amy = VertexFactory.createVertex("Amy", "Person", new String[]{"F", "18"});
      Vertex emma = VertexFactory.createVertex("Emma", "Person", new String[]{"F", "21"});

      final Edge edge1 = EdgeFactory.createEdge(
          "edge", "FriendTie", 0.2, Arrays.asList(emma, amy));

      System.out.println(graph.toString());

      System.out.println(GraphMetrics.degreeCentrality(graph, kevin));
      System.out.println(GraphMetrics.degreeCentrality(graph, amy));

      assertTrue(graph.vertices().contains(kevin));
      assertTrue(graph.vertices().contains(amy));

      assertTrue(graph.addVertex(emma));
      assertFalse(graph.addVertex(kevin));
      //assertTrue(graph.removeEdge(forwardTie));
      //assertFalse(graph.removeEdge(forwardTie));
      assertTrue(graph.addEdge(edge1));

      double sum = 0;
      Set<Edge> edges = graph.edges();
      for (Edge edge : edges) {
        sum += edge.getWeight();
      }
      //assertEquals("The weight sum of edges should be 1", Double.doubleToLongBits(1),
      // Double.doubleToLongBits(sum));

      assertTrue(graph.sources(amy).containsKey(kevin));
      assertTrue(graph.sources(amy).containsKey(emma));
      // TODO why??
      // assertTrue(graph.targets(amy).containsKey(kevin));

      assertThat(graph.toString(), containsString(kevin.getLabel()));
      assertThat(graph.toString(), containsString(edge1.getLabel()));

      assertTrue(graph.removeEdge(edge1));

      graph.addEdge(EdgeFactory.createEdge("edge111", "WordNeighborhood",
          1, Collections.singletonList(new Word("hell"))));
    } catch (IOException | InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

  }
}
