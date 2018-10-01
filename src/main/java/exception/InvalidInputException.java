package exception;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String token) {
        super(String.format("%s is not a number or an operator", token));
    }
}
