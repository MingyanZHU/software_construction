package factory;

import static org.junit.Assert.assertEquals;

import exception.InputFileAgainException;
import org.apache.log4j.Logger;
import org.junit.Test;
import vertex.Vertex;

public class VertexFactoryTest {

  private static final Logger LOGGER = LoggerFactory.createLogger(VertexFactoryTest.class);

  /*
  Test strategy
  Partition for inputs of createVertex(label, type, args)
      Because of label or args is tested in other test units, we only test different inputs of type.
      type: null, undefined type, legal type.
   */
  @Test
  public void createVertex() {
    String type = "Computer";
    String label = "Vertex";
    String[] args = {"192.168.1.1"};
    try {
      Vertex vertex = VertexFactory.createVertex(label, null, args);
      assertEquals(label, vertex.getLabel());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Vertex vertex = VertexFactory.createVertex(label, "", args);
      assertEquals(label, vertex.getLabel());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Vertex vertex = VertexFactory.createVertex(label, type, args);
      assertEquals(label, vertex.getLabel());
      assertEquals(type, vertex.getClass().getSimpleName());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}