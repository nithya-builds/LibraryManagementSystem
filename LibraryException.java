package exception;

/** Base class of all checked exceptions raised by the Library Management System. */
public class LibraryException extends Exception {

    private static final long serialVersionUID = 1L;

    public LibraryException(String message) {
        super(message);
    }
}
