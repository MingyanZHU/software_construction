package graph;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import edge.Edge;
import exception.IllegalGrammarTextException;
import exception.InputFileAgainException;
import factory.EdgeFactory;
import factory.GraphFactory;
import factory.VertexFactory;
import helper.GraphMetrics;
import helper.GraphVisualizationHelper;
import org.junit.Test;
import vertex.Vertex;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class GraphPoetTest {
    @Test
    public void testGraphPoet() {
        Graph graph = null;
        try {
            graph = GraphFactory.createGraph("src/txt/inputGraphPoet.txt");
            Vertex word1 = VertexFactory.createVertex("Word1", "Word", null);
            Vertex word2 = VertexFactory.createVertex("Word2", "Word", null);
            Vertex word4 = VertexFactory.createVertex("Word4", "Word", null);
            Vertex word5 = VertexFactory.createVertex("Word5", "Word", null);
            Edge edge = EdgeFactory.createEdge("edge", "WordEdge", 1, Arrays.asList(word1, word2));
            Edge edge2 = EdgeFactory.createEdge("edge2", "WordEdge", 2, Arrays.asList(word1, word2));
            Edge loop = EdgeFactory.createEdge("loop", "WordEdge", 3, Arrays.asList(word5));
            assertEquals("PoetGraph", graph.getGraphName());

            System.out.println(GraphMetrics.degreeCentrality(graph, word2));
            System.out.println(GraphMetrics.degreeCentrality(graph, word1));
            System.out.println(GraphMetrics.inDegreeCentrality(graph, word2));
            System.out.println(GraphMetrics.outDegreeCentrality(graph, word2));

            System.out.println(GraphMetrics.distance(graph, word1, word4));
            System.out.println(GraphMetrics.closenessCentrality(graph, word1));
            System.out.println(GraphMetrics.betweennessCentrality(graph, word2));
            System.out.println(GraphMetrics.eccentricity(graph, word1));
            System.out.println(GraphMetrics.diameter(graph));
            System.out.println(GraphMetrics.radius(graph));
            System.out.println(GraphMetrics.degreeCentrality(graph));

            assertTrue(graph.vertices().contains(word1));
            assertTrue(graph.vertices().contains(word2));
            assertEquals(4, graph.vertices().size());

            assertTrue(graph.edges().contains(edge));
            assertEquals(5, graph.edges().size());

            assertFalse(graph.addVertex(word1));
            assertTrue(graph.sources(word2).containsKey(word1));
            assertTrue(graph.targets(word1).containsKey(word2));

            assertTrue(graph.addVertex(word5));
            assertThat(graph.toString(), containsString("Word3"));

            assertTrue(graph.removeVertex(word5));
            assertFalse(graph.addEdge(edge));
            assertTrue(graph.addEdge(edge2));

            assertThat(graph.toString(), containsString("edge2"));

            assertTrue(graph.removeEdge(edge));
            assertFalse(graph.removeEdge(edge));
            assertTrue(graph.removeEdge(edge2));

            // To test loop
            assertTrue(graph.addVertex(word5));
            assertTrue(graph.addEdge(loop));
            System.out.println(graph.toString());
        } catch (IOException |InputFileAgainException e) {
            e.printStackTrace();
        }
        //GraphPoet{
        //name=PoetGraph
        //vertices=[Word{word='Word2'}, Word{word='Word1'}],
        //edges=[WordEdge{label='edge', weight=1.0, source=Word{word='Word1'}, destination=Word{word='Word2'}}]}
    }
}
