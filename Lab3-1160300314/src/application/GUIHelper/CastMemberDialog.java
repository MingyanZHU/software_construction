package application.GUIHelper;

import vertex.CastMember;
import vertex.Vertex;

import javax.swing.*;
import java.awt.*;

/**
 * A dialog when add or modify CastMember vertex
 *
 * @author Zhu Mingyan
 */

class CastMemberDialog {
    /*
    AF: Represents a dialog using when add or modify CastMember vertex
    RI: The instance of JDialog is non-null
    Safety for Rep Exposure:
        All fields are modified by private and using defencive copy when needed something field
     */

    private JDialog dialog;
    private JTextField label;
    private JTextField age;
    private JTextField gender;

    private JLabel label1 = new JLabel("Label:");
    private JLabel label2 = new JLabel("Age:");
    private JLabel label3 = new JLabel("Gender:");

    private JButton button = new JButton("Add");

    private JPanel panel = new JPanel();
    private GroupLayout layout = new GroupLayout(panel);

    /**
     * Create an instance of CastMemberDialog with parent Component, parent Frame and the modified vertex
     *
     * @param owner           parent Frame and non-null
     * @param parentComponent parent Component and non-null
     */

    CastMemberDialog(GraphGUI owner, Component parentComponent) {
        this.dialog = new JDialog(owner, "Person", true);
        label = new JTextField();
        gender = new JTextField();
        age = new JTextField();
        ini(owner, parentComponent);
    }

    /**
     * Create an instance of CastMemberDialog with parent Component, parent Frame and the modified vertex
     *
     * @param owner           parent Frame and non-null
     * @param parentComponent parent Component and non-null
     * @param vertex          the modified vertex and non-null
     */

    CastMemberDialog(GraphGUI owner, Component parentComponent, Vertex vertex) {
        this.dialog = new JDialog(owner, "Person", true);
        if (vertex instanceof CastMember) {
            label = new JTextField(vertex.getLabel());
            gender = new JTextField(((CastMember) vertex).getGender());
            age = new JTextField(String.valueOf(((CastMember) vertex).getAge()));
        } else {
            label = new JTextField();
            gender = new JTextField();
            age = new JTextField();
        }
        ini(owner, parentComponent);
    }

    private void ini(GraphGUI owner, Component parentComponent) {
        dialog.setSize(250, 180);
        dialog.setResizable(false);
        dialog.setLocationRelativeTo(parentComponent);

        panel.setLayout(layout);
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGap(5);
        hGroup.addGroup(layout.createParallelGroup().addComponent(label1).addComponent(label2).addComponent(label3));
        hGroup.addGap(5);
        hGroup.addGroup(layout.createParallelGroup().addComponent(label).addComponent(age).addComponent(gender)
                .addComponent(button));
        hGroup.addGap(5);
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(label1).addComponent(label));
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(label2).addComponent(age));
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(label3).addComponent(gender));
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup().addComponent(button));
        vGroup.addGap(10);

        layout.setVerticalGroup(vGroup);

        button.addActionListener(e -> {
            dialog.dispose();
            owner.setVertexLabel(label.getText());
            String[] args = new String[]{age.getText(), gender.getText()};
            owner.setArgs(args);
        });
        dialog.setContentPane(panel);
        dialog.setVisible(true);
    }
}
