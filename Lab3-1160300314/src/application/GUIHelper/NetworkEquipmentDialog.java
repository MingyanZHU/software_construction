package application.GUIHelper;

import vertex.NetworkEquipment;
import vertex.Vertex;

import javax.swing.*;
import java.awt.*;

/**
 * A dialog when add NetworkEquipment or modify NetworkEquipment vertex
 *
 * @author Zhu Mingyan
 */

class NetworkEquipmentDialog {
    /*
    AF: Represents a dialog using when add or modify NetworkEquipment vertex
    RI: The instance of JDialog is non-null
    Safety for Rep Exposure:
        All fields are modified by private and using defencive copy when needed something field
     */

    private JDialog dialog;
    private JTextField label;
    private JTextField IP;
    private JLabel label1 = new JLabel("Label:");
    private JLabel label2 = new JLabel("IP:");
    private JButton jButton = new JButton("Add");

    private JPanel panel = new JPanel();
    private GroupLayout layout = new GroupLayout(panel);

    /**
     * Create an instance of NetworkEquipmentDialog with parent Component, parent Frame and the modified vertex
     *
     * @param owner           parent Frame and non-null
     * @param parentComponent parent Component and non-null
     */

    NetworkEquipmentDialog(GraphGUI owner, Component parentComponent) {
        this.dialog = new JDialog(owner, "Network Equipment", true);
        label = new JTextField();
        IP = new JTextField();
        ini(owner, parentComponent);
    }

    /**
     * Create an instance of NetworkEquipmentDialog with parent Component, parent Frame and the modified vertex
     *
     * @param owner           parent Frame and non-null
     * @param parentComponent parent Component and non-null
     * @param vertex          the modified vertex and non-null
     */

    NetworkEquipmentDialog(GraphGUI owner, Component parentComponent, Vertex vertex) {
        this.dialog = new JDialog(owner, "Network Equipment", true);
        if (vertex instanceof NetworkEquipment) {
            label = new JTextField(vertex.getLabel());
            IP = new JTextField(((NetworkEquipment) vertex).getIP());
        } else {
            label = new JTextField();
            IP = new JTextField();
        }
        ini(owner, parentComponent);
    }

    private void ini(GraphGUI owner, Component parentComponent) {
        dialog.setSize(250, 150);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parentComponent);

        panel.setLayout(layout);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGap(5);
        hGroup.addGroup(layout.createParallelGroup().addComponent(label1).addComponent(label2));
        hGroup.addGap(5);
        hGroup.addGroup(layout.createParallelGroup().addComponent(label).addComponent(IP).addComponent(jButton));
        hGroup.addGap(5);
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(label1).addComponent(label));
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(label2).addComponent(IP));
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(jButton));
        vGroup.addGap(10);
        layout.setVerticalGroup(vGroup);

        jButton.addActionListener(e -> {
            owner.setVertexLabel(label.getText());
            String[] args = new String[]{IP.getText()};
            dialog.dispose();
            owner.setArgs(args);
        });
        dialog.setContentPane(panel);
        dialog.setVisible(true);

    }
}
