package exception;

public class IllegalStateOpeationException extends ContinueRunningException {

  public IllegalStateOpeationException() {
    super();
  }

  public IllegalStateOpeationException(String info) {
    super(info);
  }

  public IllegalStateOpeationException(Throwable cause) {
    super(cause);
  }
}
