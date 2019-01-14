package P3;
/**
 * The part of waiting or taking a bus in Itinerary.
 * */
public interface TripSegment {
    /**
     * Get the total time spent of the part of the itinerary.
     * @return time spent on the segment
     * */
    public int getTimeSpent();
}
