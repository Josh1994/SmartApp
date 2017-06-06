package gui;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * This is a custom dialog box that is displayed when the database has no users. This allows the new user to create
 * a new account so they can use the application. Should only appear on first run of the application.
 */
public class LoginFirstTimeInputDialog implements EventHandler {
    // Global Components
    private Stage window;
    private Controller controller;

    // Textfields
    private TextField usernameTextField;
    private TextField passwordTextField;
    private TextField confirmPasswordTextField;

    //
    private String username;
    private String password;

    public LoginFirstTimeInputDialog(Controller controller) {
        this.controller = controller;
    }

    public void display(){
        window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("First Time Login");

        BorderPane root = new BorderPane();
        Scene scene2 = new Scene(root, 350, 200, Color.WHITE);
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(10));
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        ColumnConstraints column1 = new ColumnConstraints(100);
        ColumnConstraints column2 = new ColumnConstraints(50, 150, 300);
        column2.setHgrow(Priority.ALWAYS);
        gridpane.getColumnConstraints().addAll(column1, column2);

        Label bodyLabel = new Label("Please create the first manager account for the system.");
        gridpane.add(bodyLabel,0,0, 2, 1);

        Label usernameLabel = new Label("Username:");
        gridpane.add(usernameLabel, 0, 1);

        usernameTextField = new TextField();
        gridpane.add(usernameTextField, 1, 1);

        Label passwordLabel = new Label("Password:");
        gridpane.add(passwordLabel, 0, 2);

        passwordTextField = new PasswordField();
        gridpane.add(passwordTextField, 1, 2);


        Label confirmPasswordLabel = new Label("Re-type Password:");
        gridpane.add(confirmPasswordLabel, 0, 3);

        confirmPasswordTextField = new PasswordField();
        gridpane.add(confirmPasswordTextField, 1, 3);


        Button okayButton = new Button("Okay");
        okayButton.setOnAction(this);
        okayButton.setAlignment(Pos.CENTER);
        okayButton.setPadding(new Insets(5, 20, 5,20));
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(okayButton);
        gridpane.add(hbBtn, 0, 4, 2,1);

        root.setCenter(gridpane);

        window.setOnCloseRequest(this);
        window.setScene(scene2);
        window.showAndWait();
    }

    @Override
    public void handle(Event event) {
        if(event instanceof WindowEvent) {
            event.consume();
            return;
        }

        // Retrieve inputs
        String tmpUsername = usernameTextField.getText();
        String tmpPassword = passwordTextField.getText();
        String tmpConfirmPassword = confirmPasswordTextField.getText();

        // Validate Inputs
        if(tmpUsername.length() == 0 || tmpPassword.length() == 0) {
            // Not enough characters
            AlertBox.display("Invalid Fields", "Please input a valid username and/or password.");
            return;
        }

        if(!tmpPassword.equals(tmpConfirmPassword)) {
            // Matching Passwords needed
            AlertBox.display("Invalid Fields", "Matching password is required as a confirmation.");
            return;
        }

        /* Should never get to the code below, because being in execution path implies that the database has no users.
        if (controller.getUserDatabase().getUser(tmpUsername) != null) {
            // ERROR MESSAGE - USER EXISTS ALREADY
            AlertBox.display("Username already in use", "Please input a another username, as this " +
                    "is already taken.");
            return;
        }*/

        // Store new username and password
        username = tmpUsername;
        password = tmpPassword;

        // Close window
        window.close();
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }
}
