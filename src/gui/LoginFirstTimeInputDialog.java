package gui;

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
public class LoginFirstTimeInputDialog {
    private Stage window;

    public static void display(){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("First Time Login");
        window.setMinWidth(250);
        window.setMinHeight(100);

        Label bodyLabel = new Label("Please create the first manager account for the system.");

        Label usernameLabel = new Label("Username:");
        TextField usernameTextField = new TextField();
        HBox usernameHBox = new HBox(usernameLabel, usernameTextField);
        usernameHBox.setSpacing(10);
        usernameHBox.setAlignment(Pos.CENTER);

        Label passwordLabel = new Label("Password:");
        TextField passwordField = new TextField();
        HBox passwordHBox = new HBox(passwordLabel, passwordField);
        passwordHBox.setSpacing(10);
        passwordHBox.setAlignment(Pos.CENTER);

        Button okayButton = new Button("Okay");
        okayButton.setOnAction(e -> window.close());
        okayButton.setAlignment(Pos.CENTER);

        VBox vBox = new VBox(bodyLabel, usernameHBox, passwordHBox, okayButton, new Label());
        vBox.setSpacing(20);

        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();
    }

}
