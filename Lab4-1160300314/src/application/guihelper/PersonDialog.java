package application.guihelper;

import java.awt.Component;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import vertex.Person;
import vertex.Vertex;

/**
 * A dialog when add Person or modify Person vertex.
 *
 * @author Zhu Mingyan
 */
class PersonDialog {
  /*
  AF: Represents a dialog using when add or modify Person vertex
  RI: The instance of JDialog is non-null
  Safety for Rep Exposure:
      All fields are modified by private and using defencive copy when needed something field
   */

  private JDialog dialog;
  private JTextField label;
  private JTextField gender;
  private JTextField age;

  private JLabel label1 = new JLabel("Label:");
  private JLabel label2 = new JLabel("Gender:");
  private JLabel label3 = new JLabel("Age:");

  private JButton button = new JButton("Add");

  private JPanel panel = new JPanel();
  private GroupLayout layout = new GroupLayout(panel);

  /**
   * Create an instance of PersonDialog with parent Component, parent Frame and the modified vertex.
   *
   * @param owner parent Frame and non-null
   * @param parentComponent parent Component and non-null
   */
  PersonDialog(GraphGui owner, Component parentComponent) {
    this.dialog = new JDialog(owner, "Person", true);
    label = new JTextField();
    gender = new JTextField();
    age = new JTextField();
    ini(owner, parentComponent);
  }

  /**
   * Create an instance of PersonDialog with parent Component, parent Frame and the modified vertex.
   *
   * @param owner parent Frame and non-null
   * @param parentComponent parent Component and non-null
   * @param vertex the modified vertex and non-null
   */
  PersonDialog(GraphGui owner, Component parentComponent, Vertex vertex) {
    this.dialog = new JDialog(owner, "Person", true);
    if (vertex instanceof Person) {
      label = new JTextField(vertex.getLabel());
      gender = new JTextField(((Person) vertex).getGender());
      age = new JTextField(((Person) vertex).getAge());
    } else {
      label = new JTextField();
      gender = new JTextField();
      age = new JTextField();
    }
    ini(owner, parentComponent);
  }

  private void ini(GraphGui owner, Component parentComponent) {
    dialog.setSize(250, 180);
    dialog.setResizable(false);
    dialog.setLocationRelativeTo(parentComponent);

    panel.setLayout(layout);
    GroupLayout.SequentialGroup hgroup = layout.createSequentialGroup();
    hgroup.addGap(5);
    hgroup.addGroup(layout.createParallelGroup().addComponent(label1).addComponent(label2)
        .addComponent(label3));
    hgroup.addGap(5);
    hgroup.addGroup(
        layout.createParallelGroup().addComponent(label).addComponent(gender).addComponent(age)
            .addComponent(button));
    hgroup.addGap(5);
    layout.setHorizontalGroup(hgroup);

    GroupLayout.SequentialGroup vgroup = layout.createSequentialGroup();
    vgroup.addGap(10);
    vgroup.addGroup(layout.createParallelGroup().addComponent(label1).addComponent(label));
    vgroup.addGap(10);
    vgroup.addGroup(layout.createParallelGroup().addComponent(label2).addComponent(gender));
    vgroup.addGap(10);
    vgroup.addGroup(layout.createParallelGroup().addComponent(label3).addComponent(age));
    vgroup.addGap(10);
    vgroup.addGroup(layout.createParallelGroup().addComponent(button));
    vgroup.addGap(10);

    layout.setVerticalGroup(vgroup);

    button.addActionListener(e -> {
      dialog.dispose();
      owner.setVertexLabel(label.getText());
      String[] args = new String[]{gender.getText(), age.getText()};
      owner.setArgs(args);
    });
    dialog.setContentPane(panel);
    dialog.setVisible(true);

  }
}
