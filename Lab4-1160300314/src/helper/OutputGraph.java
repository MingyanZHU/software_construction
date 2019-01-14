package helper;

import edge.DirectedEdge;
import edge.Edge;
import edge.HyperEdge;
import edge.UndirectedEdge;
import exception.InputFileAgainException;
import factory.GraphFactory;
import graph.Graph;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import strategy.BufferOutputStrategy;
import strategy.OutputStrategy;
import strategy.StreamOutputStrategy;
import strategy.WriterOutputStrategy;
import vertex.CastMember;
import vertex.Movie;
import vertex.NetworkEquipment;
import vertex.Person;
import vertex.Vertex;
import vertex.Word;

public class OutputGraph {

  private static final String graphName = "GraphName";
  private static final String graphType = "GraphType";
  private static final String vertexType = "VertexType";
  private static final String vertexInfo = "Vertex";
  private static final String edgeType = "EdgeType";
  private static final String edgeInfo = "Edge";
  private static final String hyperEdgeInfo = "HyperEdge";
  private static OutputStrategy out;

  /**
   * Output the graph with the Lab3 demanding.
   *
   * @param graph wanna to output
   */
  public static void outputGraph(Graph graph, String fileName) throws IOException {
    out = new WriterOutputStrategy(fileName);
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(graphType + " = \"").append(graph.getClass().getSimpleName())
        .append("\"\n");
    stringBuilder.append(graphName + " = \"").append(graph.getGraphName()).append("\"\n");
    stringBuilder.append("\n");
    Set<Vertex> vertices = graph.vertices();
    Set<String> vertexTypeSet = new HashSet<>();
    for (Vertex vertex : vertices) {
      vertexTypeSet.add(vertex.getClass().getSimpleName());
    }
    stringBuilder.append(vertexType + " = ");
    stringBuilder.append(getType(new ArrayList<>(vertexTypeSet)));

    stringBuilder.append(getVertex(vertices));
    stringBuilder.append("\n");

    Set<Edge> edges = graph.edges();
    Set<String> edgeTypeSet = new HashSet<>();
    Set<HyperEdge> hyperEdges = new HashSet<>();

    for (Edge edge : edges) {
      edgeTypeSet.add(edge.getClass().getSimpleName());
      if (edge instanceof HyperEdge) {
        hyperEdges.add((HyperEdge) edge);
      }
    }

    stringBuilder.append(edgeType + " = ");
    stringBuilder.append(getType(new ArrayList<>(edgeTypeSet)));

    stringBuilder.append(getEdge(edges));
    if (!hyperEdges.isEmpty()) {
      stringBuilder.append(getHyperEdge(hyperEdges));
    }
    out.write(stringBuilder.toString());
    out.close();
  }

  private static String getType(List<String> types) {
    StringBuilder stringBuilder = new StringBuilder();
    for (int i = 0; i < types.size(); i++) {
      stringBuilder.append("\"").append(types.get(i)).append("\"");
      if (i != types.size() - 1) {
        stringBuilder.append(", ");
      }
    }
    stringBuilder.append("\n");
    return stringBuilder.toString();
  }

  private static String getHyperEdge(Set<HyperEdge> hyperEdges) {
    StringBuilder stringBuilder = new StringBuilder();
    for (HyperEdge edge : hyperEdges) {
      stringBuilder.append(hyperEdgeInfo + " = <\"").append(edge.getLabel()).append("\", \"")
          .append(edge.getClass().getSimpleName()).append("\"");
      List<Vertex> vertexList = new ArrayList<>(edge.vertices());
      stringBuilder.append(", {");
      for (int i = 0; i < vertexList.size(); i++) {
        stringBuilder.append("\"").append(vertexList.get(i).getLabel()).append("\"");
        if (i != vertexList.size() - 1) {
          stringBuilder.append(", ");
        }
      }
      stringBuilder.append("}>");
    }
    return stringBuilder.toString();
  }

  private static String getEdge(Set<Edge> edges) {
    StringBuilder stringBuilder = new StringBuilder();
    for (Edge edge : edges) {
      if (!(edge instanceof HyperEdge)) {
        if (edge instanceof DirectedEdge) {
          stringBuilder.append(edgeInfo + " = <\"").append(edge.getLabel())
              .append("\", \"").append(edge.getClass().getSimpleName())
              .append("\", \"").append(edge.getWeight())
              .append("\", \"").append(new ArrayList<>(edge.sourceVertices()).get(0).getLabel())
              .append("\", \"").append(new ArrayList<>(edge.targetVertices()).get(0).getLabel())
              .append("\", \"Yes\">\n");
        }
        if (edge instanceof UndirectedEdge) {
          List<Vertex> vertexList = new ArrayList<>(edge.vertices());
          stringBuilder.append(edgeInfo + " = <\"").append(edge.getLabel())
              .append("\", \"").append(edge.getClass().getSimpleName())
              .append("\", \"").append(edge.getWeight())
              .append("\", \"").append(vertexList.get(0).getLabel())
              .append("\", \"").append(vertexList.get(1).getLabel())
              .append("\", \"No\">\n");
        }
      }
    }
    return stringBuilder.toString();
  }

  private static String getVertex(Set<Vertex> vertices) {
    StringBuilder stringBuilder = new StringBuilder();
    for (Vertex vertex : vertices) {
      stringBuilder.append(vertexInfo + " = <\"").append(vertex.getLabel()).append("\", \"")
          .append(vertex.getClass().getSimpleName()).append("\"");
      stringBuilder.append(getVertexFields(vertex));
      stringBuilder.append(">\n");
    }
    return stringBuilder.toString();
  }

  private static String getVertexFields(Vertex vertex) {
    StringBuilder stringBuilder = new StringBuilder();
    if (vertex instanceof Word) {
      return stringBuilder.toString();
    } else if (vertex instanceof Person) {
      stringBuilder.append(", <\"");
      stringBuilder.append(((Person) vertex).getGender()).append("\", \"");
      stringBuilder.append(((Person) vertex).getAge()).append("\">");
    } else if (vertex instanceof NetworkEquipment) {
      stringBuilder.append(", <\"");
      stringBuilder.append(((NetworkEquipment) vertex).getIp()).append("\">");
    } else if (vertex instanceof Movie) {
      stringBuilder.append(", <\"");
      stringBuilder.append(((Movie) vertex).getReleaseYear()).append("\", \"");
      stringBuilder.append(((Movie) vertex).getNation()).append("\", \"");
      stringBuilder.append(((Movie) vertex).getScore()).append("\">");
    } else if (vertex instanceof CastMember) {
      stringBuilder.append(", <\"");
      stringBuilder.append(((CastMember) vertex).getAge()).append("\", \"");
      stringBuilder.append(((CastMember) vertex).getGender()).append("\">");
    }
    return stringBuilder.toString();
  }

  //  public static void main(String[] args) {
  //    try {
  //      outputGraph(GraphFactory
  //          .createGraph("src/txt/inputMovieGraph.txt"), "src/txt/null2.txt");
  //    } catch (InputFileAgainException | IOException e) {
  //      e.printStackTrace();
  //    }
  //  }
}
