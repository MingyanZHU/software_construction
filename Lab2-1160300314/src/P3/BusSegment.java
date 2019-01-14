package P3;

/**
 * The part of taking a bus in Itinerary.
 */
public class BusSegment implements TripSegment {
    // AF
    // Bus segment represents a part which is from source to
    // destination spent seconds by taking a bus of itinerary.

    // RI
    // timeSpent is above 0 and below the total seconds of a day.
    // busName, source and destination is non-null and non-empty string
    // source and destination are different stops

    // Safety of Rep exposure
    // All fields are modified by keyword private and final.Clients
    // can not access the field outside the class.
    private final int timeSpent;
    private final String busName;
    private final String source;
    private final String destination;

    /**
     * Constructor
     *
     * @param src         the source stop of the bus segment
     * @param destination the destination stop of the bus segment
     * @param timeSpent   the total time spent in the bus segment
     */
    public BusSegment(String src, String destination, int timeSpent, String busName) {
        this.source = src;
        this.destination = destination;
        this.timeSpent = timeSpent;
        this.busName = busName;
        checkRep();
    }

    private void checkRep() {
        assert source != null;
        assert destination != null;
        assert source.length() != 0;
        assert destination.length() != 0;
        assert timeSpent >= 0 && timeSpent <= 24 * 3600;
        assert busName != null;
        assert busName.length() != 0;
    }

    /**
     * Get the label of source stop
     *
     * @return the label of source stop
     */
    public String getSource() {
        return this.source;
    }

    /**
     * Get the label of destination stop
     *
     * @return the label of destination stop
     */
    public String getDestination() {
        return this.destination;
    }

    /**
     * Get the total time spent in the bus segment
     *
     * @return an integer of spent time
     */
    public int getTimeSpent() {
        return this.timeSpent;
    }

    /**
     * Get the name of the bus the rider will take.
     *
     * @return the label of bus
     */
    public String getBusName() {
        return this.busName;
    }

    @Override
    public String toString() {
        return "From " + source + " to " + destination
                + " Take " + busName + " ,and used "
                + String.valueOf(timeSpent)
                + " second(s)\n";
    }
}
