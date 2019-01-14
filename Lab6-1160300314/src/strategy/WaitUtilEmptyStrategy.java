package strategy;

import basic.ConfigReader;
import basic.Params;
import java.util.List;
import ladder.Ladder;
import monkey.Monkey;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class WaitUtilEmptyStrategy implements CrossingRiverStrategy {

  private final List<Ladder> ladders;
  private final int lengthOfLadder;
  private final Monkey monkey;

  /*
  Rep invariant:
    ladders != null and ladders is not empty.
    lengthOfLadder is above 0.
    monkey != null.
  Abstraction function:
    represents a strategy of monkey will choose the empty ladder otherwise it will
    wait there.
  Safety from rep exposure:
    all fields are private and immutable.
  Thread safety arguments；
    there is no sharing data between threads.
   */

  /**
   * Monkey of this strategy will choose the empty ladder, otherwise it won't cross the river until
   * there is an empty ladder.
   *
   * @param ladders all ladders can be chosen.
   * @param monkey monkey of this strategy
   * @throws ConfigurationException if config is wrong.
   */
  public WaitUtilEmptyStrategy(List<Ladder> ladders, Monkey monkey) throws ConfigurationException {
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
  public int choose() {
    int velocity = monkey.getVelocity();
    int position = monkey.getPosition();
    int nowPosition = position % CrossingRiverStrategy.POSITION_OFFSET;
    int idOfLadder = position / CrossingRiverStrategy.POSITION_OFFSET;
    int newPosition = 0;
    if (position == 0) {
      for (Ladder ladder : ladders) {
        if (ladder.empty()) {
          newPosition = ladder.getId() * CrossingRiverStrategy.POSITION_OFFSET + 1;
          ladder.add(monkey);
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
