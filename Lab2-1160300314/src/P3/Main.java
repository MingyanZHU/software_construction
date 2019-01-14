package P3;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final int SECONDSDAILY = 86400;

    public static void main(String[] args) {

        RoutePlannerBuilderInstance routePlannerBuilderInstance = new RoutePlannerBuilderInstance();
        RoutePlanner routePlanner = routePlannerBuilderInstance.build("src/P3/txt/chen.txt", 1200);
//        System.out.println(routePlanner.toString());

        Scanner in = new Scanner(System.in);

        System.out.println("Please input the source:");
        List<Stop> sources = routePlanner.findStopsBySubstring(in.next());
        if (sources.isEmpty()) {
            System.out.println("This is no such Source Stop!");
            return;
        }
        Stop sourceStop = sources.get(0);

        System.out.println("Please input the target");
        List<Stop> destinations = routePlanner.findStopsBySubstring(in.next());
        if (destinations.isEmpty()) {
            System.out.println("This is no such Destination Stop!");
            return;
        }
        Stop destinationStop = destinations.get(0);

        System.out.println("Please input the time you leave:");
        int time = in.nextInt();
        if (time < 0 || time > SECONDSDAILY) {
            System.out.println("Time is early than 0:00 or later than 23:59:59!");
            return;
        }

        Itinerary itinerary = routePlanner.computeRoute(sourceStop, destinationStop, time);
        if (itinerary == null) {
            System.out.println("Sorry, there is no such way with limits of 1200 seconds waiting.");
        } else {
            System.out.println(itinerary.toString());
        }
        in.close();
    }
}
