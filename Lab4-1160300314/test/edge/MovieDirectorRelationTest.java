package edge;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import exception.InputFileAgainException;
import factory.EdgeFactory;
import factory.LoggerFactory;
import factory.VertexFactory;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import org.apache.log4j.MDC;
import org.junit.Test;
import vertex.Director;
import vertex.Movie;
import vertex.Vertex;

public class MovieDirectorRelationTest {

  /*
  TODO Test Strategy
   */
  private static final Logger LOGGER = LoggerFactory.createLogger(MovieDirectorRelationTest.class);
  private static final String type = "MovieDirectorRelation";
  private static final double delta = 0.00001;

  @Test
  public void testAddVertices() {
    String label = "movieDirector";
    double weight = -1;

    try {
      // Test addVertices() with null vertices
      MovieDirectorRelation movieDirectorRelation
          = (MovieDirectorRelation) EdgeFactory.createEdge(label, type, weight, null);
      assertEquals(label, movieDirectorRelation.getLabel());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      // Test addVertices() with Collections.singletonList
      String directorLabel = "FrankDarabont";
      int age = 59;
      String gender = "M";
      String[] directorArgs = {gender, String.valueOf(age)};

      Director director = (Director) VertexFactory.createVertex(directorLabel,
          "Director", directorArgs);

      MovieDirectorRelation movieDirectorRelation
          = (MovieDirectorRelation) EdgeFactory.createEdge(label, type, weight,
          Collections.singletonList(director));

      assertTrue(movieDirectorRelation.vertices().contains(director));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      // Test addVertices with correct number of vertices but both directors
      String directorLabel = "FrankDarabont";
      int age = 59;
      String gender = "M";
      String[] directorArgs = {gender, String.valueOf(age)};

      Director director = (Director) VertexFactory.createVertex(directorLabel,
          "Director", directorArgs);

      MovieDirectorRelation movieDirectorRelation
          = (MovieDirectorRelation) EdgeFactory.createEdge(label, type, weight,
          Arrays.asList(director, director));
      assertTrue(movieDirectorRelation.vertices().contains(director));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      String directorLabel = "FrankDarabont";
      int age = 59;
      String gender = "M";
      String[] directorArgs = {gender, String.valueOf(age)};

      Director director = new Director(directorLabel);
      director.fillVertexInfo(directorArgs);

      String movieLabel = "ReadyPlayerOne";
      int releaseYear = 2018;
      String nation = "America";
      double score = 8.0;
      String[] movieArgs = {String.valueOf(releaseYear), nation, String.valueOf(score)};

      Movie movie = new Movie(movieLabel);
      movie.fillVertexInfo(movieArgs);

      MovieDirectorRelation movieDirectorRelation
          = (MovieDirectorRelation) EdgeFactory.createEdge(label, type, weight,
          Arrays.asList(movie, director));
      assertTrue(movieDirectorRelation.sourceVertices().contains(movie));
      assertTrue(movieDirectorRelation.sourceVertices().contains(director));
      assertTrue(movieDirectorRelation.targetVertices().contains(movie));
      assertTrue(movieDirectorRelation.targetVertices().contains(director));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testMovieDirectorRelation() {
    try {
      String directorLabel = "FrankDarabont";
      int age = 59;
      String gender = "M";
      String[] directorArgs = {gender, String.valueOf(age)};

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
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testEdgeFactory() {
    String directorLabel = "FrankDarabont";
    int age = 59;
    String gender = "M";
    String[] directorArgs = {gender, String.valueOf(age)};

    String movieLabel = "ReadyPlayerOne";
    int releaseYear = 2018;
    String nation = "America";
    double score = 8.0;
    String[] movieArgs = {String.valueOf(releaseYear), nation, String.valueOf(score)};
    try {
      Director director = new Director(directorLabel);
      director.fillVertexInfo(directorArgs);

      Movie movie = new Movie(movieLabel);
      movie.fillVertexInfo(movieArgs);

      String label = "MD";
      double weight = -1;

      Edge movieDirectorRelation = EdgeFactory
          .createEdge(label, "MovieDirectorRelation", weight, Arrays.asList(movie, director));

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
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Movie movie = (Movie) VertexFactory.createVertex(movieLabel, "Movie", movieArgs);
      Director director = (Director) VertexFactory.createVertex(directorLabel,
          "Director", directorArgs);

      MovieDirectorRelation relation = (MovieDirectorRelation) EdgeFactory.createEdge("label",
          type, -1.1, Arrays.asList(movie, director));

      assertEquals(-1.1, relation.getWeight(), delta);
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testOverride() {
    try {
      String directorLabel = "FrankDarabont";
      int age = 59;
      String gender = "M";
      String[] directorArgs = {gender, String.valueOf(age)};

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

      Edge edge = EdgeFactory
          .createEdge(label, "MovieDirectorRelation", weight, Arrays.asList(movie, director));

      boolean answer = edge.equals(movieDirectorRelation);
      assertTrue(answer);
      answer = movieDirectorRelation.equals(edge);
      assertTrue(answer);

      assertEquals(edge.hashCode(), movieDirectorRelation.hashCode());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}
