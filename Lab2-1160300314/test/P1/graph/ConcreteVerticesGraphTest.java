/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.hamcrest.*;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {

    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override
    public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph<String>();
    }

    /*
     * Testing ConcreteVerticesGraph...
     */
    
    @Test
    public void testGraphParamsConstructor(){
        final String name = "Vertex";
        final String target = "Target";
        final int weight = 1;
        final Map<String, Integer> map = new HashMap<>();
        map.put(target, weight);
        Vertex<String> vertex = new Vertex<>(name, map);
        Graph<String> graph = new ConcreteVerticesGraph<>(Arrays.asList(vertex));
        assertThat(graph.vertices(), hasItem(name));
        
    }
    // Testing strategy for ConcreteVerticesGraph.toString()
    // TODO
    // Partition for ConcreteVerticesGraph.toString()
    //      empty graph, graph with vertices without edges, graph with vertices and edges
    
    // TODO tests for ConcreteVerticesGraph.toString()
    @Test
    public void testToStringEmptyGraph(){
        Graph<String> graph = emptyInstance();
        assertThat(graph.toString(), containsString("0 vertice(s)"));
        assertThat(graph.toString(), containsString("0 edge(s)"));
    }
    
    @Test
    public void testToStringGraphWithoutEdges(){
        Graph<String> graph = emptyInstance();
        final String vertex = "Vertex";
        graph.add(vertex);
        assertThat(graph.toString(), containsString(vertex));
        assertThat(graph.toString(), containsString("1 vertice(s)"));
    }
    
    @Test
    public void testToStringGraphWithEdges(){
        Graph<String> graph = emptyInstance();
        final String source = "Source";
        final String target = "Target";
        final int weight = 32767;
        graph.set(source, target, weight);
        assertThat(graph.toString(), containsString("2 vertice(s)"));
        assertThat(graph.toString(), containsString("1 edge(s)"));
        assertThat(graph.toString(), containsString(source));
        assertThat(graph.toString(), containsString(target));
        assertThat(graph.toString(), containsString(String.valueOf(weight)));
        assertThat(graph.toString(), containsString("\"" + source + "\" -> \"" + target + "\": " + weight));
    }
    /*
     * Testing Vertex...
     */

    // Testing strategy for Vertex
    // TODO
    // Partition for vertex.getName()
    //      only has one input, vertex
    //      only has one output, the name of vertex
    //
    // Partition for vertex.getAdjacentName()
    //      input vertex without edges, and vertex with edges
    //      only has one output, the number of edges adjacent to this vertex
    //  
    // Partition for vertex.getMap()
    //      input vertex without edges, and vertex with edges
    //      only has one output, the copy of the adjacent map
    //     
    // Partition for vertex.equalsName(input)
    //      input has the same name with vertex, input has different name
    //      output: true if input has the same with vertex, otherwise false
    // 
    // Partition for vertex.getWeightOfEdge(input)
    //      input the target of some directed edge
    //      output: if there an edge from this vertex to input return the weight of that,
    //              otherwise 0.
    //
    // Partition for vertex.put(target, weight)
    //      input the target of the edge and the weight
    //
    // Partition for vertex.adjacnetTo(input)
    //      input the target of edge
    //      output: true if this vertex is connected to target, otherwise false
    // 
    // Partition for vertex.equals(input)
    //      input equal to vertex, input not equal to vertex
    //      output true if input's name equal to name of this, otherwise false
    // 
    // Partition for vertex.hashCode()
    //      has only one input, vertex
    //      has only one output, the hash code of vertex
    // TODO tests for operations of Vertex
    
    // Test getName()
    @Test
    public void testGetName(){
        final String name = "Vertex";
        final Vertex<String> vertex = new Vertex<>(name);
        assertEquals(name, vertex.getName());
    }
    
    // Test getMap()
    // covers vertex without edges
    @Test
    public void testGetMapWithoutEdges(){
        final String name = "Vertex";
        final Vertex<String> vertex = new Vertex<>(name);
        assertEquals(Collections.EMPTY_MAP, vertex.getMap());
    }
    
    // Test getMap()
    // covers vertex with edges
    @Test
    public void testGetMapWithEdge(){
        final String name = "Vertex";
        final Vertex<String> vertex = new Vertex<>(name);
        final String target = "Target";
        final int weight = 1;
        vertex.put(target, weight);
        assertThat(vertex.getMap(),  Matchers.hasEntry(target, weight));
    }
    
    //Test getAdjacentEdgesNumber()
    // covers vertex without edges
    @Test
    public void testGetAdjacentWithoutEdges(){
        final String name = "Vertex";
        final Vertex<String> vertex = new Vertex<>(name);
        assertEquals(0, vertex.getAdjacentEdgesNumber());
    }
    
    //Test getAdjacentEdgesNumber()
    // covers vertex without edges
    @Test
    public void testGetAdjacentWithEdges(){
        final String name = "Vertex";
        final Vertex<String> vertex = new Vertex<>(name);
        final String target = "Target";
        final int weight = 1;
        vertex.put(target, weight);
        assertEquals(1, vertex.getAdjacentEdgesNumber());
    }
    
    // Test equalsName()
    @Test
    public void testEqualsName(){
        final String name = "Vertex";
        final Vertex<String> vertex = new Vertex<>(name);
        assertFalse(vertex.equalsName("Name"));
        assertTrue(vertex.equalsName("Vertex"));
    }
    
    // Test getWeightOfEdge
    @Test
    public void testGetWeightOfEdge(){
        final String name = "Vertex";
        final Vertex<String> vertex = new Vertex<>(name);
        final String target = "Target";
        final int weight = 1;
        vertex.put(target, weight);
        assertEquals(weight, vertex.getWeightOfEdge(target));
        assertEquals(0, vertex.getWeightOfEdge(name));
    }
    
    // Test put()
    @Test
    public void testPut(){
        final String name = "Vertex";
        final Vertex<String> vertex = new Vertex<>(name);
        final String target = "Target";
        final int weight = 1;
        vertex.put(target, weight);
        assertThat(vertex.getMap(), Matchers.hasEntry(target, weight));
    }
    
    // Test remove()
    @Test
    public void testRemove(){
        final String name = "Vertex";
        final Vertex<String> vertex = new Vertex<>(name);
        final String target = "Target";
        final int weight = 1;
        vertex.put(target, weight);
        assertThat(vertex.getMap(), Matchers.hasEntry(target, weight));
        vertex.remove(target);
        assertEquals(Collections.EMPTY_MAP, vertex.getMap());
    }
    
    // Test adjacentTo()
    @Test
    public void testAdjcentTo(){
        final String name = "Vertex";
        final Vertex<String> vertex = new Vertex<>(name);
        final String target = "Target";
        final int weight = 1;
        vertex.put(target, weight);
        assertTrue(vertex.adjacentTo(target));
    }
    
    // Test equals()
    // covers different vertices
    @Test
    public void testEqualsWithDifferentVertices(){
        final String source = "Source";
        final String target = "Target";
        final Vertex<String> vertex1 = new Vertex<>(source);
        Vertex<String> vertex2 = new Vertex<>(target);
        assertFalse(vertex1.equals(vertex2));
        assertFalse(vertex2.equals(vertex1));
    }
    
    // Test equals()
    // covers same name of vertices
    @Test
    public void testEqualsWithSameName(){
        final String name = "Vertex";
        final Vertex<String> vertex1 = new Vertex<>(name);
        final Vertex<String> vertex2 = new Vertex<>(name);
        assertTrue(vertex1.equals(vertex2));
        assertTrue(vertex2.equals(vertex1));
    }
    
    // Test hashCode()
    // covers different vertices
    @Test
    public void testHashCodeWithDifferentVertices(){
        final String source = "Source";
        final String target = "Target";
        final Vertex<String> vertex1 = new Vertex<>(source);
        final Vertex<String> vertex2 = new Vertex<>(target);
        assertNotEquals(vertex1.hashCode(), vertex2.hashCode());
        assertNotEquals(vertex2.hashCode(), vertex1.hashCode());
    }
    
    // Test hashCode()
    // covers same vertices
    @Test
    public void testHashCodeWithSameVertices(){
        final String name = "Vertex";
        final Vertex<String> vertex1 = new Vertex<>(name);
        final Vertex<String> vertex2 = new Vertex<>(name);
        assertEquals(vertex1.hashCode(), vertex2.hashCode());
    }
}
