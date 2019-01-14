package edge;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import exception.InputFileAgainException;
import factory.EdgeFactory;
import factory.LoggerFactory;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import org.junit.Test;
import vertex.Person;
import vertex.Vertex;
import vertex.Word;

public class ForwardTieTest {

  private static final Logger LOGGER = LoggerFactory.createLogger(ForwardTieTest.class);
  private static final String type = "ForwardTie";

  /*
 Test strategy
 Partition for inputs of addVertices(vertices)
     vertices: null, only one Person, two Person, two different type vertex
  */
  @Test
  public void testAddVertices() {
    Person source = null;
    Person destination = null;
    Word word = null;
    String label = "forward";
    double weight = 0.5;
    try {
      source = new Person("Kevin");
      String[] args1 = {"M", "20"};
      source.fillVertexInfo(args1);

      destination = new Person("Vera");
      String[] args2 = {"F", "18"};
      destination.fillVertexInfo(args2);

      word = new Word("Hello");
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      ForwardTie forwardTie = (ForwardTie) EdgeFactory.createEdge(label, type, weight, null);

      assertEquals(label, forwardTie.getLabel());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      ForwardTie forwardTie = (ForwardTie) EdgeFactory
          .createEdge(label, type, weight, Arrays.asList(source));

      assertEquals(label, forwardTie.getLabel());
      assertTrue(forwardTie.containVertex(source));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      ForwardTie forwardTie = (ForwardTie) EdgeFactory
          .createEdge(label, type, weight, Arrays.asList(source, destination));

      assertEquals(label, forwardTie.getLabel());
      assertTrue(forwardTie.containVertex(source));
      assertTrue(forwardTie.containVertex(destination));

      Set<Vertex> vertexSet = new HashSet<>();
      vertexSet.add(source);
      vertexSet.add(destination);

      assertEquals(vertexSet.size(), forwardTie.vertices().size());
      assertTrue(vertexSet.containsAll(forwardTie.vertices()));

      assertEquals(1, forwardTie.sourceVertices().size());
      assertTrue(forwardTie.sourceVertices().contains(source));

      assertEquals(1, forwardTie.targetVertices().size());
      assertTrue(forwardTie.targetVertices().contains(destination));

      assertThat(forwardTie.toString(), containsString("source=" + source.toString()));
      assertThat(forwardTie.toString(), containsString("destination=" + destination.toString()));
      assertThat(forwardTie.toString(), containsString("ForwardTie"));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      ForwardTie forwardTie = (ForwardTie) EdgeFactory
          .createEdge(label, type, weight, Arrays.asList(word, source));

      assertTrue(forwardTie.containVertex(word));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      ForwardTie forwardTie = (ForwardTie) EdgeFactory
          .createEdge(label, type, weight + 1, Arrays.asList(source, destination));

      assertEquals(weight + 1, forwardTie.getWeight(), 0.0001);
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testForwardTie() {
    try {
      Person source = new Person("Kevin");
      String[] args1 = {"M", "20"};
      source.fillVertexInfo(args1);

      Person destination = new Person("Vera");
      String[] args2 = {"F", "18"};
      destination.fillVertexInfo(args2);

      String label = "KV";
      double weight = 0.2;
      ForwardTie forwardTie = new ForwardTie(label, weight);
      forwardTie.addVertices(Arrays.asList(source, destination));

      assertEquals("KV", forwardTie.getLabel());
      assertTrue(forwardTie.containVertex(source));
      assertTrue(forwardTie.containVertex(destination));

      Set<Vertex> vertexSet = new HashSet<>();
      vertexSet.add(source);
      vertexSet.add(destination);

      assertEquals(vertexSet.size(), forwardTie.vertices().size());
      assertTrue(vertexSet.containsAll(forwardTie.vertices()));

      assertEquals(1, forwardTie.sourceVertices().size());
      assertTrue(forwardTie.sourceVertices().contains(source));

      assertEquals(1, forwardTie.targetVertices().size());
      assertTrue(forwardTie.targetVertices().contains(destination));

      assertThat(forwardTie.toString(), containsString("source=" + source.toString()));
      assertThat(forwardTie.toString(), containsString("destination=" + destination.toString()));
      assertThat(forwardTie.toString(), containsString("ForwardTie"));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testEdgeFactory() {
    try {
      Person source = new Person("Kevin");
      String[] args1 = {"M", "20"};
      source.fillVertexInfo(args1);

      Person destination = new Person("Vera");
      String[] args2 = {"F", "18"};
      destination.fillVertexInfo(args2);

      String label = "KV";
      double weight = 0.2;

      Edge forwardTie = EdgeFactory
          .createEdge(label, "ForwardTie", weight, Arrays.asList(source, destination));

      assertEquals("KV", forwardTie.getLabel());
      assertTrue(forwardTie.containVertex(source));
      assertTrue(forwardTie.containVertex(destination));

      Set<Vertex> vertexSet = new HashSet<>();
      vertexSet.add(source);
      vertexSet.add(destination);

      assertEquals(vertexSet.size(), forwardTie.vertices().size());
      assertTrue(vertexSet.containsAll(forwardTie.vertices()));

      assertEquals(1, forwardTie.sourceVertices().size());
      assertTrue(forwardTie.sourceVertices().contains(source));

      assertEquals(1, forwardTie.targetVertices().size());
      assertTrue(forwardTie.targetVertices().contains(destination));

      assertThat(forwardTie.toString(), containsString("source=" + source.toString()));
      assertThat(forwardTie.toString(), containsString("destination=" + destination.toString()));
      assertThat(forwardTie.toString(), containsString("ForwardTie"));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testOverride() {
    try {
      Person source = new Person("Kevin");
      String[] args1 = {"M", "20"};
      source.fillVertexInfo(args1);

      Person destination = new Person("Vera");
      String[] args2 = {"F", "18"};
      destination.fillVertexInfo(args2);

      String label = "KV";
      double weight = 0.2;
      ForwardTie forwardTie = new ForwardTie(label, weight);
      forwardTie.addVertices(Arrays.asList(source, destination));
      Edge edge = EdgeFactory
          .createEdge(label, "ForwardTie", weight, Arrays.asList(source, destination));

      assertEquals(edge, forwardTie);
      assertEquals(forwardTie, edge);

      assertEquals(edge.hashCode(), forwardTie.hashCode());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}
