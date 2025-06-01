package Logic;

import java.util.*;
import Object.*;
import java.io.*;
import java.nio.file.*;

public class Logic {
    Scanner s = new Scanner(System.in);
    ArrayList<User> UserList = new ArrayList<>();
    public static ArrayList<DifficultyLatihan> allDifficulty = new ArrayList<>();
    User currentUser = null;

    public void run() {
        Initialize();
        MainMenu();
    }

    public void MainMenu() {
        boolean running = true;
        boolean valid = false;
        int choice = -1;
        do {
            System.out.println();
            System.out.println("=============================");
            System.out.println("     Welcome to FitLife!     ");
            System.out.println("=============================");
            System.out.println();
            System.out.println("1. Login to your account");
            System.out.println("2. Register a new account");
            System.out.println("3. Exit");
            do {
                try {
                    System.out.print(">  ");
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
        InitializeLatihan i = new InitializeLatihan();
        i.inisialisasiLatihan();
    }

    public void Login() {
        boolean Running = true;
        do {
            System.out.println();
            System.out.println();
            System.out.println("=== Login ===");
            System.out.print("Enter Username: ");
            String username = s.next();
            System.out.print("Enter Password: ");
            String password = s.next();

            boolean found = false;
            for (User user : UserList) {
                if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                    found = true;
                    currentUser = user;
                    break;
                }
            }

            if (found) {
                System.out.println("Login successful! Welcome, " + username + "!");
                Running = false;
                menu();
            } else {
                System.out.println("Invalid username or password. Please try again.");
                Running = false;
            }
        } while (Running);
    }

    public void Register() {
        System.out.println();
        System.out.println();
        System.out.println("=== Register a New Account ===");
        System.out.print("Enter Username: ");
        String username = s.next();

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
        String recommendedLevel = rekomendasiLevel(usia, beratBadan, tinggiBadan);
        System.out.println("Your BMI is : " + (int) (beratBadan / ((tinggiBadan / 100) * (tinggiBadan / 100))));
        System.out.println("According to your BMI we recommend : " + "\u001B[32m" + recommendedLevel + "\u001B[0m");
        System.out.print("Enter exercise difficulty level : ");
        System.out.println();

        String[] levels = { "Beginner", "Intermediate", "Advanced" };
        for (int i = 0; i < levels.length; i++) {
            if (levels[i].equalsIgnoreCase(recommendedLevel)) {
                System.out.println((i + 1) + ". \u001B[32m" + levels[i] + "\u001B[0m");
            } else {
                System.out.println((i + 1) + ". " + levels[i]);
            }
        }
        System.out.print("> ");
        int levelchoice = s.nextInt();
        String level = null;

        switch (levelchoice) {
            case 1:
                System.out.println("You have chosen Beginner level.");
                level = "Beginner";
                break;
            case 2:
                System.out.println("You have chosen Intermediate level.");
                level = "Intermediate";
                break;
            case 3:
                System.out.println("You have chosen Advanced level.");
                level = "Advanced";
                break;
            default:
                break;
        }

        User newUser = new User(username, password, usia, beratBadan, tinggiBadan, level);
        UserList.add(newUser);
        currentUser = newUser;

        saveUsersToCSV();
        System.out.println("Registration successful!");

        menu();
    }

    private String rekomendasiLevel(int usia, double beratBadan, double tinggiBadan) {
        double tinggiMeter = tinggiBadan / 100.0;
        double bmi = beratBadan / (tinggiMeter * tinggiMeter);

        if (usia < 18 || usia > 50 || bmi < 18.5 || bmi >= 30) {
            return "Beginner";
        } else if (bmi >= 18.5 && bmi < 25 && usia >= 18 && usia <= 40) {
            return "Intermediate";
        } else if (bmi >= 25 && bmi < 30 && usia >= 18 && usia <= 40) {
            return "Advanced";
        } else {
            return "Beginner";
        }
    }

    public void menu() {
        boolean running = true;
        int choice = -1;
        do {
            System.out.println();
            System.out.println();
            System.out.println("=============================");
            System.out.println("           FitLife           ");
            System.out.println("=============================");
            System.out.println("1. Generate Today's Workout");
            System.out.println("2. Generate This Week's Workout");
            System.out.println("3. View Calories Burned");
            System.out.println("4. View Workout History");
            System.out.println("5. Set Your Weight Target");
            System.out.println("6. Update User Profile");
            System.out.println("7. Logout");
            System.out.print(">  ");
            choice = s.nextInt();

            switch (choice) {
                case 1:
                    // Generate Today's Workout
                    break;
                case 2:
                    // Generate This Week's Workout
                    break;
                case 3:
                    // View Calories Burned
                    break;
                case 4:
                    // View Workout History
                    break;
                case 5:
                    // Set Your Weight Target
                    break;
                case 6:
                    UpdateUserProfile();
                    break;
                case 7:
                    System.out.println("Logging out...");
                    System.out.println();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (running);
    }

    public void UpdateUserProfile() {
        boolean Running = true;
        do {
            System.out.println("=== Update User Profile ===");
            System.out.println("Your current profile:");

            System.out.println("1. Username: " + currentUser.getUsername());
            System.out.println("2. Password: " + currentUser.getPassword());
            System.out.println("3. Age: " + currentUser.getUsia());
            System.out.println("4. Weight: " + currentUser.getBeratBadan() + " kg");
            System.out.println("5. Difficulty Level: " + currentUser.getLevel());
            System.out.println();
            System.out.println("0. Exit to Main Menu");
            System.out.print("> ");
            int choice = s.nextInt();
            s.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter new Username: ");
                    String newUsername = s.nextLine();
                    boolean exists = false;
                    for (User user : UserList) {
                        if (user != currentUser && user.getUsername().equalsIgnoreCase(newUsername)) {
                            exists = true;
                            break;
                        }
                    }
                    if (exists) {
                        System.out.println("Username already exists. Please choose a different username.");
                    } else {
                        currentUser.setUsername(newUsername);
                        System.out.println("Username updated successfully!");
                    }
                    break;
                case 2:
                    System.out.print("Enter new Password: ");
                    String newPassword = s.nextLine();
                    currentUser.setPassword(newPassword);
                    System.out.println("Password updated successfully!");
                    break;
                case 3:
                    System.out.print("Enter new Age: ");
                    int newAge = s.nextInt();
                    currentUser.setUsia(newAge);
                    System.out.println("Age updated successfully!");
                    s.nextLine();
                    break;
                case 4:
                    System.out.print("Enter new Weight (kg): ");
                    double newWeight = s.nextDouble();
                    currentUser.setBeratBadan(newWeight);
                    System.out.println("Weight updated successfully!");
                    s.nextLine();
                    break;
                case 5:
                    System.out.println("Choose new Difficulty Level:");
                    String[] levels = { "Beginner", "Intermediate", "Advanced" };
                    for (int i = 0; i < levels.length; i++) {
                        System.out.println((i + 1) + ". " + levels[i]);
                    }
                    System.out.print("> ");
                    int levelChoice = s.nextInt();
                    s.nextLine();
                    String newLevel = null;
                    switch (levelChoice) {
                        case 1:
                            newLevel = "Beginner";
                            break;
                        case 2:
                            newLevel = "Intermediate";
                            break;
                        case 3:
                            newLevel = "Advanced";
                            break;
                        default:
                            System.out.println("Invalid choice. Difficulty not changed.");
                            break;
                    }
                    if (newLevel != null) {
                        currentUser.setLevel(newLevel);
                        System.out.println("Difficulty Level updated successfully!");
                    }
                    break;
                case 0:
                    Running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
            System.out.println();
            System.out.println();
            saveUsersToCSV();

        } while (Running);

    }

    private void saveUsersToCSV() {
        String filePath = "./Data/Users.csv";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false))) {
            for (User user : UserList) {
                String userData = String.join(",",
                        user.getUsername(),
                        user.getPassword(),
                        String.valueOf(user.getUsia()),
                        String.valueOf(user.getBeratBadan()),
                        String.valueOf(user.getTinggiBadan()),
                        user.getLevel());
                bw.write(userData);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to update users: " + e.getMessage());
        }
    }
}
