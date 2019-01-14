package application;

import edge.Edge;
import exception.InputFileAgainException;
import factory.GraphFactory;
import factory.LoggerFactory;
import graph.Graph;
import java.io.IOException;
import java.util.Set;
import javax.swing.JOptionPane;
import org.apache.log4j.Logger;

/**
 * GraphPoet GUI Application.
 *
 * @author Zhu Mingyan
 */
public class GraphPoetApp {

  /*
  AF: Represents a GUI Application of GraphPoet
  RI: all fields should be non-null
      instance of SubClass of GraphGui should connected with Graph instance
  Safety for Rep Exposure:
      All fields are modified by key word private and there is
        no other setter function except Constructor
   */
  private GraphPoetAppGui graphPoetAppGui;
  private Graph graph;
  private static final Logger LOGGER = LoggerFactory.createLogger(GraphPoetApp.class);

  /**
   * Constructor of GraphPoetApp with params file.
   */
  public GraphPoetApp() {
    String filePath = JOptionPane
        .showInputDialog("Please input the file path!", "src/txt/inputGraphPoet.txt");
    while (true) {
      try {
        LOGGER.debug("Read file from " + '\"' + filePath + '\"');
        if (filePath == null) {
          throw new RuntimeException("Cannot read from null file path!");
        }
        this.graph = GraphFactory.createGraph(filePath);
        graphPoetAppGui = new GraphPoetAppGui(graph);
        while (true) {
          int limit;
          try {
            String n = JOptionPane
                .showInputDialog("Please input the n of weight you wanna to limit！");
            //                        limit = Integer.valueOf(n);
            limit = Integer.parseInt(n);
            limitWeight(limit);
            LOGGER.info("Limit weight can not be less than " + n + "!");
            break;
          } catch (InputFileAgainException | NumberFormatException e) {
            LOGGER.error(e.getMessage(), e);
          }
        }
        break;
      } catch (InputFileAgainException | IOException e) {
        LOGGER.error(e.getMessage(), e);
        filePath = JOptionPane.showInputDialog("Please input the file path!",
            "src/txt/inputGraphPoet.txt");
        //                System.out.println("The path input before with errors,
        //                please input another file path!");
        //                System.out.println("such as \"src/txt/inputSocialNetwork.txt\"");
        //                System.out.println("Attention do not enter file path with any spaces!");
        //                Scanner in = new Scanner(System.in);
        //                filePath = in.next();
        LOGGER.info("Exchange file path to " + filePath);
      }
    }
  }

  /**
   * Make the GUI App visible.
   */
  public void visible() {
    this.graphPoetAppGui.visible();
  }

  /**
   * Limit the weight of edges.
   *
   * @param n the limit.
   * @throws InputFileAgainException if n <= 1.
   */
  public void limitWeight(int n) throws InputFileAgainException {
    if (n <= 1) {
      throw new InputFileAgainException("The limit n inputs is less than 1");
    }
    Set<Edge> edges = graph.edges();
    for (Edge edge : edges) {
      if (edge.getWeight() < n) {
        graph.removeEdge(edge);
      }
    }
    graphPoetAppGui = new GraphPoetAppGui(graph);
  }

  public static void main(String[] args) {
    GraphPoetApp graphPoetApp = new GraphPoetApp();
    graphPoetApp.visible();
  }
}
