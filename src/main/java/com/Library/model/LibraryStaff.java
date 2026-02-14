package com.Library.model;

public class LibraryStaff {
    private final int id;
    private String name;
    private String email;
    private boolean working;

    public LibraryStaff(int id, String name, String email, boolean working) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.working = working;
    }

    public int getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getEmail() {
        return this.email;
    }
    public boolean isWorking() {
        return this.working;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setWorking(boolean working) {
        this.working = working;
    }

    @Override
    public String toString() {
        return "Librarian Id: " + this.id + '\n'
                + ", Name: " + this.name + '\n'
                + ", Email: " + this.email + '\n'
                + ", Working: " + this.working;
    }

}
