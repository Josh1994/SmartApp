package controller;

import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * This class is the manages the access of the user database.
 */
public class UserDatabase {
    public class UserDatabaseException extends IllegalArgumentException {

        public UserDatabaseException() { super(); }
        public UserDatabaseException(String message) { super(message); }
        public UserDatabaseException(String message, Throwable cause) { super(message, cause); }
        public UserDatabaseException(Throwable cause) { super(cause); }
    }


    private static String USER_DATABASE = "users.kpsdb";
    private String pathname;

    private List<User> users;

    public UserDatabase() {
        this(".");
    }

    public UserDatabase(String pathname) {
        this.pathname = pathname + File.separator + USER_DATABASE;
        users = new ArrayList<>();

        File userDatabaseFile = new File(pathname);

        // Create Initial User Database if it doesn't exist yet
        if(!userDatabaseFile.exists()) {
            createInitialDatabase();
        } else {
            loadUserDatabase();
        }

    }

    /**
     * Load user accounts from user database file
     */
    private void loadUserDatabase() {
        // Declare class variables
        users = new ArrayList<>();

        // Create Initial User Database if it doesn't exist yet
        File userDatabaseFile = new File(pathname);
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
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates initial database (only used on first-time program launch)
     */
    private void createInitialDatabase() {
        try {
            FileWriter fileWriter = new FileWriter(pathname);
            fileWriter.append("username,password,isManager").append(System.lineSeparator()); // Header
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Update the user database file based on the data stored in the program.
     */
    private void updateUserDatabase() {
        try {
            FileWriter fileWriter = new FileWriter(pathname);

            StringBuilder stringBuilder = new StringBuilder();
            fileWriter.append("username,password,isManager").append(System.lineSeparator()); // Header
            for (User user : users) {
                stringBuilder.append(user.toFileString()).append(System.lineSeparator());
            }
            fileWriter.write(stringBuilder.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a user's account information
     *
     * @param username the user name of the user account you would like to retrieve
     * @return the User instance if the username exists, otherwise return null/
     */
    public User getUser(String username) {
        for(User user: users) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public List<User> getUsers() {
        return Collections.unmodifiableList(users);
    }

    /**
     * Change a user's username.
     *
     * @param oldUsername the username of the account you would like to modify.
     * @param newUsername the new username for this user account.
     */
    public void changeUserUsername(String oldUsername, String newUsername) {
        User user = getUser(oldUsername);
        if(user == null) {
            throw new UserDatabaseException("You cannot change the username for a user that does not exist.");
        }

        User newUser = getUser(newUsername);

        if(newUser != null) {
            throw new UserDatabaseException("You cannot change the username to one that already exists in the database.");
        }

        user.setUsername(newUsername);
        updateUserDatabase();
    }

    /**
     * Change a user's password.
     *
     * @param username the username of the account you would like to modify.
     * @param password the new password for this user account.
     */
    public void changeUserPassword(String username, String password) {
        User user = getUser(username);
        if(user == null) {
            throw new UserDatabaseException("You cannot update password for a user that does not exist.");
        } else {
            user.setPassword(password);
            updateUserDatabase();
        }

    }

    /**
     * Change a user's permission level.
     *
     * @param username the username of the account you would like to modify.
     * @param isManager the new permission level. Set to true to make a user a manager, otherwise set to false
     *                  if you would the user to be a clerk.
     */
    public void changeUserPermission(String username, boolean isManager) {
        User user = getUser(username);
        if(user == null) {
            throw new UserDatabaseException("You cannot update manager permissions for a user that does not exist.");
        } else {
            user.setManager(isManager);
            updateUserDatabase();
        }
    }


    /**
     * Deletes user from user database.
     *
     * @param username the username of the account you would like to delete.
     */
    public void deleteUser(String username) {
        User userToDelete = getUser(username);

        // Produce error if username does not exists
        if(userToDelete == null) {
            throw new UserDatabaseException("Cannot delete user that does not exists.");
        }

        // Delete user from program
        users.remove(userToDelete);

        // Update changes to database file
        updateUserDatabase();
    }

    /**
     * Adds a new user to the user database
     *
     * @param username the username of the new account.
     * @param password the password used to authenticate this account.
     * @param isManager determines if the new user is a manager or not.
     */
    public void addUser(String username, String password, boolean isManager) {
        // Produce error if username already exists
        if(getUser(username) != null) {
            throw new UserDatabaseException("Cannot add new user as the choosen username already exists in the database.");
        }

        // Create User
        User newUser = new User(username, password, isManager);
        users.add(newUser);

        // Appends new user to the end of the file
        try {
            FileWriter fileWriter = new FileWriter(pathname,true);
            fileWriter.append(newUser.toFileString()).append(System.lineSeparator());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> getManagers() {
        List<User> managers = new ArrayList<>();
        for (User user: getUsers()) {
            if(user.isManager()) {
                managers.add(user);
            }
        }
        return managers;
    }
}
