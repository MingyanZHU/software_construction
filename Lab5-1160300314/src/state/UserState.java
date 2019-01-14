package state;

import exception.IllegalStateOpeationException;

/**
 * The state of nodes of Social Network.
 *
 * @author Zhu Mingyan
 */
public abstract class UserState implements State {

  /**
   * Lock the user from the deactive or active state to locked state. And if locking in the locked
   * state it will still in the locked state.
   *
   * @return Locked State
   */
  public abstract UserState lock();

  /**
   * Deactive the user from active state to Deactive state. And if deactiving in the deactive state
   * it will still in the deactive state. Prohibited deactive in the locked state.
   *
   * @return Deactive State
   * @throws IllegalStateOpeationException if deactive on locked state.
   */
  public abstract UserState deactive() throws IllegalStateOpeationException;

  /**
   * Active the user from deactive state to Active state. And if active in the active state it will
   * still in the active state. Prohibited deactive in the locked state.
   *
   * @return Active State
   * @throws IllegalStateOpeationException if active on locked state.
   */
  public abstract UserState active() throws IllegalStateOpeationException;

  /**
   * Unlock the user from locked state to unlocked state. And if it in other states with unlocking
   * operation it will still in the state.
   *
   * @return Unlocked State
   */
  public abstract UserState unlock();
}
