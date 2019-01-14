package monkey;

import basic.ConfigReader;
import basic.Params;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class MonkeyPane extends JPanel {

  private final List<Monkey> monkeys;
  public static final int xofLadderStart = 110;
  public static final int yofLadderStart = 110;
  // 开始画梯子的位置
  public static final int pedalInterval = 80;
  public static final int ladderInterval = 120;
  // 每两个踏板之间的间距同时也是踏板高度
  private final int lengthOfLadder;
  private final int numberOfPedals;
  private final int numberOfLadders;
  public static final int radium = 60;

  /*
  Rep invariant:
    monkeys != null
  Abstraction function:
    represents a pane contains all monkeys wanna to cross river.
  Safety from rep exposure:
    monkeys is private and modified by final.
  Thread safety arguments:
    addMonkey method which can access for threads is synchronized.
   */

  /**
   * Display all monkeys in this pane.
   *
   * @throws ConfigurationException if Config is wrong.
   */
  public MonkeyPane() throws ConfigurationException {
    this.monkeys = Collections.synchronizedList(new ArrayList<>());
    this.numberOfLadders = ConfigReader.getConfig(Params.n);
    this.numberOfPedals = ConfigReader.getConfig(Params.h);
    this.lengthOfLadder = this.numberOfPedals * pedalInterval + xofLadderStart;
    Timer timer = new Timer(40, e -> repaint());
    timer.start();
  }

  @Override
  public Dimension getPreferredSize() {
    return new Dimension(1900, 800);
  }

  @Override
  protected synchronized void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g.create();
    g2d.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
        RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING,
        RenderingHints.VALUE_COLOR_RENDER_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
    g2d.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
        RenderingHints.VALUE_FRACTIONALMETRICS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
        RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

    for (int i = 0; i < numberOfLadders; i++) {
      drawLadder(g2d, i);
    }
    if (!monkeys.isEmpty()) {

      for (Monkey monkey : monkeys) {
        monkey.paint(g2d);
      }
    }

    g2d.dispose();
  }

  synchronized void addMonkey(Monkey monkey) {
    monkeys.add(monkey);
  }

  private void drawLadder(Graphics g, int id) {
    int xofLadderStartTemp = xofLadderStart;
    int yofLadderStartTemp = yofLadderStart + id * ladderInterval;
    g.drawLine(xofLadderStartTemp, yofLadderStartTemp, xofLadderStartTemp + lengthOfLadder,
        yofLadderStartTemp);
    g.drawLine(xofLadderStartTemp, yofLadderStartTemp + pedalInterval,
        xofLadderStartTemp + lengthOfLadder, yofLadderStartTemp + pedalInterval);
    for (int i = 1; i <= numberOfPedals; i++) {
      g.drawLine(xofLadderStartTemp + i * pedalInterval, yofLadderStartTemp,
          xofLadderStartTemp + i * pedalInterval, yofLadderStartTemp + pedalInterval);
    }
  }
}
