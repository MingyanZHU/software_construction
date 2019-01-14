package edge;


import exception.IllegalEdgeParamsException;
import exception.IllegalVertexParamsException;
import exception.InputFileAgainException;
import vertex.Vertex;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Describe the directed edge of graph
 *
 * @author Zhu Mingyan
 */
public class DirectedEdge extends Edge {
    /*
    AF: Represents the directed edge in graph from source to destination
    RI: source vertex is non-null and an instance of Vertex.
        destination vertex in non-null and an instance of Vertex
    Safety for Rep exposure:
        All fields except label and weight of its super class are modified by private. And there is no other
        function except addVertices can change its source and destination.
     */
    private Vertex source;
    private Vertex destination;

    public DirectedEdge(String label, double weight) throws IllegalEdgeParamsException {
        super(label, weight);
        super.checkRep();
    }

    @Override
    protected void checkRep() throws IllegalEdgeParamsException {
        super.checkRep();
        if (source == null || destination == null) {
            throw new IllegalEdgeParamsException("Some vertex in directed edge is null!");
        }
//        assert source != null;
//        assert destination != null;
    }


    @Override
    public boolean addVertices(List<Vertex> vertices) throws InputFileAgainException {
//        assert vertices.size() == 2 || vertices.size() == 1;
        // If the directed edge is a loop the size of vertices will be one.
        if (vertices == null) {
            throw new IllegalVertexParamsException("Vertices of directed edge is null!");
        }
        if (vertices.size() != 1 && vertices.size() != 2) {
            throw new IllegalVertexParamsException("Number of vertices in directed edge is illegal with " + vertices.size()
                    + " vertices!");
        }
        if (vertices.size() == 2) {
            if (source != null && destination != null &&
                    source.equals(vertices.get(0)) && destination.equals(vertices.get(1))) {
                return false;
            }
            if (vertices.get(0) == null || vertices.get(1) == null) {
                throw new IllegalVertexParamsException("vertices consists of null!");
            }
            try {
                source = vertices.get(0).clone();
                destination = vertices.get(1).clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        } else {
            if (source != null && destination != null &&
                    source.equals(vertices.get(0)) && destination.equals(vertices.get(0))) {
                return false;
            }
            if (vertices.get(0) == null) {
                throw new IllegalVertexParamsException("Vertices consists of null!");
            }
            try {
                source = destination = vertices.get(0).clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        checkRep();
        return true;
    }

    @Override
    public boolean containVertex(Vertex v) {
        return source.equals(v) || destination.equals(v);
    }

    @Override
    public Set<Vertex> vertices() {
        Set<Vertex> answerSet = new HashSet<>();
        answerSet.add(source);
        answerSet.add(destination);
        return answerSet;
    }

    @Override
    public Set<Vertex> sourceVertices() {
        Set<Vertex> answerSet = new HashSet<>();
        answerSet.add(source);
        return answerSet;
    }

    @Override
    public Set<Vertex> targetVertices() {
        Set<Vertex> answerSet = new HashSet<>();
        answerSet.add(destination);
        return answerSet;
    }

    @Override
    public String toString() {
        return "DirectedEdge{" +
                "label='" + getLabel() + '\'' +
                ", weight=" + getWeight() +
                ", source=" + source +
                ", destination=" + destination +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DirectedEdge)) return false;
        if (!super.equals(o)) return false;
        DirectedEdge that = (DirectedEdge) o;
        return source.equals(that.source) &&
                destination.equals(that.destination);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), source, destination);
    }
}
