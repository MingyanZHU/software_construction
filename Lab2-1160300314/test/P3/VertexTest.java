package P3;

import static org.hamcrest.CoreMatchers.*; 

import static org.junit.Assert.*;

import org.junit.Test;

public class VertexTest {
    private static final double doubleErrors = 0.001;
    
    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    // test strategy
    // All methods only have one input and one output.
    @Test
    public void testVertexMethod(){
        String name = "label";
        double latitude = 40;
        double longitude = 60;
        int arriveTime = 25000;
        String busName = "Bus";
        
        Vertex vertex = new Vertex(name, latitude, longitude, arriveTime, busName);
        
        assertEquals(name, vertex.getName());
        assertEquals(latitude, vertex.getLatitude(), doubleErrors);
        assertEquals(longitude, vertex.getLongitude(), doubleErrors);
        assertEquals(arriveTime, vertex.getBusArriveTime());
        assertEquals(busName, vertex.getBusName());
        assertThat(vertex.toString(), containsString(name));
        assertThat(vertex.toString(), containsString(busName));
        assertThat(vertex.toString(), containsString(String.valueOf(arriveTime)));
    }
}
