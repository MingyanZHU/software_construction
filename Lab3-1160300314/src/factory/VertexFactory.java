package factory;

import exception.IllegalVertexTypeException;
import exception.InputFileAgainException;
import vertex.*;

/**
 * A factory that used to create instances of vertices
 *
 * @author Zhu Mingyan
 */
public class VertexFactory {
    /**
     * Create an instance of some type with params
     *
     * @param label the label string of instance which is a non-null and non-empty string
     * @param type  the type of vertices that you want to that should in all types we have been defined
     * @param args  all fields needed when created an instance of determined vertex type
     * @return an vertex instance
     * TODO @throws
     */
    public static Vertex createVertex(String label, String type, String[] args) throws InputFileAgainException {
        Vertex vertex;
        if (type == null) {
            throw new IllegalVertexTypeException("Vertex type of inputs is null!");
        }
        if (type.equals("Word")) {
            vertex = new Word(label);
            return vertex;
        } else if (type.equals("Computer")) {
            vertex = new Computer(label);
        } else if (type.equals("Server")) {
            vertex = new Server(label);
        } else if (type.equals("Router")) {
            vertex = new Router(label);
        } else if (type.equals("Actor")) {
            vertex = new Actor(label);
        } else if (type.equals("Director")) {
            vertex = new Director(label);
        } else if (type.equals("Person")) {
            vertex = new Person(label);
        } else if (type.equals("Movie")) {
            vertex = new Movie(label);
        } else if (type.equals("WirelessRouter")) {
            vertex = new WirelessRouter(label);
        } else {
            vertex = null;
        }
//        assert vertex != null;
        if (vertex == null) {
            throw new IllegalVertexTypeException("Using VertexFactory with a non-defined vertex type!");
        }
        vertex.fillVertexInfo(args);
        return vertex;
    }

}
