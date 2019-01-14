import exception.InputFileAgainException;
import factory.GraphFactory;
import factory.LoggerFactory;
import graph.Graph;
import java.io.IOException;
import org.apache.log4j.Logger;

public class Main {

  /**
   * For some test.
   */
  public static void main(String[] args) {
    Logger LOGGER = LoggerFactory.createLogger(Main.class);

    try {
      Graph graph = GraphFactory.createGraph("src/txt/test.txt");
      System.out.println(graph);
    } catch (IOException | InputFileAgainException e) {
      LOGGER.debug(e.getMessage(), e);
    }
  }
}
