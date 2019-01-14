package application;

import application.GUIHelper.GraphGUI;
import edge.Edge;
import edge.NetworkConnection;
import exception.InputFileAgainException;
import graph.Graph;
import vertex.NetworkEquipment;
import vertex.Vertex;

/**
 * Create a GUI visualization of NetworkTopology
 *
 * @author Zhu Mingyan
 */
class NetworkTopologyAppGUI extends GraphGUI {
    NetworkTopologyAppGUI(Graph graph) {
        super(graph);
    }

    /**
     * Add Vertex into NetworkTopology visualization
     *
     * @param vertex its type should be NetworkEquipment we have defined before and is non-null
     * @return true if we add it successfully, otherwise false
     */
    @Override
    public boolean addVertexInGraph(Vertex vertex) {
        return vertex instanceof NetworkEquipment && super.addVertexInGraph(vertex);
    }

    /**
     * Add edge into NetworkTopology visualization
     *
     * @param edge its type should be NetworkConnection we have defined and is non-null
     * @return true if we add it successfully, otherwise false
     */
    @Override
    public boolean addEdgeInGraph(Edge edge) throws InputFileAgainException {
        return edge instanceof NetworkConnection && super.addEdgeInGraph(edge);
    }
}
