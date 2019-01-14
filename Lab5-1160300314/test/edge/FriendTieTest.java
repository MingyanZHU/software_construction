package edge;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import exception.InputFileAgainException;
import factory.EdgeFactory;
import factory.LoggerFactory;
import factory.VertexFactory;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import org.junit.Test;
import vertex.Person;
import vertex.Vertex;
import vertex.Word;

public class FriendTieTest {

  private static final Logger LOGGER = LoggerFactory.createLogger(ForwardTieTest.class);
  private static final String type = "FriendTie";

  /*
  Test strategy
  Partition for inputs of addVertices(vertices)
    vertices: null, only one Person, two Person, two different type vertex
 */

  @Test
  public void testFillVertexInfo() {
    Person source = null;
    Person destination = null;
    Word word = null;
    String label = "friend";
    double weight = 0.8;

    try {
      source = (Person) VertexFactory.createVertex("K", "Person", new String[]{"M", "20"});
      destination = (Person) VertexFactory.createVertex("V", "Person", new String[]{"F", "20"});
      word = new Word("World");
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      FriendTie friendTie = (FriendTie) EdgeFactory.createEdge(label, type, weight, null);

      assertEquals(label, friendTie.getLabel());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      FriendTie friendTie = (FriendTie) EdgeFactory
          .createEdge(label, type, weight, Arrays.asList(destination));

      assertEquals(label, friendTie.getLabel());
      assertTrue(friendTie.containVertex(destination));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      FriendTie friendTie = (FriendTie) EdgeFactory
          .createEdge(label, type, weight, Arrays.asList(source, destination));

      assertEquals(label, friendTie.getLabel());
      assertTrue(friendTie.containVertex(source));
      assertTrue(friendTie.containVertex(destination));

      Set<Vertex> vertexSet = new HashSet<>();
      vertexSet.add(source);
      vertexSet.add(destination);

      assertEquals(vertexSet.size(), friendTie.vertices().size());
      assertTrue(vertexSet.containsAll(friendTie.vertices()));

      assertEquals(1, friendTie.sourceVertices().size());
      assertTrue(friendTie.sourceVertices().contains(source));

      assertEquals(1, friendTie.targetVertices().size());
      assertTrue(friendTie.targetVertices().contains(destination));

      assertThat(friendTie.toString(), containsString("source=" + source.toString()));
      assertThat(friendTie.toString(), containsString("destination=" + destination.toString()));
      assertThat(friendTie.toString(), containsString(type));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      FriendTie friendTie = (FriendTie) EdgeFactory
          .createEdge(label, type, weight, Arrays.asList(word, source));

      assertTrue(friendTie.containVertex(word));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      FriendTie friendTie = (FriendTie) EdgeFactory
          .createEdge(label, type, weight + 1, Arrays.asList(source, destination));

      assertEquals(weight + 1, friendTie.getWeight(), 0.0001);
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testFriendTie() {
    try {
      Person source = new Person("Kevin");
      String[] args1 = {"M", "20"};
      source.fillVertexInfo(args1);

      Person destination = new Person("Vera");
      String[] args2 = {"F", "18"};
      destination.fillVertexInfo(args2);

      FriendTie friendTie = new FriendTie("KV", 0.01);
      friendTie.addVertices(Arrays.asList(source, destination));

      assertEquals("KV", friendTie.getLabel());
      assertTrue(friendTie.containVertex(source));
      assertTrue(friendTie.containVertex(destination));

      Set<Vertex> vertexSet = new HashSet<>();
      vertexSet.add(source);
      vertexSet.add(destination);

      assertEquals(vertexSet.size(), friendTie.vertices().size());
      assertTrue(friendTie.vertices().containsAll(vertexSet));

      assertEquals(1, friendTie.sourceVertices().size());
      assertTrue(friendTie.sourceVertices().contains(source));

      assertEquals(1, friendTie.targetVertices().size());
      assertTrue(friendTie.targetVertices().contains(destination));

      assertThat(friendTie.toString(), containsString("source=" + source.toString()));
      assertThat(friendTie.toString(), containsString("destination=" + destination.toString()));
      assertThat(friendTie.toString(), containsString("FriendTie"));
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
      double weight = 0.01;
      String label = "KV";
      Edge friendTie = EdgeFactory
          .createEdge(label, "FriendTie", weight, Arrays.asList(source, destination));

      assertEquals("KV", friendTie.getLabel());
      assertTrue(friendTie.containVertex(source));
      assertTrue(friendTie.containVertex(destination));

      Set<Vertex> vertexSet = new HashSet<>();
      vertexSet.add(source);
      vertexSet.add(destination);

      assertEquals(vertexSet.size(), friendTie.vertices().size());
      assertTrue(friendTie.vertices().containsAll(vertexSet));

      assertEquals(1, friendTie.sourceVertices().size());
      assertTrue(friendTie.sourceVertices().contains(source));

      assertEquals(1, friendTie.targetVertices().size());
      assertTrue(friendTie.targetVertices().contains(destination));

      assertThat(friendTie.toString(), containsString("source=" + source.toString()));
      assertThat(friendTie.toString(), containsString("destination=" + destination.toString()));
      assertThat(friendTie.toString(), containsString("FriendTie"));
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
      double weight = 0.01;
      String label = "KV";

      FriendTie friendTie = new FriendTie(label, weight);
      friendTie.addVertices(Arrays.asList(source, destination));

      Edge edge = EdgeFactory
          .createEdge(label, "FriendTie", weight, Arrays.asList(source, destination));

      assertTrue(edge.equals(friendTie));
      assertTrue(friendTie.equals(edge));

      assertEquals(edge.hashCode(), friendTie.hashCode());

      Edge edge1 = EdgeFactory
          .createEdge(label, "CommentTie", weight, Arrays.asList(source, destination));

      assertFalse(edge.equals(edge1));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}
