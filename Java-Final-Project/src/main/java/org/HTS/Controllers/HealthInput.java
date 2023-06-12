package org.HTS.Controllers;

import org.HTS.Models.Diet;
import org.HTS.Models.Exercise;
import org.HTS.Models.Sleep;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

public class HealthInput {
    private static final String DIET_FILE_PATH = "DietFile.txt";
    private static final String EXERCISE_FILE_PATH = "ExerciseFile.txt";
    private static final String SLEEP_FILE_PATH = "SleepFile.txt";
    private UserManagement userManagement;

    public HealthInput(UserManagement userManagement) {
        this.userManagement = userManagement;
    }

    public void healthDataInput(Scanner scanner) {
        boolean inputRunning = true;

        while (inputRunning) {
            System.out.println("Health Data Input");
            System.out.println("1. Daily Calorie Intake");
            System.out.println("2. Exercise Activities");
            System.out.println("3. Sleep Records");
            System.out.println("0. Back");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    performDietEntry(scanner);
                    break;
                case 2:
                    performExerciseEntry(scanner);
                    break;
                case 3:
                    logSleepRecords(scanner);
                    break;
                case 0:
                    inputRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void performDietEntry(Scanner scanner) {
        System.out.print("Enter the food item: ");
        String foodItem = scanner.nextLine();
        System.out.print("Enter the caloric value: ");
        double caloricValue = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        Diet dietEntry = new Diet(new Date(), foodItem, caloricValue);

        // Store the diet entry in the DietFile
        try (FileWriter fileWriter = new FileWriter(DIET_FILE_PATH, true)) {
            fileWriter.write(dietEntry.getDate() + "," + dietEntry.getFoodItem() + "," + dietEntry.getCalories() + "\n");
        } catch (IOException e) {
            System.out.println("Error occurred while writing to the DietFile: " + e.getMessage());
        }

        System.out.println("Diet entry added successfully.");
    }

    private void performExerciseEntry(Scanner scanner) {
        System.out.print("Enter the exercise type: ");
        String exerciseType = scanner.nextLine();
        System.out.print("Enter the exercise duration in minutes: ");
        int duration = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Enter the estimated calories burned: ");
        double caloriesBurned = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        Exercise exerciseEntry = new Exercise(new Date(), exerciseType, duration, caloriesBurned);

        // Store the exercise entry in the ExerciseFile
        try (FileWriter fileWriter = new FileWriter(EXERCISE_FILE_PATH, true)) {
            fileWriter.write(exerciseEntry.getDate() + "," + exerciseEntry.getType() + "," + exerciseEntry.getDuration() + "," + exerciseEntry.getCaloriesBurned() + "\n");
        } catch (IOException e) {
            System.out.println("Error occurred while writing to the ExerciseFile: " + e.getMessage());
        }

        System.out.println("Exercise entry added successfully.");
    }

//    private void performSleepEntry(Scanner scanner) {
//        System.out.print("Enter the time you went to sleep (HH:MM format): ");
//        String sleepTimeStr = scanner.nextLine();
//        System.out.print("Enter the time you woke up (HH:MM format): ");
//        String wakeupTimeStr = scanner.nextLine();
//
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
//            Date sleepTime = sdf.parse(sleepTimeStr);
//            Date wakeupTime = sdf.parse(wakeupTimeStr);
//
//            Sleep sleepEntry = new Sleep(sleepTime, wakeupTime);
//
//            // Store the sleep entry in the SleepFile
//            try (FileWriter fileWriter = new FileWriter(SLEEP_FILE_PATH, true)) {
//                fileWriter.write(sleepEntry.getSleepTime() + "," + sleepEntry.getWakeTime() + "\n");
//            } catch (IOException e) {
//                System.out.println("Error occurred while writing to the SleepFile: " + e.getMessage());
//            }
//
//            System.out.println("Sleep entry added successfully.");
//        } catch (Exception e) {
//            System.out.println("Invalid time format. Sleep entry not added.");
//        }
//    }
private void logSleepRecords(Scanner scanner) {
    System.out.print("Enter the time you went to sleep (hh:mm): ");
    String sleepTimeStr = scanner.nextLine();

    System.out.print("Enter the time you woke up (hh:mm): ");
    String wakeUpTimeStr = scanner.nextLine();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    LocalTime sleepTime = LocalTime.parse(sleepTimeStr, formatter);
    LocalTime wakeUpTime = LocalTime.parse(wakeUpTimeStr, formatter);

    Duration sleepDuration = Duration.between(sleepTime, wakeUpTime);
    long hoursOfSleep = sleepDuration.toHours();

    try {
        PrintWriter writer = new PrintWriter(new FileWriter("SleepFile.txt", true));
        writer.println("Sleep Time: " + sleepTime.format(formatter));
        writer.println("Wake Up Time: " + wakeUpTime.format(formatter));
        writer.println("Total Hours of Sleep: " + hoursOfSleep);
        writer.println(); // Add an empty line between sleep entries
        writer.close();
        System.out.println("Sleep record saved to SleepFile.txt");
    } catch (IOException e) {
        System.out.println("An error occurred while saving the sleep file.");
        e.printStackTrace();
    }
}
}
