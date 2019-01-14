package helper;

import edge.Edge;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.renderers.Renderer;
import graph.Graph;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Paint;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.commons.collections15.Transformer;
import vertex.Actor;
import vertex.Computer;
import vertex.Director;
import vertex.Movie;
import vertex.Person;
import vertex.Router;
import vertex.Server;
import vertex.Vertex;
import vertex.Word;

/**
 * Used for visualization of graphs and get JPanel indicates visualization of graph.
 *
 * @param <L> vertex class of graph
 * @param <E> edge class of graph
 */
public class GraphVisualizationHelper<L, E> {

  /**
   * Used for visualization of graph.
   *
   * @param g graph needed to visual and non-null
   * @param <L> vertex class of graph which should one of all vertex types we have defined before
   * @param <E> edge class of graph which should one of all edge types we have defined before
   */
  public static <L extends Vertex, E extends Edge> void visualize(Graph<L, E> g) {

    JFrame frame = new JFrame("Just for test");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    JPanel vv = getJung(g);
    //        frame.getContentPane().add(vv);
    frame.pack();
    frame.setVisible(true);
    frame.setSize(new Dimension(1000, 1000));
    frame.setLocationRelativeTo(null);
    final GraphZoomScrollPane scrollPane = new GraphZoomScrollPane((VisualizationViewer) vv);
    frame.getContentPane().add(scrollPane);
    //        frame.add(jScrollBar);

  }

  /**
   * Get a JPanel instance which contains the visualization of graph.
   *
   * @param g graph needed to visual and is non-null
   * @param <L> vertex class of graph which should one of all vertex types we have defined before
   * @param <E> edge class of graph which should one of all edge types we have defined before
   * @return an instance of VisualizationViewer
   */
  public static <L extends Vertex, E extends Edge> VisualizationViewer<L, E> getJung(
      Graph<L, E> g) {
    SparseMultigraph<L, E> graph = GraphMetrics.cloneJungGraph(g);
    //        DefaultModalGraphMouse<L, E> graph = new DefaultModalGraphMouse<L, E>();
    Layout<L, E> layout = new CircleLayout<>(graph);
    layout.setSize(new Dimension(900, 900));

    VisualizationViewer<L, E> vv = new VisualizationViewer<>(layout);
    DefaultModalGraphMouse<L, E> gm = new DefaultModalGraphMouse<>();
    gm.setMode(ModalGraphMouse.Mode.PICKING);

    vv.setGraphMouse(gm);

    vv.addKeyListener(gm.getModeKeyListener());

    vv.setPreferredSize(new Dimension(850, 850));
    vv.getRenderContext().setVertexLabelTransformer(new VertexLabelHelper());
    vv.getRenderContext().setEdgeLabelTransformer(new EdgeLabelHelper());

    Transformer<L, Paint> vertexPaint = l -> {
      if (l instanceof Movie) {
        return Color.GREEN;
      } else if (l instanceof Actor) {
        return Color.BLUE;
      } else if (l instanceof Director) {
        return Color.ORANGE;
      } else if (l instanceof Person) {
        return Color.RED;
      } else if (l instanceof Word) {
        return Color.PINK;
      } else if (l instanceof Router) {
        return Color.CYAN;
      } else if (l instanceof Computer) {
        return Color.MAGENTA;
      } else if (l instanceof Server) {
        return Color.YELLOW;
      } else {
        return Color.GRAY;
      }
    };

    vv.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
    Transformer<L, Shape> vstr = i -> {
      int len = i.getLabel().length() + i.getClass().getSimpleName().length();
      if (len < 4) {
        len = 4;
      }

      RoundRectangle2D.Double r = new RoundRectangle2D.Double(
          -len * 5, -10, len * 10, 20, 10, 10);
      return r;
    };
    vv.getRenderContext().setVertexShapeTransformer(vstr);

    vv.getRenderContext().setVertexFontTransformer(
        i -> new Font("Plain", Font.PLAIN, 16));
    vv.getRenderContext().setEdgeFontTransformer(
        i -> new Font("Plain", Font.BOLD, 16));
    vv.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.CNTR);
    return vv;
  }
  //    用于测试
  //    public static void main(String[] args) {
  //        Graph graph = GraphFactory.createGraph("src/txt/inputMovieGraph.txt");
  //        visualize(graph);
  //
  //        AddNodeDemo demo = new AddNodeDemo();
  //        demo.main(null);
  //        SubLayoutDemo subLayoutDemo = new SubLayoutDemo();
  //        subLayoutDemo.main(null);
  //        GraphZoomScrollPaneDemo scrollPaneDemo = new GraphZoomScrollPaneDemo();
  //        scrollPaneDemo.main(null);
  //
  //    }

}
