package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A regular library member. PremiumMember extends this class and overrides the borrowing
 * limits (polymorphism), so the library code never needs to check the member type.
 */
public class Member {

    private final String memberId;
    private String name;
    private String email;
    private String phone;
    private final List<BorrowTransaction> activeLoans = new ArrayList<BorrowTransaction>();

    public Member(String memberId, String name, String email, String phone) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public String getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /** Maximum number of books that can be held at the same time. */
    public int getMaxBooks() {
        return 3;
    }

    /** Number of days a book can be kept. */
    public int getLoanDays() {
        return 14;
    }

    /** Fine charged per day of delay, in rupees. */
    public double getFinePerDay() {
        return 5.0;
    }

    public String getMembershipType() {
        return "Regular";
    }

    public List<BorrowTransaction> getActiveLoans() {
        return Collections.unmodifiableList(activeLoans);
    }

    public void addLoan(BorrowTransaction transaction) {
        activeLoans.add(transaction);
    }

    public void removeLoan(BorrowTransaction transaction) {
        activeLoans.remove(transaction);
    }

    public boolean hasBorrowed(Book book) {
        for (BorrowTransaction t : activeLoans) {
            if (t.getBook().getBookId().equals(book.getBookId())) {
                return true;
            }
        }
        return false;
    }
}
