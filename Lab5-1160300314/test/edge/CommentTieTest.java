package edge;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
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

public class CommentTieTest {

  private static final Logger LOGGER = LoggerFactory.createLogger(CommentTieTest.class);
  private static final String type = "CommentTie";

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
      CommentTie commentTie = (CommentTie) EdgeFactory.createEdge(label, type, weight, null);

      assertEquals(label, commentTie.getLabel());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      CommentTie commentTie = (CommentTie) EdgeFactory
          .createEdge(label, type, weight, Arrays.asList(destination));

      assertEquals(label, commentTie.getLabel());
      assertTrue(commentTie.containVertex(destination));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      CommentTie commentTie = (CommentTie) EdgeFactory
          .createEdge(label, type, weight, Arrays.asList(source, destination));

      assertEquals(label, commentTie.getLabel());
      assertTrue(commentTie.containVertex(source));
      assertTrue(commentTie.containVertex(destination));

      Set<Vertex> vertexSet = new HashSet<>();
      vertexSet.add(source);
      vertexSet.add(destination);

      assertEquals(vertexSet.size(), commentTie.vertices().size());
      assertTrue(vertexSet.containsAll(commentTie.vertices()));

      assertEquals(1, commentTie.sourceVertices().size());
      assertTrue(commentTie.sourceVertices().contains(source));

      assertEquals(1, commentTie.targetVertices().size());
      assertTrue(commentTie.targetVertices().contains(destination));

      assertThat(commentTie.toString(), containsString("source=" + source.toString()));
      assertThat(commentTie.toString(), containsString("destination=" + destination.toString()));
      assertThat(commentTie.toString(), containsString(type));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      CommentTie commentTie = (CommentTie) EdgeFactory
          .createEdge(label, type, weight, Arrays.asList(word, source));

      assertTrue(commentTie.containVertex(word));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      CommentTie commentTie = (CommentTie) EdgeFactory
          .createEdge(label, type, weight + 1, Arrays.asList(source, destination));

      assertEquals(weight + 1, commentTie.getWeight(), 0.0001);
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testCommentTie() {
    try {
      Person source = new Person("Kevin");
      String[] args1 = {"M", "20"};
      source.fillVertexInfo(args1);

      Person destination = new Person("Vera");
      String[] args2 = {"F", "18"};
      destination.fillVertexInfo(args2);

      String label = "KV";
      double weight = 0.2;
      CommentTie commentTie = new CommentTie(label, weight);
      commentTie.addVertices(Arrays.asList(source, destination));

      assertEquals("KV", commentTie.getLabel());
      assertTrue(commentTie.containVertex(source));
      assertTrue(commentTie.containVertex(destination));

      Set<Vertex> vertexSet = new HashSet<>();
      vertexSet.add(source);
      vertexSet.add(destination);

      assertEquals(vertexSet.size(), commentTie.vertices().size());
      assertTrue(vertexSet.containsAll(commentTie.vertices()));

      assertEquals(1, commentTie.sourceVertices().size());
      assertTrue(commentTie.sourceVertices().contains(source));

      assertEquals(1, commentTie.targetVertices().size());
      assertTrue(commentTie.targetVertices().contains(destination));

      assertThat(commentTie.toString(), containsString("source=" + source.toString()));
      assertThat(commentTie.toString(), containsString("destination=" + destination.toString()));
      assertThat(commentTie.toString(), containsString("CommentTie"));
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

      Edge commentTie = EdgeFactory
          .createEdge(label, "CommentTie", weight, Arrays.asList(source, destination));

      assertEquals("KV", commentTie.getLabel());
      assertTrue(commentTie.containVertex(source));
      assertTrue(commentTie.containVertex(destination));

      Set<Vertex> vertexSet = new HashSet<>();
      vertexSet.add(source);
      vertexSet.add(destination);

      assertEquals(vertexSet.size(), commentTie.vertices().size());
      assertTrue(vertexSet.containsAll(commentTie.vertices()));

      assertEquals(1, commentTie.sourceVertices().size());
      assertTrue(commentTie.sourceVertices().contains(source));

      assertEquals(1, commentTie.targetVertices().size());
      assertTrue(commentTie.targetVertices().contains(destination));

      assertThat(commentTie.toString(), containsString("source=" + source.toString()));
      assertThat(commentTie.toString(), containsString("destination=" + destination.toString()));
      assertThat(commentTie.toString(), containsString("CommentTie"));
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
      CommentTie commentTie = new CommentTie(label, weight);
      commentTie.addVertices(Arrays.asList(source, destination));
      Edge edge = EdgeFactory
          .createEdge(label, "CommentTie", weight, Arrays.asList(source, destination));

      assertTrue(edge.equals(commentTie));
      assertTrue(commentTie.equals(edge));

      assertEquals(edge.hashCode(), commentTie.hashCode());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }


  }
}
