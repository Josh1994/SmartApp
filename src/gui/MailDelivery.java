package gui;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import event.Event;


import controller.Controller;
import gui.base.DataEntryGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MailDelivery implements DataEntryGUI, EventHandler<ActionEvent>{

	Button eventButton;
	Button backButton;
	ChoiceBox origin;
	ChoiceBox destination;
	TextField fromText;
	TextField toText;
	TextField weight;
	TextField volume;
	TextField priority;
	RadioButton land;
	RadioButton sea;
	RadioButton air;
	RadioButton domestic;
	ArrayList <String> critLabels = new ArrayList();
	static Scene scene;
	Controller controller;



	public MailDelivery(Controller controller){
		
		this.controller = controller;

		eventButton = new Button();
		eventButton.setText("Create Event");
		eventButton.setOnAction(this);

		backButton = new Button();
		backButton.setText("Back");
		backButton.setOnAction(this);

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

		Label weightLabel = new Label("Weight");
		weightLabel.setMinHeight(25);
		weight = new TextField();
		weight.setMaxHeight(10);
		weight.setMaxWidth(200);

		Label volumeLabel = new Label("Volume");
		volumeLabel.setMinHeight(25);
		volume = new TextField();
		volume.setMaxHeight(10);
		volume.setMaxWidth(200);

		Label priorityLabel = new Label("Priority");
		priorityLabel.setMinHeight(25);
		priority = new TextField();
		priority.setMaxHeight(10);
		priority.setMaxWidth(200);
		
		ToggleGroup type = new ToggleGroup();
		land = new RadioButton("Land");
		air = new RadioButton("Air");
		sea = new RadioButton("Sea");
		domestic = new RadioButton("Domestic");
		
		land.setToggleGroup(type);
		air.setToggleGroup(type);
		sea.setToggleGroup(type);
		domestic.setToggleGroup(type);
		
		
		HBox hboxType = new HBox(land,air,sea,domestic);



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
		vbox2.getChildren().add(hboxType);
		vbox2.getChildren().add(eventButton);


		critLabels.add("Revenue");
		critLabels.add("Expenditure");
		HBox hbox = new HBox(20);
		VBox vbox = new BusinessMonitor(critLabels).vbox();
		hbox.setPadding(new Insets(20, 20, 20, 20));
		hbox.getChildren().addAll( vbox,vbox1, vbox2);

		scene = new Scene(hbox, 650, 400);
	}

	public Scene scene() {
		return scene;
	}

	@Override
	public void handle(ActionEvent event) {

		if(event.getSource() == eventButton){
			/**
			 * Create MailDelivery( from fields ) Event
			 * call controller.handleEvent(maildeliveryEvent, this)
			 */
			event.MailDelivery mdEvent;
			String type = null;
			if(land.isSelected()){
				type = Event.LAND;
			}
			else if(air.isSelected()){
				type = Event.AIR;
			}
			else if(sea.isSelected()){
				type = Event.SEA;
			}
			else if(domestic.isSelected()){
				type = Event.DOMESTIC;
			}
			ZonedDateTime timeNow = ZonedDateTime.now();
			String user = controller.getLoggedInUser().getUsername();
			if(type!=null && !toText.getText().isEmpty() && !fromText.getText().isEmpty() && !weight.getText().isEmpty() && !volume.getText().isEmpty()){

				mdEvent = new event.MailDelivery(timeNow, user, fromText.getText(), toText.getText(), Double.parseDouble(weight.getText()), Double.parseDouble(volume.getText()), type);
				System.out.println(mdEvent.toString());
				controller.handleEvent(mdEvent, this);
				
			}
			else{
				AlertBox.display("Invalid Input", "Please enter valid input");
			}
			

		}
		if(event.getSource() == backButton){
			controller.handleEvent(Controller.EVENTGUI);
		}

	}
	public void showError(String errormsg) {

	}

}
