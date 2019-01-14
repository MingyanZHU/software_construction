package edge;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import exception.InputFileAgainException;
import factory.EdgeFactory;
import org.junit.Test;
import vertex.Person;
import vertex.Vertex;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class CommentConnectionTest {
    @Test
    public void testCommentTie() throws InputFileAgainException {
        Person source = new Person("Kevin");
        String[] args1 = {"M", "20"};
        source.fillVertexInfo(args1);

        Person destination = new Person("Vera");
        String[] args2 = {"F", "18"};
        destination.fillVertexInfo(args2);

        String label = "KV";
        double weight = 0.2;
        CommentConnection commentConnection = new CommentConnection(label, weight);
        commentConnection.addVertices(Arrays.asList(source, destination));

        assertEquals("KV", commentConnection.getLabel());
        assertTrue(commentConnection.containVertex(source));
        assertTrue(commentConnection.containVertex(destination));

        Set<Vertex> vertexSet = new HashSet<>();
        vertexSet.add(source);
        vertexSet.add(destination);

        assertEquals(vertexSet.size(), commentConnection.vertices().size());
        assertTrue(vertexSet.containsAll(commentConnection.vertices()));

        assertEquals(1, commentConnection.sourceVertices().size());
        assertTrue(commentConnection.sourceVertices().contains(source));

        assertEquals(1, commentConnection.targetVertices().size());
        assertTrue(commentConnection.targetVertices().contains(destination));

        assertThat(commentConnection.toString(), containsString("source=" + source.toString()));
        assertThat(commentConnection.toString(), containsString("destination=" + destination.toString()));
        assertThat(commentConnection.toString(), containsString("CommentConnection"));
    }

    @Test
    public void testEdgeFactory() throws InputFileAgainException{
        Person source = new Person("Kevin");
        String[] args1 = {"M", "20"};
        source.fillVertexInfo(args1);

        Person destination = new Person("Vera");
        String[] args2 = {"F", "18"};
        destination.fillVertexInfo(args2);

        String label = "KV";
        double weight = 0.2;

        Edge commentTie = EdgeFactory.createEdge(label, "CommentConnection", weight, Arrays.asList(source, destination));

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
        assertThat(commentTie.toString(), containsString("CommentConnection"));
    }

    @Test
    public void testOverride() throws InputFileAgainException {
        Person source = new Person("Kevin");
        String[] args1 = {"M", "20"};
        source.fillVertexInfo(args1);

        Person destination = new Person("Vera");
        String[] args2 = {"F", "18"};
        destination.fillVertexInfo(args2);

        String label = "KV";
        double weight = 0.2;
        CommentConnection commentConnection = new CommentConnection(label, weight);
        commentConnection.addVertices(Arrays.asList(source, destination));
        Edge edge = EdgeFactory.createEdge(label, "CommentConnection", weight, Arrays.asList(source, destination));

        assertTrue(edge.equals(commentConnection));
        assertTrue(commentConnection.equals(edge));

        assertEquals(edge.hashCode(), commentConnection.hashCode());


    }
}
