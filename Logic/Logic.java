package Logic;

import java.util.*;
import Object.*;
import java.io.*;
import java.nio.file.*;

public class Logic {
    Scanner s = new Scanner(System.in);
    ArrayList<User> UserList = new ArrayList<>();

    public void run() {
        Initialize();
        MainMenu();
    }

    public void MainMenu() {
        boolean running = true;
        boolean valid = false;
        int choice = -1;
        do {
            System.out.println("=============================");
            System.out.println("     Welcome to FitLife!     ");
            System.out.println("=============================");
            System.out.println();
            System.out.println("1. Login to your account");
            System.out.println("2. Register a new account");
            System.out.println("3. Exit");
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

    public void Initialize() {
        UserList.clear();
        String filePath = "./Data/Users.csv";
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    String username = parts[0];
                    String password = parts[1];
                    int usia = Integer.parseInt(parts[2]);
                    double beratBadan = Double.parseDouble(parts[3]);
                    double tinggiBadan = Double.parseDouble(parts[4]);
                    String level = parts[5];
                    UserList.add(new User(username, password, usia, beratBadan, tinggiBadan, level));
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load users: " + e.getMessage());
        }
    }

    public void Login() {
        System.out.println("=== Login ===");
        System.out.print("Enter Username: ");
        String username = s.next();
        System.out.print("Enter Password: ");
        String password = s.next();

        boolean found = false;
        for (User user : UserList) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                found = true;
                break;
            }
        }

        if (found) {
            System.out.println("Login successful! Welcome, " + username + "!");
            // You can add more logic here for after login
        } else {
            System.out.println("Invalid username or password. Please try again.");
        }
    }

    public void Register() {
        System.out.println("=== Register a New Account ===");
        System.out.print("Enter Username: ");
        String username = s.next();

        // Check if username already exists
        boolean usernameExists = false;
        for (User user : UserList) {
            if (user.getUsername().equalsIgnoreCase(username)) {
                usernameExists = true;
                break;
            }
        }
        if (usernameExists) {
            System.out.println("Registration Failed! Username already exists.");
            System.out.println("Please choose a different username.");
            return;
        }

        System.out.print("Create Password: ");
        String password = s.next();
        System.out.print("Enter Age: ");
        int usia = s.nextInt();
        System.out.print("Enter weight (kg): ");
        double beratBadan = s.nextDouble();
        System.out.print("Enter height (cm): ");
        double tinggiBadan = s.nextDouble();
        System.out.print("Enter exercise difficulty level (Beginner, Intermediate, Advanced): ");
        String level = s.next();

        User newUser = new User(username, password, usia, beratBadan, tinggiBadan, level);
        UserList.add(newUser);

        String filePath = "./Data/Users.csv";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            String userData = String.join(",",
                    username,
                    password,
                    String.valueOf(usia),
                    String.valueOf(beratBadan),
                    String.valueOf(tinggiBadan),
                    level);
            bw.write(userData);
            bw.newLine();
            System.out.println("Registration successful!");
        } catch (IOException e) {
            System.out.println("Failed to save user: " + e.getMessage());
        }
    }
}
