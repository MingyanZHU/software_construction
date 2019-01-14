package P3;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe the journey from source to destination with waiting at stop
 * or taking a bus from somewhere to somewhere.
 */
public class Itinerary {
    // AF
    // Represent a series of processes from the source to the destination.

    // RI
    // name is non-empty and non-null string.
    // startTime is above 0 and below 86400.
    // endTime is above 0.
    // tripSegments, startLocation and endLocation is non-null.

    // Safety of Rep exposure
    // All fields are modified by keyword private and final.Clients
    // can not access the field outside the class.
    // All mutable fields would not return.
    private final String name;
    private final int startTime;
    private int endTime;
    private int waitTime;
    private final Stop startLocation;
    private final Stop endLocation;
    private String instructions;
    private final List<TripSegment> tripSegments;
    private static final int SECONDSOFDAY = 86400;

    public Itinerary(Stop src, Stop dest, int time) {
        this.name = "From " + src.getName() + " To " + dest.getName() + " At " + time;
        this.startLocation = src;
        this.endLocation = dest;
        this.startTime = time;
        this.endTime = time;
        this.waitTime = 0;
        this.instructions = name;
        tripSegments = new ArrayList<>();
        checkRep();
    }

    private void checkRep() {
        assert name != null;
        assert name.length() != 0;
        assert startTime >= 0 && startTime <= SECONDSOFDAY;
        assert endTime >= 0;
        assert startLocation != null;
        assert endLocation != null;
        assert instructions != null;
        assert tripSegments != null;
    }

    /**
     * Add trip segment to the itinerary.
     *
     * @param tripSegment the part of the itinerary.
     */
    public void addTripSegment(TripSegment tripSegment) {
        if (tripSegment.getClass() == WaitSegment.class) {
            waitTime += tripSegment.getTimeSpent();
            if (tripSegments.size() > 0 && tripSegments.get(tripSegments.size() - 1).getClass() == WaitSegment.class) {
                // 用于合并相同的 waitSegment 仅仅出现在起点的等待情况
                tripSegments.set(tripSegments.size() - 1, new WaitSegment(((WaitSegment) tripSegment).getName(),
                        tripSegment.getTimeSpent() + tripSegments.get(tripSegments.size() - 1).getTimeSpent()));
                if (tripSegments.get(tripSegments.size() - 1).getTimeSpent() > 1200) {
                    endTime += SECONDSOFDAY;
                }
            } else {
                tripSegments.add(tripSegment);
            }
        } else {
            tripSegments.add(tripSegment);
        }
        endTime += tripSegment.getTimeSpent();
    }


    /**
     * Get the label of itinerary
     *
     * @return the label of the itinerary
     * */
    public String getName() {
        return this.name;
    }

    /**
     * Get the time when the person left.
     *
     * @return the time when the person left
     * */
    public int getStartTime() {
        return this.startTime;
    }

    // If end time is above 86400 seconds, it means that the itinerary is uesless.
    public int getEndTime() {
        return this.endTime;
    }

    public int getTotalTimeSpent() {
        return this.endTime - this.startTime;
    }

    public int getWaitTime() {
        return this.waitTime;
    }

    public Stop getStartLocation() {
        return this.startLocation;
    }

    public Stop getEndLocation() {
        return this.endLocation;
    }

    public String getInstructions() {
        StringBuilder stringBuilder = new StringBuilder();
        for (TripSegment tripSegment : tripSegments) {
            stringBuilder.append(tripSegment.toString());
        }
        this.instructions += stringBuilder.toString();
        return this.instructions;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.name).append("\n");
        stringBuilder.append("Start from ").append(this.startLocation.getName()).append("\n");
        stringBuilder.append("Destination is ").append(this.endLocation.getName()).append("\n");
        stringBuilder.append("Total time used ").append(this.endTime - startTime).append(" second(s)\n");
        for (TripSegment tripSegment : tripSegments) {
            stringBuilder.append(tripSegment.toString());
        }
        return stringBuilder.toString();
    }
}
