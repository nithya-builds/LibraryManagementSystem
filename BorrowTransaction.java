package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/** Records that a member borrowed a book. It links directly to the Book and the Member. */
public class BorrowTransaction {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd MMM yyyy");

    private final String transactionId;
    private final Book book;
    private final Member member;
    private final LocalDate borrowDate;
    private final LocalDate dueDate;
    private boolean returned;

    public BorrowTransaction(String transactionId, Book book, Member member, LocalDate borrowDate) {
        this.transactionId = transactionId;
        this.book = book;
        this.member = member;
        this.borrowDate = borrowDate;
        this.dueDate = borrowDate.plusDays(member.getLoanDays());
    }

    public String getTransactionId() {
        return transactionId;
    }

    public Book getBook() {
        return book;
    }

    public Member getMember() {
        return member;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public String receipt() {
        StringBuilder sb = new StringBuilder();
        sb.append("+--------------------------------------------------+\n");
        sb.append("|                 BORROW RECEIPT                   |\n");
        sb.append("+--------------------------------------------------+\n");
        sb.append(line("Transaction ID", transactionId));
        sb.append(line("Member", member.getName() + " (" + member.getMemberId() + ")"));
        sb.append(line("Membership", member.getMembershipType()));
        sb.append(line("Book", book.getTitle() + " (" + book.getBookId() + ")"));
        sb.append(line("Author", book.getAuthor().getName()));
        sb.append(line("Borrow date", borrowDate.format(FMT)));
        sb.append(line("Due date", dueDate.format(FMT) + " (" + member.getLoanDays() + " days)"));
        sb.append(line("Books held now", (member.getActiveLoans().size()) + " of " + member.getMaxBooks()));
        sb.append("+--------------------------------------------------+");
        return sb.toString();
    }

    static String line(String label, String value) {
        return String.format("| %-15s: %-31s |%n", label, value.length() > 31 ? value.substring(0, 31) : value);
    }
}
