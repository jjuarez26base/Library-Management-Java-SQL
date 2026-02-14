package com.Library.model;

public class LibraryUser {
    private int userId;
    private String userName;
    private boolean isUserMember;
    private int booksCheckedOut;
    private int madeMemberByLibrarian;

    public LibraryUser(int id, String userName, boolean isUserMember, int booksCheckedOut, int madeMemberByLibrarian) {
        this.userId = id;
        this.userName = userName;
        this.isUserMember = isUserMember;
        this.booksCheckedOut = booksCheckedOut;
        this.madeMemberByLibrarian = madeMemberByLibrarian;
    }
    public LibraryUser(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }
    public String getUserName() {
        return userName;
    }
    public boolean isUserMember() {
        return isUserMember;
    }
    public int getBooksCheckedOut() {
        return booksCheckedOut;
    }
    public int getMadeMemberByLibrarian() {
        return madeMemberByLibrarian;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void setIsUserMember(boolean isUserMember) {
        this.isUserMember = isUserMember;
    }
    public void setBooksCheckedOut(int booksCheckedOut) {
        this.booksCheckedOut = booksCheckedOut;
    }
    public void setMadeMemberByLibrarian(int madeMemberByLibrarian) {
        this.madeMemberByLibrarian = madeMemberByLibrarian;
    }

    @Override
    public String toString() {
        if (!this.isUserMember) {
            return "\nUser Id: " + this.userId + '\n'
                    + "- User Name: " + this.userName + '\n'
                    + "- Is user member: No" + '\n'
                    + "- Books checked out: " + this.booksCheckedOut + '\n'
                    + "Made member by librarian: None" + '\n';
        }

        return "\nUser Id: " + this.userId + '\n'
                + "- User Name: " + this.userName + '\n'
                + "- Is user member: Yes" + '\n'
                + "- Books checked out: " + this.booksCheckedOut + '\n'
                + "Made member by librarian: " + this.madeMemberByLibrarian + '\n';
    }
}
