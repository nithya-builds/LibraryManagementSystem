package service;

import exception.BookNotAvailableException;
import exception.InvalidInputException;
import exception.LibraryException;
import exception.LimitExceededException;
import exception.NotFoundException;
import model.Author;
import model.Book;
import model.BorrowTransaction;
import model.Member;
import model.PremiumMember;
import model.ReturnTransaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Holds all books, members and transactions and enforces the business rules. */
public class Library {

    private final Map<String, Book> books = new LinkedHashMap<String, Book>();
    private final Map<String, Member> members = new LinkedHashMap<String, Member>();
    private final Map<String, BorrowTransaction> borrows = new LinkedHashMap<String, BorrowTransaction>();
    private final List<ReturnTransaction> returns = new ArrayList<ReturnTransaction>();

    private int bookCounter = 100;
    private int memberCounter = 0;
    private int borrowCounter = 1000;
    private int returnCounter = 5000;

    public Book addBook(String title, String authorName, String country, String category, int copies) {
        Book book = new Book("B" + (++bookCounter), title, new Author(authorName, country), category, copies);
        books.put(book.getBookId(), book);
        return book;
    }

    public Member addMember(boolean premium, String name, String email, String phone) {
        String id = String.format("M%03d", ++memberCounter);
        Member member = premium ? new PremiumMember(id, name, email, phone) : new Member(id, name, email, phone);
        members.put(id, member);
        return member;
    }

    public Book getBook(String id) throws NotFoundException {
        Book book = books.get(id);
        if (book == null) {
            throw new NotFoundException("No book found with ID " + id + ".");
        }
        return book;
    }

    public Member getMember(String id) throws NotFoundException {
        Member member = members.get(id);
        if (member == null) {
            throw new NotFoundException("No member found with ID " + id + ".");
        }
        return member;
    }

    public boolean hasBooks() {
        return !books.isEmpty();
    }

    public boolean hasMembers() {
        return !members.isEmpty();
    }

    public List<Book> getBooks() {
        return new ArrayList<Book>(books.values());
    }

    public List<Book> getAvailableBooks() {
        List<Book> list = new ArrayList<Book>();
        for (Book b : books.values()) {
            if (b.isAvailable()) {
                list.add(b);
            }
        }
        return list;
    }

    /**
     * Rules: a book can be borrowed only if it is in stock; a member cannot exceed the
     * limit of the membership type or hold two copies of the same title; stock reduces.
     */
    public BorrowTransaction borrowBook(String memberId, String bookId, LocalDate date) throws LibraryException {
        Member member = getMember(memberId);
        Book book = getBook(bookId);
        if (member.getActiveLoans().size() >= member.getMaxBooks()) {
            throw new LimitExceededException(member.getName() + " already holds " + member.getMaxBooks()
                    + " books, the limit for " + member.getMembershipType() + " members.");
        }
        if (member.hasBorrowed(book)) {
            throw new LimitExceededException(member.getName() + " has already borrowed '" + book.getTitle() + "'.");
        }
        book.issueCopy();
        BorrowTransaction t = new BorrowTransaction("TXN" + (++borrowCounter), book, member, date);
        borrows.put(t.getTransactionId(), t);
        member.addLoan(t);
        return t;
    }

    /** Rules: stock increases on return; a fine is charged for every day after the due date. */
    public ReturnTransaction returnBook(String borrowId, LocalDate date) throws LibraryException {
        BorrowTransaction borrow = borrows.get(borrowId);
        if (borrow == null) {
            throw new NotFoundException("No borrow transaction found with ID " + borrowId + ".");
        }
        if (borrow.isReturned()) {
            throw new InvalidInputException("Transaction " + borrowId + " has already been returned.");
        }
        if (date.isBefore(borrow.getBorrowDate())) {
            throw new InvalidInputException("Return date cannot be before the borrow date ("
                    + borrow.getBorrowDate() + ").");
        }
        ReturnTransaction r = new ReturnTransaction("RET" + (++returnCounter), borrow, date);
        borrow.setReturned(true);
        borrow.getBook().receiveCopy();
        borrow.getMember().removeLoan(borrow);
        returns.add(r);
        return r;
    }
}
