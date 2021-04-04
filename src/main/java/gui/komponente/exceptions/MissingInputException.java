package gui.komponente.exceptions;

public class MissingInputException extends RuntimeException {
    public MissingInputException() {}

    public MissingInputException(String message) {
        super(message);
    }
}
