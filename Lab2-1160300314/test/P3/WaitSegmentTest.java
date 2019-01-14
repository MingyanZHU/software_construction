package P3;

import static org.hamcrest.CoreMatchers.*; 

import static org.junit.Assert.*;

import org.junit.Test;

public class WaitSegmentTest {
    
    @Test(expected = AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testWaitSegmentMethods(){
        int time = 100;
        String stopName = "Stop";
        WaitSegment waitSegment = new WaitSegment(stopName, time);
        
        assertEquals(time, waitSegment.getTimeSpent());
        assertEquals(stopName, waitSegment.getName());
        assertThat(waitSegment.toString(), containsString("Wait at " + stopName));
        assertThat(waitSegment.toString(), containsString("for " + time));
    }
}
