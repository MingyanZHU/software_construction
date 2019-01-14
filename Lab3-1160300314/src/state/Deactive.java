package state;

public class Deactive extends UserState {
    public static final Deactive instance = new Deactive();

    private Deactive() {
    }

    @Override
    public UserState lock() {
        return Locked.instance;
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
        return Deactive.instance;
    }

    @Override
    public String toString() {
        return "UserState:Deactive";
    }
}
