package Logic;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Logic {
    Scanner s = new Scanner(System.in);

    public void run() {
        MainMenu();
    }

    public void MainMenu() {
        boolean running = true;
        boolean valid = false;
        int choice = -1;

        System.out.println("=============================");
        System.out.println("     Welcome to FitLife!     ");
        System.out.println("=============================");
        System.out.println();
        System.out.println("1. Login to your account");
        System.out.println("2. Register a new account");
        System.out.println("3. Exit");
        do {
            do {
                try {
                    System.out.print("Please select an option : ");
                    choice = s.nextInt();
                    if (choice > 0) {
                        valid = true;
                    }
                    valid = true;
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                    System.out.println();
                    s.next();
                }
            } while (!valid);
            switch (choice) {
                case 1:
                    Login();
                    break;
                case 2:
                    Register();
                    break;
                case 3:
                    System.out.println("Thank you for using FitLife! Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println();
                    break;
            }
        } while (running);
    }

    public void Login() {
        System.out.println("Login functionality is not implemented yet.");
    }

    public void Register() {
        System.out.println("Registration functionality is not implemented yet.");
    }
}
