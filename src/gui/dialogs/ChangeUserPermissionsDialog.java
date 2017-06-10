package gui.dialogs;

import controller.Controller;
import gui.AlertBox;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This is a dialog box that is displayed when the a logged-in manager wants to change user's permission.
 * It displays a dropdown list of users which that the user can select, and they they can choose whether the
 * user is a a manager or not.
 *
 * @author Prashant Bhikhu
 */
public class ChangeUserPermissionsDialog implements EventHandler {
    // Global Components
    private Stage window;
    private Controller controller;

    // Textfields
    private ChoiceBox userSelectChoiceBox;
    private ChoiceBox userPermissionChoiceBox;

    private String selectedUsername;
    private boolean newIsManager;
    private boolean cancelled = false;

    public ChangeUserPermissionsDialog(Controller controller) {
        this.controller = controller;
    }

    public void display(){
        window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Change User Permissions");

        BorderPane root = new BorderPane();
        Scene scene2 = new Scene(root, 350, 160, Color.WHITE);
        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(10));
        gridpane.setHgap(10);
        gridpane.setVgap(10);
        ColumnConstraints column1 = new ColumnConstraints(130);
        ColumnConstraints column2 = new ColumnConstraints(50, 150, 300);
        column2.setHgrow(Priority.ALWAYS);
        gridpane.getColumnConstraints().addAll(column1, column2);

        Label bodyLabel = new Label("Select a user to change their permissions:");
        gridpane.add(bodyLabel,0,0, 2, 1);

        Label usernameLabel = new Label("Users:");
        gridpane.add(usernameLabel, 0, 1);

        userSelectChoiceBox = new ChoiceBox();
        userSelectChoiceBox.setMinWidth(150);
        List<String> tempUsernameList = new ArrayList<>();
        for(User user: controller.getUserDatabase().getUsers()) {
            tempUsernameList.add(user.getUsername());
        }
        Collections.sort(tempUsernameList);
        userSelectChoiceBox.getItems().addAll(tempUsernameList);
        userSelectChoiceBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                String newSelectedUsername = (String) userSelectChoiceBox.getItems().get(newValue.intValue());
                User newSelectedUser = controller.getUserDatabase().getUser(newSelectedUsername);

                if(newSelectedUser.isManager()) {
                    userPermissionChoiceBox.getSelectionModel().select(0);
                } else {
                    userPermissionChoiceBox.getSelectionModel().select(1);
                }
            }
        });
        gridpane.add(userSelectChoiceBox, 1, 1);

        Label passwordLabel = new Label("Manager Permission:");
        gridpane.add(passwordLabel, 0, 2);

        userPermissionChoiceBox = new ChoiceBox();
        userPermissionChoiceBox.setMinWidth(150);
        userPermissionChoiceBox.getItems().addAll("Yes", "No");
        gridpane.add(userPermissionChoiceBox, 1, 2);


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

        // validate input
        if(userSelectChoiceBox.getValue() == null) {
            AlertBox.display("Change User Permissions Error", "You must select a user.");
            return;
        }

        // Store new password
        selectedUsername = (String) userSelectChoiceBox.getValue();
        newIsManager = userPermissionChoiceBox.getValue().equals("Yes");

        // Close window
        window.close();
    }


    public String getUsername() {
        return selectedUsername;
    }

    public boolean getIsManager() {
        return newIsManager;
    }

    public boolean isCancelled() {
        return cancelled;
    }
}
