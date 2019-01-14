package P3;

import static org.hamcrest.CoreMatchers.*; 

import static org.junit.Assert.*;

import org.junit.Test;

public class ItineraryTest {
    
    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGetter(){
        String busName = "Bus";

        String stopName = "StopTime";
        int time = 1000;
        WaitSegment waitSegment = new WaitSegment(stopName, time);
        
        String stopName1 = "StopTime1";
        int waitTime = 100;
        WaitSegment waitSegment2 = new WaitSegment(stopName1, waitTime);
        
        String stopName2 = "StopTime2";
        int spentTime = 500;
        BusSegment busSegment = new BusSegment(stopName1, stopName2, spentTime, busName);
        
        String name = "label";
        double latitude = 40;
        double longitude = 60;
        int arriveTime = 25000;
        Stop src = new Vertex(name, latitude, longitude, arriveTime, busName);
        
        String nameDest = "LABEL";
        Stop dest = new Vertex(nameDest, latitude+1, longitude+1, arriveTime, busName.toUpperCase());
        Itinerary itinerary = new Itinerary(src, dest, arriveTime);
        
        assertEquals(src, itinerary.getStartLocation());
        assertEquals(dest, itinerary.getEndLocation());
        assertEquals(arriveTime, itinerary.getStartTime());
        assertEquals(arriveTime, itinerary.getEndTime());
        assertEquals(0, itinerary.getTotalTimeSpent());
        assertEquals(0, itinerary.getWaitTime());
        assertEquals(itinerary.getName(), itinerary.getInstructions());
        
        itinerary.addTripSegment(waitSegment);
        
        assertEquals(arriveTime, itinerary.getStartTime());
        assertEquals(arriveTime+time, itinerary.getEndTime());
        assertEquals(time, itinerary.getWaitTime());
        assertEquals(time, itinerary.getTotalTimeSpent());
        assertThat(itinerary.getInstructions(), containsString(waitSegment.toString()));
        
        itinerary.addTripSegment(waitSegment2);
        
        assertEquals(time+waitTime, itinerary.getWaitTime());
        assertEquals(time + waitTime, itinerary.getTotalTimeSpent());
        assertThat(itinerary.getInstructions(), containsString(String.valueOf(time+waitTime) + " second(s)"));
        
        itinerary.addTripSegment(busSegment);
        
        assertEquals(time+waitTime, itinerary.getWaitTime());
        assertEquals(time+waitTime+spentTime, itinerary.getTotalTimeSpent());
        assertThat(itinerary.getInstructions(), containsString(busSegment.toString()));
        
        assertThat(itinerary.toString(), containsString("Start from " + name));
        assertThat(itinerary.toString(), containsString("Destination is " + nameDest));
    }
}
