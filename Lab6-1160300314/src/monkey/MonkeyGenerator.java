package monkey;

import basic.ConfigReader;
import basic.Direction;
import basic.Params;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import ladder.Ladder;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import strategy.CrossingRiverStrategy;
import strategy.WaitUtilFirstPedalStrategy;

public class MonkeyGenerator {

  private final int numberOfMonkeys;
  private final int timeInterval;
  private final int monkeysPerTime;
  private final int maxVelocity;
  private static final Random RANDOM = new Random(System.currentTimeMillis());
  private static final int lengthOfDirection = Direction.values().length;
  private final List<Ladder> ladderList;
  private final Logger logger;

  private final MonkeyPane monkeyPane;

  /*
  Rep invariant:
    numberOfMonkeys is equal to Params.N in Config.
    timeInterval is equal to Params.t in config.
    monkeysPerTime is equal to Params.k in config.
    maxVelocity is equal to Params.MV in config.
    RANDOM is not null and lengthOfDirection is equal to 2.
    ladderList is not null.
    logger is not null and monkeyPane is not null.
  Abstraction function:
    represents a generator which can generating monkeys.
  Safety from rep exposure:
    all fields is private and immutable.
  Thread safety arguments:
    there is no sharing data between any threads.
   */

  /**
   * Can generating Params.N monkeys which are all wanna to cross river by ladders.
   *
   * @param ladderList all ladders monkeys can choose for strategy river.
   * @param monkeyPane pane displaying all monkeys.
   * @throws ConfigurationException if config is wrong.
   */
  public MonkeyGenerator(List<Ladder> ladderList, MonkeyPane monkeyPane)
      throws ConfigurationException {
    PropertyConfigurator.configure("log4j.properties");
    logger = Logger.getLogger(MonkeyGenerator.class);
    this.numberOfMonkeys = ConfigReader.getConfig(Params.N);
    this.timeInterval = ConfigReader.getConfig(Params.t);
    this.monkeysPerTime = ConfigReader.getConfig(Params.k);
    this.maxVelocity = ConfigReader.getConfig(Params.MV);
    this.ladderList = ladderList;
    this.monkeyPane = monkeyPane;
    this.checkRep();
  }

  private void checkRep() {
    try {
      int tempForTest = ConfigReader.getConfig(Params.N);
      assert numberOfMonkeys == tempForTest;
      tempForTest = ConfigReader.getConfig(Params.t);
      assert timeInterval == tempForTest;
      tempForTest = ConfigReader.getConfig(Params.k);
      assert monkeysPerTime == tempForTest;
      tempForTest = ConfigReader.getConfig(Params.MV);
      assert maxVelocity == tempForTest;
      assert ladderList != null;
      assert monkeyPane != null;
    } catch (ConfigurationException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Generating Params.N monkeys.
   *
   * @return list of all generating monkeys.
   * @throws InterruptedException if Current thread is interrupted
   * @throws ConfigurationException if Config is wrong.
   */
  public List<Monkey> monkeys()
      throws InterruptedException, ConfigurationException {
    List<Monkey> monkeyList = new ArrayList<>();
    int monkeyNumberNow = 0;
    int times = this.numberOfMonkeys / this.monkeysPerTime;
    for (int i = 1; i <= times; i++) {
      for (int j = 1; j <= monkeysPerTime; j++) {
        Monkey monkey = monkey((i * Monkey.ID_OFFSET + j));
        monkeyList.add(monkey);
        Thread thread = new Thread(monkey);
        thread.setPriority(monkey.getVelocity());
        // 此处用于测试Thread.setPriority是在Win10 10.0.17134可以使用的
        thread.start();
        monkeyPane.addMonkey(monkey);
        // 因为猴子的数量不会超过100 所以使用10000作为偏移量来生成猴子的ID
        // 最终每个猴子的ID都会表现为 y0xxx 其中y为第几次产生 xxx为这次产生时的排序
      }
      //      Thread.sleep(timeInterval * 1000);
      TimeUnit.SECONDS.sleep(timeInterval);
      // 可以更换函数 貌似有直接以second进行sleep的函数

      monkeyNumberNow += monkeysPerTime;
    }
    if (monkeyNumberNow + monkeysPerTime > numberOfMonkeys) {
      int leftMonkeys = numberOfMonkeys - monkeyNumberNow;
      for (int j = 1; j <= leftMonkeys; j++) {
        Monkey monkey = monkey(((times + 1) * Monkey.ID_OFFSET + j));
        monkeyList.add(monkey);
        Thread thread = new Thread(monkey);
        thread.setPriority(monkey.getVelocity());
        // 此处用于测试Thread.setPriority是在Win10 10.0.17134可以使用的
        thread.start();
        monkeyPane.addMonkey(monkey);
      }
    }
    assert monkeyList.size() == numberOfMonkeys;
    return monkeyList;
  }

  private Monkey monkey(int id) throws ConfigurationException {
    int direction = RANDOM.nextInt(lengthOfDirection);
    int velocity = RANDOM.nextInt(maxVelocity) + 1;
    // 压力测试2 所用
    //    int velocity = RANDOM.nextInt(2) == 1 ? maxVelocity : 1;
    Monkey monkey = new Monkey(id, Direction.values()[direction], velocity);
    CrossingRiverStrategy strategy = new WaitUtilFirstPedalStrategy(ladderList, monkey);

    //    int chosen = RANDOM.nextInt(3);
    //    switch (chosen) {
    //      case 0:
    //        strategy = new WaitUtilFirstPedalStrategy(ladderList, monkey);
    //        break;
    //      case 1:
    //        strategy = new WaitUtilEmptyStrategy(ladderList, monkey);
    //        break;
    //      case 2:
    //        strategy = new ChooseCrossingFastestStrategy(ladderList, monkey);
    //        break;
    //      default:
    //        assert false;
    //    }
    monkey.setStrategy(strategy);
    logger.debug(monkey.toString());
    return monkey;
  }
}
