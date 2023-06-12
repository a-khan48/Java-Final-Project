package org.HTS.Controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SaveData {
    public static void saveUsers(List<String> users) {
        try {
            FileWriter writer = new FileWriter("Users.txt");
            for (String user : users) {
                writer.write(user + "\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<String> loadUsers() {
        List<String> users = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("Users.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                users.add(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return users;
    }
}