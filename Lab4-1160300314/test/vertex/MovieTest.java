package vertex;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import exception.InputFileAgainException;
import factory.LoggerFactory;
import factory.VertexFactory;
import org.apache.log4j.Logger;
import org.junit.Test;

public class MovieTest {

  private static final double delta = 0.001;
  private static final Logger LOGGER = LoggerFactory.createLogger(MovieTest.class);
  private static final String type = "Movie";

  /*
  Test strategy
  Partition for inputs of fillVertexInfo(args)
      args: null, illegal number params, correct number of params but illegal param
      , legal param args
   */
  @Test
  public void testFillVertexInfo() {
    String label = "ReadyPlayerOne";
    int releaseYear = 2018;
    String nation = "American";
    double score = 8.0;
    String[] args = {String.valueOf(releaseYear), nation, String.valueOf(score)};
    String[] args1 = {nation};
    String[] args2 = {String.valueOf(releaseYear), nation, String.valueOf(score + 3)};
    String[] args3 = {String.valueOf(releaseYear + 10), nation, String.valueOf(score)};
    String[] args4 = {String.valueOf(releaseYear), null, String.valueOf(score)};
    String[] args5 = {String.valueOf(releaseYear), "", String.valueOf(score)};

    try {
      Movie movie = (Movie) VertexFactory.createVertex(label, type, args);

      assertEquals(label, movie.getLabel());
      assertEquals(releaseYear, movie.getReleaseYear());
      assertEquals(nation.toLowerCase(), movie.getNation().toLowerCase());
      assertEquals(score, movie.getScore(), delta);
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Movie movie = (Movie) VertexFactory.createVertex(label, type, null);

      assertEquals(label, movie.getLabel());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Movie movie = (Movie) VertexFactory.createVertex(label, type, args1);

      assertEquals(nation, movie.getNation());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Movie movie = (Movie) VertexFactory.createVertex(label, type, args2);

      assertEquals(releaseYear, movie.getReleaseYear());
      assertEquals(nation.toLowerCase(), movie.getNation().toLowerCase());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Movie movie = (Movie) VertexFactory.createVertex(label, type, args3);

      assertEquals(releaseYear + 10, movie.getReleaseYear());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Movie movie = (Movie) VertexFactory.createVertex(label, type, args4);

      assertEquals(null, movie.getNation());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Movie movie = (Movie) VertexFactory.createVertex(label, type, args5);

      assertEquals("", movie.getNation());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testMovie() {
    String label = "ReadyPlayerOne";
    int releaseYear = 2018;
    String nation = "America";
    double score = 8.0;
    String[] args = {String.valueOf(releaseYear), nation, String.valueOf(score)};

    try {
      Movie movie = new Movie(label);
      movie.fillVertexInfo(args);

      assertEquals(label, movie.getLabel());
      assertEquals(releaseYear, movie.getReleaseYear());
      assertEquals(nation.toLowerCase(), movie.getNation().toLowerCase());
      assertEquals(score, movie.getScore(), delta);
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testOverride() {
    String label = "ReadyPlayerOne";
    int releaseYear = 2018;
    String nation = "America";
    double score = 8.0;
    String[] args = {String.valueOf(releaseYear), nation, String.valueOf(score)};

    try {
      Movie movie1 = new Movie(label);
      Movie movie2 = new Movie(label);

      movie1.fillVertexInfo(args);
      movie2.fillVertexInfo(args);

      assertEquals(movie1, movie1);
      assertEquals(movie1, movie2);
      assertEquals(movie2, movie1);

      assertEquals(movie1.hashCode(), movie2.hashCode());

      assertThat(movie1.toString(), containsString("name='" + movie1.getLabel() + '\''
          + ", releaseYear=" + releaseYear + ", nation='" + nation + '\'' + ", score=" + score));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}
