package P3;

import static org.hamcrest.CoreMatchers.*; 

import static org.junit.Assert.*;

import org.junit.Test;

public class BusSegmentTest {
    
    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testBusSegmentMethod(){
        String src = "Source";
        String dest = "Destination";
        int time = 100;
        String busName = "Bus";
        
        BusSegment busSegment = new BusSegment(src, dest, time, busName);
        
        assertEquals(src, busSegment.getSource());
        assertEquals(dest, busSegment.getDestination());
        assertEquals(time, busSegment.getTimeSpent());
        assertEquals(busName, busSegment.getBusName());
        assertThat(busSegment.toString(), containsString("From " + src + " to " + dest));
        assertThat(busSegment.toString(), containsString("Take " + busName));
        assertThat(busSegment.toString(), containsString("used " + time));
    }
}
