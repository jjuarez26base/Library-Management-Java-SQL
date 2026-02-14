package com.Library.model;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import com.Library.dao.BookDao;
import com.Library.dao.StaffDao;
import com.Library.dao.UserDao;

public class UserInterface {
    Scanner input = new Scanner(System.in);
    BookDao bookDao = new BookDao();
    UserDao userDao = new UserDao();
    StaffDao staffDao = new StaffDao();
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
            updateUserCheckedOutBooks();
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
        updateUserCheckedOutBooks();
        while (true) {
            System.out.print("\n-User Menu-");
            printUsers();
            System.out.print("""
                    
                    [Add] User | [Remove] User | [Return] to Main Menu
                    
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
    public void updateUserCheckedOutBooks() {
        List<LibraryBook> bookList = bookDao.findAll();
        List<LibraryUser> userList = userDao.findAll();
        for (LibraryUser user : userList) {
            int bookCounter = 0;
            for (LibraryBook book : bookList) {
                int userId = book.getCurrentUser();
                if (user.getUserId() == userId) {
                    bookCounter++;
                }
            }
            user.setBooksCheckedOut(bookCounter);
            userDao.update(user);
        }
    }

    // Helper Methods \\
    private void printStaff() {
        List<LibraryStaff> staffList = staffDao.findAll();
        staffList.forEach(staff -> System.out.println(staff.toString()));
    }
    private void addStaff() {
        String newMemberName;
        System.out.print("Adding new staff member or enter [Stop] to quit:\n staff member Name > ");
        newMemberName = input.nextLine();
        if (isStringInputStop(newMemberName)) {
            System.out.println("\nCanceled Adding New staff member.");
            printPressEnterToContinueText();
            return;
        } else {
            newMemberName = newMemberName.trim();
        }
        String newMemberEmail;
        while (true) {
            System.out.print("Adding new email or enter [Stop] to quit:\n Email Name > ");
            newMemberEmail = input.nextLine();
            if (isStringInputStop(newMemberEmail)) {
                System.out.println("\nCanceled Adding new staff member.");
                printPressEnterToContinueText();
                return;
            } else if (!newMemberEmail.contains("@")) {
                System.out.println("Invalid email address");
                printPressEnterToContinueText();
            } else if (isLibrarianEmailUnique(newMemberEmail)) {
                newMemberEmail = newMemberEmail.trim();
                break;
            } else {
                System.out.println("Invalid email address");
                printPressEnterToContinueText();
            }
        }
        if (staffDao.create(new LibraryStaff(newMemberName, newMemberEmail))) {
            System.out.println("Staff member added successfully.");
            printPressEnterToContinueText();
        } else {
            System.out.println("Failed to add staff member.");
            printPressEnterToContinueText();
        }
    }
    private void editStaff() {
        while (true) {
            Optional<LibraryStaff> staffToEdit;
            System.out.print("Enter staff member ID to Edit");
            String stringToPrint = "Enter staff member ID to Edit";
            Optional<Integer> id = checkAndReturnInputtedId(stringToPrint);
            if (id.isPresent()) {
                staffToEdit = staffDao.findById(id.get());
            } else {
                System.out.println("\nCanceled staff member editing.");
                printPressEnterToContinueText();
                break;
            }
            if (staffToEdit.isPresent()) {
                Optional<LibraryStaff> updatedStaff = printScanAndCheckStaffInput(staffToEdit.get().getId(), staffToEdit.get());
                if (updatedStaff.isPresent()) {
                    boolean result;
                    result = staffDao.update(updatedStaff.get());
                    if (result) {
                        System.out.println("Staff member updated successfully.");
                        printPressEnterToContinueText();
                        break;
                    } else {
                        System.out.println("Failed to update staff member.");
                        printPressEnterToContinueText();
                    }

                } else {
                    System.out.println("\nCanceled staff member editing.");
                    printPressEnterToContinueText();
                    break;
                }
            } else {
                System.out.println("\nNo staff member to edit by ID: " + id.get());
                printPressEnterToContinueText();
            }
        }
    }
    private void removeStaff() {
        while (true) {
            System.out.print("Enter staff member ID to Delete");
            String stringToPrint = "Enter staff member ID to Delete";
            Optional<Integer> id = checkAndReturnInputtedId(stringToPrint);
            if (id.isPresent()) {
                if (staffDao.deleteById(id.get())) {
                    System.out.println("Staff member Deleted Successfully.");
                    printPressEnterToContinueText();
                    break;
                } else {
                    System.out.println("\nNo staff member to delete by ID: " + id.get());
                    printPressEnterToContinueText();
                }
            } else {
                System.out.println("\nCanceled staff member deletion.");
                printPressEnterToContinueText();
                break;
            }
        }
    }

    private void printBooks() {
        List<LibraryBook> bookList = bookDao.findAll();
        bookList.forEach(System.out::println);
    }
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
                Optional<LibraryBook> updatedBook = printScanAndCheckBookInput(bookToEdit.get().getId(), bookToEdit.get());
                if (updatedBook.isPresent()) {
                    boolean result;
                    result = bookDao.update(updatedBook.get());
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
                    System.out.println("Book Deleted Successfully.");
                    printPressEnterToContinueText();
                    break;
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

    private void printUsers() {
        List<LibraryUser> userList = userDao.findAll();
        userList.forEach(System.out::println);
    }
    private void addUser() {
        while (true) {
            System.out.print("Adding new User or enter [Stop] to quit:\n User Name > ");
            String newUserName = input.nextLine();
            if (isStringInputStop(newUserName)) {
                System.out.println("\nCanceled Adding New User.");
                printPressEnterToContinueText();
                break;
            } else {
                newUserName = newUserName.trim();
                LibraryUser newUser = new LibraryUser(newUserName);
                if (userDao.create(newUser)) {
                    System.out.println("User added successfully.");
                    printPressEnterToContinueText();
                    break;
                } else {
                    System.out.println("Failed to create User.");
                    printPressEnterToContinueText();
                }
            }
        }
    }
    private void editUser() {

    }
    private void removeUser() {
        while (true) {
            System.out.print("Enter User ID to Delete");
            String stringToPrint = "Enter User ID to Delete";
            Optional<Integer> id = checkAndReturnInputtedId(stringToPrint);
            if (id.isPresent()) {
                boolean bookDeletionResult = userDao.deleteById(id.get());
                if (bookDeletionResult) {
                    System.out.println("User Deleted Successfully.");
                    printPressEnterToContinueText();
                    break;
                } else {
                    System.out.println("\nNo User to delete by ID: " + id.get());
                    printPressEnterToContinueText();
                }
            } else {
                System.out.println("\nCanceled User deletion.");
                printPressEnterToContinueText();
                break;
            }
        }
    }

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
    private Optional<LibraryBook> printScanAndCheckBookInput(int id, LibraryBook bookToEdit) {
        Optional<Integer> newUserId;
        Optional<Boolean> resetCheckedOutStatus = Optional.empty();
        Optional<Integer> newLibrarianId;
        System.out.print("(Enter [Stop] to quit editing book at any time)");
        System.out.print("\nEnter new Book Name (Enter [None] to keep old book name) > ");
        String newBookName = returnStringInput();
        if (isStringInputStop(newBookName)) {
            return Optional.empty();
        } else if (isStringInputNone(newBookName)) {
            newBookName = bookToEdit.getBookName();
        } else {
            newBookName = newBookName.trim();
        }
        while (true) {
            printAllUserIdAndName();
            System.out.print("\nEnter new user ID (Enter [None] to keep older user ID, or [Remove] to remove current user ID) > ");
            String stringUserID = returnStringInput();
            if (isStringInputStop(stringUserID)) {
                return Optional.empty();
            }
            if (isStringInputNone(stringUserID)) {
                newUserId = Optional.of(bookToEdit.getCurrentUser());
                break;
            }
            if (isStringInputRemove(stringUserID)) {
                System.out.print("\nRemoving User Id will set checked out status to 'No'.\n[Yes] to continue | [No] to change user ID\n> ");
                String decision = input.nextLine();
                decision = decision.toLowerCase().trim();
                if (decision.equals("yes")) {
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
        if (newUserId.isPresent()) {
            if (newUserId.get().equals(0)) {
                System.out.println("Can't change librarian ID without user. Updating Book.");
                return Optional.of(new LibraryBook(id, newBookName, false));
            }
            while (true) {
                printAllLibrarianIdAndName();
                System.out.print("\nEnter new librarian ID (Enter [None] to keep older librarian ID) > ");
                String stringLibrarianID = returnStringInput();
                if (isStringInputStop(stringLibrarianID)) {
                    return Optional.empty();
                }
                if (isStringInputNone(stringLibrarianID)) {
                    newLibrarianId = bookToEdit.getAllowedByLibrarian();
                    if (newLibrarianId.isPresent() && !newLibrarianId.get().equals(0)) {
                        if (!bookToEdit.isLibrarianPresent()) {
                            System.out.print("\nBook has no librarian Id, but has a user. This can't happen to a book. Resetting book checked out status to 'No'\n");
                            return Optional.of(new LibraryBook(id, newBookName, false));
                        }
                        return Optional.of(new LibraryBook(id, newBookName, true, newUserId.get(), newLibrarianId.get()));
                    } else {
                        System.out.println("Can't keep librarian ID to none if adding user.");
                        printPressEnterToContinueText();
                        continue;
                    }
                }
                try {
                    int librarianId = Integer.parseInt(stringLibrarianID);
                    Optional<LibraryStaff> librarian = getLibraryStaff(librarianId);
                    if (librarian.isPresent()) {
                        newLibrarianId = Optional.of(librarianId);
                        int userId = newUserId.get();
                        return Optional.of(new LibraryBook(id, newBookName, true, userId, newLibrarianId.get()));
                    } else {
                        printInvalidText();
                    }
                } catch (NumberFormatException e) {
                    printInvalidText();
                }
            }
        }
        return Optional.of(new LibraryBook(id, newBookName, false));
    }
    private Optional<LibraryStaff> printScanAndCheckStaffInput(int id, LibraryStaff librarian) {
        System.out.print("(Enter [Stop] to quit editing staff member at any time)");
        System.out.print("\nEnter new staff member Name (Enter [None] to keep old staff member name) > ");
        String newStaffName = returnStringInput();
        if (isStringInputStop(newStaffName)) {
            return Optional.empty();
        } else if (isStringInputNone(newStaffName)) {
            newStaffName = librarian.getName();
        } else {
            newStaffName = newStaffName.trim();
        }
        String newStaffEmail;
        while (true) {
            System.out.print("\nEnter new staff member Email (Enter [None] to keep old staff member email, and two staff members can't have the same email) > ");
            newStaffEmail = returnStringInput();
            newStaffEmail = newStaffEmail.trim();
            if (isStringInputStop(newStaffEmail)) {
                return Optional.empty();
            } else if (isStringInputNone(newStaffEmail)) {
                newStaffEmail = librarian.getEmail();
                break;
            } else if (!newStaffEmail.contains("@")) {
                System.out.println("Invalid email address");
                printPressEnterToContinueText();
            } else if (isLibrarianEmailUnique(newStaffEmail)) {
                newStaffEmail = newStaffEmail.trim();
                break;
            } else {
                System.out.println("Invalid email address");
                printPressEnterToContinueText();
            }
        }
        boolean workingStatus;
        while (true) {
            System.out.print("\nEnter new staff member working status (Enter [None] to keep old staff member working status) > ");
            String newStringStaffWorkingStatus = returnStringInput();
            if (isStringInputStop(newStringStaffWorkingStatus)) {
                return Optional.empty();
            } else if (isStringInputNone(newStringStaffWorkingStatus)) {
                workingStatus = librarian.isWorking();
                break;
            }
            Optional<Boolean> unknownStringWorkingStatus = isStringBooleanInput(newStringStaffWorkingStatus);
            if (unknownStringWorkingStatus.isPresent()) {
                workingStatus = unknownStringWorkingStatus.get();
                break;
            } else {
                printInvalidText();
            }
        }
        return Optional.of(new LibraryStaff(id, newStaffName, newStaffEmail, workingStatus));
    }

    private Optional<LibraryUser> getUser(int user_id) {
        return userDao.findById(user_id);
    }
    private Optional<LibraryStaff> getLibraryStaff(int user_id) {
        return staffDao.findById(user_id);
    }
    private boolean isLibrarianEmailUnique(String email) {
        List<LibraryStaff> librarianList = staffDao.findAll();
        for (LibraryStaff librarian : librarianList) {
            return librarian.getEmail().equals(email);
        }
        return false;
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
    private void printAllUserIdAndName() {
        List<LibraryUser> users = userDao.findAll();
        System.out.println();
        users.forEach(libraryUser -> System.out.println("User Id: " +  libraryUser.getUserId() + " | User Name: " + libraryUser.getUserName()));
    }
    private void printAllLibrarianIdAndName() {
        List<LibraryStaff> librarians = staffDao.findAll();
        System.out.println();
        librarians.forEach(libraryStaff -> System.out.println("Librarian Id: " + libraryStaff.getId() + " | Librarian Name: " + libraryStaff.getName()));
    }
}
