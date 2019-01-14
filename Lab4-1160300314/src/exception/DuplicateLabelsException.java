package exception;

public class DuplicateLabelsException extends ContinueRunningException {

  public DuplicateLabelsException() {
    super();
  }

  public DuplicateLabelsException(String info) {
    super(info);
  }

  public DuplicateLabelsException(Throwable cause) {
    super(cause);
  }
}
