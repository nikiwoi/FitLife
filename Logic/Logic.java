package Logic;

import java.util.*;
import Object.*;
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
                if (parts.length == 7) {
                    String username = parts[0];
                    String password = parts[1];
                    int usia = Integer.parseInt(parts[2]);
                    double beratBadan = Double.parseDouble(parts[3]);
                    double tinggiBadan = Double.parseDouble(parts[4]);
                    String level = parts[5];
                    int targetWeight = Integer.parseInt(parts[6]);
                    UserList.add(new User(username, password, usia, beratBadan, tinggiBadan, level, targetWeight));
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to load users: " + e.getMessage());
        }
        InitializeLatihan i = new InitializeLatihan();
        allDifficulty = i.inisialisasiLatihan();
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
        System.out.print("Enter your target weight (kg): ");
        int targetWeight = s.nextInt();
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

        User newUser = new User(username, password, usia, beratBadan, tinggiBadan, level, targetWeight);
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
            System.out.println("=============================");
            System.out.println("           FitLife           ");
            System.out.println("=============================");
            System.out.println("1. Generate Today's Workout");
            System.out.println("2. Generate This Week's Workout");
            System.out.println("3. View Calories Burnt Today");
            System.out.println("4. View Workout History");
            System.out.println("5. Set Your Weight Target");
            System.out.println("6. Set your Daily Meals");
            System.out.println("7. Show your Weight Progress");
            System.out.println("8. Update User Profile");
            System.out.println("9. Logout");
            System.out.print(">  ");
            choice = s.nextInt();

            switch (choice) {
                case 1:
                    GenerateLatihanToday();
                    break;
                case 2:
                    GenerateLatihanThisWeek();
                    break;
                case 3:
                    ViewCaloriesBurnt();
                    break;
                case 4:
                    ShowWorkoutHistory();
                    break;
                case 5:
                    SetWeightTarget();
                    break;
                case 6:
                    CalculateDailyMeals();
                    break;
                case 7:
                    showWeightProgress();
                    break;
                case 8:
                    UpdateUserProfile();
                    break;
                case 9:
                    System.out.println("Logging out...");
                    System.out.println();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (running);
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
                        user.getLevel(),
                        String.valueOf(user.getTargetWeight()));
                bw.write(userData);
                bw.newLine();
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

        System.out.println("Are you done with your workout? (yes/no)");
        String response = s.next();
        while (!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no")) {
            System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            response = s.next();
        }
        if (response.equalsIgnoreCase("yes")) {
            String workoutSummary = generateWorkoutSummary(warmUp, mainWorkout, cooldown, WarmUpCount, MainWorkoutCount,
                    CooldownCount);
            saveWorkoutHistory(currentUser.getUsername(), workoutSummary);
        } else {
            System.out.println("Workout not completed. No history saved.");
            return;
        }
    }

    public void GenerateLatihanThisWeek() {
        generateLatihan();
        System.out.println(" === This Week's Workout ===");
        for (int i = 0; i < 7; i++) {
            System.out.println("--- Day " + (i + 1) + " ---");
            for (int j = 0; j < MainWorkoutCount; j++) {
                if (mainWorkout.get(j).getTipeLatihan().equalsIgnoreCase("Repetition")) {
                    System.out.println((j + 1) + ". " + mainWorkout.get(j).getNamaLatihan() + " ("
                            + ((RepetitionLatihan) mainWorkout.get(j)).getRepetition() + " reps)");
                } else if (mainWorkout.get(j).getTipeLatihan().equalsIgnoreCase("Duration")) {
                    System.out.println((j + 1) + ". " + mainWorkout.get(j).getNamaLatihan() + " ("
                            + ((DurationLatihan) mainWorkout.get(j)).getDuration() + " seconds)");
                }
            }
            Collections.shuffle(mainWorkout);
            System.out.println();
        }
    }

    public void ViewCaloriesBurnt() {
        String today = new java.text.SimpleDateFormat("yyyyMMdd").format(new Date());
        String workoutFile = "./Data/" + currentUser.getUsername() + "_history.txt";
        String mealsFile = "./Data/" + currentUser.getUsername() + "_meals.txt";

        boolean workoutToday = false;
        boolean mealsToday = false;

        // Cek workout hari ini
        File historyFile = new File(workoutFile);
        if (historyFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(historyFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("Workout Date:")) {
                        String dateStr = line.substring("Workout Date:".length()).trim();
                        String entryDate = new java.text.SimpleDateFormat("yyyyMMdd").format(new Date(dateStr));
                        if (entryDate.equals(today)) {
                            workoutToday = true;
                            break;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("Failed to read workout history: " + e.getMessage());
            }
        }

        // Cek meals hari ini
        File mealFile = new File(mealsFile);
        if (mealFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(mealFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    try {
                        java.util.Date date = new java.util.Date(line.trim());
                        String entryDate = new java.text.SimpleDateFormat("yyyyMMdd").format(date);
                        if (entryDate.equals(today)) {
                            mealsToday = true;
                            break;
                        }
                    } catch (Exception e) {
                        // skip non-date lines
                    }
                }
            } catch (Exception e) {
                System.out.println("Failed to read meal history: " + e.getMessage());
            }
        }

        if (!workoutToday && !mealsToday) {
            System.out.println("You must complete today's workout and set your daily meals before viewing calories burnt.");
            return;
        } else if (!workoutToday) {
            System.out.println("You must complete today's workout before viewing calories burnt.");
            return;
        } else if (!mealsToday) {
            System.out.println("You must set your daily meals before viewing calories burnt.");
            return;
        }

        // Jika sudah workout dan meals hari ini, lanjutkan fungsi aslinya
        String filePath = "./Data/" + currentUser.getUsername() + "_history.txt";
        File historyFile2 = new File(filePath);
        if (!historyFile2.exists()) {
            System.out.println("No workout history found.");
            return;
        }

        int totalCalories = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(historyFile2))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                // Match lines like: 1. Exercise Name (20 reps)
                if (line.matches("\\d+\\. .+\\(\\d+ reps\\)")) {
                    // Extract exercise name and reps
                    int idxStart = line.indexOf(". ") + 2;
                    int idxParen = line.lastIndexOf(" (");
                    String namaLatihan = line.substring(idxStart, idxParen).trim();
                    int reps = Integer.parseInt(line.substring(line.lastIndexOf("(") + 1, line.lastIndexOf(" reps")));
                    Latihan latihan = findLatihanByName(namaLatihan);
                    if (latihan instanceof RepetitionLatihan) {
                        int kaloriPerLatihan = latihan.getKaloriPerLatihan();
                        totalCalories += reps * kaloriPerLatihan;
                    }
                } else if (line.matches("\\d+\\. .+\\(\\d+ seconds\\)")) {
                    // Extract exercise name and seconds
                    int idxStart = line.indexOf(". ") + 2;
                    int idxParen = line.lastIndexOf(" (");
                    String namaLatihan = line.substring(idxStart, idxParen).trim();
                    int seconds = Integer
                            .parseInt(line.substring(line.lastIndexOf("(") + 1, line.lastIndexOf(" seconds")));
                    Latihan latihan = findLatihanByName(namaLatihan);
                    if (latihan instanceof DurationLatihan) {
                        int kaloriPerLatihan = latihan.getKaloriPerLatihan();
                        totalCalories += seconds * kaloriPerLatihan;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Failed to read workout history: " + e.getMessage());
            return;
        }

        System.out.println("You have burned a total of " + totalCalories + " kcal!");
    }

    private Latihan findLatihanByName(String namaLatihan) {
        for (DifficultyLatihan diff : allDifficulty) {
            for (Latihan l : diff.getLatihanList()) {
                if (l.getNamaLatihan().equalsIgnoreCase(namaLatihan)) {
                    return l;
                }
            }
        }
        return null;
    }

    public void ShowWorkoutHistory() {
        System.out.println();
        System.out.println("=== Workout History ===");
        String filePath = "./Data/" + currentUser.getUsername() + "_history.txt";
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

    private String generateWorkoutSummary(List<Latihan> warmUp, List<Latihan> mainWorkout, List<Latihan> cooldown,
            int WarmUpCount, int MainWorkoutCount, int CooldownCount) {
        StringBuilder sb = new StringBuilder();
        sb.append("Workout Date: ").append(new Date()).append("\n");
        sb.append("Warm-Up:\n");
        for (int i = 0; i < WarmUpCount; i++) {
            Latihan l = warmUp.get(i);
            if (l.getTipeLatihan().equalsIgnoreCase("Repetition")) {
                sb.append((i + 1)).append(". ").append(l.getNamaLatihan()).append(" (")
                        .append(((RepetitionLatihan) l).getRepetition()).append(" reps)\n");
            } else if (l.getTipeLatihan().equalsIgnoreCase("Duration")) {
                sb.append((i + 1)).append(". ").append(l.getNamaLatihan()).append(" (")
                        .append(((DurationLatihan) l).getDuration()).append(" seconds)\n");
            }
        }
        sb.append("Main Workout:\n");
        for (int i = 0; i < MainWorkoutCount; i++) {
            Latihan l = mainWorkout.get(i);
            if (l.getTipeLatihan().equalsIgnoreCase("Repetition")) {
                sb.append((i + 1)).append(". ").append(l.getNamaLatihan()).append(" (")
                        .append(((RepetitionLatihan) l).getRepetition()).append(" reps)\n");
            } else if (l.getTipeLatihan().equalsIgnoreCase("Duration")) {
                sb.append((i + 1)).append(". ").append(l.getNamaLatihan()).append(" (")
                        .append(((DurationLatihan) l).getDuration()).append(" seconds)\n");
            }
        }
        sb.append("Cooldown:\n");
        for (int i = 0; i < CooldownCount; i++) {
            Latihan l = cooldown.get(i);
            if (l.getTipeLatihan().equalsIgnoreCase("Repetition")) {
                sb.append((i + 1)).append(". ").append(l.getNamaLatihan()).append(" (")
                        .append(((RepetitionLatihan) l).getRepetition()).append(" reps)\n");
            } else if (l.getTipeLatihan().equalsIgnoreCase("Duration")) {
                sb.append((i + 1)).append(". ").append(l.getNamaLatihan()).append(" (")
                        .append(((DurationLatihan) l).getDuration()).append(" seconds)\n");
            }
        }
        sb.append("====================================\n");
        return sb.toString();
    }

    private void saveWorkoutHistory(String username, String workoutSummary) {
        String filePath = "./Data/" + username + "_history.txt";
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(workoutSummary);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Failed to save workout history: " + e.getMessage());
        }
    }

    public void SetWeightTarget() {
        System.out.println();
        System.out.println("=== Set Your Weight Target ===");
        System.out.println("Your current weight: " + currentUser.getBeratBadan() + " kg");
        System.out.println("==============================");
        System.out.print("Enter your target weight (kg): ");
        int targetWeight = s.nextInt();
        currentUser.setTargetWeight(targetWeight);
        System.out.println("Your target weight has been set to " + targetWeight + " kg");
        saveUsersToCSV();
    }

    public void CalculateDailyMeals() {
        String[] times = { "Breakfast", "Lunch", "Dinner" };
        double totalCalories = 0;
        StringBuilder summary = new StringBuilder();
        summary.append("=== Today's Meals ===\n");

        s.nextLine();

        for (String meal : times) {
            System.out.print("Enter your " + meal + " (- to skip): ");
            String food = s.nextLine();

            if (food.equals("-")) {
                continue;
            }

            double calories = getCaloriesAPI(food);
            summary.append(meal).append(": ").append(food).append(" (").append(calories).append(" kcal)\n");
            System.out.println("Calories for " + food + ": " + calories + " kcal");
            totalCalories += calories;
        }

        summary.append("Total calories today: ").append(totalCalories).append(" kcal\n");
        System.out.println(summary.toString());

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("./Data/" + currentUser.getUsername() + "_meals.txt", true))) {
            bw.write(new Date() + "\n" + summary.toString() + "\n");
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
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                String resp = response.toString();
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
            System.out.println();
            System.out.println("0. Exit to Main Menu");
            System.out.print("> ");
            int choice = s.nextInt();
            s.nextLine();

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

    public void showWeightProgress() {
        
    }
}
