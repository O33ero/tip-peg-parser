package exception;

public class CannotRecognizeTokenException extends RuntimeException {
    public CannotRecognizeTokenException(String line) {
        super(line);
    }
}
