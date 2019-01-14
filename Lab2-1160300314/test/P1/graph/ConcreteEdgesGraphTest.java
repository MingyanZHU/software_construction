/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;


import static org.hamcrest.CoreMatchers.*; 

import static org.junit.Assert.*;

import java.util.Arrays;

//import static org.hamcrest.Matchers.*;

//import org.junit.matchers.JUnitMatchers;

//import org.hamcrest.*;

//import java.util.Collections;

import org.junit.Test;



/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<String>();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   TODO
    //Empty graph, graph without edges, and Empty vertices.
    
    // TODO tests for ConcreteEdgesGraph.toString()
    
    //Testing empty graph
    @Test
    public void testEmptyGraph(){
        Graph<String> graph = emptyInstance();
        assertThat(graph.toString(), containsString("0 vertice(s)"));
        assertThat(graph.toString(), containsString("0 edge(s)"));
    }
    
    //Testing graph without edges
    @Test
    public void testEmptyEdgesGraph(){
        Graph<String> graph = emptyInstance();
        String vertex = "Vertex1";
        graph.add(vertex);
        assertThat(graph.toString(), containsString(vertex));
    }
    
    //Testing graph constructor with parameters
    @Test
    public void testGraphWithParamsConstructor(){
        String vertex = "Vertex1";
        Graph<String> graph = new ConcreteEdgesGraph<>(Arrays.asList(vertex));
        assertThat(graph.vertices(), hasItem(vertex));
    }
    //Testing normal graph
    @Test
    public void testNormalGraph(){
        Graph<String> graph = emptyInstance();
        String vertex1 = "Vertex1";
        String vertex2 = "Vertex2";
        int weight = 1;
        graph.set(vertex1, vertex2, weight);
        assertThat(graph.toString(), containsString(vertex1));
        assertThat(graph.toString(), containsString(vertex2));
        assertThat(graph.toString(), containsString("\"" + vertex1+"\" -> \""+vertex2));
        assertThat(graph.toString(), containsString(graph.vertices().size()+" vertice(s)"));
        assertThat(graph.toString(), containsString((graph.sources(vertex2).size()
                                                       + graph.targets(vertex1).size()) / 2
                                                       + " edge(s)"));
    }
    
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   TODO
    // Partition for edge.getSource()
    //     has only one input, edge
    //     has only one output, source 
    //
    // Partition for edge.getTarget()
    //     has only one input, edge
    //     has only one output, target
    //
    // Partition for edge.getWeight()
    //     has only one input, edge
    //     has only one output, weight
    //
    // Partition for edge.cloneEdge()
    //     has only one input, edge
    //     has only one output, an Edge instance that has the same fields with input
    // 
    // Partition for edge.toString()
    //     has only one input, edge
    //     has only one output, edge.getSource() -> edge.getTarget() : edge.getWeight()
    //
    // Partition for edge.equals(input)
    //      input equal to edge, input not equal to edge
    //      output true if input equal to edge, otherwise false
    // 
    // Partition for edge.hashCode()
    //      has only one input, edge
    //      has only one output, the hash code of edge
    
    // TODO tests for operations of Edge
    
    @Test
    public void testGetSource(){
        final String source = "Vertex1";
        final String target = "Vertex2";
        final int weight  = 1;
        Edge<String> edge = new Edge<>(source, target, weight);
        assertEquals(source, edge.getSource());
    }
    
    @Test
    public void testGetTarget(){
        final String source = "Vertex1";
        final String target = "Vertex2";
        final int weight  = 1;
        Edge<String> edge = new Edge<>(source, target, weight);   
        assertEquals(target, edge.getTarget());
    }
    
    @Test
    public void testGetWeight(){
        final String source = "Vertex1";
        final String target = "Vertex2";
        final int weight  = 1;
        Edge<String> edge = new Edge<>(source, target, weight);   
        assertEquals(weight, edge.getWeight());
    }
    
    @Test
    public void testCloneEdge(){
        final String source = "Vertex1";
        final String target = "Vertex2";
        final int weight  = 1;
        Edge<String> edge = new Edge<>(source, target, weight);   
        Edge<String> cloneEdge = edge.cloneEdge();
        assertEquals(edge.getSource(), cloneEdge.getSource());
        assertEquals(edge.getTarget(), cloneEdge.getTarget());
        assertEquals(edge.getWeight(), cloneEdge.getWeight());
    }
    
    @Test
    public void testToString(){
        final String source = "Vertex1";
        final String target = "Vertex2";
        final int weight  = 1;
        Edge<String> edge = new Edge<>(source, target, weight);   
        assertThat(edge.toString(), containsString(source));
        assertThat(edge.toString(), containsString(target));
        assertThat(edge.toString(), containsString(String.valueOf(weight)));
    }
    
    @Test
    public void testEqualsWithDifferentEdge(){
        final String source = "Vertex1";
        final String target = "Vertex2";
        final int weight  = 1;
        Edge<String> edge1 = new Edge<>(source, target, weight);
        Edge<String> edge2 = new Edge<>(target, source, weight);
        assertFalse(edge1.equals(edge2));
        assertFalse(edge2.equals(edge1));
    }
    
    @Test
    public void testEqualsWithSameEdge(){
        final String source = "Vertex1";
        final String target = "Vertex2";
        final int weight  = 1;
        Edge<String> edge = new Edge<>(source, target, weight);   
        assertTrue(edge.equals(edge.cloneEdge()));
    }
    
    @Test
    public void testHashcodeWithDifferentEdges(){
        final String source = "Vertex1";
        final String target = "Vertex2";
        final int weight  = 1;
        Edge<String> edge1 = new Edge<>(source, target, weight);
        Edge<String> edge2 = new Edge<>(target, source, weight);
        assertNotEquals(edge1.hashCode(), edge2.hashCode());
    }
    
    @Test
    public void testHashcodeWithSameEdges(){
        final String source = "Vertex1";
        final String target = "Vertex2";
        final int weight  = 1;
        Edge<String> edge = new Edge<>(source, target, weight);   
        Edge<String> edge2 = new Edge<>(source, target, weight);
        assertEquals(edge.hashCode(), edge2.hashCode());
    }
}
