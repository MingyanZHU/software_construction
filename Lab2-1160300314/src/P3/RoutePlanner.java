package P3;

import java.util.List;

/**
 * Route planner for designing a route from source to destination.
 */
public interface RoutePlanner {
    /**
     * Search the stops of which name contains the string for searching.
     *
     * @param search the string for searching
     * @return a list of stops whose name has the strings.
     */
    public List<Stop> findStopsBySubstring(String search);

    /**
     * Compute a route plan from source stop to the destination at time.
     *
     * @param src the source stop of the journey
     * @param dest the destination stop of the journey
     * @param time the time when the rider will leave
     * @return an itinerary of the route plan
     */
    public Itinerary computeRoute(Stop src, Stop dest, int time);
}
