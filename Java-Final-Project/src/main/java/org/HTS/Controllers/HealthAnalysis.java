package org.HTS.Controllers;

import org.HTS.Models.Diet;
import org.HTS.Models.Exercise;
import org.HTS.Models.Sleep;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

public class HealthAnalysis {
    private static final String DIET_FILE_PATH = "DietFile.txt";
    private static final String EXERCISE_FILE_PATH = "ExerciseFile.txt";
    private static final String SLEEP_FILE_PATH = "SleepFile.txt";
    private UserManagement userManagement;

    public HealthAnalysis(UserManagement userManagement) {
        this.userManagement = userManagement;
    }
    public void healthAnalysisData(Scanner scanner) {

        boolean exit = false;
        while (!exit) {
            System.out.println("1. Daily Caloric Balance");
            System.out.println("2. Sleep Analysis");
            System.out.println("3. Exercise Log");
            System.out.println("4. Health Summary");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    performDailyCaloricBalance();
                    break;
                case 2:
                    performSleepAnalysis();
                    break;
                case 3:
                    performExerciseLog();
                    break;
                case 4:
                    performHealthSummary();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void performDailyCaloricBalance() {
        // Load diet entries
        List<Diet> dietEntries = loadDietEntries();

        // Load exercise entries
        List<Exercise> exerciseEntries = loadExerciseEntries();

        // Calculate daily caloric balance
        Map<Date, Double> dailyCaloricBalance = new HashMap<>();
        for (Diet diet : dietEntries) {
            Date date = diet.getDate();
            double calories = diet.getCalories();
            dailyCaloricBalance.put(date, dailyCaloricBalance.getOrDefault(date, 0.0) + calories);
        }
        for (Exercise exercise : exerciseEntries) {
            Date date = exercise.getDate();
            double caloriesBurned = exercise.getCaloriesBurned();
            dailyCaloricBalance.put(date, dailyCaloricBalance.getOrDefault(date, 0.0) - caloriesBurned);
        }

        // Display daily caloric balance
        System.out.println("Daily Caloric Balance:");
        for (Map.Entry<Date, Double> entry : dailyCaloricBalance.entrySet()) {
            Date date = entry.getKey();
            double balance = entry.getValue();
            System.out.println("Date: " + date + ", Caloric Balance: " + balance);
        }
    }

    private static void performSleepAnalysis() {
        // Load sleep entries
        List<Sleep> sleepEntries = loadSleepEntries();

        // Calculate average hours of sleep per day
        double totalSleepHours = 0;
        for (Sleep sleep : sleepEntries) {
            totalSleepHours += sleep.getTotalSleepHours();
        }
        double averageSleepHours = totalSleepHours / sleepEntries.size();

        // Identify days with less sleep than average
        List<Date> belowAverageSleepDates = new ArrayList<>();
        for (Sleep sleep : sleepEntries) {
            int sleepHours = sleep.getTotalSleepHours();
            if (sleepHours < averageSleepHours) {
                belowAverageSleepDates.add(sleep.getSleepTime());
            }
        }

        // Display sleep analysis
        System.out.println("Sleep Analysis:");
        System.out.println("Average Sleep Hours per Day: " + averageSleepHours);
        System.out.println("Dates with Below Average Sleep Hours: " + belowAverageSleepDates);
    }

    private static void performExerciseLog() {
        // Load exercise entries
        List<Exercise> exerciseEntries = loadExerciseEntries();

        // Display exercise log
        System.out.println("Exercise Log:");
        for (Exercise exercise : exerciseEntries) {
            System.out.println("Type: " + exercise.getType() +
                    ", Duration: " + exercise.getDuration() +
                    ", Calories Burned: " + exercise.getCaloriesBurned());
        }
    }

    private static void performHealthSummary() {
        // Load diet entries
        List<Diet> dietEntries = loadDietEntries();

        // Load exercise entries
        List<Exercise> exerciseEntries = loadExerciseEntries();

        // Load sleep entries
        List<Sleep> sleepEntries = loadSleepEntries();

        // Calculate total calories consumed and burned
        double totalCaloriesConsumed = 0;
        double totalCaloriesBurned = 0;
        for (Diet diet : dietEntries) {
            totalCaloriesConsumed += diet.getCalories();
        }
        for (Exercise exercise : exerciseEntries) {
            totalCaloriesBurned += exercise.getCaloriesBurned();
        }

        // Calculate average daily caloric balance
        double averageCaloricBalance = (totalCaloriesConsumed - totalCaloriesBurned) / dietEntries.size();

        // Identify the most common exercise type
        Map<String, Integer> exerciseTypeCount = new HashMap<>();
        for (Exercise exercise : exerciseEntries) {
            String exerciseType = exercise.getType();
            exerciseTypeCount.put(exerciseType, exerciseTypeCount.getOrDefault(exerciseType, 0) + 1);
        }
        String mostCommonExerciseType = null;
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : exerciseTypeCount.entrySet()) {
            if (entry.getValue() > maxCount) {
                mostCommonExerciseType = entry.getKey();
                maxCount = entry.getValue();
            }
        }

        // Display health summary
        System.out.println("Health Summary:");
        System.out.println("Total Calories Consumed: " + totalCaloriesConsumed);
        System.out.println("Total Calories Burned: " + totalCaloriesBurned);
        System.out.println("Average Daily Caloric Balance: " + averageCaloricBalance);
        System.out.println("Most Common Exercise Type: " + mostCommonExerciseType);
    }

    private static List<Diet> loadDietEntries() {
        List<Diet> dietEntries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(DIET_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(parts[0]);
                String foodItem = parts[1];
                double calories = Double.parseDouble(parts[2]);
                Diet dietEntry = new Diet(date, foodItem, calories);
                dietEntries.add(dietEntry);
            }
        } catch (IOException e) {
            System.out.println("Error occurred while reading the DietFile: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error occurred while parsing the diet entry: " + e.getMessage());
        }

        return dietEntries;
    }

    private static List<Exercise> loadExerciseEntries() {
        List<Exercise> exerciseEntries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(EXERCISE_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(parts[0]);
                String type = parts[1];
                double duration = Double.parseDouble(parts[2]);
                double caloriesBurned = Double.parseDouble(parts[3]);
                Exercise exerciseEntry = new Exercise(date, type, duration, caloriesBurned);
                exerciseEntries.add(exerciseEntry);
            }
        } catch (IOException e) {
            System.out.println("Error occurred while reading the ExerciseFile: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error occurred while parsing the exercise entry: " + e.getMessage());
        }

        return exerciseEntries;
    }

    private static List<Sleep> loadSleepEntries() {
        List<Sleep> sleepEntries = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(SLEEP_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Date sleepTime = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(parts[0]);
                Date wakeTime = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy").parse(parts[1]);
                Sleep sleepEntry = new Sleep(sleepTime, wakeTime);
                sleepEntries.add(sleepEntry);
            }
        } catch (IOException e) {
            System.out.println("Error occurred while reading the SleepFile: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error occurred while parsing the sleep entry: " + e.getMessage());
        }

        return sleepEntries;
    }
}
