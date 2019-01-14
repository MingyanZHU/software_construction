package monkey;

import basic.ConfigReader;
import basic.Direction;
import basic.Params;
import java.awt.Color;
import java.awt.Graphics;
import java.util.concurrent.TimeUnit;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.log4j.Logger;
import strategy.CrossingRiverStrategy;

public class Monkey implements Runnable {

  public static final int ID_OFFSET = 10000;
  private final int numberOfPedals;
  private final int maxVelocity;
  private final int id;
  private final Direction direction;
  private int velocity;
  private CrossingRiverStrategy strategy;
  private int position;
  private int age;
  private final Logger logger;
  private final int birth;

  private int xposition;
  private int yposition;

  /*
  Rep invariant:
    id consists of 5 digital the first one is id of generation and the other four is number
    in that generation.
    direction is Direction.R2L or Direction L2R.
    velocity is between 1 and Params.MV in config.
    strategy is not null.
    position consists of 3 digital. The first one is the id of ladder and other two is the number
    of pedals on that ladder.
    age is total number of seconds after this monkey generated should be above 0.
    logger is not null.
    birth is the time after the main thread start and this monkey is generated should be above 0.
    xposition is the number of pedal which the monkey on should be between 0 and Params.h.
    yposition is the id id of ladder which the monkey on should be between 0 and Params.n.
  Abstraction function:
    represents a monkey which wanna to cross river.
  Safety from rep exposure:
    all fields are private. all fields except strategy is immutable.
    And the setter method of strategy is pack-private.
  Thread safety arguments:
    there is no sharing data between any threads and all fields is thread-safe.
   */

  /**
   * Monkeys wanna to strategy the river by ladders as soon as possible.
   *
   * @param id the ID consists of 5 or more  digital. The first one is number of generation, and the
   *        left is its order in this generation.
   * @param direction R2L or L2R
   * @param velocity monkey velocity when it strategy river cannot over Params.MV in config.
   * @throws ConfigurationException if config is wrong.
   */
  public Monkey(int id, Direction direction, int velocity) throws ConfigurationException {
    //    PropertyConfigurator.configure("log4j.properties");
    logger = Logger.getLogger(Monkey.class);
    this.id = id;
    this.direction = direction;
    this.velocity = velocity;
    setPosition(0);
    this.age = (id / ID_OFFSET - 1) * ConfigReader.getConfig(Params.t);
    this.birth = age;

    this.maxVelocity = ConfigReader.getConfig(Params.MV);
    this.numberOfPedals = ConfigReader.getConfig(Params.h);
    this.checkRep();
  }

  private void checkRep() {
    try {
      final int numberOfLadders = ConfigReader.getConfig(Params.n);
      final int numberOfPedals = ConfigReader.getConfig(Params.h);
      final int maxVelocity = ConfigReader.getConfig(Params.MV);
      assert id > Monkey.ID_OFFSET;
      assert direction.equals(Direction.L2R) || direction.equals(Direction.R2L);
      assert velocity >= 1 && velocity <= maxVelocity;
      assert position == 0 || position >= CrossingRiverStrategy.POSITION_OFFSET;
      assert age >= 0;
      assert logger != null;
      assert birth >= 0;
      assert xposition == 0 || (xposition >= 1 && xposition <= numberOfPedals);
      assert yposition == 0 || (yposition >= 1 && yposition <= numberOfLadders);
    } catch (ConfigurationException e) {
      throw new RuntimeException(e);
    }
  }

  public int getId() {
    return id;
  }

  public Direction getDirection() {
    return direction;
  }

  public int getVelocity() {
    return velocity;
  }

  public CrossingRiverStrategy getStrategy() {
    return strategy;
  }

  public int getPosition() {
    return position;
  }

  public int getAge() {
    return age;
  }

  /**
   * Set velocity of monkey.
   * @param velocity should be between 1 and Params.MV in config.
   */
  public void setVelocity(int velocity) {
    if (velocity <= 0 || velocity > maxVelocity) {
      throw new RuntimeException(velocity + " is less than 0 or more than max velocity");
    }
    this.velocity = velocity;
    this.checkRep();
  }

  void setStrategy(CrossingRiverStrategy strategy) {
    if (strategy == null) {
      throw new RuntimeException("Strategy is null!");
    }
    this.strategy = strategy;
    this.checkRep();
  }

  private void setPosition(int position) {
    this.position = position;
    xposition = position % CrossingRiverStrategy.POSITION_OFFSET;
    yposition = position / CrossingRiverStrategy.POSITION_OFFSET;
  }

  /**
   * Draw a solid circle representing the current monkey.
   *
   * @param graphics Graphics instance.
   */
  public void paint(Graphics graphics) {
    switch (this.direction) {
      case L2R:
        graphics.setColor(Color.RED);
        graphics.fillOval((xposition + 1) * MonkeyPane.pedalInterval,
            yposition * MonkeyPane.ladderInterval,
            MonkeyPane.radium, MonkeyPane.radium);
        break;
      case R2L:
        graphics.setColor(Color.BLACK);
        graphics.fillOval(((numberOfPedals + 1 - xposition) + 1) * MonkeyPane.pedalInterval,
            yposition * MonkeyPane.ladderInterval, MonkeyPane.radium, MonkeyPane.radium);
        break;
      default:
        assert false;
    }
  }

  @Override
  public void run() {
    while (true) {
      try {
        this.setPosition(strategy.choose());
        if (xposition > numberOfPedals) {
          // TODO magic number
          logger.info("Monkey" + id + " arrived at " + yposition + "th ladder "
              + direction.name().charAt(2) + " at " + age + " after " + (age - birth));
          return;
        } else if (xposition == 0) {
          logger.info("Monkey" + id + " stay at "
              + direction.name().charAt(0) + " at " + age + " after " + (age - birth));
        } else {
          logger.debug("Monkey" + id + " come to " + xposition + "th pedals of " + yposition
              + "th ladder " + direction.name() + " at " + age + " after " + (age - birth));
        }
        TimeUnit.SECONDS.sleep(1);
        age += 1;
      } catch (InterruptedException e) {
        logger.error("Monkey " + id + " is interrupted");
        return;
      }
    }
  }

  @Override
  public String toString() {
    return "Monkey = <" + id + ", " + direction.toString() + ", " + velocity + ", "
        + strategy.getClass().getSimpleName() + ">";
  }
}
