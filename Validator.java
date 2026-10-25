package util;

import exception.InvalidInputException;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/** Static validation helpers. Each method returns the cleaned value or throws InvalidInputException. */
public final class Validator {

    private Validator() {
    }

    public static String text(String v, String field) throws InvalidInputException {
        String s = v == null ? "" : v.trim();
        if (s.length() < 2 || s.length() > 60) {
            throw new InvalidInputException(field + " must be 2 to 60 characters long.");
        }
        return s;
    }

    public static String personName(String v, String field) throws InvalidInputException {
        String s = text(v, field);
        if (!s.matches("[A-Za-z][A-Za-z .'-]*")) {
            throw new InvalidInputException(field + " may contain only letters, spaces, dots and hyphens.");
        }
        return s;
    }

    public static int intInRange(String v, int min, int max, String field) throws InvalidInputException {
        try {
            int n = Integer.parseInt(v.trim());
            if (n < min || n > max) {
                throw new NumberFormatException();
            }
            return n;
        } catch (NumberFormatException e) {
            throw new InvalidInputException(field + " must be a whole number between " + min + " and " + max + ".");
        }
    }

    public static String email(String v) throws InvalidInputException {
        String s = v == null ? "" : v.trim();
        if (!s.matches("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}")) {
            throw new InvalidInputException("Email must look like name@example.com.");
        }
        return s;
    }

    public static String phone(String v) throws InvalidInputException {
        String s = v == null ? "" : v.trim();
        if (!s.matches("[6-9][0-9]{9}")) {
            throw new InvalidInputException("Phone must be a 10-digit number starting with 6-9.");
        }
        return s;
    }

    public static String bookId(String v) throws InvalidInputException {
        String s = v == null ? "" : v.trim().toUpperCase();
        if (!s.matches("B[0-9]{3,}")) {
            throw new InvalidInputException("Book ID must look like B101.");
        }
        return s;
    }

    public static String memberId(String v) throws InvalidInputException {
        String s = v == null ? "" : v.trim().toUpperCase();
        if (!s.matches("M[0-9]{3,}")) {
            throw new InvalidInputException("Member ID must look like M001.");
        }
        return s;
    }

    public static String transactionId(String v) throws InvalidInputException {
        String s = v == null ? "" : v.trim().toUpperCase();
        if (!s.matches("TXN[0-9]{4,}")) {
            throw new InvalidInputException("Transaction ID must look like TXN1001.");
        }
        return s;
    }

    /** Accepts a date typed as yyyy-MM-dd. */
    public static LocalDate date(String v) throws InvalidInputException {
        try {
            return LocalDate.parse(v.trim());
        } catch (DateTimeParseException e) {
            throw new InvalidInputException("Date must be a real date in the format yyyy-MM-dd.");
        }
    }
}
