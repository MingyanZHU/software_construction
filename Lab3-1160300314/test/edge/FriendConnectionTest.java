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

public class FriendConnectionTest {
    @Test
    public void testFriendTie() throws InputFileAgainException {
        Person source = new Person("Kevin");
        String [] args1 = {"M", "20"};
        source.fillVertexInfo(args1);

        Person destination = new Person("Vera");
        String [] args2 = {"F", "18"};
        destination.fillVertexInfo(args2);

        FriendConnection friendConnection = new FriendConnection("KV", 0.01);
        friendConnection.addVertices(Arrays.asList(source, destination));

        assertEquals("KV", friendConnection.getLabel());
        assertTrue(friendConnection.containVertex(source));
        assertTrue(friendConnection.containVertex(destination));

        Set<Vertex> vertexSet = new HashSet<>();
        vertexSet.add(source);
        vertexSet.add(destination);

        assertEquals(vertexSet.size(), friendConnection.vertices().size());
        assertTrue(friendConnection.vertices().containsAll(vertexSet));

        assertEquals(1, friendConnection.sourceVertices().size());
        assertTrue(friendConnection.sourceVertices().contains(source));

        assertEquals(1, friendConnection.targetVertices().size());
        assertTrue(friendConnection.targetVertices().contains(destination));

        assertThat(friendConnection.toString(), containsString("source="+source.toString()));
        assertThat(friendConnection.toString(), containsString("destination=" + destination.toString()));
        assertThat(friendConnection.toString(), containsString("FriendConnection"));
    }

    @Test
    public void testEdgeFactory() throws InputFileAgainException {
        Person source = new Person("Kevin");
        String [] args1 = {"M", "20"};
        source.fillVertexInfo(args1);

        Person destination = new Person("Vera");
        String [] args2 = {"F", "18"};
        destination.fillVertexInfo(args2);
        double weight = 0.01;
        String label = "KV";
        Edge friendTie = EdgeFactory.createEdge(label, "FriendConnection", weight, Arrays.asList(source, destination));

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

        assertThat(friendTie.toString(), containsString("source="+source.toString()));
        assertThat(friendTie.toString(), containsString("destination=" + destination.toString()));
        assertThat(friendTie.toString(), containsString("FriendConnection"));
    }

    @Test
    public void testOverride() throws InputFileAgainException {
        Person source = new Person("Kevin");
        String [] args1 = {"M", "20"};
        source.fillVertexInfo(args1);

        Person destination = new Person("Vera");
        String [] args2 = {"F", "18"};
        destination.fillVertexInfo(args2);
        double weight = 0.01;
        String label = "KV";

        FriendConnection friendConnection = new FriendConnection(label, weight);
        friendConnection.addVertices(Arrays.asList(source, destination));

        Edge edge = EdgeFactory.createEdge(label, "FriendConnection", weight, Arrays.asList(source, destination));

        assertTrue(edge.equals(friendConnection));
        assertTrue(friendConnection.equals(edge));

        assertEquals(edge.hashCode(), friendConnection.hashCode());
    }
}
