package display;

import java.awt.EventQueue;
import javax.swing.JFrame;
import monkey.MonkeyPane;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.log4j.PropertyConfigurator;

public class GuiDisplay {

  private final MonkeyPane monkeyPane;

  /*
  Rep invariant:
    monkeyPane != null.
  Abstraction function:
    represents displaying monkeys strategy river with GUI.
  Safety from rep exposure:
    monkeyPane is private and immutable.
  Thread safety arguments:
    there is no sharing data between threads.
   */

  /**
   * Displaying monkeys strategy river with GUI.
   *
   * @throws ConfigurationException if config has some problem.
   */
  public GuiDisplay() throws ConfigurationException {
    PropertyConfigurator.configure("log4j.properties");
    this.monkeyPane = new MonkeyPane();
    EventQueue.invokeLater(() -> {
      JFrame frame = new JFrame("Testing");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.add(monkeyPane);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
    });

    this.checkRep();
  }

  private void checkRep() {
    assert monkeyPane != null;
  }

  /**
   * To get the pane of displaying monkeys.
   *
   * @return MonkeyPane instance containing all monkeys.
   */
  public MonkeyPane getMonkeyPane() {
    return this.monkeyPane;
  }

}