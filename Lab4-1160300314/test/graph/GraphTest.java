package graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import edge.CommentTie;
import edge.FriendTie;
import edge.HyperEdge;
import edge.WordNeighborhood;
import exception.InputFileAgainException;
import factory.GraphFactory;
import factory.LoggerFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.Test;
import vertex.Person;
import vertex.Vertex;
import vertex.Word;

public class GraphTest {

  private static final Logger LOGGER = LoggerFactory.createLogger(GraphTest.class);

  /*
  Test strategy for Graph and ConcreteGraph:
    Partition for inputs of addVertex():
      null, normal vertex.
    Partition for Constructor of ConcreteGraph:
      null, normal String.
    Partition for inputs of removeVertex()
      null, vertex without any edges connected, vertex with normal edge connected,
      vertex with hyperEdge connected.
    Partition fot inputs of vertices()
      empty vertex set, normal vertex set with vertices.
    Partition for inputs of targets()
      without multi edges, with multi edges
    Partition for inputs of sources()
      without multi edges, with multi edges
   */

  @Test
  public void testTargets() {
    ConcreteGraph concreteGraph = new ConcreteGraph("world");

    try {
      Person person =  new Person("Guy");
      Person person1  = new Person("Gay");

      CommentTie commentTie = new CommentTie("cc", 1);
      commentTie.addVertices(Arrays.asList(person, person1));

      concreteGraph.addVertex(person);
      concreteGraph.addVertex(person1);
      concreteGraph.addEdge(commentTie);

      List<?> list = Collections.singletonList(concreteGraph.targets(person).get(person1));
      assertEquals("[1.0]", list.get(0).toString());
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      ConcreteGraph graph = new ConcreteGraph("HHH");
      Person person =  new Person("Guy");
      Person person1  = new Person("Gay");

      CommentTie commentTie = new CommentTie("cc", 1);
      commentTie.addVertices(Arrays.asList(person, person1));

      FriendTie friendTie = new FriendTie("ff", 0.5);
      friendTie.addVertices(Arrays.asList(person, person1));

      graph.addEdge(commentTie);
      graph.addEdge(friendTie);

      assertEquals(1, graph.targets(person).entrySet().size());
      System.out.println(graph.targets(person).get(person));
      assertEquals(2, new ArrayList<Double>(
          (Collection<? extends Double>) graph.targets(person).get(person1)).size());
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
  @Test
  public void testVertices() {
    ConcreteGraph concreteGraph = new ConcreteGraph("World");

    assertEquals(Collections.EMPTY_SET, concreteGraph.vertices());

    try {
      Word word = new Word("Hello");
      concreteGraph.addVertex(word);

      assertEquals(1, concreteGraph.vertices().size());
      assertTrue(concreteGraph.vertices().contains(word));
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testRemoveVertex() {
    try {
      ConcreteGraph concreteGraph = new ConcreteGraph("Words");
      Word word = null;

      assertFalse(concreteGraph.removeVertex(word));
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      ConcreteGraph concreteGraph = new ConcreteGraph("words");

      Word word = new Word("Hello");

      assertTrue(concreteGraph.addVertex(word));
      assertTrue(concreteGraph.removeVertex(word));
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      ConcreteGraph concreteGraph = new ConcreteGraph("Words");
      Word word = new Word("Hello");
      Word word1 = new Word("World");
      concreteGraph.addVertex(word);
      concreteGraph.addVertex(word1);
      WordNeighborhood wordNeighborhood = new WordNeighborhood("edge", 1);

      wordNeighborhood.addVertices(Arrays.asList(word, word1));
      concreteGraph.addEdge(wordNeighborhood);
      assertEquals(1, concreteGraph.edges().size());
      assertTrue(concreteGraph.removeVertex(word));
      assertEquals(Collections.EMPTY_SET, concreteGraph.edges());
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      ConcreteGraph concreteGraph = new ConcreteGraph("Words");
      Word word = new Word("Hello");
      Word word1 = new Word("World");
      Word word2 = new Word("Hey");
      concreteGraph.addVertex(word);
      concreteGraph.addVertex(word1);
      concreteGraph.addVertex(word2);

      HyperEdge edge = new HyperEdge("HelloWorld", -1);
      HyperEdge edge1 = new HyperEdge("HelloWord", -1);
      edge.addVertices(Arrays.asList(word, word1, word2));
      edge1.addVertices(Arrays.asList(word, word1));

      concreteGraph.addEdge(edge);
      concreteGraph.addEdge(edge1);
      assertEquals(2, concreteGraph.edges().size());
      assertTrue(concreteGraph.removeVertex(word2));
      assertEquals(2, concreteGraph.edges().size());
      assertTrue(concreteGraph.removeVertex(word1));
      assertEquals(1, concreteGraph.edges().size());
    } catch (Exception e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testConstructor() {
    try {
      ConcreteGraph concreteGraph = new ConcreteGraph(null);
      System.out.println(concreteGraph);
    } catch (Throwable e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testAddVertex() {
    try {
      Word word;
      ConcreteGraph concreteGraph = new ConcreteGraph("Hello");
      word = new Word("World");

      assertEquals(Collections.EMPTY_SET, concreteGraph.vertices());

      boolean answer = concreteGraph.addVertex(word);

      assertTrue(answer);
      assertEquals(1, concreteGraph.vertices().size());

    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Word word = null;
      ConcreteGraph graph = new ConcreteGraph("World");
      assertNull(word);

      assertEquals(Collections.EMPTY_SET, graph.vertices());
      graph.addVertex(word);
    } catch (RuntimeException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testConcreteGraph() {
    ConcreteGraph concreteGraph = new ConcreteGraph("Hello");
    Word word;
    Word word1;
    Word word2;
    try {
      word = new Word("H");
      word1 = new Word("e");
      word2 = new Word("L");

      assertTrue(concreteGraph.addVertex(word));
      assertTrue(concreteGraph.addVertex(word1));
      assertTrue(concreteGraph.addVertex(word2));

      HyperEdge hyperEdge = new HyperEdge("hyper", -1);
      hyperEdge.addVertices(Arrays.asList(word, word1));
      assertTrue(concreteGraph.addEdge(hyperEdge));
      assertTrue(concreteGraph.removeVertex(word));
      hyperEdge.addVertices(Arrays.asList(word, word1, word2));
      concreteGraph.addVertex(word);
      assertTrue(concreteGraph.addEdge(hyperEdge));
      concreteGraph.removeVertex(word);
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testEmpty() {
    Graph graph = Graph.empty();
    //    System.out.println(graph.toString());
    assertEquals("", graph.getGraphName());
  }

  @Test
  public void testMultiEdge() {
    try {
      Graph graph = GraphFactory.createGraph("src/txt/inputGraphPoet.txt");
      System.out.println(graph.edges());
    } catch (InputFileAgainException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

  }
}