package state;

public class Open extends NetworkState {

  public static final NetworkState instance = new Open();

  private Open() {
  }

  @Override
  public NetworkState open() {
    return Open.instance;
  }

  @Override
  public NetworkState close() {
    return Close.instance;
  }

  @Override
  public String toString() {
    return "NetworkState:Open";
  }
}

