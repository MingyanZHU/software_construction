package graph;

import edge.Edge;
import edge.WordEdge;
import vertex.Word;

import java.util.Set;

/**
 * Describe a network of words, which is a simple Simpledigraph and Weighted graph.
 *
 * @author Zhu Mingyan
 */
public class GraphPoet extends ConcreteGraph<Word, WordEdge> {
    /*
    AF: Represents a network with Word as vertex and WordEdge as Edge.
    RI: vertexSet that the collections of all vertices in graph should not be null
        edgeSet that the collections of all edges in graph should not be null
        Label(name) of graph should be non-null and non-empty String.
    Safety for Rep Exposure:
        All fields are modified by key word private, clients can not access the field outside class.
        And there is no other setter function except addVertex, addEdge, removeEdge and removeVertex.
        Anywhere needed get the mutable fields is defencive by defencive copy
     */
    public GraphPoet(String name){
        super(name);
        super.checkRep();
    }

    @Override
    public String toString() {
        return "GraphPoet" + super.toString().replace("ConcreteGraph", "");
    }
}
