package factory;

import exception.IllegalVertexTypeException;
import exception.InputFileAgainException;
import vertex.Actor;
import vertex.Computer;
import vertex.Director;
import vertex.Movie;
import vertex.Person;
import vertex.Router;
import vertex.Server;
import vertex.Vertex;
import vertex.WirelessRouter;
import vertex.Word;

/**
 * A factory that used to create instances of vertices.
 *
 * @author Zhu Mingyan
 */
public class VertexFactory {

  /**
   * Create an instance of some type with params.
   *
   * @param label the label string of instance which is a non-null and non-empty string
   * @param type the type of vertices that you want to that should in all types we have been
   *        defined
   * @param args all fields needed when created an instance of determined vertex type
   * @return an vertex instance
   * @throws InputFileAgainException if type is null string or there is no such type.
   */
  public static Vertex createVertex(String label, String type, String[] args)
      throws InputFileAgainException {
    Vertex vertex;
    if (type == null) {
      throw new IllegalVertexTypeException("Vertex type of inputs is null!");
    }
    switch (type) {
      case "Word":
        vertex = new Word(label);
        return vertex;
      case "Computer":
        vertex = new Computer(label);
        break;
      case "Server":
        vertex = new Server(label);
        break;
      case "Router":
        vertex = new Router(label);
        break;
      case "Actor":
        vertex = new Actor(label);
        break;
      case "Director":
        vertex = new Director(label);
        break;
      case "Person":
        vertex = new Person(label);
        break;
      case "Movie":
        vertex = new Movie(label);
        break;
      case "WirelessRouter":
        vertex = new WirelessRouter(label);
        break;
      default:
        vertex = null;
        break;
    }
    //assert vertex != null;
    if (vertex == null) {
      throw new IllegalVertexTypeException("Using VertexFactory with a non-defined vertex type!");
    }
    vertex.fillVertexInfo(args);
    return vertex;
  }

}
