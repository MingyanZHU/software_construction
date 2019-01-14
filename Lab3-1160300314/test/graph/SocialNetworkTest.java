package graph;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import edge.Edge;
import exception.InputFileAgainException;
import factory.EdgeFactory;
import factory.GraphFactory;
import factory.VertexFactory;
import helper.GraphMetrics;
import org.junit.Test;
import vertex.Vertex;

import java.io.IOException;
import java.util.Arrays;
import java.util.Set;

public class SocialNetworkTest {
    @Test
    public void testSocialNetworkTest() {
        Graph graph = null;
        try {
            graph = GraphFactory.createGraph("src/txt/inputSocialNetwork.txt");
            Vertex KEVIN = VertexFactory.createVertex("Kevin", "Person", new String[]{"M", "19"});
            Vertex AMY = VertexFactory.createVertex("Amy", "Person", new String[]{"F", "18"});
            Vertex EMMA = VertexFactory.createVertex("Emma", "Person", new String[]{"F", "21"});

            Edge edge1 = EdgeFactory.createEdge("edge", "FriendConnection", 0.2, Arrays.asList(EMMA, AMY));

            System.out.println(graph.toString());

            System.out.println(GraphMetrics.degreeCentrality(graph, KEVIN));
            System.out.println(GraphMetrics.degreeCentrality(graph, AMY));

            assertTrue(graph.vertices().contains(KEVIN));
            assertTrue(graph.vertices().contains(AMY));


            assertTrue(graph.addVertex(EMMA));
            assertFalse(graph.addVertex(KEVIN));
//        assertTrue(graph.removeEdge(forwardTie));
//        assertFalse(graph.removeEdge(forwardTie));
            assertTrue(graph.addEdge(edge1));

            double sum = 0;
            Set<Edge> edges = graph.edges();
            for (Edge edge : edges) {
                sum += edge.getWeight();
            }
//        assertEquals("The weight sum of edges should be 1", Double.doubleToLongBits(1), Double.doubleToLongBits(sum));

            assertTrue(graph.sources(AMY).containsKey(KEVIN));
            assertTrue(graph.sources(AMY).containsKey(EMMA));
            assertTrue(graph.targets(AMY).containsKey(KEVIN));

            assertThat(graph.toString(), containsString(KEVIN.getLabel()));
            assertThat(graph.toString(), containsString(edge1.getLabel()));
        } catch (IOException | InputFileAgainException e) {
            e.printStackTrace();
        }


    }
}
