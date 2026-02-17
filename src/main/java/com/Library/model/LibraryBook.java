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

    public LibraryBook(String newBookName, boolean newCheckedOutStatus, int newCurrentUser) {
        this.bookName = newBookName;
        this.bookCheckedOut = newCheckedOutStatus;
        this.currentUser = newCurrentUser;
    }
    public LibraryBook(String newBookName, boolean newCheckedOutStatus) {
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
    public Optional<Integer> getCurrentUser() {
        return Optional.of(currentUser);
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

    @Override
    public String toString() {
        return "Book Id: " + this.id + '\n'
                + ", Book Name: " + this.bookName + '\n'
                + ", Book Checked Out: " + this.bookCheckedOut + '\n'
                + ", Current User: " + this.currentUser + '\n'
                + "Allowed By Librarian: " + this.allowedByLibrarian + '\n';
    }
}
