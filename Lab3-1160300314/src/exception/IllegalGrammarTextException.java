package exception;

public class IllegalGrammarTextException extends InputFileAgainException {
    public static final String EXCEPTION_INFO = "Input string with grammar errors";

    public IllegalGrammarTextException() {
        super();
    }

    public IllegalGrammarTextException(String info) {
        super(info);
    }

    public IllegalGrammarTextException(Throwable throwable) {
        super(throwable);
    }
}
