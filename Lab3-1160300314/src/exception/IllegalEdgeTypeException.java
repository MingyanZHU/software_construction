package exception;

public class IllegalEdgeTypeException extends InputFileAgainException {
    public static final String EXCEPTION_INFO = "Add Edge with wrong edge type!";

    public IllegalEdgeTypeException(){
        super();
    }

    public IllegalEdgeTypeException(String info){
        super(info);
    }

    public IllegalEdgeTypeException(Throwable cause){
        super(cause);
    }
}
