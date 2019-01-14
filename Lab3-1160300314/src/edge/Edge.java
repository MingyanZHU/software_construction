package edge;

import exception.IllegalEdgeParamsException;
import exception.InputFileAgainException;
import vertex.Vertex;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Describe the edge of graph with its label and weight.
 *
 * @author Zhu Mingyan
 */
public abstract class Edge {
    /*
    AF: Represents an edge of graph with label and weight.
    RI: label of edge is non-null and non-empty string consisting of [A-Za-z_0-9] and is unique.
        weight of edge is -1 or a decimal above 0.
    Safety for Rep Exposure:
        All fields in edge are modified by key word private and final. Clients can not change them except
        Constructor of edge.
     */
    private final String label;
    private final double weight;

    /**
     * Get an instance of edge with its label and weight
     *
     * @param label
     * @param weight
     */
    public Edge(String label, double weight) {
        this.label = label;
        this.weight = weight;
    }

    protected void checkRep() throws IllegalEdgeParamsException {
        assert label != null && label.length() != 0;
        Pattern pattern = Pattern.compile("^[\\w]+$");
        Matcher matcher = pattern.matcher(label);
        assert matcher.matches();
        assert Double.doubleToLongBits(weight) == Double.doubleToLongBits(-1) ||
                Double.doubleToLongBits(weight) > Double.doubleToLongBits(0);
    }

    /**
     * Get label of edge
     *
     * @return label string
     */
    public String getLabel() {
        return label;
    }

    /**
     * Get the weight of this edge
     *
     * @return a decimal of -1 if this is an edge without weights or above 0
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Fill the vertices of edge. If it is a directed edge, list of vertices should consist source vertex and
     * destination vertex in order. If it is a undirected edge, list of vertices should consist with exactly two
     * vertices in any order or only one vertex only if it's a loop. If it is a hyper edge, lise of vertices
     * should consist with one vertex at least or more than one vertices in any order.
     *
     * @param vertices
     * @return true if add vertices successfully, otherwise false
     */
    public boolean addVertices(List<Vertex> vertices) throws InputFileAgainException {
        return false;
    }

    /**
     * Determine if there is this vertex in this edge.
     *
     * @param v the vertex need to determine
     * @return true if this vertex in this edge, otherwise false
     */
    abstract public boolean containVertex(Vertex v);

    /**
     * Get all vertices in this edge
     *
     * @return a set consists of all vertices in this edge
     */
    abstract public Set<Vertex> vertices();

    /**
     * Get all the source vertices of this edge
     *
     * @return a set consists of all source vertices of this edge
     */
    abstract public Set<Vertex> sourceVertices();

    /**
     * Get all the destination(target) vertices of this edge
     *
     * @return a set consists of all destination(target) vertices of this edge
     */
    abstract public Set<Vertex> targetVertices();

    @Override
    public String toString() {
        return "Edge{" +
                "label='" + label + '\'' +
                ", weight=" + weight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge edge = (Edge) o;
        return Double.compare(edge.weight, weight) == 0 &&
                label.equals(edge.label);
    }

    @Override
    public int hashCode() {

        return Objects.hash(label, weight);
    }
}
