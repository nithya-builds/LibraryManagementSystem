package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/** Records the return of a borrowed book and calculates the fine for late returns. */
public class ReturnTransaction {

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd MMM yyyy");

    private final String transactionId;
    private final BorrowTransaction borrow;
    private final LocalDate returnDate;
    private final long daysLate;
    private final double fine;

    public ReturnTransaction(String transactionId, BorrowTransaction borrow, LocalDate returnDate) {
        this.transactionId = transactionId;
        this.borrow = borrow;
        this.returnDate = returnDate;
        this.daysLate = Math.max(0, ChronoUnit.DAYS.between(borrow.getDueDate(), returnDate));
        this.fine = daysLate * borrow.getMember().getFinePerDay();
    }

    public String getTransactionId() {
        return transactionId;
    }

    public BorrowTransaction getBorrow() {
        return borrow;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public long getDaysLate() {
        return daysLate;
    }

    public double getFine() {
        return fine;
    }

    public String receipt() {
        Member m = borrow.getMember();
        StringBuilder sb = new StringBuilder();
        sb.append("+--------------------------------------------------+\n");
        sb.append("|                 RETURN RECEIPT                   |\n");
        sb.append("+--------------------------------------------------+\n");
        sb.append(BorrowTransaction.line("Return ID", transactionId));
        sb.append(BorrowTransaction.line("Borrow ID", borrow.getTransactionId()));
        sb.append(BorrowTransaction.line("Member", m.getName() + " (" + m.getMemberId() + ")"));
        sb.append(BorrowTransaction.line("Book", borrow.getBook().getTitle()));
        sb.append(BorrowTransaction.line("Due date", borrow.getDueDate().format(FMT)));
        sb.append(BorrowTransaction.line("Returned on", returnDate.format(FMT)));
        sb.append(BorrowTransaction.line("Days late", String.valueOf(daysLate)));
        sb.append(BorrowTransaction.line("Fine rate", "Rs. " + m.getFinePerDay() + " per day"));
        sb.append(BorrowTransaction.line("FINE PAYABLE", String.format("Rs. %.2f", fine)));
        sb.append("+--------------------------------------------------+");
        return sb.toString();
    }
}
