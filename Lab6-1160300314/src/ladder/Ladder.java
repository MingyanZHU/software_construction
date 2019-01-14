package ladder;

import basic.ConfigReader;
import basic.Params;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import monkey.Monkey;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class Ladder {

  private final int length;
  private final BlockingDeque<Monkey> queue;
  private final int id;

  /*
  Rep invariant:
    length = Params.h in config and queue is not null.
    id is between 1 and Params.n.
  Abstraction function:
    represents a ladder with monkeys in a queue on it.
  Safety from rep exposure:
    all fields are private and immutable.
  Thread safety arguments:
    queue is sharing between threads and BlockingQueue is thread safe.
    And all methods of queue is modified by lock.
   */

  /**
   * Ladders over river which monkey can cross river by them.
   *
   * @param id ID of ladder from 1 to Params.h in config.
   * @param length the number of pedals in one ladder.
   */
  public Ladder(int id, int length) {
    this.id = id;
    this.length = length;
    this.queue = new LinkedBlockingDeque<>();
    this.checkRep();
  }

  private void checkRep() {
    try {
      int numberOfLadders = ConfigReader.getConfig(Params.n);
      int numberOfPedals = ConfigReader.getConfig(Params.h);
      assert id >= 1 && id <= numberOfLadders;
      assert length == numberOfPedals;
      assert queue != null;
    } catch (ConfigurationException e) {
      throw new RuntimeException(e);
    }
  }

  public synchronized boolean empty() {
    return this.queue.isEmpty();
  }

  public synchronized void put(Monkey monkey) throws InterruptedException {
    this.queue.put(monkey);
  }

  public synchronized Monkey pollFirst() {
    return this.queue.pollFirst();
  }

  public synchronized Monkey peekFirst() {
    return this.queue.peekFirst();
  }

  public synchronized Monkey peekLast() {
    return this.queue.peekLast();
  }

  public synchronized void add(Monkey monkey) {
    this.queue.addLast(monkey);
  }

  public int getId() {
    return id;
  }

  @Override
  public String toString() {
    return "Ladder" + id + " with " + length + " pedals\n";
  }
}
