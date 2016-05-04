package by.bsu.credit.exception;

/**
 * Created by User on 02.05.2016.
 */
public class DAOException extends Exception {
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    public DAOException(Throwable cause) {
        super(cause);
    }
}
