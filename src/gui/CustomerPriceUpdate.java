package gui;

import controller.Controller;
import gui.base.DataEntryGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by phoal on 5/27/2017.
 */
public class CustomerPriceUpdate implements DataEntryGUI,EventHandler<ActionEvent> {
    Button eventButton;
    Button backButton;
    ComboBox<String> fromText;
    ComboBox<String> toText;
    TextField weight;
    TextField volume;
    TextField priority;

    static Scene scene;
    Controller controller;



    public CustomerPriceUpdate(Controller controller){

        this.controller = controller;

        eventButton = new Button();
        eventButton.setText("Create Event");
        eventButton.setOnAction(this);

        backButton = new Button();
        backButton.setText("Back");
        backButton.setOnAction(this);

        Label fromLabel = new Label("Origin");
        fromLabel.setMinHeight(25);
        //fromText = new TextField();
        //fromText.setMaxHeight(10);
        //fromText.setMaxWidth(200);
        fromText = new ComboBox<String>();
        fromText.setMinWidth(200.0);
        fromText.getItems().addAll(controller.getEventProcessor().getLocationNames());
        fromText.setEditable(false);

        Label toLabel = new Label("Destination");
        toLabel.setMinHeight(25);
        toText = new ComboBox<String>();
        toText.setMinWidth(200.0);
        toText.getItems().addAll(controller.getEventProcessor().getLocationNames());
        toText.setEditable(false);

        Label weightLabel = new Label("Price/gram");
        weightLabel.setMinHeight(25);
        weight = new TextField();
        weight.setMaxHeight(10);
        weight.setMaxWidth(200);

        Label volumeLabel = new Label("Price/volume");
        volumeLabel.setMinHeight(25);
        volume = new PasswordField();
        volume.setMaxHeight(10);
        volume.setMaxWidth(200);

        Label priorityLabel = new Label("Priority");
        priorityLabel.setMinHeight(25);
        priority = new PasswordField();
        priority.setMaxHeight(10);
        priority.setMaxWidth(200);



        VBox vbox1 = new VBox(10);
        vbox1.setPadding(new Insets(10, 10, 10, 10));
        vbox1.getChildren().add(fromLabel);
        vbox1.getChildren().add(toLabel);
        vbox1.getChildren().add(weightLabel);
        vbox1.getChildren().add(volumeLabel);
        vbox1.getChildren().add(priorityLabel);
        vbox1.getChildren().add(backButton);

        VBox vbox2 = new VBox(10);
        vbox2.setPadding(new Insets(10, 10, 10, 10));
        vbox2.getChildren().add(fromText);
        vbox2.getChildren().add(toText);
        vbox2.getChildren().add(weight);
        vbox2.getChildren().add(volume);
        vbox2.getChildren().add(priority);
        vbox2.getChildren().add(eventButton);

        HBox hbox = new HBox(20);
        hbox.setPadding(new Insets(20, 20, 20, 20));
        hbox.getChildren().addAll( vbox1, vbox2);

        scene = new Scene(hbox, 600, 400);
    }

    public static Scene scene() {
        return scene;
    }

    @Override
    public void handle(ActionEvent event) {
        // TODO Auto-generated method stub
        if(event.getSource() == eventButton){
            controller.handleEvent(Controller.MAIL);
        }
        if(event.getSource() == backButton){
            controller.handleEvent(Controller.EVENTGUI);
        }

    }

    public void showError(String errormsg) {

    }
}
