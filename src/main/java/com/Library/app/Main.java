package com.Library.app;

import com.Library.model.UserInterface;

public class Main {
    static void main(String[] args) {
        UserInterface userInterface = new UserInterface();
        while (true) {
            System.out.print("""
            -Library Management System-
            
            View [Staff] | View [Books] | View [Users]\
            
            [Quit] Library Management System\
            
            >\s""");
            String command = userInterface.returnStringInput();
            System.out.println();
            if (command.equals("staff")) {userInterface.staffMenu();}
            else if (command.equals("books")) {userInterface.booksMenu();}
            else if (command.equals("users")) {userInterface.userMenu();}
            else if (command.equals("quit")) {break;}
            else {userInterface.printInvalidText();}
        }
        System.out.println("\nShutting down Library Management System");
    }
}
