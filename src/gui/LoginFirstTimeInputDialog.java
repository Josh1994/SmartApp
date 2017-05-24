package gui;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Created by Prashant on 24/05/2017.
 */
public class LoginFirstTimeInputDialog implements EventHandler<ActionEvent> {
    private Stage window;
    private Controller controller;

    private TextField usernameTextField;
    private TextField passwordTextField;

    private String username;
    private String password;

    public LoginFirstTimeInputDialog(Controller controller) {
        this.controller = controller;
    }

    public void display(){
        window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("First Time Login");
        window.setMinWidth(250);
        window.setMinHeight(100);

        Label bodyLabel = new Label("Please create the first manager account for the system.");

        Label usernameLabel = new Label("Username:");
        usernameTextField = new TextField();
        HBox usernameHBox = new HBox(usernameLabel, usernameTextField);
        usernameHBox.setSpacing(10);
        usernameHBox.setAlignment(Pos.CENTER);

        Label passwordLabel = new Label("Password:");
        passwordTextField = new TextField();
        HBox passwordHBox = new HBox(passwordLabel, passwordTextField);
        passwordHBox.setSpacing(10);
        passwordHBox.setAlignment(Pos.CENTER);

        Button okayButton = new Button("Okay");
        okayButton.setOnAction(this);
        okayButton.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(bodyLabel, usernameHBox, passwordHBox, okayButton, new Label());
        vBox.setSpacing(20);

        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();
    }

    @Override
    public void handle(ActionEvent event) {
        String tmpUsername = usernameTextField.getText();
        String tmpPassword = passwordTextField.getText();

        if(tmpUsername.length() == 0) {
            // ERROR MESSAGE - TOO SHORT
            return;
        }

        if (controller.getUserDatabase().getUser(tmpUsername) != null) {
            // ERROR MESSAGE - USER EXISTS ALREADY
            return;
        }

        username = tmpUsername;
        password = tmpPassword;
        window.close();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
