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
import state.Close;
import state.Open;

public class NetworkEquipmentTest {

  private static final Logger LOGGER = LoggerFactory.createLogger(NetworkEquipmentTest.class);
  private static final String computerType = "Computer";
  private static final String serverType = "Server";
  private static final String routerType = "Router";

  /*
  Test strategy
  Partition for inputs of fillVertexInfo(args)
      args: null, illegal number of params of args, legal number but illegal params,
            legal param args
  Partition for inputs of close()
      close() from different state: Open, Close
  Partition for inputs of open()
      open() from different state: Open, Close
  Partition for inputs of restore()
      there are more than one states before, there is only one state
  Partition for inputs of restore(i)
      i: larger than mementos, less than mementos number, 1, 0
  Partition for outputMementos()
      do not add other memento, add some other mementos
   */

  @Test
  public void testOutputMementos() {
    String label = "hp";
    String ip = "192.168.2.2";
    String[] args = {ip};

    try {
      Server server = (Server) VertexFactory.createVertex(label, serverType, args);

      assertEquals(Open.instance, server.getState());
      assertThat(server.outputMementos(), containsString(Open.instance.toString()));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Router router = (Router) VertexFactory.createVertex(label, routerType, args);

      assertEquals(Open.instance, router.getState());
      router.close();
      router.open();

      assertThat(router.outputMementos(), containsString(Open.instance.toString()));
      assertThat(router.outputMementos(), containsString(Close.instance.toString()));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testRestoreWithParamI() {
    String label = "hp";
    String ip = "192.168.2.2";
    String[] args = {ip};

    try {
      Server server = (Server) VertexFactory.createVertex(label, serverType, args);

      assertEquals(Open.instance, server.getState());
      assertEquals(Open.instance, server.restore(1));
      assertEquals(Open.instance, server.restore(0));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Computer computer = (Computer) VertexFactory.createVertex(label, computerType, args);

      assertEquals(Open.instance, computer.getState());

      computer.open();
      computer.close();
      computer.open();
      computer.close();

      assertEquals(Close.instance, computer.restore(3));
      assertEquals(Close.instance, computer.restore(10000));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testRestore() {
    String label = "hp";
    String ip = "192.168.2.2";
    String[] args = {ip};

    try {
      Router router = (Router) VertexFactory.createVertex(label, routerType, args);

      assertEquals(Open.instance, router.getState());
      assertEquals(Open.instance, router.restore());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Computer computer = (Computer) VertexFactory.createVertex(label, computerType, args);

      assertEquals(Open.instance, computer.getState());

      computer.open();
      computer.close();

      assertEquals(Close.instance, computer.restore());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testClose() {
    String label = "hpServer";
    String ip = "192.168.2.2";
    String[] args = {ip};

    try {
      Server server = (Server) VertexFactory.createVertex(label, serverType, args);

      assertEquals(Open.instance, server.getState());
      server.close(); // close() from Open
      assertEquals(Close.instance, server.getState());

      server.close(); // close() from Close
      assertEquals(Close.instance, server.getState());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testOpen() {
    String label = "hpComputer";
    String ip = "192.168.2.2";
    String[] args = {ip};

    try {
      Computer computer = (Computer) VertexFactory.createVertex(label, computerType, args);

      assertEquals(Open.instance, computer.getState());
      computer.open(); // open() from Open state
      assertEquals(Open.instance, computer.getState());

      computer.close();
      assertEquals(Close.instance, computer.getState());
      computer.open(); // open() from Close state
      assertEquals(Open.instance, computer.getState());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testComputer() {
    String label = "hpComputer";
    String ip = "192.168.2.2";
    String[] args = {ip};

    try {
      Computer computer = (Computer) VertexFactory.createVertex(label, computerType, args);

      assertEquals(label, computer.getLabel());
      assertEquals(ip, computer.getIp());
      Vertex vertex = VertexFactory.createVertex(label, computerType, new String[]{"192.168.1.1"});
      assertFalse(computer.equals(vertex));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testServer() {
    String label = "hpServer";
    String ip = "192.168.2.1";
    String[] args = {ip};

    try {
      Server server = (Server) VertexFactory.createVertex(label, serverType, args);

      assertEquals(label, server.getLabel());
      assertEquals(ip, server.getIp());
      Vertex vertex = VertexFactory.createVertex(label, serverType, new String[]{"192.168.1.1"});
      assertFalse(server.equals(vertex));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testRouter() {
    String label = "hpRouter";
    String ip = "192.168.2.3";
    String[] args = {ip};

    try {
      Router router = (Router) VertexFactory.createVertex(label, routerType, args);

      assertEquals(label, router.getLabel());
      assertEquals(ip, router.getIp());
      Vertex vertex = VertexFactory.createVertex(label, routerType, new String[]{"192.168.1.1"});
      assertFalse(router.equals(vertex));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testComputerOverride() {
    String label = "hpComputer";
    String ip = "192.168.2.2";
    String[] args = {ip};

    try {
      Computer computer1 = new Computer(label);
      Computer computer2 = new Computer(label);

      computer1.fillVertexInfo(args);
      computer2.fillVertexInfo(args);

      assertEquals(computer1, computer1);
      assertEquals(computer1, computer2);
      assertEquals(computer2, computer1);
      assertEquals(computer1.hashCode(), computer2.hashCode());

      assertThat(computer1.toString(), containsString("hostname='" + computer1.getLabel()
          + '\'' + ", IP='" + ip + '\''));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testServerOverride() {
    String label = "hpServer";
    String ip = "192.168.2.1";
    String[] args = {ip};

    try {
      Server server = new Server(label);
      Server server2 = new Server(label);

      server.fillVertexInfo(args);
      server2.fillVertexInfo(args);

      assertEquals(server, server);
      assertEquals(server, server2);
      assertEquals(server2, server);
      assertEquals(server.hashCode(), server2.hashCode());

      assertThat(server.toString(), containsString("hostname='" + server.getLabel()
          + '\'' + ", IP='" + ip + '\''));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testRouterOverride() {
    String label = "hpComputer";
    String ip = "192.168.2.2";
    String[] args = {ip};

    try {
      Router router1 = new Router(label);
      Router router2 = new Router(label);

      router1.fillVertexInfo(args);
      router2.fillVertexInfo(args);

      assertEquals(router1, router1);
      assertEquals(router1, router2);
      assertEquals(router2, router1);
      assertEquals(router1.hashCode(), router2.hashCode());

      assertThat(router1.toString(), containsString("hostname='" + router1.getLabel()
          + '\'' + ", IP='" + ip + '\''));
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testFactory() {
    String label = "hpComputer";
    String ip = "192.168.2.2";
    String[] args = {ip};

    try {
      Computer computer = (Computer) VertexFactory.createVertex(label, computerType, args);
      Server server = (Server) VertexFactory.createVertex(label, serverType, args);
      Router router = (Router) VertexFactory.createVertex(label, routerType, args);

      assertEquals(label, computer.getLabel());
      assertEquals(label, server.getLabel());
      assertEquals(label, router.getLabel());

      assertEquals(ip, computer.getIp());
      assertEquals(ip, server.getIp());
      assertEquals(ip, router.getIp());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testFillVertexInfo() {
    String hostName = "hp";
    String ip = "192.168.2.2";
    String ipD = "256.256.256";
    String ipD2 = "123.123.123.256";
    String[] args = null;
    String[] args1 = {ip};
    String[] args2 = {ipD};
    String[] args3 = {ip, ipD2};
    String[] args4 = {null};

    try {
      Router router = new Router(hostName);
      router.fillVertexInfo(args);

      assertEquals(hostName, router.getLabel());
      assertEquals(ip, router.getIp());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Computer computer = new Computer(hostName);
      computer.fillVertexInfo(args3);

      assertEquals(hostName, computer.getLabel());
      assertEquals(ip, computer.getIp());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Server server = new Server(hostName);
      server.fillVertexInfo(args2);

      assertEquals(hostName, server.getLabel());
      assertEquals(ip, server.getIp());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Computer computer = new Computer(hostName);
      computer.fillVertexInfo(args1);

      assertEquals(hostName, computer.getLabel());
      assertEquals(ip, computer.getIp());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

    try {
      Router router = new Router(hostName);
      router.fillVertexInfo(args4);

      assertEquals(hostName, router.getLabel());
      assertEquals(ip, router.getIp());
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }
  }

  @Test
  public void testNetworkEquipment() {
    try {
      NetworkEquipment equipment = new Computer("Computer");
      boolean answer = equipment.equals(equipment);
      assertTrue(answer);
    } catch (InputFileAgainException e) {
      LOGGER.error(e.getMessage(), e);
    }

  }
}
