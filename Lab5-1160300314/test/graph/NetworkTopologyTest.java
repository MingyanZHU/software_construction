package graph;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import edge.Edge;
import exception.InputFileAgainException;
import factory.EdgeFactory;
import factory.GraphFactory;
import factory.VertexFactory;
import helper.GraphMetrics;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import org.junit.Test;
import vertex.Vertex;

public class NetworkTopologyTest {

  /*
  Test strategy:
  Because there are test enough in Concrete Graph test, so there we
  only test override methods.
   */
  @Test
  public void testNetworkTopology() {
    try {
      Vertex computer = VertexFactory
          .createVertex("Computer1", "Computer", new String[]{"192.168.1.101"});
      Vertex server = VertexFactory.createVertex("Server1", "Server", new String[]{"192.168.1.2"});
      Vertex router = VertexFactory.createVertex("Router1", "Router", new String[]{"192.168.1.1"});

      final Edge r1s1 = EdgeFactory
          .createEdge("R1S1", "NetworkConnection", 100, Arrays.asList(router, server));
      final Edge c1s1 = EdgeFactory
          .createEdge("C1S1", "NetworkConnection", 10, Arrays.asList(computer, server));

      Graph graph = null;
      graph = GraphFactory.createGraph("src/txt/inputNetworkTopology.txt");
      System.out.println(graph.toString());

      System.out.println(GraphMetrics.distance(graph, router, server));
      assertTrue(graph.vertices().contains(computer));
      assertTrue(graph.vertices().contains(server));
      assertTrue(graph.vertices().contains(router));

      assertTrue(graph.edges().contains(c1s1));
      assertTrue(graph.edges().contains(r1s1));

      assertTrue(graph.removeVertex(server));
      assertFalse(graph.removeVertex(server));

      assertEquals(Collections.EMPTY_SET, graph.edges());
      assertTrue(graph.addVertex(server));
      assertFalse(graph.addVertex(server));
      assertTrue(graph.addEdge(r1s1));
      assertTrue(graph.addEdge(c1s1));

      assertTrue(graph.sources(server).containsKey(computer));
      assertTrue(graph.sources(server).containsKey(router));
      assertTrue(graph.targets(computer).containsKey(server));
      assertTrue(graph.targets(server).containsKey(router));

      assertThat(graph.toString(), containsString(r1s1.toString()));
      assertThat(graph.toString(), containsString(computer.toString()));

      System.out.println(GraphMetrics.degreeCentrality(graph, computer));
      System.out.println(GraphMetrics.degreeCentrality(graph, server));
      System.out.println(GraphMetrics.degreeCentrality(graph, router));

      assertEquals(1, GraphMetrics.degreeCentrality(graph, computer), 0.001);
      assertEquals(2, GraphMetrics.degreeCentrality(graph, server), 0.001);
      assertEquals(1, GraphMetrics.degreeCentrality(graph, router), 0.001);
    } catch (IOException | InputFileAgainException e) {
      e.printStackTrace();
    }
  }
}
