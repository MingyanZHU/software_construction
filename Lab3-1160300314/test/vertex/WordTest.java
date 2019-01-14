package vertex;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import exception.IllegalParamsNumberException;
import exception.IllegalVertexParamsException;
import exception.InputFileAgainException;
import factory.LoggerFactory;
import factory.VertexFactory;
import org.apache.log4j.Logger;
import org.junit.Test;

public class WordTest {
    private static final Logger LOGGER = LoggerFactory.createLogger(WordTest.class);
    private static final String vertexType = "Word";

    /*
    Test Strategy
    Partition for inputs of Word()
        Label is null, empty-string, string consists of [^\w] and string consists of [\w]
    Partition for inputs of fillVertexInfo(args)
        args: null, normal string array
     */
    @Test
    public void testWord() {
        String label1 = null;
        String label2 = "";
        String label3 = "...";
        String label4 = "Hello";

        try {
            Word word = new Word(label1);
            assertEquals(label1, word.getLabel());
        } catch (IllegalVertexParamsException e) {
            LOGGER.error("Test Word Constructor with null label string!", e);
        }

        try {
            Word word = new Word(label2);
            assertEquals(label2, word.getLabel());
        } catch (IllegalVertexParamsException e) {
            LOGGER.error("Test Word Constructor with empty label!", e);
        }

        try {
            Word word = new Word(label3);
            assertEquals(label3, word.getLabel());
        } catch (IllegalVertexParamsException e) {
            LOGGER.error("Test Word Constructor with string label of [^\\w]!", e);
        }

        try {
            Word word = new Word(label4);
            assertEquals(label4, word.getLabel());
        } catch (IllegalVertexParamsException e) {
            LOGGER.error("Test Word Constructor with string label of [\\w]!", e);
        }
    }

    @Test
    public void testFillVertexInfo() {
        String label = "Hello";
        String[] args1 = null;
        String[] args2 = {"Hello"};

        try {
            Word word = new Word(label);
            word.fillVertexInfo(args1);
        } catch (InputFileAgainException e) {
            LOGGER.error("Test Word fillVertexInfo with a null array!", e);
        }

        try {
            Word word = new Word(label);
            word.fillVertexInfo(args2);
        } catch (InputFileAgainException e) {
            LOGGER.error("Test Word fillVertexInfo with a normal string array!", e);
        }

        try {
            Vertex word = VertexFactory.createVertex(label, vertexType, args1);
            assertEquals(label, word.getLabel());
        } catch (InputFileAgainException e) {
            LOGGER.error("Test Word Factory with a null array!", e);
        }

        try{
            Vertex word = VertexFactory.createVertex(label, vertexType, args2);
            assertEquals(label, word.getLabel());
        } catch (InputFileAgainException e){
            LOGGER.error("Test Word Factory with a normal String array!", e);
        }
    }

    @Test
    public void testOverride() throws CloneNotSupportedException, InputFileAgainException {
        String label = "Hello";

        Word word1 = new Word(label);
        Word word2 = new Word(label);
        Vertex word3 = word1.clone();
        Vertex word4 = null;
        try {
            word4 = VertexFactory.createVertex(label, "Word", null);
        } catch (IllegalParamsNumberException e) {
            e.printStackTrace();
        }

        assertTrue(word1.equals(word1));
        assertTrue(word1.equals(word2));
        assertTrue(word2.equals(word1));

        assertTrue(word1.equals(word4));

        assertTrue(word1.hashCode() == word2.hashCode());

        assertThat(word1.toString(), containsString("word='" + word1.getLabel() + '\''));

        assertTrue(word1.getClass() == word3.getClass());
    }

}
