package helper;

import edge.DirectedEdge;
import edge.Edge;
import edge.UndirectedEdge;
import edu.uci.ics.jung.algorithms.scoring.BetweennessCentrality;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import graph.Graph;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import vertex.Vertex;

/**
 * Used to calculate degree centrality, closeness centrality, betweenness centrality radius,
 * diameter, eccentricity and distance between any two vertices.
 *
 * @param <L> sub class of Vertex
 * @param <E> sub class of Edge
 * @author Zhu Mingyan
 */
public class GraphMetrics<L extends Vertex, E extends Edge> {

  private static final int INF = 0x3f3f3f3f;

  /**
   * Calculate degree centrality of vertex in graph.
   *
   * @param g graph contains the vertex and is non-null
   * @param v vertex which is used to calculate and is non-null
   * @param <L> vertex class of g which is one of all vertex types we have been defined
   * @param <E> edge class of g which is one of all edge types we have been defined
   * @return a decimal indicates v degree centrality
   */
  public static <L extends Vertex, E extends Edge> double degreeCentrality(Graph<L, E> g, L v) {
    long degree = 0;
    Set<E> edges = g.edges();
    for (E edge : edges) {
      if (edge.sourceVertices().contains(v) || edge.targetVertices().contains(v)) {
        degree++;
      }
    }
    return degree;  // As wiki defined
    //        return (double)degree / (g.vertices().size() - 1);
  }

  /**
   * Calculate the degree centrality of graph.
   *
   * @param g graph instance that needed to calculate degree centrality and is non-null.
   * @param <L> vertex class of graph which should be one of all vertex types we have been defined
   * @param <E> edge class of graph which should be one of all edge types we have been defined
   * @return a decimal indicates graph degree centrality
   */
  public static <L extends Vertex, E extends Edge> double degreeCentrality(Graph<L, E> g) {
    Set<L> vertexSet = g.vertices();
    int n = vertexSet.size();
    long h = n * n - 3 * n + 2;
    double maxCd = -1;
    List<Double> cds = new ArrayList<>();
    for (L vertex : vertexSet) {
      double tempCd = degreeCentrality(g, vertex);
      if (maxCd < tempCd) {
        maxCd = tempCd;
      }
      cds.add(tempCd);
    }
    double sum = 0;
    for (double cd : cds) {
      sum += (maxCd - cd);
    }
    return sum / h;
  }

  /**
   * Calculate the closeness centrality of vertex in graph.
   *
   * @param g graph that contains vertex and is non-null
   * @param v vertex that needed to calculate closeness centrality and non-null
   * @param <L> vertex class of graph which should be one of all vertex types we have been defined
   * @param <E> edge class of graph which should be one of all edge types we have been defined
   * @return a decimal indicates vertex closeness centrality and if there is some vertex is not
   *      connected with the vertex will return 0.
   */
  public static <L, E> double closenessCentrality(Graph<L, E> g, L v) {
    double sum = 0;
    Map<L, Double> shortestDistance = dijkstra(g, v);
    for (Map.Entry<L, Double> doubleEntry : shortestDistance.entrySet()) {
      sum += doubleEntry.getValue();
    }
    return sum >= INF ? 0 : 1 / sum;
  }

  /**
   * Calculate the betweenness centrality of vertex in graph.
   *
   * @param g graph that contains vertex and is non-null
   * @param v vertex that needed to calculate betweenness centrality and non-null
   * @param <L> vertex class of graph which should be one of all vertex types we have been defined
   * @param <E> edge class of graph which should be one of all edge types we have been defined
   * @return a decimal indicates vertex betweenness centrality
   */
  public static <L extends Vertex, E extends Edge> double betweennessCentrality(Graph<L, E> g,
      L v) {
    SparseMultigraph<L, E> graph = cloneJungGraph(g);
    BetweennessCentrality ranker = new BetweennessCentrality(graph);
    return ranker.getVertexScore(v);
  }

  /**
   * Calculate in degree centrality of vertex in directed graph.
   *
   * @param g directed graph contains the vertex and is non-null
   * @param v vertex which is used to calculate and is non-null
   * @param <L> vertex class of g which is one of all vertex types we have been defined
   * @param <E> edge class of g which is one of all edge types we have been defined
   * @return a decimal indicates v in degree centrality
   */
  public static <L, E> double inDegreeCentrality(Graph<L, E> g, L v) {
    long inDegree = 0;
    Set<E> edges = g.edges();
    for (E edge : edges) {
      if (edge instanceof DirectedEdge && ((DirectedEdge) edge).targetVertices().contains(v)) {
        inDegree++;
      }
    }
    return inDegree;
  }

  /**
   * Calculate out degree centrality of vertex in directed graph.
   *
   * @param g directed graph contains the vertex and is non-null
   * @param v vertex which is used to calculate and is non-null
   * @param <L> vertex class of g which is one of all vertex types we have been defined
   * @param <E> edge class of g which is one of all edge types we have been defined
   * @return a decimal indicates v out degree centrality
   */
  public static <L, E> double outDegreeCentrality(Graph<L, E> g, L v) {
    long outDegree = 0;
    Set<E> edges = g.edges();
    for (E edge : edges) {
      if (edge instanceof DirectedEdge && ((DirectedEdge) edge).sourceVertices().contains(v)) {
        outDegree++;
      }
    }
    return outDegree;
  }

  /**
   * Calculate the shortest distance from start to end.
   *
   * @param g graph contains start vertex and end vertex and is non-null.
   * @param start source vertex of the shortest path and is non-null
   * @param end destination vertex of the shortest path and is non-null
   * @param <L> vertex class of graph which should be one of all vertex type we have been defined
   * @param <E> edge class of graph which should be one of all edge type we have been defined
   * @return shortest distance if there is a path from start to end, otherwise return -1.
   */
  public static <L, E> double distance(Graph<L, E> g, L start, L end) {
    Map<L, Double> shortestDistance = dijkstra(g, start);
    return shortestDistance.get(end) >= INF ? -1 : shortestDistance.get(end);
  }

  /**
   * Calculate eccentricity vertex in graph.
   *
   * @param g graph contains the vertex and is non-null
   * @param v vertex which is used to calculate and is non-null
   * @param <L> vertex class of g which is one of all vertex types we have been defined
   * @param <E> edge class of g which is one of all edge types we have been defined
   * @return a decimal indicates v eccentricity
   */
  public static <L, E> double eccentricity(Graph<L, E> g, L v) {
    Map<L, Double> shortestDistance = dijkstra(g, v);
    double ans = -1;
    for (Double x : shortestDistance.values()) {
      if (ans < x) {
        ans = x;
      }
    }
    return ans;
  }

  /**
   * Calculate the radius of graph.
   *
   * @param g graph instance that needed to calculate degree centrality and is non-null.
   * @param <L> vertex class of graph which should be one of all vertex types we have been defined
   * @param <E> edge class of graph which should be one of all edge types we have been defined
   * @return a decimal indicates graph radius
   */
  public static <L, E> double radius(Graph<L, E> g) {
    Set<L> vertexSet = g.vertices();
    double ans = INF;
    for (L v : vertexSet) {
      double tempEccentricity = eccentricity(g, v);
      if (ans > tempEccentricity) {
        ans = tempEccentricity;
      }
    }
    return ans;
  }

  /**
   * Calculate the diameter of graph.
   *
   * @param g graph instance that needed to calculate degree centrality and is non-null.
   * @param <L> vertex class of graph which should be one of all vertex types we have been defined
   * @param <E> edge class of graph which should be one of all edge types we have been defined
   * @return a decimal indicates graph diameter and if the graph is not a connected graph return -1.
   */
  public static <L, E> double diameter(Graph<L, E> g) {
    Set<L> vertexSet = g.vertices();
    double ans = -1;
    for (L v : vertexSet) {
      double tempEccentricity = eccentricity(g, v);
      if (ans < tempEccentricity) {
        ans = tempEccentricity;
      }
    }
    return ans >= INF ? -1 : ans;
  }

  private static <L, E> Map<L, Double> dijkstra(Graph<L, E> g, L start) {
    Map<L, Boolean> visited = new HashMap<>();
    Map<L, Double> shortestDistance = new HashMap<>();
    Set<L> vertexSet = g.vertices();
    for (L vertex : vertexSet) {
      visited.put(vertex, false);
      if (g.targets(start).containsKey(vertex)) {
        shortestDistance.put(vertex, Collections.min(g.targets(start).get(vertex)));
      } else {
        shortestDistance.put(vertex, (double) INF);
      }
    }
    visited.put(start, true);
    shortestDistance.put(start, 0.0);
    for (L vertex : vertexSet) {
      double tempDistance = INF;
      L tempVertex = null;
      for (L possibleVertex : vertexSet) {
        if (!possibleVertex.equals(start) && !visited.get(possibleVertex)
            && shortestDistance.get(possibleVertex) < tempDistance) {
          tempDistance = shortestDistance.get(possibleVertex);
          tempVertex = possibleVertex;
        }
      }
      visited.put(tempVertex, true);
      //此处没有考虑多重边可能不符合Dijkstra的情况 在两个顶点之间存在多条边时 取最小权值
      Map<L, Double> mapNow = new HashMap<>();
      Iterator iterator = g.targets(tempVertex).entrySet().iterator();
      while (iterator.hasNext()) {
        Map.Entry<L, List<Double>> entry = (Map.Entry<L, List<Double>>) iterator.next();
        mapNow.put(entry.getKey(), Collections.min(entry.getValue()));
      }
      for (L possibleVertex : vertexSet) {
        if (!possibleVertex.equals(start) && !visited.get(possibleVertex) && mapNow
            .containsKey(possibleVertex)) {
          double sum = mapNow.get(possibleVertex) + shortestDistance.get(tempVertex);
          if (sum < shortestDistance.get(possibleVertex)) {
            shortestDistance.put(possibleVertex, sum);
          }
        }
      }
    }
    return shortestDistance;
  }

  /**
   * Get an instance of SparseMultigraph from a graph.Graph instance which is used in JUNG.
   *
   * @param g graph instance that needed to calculate degree centrality and is non-null.
   * @param <L> vertex class of graph which should be one of all vertex types we have been defined
   * @param <E> edge class of graph which should be one of all edge types we have been defined
   * @return an instance of SparseMultigraph
   */
  public static <L extends Vertex, E extends Edge> SparseMultigraph cloneJungGraph(Graph<L, E> g) {
    SparseMultigraph<L, E> graph = new SparseMultigraph<>();
    Set<L> vertexSet = g.vertices();
    for (L vertex : vertexSet) {
      graph.addVertex(vertex);
    }
    Set<E> edges = g.edges();
    for (E edge : edges) {
      if (edge instanceof DirectedEdge) {
        Set<Vertex> source = edge.sourceVertices();
        Set<Vertex> target = edge.targetVertices();
        List<Vertex> sourceList = new ArrayList<>(source);
        List<Vertex> targetList = new ArrayList<>(target);
        graph.addEdge(edge, (L) sourceList.get(0), (L) targetList.get(0), EdgeType.DIRECTED);
      } else if (edge instanceof UndirectedEdge) {
        Set<Vertex> vertices = edge.vertices();
        List<Vertex> vertexList = new ArrayList<>(vertices);
        if (vertexList.size() == 1) {
          graph.addEdge(edge, (L) vertexList.get(0), (L) vertexList.get(0));
        } else {
          graph.addEdge(edge, (L) vertexList.get(0), (L) vertexList.get(1));
        }
      }
    }
    return graph;
  }
}
