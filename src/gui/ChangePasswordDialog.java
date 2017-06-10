package gui;

import controller.Controller;
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
public class ChangePasswordDialog implements EventHandler {
    // Global Components
    private Stage window;
    private Controller controller;

    // Textfields
    private TextField oldPasswordTextField;
    private TextField newPasswordTextField;
    private TextField confirmNewPasswordTextField;

    private String password;
    private boolean cancelled = false;

    public ChangePasswordDialog(Controller controller) {
        this.controller = controller;
    }

    public void display(){
        window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Change Password");

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

        Label bodyLabel = new Label("Type your old password and your new password twice to confirm the change.");
        gridpane.add(bodyLabel,0,0, 2, 1);

        Label usernameLabel = new Label("Old Password:");
        gridpane.add(usernameLabel, 0, 1);

        oldPasswordTextField = new PasswordField();
        gridpane.add(oldPasswordTextField, 1, 1);

        Label passwordLabel = new Label("Password:");
        gridpane.add(passwordLabel, 0, 2);

        newPasswordTextField = new PasswordField();
        gridpane.add(newPasswordTextField, 1, 2);


        Label confirmPasswordLabel = new Label("Re-type Password:");
        gridpane.add(confirmPasswordLabel, 0, 3);

        confirmNewPasswordTextField = new PasswordField();
        gridpane.add(confirmNewPasswordTextField, 1, 3);


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
            window.close();
            cancelled = true;
            return;
        }

        // Retrieve inputs
        String tmpOldPassword = oldPasswordTextField.getText();
        String tmpNewPassword = newPasswordTextField.getText();
        String tmpConfirmNewPassword = confirmNewPasswordTextField.getText();

        // Validate Inputs
        if(tmpOldPassword.length() == 0) {
            // Not enough characters
            AlertBox.display("Invalid Fields", "Please input your current password.");
            return;
        }
        if(!tmpOldPassword.equals(controller.getLoggedInUser().getPassword())) {
            // incorrect password
            AlertBox.display("Invalid Fields", "Please input your current password correctly.");
            return;
        }

        if(tmpNewPassword.length() == 0) {
            // Not enough characters
            AlertBox.display("Invalid Fields", "Please input a valid new password.");
            return;
        }
        if(!tmpNewPassword.equals(tmpConfirmNewPassword)) {
            // Matching Passwords needed
            AlertBox.display("Invalid Fields", "Matching password is required as a confirmation.");
            return;
        }


        // Store new password
        password = tmpNewPassword;

        // Close window
        window.close();
    }


    String getNewPassword() {
        return password;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
