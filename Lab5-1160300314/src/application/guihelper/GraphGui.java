package application.guihelper;

import edge.DirectedEdge;
import edge.Edge;
import edge.HyperEdge;
import edge.UndirectedEdge;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import exception.InputFileAgainException;
import factory.EdgeFactory;
import factory.HyperEdgeFactory;
import factory.LoggerFactory;
import factory.VertexFactory;
import graph.Graph;
import helper.GraphMetrics;
import helper.GraphVisualizationHelper;
import helper.OutputGraph;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.apache.log4j.Logger;
import vertex.Vertex;

/**
 * Used to help display the visualization of Graph App.
 *
 * @author Zhu Mingyan
 */
public class GraphGui extends JFrame {

  /*
  AF: Represents a Frame which used to display graph visualization.
  RI: graph, visualizationViewer and panel which used to display graph should not be non-null
  Safety for Rep Exposure:
      All fields are modified by key word private and all setter
      we provided is based on Graph Interface.
   */
  private Graph graph;
  private VisualizationViewer viewer;
  private JPanel panel;
  private static final Logger LOGGER = LoggerFactory.createLogger(GraphGui.class);
  private GraphZoomScrollPane[] scrollPane;

  private JMenuBar bar = new JMenuBar();
  private JMenu menuHelp = new JMenu("Help(F)");
  private JMenuItem addVertex = new JMenuItem("Add Vertex");
  private JMenuItem modifyVertex = new JMenuItem("Modify Vertex");
  private JMenuItem deleteVertex = new JMenuItem("Delete Vertex");
  private JMenuItem addEdge = new JMenuItem("Add Edge");
  private JMenuItem modifyEdge = new JMenuItem("Modify Edge");
  private JMenuItem deleteEdge = new JMenuItem("Delete Edge");
  private JMenuItem outputGraph = new JMenuItem("Output Graph Info");

  private JMenu menuCalculate = new JMenu("Calculate(C)");
  private JMenuItem graphDegreeCentrality = new JMenuItem("Graph Degree Centrality");
  private JMenuItem radius = new JMenuItem("Radius");
  private JMenuItem distance = new JMenuItem("Calculate Distance");
  private JMenuItem diameter = new JMenuItem("Diameter");

  private JMenuItem getDegreeCentrality = new JMenuItem("Vertex Degree Centrality");
  private JMenuItem getClosenessCentrality = new JMenuItem("Vertex ClosenessCentrality");
  private JMenuItem getBetweennessCentrality = new JMenuItem("Vertex BetweennessCentrality");
  private JMenuItem getEccentricity = new JMenuItem("Vertex Eccentricity");

  private JMenu logQuery = new JMenu("log(L)");
  private JMenuItem query = new JMenuItem("Log Query");

  private String vertexLabel = "";
  private String edgeLabel = "";
  private String[] args;
  private Vertex source;
  private Vertex destination;
  private double weight;
  private List<Vertex> vertexList;

  /**
   * Create instance from a graph instance.
   */
  public GraphGui(Graph graph) {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
        | UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }
    this.graph = graph;
    this.viewer = GraphVisualizationHelper.getJung(this.graph);
    this.panel = new RightPanel(this.viewer);
    this.setTitle("Graph");
    this.setSize(1100, 850);
    this.setLocationRelativeTo(null);
    this.getContentPane().setLayout(new BorderLayout(5, 10));

    scrollPane = new GraphZoomScrollPane[]{new GraphZoomScrollPane(viewer)};
    this.getContentPane().add(scrollPane[0], BorderLayout.WEST);
    this.getContentPane().add(panel, BorderLayout.EAST);

    menuHelp.setMnemonic('F'); // Alt + F快捷键
    menuHelp.add(addVertex);
    menuHelp.add(deleteVertex);
    menuHelp.add(modifyVertex);
    menuHelp.add(addEdge);
    menuHelp.add(deleteEdge);
    menuHelp.add(modifyEdge);
    menuHelp.add(outputGraph);

    menuCalculate.setMnemonic('C');
    menuCalculate.add(graphDegreeCentrality);
    menuCalculate.add(radius);
    menuCalculate.add(diameter);
    menuCalculate.add(getDegreeCentrality);
    menuCalculate.add(getClosenessCentrality);
    menuCalculate.add(getBetweennessCentrality);
    menuCalculate.add(getEccentricity);
    menuCalculate.add(distance);

    logQuery.add(query);
    logQuery.setMnemonic('L');

    bar.add(menuHelp);
    bar.add(menuCalculate);
    bar.add(logQuery);
    addListener();

    this.setJMenuBar(bar);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void iniRep() {
    this.vertexList = null;
    this.weight = 0;
    this.vertexLabel = "";
    this.source = null;
    this.destination = null;
    this.args = null;
  }

  /**
   * Add edge into Graph.
   *
   * @param edge its Type should be one of all edge types we have defined and is non-null
   * @return true if adding successfully, otherwise false
   */
  public boolean addEdgeInGraph(Edge edge) throws InputFileAgainException {
    return graph.addEdge(edge);
  }

  /**
   * Add vertex into Graph.
   *
   * @param vertex its Type should be one of all vertex types we have defined before and is
   *        non-null
   * @return true if adding successfully, otherwise false
   */
  public boolean addVertexInGraph(Vertex vertex) {
    return graph.addVertex(vertex);
  }

  private void addListener() {
    // 增加outputGraph的功能 可以在GUI界面选择输出修改后的图的信息
    outputGraph.addActionListener(e -> {
      String string = JOptionPane.showInputDialog("Please input file name you wanna output to");
      String fileName = "src/txt/" + string + ".txt";
      //      OutputStreamWriter writer;
      //      BufferedWriter bufferedWriter;
      try {
        OutputGraph.outputGraph(graph, fileName);
        //        writer = new OutputStreamWriter(new FileOutputStream(fileName), "utf-8");
        //        bufferedWriter = new BufferedWriter(writer);
        //        bufferedWriter.write(OutputGraph.outputGraph(graph));
        //        bufferedWriter.flush();
        //        bufferedWriter.close();
        JOptionPane.showMessageDialog(null, "Output to src/txt/"
            + string + ".txt successfully!");
        LOGGER.debug("Output the graph into " + fileName + " successfully!");
      } catch (IOException e1) {
        LOGGER.error(e1.getMessage());
      }
    });
    query.addActionListener(e -> new LoggingQuery());
    addVertex.addActionListener(e -> {
      String[] strings = {"Word", "Person", "Computer", "Router", "Server", "Actor", "Director",
          "Movie"};

      String input = (String) JOptionPane.showInputDialog(null,
          "请选择需要增加Vertex的类型:\n", "Add Vertex", JOptionPane.QUESTION_MESSAGE,
          null, strings, strings[0]);
      if (input != null) {
        switch (input) {
          case "Computer":
          case "Router":
          case "Server":
          case "WirelessRouter":
            NetworkEquipmentDialog networkEquipmentDialog = new NetworkEquipmentDialog(this, this);
            break;
          case "Movie":
            MovieDialog movieDialog = new MovieDialog(this, this);
            break;
          case "Person":
            PersonDialog personDialog = new PersonDialog(this, this);
            break;
          case "Actor":
          case "Director":
            CastMemberDialog castMemberDialog = new CastMemberDialog(this, this);
            break;
          default:
            WordDialog wordDialog = new WordDialog(this, this);
            break;
        }
        if (vertexLabel.length() != 0) {
          Vertex vertex = null;
          try {
            vertex = VertexFactory.createVertex(vertexLabel, input, args);
            if (addVertexInGraph(vertex)) {
              LOGGER.debug("Add vertex " + vertexLabel + " in Graph ");
            }
            refreshPanel();
            iniRep();
          } catch (InputFileAgainException e1) {
            LOGGER.error(e1.getMessage(), e1);
          }
        }
      }
    });

    deleteVertex.addActionListener(e -> {
      Set<Vertex> vertexSet = this.graph.vertices();
      Object[] vertices = vertexSet.toArray();
      Vertex chosenVertex = (Vertex) JOptionPane.showInputDialog(null,
          "Choose the vertex you want to delete:\n",
          "Delete Vertex", JOptionPane.WARNING_MESSAGE, null, vertices, vertices[0]);
      if (this.graph.removeVertex(chosenVertex)) {
        LOGGER.debug("Delete vertex " + chosenVertex.getLabel() + " from Graph");
      }
      refreshPanel();
    });

    deleteEdge.addActionListener(e -> {
      Set<Edge> edgeSet = this.graph.edges();
      Object[] edges = edgeSet.toArray();
      Edge chosenEdge = (Edge) JOptionPane.showInputDialog(null,
          "Choose the edge you want to delete:\n",
          "Delete Edge", JOptionPane.WARNING_MESSAGE, null, edges, edges[0]);
      try {
        if (this.graph.removeEdge(chosenEdge)) {
          LOGGER.debug("Delete edge " + chosenEdge.getLabel() + " from Graph");
        }
        refreshPanel();
      } catch (InputFileAgainException e1) {
        LOGGER.error(e1.getMessage(), e1);
      }
    });

    modifyEdge.addActionListener(e -> {
      Set<Edge> edgeSet = this.graph.edges();
      Object[] edges = edgeSet.toArray();
      Edge chosenEdge = (Edge) JOptionPane.showInputDialog(null,
          "Choose the edge you want to delete:\n",
          "Delete Edge", JOptionPane.WARNING_MESSAGE, null, edges, edges[0]);
      String edgeType = chosenEdge.getClass().getSimpleName();
      switch (edgeType) {
        case "SameMovieHyperEdge":
          HyperEdgeDialog hyperEdgeDialog = new HyperEdgeDialog(this, this, chosenEdge);
          if (edgeLabel.length() != 0 && vertexList != null) {
            Edge edge = null;
            try {
              edge = HyperEdgeFactory.createEdge(edgeLabel, edgeType, vertexList);
              replaceEdge(chosenEdge, edge);
              refreshPanel();
              LOGGER.debug("Modify edge " + chosenEdge.toString() + " to " + edge.toString());
              iniRep();
            } catch (InputFileAgainException e1) {
              LOGGER.error(e1.getMessage(), e1);
            }
          }
          break;
        default:
          EdgeDialog edgeDialog = new EdgeDialog(this, this, chosenEdge);
          if (edgeLabel.length() != 0 && weight != 0 && vertexList != null) {
            Edge edge = null;
            try {
              edge = EdgeFactory.createEdge(edgeLabel, edgeType, weight, vertexList);
              replaceEdge(chosenEdge, edge);
              refreshPanel();
              LOGGER.debug("Modify edge " + chosenEdge.toString() + " to " + edge.toString());
              iniRep();
            } catch (InputFileAgainException e1) {
              LOGGER.error(e1.getMessage(), e1);
            }
          }
          break;
      }
    });

    graphDegreeCentrality.addActionListener(e -> {
      double tempDegreeCentrality = GraphMetrics.degreeCentrality(graph);
      JOptionPane.showMessageDialog(this, "Graph degree centrality:" + tempDegreeCentrality);
    });

    radius.addActionListener(e -> {
      double tempRadius = GraphMetrics.radius(graph);
      JOptionPane.showMessageDialog(this, "Graph Radius:" + tempRadius);
    });

    diameter.addActionListener(e -> {
      double tempDiameter = GraphMetrics.diameter(graph);
      JOptionPane.showMessageDialog(this, "Graph Diameter:" + tempDiameter);
    });

    getDegreeCentrality.addActionListener(e -> {
      Set<Vertex> vertexSet = graph.vertices();
      Vertex chosenVertex = (Vertex) JOptionPane.showInputDialog(this,
          "请选择需要进行计算的顶点:\n",
          "Calculate", JOptionPane.QUESTION_MESSAGE, null, vertexSet.toArray(),
          vertexSet.toArray()[0]);
      double tempDegree = GraphMetrics.degreeCentrality(graph, chosenVertex);
      JOptionPane
          .showMessageDialog(this, chosenVertex.getLabel()
              + " degree Centrality:" + tempDegree);
    });

    getClosenessCentrality.addActionListener(e -> {
      Set<Vertex> vertexSet = graph.vertices();
      Vertex chosenVertex = (Vertex) JOptionPane.showInputDialog(this,
          "请选择需要进行计算的顶点:\n", "Calculate", JOptionPane.QUESTION_MESSAGE,
          null, vertexSet.toArray(),
          vertexSet.toArray()[0]);
      double tempClosenessCentrality = GraphMetrics.closenessCentrality(graph, chosenVertex);
      JOptionPane.showMessageDialog(this,
          chosenVertex.getLabel() + " closeness Centrality:" + tempClosenessCentrality);
    });

    getBetweennessCentrality.addActionListener(e -> {
      Set<Vertex> vertexSet = graph.vertices();
      Vertex chosenVertex = (Vertex) JOptionPane.showInputDialog(this,
          "请选择需要进行计算的顶点:\n", "Calculate", JOptionPane.QUESTION_MESSAGE,
          null, vertexSet.toArray(),
          vertexSet.toArray()[0]);
      double tempBetweennessCentrality = GraphMetrics.betweennessCentrality(graph, chosenVertex);
      JOptionPane.showMessageDialog(this,
          chosenVertex.getLabel() + " betweenness Centrality:" + tempBetweennessCentrality);
    });

    getEccentricity.addActionListener(e -> {
      Set<Vertex> vertexSet = graph.vertices();
      Vertex chosenVertex = (Vertex) JOptionPane.showInputDialog(this,
          "请选择需要进行计算的顶点:\n",
          "Calculate", JOptionPane.QUESTION_MESSAGE, null, vertexSet.toArray(),
          vertexSet.toArray()[0]);
      double tempEccentricity = GraphMetrics.eccentricity(graph, chosenVertex);
      JOptionPane.showMessageDialog(this, chosenVertex.getLabel()
          + " eccentricity:" + tempEccentricity);
    });

    distance.addActionListener(e -> {
      DistanceDialog distanceDialog = new DistanceDialog(this, this);
      if (source != null && destination != null) {
        JOptionPane.showMessageDialog(this, source.getLabel() + "->"
            + destination.getLabel() + ":" + GraphMetrics.distance(graph, source, destination));
      }
      setSource(null);
      setDestination(null);
    });

    modifyVertex.addActionListener(e -> {
      Set<Vertex> vertexSet = this.graph.vertices();
      Object[] vertices = vertexSet.toArray();
      Vertex chosenVertex = (Vertex) JOptionPane.showInputDialog(null,
          "Choose the vertex you want to modify:\n",
          "Modify Vertex", JOptionPane.WARNING_MESSAGE,
          null, vertices, vertices[0]);
      if (chosenVertex != null) {
        String vertexType = chosenVertex.getClass().getSimpleName();
        switch (vertexType) {
          case "Computer":
          case "Router":
          case "Server":
          case "WirelessRouter":
            NetworkEquipmentDialog networkEquipmentDialog
                = new NetworkEquipmentDialog(this, this, chosenVertex);
            break;
          case "Movie":
            MovieDialog movieDialog = new MovieDialog(this, this, chosenVertex);
            break;
          case "Person":
            PersonDialog personDialog = new PersonDialog(this, this, chosenVertex);
            break;
          case "Actor":
          case "Director":
            CastMemberDialog castMemberDialog = new CastMemberDialog(this, this, chosenVertex);
            break;
          default:
            WordDialog wordDialog = new WordDialog(this, this, chosenVertex);
            break;
        }
        if (vertexLabel.length() != 0) {
          Vertex vertex = null;
          try {
            vertex = VertexFactory
                .createVertex(vertexLabel, chosenVertex.getClass().getSimpleName(), args);
            replaceVertex(chosenVertex, vertex);
            graph.removeVertex(chosenVertex);
            refreshPanel();
            LOGGER.debug(
                "Modify vertex from " + chosenVertex.toString() + " to " + vertex.toString());
            iniRep();
          } catch (InputFileAgainException e1) {
            LOGGER.error("Modify vertex with wrong number of params", e1);
          }
        }
      }
    });

    addEdge.addActionListener(e -> {
      String[] typesOfEdge = {"CommentTie", "ForwardTie", "FriendTie", "MovieActorRelation",
          "MovieDirectorRelation", "NetworkConnection", "WordNeighborhood", "SameMovieHyperEdge"};
      String chosenType = (String) JOptionPane.showInputDialog(null,
          "Choose the Type of edge you want add:\n",
          "Add Edge", JOptionPane.QUESTION_MESSAGE, null, typesOfEdge, typesOfEdge[0]);
      switch (chosenType) {
        case "CommentTie":
        case "ForwardTie":
        case "FriendTie":
        case "MovieActorRelation":
        case "MovieDirectorRelation":
        case "NetworkConnection":
        case "WordNeighborhood":
          EdgeDialog edgeDialog = new EdgeDialog(this, this);
          break;
        case "SameMovieHyperEdge":
          HyperEdgeDialog hyperEdgeDialog = new HyperEdgeDialog(this, this);
          if (edgeLabel.length() != 0 && vertexList != null) {
            HyperEdge hyperEdge = null;
            try {
              hyperEdge = HyperEdgeFactory.createEdge(edgeLabel, chosenType, vertexList);
              if (addEdgeInGraph(hyperEdge)) {
                LOGGER.debug("Add HyperEdge " + edgeLabel);
              }
              refreshPanel();
              iniRep();
            } catch (InputFileAgainException e1) {
              LOGGER.error(e1.getMessage(), e1);
            }
          }
          break;
        default:
          throw new RuntimeException("Can not reach this!");
      }
      if (edgeLabel.length() != 0 && weight != 0 && vertexList != null) {
        Edge edge = null;
        try {
          edge = EdgeFactory.createEdge(edgeLabel, chosenType, weight, vertexList);
          if (addEdgeInGraph(edge)) {
            LOGGER.debug("Add edge " + edgeLabel);
          }
          refreshPanel();
          iniRep();
        } catch (InputFileAgainException e1) {
          LOGGER.error(e1.getMessage(), e1);
        }
      }
    });
  }

  /**
   * Make the visualization of graph visible.
   */
  public void visible() {
    LOGGER.info("Set the GUI visible!");
    this.setVisible(true);
  }

  /*
   * Set the new label of latter vertex or the label of new vertex
   *
   * @param label non-null and non-empty string
   */
  protected void setVertexLabel(String label) {
    this.vertexLabel = label;
  }

  /*
   * Set the new label of latter edge or the label of new edge
   *
   * @param label non-null and non-empty string
   */
  protected void setEdgeLabel(String label) {
    this.edgeLabel = label;
  }

  /*
   * Fill the args of vertex
   *
   * @param args non-null and non-empty string array
   */
  protected void setArgs(String[] args) {
    this.args = args;
  }

  /*
   * Get all vertices in graph
   *
   * @return a set of vertices
   */
  protected Set<Vertex> getVertices() {
    return this.graph.vertices();
  }

  /*
  Replace vertex with new Vertex and change all edges connected the old one to the new one.
   */
  private void replaceVertex(Vertex vertex, Vertex newVertex) {
    try {
      Set<Edge> edgeSet = graph.edges();
      for (Edge edge : edgeSet) {
        if (edge instanceof DirectedEdge) {
          List<Vertex> source = new ArrayList<>(edge.sourceVertices());
          List<Vertex> target = new ArrayList<>(edge.targetVertices());
          if (source.contains(vertex) && target.contains(vertex)) {
            graph.removeEdge(edge);
            Edge tempEdge = EdgeFactory
                .createEdge(edge.getLabel(), edge.getClass().getSimpleName(), edge.getWeight(),
                    Arrays.asList(newVertex, newVertex));
            graph.addEdge(tempEdge);
          } else if (source.contains(vertex)) {
            graph.removeEdge(edge);
            Edge tempEdge = EdgeFactory
                .createEdge(edge.getLabel(), edge.getClass().getSimpleName(), edge.getWeight(),
                    Arrays.asList(newVertex, target.get(0)));
            graph.addEdge(tempEdge);
          } else if (target.contains(vertex)) {
            graph.removeEdge(edge);
            Edge tempEdge = EdgeFactory
                .createEdge(edge.getLabel(), edge.getClass().getSimpleName(), edge.getWeight(),
                    Arrays.asList(source.get(0), newVertex));
            graph.addEdge(tempEdge);
          }
        } else if (edge instanceof UndirectedEdge) {
          Set<Vertex> vertices = edge.vertices();
          List<Vertex> vertexList = new ArrayList<>(vertices);
          if (vertexList.size() == 1 && vertices.contains(vertex)) {
            graph.removeEdge(edge);
            Edge tempEdge = EdgeFactory
                .createEdge(edge.getLabel(), edge.getClass().getSimpleName(), edge.getWeight(),
                    Arrays.asList(newVertex));
            graph.addEdge(tempEdge);
          } else if (vertices.contains(vertex)) {
            vertexList.remove(vertex);
            graph.removeEdge(edge);
            Edge tempEdge = EdgeFactory
                .createEdge(edge.getLabel(), edge.getClass().getSimpleName(), edge.getWeight(),
                    Arrays.asList(vertexList.get(0), newVertex));
            graph.addEdge(tempEdge);
          }
        } else {
          Set<Vertex> vertexSet = edge.vertices();
          if (vertexSet.contains(vertex)) {
            vertexSet.remove(vertex);
            vertexSet.add(newVertex);
            graph.removeEdge(edge);
            HyperEdge hyperEdge = null;
            hyperEdge = HyperEdgeFactory
                .createEdge(edge.getLabel(), edge.getClass().getSimpleName(),
                    new ArrayList<>(vertexSet));
            graph.addEdge(hyperEdge);
          }
        }
      }
    } catch (InputFileAgainException e) {
      e.printStackTrace();
    }
  }

  private void replaceEdge(Edge edge, Edge newEdge) throws InputFileAgainException {
    graph.removeEdge(edge);
    graph.addEdge(newEdge);
  }

  protected void setSource(Vertex vertex) {
    this.source = vertex;
  }

  protected void setDestination(Vertex vertex) {
    this.destination = vertex;
  }

  protected void setWeight(double weight) {
    this.weight = weight;
  }

  protected void setVertexList(List<Vertex> list) {
    this.vertexList = list;
  }

  private void refreshPanel() {
    this.viewer = GraphVisualizationHelper.getJung(graph);
    this.getContentPane().removeAll();
    this.scrollPane[0] = new GraphZoomScrollPane(this.viewer);
    panel = new RightPanel(this.viewer);
    this.getContentPane().add(scrollPane[0], BorderLayout.WEST);
    this.getContentPane().add(panel, BorderLayout.EAST);
    this.getContentPane().invalidate();
    this.getContentPane().validate();
    this.getContentPane().repaint();
  }

  //    public static void main(String[] args) {
  //        GraphGui tempGraphGUI = new GraphGui(GraphFactory.createGraph("src/txt/GraphPoet.txt"));
  //        tempGraphGUI.visible();
  //    }

  class RightPanel extends JPanel {

    GroupLayout layout = new GroupLayout(this);
    JLabel label = new JLabel("Graph Name");
    JLabel graphName = new JLabel(graph.getGraphName());
    JButton plus = new JButton("+");
    JButton minus = new JButton("-");
    JButton reset = new JButton("reset");
    JTextArea hyperEdge = new JTextArea(10, 15);
    JScrollPane scrollPane1;
    int number = 0;

    RightPanel(VisualizationViewer vv) {
      hyperEdge.setFont(new Font(Font.DIALOG, Font.PLAIN, 14));
      hyperEdge.setLineWrap(true);
      hyperEdge.setWrapStyleWord(true);
      hyperEdge.setEditable(false);
      StringBuilder hyperEdgeString = new StringBuilder("HyperEdge(s):\n");
      Set<Edge> edges = graph.edges();
      for (Edge edge : edges) {
        if (edge instanceof HyperEdge) {
          hyperEdgeString.append("\n").append(String.valueOf(++number)).append(".\n")
              .append(edge.vertices());
        }
      }
      hyperEdge.setText(hyperEdgeString.toString());

      scrollPane1 = new JScrollPane(hyperEdge);

      final ScalingControl scalingControl = new CrossoverScalingControl();
      plus.setPreferredSize(new Dimension(100, 100));

      plus.addActionListener(e -> scalingControl.scale(vv, 1.1f, vv.getCenter()));

      minus.addActionListener(e -> scalingControl.scale(vv, 1 / 1.1f, vv.getCenter()));

      reset.addActionListener(e -> {
        vv.getRenderContext()
            .getMultiLayerTransformer()
            .getTransformer(Layer.LAYOUT)
            .setToIdentity();
        vv.getRenderContext()
            .getMultiLayerTransformer()
            .getTransformer(Layer.VIEW)
            .setToIdentity();
      });

      label.setFont(new Font("Dialog", 0, 15));
      graphName.setFont(new Font("Dialog", 1, 20));
      graphName.setForeground(Color.BLACK);
      GroupLayout.SequentialGroup hgroup = layout.createSequentialGroup();
      this.setLayout(layout);
      hgroup.addGap(5);
      hgroup.addGroup(layout.createParallelGroup().addComponent(label).addComponent(graphName)
          .addComponent(plus)
          .addComponent(minus)
          .addComponent(reset)
          .addComponent(scrollPane1));
      hgroup.addGap(5);
      layout.setHorizontalGroup(hgroup);

      GroupLayout.SequentialGroup vgroup = layout.createSequentialGroup();
      vgroup.addGap(10);
      vgroup.addGroup(layout.createParallelGroup().addComponent(label));
      vgroup.addGap(10);
      vgroup.addGroup(layout.createParallelGroup().addComponent(graphName));
      vgroup.addGap(10);
      vgroup.addGroup(layout.createParallelGroup().addComponent(plus));
      vgroup.addGap(10);
      vgroup.addGroup(layout.createParallelGroup().addComponent(minus));
      vgroup.addGap(10);
      vgroup.addGroup(layout.createParallelGroup().addComponent(reset));
      vgroup.addGap(10);
      vgroup.addGroup(layout.createParallelGroup().addComponent(scrollPane1));
      vgroup.addGap(10);
      layout.setVerticalGroup(vgroup);
    }
  }
}
