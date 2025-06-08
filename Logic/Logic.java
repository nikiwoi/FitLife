package Logic;

import java.util.*;
import Model.*;
import java.io.*;
import java.nio.file.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Logic {
    Scanner s = new Scanner(System.in);
    ArrayList<User> UserList = new ArrayList<>();
    ArrayList<DifficultyLatihan> allDifficulty = new ArrayList<>();
    User currentUser = null;
    ArrayList<Latihan> warmUp = new ArrayList<>();
    ArrayList<Latihan> mainWorkout = new ArrayList<>();
    ArrayList<Latihan> cooldown = new ArrayList<>();
    int WarmUpCount = 3;
    int MainWorkoutCount = 0;
    int CooldownCount = 2;

    public void run() {
        Initialize();
        MainMenu();
    }

    public void MainMenu() {
        boolean running = true;
        int choice = -1;
        do {
            System.out.println();
            System.out.println("=============================");
            System.out.println("     Welcome to FitLife!     ");
            System.out.println("=============================");
            System.out.println();
            System.out.println("1. Login to your account");
            System.out.println("2. Register a new account");
            System.out.println("0. Exit");
            boolean valid = false;
            do {
                System.out.print(">  ");
                try {
                    choice = s.nextInt();
                    s.nextLine();
                    if (choice >= 0 && choice <= 2) {
                        valid = true;
                    } else {
                        System.out.println("Invalid choice. Please enter a number between 0 and 2.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number between 0 and 2.");
                    s.nextLine();
                }
            } while (!valid);
            switch (choice) {
                case 1:
                    Login();
                    break;
                case 2:
                    Register();
                    break;
                case 0:
                    System.out.println("Thank you for using FitLife! Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (running);
    }

    public void Initialize() {
        UserList.clear();
        String filePath = "./Data/Users.txt";
        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty())
                    continue;
                // Each user is stored in 7 lines, separated by a blank line
                String username = line.trim();
                String password = br.readLine().trim();
                int usia = Integer.parseInt(br.readLine().trim());
                double beratBadan = Double.parseDouble(br.readLine().trim());
                double tinggiBadan = Double.parseDouble(br.readLine().trim());
                String level = br.readLine().trim();
                int targetWeight = Integer.parseInt(br.readLine().trim());
                UserList.add(new User(username, password, usia, beratBadan, tinggiBadan, level, targetWeight));
                // Skip possible blank line between users
                br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Failed to load users: " + e.getMessage());
        }
        InitializeLatihan i = new InitializeLatihan();
        allDifficulty = i.initializeLatihan();
    }

    public void Login() {
        boolean Running = true;
        do {
            System.out.println();
            System.out.println();
            System.out.println("=== Login ===");
            System.out.print("Enter Username: ");
            String username = s.next() + s.nextLine();
            System.out.print("Enter Password: ");
            String password = s.next() + s.nextLine();

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
        String username = s.next() + s.nextLine();

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
        String password = s.next() + s.nextLine();

        int usia = 0;
        while (true) {
            System.out.print("Enter your Age: ");
            try {
                usia = s.nextInt();
                s.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                s.nextLine();
            }
        }

        double beratBadan = 0;
        while (true) {
            System.out.print("Enter your weight (kg): ");
            try {
                beratBadan = s.nextDouble();
                s.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                s.nextLine();
            }
        }

        double tinggiBadan = 0;
        while (true) {
            System.out.print("Enter your height (cm): ");
            try {
                tinggiBadan = s.nextDouble();
                s.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                s.nextLine();
            }
        }

        int targetWeight = 0;
        while (true) {
            System.out.print("Enter your target weight (kg): ");
            try {
                targetWeight = s.nextInt();
                s.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                s.nextLine();
            }
        }

        String recommendedLevel = rekomendasiLevel(usia, beratBadan, tinggiBadan);
        System.out.println("Your BMI is : " + (int) (beratBadan / ((tinggiBadan / 100) * (tinggiBadan / 100))));
        System.out.println("According to your BMI we recommend : " + "\u001B[32m" + recommendedLevel + "\u001B[0m");
        System.out.print("Enter your difficulty level : ");
        System.out.println();

        String[] levels = { "Beginner", "Intermediate", "Advanced" };
        for (int i = 0; i < levels.length; i++) {
            if (levels[i].equalsIgnoreCase(recommendedLevel)) {
                System.out.println((i + 1) + ". \u001B[32m" + levels[i] + "\u001B[0m");
            } else {
                System.out.println((i + 1) + ". " + levels[i]);
            }
        }

        int levelchoice = 0;
        while (true) {
            System.out.print("> ");
            try {
                levelchoice = s.nextInt();
                s.nextLine();
                if (levelchoice >= 1 && levelchoice <= 3) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                s.nextLine();
            }
        }

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

        User newUser = new User(username, password, usia, beratBadan, tinggiBadan, level, targetWeight);
        UserList.add(newUser);
        currentUser = newUser;

        saveUsersToTXT();
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
            System.out.println(" ================================= ");
            System.out.println("|             FitLife             |");
            System.out.println("|=================================|");
            System.out.println("|1. Generate Today's Workout      |");
            System.out.println("|---------------------------------|");
            System.out.println("|2. View Calories Burnt Today     |");
            System.out.println("|3. View Workout History          |");
            System.out.println("|---------------------------------|");
            System.out.println("|4. Set your Daily Meals          |");
            System.out.println("|5. Set your Weight Target        |");
            System.out.println("|---------------------------------|");
            System.out.println("|6. Update User Profile           |");
            System.out.println("|0. Logout                        |");
            System.out.println(" ================================= ");
            boolean valid = false;
            do {
                System.out.print(">  ");
                try {
                    choice = s.nextInt();
                    s.nextLine();
                    if (choice >= 0 && choice <= 6) {
                        valid = true;
                    } else {
                        System.out.println("Invalid choice. Please enter a number between 0 and 6.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number between 0 and 6.");
                    s.nextLine();
                }
            } while (!valid);

            switch (choice) {
                case 1:
                    GenerateLatihanToday();
                    break;
                case 2:
                    ViewCaloriesBurnt();
                    break;
                case 3:
                    ViewWorkoutHistory();
                    break;
                case 4:
                    CalculateDailyMeals();
                    break;
                case 5:
                    SetWeightTarget();
                    break;
                case 6:
                    UpdateUserProfile();
                    break;
                case 0:
                    System.out.println("Logging out...");
                    System.out.println();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (running);
    }

    private void saveUsersToTXT() {
        String filePath = "./Data/Users.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false))) {
            for (User user : UserList) {
                bw.write(user.getUsername() + "\n");
                bw.write(user.getPassword() + "\n");
                bw.write(user.getUsia() + "\n");
                bw.write(user.getBeratBadan() + "\n");
                bw.write(user.getTinggiBadan() + "\n");
                bw.write(user.getLevel() + "\n");
                bw.write(user.getTargetWeight() + "\n");
                bw.write("\n"); // blank line between users
            }
        } catch (IOException e) {
            System.out.println("Failed to update users: " + e.getMessage());
        }
    }

    public void generateLatihan() {
        DifficultyLatihan selected = null;
        for (DifficultyLatihan diff : allDifficulty) {
            if (diff.getDifficultyLevel().equalsIgnoreCase(currentUser.getLevel())) {
                selected = diff;
                break;
            }
        }

        for (Latihan latihan : selected.getLatihanList()) {
            if (latihan.getKategoriLatihan().equalsIgnoreCase("WarmUp")) {
                warmUp.add(latihan);
            } else if (latihan.getKategoriLatihan().equalsIgnoreCase("MainWorkout")) {
                mainWorkout.add(latihan);
            } else if (latihan.getKategoriLatihan().equalsIgnoreCase("Cooldown")) {
                cooldown.add(latihan);
            }
        }
    }

    public void GenerateLatihanToday() {
        String todayDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String workoutFile = "./Data/" + currentUser.getUsername() + "_workout.txt";
        boolean alreadyDone = false;

        // Check if today's workout already exists
        File historyFile = new File(workoutFile);
        if (historyFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(historyFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("Workout Date:")) {
                        String dateStr = line.substring("Workout Date:".length()).trim();
                        if (dateStr.equals(todayDate)) {
                            alreadyDone = true;
                            break;
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("Failed to read workout history: " + e.getMessage());
            }
        }

        if (alreadyDone) {
            System.out.println("You have already completed today's workout. See you tomorrow!");
            return;
        }

        generateLatihan();

        Collections.shuffle(mainWorkout);

        if (currentUser.getLevel().equalsIgnoreCase("Beginner")) {
            WarmUpCount = 2;
            MainWorkoutCount = 5;
            CooldownCount = 2;
        } else if (currentUser.getLevel().equalsIgnoreCase("Intermediate")) {
            WarmUpCount = 3;
            MainWorkoutCount = 6;
            CooldownCount = 3;
        } else if (currentUser.getLevel().equalsIgnoreCase("Advanced")) {
            WarmUpCount = 4;
            MainWorkoutCount = 7;
            CooldownCount = 4;
        }

        System.out.println("=== Today's Workout Plan ===");
        System.out.println();
        System.out.println("Warm-Up:");
        for (int i = 0; i < WarmUpCount; i++) {
            if (warmUp.get(i).getTipeLatihan().equalsIgnoreCase("Repetition")) {
                System.out.println((i + 1) + ". " + warmUp.get(i).getNamaLatihan() + " ("
                        + ((RepetitionLatihan) warmUp.get(i)).getRepetition() + " reps)");
            } else if (warmUp.get(i).getTipeLatihan().equalsIgnoreCase("Duration")) {
                System.out.println((i + 1) + ". " + warmUp.get(i).getNamaLatihan() + " ("
                        + ((DurationLatihan) warmUp.get(i)).getDuration() + " seconds)");
            }
        }
        System.out.println();
        System.out.println("Main Workout:");
        for (int i = 0; i < MainWorkoutCount; i++) {
            if (mainWorkout.get(i).getTipeLatihan().equalsIgnoreCase("Repetition")) {
                System.out.println((i + 1) + ". " + mainWorkout.get(i).getNamaLatihan() + " ("
                        + ((RepetitionLatihan) mainWorkout.get(i)).getRepetition() + " reps)");
            } else if (mainWorkout.get(i).getTipeLatihan().equalsIgnoreCase("Duration")) {
                System.out.println((i + 1) + ". " + mainWorkout.get(i).getNamaLatihan() + " ("
                        + ((DurationLatihan) mainWorkout.get(i)).getDuration() + " seconds)");
            }
        }
        System.out.println();
        System.out.println("Cooldown:");
        for (int i = 0; i < CooldownCount; i++) {
            if (cooldown.get(i).getTipeLatihan().equalsIgnoreCase("Repetition")) {
                System.out.println((i + 1) + ". " + cooldown.get(i).getNamaLatihan() + " ("
                        + ((RepetitionLatihan) cooldown.get(i)).getRepetition() + " reps)");
            } else if (cooldown.get(i).getTipeLatihan().equalsIgnoreCase("Duration")) {
                System.out.println((i + 1) + ". " + cooldown.get(i).getNamaLatihan() + " ("
                        + ((DurationLatihan) cooldown.get(i)).getDuration() + " seconds)");
            }
        }

        System.out.println();
        boolean workoutMenu = true;
        while (workoutMenu) {
            System.out.println();
            System.out.println("==============================");
            System.out.println("1. View Workout Steps");
            System.out.println("2. View Workout Benefits");
            System.out.println("3. Done with Workout");
            System.out.println("0. Back to Main Menu");
            int choice = -1;
            boolean valid = false;
            do {
                System.out.print("> ");
                try {
                    choice = s.nextInt();
                    s.nextLine();
                    if (choice >= 0 && choice <= 3) {
                        valid = true;
                    } else {
                        System.out.println("Invalid choice. Please enter a number between 0 and 3.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number between 0 and 3.");
                    s.nextLine();
                }
            } while (!valid);

            switch (choice) {
                case 1:
                    ViewWorkoutSteps();
                    break;
                case 2:
                    ViewWorkoutBenefits();
                    break;
                case 3:
                    int totalKalori = 0;
                    for (int i = 0; i < WarmUpCount; i++) {
                        if (warmUp.get(i).getTipeLatihan().equalsIgnoreCase("Repetition")) {
                            totalKalori += warmUp.get(i).getKaloriPerLatihan()
                                    * ((RepetitionLatihan) warmUp.get(i)).getRepetition();
                        } else if (warmUp.get(i).getTipeLatihan().equalsIgnoreCase("Duration")) {
                            totalKalori += warmUp.get(i).getKaloriPerLatihan()
                                    * ((DurationLatihan) warmUp.get(i)).getDuration();
                        }
                    }
                    for (int i = 0; i < MainWorkoutCount; i++) {
                        if (mainWorkout.get(i).getTipeLatihan().equalsIgnoreCase("Repetition")) {
                            totalKalori += mainWorkout.get(i).getKaloriPerLatihan()
                                    * ((RepetitionLatihan) mainWorkout.get(i)).getRepetition();
                        } else if (mainWorkout.get(i).getTipeLatihan().equalsIgnoreCase("Duration")) {
                            totalKalori += mainWorkout.get(i).getKaloriPerLatihan()
                                    * ((DurationLatihan) mainWorkout.get(i)).getDuration();
                        }
                    }
                    for (int i = 0; i < CooldownCount; i++) {
                        if (cooldown.get(i).getTipeLatihan().equalsIgnoreCase("Repetition")) {
                            totalKalori += cooldown.get(i).getKaloriPerLatihan()
                                    * ((RepetitionLatihan) cooldown.get(i)).getRepetition();
                        } else if (cooldown.get(i).getTipeLatihan().equalsIgnoreCase("Duration")) {
                            totalKalori += cooldown.get(i).getKaloriPerLatihan()
                                    * ((DurationLatihan) cooldown.get(i)).getDuration();
                        }
                    }
                    System.out.println("Workout completed! You have burned a total of " + totalKalori + " kcal.");
                    String workoutSummary = generateWorkoutSummary(warmUp, mainWorkout, cooldown, WarmUpCount,
                            MainWorkoutCount,
                            CooldownCount, todayDate);
                    workoutSummary += "Total calories burned: " + totalKalori + " kcal\n";
                    saveWorkoutHistory(currentUser.getUsername(), workoutSummary);
                    workoutMenu = false; // exit the loop after done
                    break;
                case 0:
                    workoutMenu = false; // back to main menu
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    public void ViewWorkoutSteps() {
        System.out.println();
        System.out.println("=== Workout Steps ===");
        System.out.println();
        System.out.println("Warm-Up:");
        for (int i = 0; i < WarmUpCount; i++) {
            System.out.println((i + 1) + ". " + warmUp.get(i).getNamaLatihan());
            System.out.println("   Step: " + warmUp.get(i).getDeskripsiLatihan());
        }
        System.out.println();
        System.out.println("Main Workout:");
        for (int i = 0; i < MainWorkoutCount; i++) {
            System.out.println((i + 1) + ". " + mainWorkout.get(i).getNamaLatihan());
            System.out.println("   Step: " + mainWorkout.get(i).getDeskripsiLatihan());
        }
        System.out.println();
        System.out.println("Cooldown:");
        for (int i = 0; i < CooldownCount; i++) {
            System.out.println((i + 1) + ". " + cooldown.get(i).getNamaLatihan());
            System.out.println("   Step: " + cooldown.get(i).getDeskripsiLatihan());
        }
    }

    public void ViewWorkoutBenefits() {
        System.out.println();
        System.out.println("=== Workout Benefits ===");
        System.out.println();
        System.out.println("Warm-Up:");
        for (int i = 0; i < WarmUpCount; i++) {
            System.out.println((i + 1) + ". " + warmUp.get(i).getNamaLatihan());
            System.out.println("   Benefit: " + warmUp.get(i).getManfaatLatihan());
        }
        System.out.println();
        System.out.println("Main Workout:");
        for (int i = 0; i < MainWorkoutCount; i++) {
            System.out.println((i + 1) + ". " + mainWorkout.get(i).getNamaLatihan());
            System.out.println("   Benefit: " + mainWorkout.get(i).getManfaatLatihan());
        }
        System.out.println();
        System.out.println("Cooldown:");
        for (int i = 0; i < CooldownCount; i++) {
            System.out.println((i + 1) + ". " + cooldown.get(i).getNamaLatihan());
            System.out.println("   Benefit: " + cooldown.get(i).getManfaatLatihan());
        }
    }

    public void ViewCaloriesBurnt() {
        String today = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String workoutFile = "./Data/" + currentUser.getUsername() + "_workout.txt";
        String mealsFile = "./Data/" + currentUser.getUsername() + "_meals.txt";

        int totalCaloriesBurned = 0;
        boolean workoutToday = false;
        double totalCaloriesIntake = 0;
        boolean mealsToday = false;

        // Cek workout hari ini & ambil total kalori terbakar
        File historyFile = new File(workoutFile);
        if (historyFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(historyFile))) {
                String line;
                boolean isToday = false;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("Workout Date:")) {
                        String dateStr = line.substring("Workout Date:".length()).trim();
                        isToday = dateStr.equals(today);
                        if (isToday)
                            workoutToday = true;
                    }
                    if (isToday && line.startsWith("Total calories burned:")) {
                        String[] parts = line.split(":");
                        if (parts.length > 1) {
                            String calStr = parts[1].replace("kcal", "").trim();
                            try {
                                totalCaloriesBurned = Integer.parseInt(calStr);
                            } catch (NumberFormatException e) {
                                totalCaloriesBurned = 0;
                            }
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Failed to read workout history: " + e.getMessage());
            }
        }

        // Cek meals hari ini & ambil total kalori masuk
        File mealFile = new File(mealsFile);
        if (mealFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(mealFile))) {
                String line;
                boolean isToday = false;
                while ((line = br.readLine()) != null) {
                    if (line.trim().equals(today)) {
                        isToday = true;
                        mealsToday = true;
                    } else if (isToday && line.startsWith("Total calories intake:")) {
                        String[] parts = line.split(":");
                        if (parts.length > 1) {
                            String calStr = parts[1].replace("kcal", "").trim();
                            try {
                                totalCaloriesIntake = Double.parseDouble(calStr);
                            } catch (NumberFormatException ex) {
                                totalCaloriesIntake = 0;
                            }
                        }
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Failed to read meal history: " + e.getMessage());
            }
        }

        // Tampilkan hasil
        System.out.println("=== Calories Summary for Today ===");
        System.out.println("Total calories burned (workout): " + totalCaloriesBurned + " kcal");
        System.out.println("Total calories intake (meals): " + totalCaloriesIntake + " kcal");
        double netCalories = totalCaloriesBurned - totalCaloriesIntake;
        System.out.println("Total calories burned (net): " + netCalories + " kcal");
        if (netCalories > 0) {
            System.out.println("You are in a calorie deficit today");
        } else if (netCalories < 0) {
            System.out.println("You are in a calorie surplus today");
        } else {
            System.out.println("Your calories are balanced today");
        }

        System.out.println("====================================");
        System.out.println("1. View Calories Progress");
        System.out.println("0. Back to Main Menu");
        System.out.print("> ");
        int choice = s.nextInt();

        if (choice == 1) {
            if (currentUser.getTargetWeight() == 0) {
                System.out.println("You have not set a target weight yet. Please set your target weight first.");
                SetWeightTarget();
            }
            ViewCaloriesProgress();
        } else if (choice == 0) {
            return;
        } else {
            System.out.println("Invalid choice. Returning to main menu.");
        }
    }

    public void ViewCaloriesProgress() {
        System.out.println();
        System.out.println("=== Calories Progress ===");
        double currentWeight = currentUser.getBeratBadan();
        int targetWeight = currentUser.getTargetWeight();

        if (targetWeight == 0 || targetWeight >= currentWeight) {
            System.out.println("Please set a valid target weight first.");
            return;
        }

        // Read workout history
        ArrayList<String> workoutDates = new ArrayList<>();
        ArrayList<Integer> workoutCalories = new ArrayList<>();
        String workoutFile = "./Data/" + currentUser.getUsername() + "_workout.txt";
        File historyFile = new File(workoutFile);
        if (historyFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(workoutFile))) {
                String line;
                String date = null;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("Workout Date:")) {
                        date = line.substring("Workout Date:".length()).trim();
                    }
                    if (date != null && line.startsWith("Total calories burned:")) {
                        String[] parts = line.split(":");
                        if (parts.length > 1) {
                            String calStr = parts[1].replace("kcal", "").trim();
                            try {
                                workoutDates.add(date);
                                workoutCalories.add(Integer.parseInt(calStr));
                            } catch (NumberFormatException e) {
                                // skip
                            }
                        }
                        date = null;
                    }
                }
            } catch (IOException e) {
                System.out.println("Failed to read workout history: " + e.getMessage());
            }
        }

        // Read meal history
        ArrayList<String> mealDates = new ArrayList<>();
        ArrayList<Double> mealCalories = new ArrayList<>();
        String mealsFile = "./Data/" + currentUser.getUsername() + "_meals.txt";
        File mealFile = new File(mealsFile);
        if (mealFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(mealsFile))) {
                String line;
                String date = null;
                while ((line = br.readLine()) != null) {
                    if (line.trim().matches("\\d{4}-\\d{2}-\\d{2}")) {
                        date = line.trim();
                    }
                    if (date != null && line.startsWith("Total calories intake:")) {
                        String[] parts = line.split(":");
                        if (parts.length > 1) {
                            String calStr = parts[1].replace("kcal", "").trim();
                            try {
                                mealDates.add(date);
                                mealCalories.add(Double.parseDouble(calStr));
                            } catch (NumberFormatException e) {
                                // skip
                            }
                        }
                        date = null;
                    }
                }
            } catch (IOException e) {
                System.out.println("Failed to read meal history: " + e.getMessage());
            }
        }

        // Calculate total net calories for days that exist in both lists
        double totalNetCalories = 0;

        for (int i = 0; i < workoutDates.size(); i++) {
            totalNetCalories += workoutCalories.get(i);
        }
        for (int i = 0; i < mealDates.size(); i++) {
            totalNetCalories -= mealCalories.get(i);
        }

        // Calculate target calories
        double weightToLose = currentWeight - targetWeight;
        double targetCalories = weightToLose * 7700;

        System.out.println("Current Weight: " + currentWeight + " kg");
        System.out.println("Target Weight: " + targetWeight + " kg");
        System.out.println("-------------------------------");
        System.out.println("Total Calories you have Burned: " + totalNetCalories + " kcal");
        System.out.println("Target Calories to Burn: " + targetCalories + " kcal");
        System.out.println("-------------------------------");
        System.out.println(totalNetCalories + " kcal ---> " + targetCalories + " kcal");
        if (totalNetCalories >= targetCalories) {
            System.out.println("Congratulations! You have reached your target weight!");
        } else {
            double remainingCalories = targetCalories - totalNetCalories;
            System.out.println("You need to burn " + remainingCalories + " more calories");
        }
    }

    public void ViewWorkoutHistory() {
        System.out.println();
        System.out.println("=== Workout History ===");
        String filePath = "./Data/" + currentUser.getUsername() + "_workout.txt";
        File historyFile = new File(filePath);
        if (!historyFile.exists()) {
            System.out.println("No workout history found.");
            return;
        }
        try (BufferedReader br = new BufferedReader(new FileReader(historyFile))) {
            String line;
            boolean hasHistory = false;
            while ((line = br.readLine()) != null) {
                hasHistory = true;
                System.out.println(line);
            }
            if (!hasHistory) {
                System.out.println("No workout history found.");
            }
        } catch (IOException e) {
            System.out.println("Failed to read workout history: " + e.getMessage());
        }
    }

    private String generateWorkoutSummary(ArrayList<Latihan> warmUp, ArrayList<Latihan> mainWorkout,
            ArrayList<Latihan> cooldown,
            int WarmUpCount, int MainWorkoutCount, int CooldownCount, String todayDate) {
        StringBuilder workoutSummary = new StringBuilder();
        workoutSummary.append("Workout Date: ").append(todayDate).append("\n");
        workoutSummary.append("Warm-Up:\n");
        for (int i = 0; i < WarmUpCount; i++) {
            Latihan l = warmUp.get(i);
            if (l.getTipeLatihan().equalsIgnoreCase("Repetition")) {
                workoutSummary.append("- ").append(l.getNamaLatihan()).append(" (")
                        .append(((RepetitionLatihan) l).getRepetition()).append(" reps)\n");
            } else if (l.getTipeLatihan().equalsIgnoreCase("Duration")) {
                workoutSummary.append("- ").append(l.getNamaLatihan()).append(" (")
                        .append(((DurationLatihan) l).getDuration()).append(" seconds)\n");
            }
        }
        workoutSummary.append("Main Workout:\n");
        for (int i = 0; i < MainWorkoutCount; i++) {
            Latihan l = mainWorkout.get(i);
            if (l.getTipeLatihan().equalsIgnoreCase("Repetition")) {
                workoutSummary.append("- ").append(l.getNamaLatihan()).append(" (")
                        .append(((RepetitionLatihan) l).getRepetition()).append(" reps)\n");
            } else if (l.getTipeLatihan().equalsIgnoreCase("Duration")) {
                workoutSummary.append("- ").append(l.getNamaLatihan()).append(" (")
                        .append(((DurationLatihan) l).getDuration()).append(" seconds)\n");
            }
        }
        workoutSummary.append("Cooldown:\n");
        for (int i = 0; i < CooldownCount; i++) {
            Latihan l = cooldown.get(i);
            if (l.getTipeLatihan().equalsIgnoreCase("Repetition")) {
                workoutSummary.append("- ").append(l.getNamaLatihan()).append(" (")
                        .append(((RepetitionLatihan) l).getRepetition()).append(" reps)\n");
            } else if (l.getTipeLatihan().equalsIgnoreCase("Duration")) {
                workoutSummary.append("- ").append(l.getNamaLatihan()).append(" (")
                        .append(((DurationLatihan) l).getDuration()).append(" seconds)\n");
            }
        }
        return workoutSummary.toString();
    }

    private void saveWorkoutHistory(String username, String workoutSummary) {
        String filePath = "./Data/" + username + "_workout.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(workoutSummary);
            bw.write("\n");
        } catch (IOException e) {
            System.out.println("Failed to save workout history: " + e.getMessage());
        }
    }

    public void CalculateDailyMeals() {
        System.out.println();
        System.out.println("=== Set Your Daily Meals ===");
        String todayDate = new java.text.SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String mealsFile = "./Data/" + currentUser.getUsername() + "_meals.txt";

        // Check if meals are already set for today
        boolean alreadySet = false;
        File file = new File(mealsFile);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                boolean isToday = false;
                while ((line = br.readLine()) != null) {
                    if (line.trim().equals(todayDate)) {
                        isToday = true;
                    } else if (isToday && line.trim().isEmpty()) {
                        // End of today's meals
                        break;
                    }
                    if (isToday) {
                        alreadySet = true;
                    }
                }
            } catch (IOException e) {
                System.out.println("Failed to read meal history: " + e.getMessage());
            }
        }

        if (alreadySet) {
            System.out.print(
                    "You have already set your daily meals for today. Do you want to change today's meal (y/n)? ");
            String change = s.next() + s.nextLine();
            if (change.equalsIgnoreCase("y")) {
                // Remove today's entry from the file
                ArrayList<String> lines = new ArrayList<>();
                try (BufferedReader br = new BufferedReader(new FileReader(mealsFile))) {
                    String line;
                    boolean skip = false;
                    while ((line = br.readLine()) != null) {
                        if (line.trim().equals(todayDate)) {
                            skip = true;
                            continue;
                        }
                        if (skip && line.trim().isEmpty()) {
                            skip = false;
                            continue;
                        }
                        if (!skip) {
                            lines.add(line);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Failed to update meal history: " + e.getMessage());
                }
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(mealsFile, false))) {
                    for (String l : lines) {
                        bw.write(l);
                        bw.newLine();
                    }
                } catch (IOException e) {
                    System.out.println("Failed to update meal history: " + e.getMessage());
                }
                alreadySet = false;
            } else {
                return;
            }
        }

        StringBuilder mealSummary = new StringBuilder();
        double totalCalories = 0;

        // Input meals
        String[] times = { "Breakfast", "Lunch", "Dinner", "Snack" };
        for (String meal : times) {
            System.out.print("Enter your " + meal + " (- to skip): ");
            String food = s.next() + s.nextLine();

            if (food.equals("-")) {
                continue;
            }

            double calories = getCaloriesAPI(food);
            mealSummary.append(meal).append(": ").append(food).append(" (").append(calories).append(" kcal)\n");
            System.out.println("Calories for " + food + ": " + calories + " kcal");
            totalCalories += calories;
        }

        mealSummary.append("Total calories intake: ").append(totalCalories).append(" kcal\n");
        System.out.println(mealSummary.toString());

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(mealsFile, true))) {
            bw.write(todayDate + "\n" + mealSummary.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Failed to save meal history: " + e.getMessage());
        }
    }

    private double getCaloriesAPI(String query) {
        try {
            String apiUrl = "https://trackapi.nutritionix.com/v2/natural/nutrients";
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("x-app-id", "9349888e");
            conn.setRequestProperty("x-app-key", "7539abea0fa15a7ceac250bc33dc92dc");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            String jsonInputString = "{\"query\": \"" + query + "\"}";
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder apiResponse = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    apiResponse.append(inputLine);
                }
                in.close();

                String resp = apiResponse.toString();
                double total = 0;
                int idx = 0;
                while ((idx = resp.indexOf("\"nf_calories\"", idx)) != -1) {
                    int startCal = resp.indexOf(":", idx) + 1;
                    int endCal = resp.indexOf(",", startCal);
                    if (endCal == -1)
                        endCal = resp.indexOf("}", startCal);
                    String calStr = resp.substring(startCal, endCal).trim();
                    total += Double.parseDouble(calStr);
                    idx = endCal;
                }
                if (total > 0) {
                    return total;
                }
            }
        } catch (Exception e) {
            System.out.println("Error calling Nutritionix API: " + e.getMessage());
        }

        System.out.print("Calories for \"" + query + "\" not found. Please enter the calories manually (kcal): ");
        double manualCal = s.nextDouble();
        s.nextLine();
        return manualCal;
    }

    public void SetWeightTarget() {
        int targetWeight = 0;
        boolean Running = true;
        System.out.println();
        System.out.println("=== Set Your Weight Target ===");
        System.out.println("Your current weight: " + currentUser.getBeratBadan() + " kg");
        System.out.println("==============================");
        do {
            System.out.print("Enter your target weight (kg): ");
            try {
                targetWeight = s.nextInt();
                s.nextLine();
                if (targetWeight <= 0) {
                    System.out.println("Invalid target weight. Please enter a positive number.");
                    Running = true;
                } else if (targetWeight >= currentUser.getBeratBadan()) {
                    System.out.println("Target weight must be less than your current weight.");
                    Running = true;
                } else {
                    Running = false;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                s.nextLine();
            }
        } while (Running);
        currentUser.setTargetWeight(targetWeight);
        System.out.println("Your target weight has been set to " + targetWeight + " kg");
        saveUsersToTXT();
    }

    public void UpdateUserProfile() {
        boolean Running = true;
        do {
            System.out.println();
            System.out.println("=== Update User Profile ===");
            System.out.println("Your current profile:");

            System.out.println("1. Username: " + currentUser.getUsername());
            System.out.println("2. Password: " + currentUser.getPassword());
            System.out.println("3. Age: " + currentUser.getUsia());
            System.out.println("4. Weight: " + currentUser.getBeratBadan() + " kg");
            System.out.println("5. Difficulty Level: " + currentUser.getLevel());
            System.out.println("0. Exit to Main Menu");
            int choice = -1;
            boolean valid = false;
            do {
                System.out.print("> ");
                try {
                    choice = s.nextInt();
                    s.nextLine();
                    if (choice >= 0 && choice <= 5) {
                        valid = true;
                    } else {
                        System.out.println("Invalid choice. Please enter a number between 0 and 5.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number between 0 and 5.");
                    s.nextLine();
                }
            } while (!valid);

            switch (choice) {
                case 1:
                    System.out.print("Enter your new username: ");
                    String newUsername = s.next() + s.nextLine();
                    currentUser.setUsername(newUsername);
                    System.out.println("Username updated successfully.");
                    break;
                case 2:
                    System.out.print("Enter your new password: ");
                    String newPassword = s.next() + s.nextLine();
                    currentUser.setPassword(newPassword);
                    System.out.println("Password updated successfully.");
                    break;
                case 3:
                    int newUsia = 0;
                    while (true) {
                        System.out.print("Enter your new age: ");
                        try {
                            newUsia = s.nextInt();
                            s.nextLine();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            s.nextLine();
                        }
                    }
                    currentUser.setUsia(newUsia);
                    System.out.println("Age updated successfully.");
                    break;
                case 4:
                    double newBeratBadan = 0;
                    while (true) {
                        System.out.print("Enter your new weight (kg): ");
                        try {
                            newBeratBadan = s.nextDouble();
                            s.nextLine();
                            break;
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            s.nextLine();
                        }
                    }
                    currentUser.setBeratBadan(newBeratBadan);
                    System.out.println("Weight updated successfully.");
                    SetWeightTarget();
                    break;
                case 5:
                    String recommendedLevel = rekomendasiLevel(currentUser.getUsia(), currentUser.getBeratBadan(),
                            currentUser.getTinggiBadan());
                    System.out.println(
                            "Your BMI is : " + (int) (currentUser.getBeratBadan()
                                    / ((currentUser.getTinggiBadan() / 100) * (currentUser.getTinggiBadan() / 100))));
                    System.out.println(
                            "According to your BMI we recommend : " + "\u001B[32m" + recommendedLevel + "\u001B[0m");
                    System.out.print("Enter your new difficulty level : ");
                    System.out.println();

                    String[] levels = { "Beginner", "Intermediate", "Advanced" };
                    for (int i = 0; i < levels.length; i++) {
                        if (levels[i].equalsIgnoreCase(recommendedLevel)) {
                            System.out.println((i + 1) + ". \u001B[32m" + levels[i] + "\u001B[0m");
                        } else {
                            System.out.println((i + 1) + ". " + levels[i]);
                        }
                    }
                    int levelchoice = 0;
                    while (true) {
                        System.out.print("> ");
                        try {
                            levelchoice = s.nextInt();
                            s.nextLine();
                            if (levelchoice >= 1 && levelchoice <= 3) {
                                break;
                            } else {
                                System.out.println("Invalid choice. Please enter 1, 2, or 3.");
                            }
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Please enter a valid number.");
                            s.nextLine();
                        }
                    }
                    switch (levelchoice) {
                        case 1:
                            System.out.println("You have chosen Beginner level.");
                            currentUser.setLevel("Beginner");
                            break;
                        case 2:
                            System.out.println("You have chosen Intermediate level.");
                            currentUser.setLevel("Intermediate");
                            break;
                        case 3:
                            System.out.println("You have chosen Advanced level.");
                            currentUser.setLevel("Advanced");
                            break;
                        default:
                            break;
                    }
                    break;
                case 0:
                    Running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (Running);

        saveUsersToTXT();
        System.out.println("User profile updated successfully.");
        System.out.println("Returning to main menu...");
    }
}
