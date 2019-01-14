package state;

import exception.IllegalStateOpeationException;

public class Locked extends UserState {

  public static final Locked instance = new Locked();

  private Locked() {
  }

  @Override
  public UserState unlock() {
    return Active.instance;
  }

  @Override
  public UserState lock() {
    return Locked.instance;
  }

  @Override
  public UserState deactive() throws IllegalStateOpeationException {
    throw new IllegalStateOpeationException("Person can not deactive on the Locked state!");
  }

  @Override
  public UserState active() throws IllegalStateOpeationException {
    throw new IllegalStateOpeationException("Person can not active on the Locked state!");
  }

  @Override
  public String toString() {
    return "UserState:Locked";
  }
}
