package P2;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import P1.graph.ConcreteEdgesGraph;
 
public class FriendshipGraph extends ConcreteEdgesGraph<Person>{
    private final Map<Person, Integer> distance = new HashMap<>();
    private final Map<Person, Boolean> visited = new HashMap<>();
    /**
     * Add vertexes to graph or we can say add person into social network. If
     * person.name has been used before, it will exit this program with return
     * -1.
     * 
     * @param  person, an instance of class Person of "social network".
     */
    public void addVertex(Person person) {
        if(!add(person)) {
            System.out.println(person.getName() + " has been used!");
            System.exit(-1);
        }
        distance.put(person, 0);
        visited.put(person, false);
    }

    /**
     * Add edges to graph or we can say add interaction into social network. If
     * someone's name has not been added to the graph, it can not add the edge
     * into social network.
     * 
     * @param person1 source vertex of the edge.
     * @param person2 target vertex of the edge.
     */
    public void addEdge(Person person1, Person person2) {
        if(person1.equals(person2)) {
            return;
        }
        set(person1, person2, 1);
    }

    /**
     * Get the shortest distance between two persons in the social network.
     * 
     * @param  person1 Source of the two people that needed to know the
     *            distance between them.
     * @param  person2 Target of the two people that needed to know the
     *            distance between them.
     * @return the shortest distance between the two people.
     */
    public int getDistance(Person person1, Person person2) {
        if(person1.equals(person2)){
            return 0;
        }
        Queue<Person> queue = new LinkedList<>();
        for (Person person : vertices()) {
            distance.put(person, 0);
            visited.put(person, false);
        }
        int answer = -1;
        visited.put(person1, true);
        queue.offer(person1);
        Person person;
        while(!queue.isEmpty()) {
            person = queue.poll();
            Set<Person> adjacent = targets(person).keySet();
            for(Person person3 : adjacent) {
                if(!visited.get(person3)) {
                    visited.put(person3, true);
                    queue.offer(person3);
                    distance.put(person3, distance.get(person) + 1);
                    if(person3.equals(person2)) {
                        answer = distance.get(person3);
                        break;
                    }
                }
            }
        }
        return answer;
    }

//    public static void main(String[] args) {
//        FriendshipGraph graph = new FriendshipGraph();
//        Person rachel = new Person("Rachel");
//        Person ross = new Person("Ross");
//        Person ben = new Person("Ben");
//        Person kramer = new Person("Kramer");
//        graph.addVertex(rachel);
//        graph.addVertex(ross);
//        graph.addVertex(ben);
//        graph.addVertex(kramer);
//        graph.addEdge(rachel, ross);
//        graph.addEdge(ross, rachel);
//        graph.addEdge(ross, ben);
//        graph.addEdge(ben, ross);
//        System.out.println(graph.getDistance(rachel, ross));
//        System.out.println(graph.getDistance(rachel, ben));
//        System.out.println(graph.getDistance(rachel, rachel));
//        System.out.println(graph.getDistance(rachel, kramer));
//    }
}
