package edge;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import exception.InputFileAgainException;
import factory.EdgeFactory;
import org.junit.Test;
import vertex.Actor;
import vertex.Movie;
import vertex.Vertex;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MovieActorRelationTest {
    @Test
    public void testMovieActorRelation() throws InputFileAgainException {
        Actor actor = new Actor("TimRobbins");
        String[] args1 = {"60", "M"};
        actor.fillVertexInfo(args1);

        String movieLabel = "ReadyPlayerOne";
        int releaseYear = 2018;
        String nation = "America";
        double score = 8.0;
        String[] args = {String.valueOf(releaseYear), nation, String.valueOf(score)};

        Movie movie = new Movie(movieLabel);
        movie.fillVertexInfo(args);

        String label = "SRFD";
        double weight = 1;

        MovieActorRelation movieActorRelation = new MovieActorRelation(label, weight);
        movieActorRelation.addVertices(Arrays.asList(movie, actor));

        assertEquals(label, movieActorRelation.getLabel());
        assertTrue(movieActorRelation.containVertex(movie));
        assertTrue(movieActorRelation.containVertex(actor));

        Set<Vertex> vertexSet = new HashSet<>();
        vertexSet.add(movie);
        vertexSet.add(actor);

        assertEquals(vertexSet.size(), movieActorRelation.vertices().size());
        assertTrue(movieActorRelation.vertices().containsAll(vertexSet));

        assertEquals(vertexSet.size(), movieActorRelation.sourceVertices().size());
        assertTrue(vertexSet.containsAll(movieActorRelation.sourceVertices()));

        assertEquals(movieActorRelation.targetVertices().size(), vertexSet.size());
        assertTrue(vertexSet.containsAll(movieActorRelation.targetVertices()));

        assertThat(movieActorRelation.toString(), containsString(movie.toString()));
        assertThat(movieActorRelation.toString(), containsString(actor.toString()));
    }

    @Test
    public void testEdgeFactory() throws InputFileAgainException {
        Actor actor = new Actor("TimRobbins");
        String[] args1 = {"60", "M"};
        actor.fillVertexInfo(args1);

        String movieLabel = "ReadyPlayerOne";
        int releaseYear = 2018;
        String nation = "America";
        double score = 8.0;
        String[] args = {String.valueOf(releaseYear), nation, String.valueOf(score)};

        Movie movie = new Movie(movieLabel);
        movie.fillVertexInfo(args);

        String label = "SRFD";
        double weight = 1;

        Edge movieActorRelation = EdgeFactory.createEdge(label, "MovieActorRelation", weight, Arrays.asList(movie, actor));

        assertEquals(label, movieActorRelation.getLabel());
        assertTrue(movieActorRelation.containVertex(movie));
        assertTrue(movieActorRelation.containVertex(actor));

        Set<Vertex> vertexSet = new HashSet<>();
        vertexSet.add(movie);
        vertexSet.add(actor);

        assertEquals(vertexSet.size(), movieActorRelation.vertices().size());
        assertTrue(movieActorRelation.vertices().containsAll(vertexSet));

        assertEquals(vertexSet.size(), movieActorRelation.sourceVertices().size());
        assertTrue(vertexSet.containsAll(movieActorRelation.sourceVertices()));

        assertEquals(movieActorRelation.targetVertices().size(), vertexSet.size());
        assertTrue(vertexSet.containsAll(movieActorRelation.targetVertices()));

        assertThat(movieActorRelation.toString(), containsString(movie.toString()));
        assertThat(movieActorRelation.toString(), containsString(actor.toString()));
    }

    @Test
    public void testOverride() throws InputFileAgainException {
        Actor actor = new Actor("TimRobbins");
        String[] args1 = {"60", "M"};
        actor.fillVertexInfo(args1);

        String movieLabel = "ReadyPlayerOne";
        int releaseYear = 2018;
        String nation = "America";
        double score = 8.0;
        String[] args = {String.valueOf(releaseYear), nation, String.valueOf(score)};

        Movie movie = new Movie(movieLabel);
        movie.fillVertexInfo(args);

        String label = "SRFD";
        double weight = 1;

        MovieActorRelation movieActorRelation = new MovieActorRelation(label, weight);
        movieActorRelation.addVertices(Arrays.asList(movie, actor));

        Edge edge = EdgeFactory.createEdge(label, "MovieActorRelation", weight, Arrays.asList(movie, actor));

        assertTrue(edge.equals(movieActorRelation));
        assertTrue(movieActorRelation.equals(edge));

        assertEquals(edge.hashCode(), movieActorRelation.hashCode());
    }
}
