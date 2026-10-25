package util;

import exception.InvalidInputException;

import java.util.Scanner;

/** Reads console input and keeps asking until the value passes validation. */
public class InputReader {

    /** A validation step that converts raw text into a value or throws InvalidInputException. */
    public interface Parser<T> {
        T parse(String value) throws InvalidInputException;
    }

    private final Scanner scanner;

    public InputReader(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    public <T> T read(String prompt, Parser<T> parser) {
        while (true) {
            try {
                return parser.parse(readLine(prompt));
            } catch (InvalidInputException e) {
                System.out.println("  Invalid input: " + e.getMessage() + " Please try again.");
            }
        }
    }

    public int readInt(String prompt, final String field, final int min, final int max) {
        return read(prompt, new Parser<Integer>() {
            public Integer parse(String value) throws InvalidInputException {
                return Validator.intInRange(value, min, max, field);
            }
        });
    }
}
