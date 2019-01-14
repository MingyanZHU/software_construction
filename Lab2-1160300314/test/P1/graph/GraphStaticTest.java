/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.*; 

/**
 * Tests for static methods of Graph.
 * 
 * To facilitate testing multiple implementations of Graph, instance methods are
 * tested in GraphInstanceTest.
 */
public class GraphStaticTest {
    
    // Testing strategy
    //   empty()
    //     no inputs, only output is empty graph
    //     observe with vertices()
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testEmptyVerticesEmpty() {
        assertEquals("expected empty() graph to have no vertices",
                Collections.emptySet(), Graph.empty().vertices());
    }
    
    // TODO test other vertex label types in Problem 3.2
    @Test
    public void testIntegerAsLabel(){
        final Graph<Integer> graph = Graph.empty();
        final int vertex1 = 1;
        final int vertex2 = 2;
        final int vertex3 = 3;
        final int weight31 = 32767;
        final int weight12 = 100;
        
        assertTrue(graph.add(vertex1));
        assertTrue(graph.add(vertex2));
        assertEquals(0, graph.set(vertex1, vertex2, weight12));
        assertEquals(0, graph.set(vertex3, vertex1, weight31));
        assertEquals(weight31, graph.set(vertex3, vertex1, weight12));   
        assertThat(graph.vertices(), hasItem(vertex3));
        assertTrue(graph.remove(vertex3));
        assertFalse(graph.vertices().contains(vertex3));
    }
    
    @Test
    public void testLongAsLabel(){
        final Graph<Long> graph = Graph.empty();
        final long vertex1 = 1 + (long)Integer.MAX_VALUE;
        final long vertex2 = 2 + (long)Integer.MAX_VALUE;
        final long vertex3 = 3 + (long)Integer.MAX_VALUE;
        final int weight31 = 100;
        final int weight12 = 32767;
        
        assertTrue(graph.add(vertex1));
        assertTrue(graph.add(vertex2));
        assertEquals(0, graph.set(vertex1, vertex2, weight12));
        assertEquals(0, graph.set(vertex3, vertex1, weight31));
        assertEquals(weight31, graph.set(vertex3, vertex1, weight12));   
        assertThat(graph.vertices(), hasItem(vertex3));
        assertTrue(graph.remove(vertex3));
        assertFalse(graph.vertices().contains(vertex3));
        
    }
}
