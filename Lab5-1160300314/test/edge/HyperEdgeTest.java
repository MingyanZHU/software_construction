package edge;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import exception.InputFileAgainException;
import factory.EdgeFactory;
import factory.LoggerFactory;
import java.util.Arrays;
import java.util.Collections;
import org.apache.log4j.Logger;
import org.junit.Test;
import vertex.Word;

public class HyperEdgeTest {

  private static final Logger LOGGER = LoggerFactory.createLogger(HyperEdgeTest.class);

  @Test
  public void testEquals() {
    String label = "hyper";
    double weight = -1;
    try {
      HyperEdge edge = new HyperEdge(label, weight);
      final Word word = new Word(label);
      final Word word1 = new Word(label + "1");
      final Word word2 = new Word(label + "2");
      edge.addVertices(Arrays.asList(word, word1));

      Edge directedEdge = EdgeFactory.createEdge(label, "WordNeighborhood", 2,
          Arrays.asList(word, word1));

      assertTrue(edge.equals(edge));
      assertFalse(edge.equals(directedEdge));
      HyperEdge hyperEdge = new HyperEdge(label + "2", weight);
      hyperEdge.addVertices(Arrays.asList(word, word1, word2));
      assertFalse(edge.equals(hyperEdge));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void addVertices() {
    String label = "hyper";
    double weight = -1;
    try {
      HyperEdge edge = new HyperEdge(label, weight);

      edge.addVertices(null);
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      HyperEdge edge = new HyperEdge(label, weight);

      Word word = new Word(label);
      edge.addVertices(Collections.singletonList(word));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      HyperEdge edge = new HyperEdge(label, weight);

      Word word = new Word(label);
      Word word1 = new Word(label + "1");
      edge.addVertices(Arrays.asList(word, word1));
      assertFalse(edge.addVertices(Arrays.asList(word, word1)));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void vertices() {
    String label = "hyper";
    double weight = -1;
    try {
      HyperEdge edge = new HyperEdge(label, weight);

      Word word = new Word(label);
      Word word1 = new Word(label + "1");
      edge.addVertices(Arrays.asList(word, word1));

      assertTrue(edge.vertices().contains(word));
      assertTrue(edge.vertices().contains(word1));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void removeVertex() {
    String label = "hyper";
    double weight = -1;
    try {
      HyperEdge edge = new HyperEdge(label, weight);

      Word word = new Word(label);
      Word word1 = new Word(label + "1");
      edge.addVertices(Arrays.asList(word, word1));

      assertTrue(edge.removeVertex(word));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}
