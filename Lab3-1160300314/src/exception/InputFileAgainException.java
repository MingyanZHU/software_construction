package exception;

import org.apache.log4j.Logger;

public class InputFileAgainException extends Exception {
    //    private static final LoggerFactory LOGGER = LoggerFactory.getLogger(InputFileAgainException.class);
    public static final String EXCEPTION_INFO = "Try to exchange to other input file";

    public InputFileAgainException() {
        super();
//        setLogger();
    }

    public InputFileAgainException(Throwable cause) {
        super(cause);
//        setLogger();
    }

    public InputFileAgainException(String info) {
        super(info);
    }
//    private void setLogger(){
//        LOGGER.error(INFO, this);
//    }
}
