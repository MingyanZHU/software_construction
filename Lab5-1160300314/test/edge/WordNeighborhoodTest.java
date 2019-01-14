package edge;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import exception.InputFileAgainException;
import factory.EdgeFactory;
import factory.LoggerFactory;
import factory.VertexFactory;
import java.util.Arrays;
import java.util.Collections;
import org.apache.log4j.Logger;
import org.junit.Test;
import vertex.Person;
import vertex.Word;

public class WordNeighborhoodTest {

  private static final Logger LOGGER = LoggerFactory.createLogger(WordNeighborhoodTest.class);
  private static final String type = "WordNeighborhood";
  private static final double delta = 0.0001;

  /*
  Test strategy
  Partition for inputs of addVertices(vertices)
      vertices: null, only one Word, two Words, two different type vertex
   */
  @Test
  public void testAddVertices() {
    Person person = null;
    Word word1 = null;
    Word word2 = null;
    String label = "wordConnection";
    double weight = 1;
    try {
      word1 = new Word("Hello");
      word2 = new Word("World");
      person = (Person) VertexFactory.createVertex("Rainy", "Person", new String[]{"M", "18"});
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      WordNeighborhood wordNeighborhood = (WordNeighborhood) EdgeFactory
          .createEdge(label, type, weight, null);

      assertEquals(label, wordNeighborhood.getLabel());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      WordNeighborhood wordNeighborhood = (WordNeighborhood) EdgeFactory
          .createEdge(label, type, weight, Collections.singletonList(word1));

      assertEquals(label, wordNeighborhood.getLabel());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      WordNeighborhood wordNeighborhood = (WordNeighborhood) EdgeFactory
          .createEdge(label, type, weight, Arrays.asList(word1, word2));

      assertEquals(label, wordNeighborhood.getLabel());
      assertEquals(weight, wordNeighborhood.getWeight(), delta);
      assertTrue(wordNeighborhood.sourceVertices().contains(word1));
      assertTrue(wordNeighborhood.targetVertices().contains(word2));
      assertTrue(wordNeighborhood.containVertex(word1));
      assertTrue(wordNeighborhood.containVertex(word2));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      WordNeighborhood wordNeighborhood = (WordNeighborhood) EdgeFactory
          .createEdge(label, type, weight, Arrays.asList(word1, person));

      assertTrue(wordNeighborhood.sourceVertices().contains(word1));
      assertTrue(wordNeighborhood.targetVertices().contains(word2));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      WordNeighborhood wordNeighborhood = (WordNeighborhood) EdgeFactory
          .createEdge(label, type, -1, Arrays.asList(word1, word2));

      assertEquals(-1, wordNeighborhood.getWeight(), delta);
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      WordNeighborhood wordNeighborhood = (WordNeighborhood) EdgeFactory
          .createEdge(label, type, weight, Arrays.asList(word1, word2, person));

      assertTrue(wordNeighborhood.containVertex(word1));
      assertTrue(wordNeighborhood.containVertex(word2));
      assertTrue(wordNeighborhood.containVertex(person));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testWordNeighborhood() {
    String label = "WordN";
    try {
      Word word1 = new Word("WORD1");
      Word word2 = new Word("WORD2");
      double weight = 1;
      WordNeighborhood wordNeighborhood = new WordNeighborhood(label, weight);
      wordNeighborhood.addVertices(Arrays.asList(word1, word2));

      assertEquals(label, wordNeighborhood.getLabel());
      assertEquals(Double.doubleToLongBits(weight),
          Double.doubleToLongBits(wordNeighborhood.getWeight()));

      assertTrue(wordNeighborhood.sourceVertices().contains(word1));
      assertTrue(wordNeighborhood.targetVertices().contains(word2));
      assertTrue(wordNeighborhood.vertices().containsAll(Arrays.asList(word1, word2)));
      assertEquals(2, wordNeighborhood.vertices().size());

      assertThat(wordNeighborhood.toString(), containsString("source=" + word1.toString()));
      assertThat(wordNeighborhood.toString(), containsString("destination=" + word2.toString()));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testEdgeFactory() {
    String label = "WordN";
    try {
      Word word1 = new Word("WORD1");
      Word word2 = new Word("WORD2");
      double weight = 1;

      Edge wordNeighborhood = EdgeFactory
          .createEdge(label, "WordNeighborhood", weight, Arrays.asList(word1, word2));

      assertEquals(label, wordNeighborhood.getLabel());
      assertEquals(Double.doubleToLongBits(weight),
          Double.doubleToLongBits(wordNeighborhood.getWeight()));

      assertTrue(wordNeighborhood.sourceVertices().contains(word1));
      assertTrue(wordNeighborhood.targetVertices().contains(word2));
      assertTrue(wordNeighborhood.vertices().containsAll(Arrays.asList(word1, word2)));
      assertEquals(2, wordNeighborhood.vertices().size());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testOverride() {
    String label = "WordN";
    try {
      Word word1 = new Word("WORD1");
      Word word2 = new Word("WORD2");
      double weight = 1;

      WordNeighborhood wordNeighborhood = new WordNeighborhood(label, weight);
      wordNeighborhood.addVertices(Arrays.asList(word1, word2));

      Edge edge = EdgeFactory
          .createEdge(label, "WordNeighborhood", weight, Arrays.asList(word1, word2));

      assertEquals(edge, wordNeighborhood);
      assertEquals(wordNeighborhood, edge);
      assertEquals(edge.hashCode(), wordNeighborhood.hashCode());

      Edge commentTie = EdgeFactory.createEdge(label, "CommentTie", weight,
          Arrays.asList(new Person(label), new Person(label)));

      assertNotEquals(wordNeighborhood, commentTie);
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}
