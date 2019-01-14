package application.GUIHelper;

import edge.Edge;
import vertex.Vertex;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Set;

/**
 * A dialog when add or modify edge
 *
 * @author Zhu Mingyan
 */
class EdgeDialog {
    /*
    AF: Represents a dialog using when add or modify edge
    RI: The instance of JDialog is non-null
    Safety for Rep Exposure:
        All fields are modified by private and using defencive copy when needed something field
     */

    private JDialog dialog;
    private JComboBox<Vertex> source = new JComboBox<>();
    private JComboBox<Vertex> destination = new JComboBox<>();
    private JTextField label = new JTextField();
    private JTextField weight = new JTextField();

    private JButton button = new JButton("OK");

    private JLabel label1 = new JLabel("Vertex1:");
    private JLabel label2 = new JLabel("Vertex2:");
    private JLabel label3 = new JLabel("Label:");
    private JLabel label4 = new JLabel("Weight");

    private JPanel panel = new JPanel();
    private GroupLayout layout = new GroupLayout(panel);

    /**
     * Create an instance of EdgeDialog with parent Component, parent Frame and the modified edge
     *
     * @param owner           parent Frame and non-null
     * @param parentComponent parent Component and non-null
     */

    EdgeDialog(GraphGUI owner, Component parentComponent) {
        this.dialog = new JDialog(owner, "Edge", true);
        ini(owner, parentComponent);
    }

    /**
     * Create an instance of EdgeDialog with parent Component, parent Frame and the modified edge
     *
     * @param owner           parent Frame and non-null
     * @param parentComponent parent Component and non-null
     * @param edge  edge that added or modified
     */
    EdgeDialog(GraphGUI owner, Component parentComponent, Edge edge) {
        this.dialog = new JDialog(owner, "Edge", true);
        label = new JTextField(edge.getLabel());
        weight = new JTextField(String.valueOf(edge.getWeight()));
        ini(owner, parentComponent);
    }

    private void ini(GraphGUI owner, Component parentComponent) {
        this.dialog.setSize(600, 250);
        dialog.setLocationRelativeTo(parentComponent);

        Set<Vertex> vertexSet = owner.getVertices();
        for (Vertex vertex : vertexSet) {
            source.addItem(vertex);
            destination.addItem(vertex);
        }

        panel.setLayout(layout);
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGap(5);
        hGroup.addGroup(layout.createParallelGroup().addComponent(label3).addComponent(label1).addComponent(label2)
                .addComponent(label4));
        hGroup.addGap(5);
        hGroup.addGroup(layout.createParallelGroup().addComponent(label).addComponent(source).addComponent(destination)
                .addComponent(weight).addComponent(button));
        hGroup.addGap(5);
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(label3).addComponent(label));
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(label1).addComponent(source));
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(label2).addComponent(destination));
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(label4).addComponent(weight));
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(button));
        vGroup.addGap(10);
        layout.setVerticalGroup(vGroup);

        button.addActionListener(e -> {
            owner.setEdgeLabel(label.getText());
            owner.setWeight(Double.valueOf(weight.getText()));
            owner.setVertexList(Arrays.asList((Vertex) source.getSelectedItem(), (Vertex) destination.getSelectedItem()));
            dialog.dispose();
        });
        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }
}
