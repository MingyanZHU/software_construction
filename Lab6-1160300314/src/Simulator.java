import display.GuiDisplay;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import ladder.Ladder;
import ladder.LadderGenerator;
import monkey.Monkey;
import monkey.MonkeyGenerator;
import org.apache.commons.configuration2.ex.ConfigurationException;
import profiler.ThroughputProfiler;

public class Simulator {

  /**
   * 模拟器运行.
   */
  public static void main(String[] args)
      throws InterruptedException, ConfigurationException, IOException {
    int numberOfBasicThread;
    PrintStream systemOut = System.out;
    PrintStream printStream = new PrintStream(
        new FileOutputStream("./log/monkey.log"), true, "UTF8");
    System.setOut(printStream);
    GuiDisplay guiDisplayMonkey = new GuiDisplay();
    numberOfBasicThread = Thread.activeCount();
    LadderGenerator ladderGenerator = new LadderGenerator();
    List<Ladder> ladders = ladderGenerator.ladders();
    MonkeyGenerator monkeyGenerator
        = new MonkeyGenerator(ladders, guiDisplayMonkey.getMonkeyPane());
    List<Monkey> monkeys = monkeyGenerator.monkeys();
    while (true) {
      Thread.sleep(1000);
      if (Thread.activeCount() == numberOfBasicThread) {
        System.setOut(systemOut);
        for (Monkey monkey : monkeys) {
          System.out.println(monkey);
        }
        System.out.println(ThroughputProfiler.computeRecord());
        break;
      }
    }
  }
}
