package vertex;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import exception.InputFileAgainException;
import factory.LoggerFactory;
import factory.VertexFactory;
import org.apache.log4j.Logger;
import org.junit.Test;
import state.Open;

public class WirelessRouterTest {

  private static final Logger LOGGER = LoggerFactory.createLogger(WirelessRouterTest.class);
  private static final String type = "WirelessRouter";

  /*
  Test strategy
  Only test basic operation.
   */
  @Test
  public void test() {
    String label = "wire";
    String ip = "192.168.23.2";
    String[] args = {ip};

    try {
      WirelessRouter router = (WirelessRouter) VertexFactory.createVertex(label, type, args);

      assertEquals(label, router.getLabel());
      assertEquals(ip, router.getIp());
      assertThat(router.toString(), containsString(type));
      assertThat(router.toString(), containsString(ip));
      assertThat(router.toString(), containsString(label));

      WirelessRouter wirelessRouter = new WirelessRouter(label);
      wirelessRouter.fillVertexInfo(args);

      assertEquals(wirelessRouter.hashCode(), router.hashCode());

      boolean answer = wirelessRouter.equals(wirelessRouter);

      assertTrue(answer);
      answer = router.equals(wirelessRouter);
      assertTrue(answer);
      Vertex router1 = VertexFactory.createVertex(label, "Router", args);
      answer = router.equals(router1);
      assertFalse(answer);
      Vertex vertex = VertexFactory.createVertex(label, "Word", null);
      assertFalse(router.equals(vertex));

      Vertex vertex1 = VertexFactory.createVertex(label, type, new String[]{"192.168.2.5"});
      answer = router.equals(vertex1);
      assertFalse(answer);
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }
}