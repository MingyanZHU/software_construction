package application.guihelper;

import java.awt.Component;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import vertex.Movie;
import vertex.Vertex;

/**
 * A dialog when add or modify Movie vertex.
 *
 * @author Zhu Mingyan
 */

class MovieDialog {
  /*
  AF: Represents a dialog using when add or modify Movie vertex
  RI: The instance of JDialog is non-null
  Safety for Rep Exposure:
      All fields are modified by private and using defencive copy when needed something field
   */

  private JDialog dialog;
  private JTextField label;
  private JTextField releaseYear;
  private JTextField nation;
  private JTextField score;

  private JLabel label1 = new JLabel("Label:");
  private JLabel label2 = new JLabel("ReleaseYear:");
  private JLabel label3 = new JLabel("Nation:");
  private JLabel label4 = new JLabel("Score:");
  private JButton button = new JButton("Add");

  private JPanel panel = new JPanel();

  private GroupLayout layout = new GroupLayout(panel);

  /**
   * Create an instance of MovieDialog with parent Component, parent Frame and the modified vertex.
   *
   * @param owner parent Frame and non-null
   * @param parentComponent parent Component and non-null
   */

  MovieDialog(GraphGui owner, Component parentComponent) {
    this.dialog = new JDialog(owner, "Movie", true);
    label = new JTextField();
    releaseYear = new JTextField();
    nation = new JTextField();
    score = new JTextField();
    ini(owner, parentComponent);
  }

  /**
   * Create an instance of MovieDialog with parent Component, parent Frame and the modified vertex.
   *
   * @param owner parent Frame and non-null
   * @param parentComponent parent Component and non-null
   * @param vertex the modified vertex and non-null
   */

  MovieDialog(GraphGui owner, Component parentComponent, Vertex vertex) {
    this.dialog = new JDialog(owner, "Movie", true);
    if (vertex instanceof Movie) {
      label = new JTextField(vertex.getLabel());
      releaseYear = new JTextField(((Movie) vertex).getReleaseYear());
      nation = new JTextField(((Movie) vertex).getNation());
      score = new JTextField(String.valueOf(((Movie) vertex).getScore()));
    } else {
      label = new JTextField();
      releaseYear = new JTextField();
      nation = new JTextField();
      score = new JTextField();
    }
    ini(owner, parentComponent);
  }

  private void ini(GraphGui owner, Component parentComponent) {
    dialog.setSize(250, 220);
    dialog.setResizable(false);
    dialog.setLocationRelativeTo(parentComponent);

    panel.setLayout(layout);
    GroupLayout.SequentialGroup hgroup = layout.createSequentialGroup();
    hgroup.addGap(5);
    hgroup.addGroup(
        layout.createParallelGroup().addComponent(label1).addComponent(label2).addComponent(label3)
            .addComponent(label4));
    hgroup.addGap(5);
    hgroup.addGroup(layout.createParallelGroup().addComponent(label).addComponent(releaseYear)
        .addComponent(nation)
        .addComponent(score).addComponent(button));
    hgroup.addGap(5);
    layout.setHorizontalGroup(hgroup);

    GroupLayout.SequentialGroup vgroup = layout.createSequentialGroup();
    vgroup.addGap(10);
    vgroup.addGroup(layout.createParallelGroup().addComponent(label1).addComponent(label));
    vgroup.addGap(10);
    vgroup.addGroup(layout.createParallelGroup().addComponent(label2).addComponent(releaseYear));
    vgroup.addGap(10);
    vgroup.addGroup(layout.createParallelGroup().addComponent(label3).addComponent(nation));
    vgroup.addGap(10);
    vgroup.addGroup(layout.createParallelGroup().addComponent(label4).addComponent(score));
    vgroup.addGap(10);
    vgroup.addGroup(layout.createParallelGroup().addComponent(button));
    vgroup.addGap(10);
    layout.setVerticalGroup(vgroup);

    button.addActionListener(e -> {
      dialog.dispose();
      owner.setVertexLabel(label.getText());
      String[] args = new String[]{releaseYear.getText(), nation.getText(), score.getText()};
      owner.setArgs(args);
    });
    dialog.setContentPane(panel);
    dialog.setVisible(true);

  }
}
