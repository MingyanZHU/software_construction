package P3;

import static org.hamcrest.CoreMatchers.*; 

import static org.junit.Assert.*;

import org.junit.Test;

public class MainTest {
    @Test
    public void testMain(){
        RoutePlannerBuilder routePlannerBuilderInstance = new RoutePlannerBuilderInstance();
        RoutePlanner routePlanner = routePlannerBuilderInstance.build("src/P3/txt/mySample.txt", 1200);
        
        String src = "A";
        String dest = "C";
        Stop source = routePlanner.findStopsBySubstring(src).get(0);
        Stop deStop = routePlanner.findStopsBySubstring(dest).get(0);
        int time = 25000;
        Itinerary itinerary = routePlanner.computeRoute(source, deStop, time);
        assertEquals(1250, itinerary.getTotalTimeSpent());
        
        assertThat(itinerary.getInstructions(), containsString("From A to C Take bus3 ,and used 45 second(s)"));
    }

    @Test
    public void testCannotWaitOverLimits(){
        // 不能连续等待两次 (除了最开始的等待外)
        RoutePlannerBuilder routePlannerBuilder = new RoutePlannerBuilderInstance();
        RoutePlanner routePlanner = routePlannerBuilder.build("src/P3/txt/chen.txt", 1200);

        String src = "A";
        String dest = "B";

        Stop source = routePlanner.findStopsBySubstring(src).get(0);
        Stop destination = routePlanner.findStopsBySubstring(dest).get(0);

        Itinerary itinerary = routePlanner.computeRoute(source, destination, 0);

        assertEquals(3000, itinerary.getTotalTimeSpent());
        assertThat(itinerary.getInstructions(), containsString("From A to B Take bus1 ,and used 2940 second(s)"));
    }

    @Test
    public void testCannotWaitOverLimits2(){
        RoutePlannerBuilder routePlannerBuilder = new RoutePlannerBuilderInstance();
        RoutePlanner routePlanner = routePlannerBuilder.build("src/P3/txt/wait.txt", 1200);

        String src = "A";
        String dest = "D";

        Stop source = routePlanner.findStopsBySubstring(src).get(0);
        Stop destination = routePlanner.findStopsBySubstring(dest).get(0);

        Itinerary itinerary = routePlanner.computeRoute(source, destination, 0);

        assertEquals(2100, itinerary.getTotalTimeSpent());

        assertThat(itinerary.getInstructions(), containsString("From E to D Take bus1 ,and used 1000 second(s)"));
    }
}
