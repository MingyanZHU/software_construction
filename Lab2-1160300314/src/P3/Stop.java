package P3;
/**
 * Stops on the bus line.
 * */
public interface Stop {
    /**
     * Get the name of stop.
     * @return the name of the stop which is its label
     * */
    public String getName();

    /**
     * Get the latitude of the stop.
     * @return the latitude of the stop
     * */
    public double getLatitude();

    /**
     * Get the longitude of the stop
     * @return the longitude of the stop
     * */
    public double getLongitude();

    /**
     * Get the time when the bus will arrive.
     * @return the time when bus will arrive.
     * */
    public int getBusArriveTime();
}
