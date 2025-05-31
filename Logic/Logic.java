package Logic;

import java.util.*;
import Object.*;
import java.io.*;
import java.nio.file.*;

public class Logic {
    Scanner s = new Scanner(System.in);
    ArrayList<User> UserList = new ArrayList<>();
    ArrayList<DifficultyLatihan> allDifficulty = new ArrayList<>();

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
        String recommendedLevel = rekomendasiLevel(usia, beratBadan, tinggiBadan);
        System.out.println("Your BMI is : " + (int) (beratBadan / ((tinggiBadan / 100) * (tinggiBadan / 100))));
        System.out.println("According to your BMI we recommend : " + "\u001B[32m" + recommendedLevel + "\u001B[0m");
        System.out.print("Enter exercise difficulty level : ");
        System.out.println();

        String[] levels = { "Beginner", "Intermediate", "Advanced"};
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

    public void inisialisasiLatihan() {
        DifficultyLatihan beginner = new DifficultyLatihan("Beginner");
        DifficultyLatihan intermediate = new DifficultyLatihan("Intermediate");
        DifficultyLatihan advanced = new DifficultyLatihan("Advanced");

        // Beginner
        beginner.addLatihan(new DurationLatihan(
            "Arm Circles",
            "Stand straight, extend arms, make small circles",
            "Shoulder Mobility, WarmUp",
            "WarmUp",
            1,
            30
        ));
        beginner.addLatihan(new RepetitionLatihan(
            "Jumping Jacks",
            "Jump with legs spread and hands overhead, then return",
            "Cardio, WarmUp",
            "WarmUp",
            1,
            20
        ));
        beginner.addLatihan(new DurationLatihan(
            "Neck Rolls",
            "Slowly roll your neck clockwise and counterclockwise",
            "Neck Mobility, WarmUp",
            "WarmUp",
            1,
            20
        ));
        beginner.addLatihan(new DurationLatihan(
            "Wall Sit",
            "Lean against a wall and sit as if on a chair",
            "Leg Strength, Endurance",
            "MainWorkout",
            2,
            30
        ));
        beginner.addLatihan(new RepetitionLatihan(
            "Squat",
            "Stand with feet shoulder-width, lower hips, return up",
            "Leg Strength, Glutes",
            "MainWorkout",
            3,
            15
        ));
        beginner.addLatihan(new RepetitionLatihan(
            "Push Up (knee)",
            "Knee push up for upper body strength",
            "Upper Body Strength, Core Stability",
            "MainWorkout",
            3,
            10
        ));
        beginner.addLatihan(new DurationLatihan(
            "Child Pose",
            "Kneel and stretch arms forward, relax back",
            "Stretching, Cooldown",
            "Cooldown",
            1,
            30
        ));
        beginner.addLatihan(new DurationLatihan(
            "Standing Stretch",
            "Stand and reach for your toes, hold",
            "Hamstring Stretch, Cooldown",
            "Cooldown",
            1,
            30
        ));
        allDifficulty.add(beginner);

        // Intermediate
        intermediate.addLatihan(new DurationLatihan(
            "High Knees",
            "Run in place bringing knees up high",
            "Cardio, WarmUp",
            "WarmUp",
            2,
            30
        ));
        intermediate.addLatihan(new RepetitionLatihan(
            "Arm Swings",
            "Swing arms forward and backward",
            "Shoulder Mobility, WarmUp",
            "WarmUp",
            1,
            20
        ));
        intermediate.addLatihan(new DurationLatihan(
            "Jump Rope",
            "Jump over imaginary rope",
            "Cardio, Coordination",
            "WarmUp",
            2,
            30
        ));
        intermediate.addLatihan(new DurationLatihan(
            "Plank",
            "Hold plank position on elbows",
            "Core Strength, Endurance",
            "MainWorkout",
            2,
            45
        ));
        intermediate.addLatihan(new RepetitionLatihan(
            "Squat Jump",
            "Squat then jump explosively",
            "Leg Power, Cardio",
            "MainWorkout",
            4,
            15
        ));
        intermediate.addLatihan(new RepetitionLatihan(
            "Push Up",
            "Standard push up for upper body",
            "Upper Body Strength, Core Stability",
            "MainWorkout",
            4,
            12
        ));
        intermediate.addLatihan(new DurationLatihan(
            "Cobra Stretch",
            "Lie face down, push chest up",
            "Back Stretch, Cooldown",
            "Cooldown",
            1,
            30
        ));
        intermediate.addLatihan(new DurationLatihan(
            "Seated Hamstring",
            "Sit and reach for toes",
            "Hamstring Stretch, Cooldown",
            "Cooldown",
            1,
            30
        ));
        allDifficulty.add(intermediate);

        // Advanced
        advanced.addLatihan(new RepetitionLatihan(
            "Jumping Lunges",
            "Lunge and jump to switch legs",
            "Leg Power, Cardio",
            "WarmUp",
            3,
            20
        ));
        advanced.addLatihan(new RepetitionLatihan(
            "Skater Jumps",
            "Jump side to side like a speed skater",
            "Lateral Power, Cardio",
            "WarmUp",
            3,
            20
        ));
        advanced.addLatihan(new RepetitionLatihan(
            "Burpees (slow)",
            "Full body movement, slow pace",
            "Full Body, Cardio",
            "WarmUp",
            4,
            10
        ));
        advanced.addLatihan(new RepetitionLatihan(
            "Burpees",
            "Full body movement, fast pace",
            "Full Body, Cardio",
            "MainWorkout",
            5,
            15
        ));
        advanced.addLatihan(new RepetitionLatihan(
            "Plank to Pushup",
            "Alternate plank and pushup positions",
            "Core, Upper Body",
            "MainWorkout",
            4,
            15
        ));
        advanced.addLatihan(new DurationLatihan(
            "Mountain Climbers",
            "Run in plank position, knees to chest",
            "Cardio, Core",
            "MainWorkout",
            3,
            45
        ));
        advanced.addLatihan(new DurationLatihan(
            "Forward Fold",
            "Stand and fold forward, relax",
            "Back/Hamstring Stretch, Cooldown",
            "Cooldown",
            1,
            30
        ));
        advanced.addLatihan(new DurationLatihan(
            "Downward Dog",
            "Yoga pose, hips up, stretch back/legs",
            "Full Body Stretch, Cooldown",
            "Cooldown",
            1,
            30
        ));
        allDifficulty.add(advanced);
    }

}
