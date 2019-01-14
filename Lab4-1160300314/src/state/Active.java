package state;

public class Active extends UserState {

  public static final Active instance = new Active();

  private Active() {
  }

  @Override
  public UserState deactive() {
    return Deactive.instance;
  }

  @Override
  public UserState active() {
    return Active.instance;
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
  public String toString() {
    return "UserState:Active";
  }
}
