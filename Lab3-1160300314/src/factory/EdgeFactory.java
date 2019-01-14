package factory;

import edge.*;
import exception.InputFileAgainException;
import vertex.Vertex;

import java.util.List;

/**
 * A factory that used to create instances of edges
 *
 * @author Zhu Mingyan
 */
public class EdgeFactory {
    /**
     * Create an instance of determined type with all params provided
     *
     * @param label    the label string of instance which is non-null and non-empty string
     * @param type     the type of this instance you want to which should be in all types we have been defined
     * @param weight   the weight of this edge instance if this edge is unweighted edge weight should be -1
     *                 otherwise should be above 0.
     * @param vertices all vertices should in this edge. If this edge is directed, the former should be the
     *                 source vertex and the latter should be the destination. If this edge is a loop, vertices
     *                 should have exactly one vertex.
     * @return an instance of the determined type and provided params
     */
    public static Edge createEdge(String label, String type, double weight, List<Vertex> vertices) throws InputFileAgainException {
        Edge edge;
        if (type.equals("WordEdge")) {
            edge = new WordEdge(label, weight);
        } else if (type.equals("NetworkConnection")) {
            edge = new NetworkConnection(label, weight);
        } else if (type.equals("MovieDirectorRelation")) {
            edge = new MovieDirectorRelation(label, weight);
        } else if (type.equals("MovieActorRelation")) {
            edge = new MovieActorRelation(label, weight);
        } else if (type.equals("FriendConnection")) {
            edge = new FriendConnection(label, weight);
        } else if (type.equals("ForwardConnection")) {
            edge = new ForwardConnection(label, weight);
        } else if (type.equals("CommentConnection")) {
            edge = new CommentConnection(label, weight);
        } else {
            edge = null;
        }
        assert edge != null;
        edge.addVertices(vertices);
        return edge;
    }
}
