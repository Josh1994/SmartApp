package gui.dialogs;

import controller.Controller;
import gui.AlertBox;
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
 * This is a dialog box that is allows the user to input and change their username.
 *
 * @author Prashant Bhikhu
 */
public class ChangeUsernameDialog implements EventHandler {
    // Global Components
    private Stage window;
    private Controller controller;

    // Textfields
    private TextField newUsernameTextField;
    private TextField confirmNewUsernameTextField;

    //
    private String newUsername;
    private boolean cancelled = false;

    public ChangeUsernameDialog(Controller controller) {
        this.controller = controller;
    }

    public void display(){
        window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("New Username");

        BorderPane root = new BorderPane();
        Scene scene2 = new Scene(root, 350, 150, Color.WHITE);
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(10));
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        ColumnConstraints column1 = new ColumnConstraints(130);
        ColumnConstraints column2 = new ColumnConstraints(50, 150, 300);
        column2.setHgrow(Priority.ALWAYS);
        gridpane.getColumnConstraints().addAll(column1, column2);

        Label bodyLabel = new Label("Enter your new proposed new username:");
        gridpane.add(bodyLabel,0,0, 2, 1);

        Label newUsernameLabel = new Label("New Username:");
        gridpane.add(newUsernameLabel, 0, 1);

        newUsernameTextField = new TextField();
        gridpane.add(newUsernameTextField, 1, 1);


        Label confirmNewUsernameLabel = new Label("Re-type New Username:");
        gridpane.add(confirmNewUsernameLabel, 0, 2);

        confirmNewUsernameTextField = new PasswordField();
        gridpane.add(confirmNewUsernameTextField, 1, 2);


        Button okayButton = new Button("Okay");
        okayButton.setOnAction(this);
        okayButton.setAlignment(Pos.CENTER);
        okayButton.setPadding(new Insets(5, 20, 5,20));
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.CENTER);
        hbBtn.getChildren().add(okayButton);
        gridpane.add(hbBtn, 0, 3, 2,1);

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
        String tmpNewUsername = newUsernameTextField.getText();
        String tmpConfirmNewUsername = confirmNewUsernameTextField.getText();

        // Validate Inputs
        if(tmpNewUsername.length() == 0) {
            // Not enough characters
            AlertBox.display("Invalid Fields", "Please input a valid newUsername.");
            return;
        }

        if(!tmpNewUsername.equals(tmpConfirmNewUsername)) {
            // Matching Passwords needed
            AlertBox.display("Invalid Fields", "Matching usernames is required as a confirmation.");
            return;
        }

        /* Should never get to the code below, because being in execution path implies that the database has no users.
        if (controller.getUserDatabase().getUser(tmpUsername) != null) {
            // ERROR MESSAGE - USER EXISTS ALREADY
            AlertBox.display("Username already in use", "Please input a another newUsername, as this " +
                    "is already taken.");
            return;
        }*/

        // Store new newUsername and password
        newUsername = tmpNewUsername;

        // Close window
        window.close();
    }

    public String getNewUsername() {
        return newUsername;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
