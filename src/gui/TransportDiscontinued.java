package gui;
import event.Event;
import java.time.ZonedDateTime;

import controller.Controller;
import gui.base.DataEntryGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TransportDiscontinued implements DataEntryGUI, EventHandler<ActionEvent>{

	Button eventButton;
	Button backButton;
	Button logoutButton;
	TextField fromText;
	TextField toText;
	TextField firm;
	
	TextField priority;
	RadioButton land;
	RadioButton sea;
	RadioButton air;
	RadioButton domestic;

	static Scene scene;
	Controller controller;



	public TransportDiscontinued(Controller controller){

		this.controller = controller;

		eventButton = new Button();
		eventButton.setText("Submit");
		eventButton.setOnAction(this);

		backButton = new Button();
		backButton.setText("Back");
		backButton.setOnAction(this);
		
		logoutButton = new Button();
		logoutButton.setText("Logout");
		logoutButton.setOnAction(this);

		Label fromLabel = new Label("Origin");
		fromLabel.setMinHeight(25);
		fromText = new TextField();
		fromText.setMaxHeight(10);
		fromText.setMaxWidth(200);

		Label toLabel = new Label("Destination");
		toLabel.setMinHeight(25);
		toText = new TextField();
		toText.setMaxHeight(10);
		toText.setMaxWidth(200);

		Label priorityLabel = new Label("Transport Type");
		priorityLabel.setMinHeight(25);
		ToggleGroup type = new ToggleGroup();
		land = new RadioButton("Land");
		air = new RadioButton("Air");
		sea = new RadioButton("Sea");
		domestic = new RadioButton("Domestic");
		
		land.setToggleGroup(type);
		sea.setToggleGroup(type);
		air.setToggleGroup(type);
		domestic.setToggleGroup(type);
		
		
//		priority = new PasswordField();
//		priority.setMaxHeight(10);
//		priority.setMaxWidth(200);

		Label firmLabel = new Label("Transport Firm");
		firmLabel.setMinHeight(25);
		firm = new TextField();
		firm.setMaxHeight(10);
		firm.setMaxWidth(200);
		HBox typeHbox = new HBox(land, sea, air, domestic);


		VBox vbox1 = new VBox(10);
		vbox1.setPadding(new Insets(10, 10, 10, 10));
		vbox1.getChildren().add(fromLabel);
		vbox1.getChildren().add(toLabel);
		vbox1.getChildren().add(firmLabel);
		vbox1.getChildren().add(priorityLabel);
		vbox1.getChildren().add(backButton);
		vbox1.getChildren().add(logoutButton);

		VBox vbox2 = new VBox(10);
		vbox2.setPadding(new Insets(10, 10, 10, 10));
		vbox2.getChildren().add(fromText);
		vbox2.getChildren().add(toText);
		vbox2.getChildren().add(firm);
		vbox2.getChildren().add(typeHbox);
		vbox2.getChildren().add(eventButton);

		HBox hbox = new HBox(20);
		hbox.setPadding(new Insets(20, 20, 20, 20));
		hbox.getChildren().addAll( vbox1, vbox2);

		scene = new Scene(hbox, 600, 400);
	}

	public Scene scene() {
		return scene;
	}

	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource() == eventButton){
			System.out.println("Submit Button pressed");
			if(!fromText.getText().isEmpty() && !toText.getText().isEmpty() && !firm.getText().isEmpty()){
				event.TransportDiscontinued tdEvent;
				String type;
				if(air.isSelected()){
					type = Event.AIR;
				}
				else if(land.isSelected()){
					type = Event.LAND;
				}
				else if(sea.isSelected()){
					type = Event.SEA;
				}
				else if(domestic.isSelected()){
					type = Event.DOMESTIC;
				}
				else{
					AlertBox.display("Select a priority", "Please select a priority");
					return;
				}
				ZonedDateTime timeNow = ZonedDateTime.now();
				String user = controller.getLoggedInUser().getUsername();
				String origin = fromText.getText();
				String destination = toText.getText();
				String company = firm.getText();
				
				
				tdEvent = new event.TransportDiscontinued(timeNow, user, origin, destination, company, type);
				controller.handleEvent(tdEvent, this);
			}
			else{
				AlertBox.display("Invalid Input", "Please enter valid input.");
			}
			//controller.handleEvent(Controller.MAIL);
		}
		if(event.getSource() == backButton){
			controller.handleEvent(Controller.EVENTGUI);
		}if(event.getSource() == logoutButton){
			Logout logout = new Logout(controller);
			logout.display();
		}

	}
	public void showError(String errormsg) {

	}
}
