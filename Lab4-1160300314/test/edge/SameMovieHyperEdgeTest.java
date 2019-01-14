package edge;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import exception.InputFileAgainException;
import factory.HyperEdgeFactory;
import factory.LoggerFactory;
import factory.VertexFactory;
import java.util.Arrays;
import org.apache.log4j.Logger;
import org.junit.Test;
import vertex.Actor;
import vertex.Director;

public class SameMovieHyperEdgeTest {

  private static final Logger LOGGER = LoggerFactory.createLogger(SameMovieHyperEdge.class);
  private static final String type = "SameMovieHyperEdge";

  @Test
  public void testAddVertices() {
    try {
      final String label = "smhe";
      Actor actor1 = new Actor("TimRobbins");
      String[] args1 = {"M", "60"};
      actor1.fillVertexInfo(args1);

      Director director = (Director) VertexFactory.createVertex("director",
          "Director", args1);

      SameMovieHyperEdge sameMovieHyperEdge = (SameMovieHyperEdge) HyperEdgeFactory
          .createEdge(label, type, Arrays.asList(director, actor1));
      assertEquals(label, sameMovieHyperEdge.getLabel());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      final String label = "shme";

      SameMovieHyperEdge sameMovieHyperEdge = (SameMovieHyperEdge) HyperEdgeFactory
          .createEdge(label, type, null);

      assertEquals(label, sameMovieHyperEdge.getLabel());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testSameMovieHyperEdge() {
    try {
      final String label = "ActorsInSR";

      Actor actor1 = new Actor("TimRobbins");
      String[] args1 = {"M", "60"};
      actor1.fillVertexInfo(args1);

      Actor actor2 = new Actor("TomHanks");
      String[] args2 = {"M", "62"};
      actor2.fillVertexInfo(args2);

      Actor actor3 = new Actor("MorganFreeman");
      String[] args3 = {"M", "81"};
      actor3.fillVertexInfo(args3);

      SameMovieHyperEdge sameMovieHyperEdge = new SameMovieHyperEdge(label, -1);
      sameMovieHyperEdge.addVertices(Arrays.asList(actor1, actor2, actor3));

      assertEquals(label, sameMovieHyperEdge.getLabel());
      assertTrue(sameMovieHyperEdge.containVertex(actor1));
      assertTrue(sameMovieHyperEdge.containVertex(actor2));
      assertTrue(sameMovieHyperEdge.containVertex(actor3));

      assertThat(sameMovieHyperEdge.toString(), containsString(actor1.toString()));
      assertThat(sameMovieHyperEdge.toString(), containsString(actor2.toString()));
      assertThat(sameMovieHyperEdge.toString(), containsString(actor3.toString()));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testHyperEdgeFactory() {
    try {
      final String label = "ActorsInSR";

      Actor actor1 = new Actor("TimRobbins");
      String[] args1 = {"M", "60"};
      actor1.fillVertexInfo(args1);

      Actor actor2 = new Actor("TomHanks");
      String[] args2 = {"M", "62"};
      actor2.fillVertexInfo(args2);

      Actor actor3 = new Actor("MorganFreeman");
      String[] args3 = {"M", "81"};
      actor3.fillVertexInfo(args3);

      HyperEdge sameMovieHyperEdge = HyperEdgeFactory
          .createEdge(label, "SameMovieHyperEdge", Arrays.asList(actor1, actor2, actor3));

      assertEquals(label, sameMovieHyperEdge.getLabel());
      assertTrue(sameMovieHyperEdge.containVertex(actor1));
      assertTrue(sameMovieHyperEdge.containVertex(actor2));
      assertTrue(sameMovieHyperEdge.containVertex(actor3));

      assertThat(sameMovieHyperEdge.toString(), containsString(actor1.toString()));
      assertThat(sameMovieHyperEdge.toString(), containsString(actor2.toString()));
      assertThat(sameMovieHyperEdge.toString(), containsString(actor3.toString()));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testOverride() {
    try {
      final String label = "ActorsInSR";

      Actor actor1 = new Actor("TimRobbins");
      String[] args1 = {"M", "60"};
      actor1.fillVertexInfo(args1);

      Actor actor2 = new Actor("TomHanks");
      String[] args2 = {"M", "62"};
      actor2.fillVertexInfo(args2);

      Actor actor3 = new Actor("MorganFreeman");
      String[] args3 = {"M", "81"};
      actor3.fillVertexInfo(args3);

      SameMovieHyperEdge sameMovieHyperEdge = new SameMovieHyperEdge(label, -1);
      sameMovieHyperEdge.addVertices(Arrays.asList(actor1, actor2, actor3));

      HyperEdge hyperEdge = HyperEdgeFactory
          .createEdge(label, "SameMovieHyperEdge", Arrays.asList(actor1, actor2, actor3));

      assertTrue(hyperEdge.equals(sameMovieHyperEdge));
      assertTrue(sameMovieHyperEdge.equals(hyperEdge));

      assertEquals(hyperEdge.hashCode(), sameMovieHyperEdge.hashCode());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

}
