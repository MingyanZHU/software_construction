package graph;

import edge.Edge;
import exception.InputFileAgainException;
import vertex.Vertex;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Graph Interface with Vertex Class L and Edge Class E.
 *
 * @param <L> Vertex Class
 * @param <E> Edge Class
 * @author Zhu Mingyan
 */
public interface Graph<L, E> {
    /**
     * Get an empty graph instance
     *
     * @param <L> Vertex Class
     * @param <E> Edge Class
     * @return an empty Graph
     */
    static <L extends Vertex, E extends Edge> Graph<L, E> empty() {
        return new ConcreteGraph<L, E>();
    }

    /**
     * Get the name of Graph
     *
     * @return label of Graph
     */
    String getGraphName();

    /**
     * Add a vertex instance into Graph
     *
     * @param v vertex will be added
     * @return true if adding successfully, false otherwise
     */
    boolean addVertex(L v);

    /**
     * Remove a vertex from Graph, and any edges connected it will be also removed. If it belongs to the hyper
     * edge and hyper edge consisted of less than two vertices, the hyper edge will also removed; otherwise the
     * hyper edge will not be removed.
     *
     * @param v vertex will be removed
     * @return true if there is such vertex in graph, other false.
     */
    boolean removeVertex(L v);

    /**
     * Get all vertices of Graph
     *
     * @return a set of all vertices
     */
    Set<L> vertices();

    /**
     * Get all edges that to some target vertex
     *
     * @param target
     * @return a map with source vertex and weight of edge connected source and target.
     */
    Map<L, List<Double>> sources(L target);

    /**
     * Get all edges that from source vertex
     *
     * @param source
     * @return a map with target vertex and weight of edge connected source and target.
     */
    Map<L, List<Double>> targets(L source);

    /**
     * Add an edge into Graph
     *
     * @param edge
     * @return true if adding successfully, otherwise false
     */
    boolean addEdge(E edge) throws InputFileAgainException;

    /**
     * Remove an edge from Graph
     *
     * @param edge
     * @return true if there is such an edge in this graph, other false
     */
    boolean removeEdge(E edge) throws InputFileAgainException;

    /**
     * Get all edges of this Graph
     *
     * @return a set consists of all edges of graph
     */
    Set<E> edges();
}
