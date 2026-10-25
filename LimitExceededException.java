package exception;

/** Raised when a member breaks a borrowing rule such as the book limit. */
public class LimitExceededException extends LibraryException {

    private static final long serialVersionUID = 1L;

    public LimitExceededException(String message) {
        super(message);
    }
}
