package vertex;

import exception.IllegalParamsNumberException;
import exception.IllegalVertexParamsException;
import java.util.Objects;

/**
 * Describe the word node of a graph with its label.
 *
 * @author Zhu Mingyan
 */
public class Word extends Vertex {

  /*
  AF: Represents an word node of Graph Poet.
  RI: The label of Word is the word itself.
  Safety for Rep Exposure:
      There is not other fields expect label in Class Word,
      and its super Class Vertex is safe for Rep Exposure.
   */
  public Word(String label) throws IllegalVertexParamsException {
    super(label);
    super.checkRep();
  }

  @Override
  public void fillVertexInfo(String[] args) throws IllegalParamsNumberException {
    if (args != null) {
      throw new IllegalParamsNumberException("Class Word do not need any params!");
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), "Word");
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public String toString() {
    return "Word{" + "word='" + getLabel() + '\'' + '}';
  }

}
