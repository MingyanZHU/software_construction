package strategy;

import basic.ConfigReader;
import basic.Params;
import java.util.List;
import ladder.Ladder;
import monkey.Monkey;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class WaitUtilFirstPedalStrategy implements CrossingRiverStrategy {

  private final List<Ladder> ladders;
  private final int lengthOfLadder;
  private final Monkey monkey;

  /*
  Rep invariant:
    ladders != null and ladders is not empty.
    lengthOfLadder is above 0.
    monkey != null.
  Abstraction function:
    represents a strategy of monkey will choose the ladder with its first pedal is
    empty, otherwise monkey with this strategy will wait.
  Safety from rep exposure:
    all fields are private and immutable.
  Thread safety arguments；
    there is no sharing data between threads.
   */

  /**
   * Monkey of this strategy will choose the ladder with its first pedal is empty and all monkeys on
   * it have the same direction first.
   *
   * @param ladders all ladders can be chosen.
   * @param monkey monkey of this strategy.
   * @throws ConfigurationException if config is wrong.
   */
  public WaitUtilFirstPedalStrategy(List<Ladder> ladders, Monkey monkey)
      throws ConfigurationException {
    this.ladders = ladders;
    this.monkey = monkey;
    this.lengthOfLadder = ConfigReader.getConfig(Params.h);
    this.checkRep();
  }

  private void checkRep() {
    assert ladders != null && !ladders.isEmpty();
    assert lengthOfLadder > 0;
    assert monkey != null;
  }

  @Override
  public int choose() throws InterruptedException {
    int velocity = monkey.getVelocity();
    int position = monkey.getPosition();
    int nowPosition = position % CrossingRiverStrategy.POSITION_OFFSET;
    int idOfLadder = position / CrossingRiverStrategy.POSITION_OFFSET;
    int newPosition = 0;
    if (position == 0) {
      for (Ladder ladder : ladders) {
        if (ladder.empty()) {
          newPosition = ladder.getId() * CrossingRiverStrategy.POSITION_OFFSET + 1;
          ladder.put(monkey);
          break;
        } else if (ladder.peekFirst().getDirection().equals(monkey.getDirection())
            && ladder.peekLast().getPosition() % CrossingRiverStrategy.POSITION_OFFSET > 1) {
          newPosition = ladder.getId() * CrossingRiverStrategy.POSITION_OFFSET + 1;
          if (ladder.peekLast().getVelocity() < monkey.getVelocity()) {
            monkey.setVelocity(ladder.peekLast().getVelocity());
          }
          ladder.put(monkey);
          break;
        }
      }
    } else {
      nowPosition += velocity;
      newPosition = nowPosition + idOfLadder * CrossingRiverStrategy.POSITION_OFFSET;
      if (nowPosition > lengthOfLadder) {
        ladders.get(idOfLadder - 1).pollFirst();
      }
    }
    return newPosition;
  }
}
