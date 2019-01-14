package application.guihelper;

import edge.Edge;
import java.awt.Component;
import java.util.Arrays;
import java.util.Set;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import vertex.Vertex;

/**
 * A dialog when add or modify edge.
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
   * Create an instance of EdgeDialog with parent Component, parent Frame and the modified edge.
   *
   * @param owner parent Frame and non-null
   * @param parentComponent parent Component and non-null
   */

  EdgeDialog(GraphGui owner, Component parentComponent) {
    this.dialog = new JDialog(owner, "Edge", true);
    ini(owner, parentComponent);
  }

  /**
   * Create an instance of EdgeDialog with parent Component, parent Frame and the modified edge.
   *
   * @param owner parent Frame and non-null
   * @param parentComponent parent Component and non-null
   * @param edge edge that added or modified
   */
  EdgeDialog(GraphGui owner, Component parentComponent, Edge edge) {
    this.dialog = new JDialog(owner, "Edge", true);
    label = new JTextField(edge.getLabel());
    weight = new JTextField(String.valueOf(edge.getWeight()));
    ini(owner, parentComponent);
  }

  private void ini(GraphGui owner, Component parentComponent) {
    this.dialog.setSize(600, 250);
    dialog.setLocationRelativeTo(parentComponent);

    Set<Vertex> vertexSet = owner.getVertices();
    for (Vertex vertex : vertexSet) {
      source.addItem(vertex);
      destination.addItem(vertex);
    }

    panel.setLayout(layout);
    GroupLayout.SequentialGroup hgroup = layout.createSequentialGroup();
    hgroup.addGap(5);
    hgroup.addGroup(
        layout.createParallelGroup().addComponent(label3).addComponent(label1).addComponent(label2)
            .addComponent(label4));
    hgroup.addGap(5);
    hgroup.addGroup(layout.createParallelGroup().addComponent(label).addComponent(source)
        .addComponent(destination)
        .addComponent(weight).addComponent(button));
    hgroup.addGap(5);
    layout.setHorizontalGroup(hgroup);

    GroupLayout.SequentialGroup vgroup = layout.createSequentialGroup();
    vgroup.addGap(10);
    vgroup.addGroup(layout.createParallelGroup().addComponent(label3).addComponent(label));
    vgroup.addGap(10);
    vgroup.addGroup(layout.createParallelGroup().addComponent(label1).addComponent(source));
    vgroup.addGap(10);
    vgroup.addGroup(layout.createParallelGroup().addComponent(label2).addComponent(destination));
    vgroup.addGap(10);
    vgroup.addGroup(layout.createParallelGroup().addComponent(label4).addComponent(weight));
    vgroup.addGap(10);
    vgroup.addGroup(layout.createParallelGroup().addComponent(button));
    vgroup.addGap(10);
    layout.setVerticalGroup(vgroup);

    button.addActionListener(e -> {
      owner.setEdgeLabel(label.getText());
      owner.setWeight(Double.valueOf(weight.getText()));
      owner.setVertexList(
          Arrays.asList((Vertex) source.getSelectedItem(), (Vertex) destination.getSelectedItem()));
      dialog.dispose();
    });
    dialog.setContentPane(panel);
    dialog.setVisible(true);
  }
}
