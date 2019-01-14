package exception;

public class UndirectedEdgeWithDirectionException extends ContinueRunningException {
    public static final String EXCEPTION_INFO = "Undirected edge with params directed \"Yes\"";

    public UndirectedEdgeWithDirectionException(){
        super();
    }

    public UndirectedEdgeWithDirectionException(String info){
        super(info);
    }
}
