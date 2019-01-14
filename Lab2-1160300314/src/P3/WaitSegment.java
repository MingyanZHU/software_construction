package P3;

/**
 * The part of waiting in itinerary.
 */
public class WaitSegment implements TripSegment {
    // AF
    // Wait segment represents a part which the rider will wait at the
    // stop for a certain time.

    // RI
    // stopName is a non-null and non-empty string
    // timeSpent is above 0

    // Safety of Rep exposure
    // All fields are modified by keyword private and final.
    // Clients can not access the field.
    private final int timeSpent;
    private final String stopName;

    public WaitSegment(String stopName, int time) {
        this.stopName = stopName;
        timeSpent = time;
        checkRep();
    }

    private void checkRep() {
        assert stopName != null;
        assert stopName.length() != 0;
        assert timeSpent >= 0;
    }

    /**
     * Get the label of stop
     *
     * @return the label of stop
     */
    public String getName() {
        return this.stopName;
    }

    /**
     * Get the total time spent in the bus segment
     *
     * @return an integer of spent time
     */
    @Override
    public int getTimeSpent() {
        return this.timeSpent;
    }

    @Override
    public String toString() {
        return "Wait at " + this.stopName + " for " + String.valueOf(timeSpent) + " second(s)\n";
    }
}
