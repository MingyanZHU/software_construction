package exception;

public class UndefinedVertexException extends InputFileAgainException {
    public static final String EXCEPTION_INFO = "Using undefined vertex in edge";

    public UndefinedVertexException(){
        super();
    }

    public UndefinedVertexException(String info) {
        super(info);
    }

    public UndefinedVertexException(Throwable cause){
        super(cause);
    }
}
