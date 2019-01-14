package edge;

import static org.junit.Assert.*;

import static org.hamcrest.CoreMatchers.*;

import exception.InputFileAgainException;
import factory.EdgeFactory;
import org.junit.Test;
import vertex.Director;
import vertex.Movie;
import vertex.Vertex;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MovieDirectorRelationTest {
    @Test
    public void testMovieDirectorRelation() throws InputFileAgainException {
        String directorLabel = "FrankDarabont";
        int age = 59;
        String gender = "M";
        String[] directorArgs = {String.valueOf(age), gender};

        Director director = new Director(directorLabel);
        director.fillVertexInfo(directorArgs);

        String movieLabel = "ReadyPlayerOne";
        int releaseYear = 2018;
        String nation = "America";
        double score = 8.0;
        String[] movieArgs = {String.valueOf(releaseYear), nation, String.valueOf(score)};

        Movie movie = new Movie(movieLabel);
        movie.fillVertexInfo(movieArgs);

        String label = "MD";
        double weight = -1;
        MovieDirectorRelation movieDirectorRelation = new MovieDirectorRelation(label, weight);
        movieDirectorRelation.addVertices(Arrays.asList(movie, director));

        assertEquals(label, movieDirectorRelation.getLabel());
        assertTrue(movieDirectorRelation.containVertex(movie));
        assertTrue(movieDirectorRelation.containVertex(director));

        Set<Vertex> vertexSet = new HashSet<>();
        vertexSet.add(movie);
        vertexSet.add(director);

        assertEquals(vertexSet.size(), movieDirectorRelation.vertices().size());
        assertTrue(movieDirectorRelation.vertices().containsAll(vertexSet));

        assertEquals(vertexSet.size(), movieDirectorRelation.sourceVertices().size());
        assertTrue(vertexSet.containsAll(movieDirectorRelation.sourceVertices()));

        assertEquals(movieDirectorRelation.targetVertices().size(), vertexSet.size());
        assertTrue(vertexSet.containsAll(movieDirectorRelation.targetVertices()));

        assertThat(movieDirectorRelation.toString(), containsString(movie.toString()));
        assertThat(movieDirectorRelation.toString(), containsString(director.toString()));
    }

    @Test
    public void testEdgeFactory() throws InputFileAgainException{
        String directorLabel = "FrankDarabont";
        int age = 59;
        String gender = "M";
        String[] directorArgs = {String.valueOf(age), gender};

        Director director = new Director(directorLabel);
        director.fillVertexInfo(directorArgs);

        String movieLabel = "ReadyPlayerOne";
        int releaseYear = 2018;
        String nation = "America";
        double score = 8.0;
        String[] movieArgs = {String.valueOf(releaseYear), nation, String.valueOf(score)};

        Movie movie = new Movie(movieLabel);
        movie.fillVertexInfo(movieArgs);

        String label = "MD";
        double weight = -1;

        Edge movieDirectorRelation = EdgeFactory.createEdge(label, "MovieDirectorRelation", weight, Arrays.asList(movie, director));

        assertEquals(label, movieDirectorRelation.getLabel());
        assertTrue(movieDirectorRelation.containVertex(movie));
        assertTrue(movieDirectorRelation.containVertex(director));

        Set<Vertex> vertexSet = new HashSet<>();
        vertexSet.add(movie);
        vertexSet.add(director);

        assertEquals(vertexSet.size(), movieDirectorRelation.vertices().size());
        assertTrue(movieDirectorRelation.vertices().containsAll(vertexSet));

        assertEquals(vertexSet.size(), movieDirectorRelation.sourceVertices().size());
        assertTrue(vertexSet.containsAll(movieDirectorRelation.sourceVertices()));

        assertEquals(movieDirectorRelation.targetVertices().size(), vertexSet.size());
        assertTrue(vertexSet.containsAll(movieDirectorRelation.targetVertices()));

        assertThat(movieDirectorRelation.toString(), containsString(movie.toString()));
        assertThat(movieDirectorRelation.toString(), containsString(director.toString()));
    }

    @Test
    public void testOverride() throws InputFileAgainException {
        String directorLabel = "FrankDarabont";
        int age = 59;
        String gender = "M";
        String[] directorArgs = {String.valueOf(age), gender};

        Director director = new Director(directorLabel);
        director.fillVertexInfo(directorArgs);

        String movieLabel = "ReadyPlayerOne";
        int releaseYear = 2018;
        String nation = "America";
        double score = 8.0;
        String[] movieArgs = {String.valueOf(releaseYear), nation, String.valueOf(score)};

        Movie movie = new Movie(movieLabel);
        movie.fillVertexInfo(movieArgs);

        String label = "MD";
        double weight = -1;
        MovieDirectorRelation movieDirectorRelation = new MovieDirectorRelation(label, weight);
        movieDirectorRelation.addVertices(Arrays.asList(movie, director));

        Edge edge = EdgeFactory.createEdge(label, "MovieDirectorRelation", weight, Arrays.asList(movie, director));

        assertTrue(edge.equals(movieDirectorRelation));
        assertTrue(movieDirectorRelation.equals(edge));

        assertEquals(edge.hashCode(), movieDirectorRelation.hashCode());
    }
}
