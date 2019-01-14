package application.guihelper;

import java.awt.Component;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import vertex.Vertex;

/**
 * A dialog when calculate the distance between two vertices.
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

  DistanceDialog(GraphGui owner, Component parentComponent) {
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

    panel.add(button);
    panel.add(source);
    panel.add(destination);
    button.addActionListener(e -> {
      dialog.dispose();
      owner.setSource((Vertex) source.getSelectedItem());
      owner.setDestination((Vertex) destination.getSelectedItem());
    });
    dialog.setContentPane(panel);
    dialog.setVisible(true);
  }
}
