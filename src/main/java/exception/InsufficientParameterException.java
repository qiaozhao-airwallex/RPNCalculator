package exception;

public class InsufficientParameterException extends RuntimeException {

    public InsufficientParameterException(String operator, int position) {
        super(String.format("operator %s (position: %d): insufficient parameters", operator, position));
    }

}
