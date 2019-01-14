package graph;

import edge.Edge;
import edge.HyperEdge;
import exception.InputFileAgainException;
import factory.LoggerFactory;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import vertex.Vertex;

/**
 * A concrete graph implements of Graph interface.
 *
 * @param <L> sub class of Class Vertex
 * @param <E> sub class of Class Edge
 * @author Zhu Mingyan
 */
public class ConcreteGraph<L extends Vertex, E extends Edge> implements Graph<L, E> {

  private static final Logger LOGGER = LoggerFactory.createLogger(ConcreteGraph.class);
  /*
  AF: Represents a concrete graph in real world with vertex L and edge E.
  RI: vertexSet that the collections of all vertices in graph should not be null
      edgeSet that the collections of all edges in graph should not be null
      Label(name) of graph should be non-null and non-empty String.
  Safety for Rep Exposure:
      All fields are modified by key word private, clients can not access the field outside class.
      And there is no other setter function except addVertex, addEdge, removeEdge and removeVertex.
      Anywhere needed get the mutable fields is defencive by defencive copy
   */
  //  private final Set<L> vertexSet = new HashSet<>();
  //  private final Set<E> edgeSet = new HashSet<>();
  private final Map<String, L> vertexMap = new HashMap<>();
  private final Map<String, E> edgeMap = new HashMap<>();

  private String name = "";

  ConcreteGraph() {
    super();
  }

  public ConcreteGraph(String name) {
    this.name = name;
  }

  protected void checkRep() {
    assert name != null;
  }

  @Override
  public String getGraphName() {
    return this.name;
  }

  @Override
  public boolean addVertex(L v) {
    //        LOGGER.debug("Attempt to add Vertex " + v.getLabel());
    //    return vertexSet.add(v);
    return vertexMap.put(v.getLabel(), v) == null;
  }

  @Override
  public boolean removeVertex(L v) {
    //    boolean answer = vertexSet.remove(v);
    boolean answer = vertexMap.remove(v.getLabel(), v);
    //    try{
    //      // For test
    //      System.out.println("!!!!!!!!!!!");
    //      Thread.sleep(10000);
    //    } catch (InterruptedException e){
    //      e.printStackTrace();
    //    }
    if (answer) {
      Iterator<E> iterator = edgeMap.values().iterator();
      //      Iterator<E> iterator = edgeSet.iterator();
      while (iterator.hasNext()) {
        E e = iterator.next();
        if (e instanceof HyperEdge && e.vertices().contains(v)) {
          if (e.vertices().size() == 2) {
            iterator.remove();
          } else {
            ((HyperEdge) e).removeVertex(v);
          }
        } else {
          if (e.sourceVertices().contains(v) || e.targetVertices().contains(v)) {
            iterator.remove();
          }
        }
      }
    }
    //        LOGGER.debug("Attempt to delete Vertex " + v.getLabel());
    return answer;
  }

  @Override
  public Set<L> vertices() {
    //    return vertexSet;
    return new HashSet<>(vertexMap.values());
    //    return new HashSet<>(vertexSet);
  }

  @Override
  public Map<L, List<Double>> sources(L target) {
    Map<L, List<Double>> map = new HashMap<>();
    for (E e : edgeMap.values()) {
      if (e.targetVertices().contains(target) && !(e instanceof HyperEdge)) {
        for (Vertex vertex : e.sourceVertices()) {
          // Attention to the DownCast !!
          if (!target.equals(vertex)) {
            if (map.containsKey(vertex)) {
              map.get(vertex).add(e.getWeight());
            } else {
              map.put((L) vertex, new ArrayList<>(Arrays.asList(e.getWeight())));
            }
          }
        }
      }
    }
    return map;
  }

  @Override
  public Map<L, List<Double>> targets(L source) {
    Map<L, List<Double>> map = new HashMap<>();
    for (E e : edgeMap.values()) {
      if (e.sourceVertices().contains(source) && !(e instanceof HyperEdge)) {
        for (Vertex vertex : e.targetVertices()) {
          if (!source.equals(vertex)) {
            if (map.containsKey(vertex)) {
              map.get(vertex).add(e.getWeight());
            } else {
              map.put((L) vertex, new ArrayList<>(Arrays.asList(e.getWeight())));
            }
          }
        }
      }
    }
    return map;
  }

  @Override
  public boolean addEdge(E edge) throws InputFileAgainException {
    //        LOGGER.debug("Attempt to add edge " + edge.getLabel());
    //    try{
    //      System.out.println("!!!!!!!");
    //      Thread.sleep(10000);
    //    } catch (InterruptedException e){
    //      e.printStackTrace();
    //    }
    return edgeMap.put(edge.getLabel(), edge) == null;
    //    return edgeSet.add(edge);
  }

  @Override
  public boolean removeEdge(E edge) throws InputFileAgainException {
    //        LOGGER.debug("Attempt to remove edge " + edge.getLabel());
    return edgeMap.remove(edge.getLabel(), edge);
    //    return edgeSet.remove(edge);
  }

  @Override
  public Set<E> edges() {

    //    return edgeSet;
    //    return new HashSet<>(edgeSet);
    return new HashSet<>(edgeMap.values());
  }

  @Override
  public L getVertex(String label) {
    return vertexMap.get(label);
  }

  @Override
  public E getEdge(String label) {
    return edgeMap.get(label);
  }

  @Override
  public String toString() {
    return "ConcreteGraph{" + "\nname=" + name + "\nvertices=" + vertexMap.values()
        + ", \nedges=" + edgeMap.values() + '}';
  }
}
