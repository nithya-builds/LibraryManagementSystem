package model;

import exception.BookNotAvailableException;

/** A library book with a total number of copies and the number currently available. */
public class Book {

    private final String bookId;
    private String title;
    private Author author;
    private String category;
    private int totalCopies;
    private int availableCopies;

    public Book(String bookId, String title, Author author, String category, int totalCopies) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.category = category;
        this.totalCopies = totalCopies;
        this.availableCopies = totalCopies;
    }

    public String getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }

    public boolean isAvailable() {
        return availableCopies > 0;
    }

    /** Business rule: stock reduces when a book is borrowed. */
    public void issueCopy() throws BookNotAvailableException {
        if (!isAvailable()) {
            throw new BookNotAvailableException("'" + title + "' is out of stock right now.");
        }
        availableCopies--;
    }

    /** Business rule: stock increases when a book is returned. */
    public void receiveCopy() {
        if (availableCopies < totalCopies) {
            availableCopies++;
        }
    }

    public String stockText() {
        return availableCopies + " of " + totalCopies + " copies available";
    }
}
