package exception;

public class LackVerticesHyperEdgeException extends InputFileAgainException {

  public static final String EXCEPTION_INFO = "The number of vertices in hyperedge is less than 2";

  public LackVerticesHyperEdgeException() {
    super();
  }

  public LackVerticesHyperEdgeException(String info) {
    super(info);
  }

  public LackVerticesHyperEdgeException(Throwable cause) {
    super(cause);
  }
}
