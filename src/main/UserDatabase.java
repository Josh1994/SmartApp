package main;

import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Prashant on 23/05/2017.
 */
public class UserDatabase {
    private static final String USER_DATABASE = "user.config";

    private List<User> users;
    private User loggedIn;

    public UserDatabase() {
        // Declare class variables
        users = new ArrayList<>();

        File userDatabaseFile = new File(USER_DATABASE);

        // Create Initial User Database if it doesn't exist yet
        if(!userDatabaseFile.exists()) {
            createInitialDatabase();
        } else {
            loadUserDatabase();
        }
    }

    private void loadUserDatabase() {
        // Declare class variables
        users = new ArrayList<>();

        File userDatabaseFile = new File(USER_DATABASE);

        // Create Initial User Database if it doesn't exist yet
        if(!userDatabaseFile.exists()) {
            createInitialDatabase();
        }

        try {
            Scanner scanner = new Scanner(userDatabaseFile);
            scanner.nextLine(); // skip header line in config file

            while(scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] lineSplit = line.split(",");


                String username = lineSplit[0];
                String password = lineSplit[1];
                boolean isManager = Boolean.parseBoolean(lineSplit[2]);

                User user = new User(username,password,isManager);
                users.add(user);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createInitialDatabase() {
        try {
            FileWriter fileWriter = new FileWriter(USER_DATABASE);
            // Header
            fileWriter.append("username,password,isManager").append(System.lineSeparator());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean userExists(String username) {
        for(User user: users) {
            if(user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public User getUser(String username) {
        for(User user: users) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }
}
