package exception;

// 用作处理在Vertex中增加错误的参数 例如将Person的性别写为"Man"等之类的行为
public class IllegalVertexParamsException extends InputFileAgainException {

  public IllegalVertexParamsException() {
    super();
  }

  public IllegalVertexParamsException(String info) {
    super(info);
  }

  public IllegalVertexParamsException(Throwable cause) {
    super(cause);
  }
}
