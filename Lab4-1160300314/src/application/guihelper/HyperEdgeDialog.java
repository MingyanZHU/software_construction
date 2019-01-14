package application.guihelper;

import edge.Edge;
import java.awt.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import vertex.Vertex;

/**
 * A dialog when add or modify hyperEdge.
 *
 * @author Zhu Mingyan
 */
class HyperEdgeDialog {
  /*
  AF: Represents a dialog using when add or modify hyperEdge
  RI: The instance of JDialog is non-null
  Safety for Rep Exposure:
      All fields are modified by private and using defencive copy when needed something field
   */

  private JDialog dialog;
  private JPanel panel = new JPanel();
  private JLabel label = new JLabel("label:");
  private JTextField textField = new JTextField(30);
  private JButton button = new JButton("OK");
  private Map<Vertex, JCheckBox> map = new HashMap<>();

  /**
   * Create an instance of HyperEdgeDialog with parent Component, parent Frame and the modified
   * edge.
   *
   * @param owner parent Frame and non-null
   * @param parentComponent parent Component and non-null
   */
  HyperEdgeDialog(GraphGui owner, Component parentComponent) {
    dialog = new JDialog(owner, "HyperEdge", true);
    Set<Vertex> vertexSet = owner.getVertices();
    for (Vertex vertex : vertexSet) {
      JCheckBox checkBox = new JCheckBox(vertex.getLabel());
      map.put(vertex, checkBox);
      panel.add(checkBox);
    }
    ini(owner, parentComponent);
  }

  /**
   * Create an instance of HyperEdgeDialog with parent Component, parent Frame and the modified
   * edge.
   *
   * @param owner parent Frame and non-null
   * @param parentComponent parent Component and non-null
   * @param edge edge that added or modified
   */
  HyperEdgeDialog(GraphGui owner, Component parentComponent, Edge edge) {
    dialog = new JDialog(owner, "HyperEdge", true);
    Set<Vertex> hyperEdgeSet = edge.vertices();
    textField = new JTextField(edge.getLabel(), 30);
    Set<Vertex> vertexSet = owner.getVertices();
    for (Vertex vertex : vertexSet) {
      JCheckBox checkBox = new JCheckBox(vertex.getLabel());
      map.put(vertex, checkBox);
      panel.add(checkBox);
    }
    for (Vertex vertex : hyperEdgeSet) {
      map.get(vertex).setSelected(true);
    }
    ini(owner, parentComponent);
  }

  private void ini(GraphGui owner, Component parentComponent) {
    this.dialog.setSize(300, 250);
    dialog.setLocationRelativeTo(parentComponent);

    panel.add(label);
    panel.add(textField);
    List<Vertex> chosenVertices = new ArrayList<>();
    button.addActionListener(e -> {
      dialog.dispose();
      owner.setEdgeLabel(textField.getText());
      for (Map.Entry<Vertex, JCheckBox> entry : map.entrySet()) {
        if (entry.getValue().isSelected()) {
          chosenVertices.add(entry.getKey());
        }
      }
      owner.setVertexList(chosenVertices);
    });
    panel.add(button);
    dialog.setContentPane(panel);
    dialog.setVisible(true);
  }
}
