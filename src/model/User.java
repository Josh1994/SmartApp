package model;

/**
 * User Model
 */
public class User {
    private String username;
    private String password;
    private Boolean isManager;

    public User(String username, String password, Boolean isManager) {
        this.username = username;
        this.password = password;
        this.isManager = isManager;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isManager() {
        return isManager;
    }

    public void setManager(Boolean manager) {
        isManager = manager;
    }

    public String toFileString() {
        return username + "," + password + "," + isManager;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isManager=" + isManager +
                '}';
    }
}
