package P3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * The instance of RoutePlannerBuilder.
 * */
public class RoutePlannerBuilderInstance implements RoutePlannerBuilder {

    @Override
    public RoutePlanner build(String fileName, int maxWaitLimit) {
        RoutePlannerInstance routePlanner = new RoutePlannerInstance();
        Map<String, List<Vertex>> verticesOfStop = new HashMap<>();
        File file = new File(fileName);
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            String string;
            String strings[];
            String busName = "";
            Vertex vertexForCache = null;
            try {
                while ((string = in.readLine()) != null) {
                    strings = string.split(",");
                    if (strings.length == 2) {
                        busName = strings[0];
                        vertexForCache = null;
                        continue;
                    }
                    if (strings.length == 4) {
                        Vertex vertex = new Vertex(strings[0], Double.valueOf(strings[1]), Double.valueOf(strings[2]),
                                Integer.valueOf(strings[3]), busName);
                        routePlanner.add(vertex);
                        if (verticesOfStop.containsKey(strings[0])) {
                            verticesOfStop.get(strings[0]).add(vertex);
                        } else {
                            List<Vertex> list = new ArrayList<>();
                            list.add(vertex);
                            verticesOfStop.put(strings[0], list);
                        }
                        if (vertexForCache != null) {
                            routePlanner.set(vertexForCache, vertex, vertex.getBusArriveTime() - vertexForCache.getBusArriveTime());
                        }
                        vertexForCache = vertex;
                    }
                }
                in.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (Map.Entry<String, List<Vertex>> entry : verticesOfStop.entrySet()) {
            entry.getValue().sort(new Comparator<Vertex>() {

                @Override
                public int compare(Vertex o1, Vertex o2) {
                    return o1.getBusArriveTime() - o2.getBusArriveTime();
                }
            });

            // Add extra edges between two stop with same name.
            for (int i = 0; i < entry.getValue().size() - 1; i++) {
                for (int j = i + 1; j < entry.getValue().size(); j++) {
                    if (entry.getValue().get(j).getBusArriveTime()
                            - entry.getValue().get(i).getBusArriveTime() <= maxWaitLimit) {
                        routePlanner.set(entry.getValue().get(i), entry.getValue().get(j),
                                entry.getValue().get(j).getBusArriveTime() - entry.getValue().get(i).getBusArriveTime());
                    } else {
                        break;
                    }
                }
            }
        }
        routePlanner.setUnmodified(verticesOfStop);
        return routePlanner;
    }
}
