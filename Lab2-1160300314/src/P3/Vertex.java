package P3;
/**
 * An implement of Stop which is a vertex of Bus Graph.
 * */
public class Vertex implements Stop {
    // AF
    // The stop that contains a bus arriving time

    // RI
    // name and busName is non-null and non-empty string.
    // busArriveTime is above 0 and below 86400(total seconds of a day)
    // latitude is above -90.0 and below 90.0
    // longitude is above -180.0 and below 180.0

    // Safety of Rep exposure
    // All fields are modified by keyword private and final.Clients
    // can not access the field outside the class.
    private final String name;
    private final double latitude;
    private final double longitude;
    private final String busName;
    private final int busArriveTime;

    private static final int SECONDARILY = 86400;
    
    public Vertex(String name, double latitude, double longitude, int busArriveTime, String busName) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.busArriveTime = busArriveTime;
        this.busName = busName;
        checkRep();
    }

    private void checkRep(){
        assert name != null;
        assert busArriveTime >= 0 && busArriveTime < SECONDARILY;
        assert latitude >= -90.0 && latitude <= 90.0;
        assert longitude >= -180.0 && longitude <= 180.0;
        assert busName != null;
        assert name.length() != 0;
        assert busName.length() != 0;
    }
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public double getLatitude() {
        return this.latitude;
    }

    @Override
    public double getLongitude() {
        return this.longitude;
    }

    // Get the label of the bus will arrive.
    public String getBusName() {
        return this.busName;
    }

    @Override
    public int getBusArriveTime(){
        return this.busArriveTime;
    }
    
    @Override
    public String toString(){
        return name + " " + busName + " " + busArriveTime;
    }
}
