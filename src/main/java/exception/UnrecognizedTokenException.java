package exception;

public class UnrecognizedTokenException extends RuntimeException {
    public UnrecognizedTokenException(String message) {
        super(message);
    }
}
