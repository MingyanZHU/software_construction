package graph;

import edge.Edge;
import edge.NetworkConnection;
import exception.InputFileAgainException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import vertex.NetworkEquipment;
import vertex.Vertex;

/**
 * Describe a network graph which is a multi graph, simple graph and weighted graph.
 *
 * @author Zhu Mingyan
 */
public class NetworkTopology extends ConcreteGraph<NetworkEquipment, NetworkConnection> {

  /*
  AF: Represents a network with NetworkEquipment as vertex and NetworkConnection as Edge
  RI: vertexSet that the collections of all vertices in graph should not be null
      edgeSet that the collections of all edges in graph should not be null
      Label(name) of graph should be non-null and non-empty String.
      All edges are not loops.
  Safety for Rep Exposure:
      All fields are modified by key word private, clients can not access the field outside class.
      And there is no other setter function except addVertex, addEdge, removeEdge and removeVertex.
      Anywhere needed get the mutable fields is defencive by defencive copy
   */
  public NetworkTopology(String name) {
    super(name);
    super.checkRep();
  }

  @Override
  protected void checkRep() {
    super.checkRep();
    Set<NetworkConnection> edgeSet = this.edges();
    for (Edge edge : edgeSet) {
      List<Vertex> source = new ArrayList<>(edge.sourceVertices());
      assert source.size() == 2;
      List<Vertex> target = new ArrayList<>(edge.targetVertices());
      assert target.size() == 2;
      assert !source.get(0).equals(target.get(1));
    }
  }

  @Override
  public boolean addEdge(NetworkConnection edge) throws InputFileAgainException {
    boolean answer = super.addEdge(edge);
    //    checkRep();
    return answer;
  }

  @Override
  public String toString() {
    return "NetworkTopology" + super.toString().replace("ConcreteGraph", "");
  }
}
