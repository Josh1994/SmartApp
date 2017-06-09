package gui;

import controller.Controller;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a custom dialog box that is displayed when the database has no users. This allows the new user to create
 * a new account so they can use the application. Should only appear on first run of the application.
 */
public class ListUserAccountBox implements EventHandler {
    // Global Components
    private Stage window;
    private Controller controller;

    private ListView listView;
    private String action;
    private boolean onForceClose = false;

    public ListUserAccountBox(Controller controller) {
        this.controller = controller;
    }

    public void display(String title, String action){
        this.window = new Stage();
        this.action = action;

        this.window.initModality(Modality.APPLICATION_MODAL);
        this.window.setTitle(title);

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

        Label bodyLabel = new Label("Select users you would like to "+action+":");
        gridpane.add(bodyLabel,0,0, 2, 1);


        listView = new ListView();
        listView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        for(User user: controller.getUserDatabase().getUsers()) {
            listView.getItems().add(user.getUsername());
        }
        gridpane.add(listView, 0, 1,2,1);



        Button okayButton = new Button("Okay");
        okayButton.setAlignment(Pos.CENTER);
        okayButton.setPadding(new Insets(5, 20, 5,20));
        okayButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this);

        Button cancelButton = new Button("Cancel");
        cancelButton.setAlignment(Pos.CENTER);
        cancelButton.setPadding(new Insets(5, 20, 5,20));
        cancelButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this);

        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().addAll(okayButton, cancelButton);
        gridpane.add(hbBtn, 1, 2, 1,1);

        root.setCenter(gridpane);

        window.setOnCloseRequest(this);
        window.setScene(scene2);
        window.showAndWait();
    }

    @Override
    public void handle(Event event) {
        if(event instanceof MouseEvent) {
            Button button = (Button) event.getSource();

            switch (button.getText()) {
                case "Cancel":
                    onForceClose = true;
                    break;
                case "Okay":
                    if (listView.getSelectionModel().getSelectedIndices().size() == 0) {
                        AlertBox.display("Selection Error", "Please select one or more username for" +
                                " the " + action + " operation to continue.");
                    }
                    break;
                default:
                    throw new IllegalArgumentException("Event was not handled properly.");
            }
            // Close window
            window.close();
        } else if(event instanceof WindowEvent) {
            onForceClose = true;
        }
    }

    public ObservableList getSelectedIndices() {
        return onForceClose ? null : listView.getSelectionModel().getSelectedIndices();
    }

    public ObservableList getListItems() {
        return onForceClose ? null : listView.getItems();
    }
}
