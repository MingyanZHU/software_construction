package state;

public class Close extends NetworkState {
    public static final NetworkState instance = new Close();

    private Close() {
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
        return "NetworkState:Close";
    }
}
