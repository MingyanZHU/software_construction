package graph;

import edge.CommentConnection;
import edge.Edge;
import edge.ForwardConnection;
import edge.FriendConnection;
import exception.InputFileAgainException;
import factory.EdgeFactory;
import vertex.Person;
import vertex.Vertex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Describe a graph of Weibo social network, which is a multidigraph, weighted graph and single mode graph.
 *
 * @author Zhu Mingyan
 */
public class SocialNetwork extends ConcreteGraph<Person, Edge> {
    /*
    AF: Represents a graph of Weibo social network with Person as vertex and Edge as edge
    RI: vertexSet that the collections of all vertices in graph should not be null
        edgeSet that the collections of all edges in graph should not be null
        Label(name) of graph should be non-null and non-empty String.
        Sum of all edges are exactly one.
    Safety for Rep Exposure:
        All fields are modified by key word private, clients can not access the field outside class.
        And there is no other setter function except addVertex, addEdge, removeEdge and removeVertex.
        Anywhere needed get the mutable fields is defencive by defencive copy
     */
    public SocialNetwork(String name) {
        super(name);
        super.checkRep();
    }

    @Override
    protected void checkRep() {
        super.checkRep();
        double sum = 0;
        Set<Edge> edgeSet = this.edges();
        for (Edge edge : edgeSet) {
            List<Vertex> source = new ArrayList<>(edge.sourceVertices());
            List<Vertex> target = new ArrayList<>(edge.targetVertices());
            assert !source.get(0).equals(target.get(0));
            sum += edge.getWeight();
        }
        assert Math.abs(sum - 1) < 0.0001;
    }

    /**
     * Add edge into Social Network and the edge should be instance of
     * CommentConnection or ForwardConnection or FriendConnection.
     *
     * @param edge
     * @return
     */
    public boolean addEdge(Edge edge) throws InputFileAgainException {
        boolean answer = (edge instanceof CommentConnection || edge instanceof ForwardConnection
                || edge instanceof FriendConnection) && super.addEdge(edge);
        if (answer) {
            Set<Edge> edges = this.edges();
            double factor = 1 - edge.getWeight();
            for (Edge edge1 : edges) {
                if (!edge1.equals(edge)) {
                    super.removeEdge(edge1);
                    List<Vertex> source = new ArrayList<>(edge1.sourceVertices());
                    List<Vertex> target = new ArrayList<>(edge1.targetVertices());
                    Edge edge2 = EdgeFactory.createEdge(edge1.getLabel(), edge1.getClass().getSimpleName(), edge1.getWeight() * factor, Arrays.asList(source.get(0), target.get(0)));
                    super.addEdge(edge2);
                }
            }
        }
        checkRep();
        return answer;
    }

    public boolean removeEdge(Edge edge) throws InputFileAgainException {
        boolean answer = super.removeEdge(edge);
        if (answer) {
            Set<Edge> edges = this.edges();
            double factor = 1 - edge.getWeight();
            for (Edge edge1 : edges) {
                super.removeEdge(edge1);
                List<Vertex> source = new ArrayList<>(edge1.sourceVertices());
                List<Vertex> target = new ArrayList<>(edge1.targetVertices());
                assert source.size() == 1;
                assert target.size() == 1;
                Edge edge2 = EdgeFactory.createEdge(edge1.getLabel(), edge1.getClass().getTypeName(), edge1.getWeight() / factor, Arrays.asList(source.get(0), target.get(0)));
                super.addEdge(edge2);
            }
        }
        checkRep();
        return answer;
    }

    @Override
    public String toString() {
        return "SocialNetwork" + super.toString().replace("ConcreteGraph", "");
    }
}
