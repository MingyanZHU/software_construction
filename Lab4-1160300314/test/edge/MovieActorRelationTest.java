package edge;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import exception.InputFileAgainException;
import factory.EdgeFactory;
import factory.LoggerFactory;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.apache.log4j.Logger;
import org.junit.Test;
import vertex.Actor;
import vertex.Movie;
import vertex.Vertex;

public class MovieActorRelationTest {

  /*
  TODO Test Strategy
   */
  private static final Logger LOGGER = LoggerFactory.createLogger(MovieActorRelationTest.class);
  private static final String type = "MovieActorRelation";
  private static final double delta = 0.00001;

  @Test
  public void testAddVertices() {
    String label = "movieActor";
    double weight = 1;

    try {
      // Test addVertices() with null vertices.
      MovieActorRelation movieActorRelation = (MovieActorRelation) EdgeFactory.createEdge(label,
          type, weight, null);

      assertEquals(label, movieActorRelation.getLabel());
      assertEquals(weight, movieActorRelation.getWeight(), delta);
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      // Test addVertices() with Collections.singletonList.
      Actor actor = new Actor("TimRobbins");
      String[] args1 = {"M", "60"};
      actor.fillVertexInfo(args1);

      MovieActorRelation movieActorRelation = (MovieActorRelation) EdgeFactory.createEdge(label,
          type, weight, Collections.singletonList(actor));

      assertTrue(movieActorRelation.vertices().contains(actor));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      // Test addVertices() with correct number of vertices but both are actors.
      Actor actor = new Actor("TimRobbins");
      String[] args1 = {"M", "60"};
      actor.fillVertexInfo(args1);

      MovieActorRelation movieActorRelation = (MovieActorRelation) EdgeFactory.createEdge(label,
          type, weight, Arrays.asList(actor, actor));

      assertTrue(movieActorRelation.vertices().contains(actor));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      // Test addVertices in MovieActorRelation normal function
      Actor actor = new Actor("TimRobbins");
      String[] args1 = {"M", "60"};
      actor.fillVertexInfo(args1);

      String movieLabel = "ReadyPlayerOne";
      int releaseYear = 2018;
      String nation = "America";
      double score = 8.0;
      String[] args = {String.valueOf(releaseYear), nation, String.valueOf(score)};

      Movie movie = new Movie(movieLabel);
      movie.fillVertexInfo(args);

      MovieActorRelation movieActorRelation = (MovieActorRelation) EdgeFactory.createEdge(label,
          type, weight, Arrays.asList(actor, movie));

      assertTrue(movieActorRelation.sourceVertices().contains(actor));
      assertTrue(movieActorRelation.sourceVertices().contains(movie));
      assertTrue(movieActorRelation.targetVertices().contains(actor));
      assertTrue(movieActorRelation.targetVertices().contains(movie));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testMovieActorRelation() {
    String label = "SRFD";
    double weight = 1;
    try {
      Actor actor = new Actor("TimRobbins");
      String[] args1 = {"M", "60"};
      actor.fillVertexInfo(args1);

      String movieLabel = "ReadyPlayerOne";
      int releaseYear = 2018;
      String nation = "America";
      double score = 8.0;
      String[] args = {String.valueOf(releaseYear), nation, String.valueOf(score)};

      Movie movie = new Movie(movieLabel);
      movie.fillVertexInfo(args);

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
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Actor actor = new Actor("TimRobbins");
      String[] args1 = {"M", "60"};
      actor.fillVertexInfo(args1);

      String movieLabel = "ReadyPlayerOne";
      int releaseYear = 2018;
      String nation = "America";
      double score = 8.0;
      String[] args = {String.valueOf(releaseYear), nation, String.valueOf(score)};

      Movie movie = new Movie(movieLabel);
      movie.fillVertexInfo(args);

      MovieActorRelation movieActorRelation = (MovieActorRelation) EdgeFactory.createEdge(label,
          type, -weight, Arrays.asList(actor, movie));

      assertEquals(-weight, movieActorRelation.getWeight(), delta);
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testEdgeFactory() {
    try {
      Actor actor = new Actor("TimRobbins");
      String[] args1 = {"M", "60"};
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

      Edge movieActorRelation = EdgeFactory
          .createEdge(label, "MovieActorRelation", weight, Arrays.asList(movie, actor));

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
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testOverride() {
    try {
      Actor actor = new Actor("TimRobbins");
      String[] args1 = {"M", "60"};
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

      MovieActorRelation edge = (MovieActorRelation) EdgeFactory
          .createEdge(label, "MovieActorRelation", weight, Arrays.asList(movie, actor));

      boolean answer = edge.equals(movieActorRelation);
      assertTrue(answer);
      answer = movieActorRelation.equals(edge);
      assertTrue(answer);
      assertEquals(movieActorRelation.hashCode(), edge.hashCode());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}
