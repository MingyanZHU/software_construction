package application.guihelper;

import java.awt.Component;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import vertex.NetworkEquipment;
import vertex.Vertex;

/**
 * A dialog when add NetworkEquipment or modify NetworkEquipment vertex.
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
  private JTextField ip;
  private JLabel label1 = new JLabel("Label:");
  private JLabel label2 = new JLabel("ip:");
  private JButton button = new JButton("Add");

  private JPanel panel = new JPanel();
  private GroupLayout layout = new GroupLayout(panel);

  /**
   * Create an instance of NetworkEquipmentDialog with parent Component, parent Frame and the
   * modified vertex.
   *
   * @param owner parent Frame and non-null
   * @param parentComponent parent Component and non-null
   */

  NetworkEquipmentDialog(GraphGui owner, Component parentComponent) {
    this.dialog = new JDialog(owner, "Network Equipment", true);
    label = new JTextField();
    ip = new JTextField();
    ini(owner, parentComponent);
  }

  /**
   * Create an instance of NetworkEquipmentDialog with parent Component, parent Frame and the
   * modified vertex.
   *
   * @param owner parent Frame and non-null
   * @param parentComponent parent Component and non-null
   * @param vertex the modified vertex and non-null
   */

  NetworkEquipmentDialog(GraphGui owner, Component parentComponent, Vertex vertex) {
    this.dialog = new JDialog(owner, "Network Equipment", true);
    if (vertex instanceof NetworkEquipment) {
      label = new JTextField(vertex.getLabel());
      ip = new JTextField(((NetworkEquipment) vertex).getIp());
    } else {
      label = new JTextField();
      ip = new JTextField();
    }
    ini(owner, parentComponent);
  }

  private void ini(GraphGui owner, Component parentComponent) {
    dialog.setSize(250, 150);
    dialog.setResizable(false);
    dialog.setLocationRelativeTo(parentComponent);

    panel.setLayout(layout);

    GroupLayout.SequentialGroup hgroup = layout.createSequentialGroup();
    hgroup.addGap(5);
    hgroup.addGroup(layout.createParallelGroup().addComponent(label1).addComponent(label2));
    hgroup.addGap(5);
    hgroup.addGroup(
        layout.createParallelGroup().addComponent(label).addComponent(ip).addComponent(button));
    hgroup.addGap(5);
    layout.setHorizontalGroup(hgroup);

    GroupLayout.SequentialGroup vgroup = layout.createSequentialGroup();
    vgroup.addGap(10);
    vgroup.addGroup(layout.createParallelGroup().addComponent(label1).addComponent(label));
    vgroup.addGap(10);
    vgroup.addGroup(layout.createParallelGroup().addComponent(label2).addComponent(ip));
    vgroup.addGap(10);
    vgroup.addGroup(layout.createParallelGroup().addComponent(button));
    vgroup.addGap(10);
    layout.setVerticalGroup(vgroup);

    button.addActionListener(e -> {
      owner.setVertexLabel(label.getText());
      String[] args = new String[]{ip.getText()};
      dialog.dispose();
      owner.setArgs(args);
    });
    dialog.setContentPane(panel);
    dialog.setVisible(true);

  }
}
