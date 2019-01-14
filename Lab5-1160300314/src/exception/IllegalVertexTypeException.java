package exception;

public class IllegalVertexTypeException extends InputFileAgainException {

  //  public static final String EXCEPTION_INFO
  //      = "Add vertex with its type is not permitted in that graph";

  public IllegalVertexTypeException() {
    super();
  }

  public IllegalVertexTypeException(String info) {
    super(info);
  }

  public IllegalVertexTypeException(Throwable cause) {
    super(cause);
  }
}
