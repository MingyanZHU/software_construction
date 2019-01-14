/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>
 * PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteVerticesGraph<L> implements Graph<L> {

    private final List<Vertex<L>> vertices = new ArrayList<>();

    // Abstraction function:
    // TODO
    // Represents a set of weighted directed edges from a source 
    // vertex to a target vertex that is different from source in a directed graph.
    
    // Representation invariant:
    // TODO
    // Each vertex is different from each other.
    
    // Safety from rep exposure:
    // vertices is modified by keyword private and final.
    // so it can't be accessed from outside the class or be reassigned.

    // TODO constructor
    /**
     * Create an empty ConcreteVerticesGraph.
     */
    public ConcreteVerticesGraph() {
        vertices.clear();
        checkRep();
    }
    
    public ConcreteVerticesGraph(final List<Vertex<L>> vertices) {
        for(Vertex<L> vertex : vertices) {
            this.vertices.add(new Vertex<L>(vertex.getName(), vertex.getMap()));
        }
        checkRep();
    }
    // TODO checkRep
    private void checkRep(){
        final int size = vertices.size();
        if(!vertices.isEmpty()){
            for(int i = 0;i<size;i++) {
                for(int j = i+1;j<size;j++){
                    assert !vertices.get(i).equals(vertices.get(j));
                }
            }
        }
    }
    
    @Override
    public boolean add(L vertex) {
        for (Vertex<L> vertexTemp : vertices) {
            if (vertexTemp.equalsName(vertex)) {
                return false;
            }
        }
        vertices.add(new Vertex<L>(vertex));
        checkRep();
        return true;
    }

    @Override
    public int set(L source, L target, int weight) {
        int weightOfAnswer = 0;
        Iterator<Vertex<L>> iterator = vertices.iterator();
        if(weight == 0){
            while(iterator.hasNext()) {
                Vertex<L> vertex = iterator.next();
                if(vertex.equalsName(source) && vertex.adjacentTo(target)) {
                    weightOfAnswer = vertex.getWeightOfEdge(target);
                    vertex.remove(target);
                    break;
                }
            }
        } else {
            boolean sourceExisted = false;
            add(target);
            iterator = vertices.iterator();
            while(iterator.hasNext()){
                Vertex<L> vertex = iterator.next();
                if(vertex.equalsName(source)) {
                    weightOfAnswer = vertex.getWeightOfEdge(target);
                    vertex.put(target, weight);
                    sourceExisted = true;
                    break;
                }
            }
            if(!sourceExisted) {
                add(source);
                vertices.get(vertices.size()-1).put(target, weight);
            }
        }
        checkRep();
        return weightOfAnswer;
    }
    
    @Override
    public boolean remove(L vertex) {
        Iterator<Vertex<L>> iterator = vertices.iterator();
        boolean removeSuceess = false;
        while (iterator.hasNext()) {
            Vertex<L> vertex2 = iterator.next();
            if(vertex2.adjacentTo(vertex)) {
                vertex2.remove(vertex);
            }
            if (vertex2.getName().equals(vertex)) {
                iterator.remove();
                removeSuceess = true;
            }
        }
        checkRep();
        return removeSuceess;
    }
    
    @Override
    public Set<L> vertices() {
        Set<L> anSet = new HashSet<>();
        for (Vertex<L> vertex : vertices) {
            anSet.add(vertex.getName());
        }
        return anSet;
    }

    @Override
    public Map<L, Integer> sources(L target) {
        Map<L, Integer> sourcesOfTarget = new HashMap<>();
        for (Vertex<L> vertex : vertices) {
            if (vertex.adjacentTo(target)) {
                sourcesOfTarget.put(vertex.getName(), vertex.getWeightOfEdge(target));
            }
        }
        return sourcesOfTarget;
    }

    @Override
    public Map<L, Integer> targets(L source) {
        Iterator<Vertex<L>> iterator = vertices.iterator();
        Map<L, Integer> targetsOfSources = new HashMap<>();
        while (iterator.hasNext()) {
            Vertex<L> vertex = iterator.next();
            if (vertex.equalsName(source)) {
                targetsOfSources.putAll(vertex.getMap());
                break;
            }
        }
        return targetsOfSources;
    }

    // TODO toString()
    @Override
    public String toString(){
        StringBuilder stringBuilder = new StringBuilder("This Graph has ");
        int numberOfEdges = 0;
        StringBuilder stringBuffer2 = new StringBuilder();
        stringBuilder.append(vertices.size()).append(" vertice(s), there are:\n");
        for(Vertex<L> vertex : vertices) {
            numberOfEdges += vertex.getAdjacentEdgesNumber();
            stringBuilder.append("\"").append(vertex.getName()).append("\"\n");
            stringBuffer2.append(vertex.toString());
        }
        stringBuilder.append(numberOfEdges).append(" edge(s), there are:\n");
        stringBuilder.append(stringBuffer2);
        return stringBuilder.toString();
    }
}

/**
 * TODO specification Mutable. This class is internal to the rep of
 * ConcreteVerticesGraph.
 * 
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */
class Vertex<L>{

    // TODO fields
    private final L name;
    private final Map<L, Integer> adjacent = new HashMap<>();
    // Abstraction function:
    // TODO
    // Represents a vertex which is one of two vertices of a directed edge that
    // is from this to the other vertex with weight.

    // Representation invariant:
    // TODO
    // name != null.
    // Value in adjacent.getValue() is positive.
    // Target in adjacent.getKey() is not null or empty string.

    // Safety from rep exposure:
    // TODO
    // All fields are private and final.
    // We’ve done some defensive copying in the return value of adjacent.

    // TODO constructor
    /**
     * Create a vertex with its name
     * 
     * @param name
     *            the identifier of every vertex.
     */
    public Vertex(final L name) {
        this.name = name;
        checkRep();
    }
    
    public Vertex(final L name, final Map<L, Integer> aMap) {
        this.name = name;
        this.adjacent.putAll(aMap);
        checkRep();
    }
    // TODO checkRep
    private void checkRep() {
        assert name != null;
//        assert !name.isEmpty();
        if (!adjacent.isEmpty()) {
            for (Map.Entry<L, Integer> entry : adjacent.entrySet()) {
                assert !name.equals(entry.getKey());
                assert entry.getValue() > 0;
            }
        }
    }

    // TODO methods
    /**
     * @return the label of this vertex.
     */
    public L getName() {
        return this.name;
    }

    /**
     * @return the number of edges adjacent to
     */
    public int getAdjacentEdgesNumber(){
        return adjacent.size();
    }
    
    /**
     * @return the copy of adjacent map
     * */
    public Map<L, Integer> getMap() {
        Map<L, Integer> mapCoyied = new HashMap<>();
        for (Map.Entry<L, Integer> entry : adjacent.entrySet()) {
            mapCoyied.put(entry.getKey(), entry.getValue());
        }
        return mapCoyied;
    }

    /**
     * @param name
     *            that needs to be compared.
     * @return true if this.name is equal to that.
     */
    public boolean equalsName(final L name) {
        return this.name.equals(name);
    }

    /**
     * @param target
     *            that the edge point to.
     * @return weight of the edge. if the target point don't existed, return 0.
     */
    public int getWeightOfEdge(final L target) {
        if (this.adjacent.containsKey(target)) {
            return this.adjacent.get(target);
        }
        return 0;
    }

    /**
     * Add an edge from this vertex to the target vertex with weight.
     * 
     * @param target
     *            the edge that is from this vertex to that vertex different from this.
     * @param weight
     *            the weight of the edge is positive.
     */
    public void put(final L target, final int weight) {
        this.adjacent.put(target, weight);
        checkRep();
    }

    /**
     * Remove the edge from this vertex to the target.
     * 
     * @param target
     *            the target of the edge.
     */
    public void remove(final L target) {
        this.adjacent.remove(target);
        checkRep();
    }

    /**
     * Determine if there is a directed edge from this vertex to the target.
     * 
     * @param target
     *            the target of the edge which is needed to be determined.
     * @return true if there is a directed edge from this vertex to the target
     *         otherwise false.
     */
    public boolean adjacentTo(final L target) {
        return this.adjacent.containsKey(target);
    }

    // TODO toString()
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<L, Integer> entry : adjacent.entrySet()) {
            stringBuilder.append("\"").append(name).append("\" -> \"").append(entry.getKey()).append("\": ").append(entry.getValue()).append("\n");
        }
        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object vertex) {
        if (this == vertex) {
            return true;
        }
        if (vertex instanceof Vertex) {
            Vertex<?> anotherVertex = (Vertex<?>) vertex;
            return this.getName().equals(anotherVertex.getName());
        }
        return false;
    }
    
    @Override
    public int hashCode(){
        int result = 17;
        result = 31 * result + name.hashCode();
        return result;
    }
}
