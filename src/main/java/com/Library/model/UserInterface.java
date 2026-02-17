package com.Library.model;

import java.util.Optional;
import java.util.Scanner;
import com.Library.dao.BookDao;
import com.Library.dao.UserDao;

public class UserInterface {
    Scanner input = new Scanner(System.in);
    BookDao bookDao = new BookDao();
    UserDao userDao = new UserDao();
    public void staffMenu() {
        while (true) {
            System.out.print("\n-Staff Menu-");
            printStaff();
            System.out.print("""
                    
                    [Add] Staff | [Edit] Staff | [Remove] Staff | [Return] to Main Menu
                    
                    >\s""");
            String command = returnStringInput();
            if (command.equals("add")) {
                addStaff();
            } else if (command.equals("edit")) {
                editStaff();
            } else if (command.equals("remove")) {
                removeStaff();
            } else if (command.equals("return")) {
                break;
            } else {
                printInvalidText();
            }
        }
    }
    public void booksMenu() {
        while (true) {
            System.out.print("\n-Books Menu-");
            printBooks();
            System.out.print("""
                    
                    [Add] Book | [Edit] Book | [Remove] Book | [Return] to Main Menu
                    
                    >\s""");
            String command = returnStringInput();
            if (command.equals("add")) {addBook();}
            else if (command.equals("edit")) {editBook();}
            else if (command.equals("remove")) {removeBook();}
            else if (command.equals("return")) {break;}
            else {printInvalidText();}
        }
    }
    public void userMenu() {
        while (true) {
            System.out.print("\n-User Menu-");
            printUsers();
            System.out.print("""
                    
                    [Add] User | [Edit] User | [Remove] User | [Return] to Main Menu
                    
                    >\s""");
            String userCommand = returnStringInput();
            if (userCommand.equals("add")) {addUser();}
            else if (userCommand.equals("edit")) {editUser();}
            else if (userCommand.equals("remove")) {removeUser();}
            else if (userCommand.equals("return")) {break;}
            else {printInvalidText();}
        }
    }
    public void printInvalidText() {
        System.out.println("\nInvalid Input");
        System.out.print("Press enter to try again.\n");
        input.nextLine();
    }
    public String returnStringInput() {
        String command = input.nextLine();
        return command.toLowerCase().trim();
    }

    // Helper Methods \\
    private void printStaff() {}
    private void addStaff() {}
    private void editStaff() {}
    private void removeStaff() {}

    private void printBooks() {}
    private void addBook() {
        while (true) {
            System.out.print("Adding new Book or enter [Stop] to quit:\n Book Name > ");
            String newBookName = input.nextLine();
            if (isStringInputStop(newBookName)) {
                System.out.println("\nCanceled Adding New Book.");
                printPressEnterToContinueText();
                break;
            } else {
                newBookName = newBookName.trim();
                LibraryBook newBook = new LibraryBook(newBookName);
                if (bookDao.create(newBook)) {
                    System.out.println("Book added successfully.");
                    printPressEnterToContinueText();
                    break;
                } else {
                    System.out.println("Failed to create book.");
                    printPressEnterToContinueText();
                }
            }
        }
    }
    private void editBook() {
        while (true) {
            Optional<LibraryBook> bookToEdit;
            System.out.print("Enter Book ID to Edit");
            String stringToPrint = "Enter Book ID to Edit";
            Optional<Integer> id = checkAndReturnInputtedId(stringToPrint);
            if (id.isPresent()) {
                bookToEdit = bookDao.findById(id.get());
            } else {
                System.out.println("\nCanceled book editing.");
                printPressEnterToContinueText();
                break;
            }
            if (bookToEdit.isPresent()) {
                Optional<LibraryBook> updatedBook = printScanAndCheckBookInput(bookToEdit.get());
                if (updatedBook.isPresent()) {
                    Optional<LibraryBook> newBook = printScanAndCheckBookInput(updatedBook.get());
                    boolean result;
                    if (newBook.isPresent()) {
                        result = bookDao.update(newBook.get());
                    } else {
                        System.out.println("\nCanceled book editing.");
                        printPressEnterToContinueText();
                        break;
                    }
                    if (result) {
                        System.out.println("Book updated successfully.");
                        printPressEnterToContinueText();
                        break;
                    } else {
                        System.out.println("Failed to update book.");
                        printPressEnterToContinueText();
                    }

                } else {
                    System.out.println("\nCanceled book editing.");
                    printPressEnterToContinueText();
                    break;
                }
            } else {
                System.out.println("\nNo book to edit by ID: " + id.get());
                printPressEnterToContinueText();
            }
        }
    }
    private void removeBook() {
        while (true) {
            System.out.print("Enter Book ID to Delete");
            String stringToPrint = "Enter Book ID to Delete";
            Optional<Integer> id = checkAndReturnInputtedId(stringToPrint);
            if (id.isPresent()) {
                boolean bookDeletionResult = bookDao.deleteById(id.get());
                if (bookDeletionResult) {
                    System.out.println("Book Deleted");
                    printPressEnterToContinueText();
                } else {
                    System.out.println("\nNo book to delete by ID: " + id.get());
                    printPressEnterToContinueText();
                }
            } else {
                System.out.println("\nCanceled book deletion.");
                printPressEnterToContinueText();
                break;
            }
        }
    }

    private void printUsers() {}
    private void addUser() {}
    private void editUser() {}
    private void removeUser() {}

    private Optional<Integer> checkAndReturnInputtedId(String printString) {
        while (true) {
            System.out.print(" or enter [stop] to quit:\n> ");
            String command = input.nextLine();
            command = command.toLowerCase().trim();
            if (command.equals("stop")) {
                return Optional.empty();
            }
            try {return Optional.of(Integer.parseInt(command));
            } catch (NumberFormatException e) {
                printInvalidText();
                System.out.print(printString);
            }
        }
    }
    private void printPressEnterToContinueText() {
        System.out.println("Press enter to continue.");
        input.nextLine();
    }
    private Optional<LibraryBook> printScanAndCheckBookInput(LibraryBook bookToEdit) {
        while (true) {
            Optional<Integer> newUserId;
            Optional<Boolean> resetCheckedOutStatus = Optional.empty();
            System.out.print("(Enter [Stop] to quit editing book at any time)");
            System.out.print("\nEnter new Book Name (Enter [None] to keep old book name) > ");
            String newBookName = returnStringInput();
            if (isStringInputStop(newBookName)) {
                System.out.println("\nCanceled book editing.");
                printPressEnterToContinueText();
                return Optional.empty();
            } else if (isStringInputNone(newBookName)) {
                newBookName = bookToEdit.getBookName();
            } else {
                newBookName = newBookName.trim();
            }
            while (true) {
                System.out.print("\nEnter new user ID (Enter [None] to keep older user ID, or [Remove] to remove current user ID) > ");
                String stringUserID = returnStringInput();
                if (isStringInputStop(stringUserID)) {
                    System.out.println("\nCanceled book editing.");
                    printPressEnterToContinueText();
                    return Optional.empty();
                }
                if (isStringInputNone(stringUserID)) {
                    newUserId = bookToEdit.getCurrentUser();
                    break;
                }
                if (isStringInputRemove(stringUserID)) {
                    System.out.print("\nRemoving User Id will set checked out status to false.\n[Yes] to continue | [No] to change user ID\n> ");
                    String decision = input.nextLine();
                    if (decision.equals("Yes")) {
                        newUserId = Optional.empty();
                        resetCheckedOutStatus = Optional.of(true);
                        break;
                    } else {
                        continue;
                    }
                }
                try {
                    int userID = Integer.parseInt(stringUserID);
                    Optional<LibraryUser> user = getUser(userID);
                    if (user.isPresent()) {
                        newUserId = Optional.of(userID);
                        break;
                    } else {
                        printInvalidText();
                        printPressEnterToContinueText();
                    }
                } catch (NumberFormatException e) {
                    printInvalidText();
                    printPressEnterToContinueText();
                }
            }
            if (resetCheckedOutStatus.isPresent()) {
                return Optional.of(new LibraryBook(newBookName, false));
            } else {
                if (newUserId.isPresent()) {
                    int userId = newUserId.get();
                    return Optional.of(new LibraryBook(newBookName, true, userId));
                }
            }
        }
    }

    private Optional<LibraryUser> getUser(int user_id) {
        return userDao.findById(user_id);
    }
    private boolean isStringInputStop(String input) {
        input = input.toLowerCase().trim();
        return input.equals("stop");
    }
    private boolean isStringInputNone(String input) {
        input = input.toLowerCase().trim();
        return input.equals("none");
    }
    private boolean isStringInputRemove(String input) {
        input = input.toLowerCase().trim();
        return input.equals("remove");
    }
    private Optional<Boolean> isStringBooleanInput(String input) {
        input = input.toLowerCase().trim();
        if (input.equals("true")) {
            return Optional.of(true);
        } else if (input.equals("false")) {
            return Optional.of(false);
        } else  {
            return Optional.empty();
        }
    }

}
