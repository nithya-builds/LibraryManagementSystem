package exception;

/** Raised when a book, member or transaction cannot be found. */
public class NotFoundException extends LibraryException {

    private static final long serialVersionUID = 1L;

    public NotFoundException(String message) {
        super(message);
    }
}
