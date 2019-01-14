package application.GUIHelper;

import edge.Edge;
import vertex.Vertex;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * A dialog when add or modify hyperEdge
 *
 * @author Zhu Mingyan
 */
class HyperEdgeDialog {
    /*
    AF: Represents a dialog using when add or modify hyperEdge
    RI: The instance of JDialog is non-null
    Safety for Rep Exposure:
        All fields are modified by private and using defencive copy when needed something field
     */

    private JDialog dialog;
    private JPanel panel = new JPanel();
    private JLabel jLabel = new JLabel("Label:");
    private JTextField label = new JTextField(30);
    private JButton button = new JButton("OK");
    private Map<Vertex, JCheckBox> map = new HashMap<>();

    /**
     * Create an instance of HyperEdgeDialog with parent Component, parent Frame and the modified edge
     *
     * @param owner           parent Frame and non-null
     * @param parentComponent parent Component and non-null
     */
    HyperEdgeDialog(GraphGUI owner, Component parentComponent) {
        dialog = new JDialog(owner, "HyperEdge", true);
        Set<Vertex> vertexSet = owner.getVertices();
        for (Vertex vertex : vertexSet) {
            JCheckBox jCheckBox = new JCheckBox(vertex.getLabel());
            map.put(vertex, jCheckBox);
            panel.add(jCheckBox);
        }
        ini(owner, parentComponent);
    }

    /**
     * Create an instance of HyperEdgeDialog with parent Component, parent Frame and the modified edge
     *
     * @param owner           parent Frame and non-null
     * @param parentComponent parent Component and non-null
     * @param edge            edge that added or modified
     */
    HyperEdgeDialog(GraphGUI owner, Component parentComponent, Edge edge) {
        dialog = new JDialog(owner, "HyperEdge", true);
        Set<Vertex> hyperEdgeSet = edge.vertices();
        label = new JTextField(edge.getLabel(), 30);
        Set<Vertex> vertexSet = owner.getVertices();
        for (Vertex vertex : vertexSet) {
            JCheckBox jCheckBox = new JCheckBox(vertex.getLabel());
            map.put(vertex, jCheckBox);
            panel.add(jCheckBox);
        }
        for (Vertex vertex : hyperEdgeSet) {
            map.get(vertex).setSelected(true);
        }
        ini(owner, parentComponent);
    }

    private void ini(GraphGUI owner, Component parentComponent) {
        this.dialog.setSize(300, 250);
        dialog.setLocationRelativeTo(parentComponent);

        panel.add(jLabel);
        panel.add(label);
        List<Vertex> chosenVertices = new ArrayList<>();
        button.addActionListener(e -> {
            dialog.dispose();
            owner.setEdgeLabel(label.getText());
            for (Map.Entry<Vertex, JCheckBox> entry : map.entrySet()) {
                if (entry.getValue().isSelected()) {
                    chosenVertices.add(entry.getKey());
                }
            }
            owner.setVertexList(chosenVertices);
        });
        panel.add(button);
        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }
}
