package edge;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import exception.InputFileAgainException;
import factory.EdgeFactory;
import factory.LoggerFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import vertex.Person;
import vertex.Vertex;
import vertex.Word;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ForwardConnectionTest {
    private static final Logger LOGGER = LoggerFactory.createLogger(ForwardConnectionTest.class);
    private static final String type = "ForwardConnection";

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
            ForwardConnection forwardConnection = (ForwardConnection) EdgeFactory.createEdge(label, type, weight, null);

            assertEquals(label, forwardConnection.getLabel());
        } catch (InputFileAgainException e) {
            LOGGER.error(e.getMessage(), e);
        }

        try {
            ForwardConnection forwardConnection = (ForwardConnection) EdgeFactory.createEdge(label, type, weight, Arrays.asList(source));

            assertEquals(label, forwardConnection.getLabel());
            assertTrue(forwardConnection.containVertex(source));
        } catch (InputFileAgainException e) {
            LOGGER.error(e.getMessage(), e);
        }

        try {
            ForwardConnection forwardConnection = (ForwardConnection) EdgeFactory.createEdge(label, type, weight, Arrays.asList(source, destination));

            assertEquals(label, forwardConnection.getLabel());
            assertTrue(forwardConnection.containVertex(source));
            assertTrue(forwardConnection.containVertex(destination));

            Set<Vertex> vertexSet = new HashSet<>();
            vertexSet.add(source);
            vertexSet.add(destination);

            assertEquals(vertexSet.size(), forwardConnection.vertices().size());
            assertTrue(vertexSet.containsAll(forwardConnection.vertices()));

            assertEquals(1, forwardConnection.sourceVertices().size());
            assertTrue(forwardConnection.sourceVertices().contains(source));

            assertEquals(1, forwardConnection.targetVertices().size());
            assertTrue(forwardConnection.targetVertices().contains(destination));

            assertThat(forwardConnection.toString(), containsString("source=" + source.toString()));
            assertThat(forwardConnection.toString(), containsString("destination=" + destination.toString()));
            assertThat(forwardConnection.toString(), containsString("ForwardConnection"));
        } catch (InputFileAgainException e) {
            LOGGER.error(e.getMessage(), e);
        }

        try {
            ForwardConnection forwardConnection = (ForwardConnection) EdgeFactory.createEdge(label, type, weight, Arrays.asList(word, source));

            assertTrue(forwardConnection.containVertex(word));
        } catch (InputFileAgainException e) {
            LOGGER.error(e.getMessage(), e);
        }

        try {
            ForwardConnection forwardConnection = (ForwardConnection) EdgeFactory.createEdge(label, type, weight + 1, Arrays.asList(source, destination));

            assertEquals(weight + 1, forwardConnection.getWeight(), 0.0001);
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
            ForwardConnection forwardConnection = new ForwardConnection(label, weight);
            forwardConnection.addVertices(Arrays.asList(source, destination));

            assertEquals("KV", forwardConnection.getLabel());
            assertTrue(forwardConnection.containVertex(source));
            assertTrue(forwardConnection.containVertex(destination));

            Set<Vertex> vertexSet = new HashSet<>();
            vertexSet.add(source);
            vertexSet.add(destination);

            assertEquals(vertexSet.size(), forwardConnection.vertices().size());
            assertTrue(vertexSet.containsAll(forwardConnection.vertices()));

            assertEquals(1, forwardConnection.sourceVertices().size());
            assertTrue(forwardConnection.sourceVertices().contains(source));

            assertEquals(1, forwardConnection.targetVertices().size());
            assertTrue(forwardConnection.targetVertices().contains(destination));

            assertThat(forwardConnection.toString(), containsString("source=" + source.toString()));
            assertThat(forwardConnection.toString(), containsString("destination=" + destination.toString()));
            assertThat(forwardConnection.toString(), containsString("ForwardConnection"));
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

            Edge forwardTie = EdgeFactory.createEdge(label, "ForwardConnection", weight, Arrays.asList(source, destination));

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
            assertThat(forwardTie.toString(), containsString("ForwardConnection"));
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
            ForwardConnection forwardConnection = new ForwardConnection(label, weight);
            forwardConnection.addVertices(Arrays.asList(source, destination));
            Edge edge = EdgeFactory.createEdge(label, "ForwardConnection", weight, Arrays.asList(source, destination));

            assertTrue(edge.equals(forwardConnection));
            assertTrue(forwardConnection.equals(edge));

            assertEquals(edge.hashCode(), forwardConnection.hashCode());
        } catch (InputFileAgainException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }
}
