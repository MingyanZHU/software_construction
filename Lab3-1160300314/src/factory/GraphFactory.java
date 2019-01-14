package factory;

import edge.DirectedEdge;
import edge.Edge;
import edge.UndirectedEdge;
import exception.*;
import graph.*;
import helper.ParseCommandHelper;
import org.apache.log4j.Logger;
import vertex.Vertex;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

/**
 * A factory that created instances of graph from file
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

    /**
     * Create an instance from params file
     *
     * @param filePath params file path that must existed and path is non-empty and non-null string.
     * @return an instance of the determined file.
     * TODO @throws
     */
    public static Graph createGraph(String filePath) throws InputFileAgainException, IOException {
        Graph graph = null;
        InputStreamReader reader = null;
        BufferedReader bufferedReader = null;
        vertexType = new HashSet<>();
        edgeType = new HashSet<>();
        labelSet = new HashSet<>();
        vertexExtraLabel = 1;
        edgeExtraLabel = 1;
        try {
//            reader = new FileReader(filePath);
            reader =  new InputStreamReader(new FileInputStream(filePath), "utf-8");
            bufferedReader = new BufferedReader(reader);

//            List<String> vertexType = new ArrayList<>();
//            List<String> edgeType = new ArrayList<>();
            String input;
            while ((input = bufferedReader.readLine()) != null) {
                List<String> list = ParseCommandHelper.fileCommandHelper(input);
                if (list.size() == 0)
                    continue;
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
                            graph.addVertex(VertexFactory.createVertex(list.get(1), list.get(2)
                                    , list.subList(3, list.size()).toArray(new String[0])));
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
                        if (!edgeType.contains(list.get(2))) {
                            throw new IllegalEdgeTypeException(list.get(2) + " is an illegal edge type!");
                        }
                        // 在ParseCommandHelper可以保证edge的输入定有7个分量
//                        assert list.size() == 7 && edgeType.contains(list.get(2));
                        // 可以增加寻找标志进行优化
                        Vertex vertex1 = null;
                        Vertex vertex2 = null;
                        Set<Vertex> vertexSet = graph.vertices();
                        for (Vertex vertex : vertexSet) {
                            if (vertex.getLabel().equals(list.get(4))) {
                                vertex1 = vertex.clone();
                            }
                            if (vertex.getLabel().equals(list.get(5))) {
                                vertex2 = vertex.clone();
                            }
                        }
                        if (vertex1 == null && vertex2 == null) {
                            throw new UndefinedVertexException(list.get(4) + " and " + list.get(5) + " are undefined");
                        } else if (vertex1 == null) {
                            throw new UndefinedVertexException(list.get(4) + " is undefined!");
                        } else if (vertex2 == null) {
                            throw new UndefinedVertexException(list.get(5) + " is undefined!");
                        }
                        list.set(1, modifyDuplicateLabels(list.get(1)));
                        Edge newEdge = EdgeFactory.createEdge(list.get(1), list.get(2), Double.valueOf(list.get(3))
                                , Arrays.asList(vertex1, vertex2));
                        graph.addEdge(newEdge);
                        try {
                            if (newEdge instanceof DirectedEdge && list.get(6).length() == 2) {
                                throw new UndirectedEdgeWithDirectionException(list.get(2) + " use as UndirectedEdge");
                            }
                            if (newEdge instanceof UndirectedEdge && list.get(6).length() == 3) {
                                throw new UndirectedEdgeWithDirectionException(list.get(2) + " use as DirectedEdge");
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
                                    vertexList.add(vertex.clone());
                                    break;
                                }
                            }
                        }
                        if (vertexList.size() + 3 != list.size()) {
                            throw new UndefinedVertexException("Some vertices are not defined!" + list.subList(3
                                    , list.size()).toString());
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
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (reader != null) {
                reader.close();
            }
        }
        return graph;
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
        Set<String> validNetworkTopologyVertexType = new HashSet<>(Arrays.asList("Computer", "Server", "Router", "WirelessRouter"));
        Set<String> validMovieGraphVertexType = new HashSet<>(Arrays.asList("Movie", "Actor", "Director"));
        if (graphType.equals("GraphPoet")) {
            if (validGraphPoetVertexType.containsAll(vertexType)) {
                return vertexType;
            } else {
                throw new IllegalVertexTypeException(vertexType.toString() + " are not defined in GraphPoet!");
            }
        } else if (graphType.equals("SocialNetwork")) {
            if (validSocialNetworkVertexType.containsAll(vertexType)) {
                return vertexType;
            } else {
                throw new IllegalVertexTypeException(vertexType.toString() + " are not defined in SocialNetwork!");
            }
        } else if (graphType.equals("NetworkTopology")) {
            if (validNetworkTopologyVertexType.containsAll(vertexType)) {
                return vertexType;
            } else {
                throw new IllegalVertexTypeException(vertexType.toString() + " are not defined in NetworkTopology!");
            }
        } else if (graphType.equals("MovieGraph")) {
            if (validMovieGraphVertexType.containsAll(vertexType)) {
                return vertexType;
            } else {
                throw new IllegalVertexTypeException(vertexType.toString() + " are not defined in MovieGraph!");
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
            label += String.valueOf(edgeExtraLabel++);
        } finally {
            labelSet.add(label);
        }
        return label;
    }

    private static Set legalEdgeType() throws InputFileAgainException {
        Set<String> validGraphPoetEdgeType = new HashSet<>(Arrays.asList("WordEdge"));
        Set<String> validSocialNetworkEdgeType = new HashSet<>(Arrays.asList("ForwardConnection", "FriendConnection"
                , "CommentConnection"));
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
                throw new IllegalEdgeTypeException(edgeType.toString() + " are not defined in SocialNetwork");
            }
        } else if (graphType.equals("NetworkTopology")) {
            if (validNetworkTopologyEdgeType.containsAll(edgeType)) {
                return edgeType;
            } else {
                throw new IllegalEdgeTypeException(edgeType.toString() + " are not defined in NetworkTopology");
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
