package com.Library.model;

import java.util.Optional;

public class LibraryBook {
    private int id;
    private String bookName;
    private boolean bookCheckedOut;
    private int currentUser;
    private int allowedByLibrarian;

    public LibraryBook(int id, String bookName, boolean bookCheckedOut, int currentUser, int allowedByLibrarian) {
        this.id = id;
        this.bookName = bookName;
        this.bookCheckedOut = bookCheckedOut;
        this.currentUser = currentUser;
        this.allowedByLibrarian = allowedByLibrarian;
    }

    public LibraryBook(int id, String newBookName, boolean newCheckedOutStatus) {
        this.id = id;
        this.bookName = newBookName;
        this.bookCheckedOut = newCheckedOutStatus;
    }
    public LibraryBook(String newBookName) {
        this.bookName = newBookName;
    }

    public int getId() {
        return id;
    }
    public String getBookName() {
        return bookName;
    }
    public Optional<Boolean> isBookCheckedOut() {
        return Optional.of(bookCheckedOut);
    }
    public int getCurrentUser() {
        return currentUser;
    }
    public Optional<Integer> getAllowedByLibrarian() {
        return Optional.of(allowedByLibrarian);
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
    public void setBookCheckedOut(boolean bookCheckedOut) {
        this.bookCheckedOut = bookCheckedOut;
    }
    public void setCurrentUser(int currentUser) {
        this.currentUser = currentUser;
    }
    public void setAllowedByLibrarian(int allowedByLibrarian) {
        this.allowedByLibrarian = allowedByLibrarian;
    }

    public boolean isCurrentUserPresent() {
        return currentUser > 0;
    }
    public boolean isLibrarianPresent() {
        return allowedByLibrarian > 0;
    }

    @Override
    public String toString() {
        if (!isCurrentUserPresent()) {
            return "\nBook Id: " + this.id + '\n'
                    + "- Book Name: " + this.bookName + '\n'
                    + "- Book Checked Out: No" + '\n'
                    + "- Current User: None" + '\n'
                    + "- Allowed By Librarian: None";
        }
        return "\nBook Id: " + this.id + '\n'
                + "- Book Name: " + this.bookName + '\n'
                + "- Book Checked Out: Yes" + '\n'
                + "- Current User: " + this.currentUser + '\n'
                + "- Allowed By Librarian: " + this.allowedByLibrarian;
    }
}
