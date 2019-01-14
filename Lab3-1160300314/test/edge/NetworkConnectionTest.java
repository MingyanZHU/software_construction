package edge;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import exception.InputFileAgainException;
import factory.EdgeFactory;
import org.junit.Test;
import vertex.Router;
import vertex.Server;
import vertex.Vertex;

import java.util.*;

public class NetworkConnectionTest {
    @Test
    public void testNetworkConnectionTest() throws InputFileAgainException {
        String label = "R1S1";
        String IP = "192.168.10.1";
        String[] args = {IP};
        int weight = 100;
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
    }

    @Test
    public void testEdgeFactory() throws InputFileAgainException{
        String label = "R1S1";
        String IP = "192.168.10.1";
        String[] args = {IP};
        int weight = 100;
        Router router = new Router("Router1");
        Server server = new Server("Server1");

        router.fillVertexInfo(args);
        server.fillVertexInfo(args);

        Edge networkConnection = EdgeFactory.createEdge(label, "NetworkConnection", weight, Arrays.asList(router, server));

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
    }

    @Test
    public void testOverride() throws InputFileAgainException {
        String label = "R1S1";
        String IP = "192.168.10.1";
        String[] args = {IP};
        int weight = 100;
        Router router = new Router("Router1");
        Server server = new Server("Server1");

        router.fillVertexInfo(args);
        server.fillVertexInfo(args);

        NetworkConnection networkConnection = new NetworkConnection(label, weight);
        networkConnection.addVertices(Arrays.asList(router, server));

        Edge edge = EdgeFactory.createEdge(label, "NetworkConnection", weight, Arrays.asList(router, server));

        assertTrue(edge.equals(networkConnection));
        assertTrue(networkConnection.equals(edge));

        assertEquals(edge.hashCode(), networkConnection.hashCode());
    }
}
