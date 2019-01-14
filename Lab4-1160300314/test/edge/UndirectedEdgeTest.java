package edge;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import exception.InputFileAgainException;
import factory.LoggerFactory;
import java.util.Arrays;
import java.util.Collections;
import org.apache.log4j.Logger;
import org.junit.Test;
import vertex.Word;

public class UndirectedEdgeTest {

  private static final Logger LOGGER = LoggerFactory.createLogger(UndirectedEdgeTest.class);

  @Test
  public void addVertices() {
    String label = "undirected";
    double weight = 1;
    try {
      UndirectedEdge undirectedEdge = new UndirectedEdge(label, weight);
      undirectedEdge.addVertices(null);
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      UndirectedEdge undirectedEdge = new UndirectedEdge(label, weight);
      Word word = new Word("Hello");
      Word word1 = new Word("World");
      Word word2 = new Word("Hey");
      undirectedEdge.addVertices(Arrays.asList(word, word1, word2));

    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      UndirectedEdge undirectedEdge = new UndirectedEdge(label, weight);
      Word word = new Word("Hello");
      Word word1 = new Word("World");

      undirectedEdge.addVertices(Arrays.asList(word, word1));
      assertFalse(undirectedEdge.addVertices(Arrays.asList(word, word1)));

      UndirectedEdge edge = new UndirectedEdge(label, weight);
      edge.addVertices(Arrays.asList(word1, word));

      assertTrue(edge.equals(edge));
      assertTrue(edge.equals(undirectedEdge));

      DirectedEdge directedEdge = new DirectedEdge(label, weight);
      directedEdge.addVertices(Arrays.asList(word, word1));

      assertFalse(edge.equals(directedEdge));

      UndirectedEdge undirectedEdge1 = new UndirectedEdge(label, weight + 1);
      undirectedEdge1.addVertices(Arrays.asList(word, word1));

      assertFalse(undirectedEdge.equals(undirectedEdge1));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      UndirectedEdge undirectedEdge = new UndirectedEdge(label, weight);
      Word word = new Word("Hello");

      undirectedEdge.addVertices(Collections.singletonList(word));
      assertTrue(undirectedEdge.vertices().contains(word));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      UndirectedEdge undirectedEdge = new UndirectedEdge(label, weight);
      Word word = new Word("Hello");
      undirectedEdge.addVertices(Collections.singletonList(word));
      undirectedEdge.addVertices(Collections.singletonList(null));

    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      UndirectedEdge undirectedEdge = new UndirectedEdge(label, weight);
      Word word = new Word("Hello");
      Word word1 = new Word("World");
      Word word2 = new Word("hey");
      undirectedEdge.addVertices(Arrays.asList(word, word1));
      undirectedEdge.addVertices(Arrays.asList(word2, null));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

  }
}