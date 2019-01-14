package application.guihelper;

import java.awt.Component;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import vertex.Vertex;
import vertex.Word;

/**
 * A dialog when add word or modify word vertex.
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
  private JTextField textField;
  private JLabel label = new JLabel("Label:");
  private JButton button = new JButton("Add");
  private JPanel panel = new JPanel();

  private GroupLayout layout = new GroupLayout(panel);

  /**
   * Create an instance of WordDialog with parent Component and parent Frame.
   *
   * @param owner parent Frame and non-null
   * @param parentComponent parent Component and non-null
   */
  WordDialog(GraphGui owner, Component parentComponent) {
    this.dialog = new JDialog(owner, "Word", true);
    textField = new JTextField();
    ini(owner, parentComponent);
  }

  /**
   * Create an instance of WordDialog with parent Component, parent Frame and the modified vertex.
   *
   * @param owner parent Frame and non-null
   * @param parentComponent parent Component and non-null
   * @param vertex the modified vertex and non-null
   */
  WordDialog(GraphGui owner, Component parentComponent, Vertex vertex) {
    this.dialog = new JDialog(owner, "Word", true);
    if (vertex instanceof Word) {
      textField = new JTextField(vertex.getLabel());
    } else {
      textField = new JTextField();
    }
    ini(owner, parentComponent);
  }

  private void ini(GraphGui owner, Component parentComponent) {
    dialog.setSize(250, 120);
    dialog.setResizable(false);
    dialog.setLocationRelativeTo(parentComponent);
    panel.setLayout(layout);
    GroupLayout.SequentialGroup hgroup = layout.createSequentialGroup();
    hgroup.addGap(5);
    hgroup.addGroup(layout.createParallelGroup().addComponent(label));
    hgroup.addGap(5);
    hgroup.addGroup(layout.createParallelGroup().addComponent(textField).addComponent(button));
    hgroup.addGap(5);
    layout.setHorizontalGroup(hgroup);

    GroupLayout.SequentialGroup vgroup = layout.createSequentialGroup();
    vgroup.addGap(10);
    vgroup.addGroup(layout.createParallelGroup().addComponent(label).addComponent(textField));
    vgroup.addGap(10);
    vgroup.addGroup(layout.createParallelGroup().addComponent(button));
    vgroup.addGap(10);
    layout.setVerticalGroup(vgroup);

    button.addActionListener(e -> {
      dialog.dispose();
      owner.setVertexLabel(textField.getText());
    });

    dialog.setContentPane(panel);
    dialog.setVisible(true);
  }

}
