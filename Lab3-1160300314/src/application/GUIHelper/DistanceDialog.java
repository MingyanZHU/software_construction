package application.GUIHelper;

import vertex.Vertex;

import javax.swing.*;
import java.awt.*;
import java.util.Set;

/**
 * A dialog when calculate the distance between two vertices
 *
 * @author Zhu Mingyan
 */
class DistanceDialog {
    /*
    AF: Represents a dialog using when calculate the distance
    RI: The instance of JDialog is non-null
    Safety for Rep Exposure:
        All fields are modified by private and using defencive copy when needed something field
     */

    private JDialog dialog;

    DistanceDialog(GraphGUI owner, Component parentComponent) {
        this.dialog = new JDialog(owner, "Calculate Distance", true);
        dialog.setSize(450, 150);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parentComponent);
        Set<Vertex> vertexSet = owner.getVertices();
        JComboBox<Vertex> source = new JComboBox<>();
        JComboBox<Vertex> destination = new JComboBox<>();
        JPanel panel = new JPanel();
        JButton button = new JButton("OK");

        for (Vertex vertex : vertexSet) {
            source.addItem(vertex);
            destination.addItem(vertex);
        }

        panel.add(source);
        panel.add(destination);
        panel.add(button);
        button.addActionListener(e -> {
            dialog.dispose();
            owner.setSource((Vertex) source.getSelectedItem());
            owner.setDestination((Vertex) destination.getSelectedItem());
        });
        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }
}
