package org.HTS;

import org.HTS.Controllers.HealthAnalysis;
import org.HTS.Controllers.HealthInput;
import org.HTS.Controllers.UserManagement;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserManagement userManager = new UserManagement();
        HealthInput healthInput = new HealthInput(userManager);

        boolean loggedIn = userManager.userLogin(scanner);

        if (loggedIn) { // user: ahmad
            System.out.println("Logged In Successfully!");
            runHealthMenu(); // Menu to prompt Health Input or Health Analyzer

        }


    }// end of login

    public static void runHealthMenu() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        UserManagement userManager = new UserManagement();
        HealthInput manager = new HealthInput(userManager);
        HealthAnalysis data = new HealthAnalysis(userManager);

        while (running) {
            System.out.println("1. Health Data Input");
            System.out.println("2. Health Data Analysis");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    manager.healthDataInput(scanner); // Passing in scanner object as an argument
                    break;
                case 2:
                    data.healthAnalysisData(scanner);
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        scanner.close();
    }
}