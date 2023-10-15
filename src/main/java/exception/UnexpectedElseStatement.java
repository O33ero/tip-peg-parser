package exception;

public class UnexpectedElseStatement extends RuntimeException {
    public UnexpectedElseStatement(String message) {
        super(message);
    }
}
