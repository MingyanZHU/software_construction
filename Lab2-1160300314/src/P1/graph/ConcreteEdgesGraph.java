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
public class ConcreteEdgesGraph<L> implements Graph<L> {

    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();

    // Abstraction function:
    // TODO
    // Represents a set of weighted directed edges from a source 
    // vertex to a target vertex that is different from source in a directed graph.

    // Representation invariant:
    // TODO
    // The weight of edge in edges is a positive integer.
    // Each vertex of edge in edges should also be in vertices set.
    // There is at most one directed edge between any source and target.
    
    // Safety from rep exposure:
    // TODO
    // Each one of the fields are modified by keyword private and final,
    // so they can't be accessed from outside the class or be reassigned.
    // We’ve done some defensive copying in the return value of vertices and edges.

    // TODO constructor
    /**
     * Create an empty ConcreteEdgesGraph.
     */
    public ConcreteEdgesGraph() {
        vertices.clear();
        edges.clear();
        checkRep();
    }
    
    public ConcreteEdgesGraph(final List<L> vertices) {
        this.vertices.addAll(vertices);
        checkRep();
    }
    // TODO checkRep
    private void checkRep(){
        for(L vertex : vertices) {
            assert vertex != null;
        }
    }
    
    @Override
    public boolean add(L vertex) {
        assert vertex != null;
        return this.vertices.add(vertex);
    }

    @Override
    public int set(L source, L target, int weight) {
        int weightOfAnswer = 0;
        Iterator<Edge<L>> iterator = edges.iterator();
        while (iterator.hasNext()) {
            Edge<L> edge = iterator.next();
            if (edge.getSource().equals(source) && edge.getTarget().equals(target)) {
                weightOfAnswer = edge.getWeight();
                iterator.remove();
                break;
            }
        }
        if (weight > 0) {
            edges.add(new Edge<L>(source, target, weight));
            vertices.add(source);
            vertices.add(target);
        }
        checkRep();
        return weightOfAnswer;
    }

    @Override
    public boolean remove(L vertex) {
        Iterator<L> iterator = vertices.iterator();
        boolean removeSuceess = false;
        while (iterator.hasNext()) {
            L string = iterator.next();
            if (string.equals(vertex)) {
                removeSuceess = true;
                iterator.remove();
                break;
            }
        }
        Iterator<Edge<L>> iterator2 = edges.iterator();
        while (iterator2.hasNext()) {
            Edge<L> edge = iterator2.next();
            if (edge.getSource().equals(vertex) || edge.getTarget().equals(vertex)) {
                iterator2.remove();
            }
        }
        checkRep();
        return removeSuceess;
    }

    @Override
    public Set<L> vertices() {
        return new HashSet<>(vertices);
    }

    @Override
    public Map<L, Integer> sources(L target) {
        Iterator<Edge<L>> iterator = edges.iterator();
        Map<L, Integer> sourcesOfTarget = new HashMap<>();
        while (iterator.hasNext()) {
            Edge<L> edge = iterator.next();
            if (edge.getTarget().equals(target)) {
                sourcesOfTarget.put(edge.getSource(), edge.getWeight());
            }
        }
        return sourcesOfTarget;
    }

    @Override
    public Map<L, Integer> targets(L source) {
        Iterator<Edge<L>> iterator = edges.iterator();
        Map<L, Integer> targetsOfSource = new HashMap<>();
        while (iterator.hasNext()) {
            Edge<L> edge = iterator.next();
            if (edge.getSource().equals(source)) {
                targetsOfSource.put(edge.getTarget(), edge.getWeight());
            }
        }
        return targetsOfSource;
    }

    // TODO toString()
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("This Grpah has ");
        stringBuilder.append(vertices.size()).append(" vertice(s), they are:\n");
        for(L string : vertices) {
            stringBuilder.append("\"").append(string.toString()).append("\"\n");
        }
        stringBuilder.append(edges.size()).append(" edge(s), they are:\n");
        for(Edge<L> edge : edges){
            stringBuilder.append(edge.toString());
        }
        return stringBuilder.toString();
    }

}

/**
 * TODO specification Immutable. This class is internal to the rep of
 * ConcreteEdgesGraph.
 * 
 * <p>
 * PS2 instructions: the specification and implementation of this class is up to
 * you.
 */
class Edge<L>{

    // TODO fields
    private final L source;
    private final L target;
    private final int weight;

    // Abstraction function:
    // TODO
    // Represents a directed edge with weight from source vertex to target vertex
    // that is different from the source.

    // Representation invariant:
    // TODO
    // Source is different from target and neither of them is empty string.
    // Weight is an integer that is nonnegative.
    // Source != null, target != null.

    // Safety from rep exposure:
    // TODO
    // Each one of the fields are modified by keyword private and final,
    // so they can't be accessed from outside the class or be reassigned.

    // TODO constructor
    /**
     * Make a directed edge from source to target with the weight.
     * 
     * @param source
     *            The source vertex of the directed edge
     * @param target
     *            The target vertex of the directed edge
     * @param weight
     *            The weight of the directed edge is an integer that is
     *            nonnegative
     */
    public Edge(L source, L target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }

    // TODO checkRep
    private void checkRep() {
        assert weight >= 0;
        assert source != null;
        assert target != null;
//        assert !source.equals(target);
        // 有没有自环?
//        assert !source.equals("");
//        assert !target.equals("");
        // 允许空串?
    }

    // TODO methods

    /**
     * @return the source vertex of the directed edge
     */
    public L getSource() {
        return this.source;
    }

    /**
     * @return the target vertex of the directed edge
     */
    public L getTarget() {
        return this.target;
    }

    /**
     * @return the weight of the directed edge
     */
    public int getWeight() {
        return weight;
    }

    /**
     * @return a new edge object that has the same fields with this.
     * */
    public Edge<L> cloneEdge() {
        return new Edge<L>(source, target, weight);
    }

    @Override
    public boolean equals(Object edge) {
        if (this == edge)
            return true;
        if (edge instanceof Edge) {
            Edge<?> anotherEdge =  (Edge<?>) edge;
            return this.source.equals(anotherEdge.source) && this.target.equals(anotherEdge.target)
                    && this.weight == anotherEdge.weight;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + source.hashCode();
        result = 31 * result + target.hashCode();
        result = 31 * result + weight;
        return result;
    }
    
    // TODO toString()
    @Override
    public String toString() {
        return "\"" + this.source + "\" -> \"" + this.target + "\": " + this.weight + "\n";
    }
}
