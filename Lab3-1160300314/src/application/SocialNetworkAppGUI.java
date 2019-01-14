package application;

import application.GUIHelper.GraphGUI;
import edge.CommentConnection;
import edge.Edge;
import edge.ForwardConnection;
import edge.FriendConnection;
import exception.InputFileAgainException;
import graph.Graph;
import vertex.Person;
import vertex.Vertex;

/**
 * Create a GUI visualization of SocialNetwork
 *
 * @author Zhu Mingyan
 */
class SocialNetworkAppGUI extends GraphGUI {
    SocialNetworkAppGUI(Graph graph) {
        super(graph);
    }

    /**
     * Add vertex into SocialNetwork visualization
     *
     * @param vertex its type should be Person Class we have defined before and is non-null
     * @return true if we add it successfully, otherwise false
     */
    @Override
    public boolean addVertexInGraph(Vertex vertex) {
        return vertex instanceof Person && super.addVertexInGraph(vertex);
    }

    /**
     * Add edge into SocialNetwork visualization
     *
     * @param edge its type should be CommentConnection, ForwardConnection or FriendConnection Class
     *             we have defined and is non-null
     * @return true if we add it successfully, otherwise false
     */
    @Override
    public boolean addEdgeInGraph(Edge edge) throws InputFileAgainException {
        return (edge instanceof CommentConnection || edge instanceof FriendConnection
                || edge instanceof ForwardConnection) && super.addEdgeInGraph(edge);
    }
}
