package P3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import P1.graph.ConcreteEdgesGraph;

/**
 * A mutable subclass of ConcreteEdgesGraph and implements of RoutePlanner.
 */
public class RoutePlannerInstance extends ConcreteEdgesGraph<Vertex> implements RoutePlanner {
    // AF
    // Represents a route planner which can compute the route and search stops.

    // RI
    // All fields are not null

    // Safety for Rep exposure
    // All fields are modified by keyword final and private.
    // The mutable classes in fields when they are needed to return
    // by defensive copying.

    private Map<String, List<Vertex>> map;
    private Map<Vertex, Boolean> visited = new HashMap<>();
    private Map<Vertex, Integer> distance = new HashMap<>();
    private Map<Vertex, Vertex> path = new HashMap<>();
    private List<Vertex> possibleVertices = new ArrayList<>();

    private static final int SECONDARILY = 24 * 3600;
    private static final int INF = 0x3f3f3f3f;

    private void checkRep() {
        assert map != null;
        assert visited != null;
        assert distance != null;
        assert path != null;
        assert possibleVertices != null;
    }

    protected void setUnmodified(Map<String, List<Vertex>> map) {
        this.map = Collections.unmodifiableMap(map);
        checkRep();
    }

    @Override
    public List<Stop> findStopsBySubstring(String search) {
        List<Stop> anStops = new ArrayList<>();
        for (Map.Entry<String, List<Vertex>> entry : map.entrySet()) {
            if (entry.getKey().contains(search)) {
                anStops.addAll(entry.getValue());
            }
        }
        return anStops;
    }

    @Override
    public Itinerary computeRoute(Stop src, Stop dest, int time) {
        for (Map.Entry<String, List<Vertex>> entry : map.entrySet()) {
            for (Vertex vertex : entry.getValue()) {
                // 添加所有可能的经过的节点在possibleVertices里
                if (vertex.getBusArriveTime() >= time) {
                    possibleVertices.add(vertex);
                }
            }
        }
        List<Stop> sources = findStopsBySubstring(src.getName());
        Itinerary itineraryAnswer = new Itinerary(src, dest, SECONDARILY);
        for (Stop stop : sources) {
            // 将所有图中顶点名字为出发地名字中能够作为Dijkstra的进行搜索
            if (stop.getBusArriveTime() <= time + 1200 && (stop.getBusArriveTime() >= time)) {
                ini(stop);
                Itinerary itineraryTemp;
                itineraryTemp = dijkstra((Vertex) stop, dest, time, ((Vertex) stop).getBusArriveTime() - time);
                if (itineraryAnswer.getEndTime() > itineraryTemp.getEndTime()) {
                    // 如果当前Dijkstra的得到的结果更好
                    itineraryAnswer = itineraryTemp;
                }
            }
        }
        if (itineraryAnswer.getStartTime() >= SECONDARILY || itineraryAnswer.getTotalTimeSpent() >= SECONDARILY) {
            // 如果出发时间为超过午夜或者总共用时超过24小时时间(在初始等待和第一次等待的时间总和超过1200s) 则没有合法的路径存在
            return null;
        } else {
            return itineraryAnswer;
        }
    }

    /**
     * Initialize the auxiliary data structure before dijkstra.
     *
     * @param source the source stop of dijkstra.
     */
    private void ini(Stop source) {
        Map<Vertex, Integer> targetMap = targets((Vertex) source);
        for (Vertex vertex : possibleVertices) {
            visited.put(vertex, false);
            distance.put(vertex, targetMap.getOrDefault(vertex, INF));
            path.put(vertex, (Vertex) source);
        }
    }

    /**
     * Get the shortest distance between src and destination leaving at time with the limit of maxWaitLimit
     *
     * @param src           the source vertex of dijkstra
     * @param dest          the destination of the itinerary.
     * @param time          the time when the rider will leave
     * @param startWaitTime the wait time before the source.
     * @return an itinerary of the shortest time spent and if there is no such itinerary the returning itinerary
     * will have a wait segment spent over a day.
     */
    private Itinerary dijkstra(Vertex src, Stop dest, int time, int startWaitTime) {
        Itinerary itinerary = new Itinerary(src, dest, time);
        if (startWaitTime > 0 && startWaitTime <= 1200) {
            itinerary.addTripSegment(new WaitSegment(src.getName(), startWaitTime));
        }
        visited.put(src, true);
        distance.put(src, 0);
        for (int i = 0; i < possibleVertices.size(); i++) {
            int t = INF;
            Vertex vertex = null;
            for (Vertex possibleVertex : possibleVertices) {
                if (!possibleVertex.equals(src) && !visited.get(possibleVertex) && distance.get(possibleVertex) < t) {
                    t = distance.get(possibleVertex);
                    vertex = possibleVertex;
                }
            }
            visited.put(vertex, true);

            Map<Vertex, Integer> mapNow = targets(vertex);
            for (int j = 0; possibleVertices.size() > j; j++) {
                if (!possibleVertices.get(j).equals(src) && !visited.get(possibleVertices.get(j))) {
                    if (mapNow.containsKey(possibleVertices.get(j))) {
                        int sum = mapNow.get(possibleVertices.get(j)) + distance.get(vertex);
                        if ((!path.get(vertex).getName().equals(possibleVertices.get(j).getName())
                                || !vertex.getName().equals(path.get(vertex).getName()))
                                // 连续的三个顶点不能在最短路上同名 即不能连续等待
                                && sum < distance.get(possibleVertices.get(j))) {
                            distance.put(possibleVertices.get(j), sum);
                            path.put(possibleVertices.get(j), vertex);
                        }
                    }
                }
            }
        }

        Stack<TripSegment> stack = new Stack<>();
        Vertex destVertex = null;
        for (Vertex possibleVertex : possibleVertices) {
            if (destVertex == null && dest.getName().equals(possibleVertex.getName())) {
                destVertex = possibleVertex;
            }
            if (destVertex != null && destVertex.getName().equals(possibleVertex.getName())
                    && distance.get(destVertex) > distance.get(possibleVertex)) {
                destVertex = possibleVertex;
            }
        }
        if (distance.get(destVertex) < INF) {
            // 如果最终的目的顶点是可达的
            while (!destVertex.equals(src)) {
                if (path.get(destVertex).getName().equals(destVertex.getName())) {
                    stack.push(new WaitSegment(destVertex.getName(),
                            destVertex.getBusArriveTime() - path.get(destVertex).getBusArriveTime()));
                } else {
                    stack.push(new BusSegment(path.get(destVertex).getName(), destVertex.getName(),
                            destVertex.getBusArriveTime() - path.get(destVertex).getBusArriveTime(),
                            path.get(destVertex).getBusName()));
                }
                destVertex = path.get(destVertex);
            }
            while (!stack.isEmpty()) {
                itinerary.addTripSegment(stack.pop());
            }
        } else {
            // 如果不可达 则人为增加一个等待时长为24小时的waitSegment 表示无法到达
            itinerary.addTripSegment(new WaitSegment(destVertex.getName(), SECONDARILY));
        }
        return itinerary;
    }
}
