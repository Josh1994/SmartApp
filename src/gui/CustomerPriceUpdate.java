package gui;

import java.time.ZonedDateTime;

import controller.Controller;
import event.Event;
import gui.base.DataEntryGUI;
import gui.dialogs.AlertDialog;
import gui.dialogs.LogoutDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.User;

/**
 * Created by phoal on 5/27/2017.
 */
public class CustomerPriceUpdate implements DataEntryGUI,EventHandler<ActionEvent> {
    Button eventButton;
    Button backButton;

    ComboBox<String> fromText;
    ComboBox<String> toText;

    Button logoutButton;
    

    TextField weight;
    TextField volume;
	String prio;
    ChoiceBox<String> priorityBox;

    static Scene scene;
    Controller controller;



    public CustomerPriceUpdate(Controller controller){

        this.controller = controller;

        eventButton = new Button();
        eventButton.setText("Update");
        eventButton.setOnAction(this);

        backButton = new Button();
        backButton.setText("Back");
        backButton.setOnAction(this);
		
        logoutButton = new Button();
		logoutButton.setText("Logout");
		logoutButton.setOnAction(this);

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
        volume = new TextField();
        volume.setMaxHeight(10);
        volume.setMaxWidth(200);

		Label priorityLabel = new Label("Transport Type");
		priorityLabel.setMinHeight(25);
		priorityBox = new ChoiceBox<String>();
		priorityBox.getItems().addAll("Land", "Air", "Sea", "Domestic");
		priorityBox.setValue("Land");



        VBox vbox1 = new VBox(10);
        vbox1.setPadding(new Insets(10, 10, 10, 10));
        vbox1.getChildren().add(fromLabel);
        vbox1.getChildren().add(toLabel);
        vbox1.getChildren().add(weightLabel);
        vbox1.getChildren().add(volumeLabel);
        vbox1.getChildren().add(priorityLabel);
        vbox1.getChildren().add(backButton);
        vbox1.getChildren().add(logoutButton);

        VBox vbox2 = new VBox(10);
        vbox2.setPadding(new Insets(10, 10, 10, 10));
        vbox2.getChildren().add(fromText);
        vbox2.getChildren().add(toText);
        vbox2.getChildren().add(weight);
        vbox2.getChildren().add(volume);
        vbox2.getChildren().add(priorityBox);
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
			System.out.println("Submit Button pressed");
			prio = priorityBox.getValue();
			System.out.println("From: "+ fromText.getValue());
			System.out.println("To: "+ toText.getValue());
			System.out.println(fromText.getValue() +" "+ toText.getValue() +" "+ weight.getText() +" "+ volume.getText() +" " + prio);
			if(fromText.getValue().isEmpty() || toText.getValue().isEmpty() || weight.getText().isEmpty() || prio==null || volume.getText().isEmpty() ){
				AlertDialog.display("Invalid Input", "Invalid Input Fields");
			}
			else{

				if(prio.equals("Land")){
					prio = Event.LAND;
				}
				else if(prio.equals("Air")){
					prio = Event.AIR;
				}
				else if(prio.equals("Sea")){
					prio = Event.SEA;
				}
				else{
					prio = Event.DOMESTIC;
				}
				System.out.println(prio);
				ZonedDateTime zdt = ZonedDateTime.now();
				User user = controller.getLoggedInUser();
				double w = Double.parseDouble(weight.getText());
				double vol = Double.parseDouble(volume.getText());
				event.CustomerPriceUpdate cpu = new event.CustomerPriceUpdate(zdt, user.getUsername(), fromText.getValue(), toText.getValue(), w, vol ,prio);
				System.out.println(cpu.toString());

				controller.handleEvent(cpu, this);
			}
        }
        if(event.getSource() == backButton){
            controller.handleEvent(Controller.EVENTGUI);
        }if(event.getSource() == logoutButton){
			LogoutDialog logoutDialog = new LogoutDialog(controller);
			logoutDialog.display();
		}

    }

    public void showError(String errormsg) {

    }
}
