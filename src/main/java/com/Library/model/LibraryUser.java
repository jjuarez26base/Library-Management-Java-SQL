package com.Library.model;

public class LibraryUser {
    private int user_id;
    private String userName;
    private boolean isUserMember;
    private int checkOutLimit;
    private int booksCheckedOut;
    private int madeMemberByLibrarian;

    public LibraryUser(int id, String userName, boolean isUserMember,  int checkOutLimit, int booksCheckedOut, int madeMemberByLibrarian) {
        this.user_id = id;
        this.userName = userName;
        this.isUserMember = isUserMember;
        this.checkOutLimit = checkOutLimit;
        this.booksCheckedOut = booksCheckedOut;
        this.madeMemberByLibrarian = madeMemberByLibrarian;
    }

    public int getUser_id() {
        return user_id;
    }
    public String getUserName() {
        return userName;
    }
    public boolean isUserMember() {
        return isUserMember;
    }
    public int getCheckOutLimit() {
        return checkOutLimit;
    }
    public int getBooksCheckedOut() {
        return booksCheckedOut;
    }
    public int getMadeMemberByLibrarian() {
        return madeMemberByLibrarian;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setIsUserMember(boolean isUserMember) {
        this.isUserMember = isUserMember;
    }
    public void setCheckOutLimit(int checkOutLimit) {
        this.checkOutLimit = checkOutLimit;
    }
    public void setBooksCheckedOut(int booksCheckedOut) {
        this.booksCheckedOut = booksCheckedOut;
    }
    public void setMadeMemberByLibrarian(int madeMemberByLibrarian) {
        this.madeMemberByLibrarian = madeMemberByLibrarian;
    }
}
