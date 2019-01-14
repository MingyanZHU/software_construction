package exception;

public class IllegalVertexTypeDefinedException extends ContinueRunningException {
    public IllegalVertexTypeDefinedException(){
        super();
    }

    public IllegalVertexTypeDefinedException(String info){
        super(info);
    }

    public IllegalVertexTypeDefinedException(Throwable cause){
        super(cause);
    }
}
