package factory;

import edge.DirectedEdge;
import edge.Edge;
import edge.UndirectedEdge;
import exception.ContinueRunningException;
import exception.DuplicateLabelsException;
import exception.IllegalEdgeTypeException;
import exception.IllegalGrammarTextException;
import exception.IllegalVertexTypeException;
import exception.InputFileAgainException;
import exception.LackVerticesHyperEdgeException;
import exception.UndefinedVertexException;
import exception.UndirectedEdgeWithDirectionException;
import graph.Graph;
import graph.GraphPoet;
import graph.MovieGraph;
import graph.NetworkTopology;
import graph.SocialNetwork;
import helper.ParseCommandHelper;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.log4j.Logger;
import strategy.BufferInputStrategy;
import strategy.InputStrategy;
import vertex.Vertex;

/**
 * A factory that created instances of graph from file.
 *
 * @author Zhu Mingyan
 */
public class GraphFactory {

  private static final Logger LOGGER = LoggerFactory.createLogger(GraphFactory.class);
  private static String graphType = "";
  private static Set<String> vertexType = new HashSet<>();
  private static Set<String> edgeType = new HashSet<>();
  private static Set<String> labelSet = new HashSet<>();

  private static int vertexExtraLabel = 1;
  private static int edgeExtraLabel = 1;
  private static InputStrategy in = null;

  /**
   * Adaptor of original createGraph which you choose you input strategy.
   *
   * @param inputStrategy choosing as input strategy of file path.
   * @param filePath file path of the input graph.
   * @return graph consists of inputting file.
   * @throws InputFileAgainException if there are some errors in file.
   * @throws IOException if there is no such file on the disk.
   */
  // 原工厂函数的Adaptor 可以选择输入的策略
  public static Graph createGraphStrategy(InputStrategy inputStrategy, String filePath)
      throws InputFileAgainException, IOException {
    in = inputStrategy;
    if (!in.getFilePath().equals(filePath)) {
      throw new RuntimeException("Using different file path!");
    }
    return createGraph(filePath);
  }

  /**
   * Create an instance from params file.
   *
   * @param filePath params file path that must existed and path is non-empty and non-null string.
   * @return an instance of the determined file.
   * @throws InputFileAgainException when the input file is illegal.
   */
  public static Graph createGraph(String filePath) throws InputFileAgainException, IOException {
    Graph graph = null;
    vertexType = new HashSet<>();
    edgeType = new HashSet<>();
    labelSet = new HashSet<>();
    vertexExtraLabel = 1;
    edgeExtraLabel = 1;
    try {
      String input;
      if (in == null || !filePath.equals(in.getFilePath())) {
        in = new BufferInputStrategy(filePath);
      }
      while ((input = in.readLine()) != null) {
        List<String> list = ParseCommandHelper.fileCommandHelper(input);
        if (list.size() == 0) {
          continue;
        }
        switch (list.get(0)) {
          case "GraphType":
            assert list.size() == 2;
            graphType = list.get(1);
            break;
          case "GraphName":
            assert list.size() == 2;
            graph = graphIni(graphType, list.get(1));
            break;
          case "VertexType":
            assert list.size() >= 2;
            for (int i = 1; i < list.size(); i++) {
              vertexType.add(list.get(i));
            }
            vertexType = legalVertexType();
            break;
          case "Vertex":
            // 在ParseCommandHelper中可以保证Vertex命令中包含至少3个元素
            //                        assert list.size() >= 3 && vertexType.contains(list.get(2));
            if (!vertexType.contains(list.get(2))) {
              // 如果在该类型图中加入了没有事先声明类型的顶点 会throw IllegalVertexTypeException
              throw new IllegalVertexTypeException(list.get(2) + " is an illegal vertex type!");
            }
            list.set(1, modifyDuplicateLabels(list.get(1)));
            if (list.size() == 3) {
              graph.addVertex(VertexFactory.createVertex(list.get(1), list.get(2), null));
            } else {
              graph.addVertex(VertexFactory.createVertex(list.get(1), list.get(2),
                  list.subList(3, list.size()).toArray(new String[0])));
            }

            break;
          case "EdgeType":
            // 在ParseCommandHelper可以保证EdgeType至少有两个分量
            //                        assert list.size() >= 2;
            for (int i = 1; i < list.size(); i++) {
              edgeType.add(list.get(i));
            }
            edgeType = legalEdgeType();
            break;
          case "Edge":
            try {
              if (!edgeType.contains(list.get(2))) {
                throw new IllegalEdgeTypeException(list.get(2) + " is an illegal edge type!");
              }
              // 在ParseCommandHelper可以保证edge的输入定有7个分量
              //                        assert list.size() == 7 && edgeType.contains(list.get(2));
              // 可以增加寻找标志进行优化
              Vertex vertex1 = (Vertex) graph.getVertex(list.get(4));
              Vertex vertex2 = (Vertex) graph.getVertex(list.get(5));
              //              Set<Vertex> vertexSet = graph.vertices();
              //              for (Vertex vertex : vertexSet) {
              //                if (vertex.getLabel().equals(list.get(4))) {
              //                  vertex1 = vertex;
              //                  //                  vertex1 = vertex.clone();
              //                }
              //                if (vertex.getLabel().equals(list.get(5))) {
              //                  vertex2 = vertex;
              //                  //                  vertex2 = vertex.clone();
              //                }
              //                if (vertex1 != null && vertex2 != null) {
              //                  break;
              //                }
              //              }
              if (vertex1 == null && vertex2 == null) {
                throw new UndefinedVertexException(
                    list.get(4) + " and " + list.get(5) + " are undefined");
              } else if (vertex1 == null) {
                throw new UndefinedVertexException(list.get(4) + " is undefined!");
              } else if (vertex2 == null) {
                throw new UndefinedVertexException(list.get(5) + " is undefined!");
              }
              //for (Object edge : graph.edges()) {
              //if (!(edge instanceof Edge)) {
              //continue;
              //}
              //Edge edge1 = (Edge) edge;
              //if (edge instanceof UndirectedEdge) {
              //if (list.get(2).equals(edge.getClass().getSimpleName())
              //&& edge1.containVertex(vertex1) && edge1.containVertex(vertex2)) {
              //throw new ContinueRunningException(list.get(1) + " has been multi defined!");
              //}
              //}
              //if (edge instanceof DirectedEdge) {
              //if (list.get(2).equals(edge.getClass().getSimpleName())
              //&& edge1.sourceVertices().contains(vertex1)
              //&& edge1.targetVertices().contains(vertex2)) {
              //throw new ContinueRunningException(list.get(1) + " has been multi defined!");
              //}
              //}
              //}
              list.set(1, modifyDuplicateLabels(list.get(1)));
              Edge newEdge = EdgeFactory
                  .createEdge(list.get(1), list.get(2), Double.valueOf(list.get(3)),
                      Arrays.asList(vertex1, vertex2));
              graph.addEdge(newEdge);
              if (newEdge instanceof DirectedEdge && list.get(6).length() == 2) {
                throw new UndirectedEdgeWithDirectionException(
                    list.get(2) + " use as UndirectedEdge");
              }
              if (newEdge instanceof UndirectedEdge && list.get(6).length() == 3) {
                throw new UndirectedEdgeWithDirectionException(
                    list.get(2) + " use as DirectedEdge");
              }
            } catch (ContinueRunningException e) {
              LOGGER.error(e.getMessage(), e);
            }
            break;
          case "HyperEdge":
            //                        assert list.size() >= 5;
            Set<Vertex> vertexSet1 = graph.vertices();
            List<Vertex> vertexList = new ArrayList<>();
            for (int i = 3; i < list.size(); i++) {
              for (Vertex vertex : vertexSet1) {
                if (vertex.getLabel().equals(list.get(i))) {
                  vertexList.add(vertex);
                  break;
                }
              }
            }
            if (vertexList.size() + 3 != list.size()) {
              throw new UndefinedVertexException("Some vertices are not defined!" + list.subList(3,
                  list.size()).toString());
            }
            if (vertexList.size() < 2) {
              throw new LackVerticesHyperEdgeException("HyperEdge only contains one vertex!");
            }
            //                        assert vertexList.size() + 3 == list.size();
            graph.addEdge(HyperEdgeFactory.createEdge(list.get(1), list.get(2), vertexList));
            break;
          default:
            assert false;
        }
      }
    } catch (IllegalGrammarTextException e) {
      throw new InputFileAgainException(
          String.valueOf(in.getLineNumber() + "  :" + e.getMessage()));
    } finally {
      if (in != null) {
        in.close();
      }
    }
    return graph;
    //    catch (CloneNotSupportedException e) {
    //      e.printStackTrace();
    //    }
  }

  private static Graph graphIni(String graphType, String graphName) {
    /**
     * Create an instance of determined graph type and graph name.
     */
    Graph graph;
    if (graphType.equals("GraphPoet")) {
      graph = new GraphPoet(graphName);
    } else if (graphType.equals("SocialNetwork")) {
      graph = new SocialNetwork(graphName);
    } else if (graphType.equals("NetworkTopology")) {
      graph = new NetworkTopology(graphName);
    } else if (graphType.equals("MovieGraph")) {
      graph = new MovieGraph(graphName);
    } else {
      graph = null;
    }
    return graph;
  }

  private static Set legalVertexType() throws InputFileAgainException {
    Set<String> validGraphPoetVertexType = new HashSet<>(Arrays.asList("Word"));
    Set<String> validSocialNetworkVertexType = new HashSet<>(Arrays.asList("Person"));
    Set<String> validNetworkTopologyVertexType = new HashSet<>(
        Arrays.asList("Computer", "Server", "Router", "WirelessRouter"));
    Set<String> validMovieGraphVertexType = new HashSet<>(
        Arrays.asList("Movie", "Actor", "Director"));
    if (graphType.equals("GraphPoet")) {
      if (validGraphPoetVertexType.containsAll(vertexType)) {
        return vertexType;
      } else {
        throw new IllegalVertexTypeException(
            vertexType.toString() + " are not defined in GraphPoet!");
      }
    } else if (graphType.equals("SocialNetwork")) {
      if (validSocialNetworkVertexType.containsAll(vertexType)) {
        return vertexType;
      } else {
        throw new IllegalVertexTypeException(
            vertexType.toString() + " are not defined in SocialNetwork!");
      }
    } else if (graphType.equals("NetworkTopology")) {
      if (validNetworkTopologyVertexType.containsAll(vertexType)) {
        return vertexType;
      } else {
        throw new IllegalVertexTypeException(
            vertexType.toString() + " are not defined in NetworkTopology!");
      }
    } else if (graphType.equals("MovieGraph")) {
      if (validMovieGraphVertexType.containsAll(vertexType)) {
        return vertexType;
      } else {
        throw new IllegalVertexTypeException(
            vertexType.toString() + " are not defined in MovieGraph!");
      }
    } else {
      throw new InputFileAgainException("Undefined Graph type!");
    }
  }

  private static String modifyDuplicateLabels(String label) {
    try {
      if (labelSet.contains(label)) {
        throw new DuplicateLabelsException(label + " has been used!");
      }
    } catch (ContinueRunningException e) {
      LOGGER.error(e.getMessage(), e);
      label += ("00" + String.valueOf(edgeExtraLabel++));
      LOGGER.info("We change it to " + label);
    } finally {
      labelSet.add(label);
    }
    return label;
  }

  private static Set legalEdgeType() throws InputFileAgainException {
    Set<String> validGraphPoetEdgeType = new HashSet<>(Arrays.asList("WordNeighborhood"));
    Set<String> validSocialNetworkEdgeType = new HashSet<>(Arrays.asList("ForwardTie", "FriendTie",
        "CommentTie"));
    Set<String> validNetworkTopologyEdgeType = new HashSet<>(Arrays.asList("NetworkConnection"));
    Set<String> validMovieGraphEdgeType = new HashSet<>(Arrays.asList("MovieActorRelation",
        "MovieDirectorRelation", "SameMovieHyperEdge"));
    if (graphType.equals("GraphPoet")) {
      if (validGraphPoetEdgeType.containsAll(edgeType)) {
        return edgeType;
      } else {
        throw new IllegalEdgeTypeException(edgeType.toString() + " are not defined in GraphPoet");
      }
    } else if (graphType.equals("SocialNetwork")) {
      if (validSocialNetworkEdgeType.containsAll(edgeType)) {
        return edgeType;
      } else {
        throw new IllegalEdgeTypeException(
            edgeType.toString() + " are not defined in SocialNetwork");
      }
    } else if (graphType.equals("NetworkTopology")) {
      if (validNetworkTopologyEdgeType.containsAll(edgeType)) {
        return edgeType;
      } else {
        throw new IllegalEdgeTypeException(
            edgeType.toString() + " are not defined in NetworkTopology");
      }
    } else if (graphType.equals("MovieGraph")) {
      // TODO 没有有效地处理超边
      if (validMovieGraphEdgeType.containsAll(edgeType)) {
        return edgeType;
      } else {
        throw new IllegalEdgeTypeException(edgeType.toString() + " are not defined in MovieGraph");
      }
    } else {
      throw new InputFileAgainException("Undefined Graph type!");
    }
  }
}
