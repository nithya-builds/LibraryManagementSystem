package exception;

/** Raised when no copy of a book is available to borrow. */
public class BookNotAvailableException extends LibraryException {

    private static final long serialVersionUID = 1L;

    public BookNotAvailableException(String message) {
        super(message);
    }
}
