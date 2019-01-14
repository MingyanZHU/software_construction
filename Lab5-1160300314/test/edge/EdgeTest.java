package edge;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import exception.IllegalEdgeParamsException;
import exception.InputFileAgainException;
import factory.LoggerFactory;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import org.junit.Test;
import vertex.Vertex;

public class EdgeTest {

  private static final Logger LOGGER = LoggerFactory.createLogger(EdgeTest.class);

  @Test
  public void testEquals() {
    try {
      Edge edge = new EdgeSon(null, -1);
      assertEquals(null, edge.getLabel());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Edge edge = new EdgeSon("", -1);
      assertEquals("", edge.getLabel());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Edge edge = new EdgeSon("+", -1);
      assertEquals("+", edge.getLabel());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Edge edge = new EdgeSon("Hello", 1);
      assertTrue(edge.equals(edge));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  static class EdgeSon extends Edge {

    /**
     * Get an instance of edge with its label and weight.
     */
    public EdgeSon(String label, double weight) throws IllegalEdgeParamsException {
      super(label, weight);
    }

    @Override
    public boolean addVertices(List<Vertex> vertices) throws InputFileAgainException {
      return false;
    }

    @Override
    public boolean containVertex(Vertex v) {
      return false;
    }

    @Override
    public Set<Vertex> vertices() {
      return null;
    }

    @Override
    public Set<Vertex> sourceVertices() {
      return null;
    }

    @Override
    public Set<Vertex> targetVertices() {
      return null;
    }
  }
}