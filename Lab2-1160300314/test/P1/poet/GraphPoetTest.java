/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.poet;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*; 

import java.io.File;

import java.io.IOException;

import org.junit.Test;

/**
 * Tests for GraphPoet.
 */
public class GraphPoetTest {
    
    // Testing strategy
    //   TODO
    // Partition for constructor of GraphPoet
    //              empty file, one line and several lines
    // 
    // Partition for graph of poem
    //              empty graph, a directed tree and a directed graph with rings
    // 
    // Partition for graphPoet.poem(input)
    //              empty string, one word, and several words
    //
    // Partition for graphPoet.toString()
    //              empty graph, a directed tree and a directed graph with rings
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    // TODO tests
    @Test
    public void testSampleTests() throws IOException{
        final GraphPoet nimoy = new GraphPoet(new File("test/P1/poet/mugar-omni-theater.txt"));
        final String input = "Test the system.";
        final String output = "Test of the system.";
        assertEquals(output, nimoy.poem(input));
    }
    
    @Test
    public void testOnlineSamples() throws IOException{
        final GraphPoet graphPoet = new GraphPoet(new File("test/P1/poet/seven-words.txt"));
        final String input = "Seek to explore new and exciting synergies!";
        final String output = graphPoet.poem(input);
        assertThat(output, containsString("strange"));
        assertThat(output, containsString("life"));
//        System.out.println(output);
    }
    
    // covers empty file
    //          empty graph
    //          empty string
    @Test
    public void testEmptyFileEmptyString() throws IOException{
        final GraphPoet graphPoet = new GraphPoet(new File("test/P1/poet/empty.txt"));
        final String input = "";
        assertEquals(0, graphPoet.poem(input).length());
    }
    
    // covers empty file
    //          empty graph
    //          one word
    @Test
    public void testEmptyFileOneWord() throws IOException{
        final GraphPoet graphPoet = new GraphPoet(new File("test/P1/poet/empty.txt"));
        final String input = "one";
        assertEquals(input, graphPoet.poem(input));
    }
    
    // covers one line
    //          directed tree
    //          one word
    @Test
    public void testOneLineTreeOneWord() throws IOException{
        final GraphPoet graphPoet = new GraphPoet(new File("test/P1/poet/one-line-tree.txt"));
        final String input = "One";
        assertEquals(input, graphPoet.poem(input));
    }
    
    // covers one line
    //          directed tree
    //          several words
    @Test
    public void testOneLineTreeSeveralWords() throws IOException{
        final GraphPoet graphPoet = new GraphPoet(new File("test/P1/poet/one-line-tree.txt"));
        final String input = "One three";
        final String output = graphPoet.poem(input);
        assertThat(output, containsString("One"));
        assertThat(output, containsString("two"));
        assertThat(output, containsString("three"));
    }
    
    // covers several lines
    //          directed tree
    //          one word
    @Test
    public void testSeveralLinesTreeOneWord() throws IOException{
        final GraphPoet graphPoet = new GraphPoet(new File("test/P1/poet/lines-tree.txt"));
        final String input = "eleven";
        assertEquals(input, graphPoet.poem(input));
    }
    
    // covers several lines
    //          directed tree
    //          several words
    @Test
    public void testSeveralLinesTreeSeveralWords() throws IOException{
        final GraphPoet graphPoet = new GraphPoet(new File("test/P1/poet/lines-tree.txt"));
        final String input = "Ten twelve";
        final String output = graphPoet.poem(input);
        assertThat(output, containsString("Ten"));
        assertThat(output, containsString("twelve"));
        assertThat(output, containsString("eleven"));
    }
    
    // covers one line
    //          directed graph with rings
    //          one word
    @Test
    public void testOneLineGraphOneWord() throws IOException{
        final GraphPoet graphPoet = new GraphPoet(new File("test/P1/poet/one-line-graph.txt"));
        final String input = "one";
        assertEquals(input, graphPoet.poem(input));
    }
    
    // covers several lines
    //          directed graph with rings
    //          several words
    @Test
    public void testSeveralLinesGraphSeveralWords() throws IOException{
        final GraphPoet graphPoet = new GraphPoet(new File("test/P1/poet/lines-graph.txt"));
        final String input = "One one";
        final String output = graphPoet.poem(input);
        assertThat(output, containsString("One"));
        assertThat(output, containsString("one"));
        assertThat(output, containsString("two"));
    }
    
    // Test graphPoet.toString()
    // covers empty graph
    @Test
    public void testToStringEmptyGraph() throws IOException{
        final GraphPoet graphPoet = new GraphPoet(new File("test/P1/poet/empty.txt"));
        assertThat(graphPoet.toString(), containsString("0 vertice(s)"));
        assertThat(graphPoet.toString(), containsString("0 edge(s)"));
    }
    
    // Test graphPoet.toString()
    // covers a directed tree
    @Test
    public void testToStringTree() throws IOException{
        final GraphPoet graphPoet = new GraphPoet(new File("test/P1/poet/lines-tree.txt"));
        assertThat(graphPoet.toString(), containsString("five"));
        assertThat(graphPoet.toString(), containsString("101 vertice(s)"));
        assertThat(graphPoet.toString(), containsString("\"one\" -> \"two\": 1"));
        
    }
    // Test a real poem
    @Test
    public void testRealPoem() throws IOException{
        //Desiderata by Max Ehrmann, 1927
        final GraphPoet graphPoet = new GraphPoet(new File("test/P1/poet/Desiderata.txt"));
        final String input = "a world.";
        final String output = graphPoet.poem(input);
        assertThat(output, containsString("a"));
        assertThat(output, containsString("world."));
        assertThat(output, containsString("beautiful"));
//        System.out.println(graphPoet.toString());
    }
}
