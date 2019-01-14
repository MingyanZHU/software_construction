package edge;

import exception.IllegalEdgeParamsException;
import exception.IllegalVertexParamsException;
import exception.IllegalVertexTypeException;
import exception.InputFileAgainException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import vertex.Vertex;
import vertex.Word;

/**
 * Describe an interaction between two words if the former word is next to the latter word in a
 * text.
 *
 * @author Zhu Mingyan
 */
public class WordNeighborhood extends DirectedEdge {

  /**
   * Edge in GraphPoet that means there is a neighborhood between any two words.
   *
   * @param label non-null string
   * @param weight represent the number of occur and should > 0.
   * @throws InputFileAgainException if weight <= 0.
   */
  public WordNeighborhood(String label, double weight) throws InputFileAgainException {
    super(label, weight);
    if (weight <= 0) {
      throw new IllegalEdgeParamsException("Weight of WordNeighborhood is less than 0");
    }
  }

  @Override
  protected void checkRep() throws InputFileAgainException {
    assert this.getWeight() > 0;
    super.checkRep();
  }

  /**
   * Add vertices in wordEdge. All vertex in list should be an instance of Word.
   *
   * @return true if add vertices successfully, otherwise false
   * @throws InputFileAgainException if vertices is null or number of vertex is not correct or there
   *          is other type except Word in vertices.
   */
  @Override
  public boolean addVertices(List<Vertex> vertices) throws InputFileAgainException {
    if (vertices == null) {
      throw new IllegalVertexParamsException(
          "Add vertices into WordNeighborhood with null vertices!");
    }
    if (vertices.size() != 2 && vertices.size() != 1) {
      throw new IllegalVertexParamsException(
          "Add vertices into WordNeighborhood with " + vertices.size() + " vertices");
    }
    boolean answer;
    for (Vertex vertex : vertices) {
      answer = vertex instanceof Word;
      if (!answer) {
        throw new IllegalVertexTypeException(
            "Add " + (vertex == null ? "null" : vertex.getClass().getSimpleName())
                + " vertex into WordNeighborhood!");
      }
    }
    return super.addVertices(vertices);
  }

  @Override
  public String toString() {
    return "WordNeighborhood" + super.toString().replace("DirectedEdge", "");
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof WordNeighborhood)) {
      return false;
    }
    return super.equals(o);
  }

}
