package application.GUIHelper;

import edge.DirectedEdge;
import edge.Edge;
import edge.HyperEdge;
import edge.UndirectedEdge;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.Layer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import exception.*;
import factory.*;
import graph.Graph;
import helper.GraphMetrics;
import helper.GraphVisualizationHelper;
import org.apache.log4j.Logger;
import vertex.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

/**
 * Used to help display the visualization of Graph App
 *
 * @author Zhu Mingyan
 */
public class GraphGUI extends JFrame {
    /*
    AF: Represents a Frame which used to display graph visualization.
    RI: graph, visualizationViewer and panel which used to display graph should not be non-null
    Safety for Rep Exposure:
        All fields are modified by key word private and all setter we provided is based on Graph Interface.
     */
    private Graph graph;
    private VisualizationViewer g;
    private JPanel jPanel;
    private static final Logger LOGGER = LoggerFactory.createLogger(GraphGUI.class);
    private GraphZoomScrollPane[] scrollPane;

    private JMenuBar jMenuBar = new JMenuBar();
    private JMenu menuHelp = new JMenu("Help(F)");
    private JMenuItem add_vertex = new JMenuItem("Add Vertex");
    private JMenuItem modify_vertex = new JMenuItem("Modify Vertex");
    private JMenuItem delete_vertex = new JMenuItem("Delete Vertex");
    private JMenuItem add_edge = new JMenuItem("Add Edge");
    private JMenuItem modify_edge = new JMenuItem("Modify Edge");
    private JMenuItem delete_edge = new JMenuItem("Delete Edge");

    private JMenu menuCalculate = new JMenu("Calculate(C)");
    private JMenuItem graph_degree_centrality = new JMenuItem("Graph Degree Centrality");
    private JMenuItem radius = new JMenuItem("Radius");
    private JMenuItem distance = new JMenuItem("Calculate Distance");
    private JMenuItem diameter = new JMenuItem("Diameter");

    private JMenuItem getDegreeCentrality = new JMenuItem("Vertex Degree Centrality");
    private JMenuItem getClosenessCentrality = new JMenuItem("Vertex ClosenessCentrality");
    private JMenuItem getBetweennessCentrality = new JMenuItem("Vertex BetweennessCentrality");
    private JMenuItem getEccentricity = new JMenuItem("Vertex Eccentricity");

    private String vertexLabel = "";
    private String edgeLabel = "";
    private String[] args;
    private Vertex source;
    private Vertex destination;
    private double weight;
    private List<Vertex> vertexList;

    /**
     * Create instance from a graph instance.
     *
     * @param graph
     */
    public GraphGUI(Graph graph) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        this.graph = graph;
        this.g = GraphVisualizationHelper.getJUNG(this.graph);
        this.jPanel = new RightPanel(this.g);
        this.setTitle("Graph");
        this.setSize(1100, 850);
        this.setLocationRelativeTo(null);
        this.getContentPane().setLayout(new BorderLayout(5, 10));

        scrollPane = new GraphZoomScrollPane[]{new GraphZoomScrollPane(g)};
        this.getContentPane().add(scrollPane[0], BorderLayout.WEST);
        this.getContentPane().add(jPanel, BorderLayout.EAST);

        menuHelp.setMnemonic('F'); // Alt + F快捷键
        menuHelp.add(add_vertex);

        menuHelp.add(delete_vertex);

        menuHelp.add(modify_vertex);
        menuHelp.add(add_edge);

        menuHelp.add(delete_edge);
        menuCalculate.setMnemonic('C');

        menuHelp.add(modify_edge);

        menuCalculate.add(graph_degree_centrality);

        menuCalculate.add(radius);

        menuCalculate.add(diameter);

        menuCalculate.add(getDegreeCentrality);

        menuCalculate.add(getClosenessCentrality);

        menuCalculate.add(getBetweennessCentrality);

        menuCalculate.add(getEccentricity);

        menuCalculate.add(distance);

        jMenuBar.add(menuHelp);
        jMenuBar.add(menuCalculate);

        addListener();

        this.setJMenuBar(jMenuBar);
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
     * Add edge into Graph
     *
     * @param edge its type should be one of all edge types we have defined and is non-null
     * @return true if adding successfully, otherwise false
     */
    public boolean addEdgeInGraph(Edge edge) throws InputFileAgainException {
        return graph.addEdge(edge);
    }

    /**
     * Add vertex into Graph
     *
     * @param vertex its type should be one of all vertex types we have defined before and is non-null
     * @return true if adding successfully, otherwise false
     */
    public boolean addVertexInGraph(Vertex vertex) {
        return graph.addVertex(vertex);
    }

    private void addListener() {
        add_vertex.addActionListener(e -> {
            String[] strings = {"Word", "Person", "Computer", "Router", "Server", "Actor", "Director", "Movie"};

            String input = (String) JOptionPane.showInputDialog(null, "请选择需要增加Vertex的类型:\n"
                    , "Add Vertex", JOptionPane.QUESTION_MESSAGE, null, strings, strings[0]);
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

        delete_vertex.addActionListener(e -> {
            Set<Vertex> vertexSet = this.graph.vertices();
            Object[] vertices = vertexSet.toArray();
            Vertex chosenVertex = (Vertex) JOptionPane.showInputDialog(null
                    , "Choose the vertex you want to delete:\n"
                    , "Delete Vertex", JOptionPane.WARNING_MESSAGE, null, vertices, vertices[0]);
            if (this.graph.removeVertex(chosenVertex)) {
                LOGGER.debug("Delete vertex " + chosenVertex.getLabel() + " from Graph");
            }
            refreshPanel();
        });

        delete_edge.addActionListener(e -> {
            Set<Edge> edgeSet = this.graph.edges();
            Object[] edges = edgeSet.toArray();
            Edge chosenEdge = (Edge) JOptionPane.showInputDialog(null
                    , "Choose the edge you want to delete:\n"
                    , "Delete Edge", JOptionPane.WARNING_MESSAGE, null, edges, edges[0]);
            try {
                if (this.graph.removeEdge(chosenEdge)) {
                    LOGGER.debug("Delete edge " + chosenEdge.getLabel() + " from Graph");
                }
                refreshPanel();
            } catch (InputFileAgainException e1) {
                LOGGER.error(e1.getMessage(), e1);
            }
        });

        modify_edge.addActionListener(e -> {
            Set<Edge> edgeSet = this.graph.edges();
            Object[] edges = edgeSet.toArray();
            Edge chosenEdge = (Edge) JOptionPane.showInputDialog(null
                    , "Choose the edge you want to delete:\n"
                    , "Delete Edge", JOptionPane.WARNING_MESSAGE, null, edges, edges[0]);
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

        graph_degree_centrality.addActionListener(e -> {
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
            Vertex chosenVertex = (Vertex) JOptionPane.showInputDialog(this, "请选择需要进行计算的顶点:\n"
                    , "Calculate", JOptionPane.QUESTION_MESSAGE, null, vertexSet.toArray(), vertexSet.toArray()[0]);
            double tempDegree = GraphMetrics.degreeCentrality(graph, chosenVertex);
            JOptionPane.showMessageDialog(this, chosenVertex.getLabel() + " degree Centrality:" + tempDegree);
        });

        getClosenessCentrality.addActionListener(e -> {
            Set<Vertex> vertexSet = graph.vertices();
            Vertex chosenVertex = (Vertex) JOptionPane.showInputDialog(this, "请选择需要进行计算的顶点:\n"
                    , "Calculate", JOptionPane.QUESTION_MESSAGE, null, vertexSet.toArray(), vertexSet.toArray()[0]);
            double tempClosenessCentrality = GraphMetrics.closenessCentrality(graph, chosenVertex);
            JOptionPane.showMessageDialog(this, chosenVertex.getLabel() + " closeness Centrality:" + tempClosenessCentrality);
        });

        getBetweennessCentrality.addActionListener(e -> {
            Set<Vertex> vertexSet = graph.vertices();
            Vertex chosenVertex = (Vertex) JOptionPane.showInputDialog(this, "请选择需要进行计算的顶点:\n"
                    , "Calculate", JOptionPane.QUESTION_MESSAGE, null, vertexSet.toArray(), vertexSet.toArray()[0]);
            double tempBetweennessCentrality = GraphMetrics.betweennessCentrality(graph, chosenVertex);
            JOptionPane.showMessageDialog(this, chosenVertex.getLabel() + " betweenness Centrality:" + tempBetweennessCentrality);
        });

        getEccentricity.addActionListener(e -> {
            Set<Vertex> vertexSet = graph.vertices();
            Vertex chosenVertex = (Vertex) JOptionPane.showInputDialog(this, "请选择需要进行计算的顶点:\n"
                    , "Calculate", JOptionPane.QUESTION_MESSAGE, null, vertexSet.toArray(), vertexSet.toArray()[0]);
            double tempEccentricity = GraphMetrics.eccentricity(graph, chosenVertex);
            JOptionPane.showMessageDialog(this, chosenVertex.getLabel() + " eccentricity:" + tempEccentricity);
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

        modify_vertex.addActionListener(e -> {
            Set<Vertex> vertexSet = this.graph.vertices();
            Object[] vertices = vertexSet.toArray();
            Vertex chosenVertex = (Vertex) JOptionPane.showInputDialog(null
                    , "Choose the vertex you want to modify:\n"
                    , "Modify Vertex", JOptionPane.WARNING_MESSAGE, null, vertices, vertices[0]);
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
                        vertex = VertexFactory.createVertex(vertexLabel
                                , chosenVertex.getClass().getSimpleName(), args);
                        replaceVertex(chosenVertex, vertex);
                        graph.removeVertex(chosenVertex);
                        refreshPanel();
                        LOGGER.debug("Modify vertex from " + chosenVertex.toString() + " to " + vertex.toString());
                        iniRep();
                    } catch (InputFileAgainException e1) {
                        LOGGER.error("Modify vertex with wrong number of params", e1);
                    }
                }
            }
        });

        add_edge.addActionListener(e -> {
            String[] typesOfEdge = {"CommentConnection", "ForwardConnection", "FriendConnection", "MovieActorRelation"
                    , "MovieDirectorRelation", "NetworkConnection", "WordEdge", "SameMovieHyperEdge"};
            String chosenType = (String) JOptionPane.showInputDialog(null
                    , "Choose the type of edge you want add:\n"
                    , "Add Edge", JOptionPane.QUESTION_MESSAGE, null, typesOfEdge, typesOfEdge[0]);
            switch (chosenType) {
                case "CommentConnection":
                case "ForwardConnection":
                case "FriendConnection":
                case "MovieActorRelation":
                case "MovieDirectorRelation":
                case "NetworkConnection":
                case "WordEdge":
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
     * Make the visualization of graph visible
     */
    public void visible() {
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
                        Edge tempEdge = EdgeFactory.createEdge(edge.getLabel(), edge.getClass().getSimpleName()
                                , edge.getWeight(), Arrays.asList(newVertex, newVertex));
                        graph.addEdge(tempEdge);
                    } else if (source.contains(vertex)) {
                        graph.removeEdge(edge);
                        Edge tempEdge = EdgeFactory.createEdge(edge.getLabel(), edge.getClass().getSimpleName()
                                , edge.getWeight(), Arrays.asList(newVertex, target.get(0)));
                        graph.addEdge(tempEdge);
                    } else if (target.contains(vertex)) {
                        graph.removeEdge(edge);
                        Edge tempEdge = EdgeFactory.createEdge(edge.getLabel(), edge.getClass().getSimpleName()
                                , edge.getWeight(), Arrays.asList(source.get(0), newVertex));
                        graph.addEdge(tempEdge);
                    }
                } else if (edge instanceof UndirectedEdge) {
                    Set<Vertex> vertices = edge.vertices();
                    List<Vertex> vertexList = new ArrayList<>(vertices);
                    if (vertexList.size() == 1 && vertices.contains(vertex)) {
                        graph.removeEdge(edge);
                        Edge tempEdge = EdgeFactory.createEdge(edge.getLabel(), edge.getClass().getSimpleName()
                                , edge.getWeight(), Arrays.asList(newVertex));
                        graph.addEdge(tempEdge);
                    } else if (vertices.contains(vertex)) {
                        vertexList.remove(vertex);
                        graph.removeEdge(edge);
                        Edge tempEdge = EdgeFactory.createEdge(edge.getLabel(), edge.getClass().getSimpleName()
                                , edge.getWeight(), Arrays.asList(vertexList.get(0), newVertex));
                        graph.addEdge(tempEdge);
                    }
                } else {
                    Set<Vertex> vertexSet = edge.vertices();
                    if (vertexSet.contains(vertex)) {
                        vertexSet.remove(vertex);
                        vertexSet.add(newVertex);
                        graph.removeEdge(edge);
                        HyperEdge hyperEdge = null;
                        hyperEdge = HyperEdgeFactory.createEdge(edge.getLabel(), edge.getClass().getSimpleName()
                                , new ArrayList<>(vertexSet));
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
        this.g = GraphVisualizationHelper.getJUNG(graph);
        this.getContentPane().removeAll();
        this.scrollPane[0] = new GraphZoomScrollPane(this.g);
        jPanel = new RightPanel(this.g);
        this.getContentPane().add(scrollPane[0], BorderLayout.WEST);
        this.getContentPane().add(jPanel, BorderLayout.EAST);
        this.getContentPane().invalidate();
        this.getContentPane().validate();
        this.getContentPane().repaint();
    }

//    public static void main(String[] args) {
//        GraphGUI tempGraphGUI = new GraphGUI(GraphFactory.createGraph("src/txt/GraphPoet.txt"));
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
        JScrollPane jScrollPane;
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
                    hyperEdgeString.append("\n").append(String.valueOf(++number)).append(".\n").append(edge.vertices());
                }
            }
            hyperEdge.setText(hyperEdgeString.toString());

            jScrollPane = new JScrollPane(hyperEdge);

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
            GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
            this.setLayout(layout);
            hGroup.addGap(5);
            hGroup.addGroup(layout.createParallelGroup().addComponent(label).addComponent(graphName).addComponent(plus)
                    .addComponent(minus)
                    .addComponent(reset)
                    .addComponent(jScrollPane));
            hGroup.addGap(5);
            layout.setHorizontalGroup(hGroup);

            GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
            vGroup.addGap(10);
            vGroup.addGroup(layout.createParallelGroup().addComponent(label));
            vGroup.addGap(10);
            vGroup.addGroup(layout.createParallelGroup().addComponent(graphName));
            vGroup.addGap(10);
            vGroup.addGroup(layout.createParallelGroup().addComponent(plus));
            vGroup.addGap(10);
            vGroup.addGroup(layout.createParallelGroup().addComponent(minus));
            vGroup.addGap(10);
            vGroup.addGroup(layout.createParallelGroup().addComponent(reset));
            vGroup.addGap(10);
            vGroup.addGroup(layout.createParallelGroup().addComponent(jScrollPane));
            vGroup.addGap(10);
            layout.setVerticalGroup(vGroup);
        }
    }
}
