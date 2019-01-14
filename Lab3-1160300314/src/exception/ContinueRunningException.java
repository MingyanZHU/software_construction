package exception;

public class ContinueRunningException extends Exception {
    public static final String EXCEPTION_INFO = "Programs with some little errors but it can still run!";

    public ContinueRunningException(){
        super();
    }

    public ContinueRunningException(String info){
        super(info);
    }

    public ContinueRunningException(Throwable cause){
        super(cause);
    }
}
