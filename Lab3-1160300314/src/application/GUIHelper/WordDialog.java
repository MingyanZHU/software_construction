package application.GUIHelper;

import vertex.Vertex;
import vertex.Word;

import javax.swing.*;
import java.awt.*;

/**
 * A dialog when add word or modify word vertex
 *
 * @author Zhu Mingyan
 */
class WordDialog {
    /*
    AF: Represents a dialog using when add or modify word vertex
    RI: The instance of JDialog is non-null
    Safety for Rep Exposure:
        All fields are modified by private and using defencive copy when needed something field
     */
    private JDialog dialog;
    private JTextField label;
    private JLabel jLabel = new JLabel("Label:");
    private JButton button = new JButton("Add");
    private JPanel panel = new JPanel();

    GroupLayout layout = new GroupLayout(panel);

    /**
     * Create an instance of WordDialog with parent Component and parent Frame
     *
     * @param owner           parent Frame and non-null
     * @param parentComponent parent Component and non-null
     */
    WordDialog(GraphGUI owner, Component parentComponent) {
        this.dialog = new JDialog(owner, "Word", true);
        label = new JTextField();
        ini(owner, parentComponent);
    }

    /**
     * Create an instance of WordDialog with parent Component, parent Frame and the modified vertex
     *
     * @param owner           parent Frame and non-null
     * @param parentComponent parent Component and non-null
     * @param vertex          the modified vertex and non-null
     */
    WordDialog(GraphGUI owner, Component parentComponent, Vertex vertex) {
        this.dialog = new JDialog(owner, "Word", true);
        if (vertex instanceof Word) {
            label = new JTextField(vertex.getLabel());
        } else {
            label = new JTextField();
        }
        ini(owner, parentComponent);
    }

    private void ini(GraphGUI owner, Component parentComponent) {
        dialog.setSize(250, 120);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parentComponent);
        panel.setLayout(layout);
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGap(5);
        hGroup.addGroup(layout.createParallelGroup().addComponent(jLabel));
        hGroup.addGap(5);
        hGroup.addGroup(layout.createParallelGroup().addComponent(label).addComponent(button));
        hGroup.addGap(5);
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(jLabel).addComponent(label));
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(button));
        vGroup.addGap(10);
        layout.setVerticalGroup(vGroup);

        button.addActionListener(e -> {
            dialog.dispose();
            owner.setVertexLabel(label.getText());
        });

        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }

}
