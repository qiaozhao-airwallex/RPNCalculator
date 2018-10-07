package exception;

public class InsufficientHistoryException extends RuntimeException {

    public InsufficientHistoryException() {
        super("No more history to undo");
    }

}
