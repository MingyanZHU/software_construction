package edge;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import exception.InputFileAgainException;
import factory.EdgeFactory;
import factory.LoggerFactory;
import factory.VertexFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import vertex.Person;
import vertex.Word;

import java.util.Arrays;

public class WordEdgeTest {
    private static final Logger LOGGER = LoggerFactory.createLogger(WordEdgeTest.class);
    private static final String type = "WordEdge";
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
            WordEdge wordEdge = (WordEdge) EdgeFactory.createEdge(label, type, weight, null);

            assertEquals(label, wordEdge.getLabel());
        } catch (InputFileAgainException e) {
            LOGGER.error(e.getMessage(), e);
        }

        try {
            WordEdge wordEdge = (WordEdge) EdgeFactory.createEdge(label, type, weight, Arrays.asList(word1));

            assertEquals(label, wordEdge.getLabel());
        } catch (InputFileAgainException e) {
            LOGGER.error(e.getMessage(), e);
        }

        try {
            WordEdge wordEdge = (WordEdge) EdgeFactory.createEdge(label, type, weight, Arrays.asList(word1, word2));

            assertEquals(label, wordEdge.getLabel());
            assertEquals(weight, wordEdge.getWeight(), delta);
            assertTrue(wordEdge.sourceVertices().contains(word1));
            assertTrue(wordEdge.targetVertices().contains(word2));
            assertTrue(wordEdge.containVertex(word1));
            assertTrue(wordEdge.containVertex(word2));
        } catch (InputFileAgainException e) {
            LOGGER.error(e.getMessage(), e);
        }

        try {
            WordEdge wordEdge = (WordEdge) EdgeFactory.createEdge(label, type, weight, Arrays.asList(word1, person));

            assertTrue(wordEdge.sourceVertices().contains(word1));
            assertTrue(wordEdge.targetVertices().contains(word2));
        } catch (InputFileAgainException e) {
            LOGGER.error(e.getMessage(), e);
        }

        try {
            WordEdge wordEdge = (WordEdge) EdgeFactory.createEdge(label, type, -1, Arrays.asList(word1, word2));

            assertEquals(-1, wordEdge.getWeight(), delta);
        } catch (InputFileAgainException e) {
            LOGGER.error(e.getMessage(), e);
        }

        try {
            WordEdge wordEdge = (WordEdge) EdgeFactory.createEdge(label, type, weight, Arrays.asList(word1, word2, person));

            assertTrue(wordEdge.containVertex(word1));
            assertTrue(wordEdge.containVertex(word2));
            assertTrue(wordEdge.containVertex(person));
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
            WordEdge wordEdge = new WordEdge(label, weight);
            wordEdge.addVertices(Arrays.asList(word1, word2));

            assertEquals(label, wordEdge.getLabel());
            assertEquals(Double.doubleToLongBits(weight), Double.doubleToLongBits(wordEdge.getWeight()));

            assertTrue(wordEdge.sourceVertices().contains(word1));
            assertTrue(wordEdge.targetVertices().contains(word2));
            assertTrue(wordEdge.vertices().containsAll(Arrays.asList(word1, word2)));
            assertEquals(2, wordEdge.vertices().size());

            assertThat(wordEdge.toString(), containsString("source=" + word1.toString()));
            assertThat(wordEdge.toString(), containsString("destination=" + word2.toString()));
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

            Edge wordNeighborhood = EdgeFactory.createEdge(label, "WordEdge", weight, Arrays.asList(word1, word2));

            assertEquals(label, wordNeighborhood.getLabel());
            assertEquals(Double.doubleToLongBits(weight), Double.doubleToLongBits(wordNeighborhood.getWeight()));

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

            WordEdge wordEdge = new WordEdge(label, weight);
            wordEdge.addVertices(Arrays.asList(word1, word2));

            Edge edge = EdgeFactory.createEdge(label, "WordEdge", weight, Arrays.asList(word1, word2));

            assertTrue(edge.equals(wordEdge));
            assertTrue(wordEdge.equals(edge));
            assertEquals(edge.hashCode(), wordEdge.hashCode());
        } catch (InputFileAgainException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
