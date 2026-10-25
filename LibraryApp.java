package main;

import exception.LibraryException;
import exception.NotFoundException;
import model.Book;
import model.BorrowTransaction;
import model.Member;
import model.ReturnTransaction;
import service.Library;
import util.InputReader;
import util.Validator;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/** Entry point: menu-driven console application for the Library Management System. */
public class LibraryApp {

    private final Library library = new Library();
    private final InputReader in = new InputReader(new Scanner(System.in));

    public static void main(String[] args) {
        new LibraryApp().run();
    }

    private void run() {
        boolean running = true;
        while (running) {
            try {
                printMenu();
                int choice = in.readInt("Enter your choice (1-6): ", "Choice", 1, 6);
                switch (choice) {
                    case 1:
                        addBook();
                        break;
                    case 2:
                        addMember();
                        break;
                    case 3:
                        borrowBook();
                        break;
                    case 4:
                        returnBook();
                        break;
                    case 5:
                        displayAvailableBooks();
                        break;
                    default:
                        running = false;
                        System.out.println("\nThank you for using the Library Management System. Goodbye!");
                        break;
                }
            } catch (LibraryException e) {
                System.out.println("\n  [ERROR] " + e.getMessage());
            } catch (NoSuchElementException e) {
                System.out.println("\nInput closed. Exiting.");
                running = false;
            } catch (RuntimeException e) {
                System.out.println("\n  [UNEXPECTED ERROR] " + e.getMessage() + " - returning to the menu.");
            }
        }
    }

    private void printMenu() {
        System.out.println();
        System.out.println("==================================================");
        System.out.println("        LIBRARY MANAGEMENT SYSTEM");
        System.out.println("==================================================");
        System.out.println("  1. Add Book");
        System.out.println("  2. Add Member");
        System.out.println("  3. Borrow Book");
        System.out.println("  4. Return Book");
        System.out.println("  5. Display Available Books");
        System.out.println("  6. Exit");
        System.out.println("--------------------------------------------------");
    }

    private static String prompt(String label) {
        return String.format("%-28s: ", label);
    }

    private void addBook() {
        System.out.println("\n--- ADD BOOK ---");
        String title = in.read(prompt("Title"), v -> Validator.text(v, "Title"));
        String author = in.read(prompt("Author name"), v -> Validator.personName(v, "Author name"));
        String country = in.read(prompt("Author country"), v -> Validator.personName(v, "Country"));
        String category = in.read(prompt("Category (e.g. Fiction)"), v -> Validator.text(v, "Category"));
        int copies = in.readInt(prompt("Number of copies (1-50)"), "Copies", 1, 50);
        Book book = library.addBook(title, author, country, category, copies);
        System.out.println("\n  Book added: " + book.getBookId() + " - " + book.getTitle() + " by "
                + book.getAuthor() + " [" + book.stockText() + "]");
    }

    private void addMember() {
        System.out.println("\n--- ADD MEMBER ---");
        int type = in.readInt("Membership (1 = Regular, 2 = Premium): ", "Membership", 1, 2);
        String name = in.read(prompt("Full name"), v -> Validator.personName(v, "Name"));
        String email = in.read(prompt("Email"), Validator::email);
        String phone = in.read(prompt("Phone (10 digits)"), Validator::phone);
        Member m = library.addMember(type == 2, name, email, phone);
        System.out.println("\n  Member registered: " + m.getMemberId() + " - " + m.getName() + " ("
                + m.getMembershipType() + ": up to " + m.getMaxBooks() + " books, " + m.getLoanDays()
                + " days, fine Rs. " + m.getFinePerDay() + "/day)");
    }

    private void borrowBook() throws LibraryException {
        System.out.println("\n--- BORROW BOOK ---");
        requireData();
        printBookTable(library.getBooks());
        String memberId = in.read(prompt("Member ID (e.g. M001)"), Validator::memberId);
        library.getMember(memberId);
        String bookId = in.read(prompt("Book ID (e.g. B101)"), Validator::bookId);
        LocalDate date = readDate("Borrow date (yyyy-MM-dd, Enter = today)");
        BorrowTransaction t = library.borrowBook(memberId, bookId, date);
        System.out.println();
        System.out.println(t.receipt());
        System.out.println("  Updated stock: " + t.getBook().getTitle() + " - " + t.getBook().stockText());
    }

    private void returnBook() throws LibraryException {
        System.out.println("\n--- RETURN BOOK ---");
        String id = in.read(prompt("Borrow transaction ID"), Validator::transactionId);
        LocalDate date = readDate("Return date (yyyy-MM-dd, Enter = today)");
        ReturnTransaction r = library.returnBook(id, date);
        System.out.println();
        System.out.println(r.receipt());
        if (r.getFine() > 0) {
            System.out.println("  NOTE: Book returned " + r.getDaysLate() + " day(s) late. Please pay the fine.");
        } else {
            System.out.println("  Returned on time. No fine.");
        }
        Book b = r.getBorrow().getBook();
        System.out.println("  Updated stock: " + b.getTitle() + " - " + b.stockText());
    }

    private void displayAvailableBooks() {
        System.out.println("\n--- AVAILABLE BOOKS ---");
        List<Book> list = library.getAvailableBooks();
        if (list.isEmpty()) {
            System.out.println("  No books are available right now.");
            return;
        }
        printBookTable(list);
    }

    private void printBookTable(List<Book> list) {
        String line = "-------------------------------------------------------------------------------";
        System.out.println(line);
        System.out.printf("%-6s %-26s %-18s %-12s %s%n", "ID", "TITLE", "AUTHOR", "CATEGORY", "AVAILABLE");
        System.out.println(line);
        for (Book b : list) {
            System.out.printf("%-6s %-26s %-18s %-12s %d/%d%n", b.getBookId(), cut(b.getTitle(), 26),
                    cut(b.getAuthor().getName(), 18), cut(b.getCategory(), 12), b.getAvailableCopies(),
                    b.getTotalCopies());
        }
        System.out.println(line);
    }

    private static String cut(String s, int n) {
        return s.length() > n ? s.substring(0, n) : s;
    }

    private LocalDate readDate(String label) {
        return in.read(label + ": ", v -> v.trim().isEmpty() ? LocalDate.now() : Validator.date(v));
    }

    private void requireData() throws NotFoundException {
        if (!library.hasBooks()) {
            throw new NotFoundException("No books in the library. Add a book first.");
        }
        if (!library.hasMembers()) {
            throw new NotFoundException("No members registered. Add a member first.");
        }
    }
}
