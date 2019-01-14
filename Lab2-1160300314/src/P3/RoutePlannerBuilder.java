package P3;

/**
 * Build an instance of RoutePlanner from a file which is a bus
 * schedule contains the stops and buses.
 */
public interface RoutePlannerBuilder {
    /**
     * Build an instance of RoutePlanner from the file with
     * the max wait time limit.
     *
     * @param fileName     the name of the file which is the source bus schedule
     * @param maxWaitLimit The maximum waiting time
     * @return an instance of RoutePlanner
     */
    public RoutePlanner build(String fileName, int maxWaitLimit);
}
