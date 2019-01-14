package exception;

public class IllegalParamsNumberException extends InputFileAgainException {
    public static final String EXCEPTION_INFO = "The params used to fill vertex fields with wrong number";

    public IllegalParamsNumberException(){
        super();
    }

    public IllegalParamsNumberException(String info){
        super(info);
    }

    public IllegalParamsNumberException(Throwable cause){
        super(cause);
    }
}
