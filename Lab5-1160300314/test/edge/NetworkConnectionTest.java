package edge;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import exception.InputFileAgainException;
import factory.EdgeFactory;
import factory.LoggerFactory;
import factory.VertexFactory;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import org.junit.Test;
import vertex.Computer;
import vertex.Router;
import vertex.Server;
import vertex.Vertex;
import vertex.Word;

public class NetworkConnectionTest {

  private static final Logger LOGGER = LoggerFactory.createLogger(NetworkConnection.class);
  private static final String type = "NetworkConnection";
  private static final double delta = 0.00001;

  @Test
  public void testAddVertices() {
    String label = "network";
    double weight = 1;

    try {
      NetworkConnection connection = (NetworkConnection) EdgeFactory.createEdge(label,
          type, weight, null);

      assertEquals(label, connection.getLabel());
      assertEquals(weight, connection.getWeight(), delta);
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Computer computer = (Computer) VertexFactory.createVertex("Computer", "Computer",
          new String[]{"192.168.1.5"});
      NetworkConnection connection = (NetworkConnection) EdgeFactory.createEdge(label, type, weight,
          Collections.singletonList(computer));

      assertEquals(label, connection.getLabel());
      assertTrue(connection.vertices().contains(computer));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Computer computer = (Computer) VertexFactory.createVertex("Computer", "Computer",
          new String[]{"192.168.1.5"});
      NetworkConnection connection = (NetworkConnection) EdgeFactory.createEdge(label, type, weight,
          Arrays.asList(computer, computer));

      assertTrue(connection.sourceVertices().contains(computer));
      assertTrue(connection.targetVertices().contains(computer));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Computer computer = (Computer) VertexFactory.createVertex("Computer", "Computer",
          new String[]{"192.168.1.5"});
      Server server = (Server) VertexFactory.createVertex("Server", "Server",
          new String[]{"192.109.2.3"});
      NetworkConnection connection = (NetworkConnection) EdgeFactory.createEdge(label, type, weight,
          Arrays.asList(computer, server));

      assertTrue(connection.targetVertices().contains(server));
      assertTrue(connection.targetVertices().contains(computer));
      assertTrue(connection.sourceVertices().contains(server));
      assertTrue(connection.sourceVertices().contains(computer));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Word hello = new Word("hello");
      Word world = new Word("world");

      NetworkConnection connection = (NetworkConnection) EdgeFactory.createEdge(label, type, weight,
          Arrays.asList(hello, world));

      assertEquals(label, connection.getLabel());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testNetworkConnectionTest() {
    String label = "R1S1";
    String ip = "192.168.10.1";
    String[] args = {ip};
    int weight = 100;
    try {
      Router router = new Router("Router1");
      Server server = new Server("Server1");

      router.fillVertexInfo(args);
      server.fillVertexInfo(args);

      NetworkConnection networkConnection = new NetworkConnection(label, weight);
      networkConnection.addVertices(Arrays.asList(router, server));

      assertEquals(label, networkConnection.getLabel());
      assertTrue(networkConnection.containVertex(router));
      assertTrue(networkConnection.containVertex(server));

      Set<Vertex> vertexSet = new HashSet<>();
      vertexSet.add(router);
      vertexSet.add(server);

      assertEquals(vertexSet.size(), networkConnection.vertices().size());
      assertTrue(networkConnection.vertices().containsAll(vertexSet));

      assertEquals(vertexSet.size(), networkConnection.sourceVertices().size());
      assertTrue(vertexSet.containsAll(networkConnection.sourceVertices()));

      assertEquals(vertexSet.size(), networkConnection.targetVertices().size());
      assertTrue(vertexSet.containsAll(networkConnection.targetVertices()));

      assertThat(networkConnection.toString(), containsString(router.toString()));
      assertThat(networkConnection.toString(), containsString(server.toString()));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      NetworkConnection connection = (NetworkConnection) EdgeFactory
          .createEdge(label, type, -1, null);

      assertEquals(-1, connection.getWeight(), delta);
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testEdgeFactory() {
    String label = "R1S1";
    String ip = "192.168.10.1";
    String[] args = {ip};
    int weight = 100;
    try {
      Router router = new Router("Router1");
      Server server = new Server("Server1");

      router.fillVertexInfo(args);
      server.fillVertexInfo(args);

      Edge networkConnection = EdgeFactory
          .createEdge(label, "NetworkConnection", weight, Arrays.asList(router, server));

      assertEquals(label, networkConnection.getLabel());
      assertTrue(networkConnection.containVertex(router));
      assertTrue(networkConnection.containVertex(server));

      Set<Vertex> vertexSet = new HashSet<>();
      vertexSet.add(router);
      vertexSet.add(server);

      assertEquals(vertexSet.size(), networkConnection.vertices().size());
      assertTrue(networkConnection.vertices().containsAll(vertexSet));

      assertEquals(vertexSet.size(), networkConnection.sourceVertices().size());
      assertTrue(vertexSet.containsAll(networkConnection.sourceVertices()));

      assertEquals(vertexSet.size(), networkConnection.targetVertices().size());
      assertTrue(vertexSet.containsAll(networkConnection.targetVertices()));

      assertThat(networkConnection.toString(), containsString(router.toString()));
      assertThat(networkConnection.toString(), containsString(server.toString()));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testOverride() {
    String label = "R1S1";
    String ip = "192.168.10.1";
    String[] args = {ip};
    int weight = 100;
    try {
      Router router = new Router("Router1");
      Server server = new Server("Server1");

      router.fillVertexInfo(args);
      server.fillVertexInfo(args);

      NetworkConnection networkConnection = new NetworkConnection(label, weight);
      networkConnection.addVertices(Arrays.asList(router, server));

      Edge edge = EdgeFactory
          .createEdge(label, "NetworkConnection", weight, Arrays.asList(router, server));
      boolean answer = edge.equals(networkConnection);
      assertTrue(answer);
      answer = networkConnection.equals(edge);
      assertTrue(answer);
      answer = (edge.hashCode() == networkConnection.hashCode());
      assertTrue(answer);
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}
