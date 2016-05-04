package exception;

/**
 * Created by User on 03.05.2016.
 */
public class TestException extends Exception {
    public TestException(String message, Throwable cause) {
        super(message, cause);
    }

    public TestException(Throwable cause) {
        super(cause);
    }
}