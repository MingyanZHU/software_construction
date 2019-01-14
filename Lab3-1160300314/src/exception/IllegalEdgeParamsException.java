package exception;

public class IllegalEdgeParamsException extends InputFileAgainException {
    public static final String EXCEPTION_INFO = "The weight of edge is illegal!";

    public IllegalEdgeParamsException(){
        super();
    }

    public IllegalEdgeParamsException(String info){
        super(info);
    }

    public IllegalEdgeParamsException(Throwable cause){
        super(cause);
    }
}
