/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*; 

import org.hamcrest.*;

import java.util.Collections;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST NOT add constructors, fields, or non-@Test methods
 * to this class, or change the spec of {@link #emptyInstance()}. Your tests
 * MUST only obtain Graph instances by calling emptyInstance(). Your tests MUST
 * NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {

    // Testing strategy
    // TODO
    /*
     * Testing strategy Partition the directed graph as follows: Empty graph:
     * yes, no Strongly connected graph: yes, no Full graph: yes, no Ring
     * diagram: yes, no Weight of edges: all >0, all =0, some >0 and some =0,
     * illegal weight(negative or not an integer)
     * 
     * Partition for inputs of graph.add(input)
     *                          graph: empty graph, graph with vertices
     *                          input: new vertices, vertices existed in graph already
     *                          
     * Partition for inputs of graph.remove(input)
     *                          graph: empty graph, graph with vertices
     *                          input: new vertices, vertices existed in graph already
     *                                  without edges, vertices exited with edges.
     *                          
     * Partition for inputs of graph.set(source, target, weight)
     *                          graph: empty, graph with vertices
     *                          source: new vertex, vertex existed in graph already.
     *                          target: new vertex, vertex existed in graph already.
     *                          weight: 0, positive.
     *                          edge: new edge, edge existed in graph already.
     *                          
     * Partition for graph.vertices()
     *                  graph: empty graph, graph with vertices.
     *                  
     * Partition for inputs of graph.source(input)
     *                          graph: empty graph, graph with vertices
     *                          input: new vertex, vertex without any edge point to,
     *                                  vertex with edges point to
     *                                  
     * Partition for inputs of graph.target(input)
     *                          graph: empty graph, graph with vertices
     *                          input: new vertex, vertex without any edge start with,
     *                                  vertex with edges start with.
     */

    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();

    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    // 用于测试一个新生成的图 边集为空集
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices", Collections.emptySet(), emptyInstance().vertices());
    }

    // TODO other tests for instance methods of Graph

    // 用于测试向图中加入一个未加入过的新顶点的add方法
    // Test graph.add(input)
    // covers empty graph
    //        new vertex
    @Test
    public void testAddNewVertex() {
        final String vertex = "Vertex";
        final Graph<String> graph = emptyInstance();
        assertTrue(graph.add(vertex));
        assertFalse(graph.vertices().isEmpty());
        assertThat(graph.vertices(), hasItem(vertex));
    }

    // 用于测试向图中加入一个已加入过的顶点的add方法
    // Test graph.add(input)
    // covers graph with vertices
    //          vertices existed in graph
    @Test
    public void testAddExistedVertex() {
        final String vertex = "Vertex";
        final Graph<String> graph = emptyInstance();
        graph.add(vertex);
        assertThat(graph.vertices(), hasItem(vertex));
        assertFalse(graph.add(vertex));
    }

    // 用于测试从图中删去一个未加入过的顶点的remove方法
    // Test graph.remove(input)
    // covers empty graph
    //          new vertex
    @Test
    public void testRemoveNewVertex() {
        final String vertex = "Vertex";
        final Graph<String> graph = emptyInstance();
        assertFalse(graph.remove(vertex));
        assertEquals(Collections.EMPTY_SET, graph.vertices());
    }

    // 用于测试从图中删去一个已加入过的顶点且没有邻接的边的remove方法
    // Test graph.remove(input)
    // covers graph with vertices
    //          vertices existed without edges
    @Test
    public void testRemoveExsitedVertexWithoutEdges() {
        final String vertex = "Vertex";
        final Graph<String> graph = emptyInstance();
        graph.add(vertex);
        assertThat(graph.vertices(), hasItem(vertex));
        assertTrue(graph.remove(vertex));
        assertEquals(Collections.EMPTY_SET, graph.vertices());
    }

    // 用于测试从图中删去一个已加入过的顶点且有邻接的边的remove方法
    // Test graph.remove(input)
    // covers graph with vertices
    //          vertices existed with edges
    @Test
    public void testRemoveExsitedVertexWithEdges() {
        final String vertex1 = "Vertex1";
        final String vertex2 = "Vertex2";
        final int weight = 1;
        final Graph<String> graph = emptyInstance();
        graph.set(vertex1, vertex2, weight);
        assertThat(graph.vertices(), hasItem(vertex2));
        assertThat(graph.vertices(), hasItem(vertex1));
        assertThat(graph.sources(vertex2), Matchers.hasEntry(vertex1, weight));
        assertThat(graph.targets(vertex1), Matchers.hasEntry(vertex2, weight));
        assertTrue(graph.remove(vertex2));
        assertThat(graph.vertices(), hasItem(vertex1));
        assertFalse(graph.vertices().contains(vertex2));
        assertEquals(Collections.EMPTY_MAP, graph.targets(vertex1));
    }
    
    // 用于测试向一个图中加入一个权值为0的且两个顶点均未加入顶点集的set方法
    // Test graph.set(source, target, weight)
    // covers empty graph
    //          new source and new target
    //          new edge, weight 0
    @Test
    public void testSetEdgeWithNewVertexesWeightZero() {
        final String vertex1 = "Vertex1";
        final String vertex2 = "Vertex2";
        final int weight = 0;
        final Graph<String> graph = emptyInstance();
        assertEquals(0, graph.set(vertex1, vertex2, weight));
        assertEquals(Collections.EMPTY_SET, graph.vertices());
    }

    // 用于测试向一个图中加入一个权值为0的且两个顶点都已加入顶点集的set方法
    // Test graph.set(source, target, weight)
    // covers graph with vertices
    //          source and target existed in graph already
    //          new edge, weight 0
    @Test
    public void testSetEdgeWithNeitherVertexWeightZero() {
        final String vertex1 = "Vertex1";
        final String vertex2 = "Vertex2";
        final int weight = 0;
        final Graph<String> graph = emptyInstance();
        graph.add(vertex1);
        graph.add(vertex2);
        assertThat(graph.vertices(), hasItem(vertex1));
        assertThat(graph.vertices(), hasItem(vertex2));
        assertEquals(0, graph.set(vertex1, vertex2, weight));
        assertThat(graph.vertices(), hasItem(vertex1));
        assertThat(graph.vertices(), hasItem(vertex2));
        assertEquals(Collections.EMPTY_MAP, graph.sources(vertex2));
        assertEquals(Collections.EMPTY_MAP, graph.targets(vertex1));
    }

    // 用于测试向一个图中加入一个权值为0且两个顶点中有一个未加入顶点集的set方法
    // Test graph.set(source, target, weight)
    // covers graph with vertices
    //          source existed in graph and new target
    //          new edge, weight 0
    @Test
    public void testSetEdgeWithEitherVertexWeightZero() {
        final String vertex1 = "Vertex1";
        final String vertex2 = "Vertex2";
        final int weight = 0;
        final Graph<String> graph = emptyInstance();
        graph.add(vertex1);
        assertThat(graph.vertices(), hasItem(vertex1));
        assertFalse(graph.vertices().contains(vertex2));
        assertEquals(0, graph.set(vertex1, vertex2, weight));
        assertThat(graph.vertices(), hasItem(vertex1));
        assertFalse(graph.vertices().contains(vertex2));
        assertEquals(Collections.EMPTY_MAP, graph.targets(vertex1));
    }

    // 用于测试向一个图中加入一个权值为正数且两个顶点均未加入顶点集的set方法
    // Test graph.set(source, target, weight)
    // covers empty graph
    //          new source and new target
    //          new edge, weight positive
    @Test
    public void testSetEdgeWithNewVertexesWeightPositive() {
        final String vertex1 = "Vertex1";
        final String vertex2 = "Vertex2";
        final int weight = 1;
        final Graph<String> graph = emptyInstance();
        assertEquals(0, graph.set(vertex1, vertex2, weight));
        assertThat(graph.vertices(), hasItem(vertex1));
        assertThat(graph.vertices(), hasItem(vertex2));        
        assertThat(graph.targets(vertex1), Matchers.hasEntry(vertex2, weight));
        assertThat(graph.sources(vertex2), Matchers.hasEntry(vertex1, weight));
    }

    // 用于测试向一个图中加入一个权值为正数且两个顶点均已加入顶点集的set方法
    // Test graph.set(source, target, weight)
    // covers graph with vertices
    //          source and target in graph already
    //          new edge, weight positive
    @Test
    public void testSetEdgeWithNeitherVertexWeightPositive() {
        final String vertex1 = "Vertex1";
        final String vertex2 = "Vertex2";
        final int weight = 1;
        final Graph<String> graph = emptyInstance();
        graph.add(vertex1);
        graph.add(vertex2);
        assertEquals(0, graph.set(vertex1, vertex2, weight));
        assertThat(graph.vertices(), hasItem(vertex1));
        assertThat(graph.vertices(), hasItem(vertex2)); 
        assertThat(graph.targets(vertex1), Matchers.hasEntry(vertex2, weight));
        assertThat(graph.sources(vertex2), Matchers.hasEntry(vertex1, weight));
    }

    // 用于测试向一个图中加入一个权值为正数且两个顶点中有一个顶点未加入顶点集的set方法
    // Test graph.set(source, target, weight)
    // covers graph with vertices
    //          new source and target in graph
    //          new edge, weight positive
    @Test
    public void testSetEdgeWithEitherVertexWeightPositive() {
        final String vertex1 = "Vertex1";
        final String vertex2 = "Vertex2";
        final int weight = 1;
        final Graph<String> graph = emptyInstance();
        graph.add(vertex2);
        assertFalse(graph.vertices().contains(vertex1));
        assertThat(graph.vertices(), hasItem(vertex2));
        assertEquals(0, graph.set(vertex1, vertex2, weight));
        assertThat(graph.vertices(), hasItem(vertex1));
        assertThat(graph.vertices(), hasItem(vertex2));
        assertThat(graph.targets(vertex1), Matchers.hasEntry(vertex2, weight));
        assertThat(graph.sources(vertex2), Matchers.hasEntry(vertex1, weight));
    }
    
    // Test graph.set(source, target, weight)
    // covers graph with vertices
    //          source and target in graph
    //          edge existed, weight 0
    @Test
    public void testSetEdgeWeightZero(){
        final String vertex1 = "Vertex1";
        final String vertex2 = "Vertex2";
        final int weight = 1;
        final Graph<String> graph = emptyInstance();
        graph.add(vertex1);
        graph.add(vertex2);
        graph.set(vertex1, vertex2, weight);
        assertThat(graph.vertices(), hasItem(vertex1));
        assertThat(graph.vertices(), hasItem(vertex2));
        assertEquals(weight, graph.set(vertex1, vertex2, 0));
        assertThat(graph.vertices(), hasItem(vertex1));
        assertThat(graph.vertices(), hasItem(vertex2));
    }
    
    // Test graph.set(source, target, weight)
    // covers graph with vertices
    //          source and target in graph
    //          edge existed, weight positive
    @Test
    public void testSetEdgeWeightPositive(){
        final String vertex1 = "Vertex1";
        final String vertex2 = "Vertex2";
        int weight = 1;
        final Graph<String> graph = emptyInstance();
        graph.add(vertex1);
        graph.add(vertex2);
        graph.set(vertex1, vertex2, weight);
        assertThat(graph.vertices(), hasItem(vertex1));
        assertThat(graph.vertices(), hasItem(vertex2));
        assertThat(graph.sources(vertex2), Matchers.hasEntry(vertex1, weight));
        assertThat(graph.targets(vertex1), Matchers.hasEntry(vertex2, weight));
        assertEquals(weight, graph.set(vertex1, vertex2, ++weight));
        assertThat(graph.vertices(), hasItem(vertex1));
        assertThat(graph.vertices(), hasItem(vertex2));
        assertThat(graph.sources(vertex2), Matchers.hasEntry(vertex1, weight));
        assertThat(graph.targets(vertex1), Matchers.hasEntry(vertex2, weight));
    }
    // 用于测试一个图中没有边邻接的顶点的source方法
    // Test graph.source(input)
    // covers empty graph
    //          new vertex
    @Test
    public void testSourcesWithoutEdge() {
        final String vertex = "Vertex";
        final Graph<String> graph = emptyInstance();
        assertEquals(Collections.EMPTY_SET, graph.vertices());
        assertEquals(Collections.EMPTY_MAP, graph.sources(vertex));
    }

    // 用于测试一个图中没有边邻接的顶点的target方法
    // Test graph.target(input)
    // covers empty graph
    //          new vertex
    @Test
    public void testTargetsWithoutEdge() {
        final String vertex = "Vertex";
        final Graph<String> graph = emptyInstance();
        assertEquals(Collections.EMPTY_SET, graph.vertices());
        assertEquals(Collections.EMPTY_MAP, graph.targets(vertex));
    }

    // 用于测试一个图中有边邻接的顶点的source方法
    // Test graph.source(input)
    // covers graph with vertices
    //          vertex without edges target to
    @Test
    public void testSourcesWithoutEdgePointTo() {
        final String vertex1 = "Vertex1";
        final String vertex2 = "Vertex2";
        final String vertex3 = "Vertex3";
        final int weight31 = 1;
        final int weight32 = 2;
        final Graph<String> graph = emptyInstance();
        graph.set(vertex3, vertex1, weight31);
        graph.set(vertex3, vertex2, weight32);
        graph.add(vertex3);
        assertEquals(Collections.EMPTY_MAP, graph.sources(vertex3));
    }
    
    // 用于测试一个图中有边邻接的顶点的source方法
    // Test graph.source(input)
    // covers graph with vertices
    //          vertex with edges target to
    @Test
    public void testSourcesWithEdgePointTo() {
        final String vertex1 = "Vertex1";
        final String vertex2 = "Vertex2";
        final String vertex3 = "Vertex3";
        final int weight13 = 1;
        final int weight23 = 2;
        final Graph<String> graph = emptyInstance();
        graph.set(vertex1, vertex3, weight13);
        graph.set(vertex2, vertex3, weight23);
        graph.add(vertex3);
        assertThat(graph.vertices(), hasItem(vertex3));
        assertThat(graph.sources(vertex3), Matchers.hasEntry(vertex1, weight13));
        assertThat(graph.sources(vertex3), Matchers.hasEntry(vertex2, weight23));
    }

    // 用于测试一个图中有边邻接的顶点的方法
    // Test graph.target(input)
    // covers graph with vertices
    //          vertex without edge start with
    @Test
    public void testTargetsWithoutEdgeStartWith() {
        final String vertex1 = "Vertex1";
        final String vertex2 = "Vertex2";
        final String vertex3 = "Vertex3";
        final int weight13 = 1;
        final int weight23 = 2;
        final Graph<String> graph = emptyInstance();
        graph.set(vertex1, vertex3, weight13);
        graph.set(vertex2, vertex3, weight23);
        graph.add(vertex3);
        assertEquals(Collections.EMPTY_MAP, graph.targets(vertex3));
    }
    
    // 用于测试一个图中有边邻接的顶点的方法
    // Test graph.target(input)
    // covers graph with vertices
    //          vertex with edge start with
    @Test
    public void testTargetsWithEdgeStartWith() {
        final String vertex1 = "Vertex1";
        final String vertex2 = "Vertex2";
        final String vertex3 = "Vertex3";
        final int weight31 = 1;
        final int weight32 = 2;
        final Graph<String> graph = emptyInstance();
        graph.set(vertex3, vertex1, weight31);
        graph.set(vertex3, vertex2, weight32);
        graph.add(vertex3);
        assertThat(graph.vertices(), hasItem(vertex3));
        assertThat(graph.targets(vertex3), Matchers.hasEntry(vertex1, weight31));
        assertThat(graph.targets(vertex3), Matchers.hasEntry(vertex2, weight32));
    }
}
