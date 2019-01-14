package application.guihelper;

import java.time.Instant;

class LogRecord {

  private Instant time;
  private String className;
  private Type type;
  //    private String method;
  private String position;
  private String message;

  public LogRecord(Instant time, String className, String type, String position, String message) {
    this.time = time;
    this.className = className;
    this.type = Type.valueOf(type);
    //        this.method = method;
    this.position = position;
    this.message = message;
  }

  public Instant getTime() {
    return time;
  }

  public String getClassName() {
    return className;
  }

  //    public String getMethod() {
  //        return method;
  //    }

  public String getType() {

    return type.name();
  }

  public String getPosition() {
    return position;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public String toString() {
    return message + " at " + position + " in " + time + "\n";
  }
}
