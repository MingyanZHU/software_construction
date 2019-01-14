package strategy;

public interface CrossingRiverStrategy {

  public static final int POSITION_OFFSET = 100;

  /**
   * The way of monkeys of this strategy to choose ladders.
   *
   * @return position of monkey with XYY, X is ladder number and YY is pedal number.
   * @throws InterruptedException if the monkey thread is interrupted.
   */
  public int choose() throws InterruptedException;
}
