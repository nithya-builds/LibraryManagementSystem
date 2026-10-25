package exception;

/** Raised when a value typed by the user fails validation. */
public class InvalidInputException extends LibraryException {

    private static final long serialVersionUID = 1L;

    public InvalidInputException(String message) {
        super(message);
    }
}
