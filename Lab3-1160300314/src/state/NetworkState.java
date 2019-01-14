package state;

/**
 * The state of nodes of network state.
 *
 * @author Zhu Mingyan
 */
public abstract class NetworkState implements State {
    /**
     * Open the network equipment from close state to open state.
     * And if open in the open state it will still in the open state.
     *
     * @return Open State
     */
    public abstract NetworkState open();

    /**
     * Close the Network equipment from open state to close state.
     * And if close in the close state it will still in the close state
     *
     * @return Close State
     */
    public abstract NetworkState close();
}
