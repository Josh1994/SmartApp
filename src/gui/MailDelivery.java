package gui;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import event.Event;


import controller.Controller;
import gui.base.DataEntryGUI;
import gui.dialogs.AlertDialog;
import gui.dialogs.ConfirmDialog;
import gui.dialogs.LogoutDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Route;

public class MailDelivery implements DataEntryGUI, EventHandler<ActionEvent>{

	Button eventButton;
	Button backButton;
	Button logoutButton;
	ChoiceBox origin;
	ChoiceBox destination;
	ComboBox<String> fromText;
	ComboBox<String> toText;
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
	BusinessMonitor bm;
	VBox vbox;

	public MailDelivery(Controller controller){

		this.controller = controller;

		eventButton = new Button();
		eventButton.setText("Create Event");
		eventButton.setOnAction(this);

		backButton = new Button();
		backButton.setText("Back");
		backButton.setOnAction(this);

		logoutButton = new Button();
		logoutButton.setText("Logout");
		logoutButton.setOnAction(this);

		Label fromLabel = new Label("Origin");
		fromLabel.setMinHeight(25);
		fromText = new ComboBox<String>();
		fromText.setMinWidth(200.0);
		fromText.getItems().addAll(controller.getModel().getEventManager().getLocationNames());
		fromText.setEditable(false);

		Label toLabel = new Label("Destination");
		toLabel.setMinHeight(25);
		toText = new ComboBox<String>();
		toText.setMinWidth(200.0);
		toText.getItems().addAll(controller.getModel().getEventManager().getLocationNames());
		toText.setEditable(false);

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
		vbox1.getChildren().add(logoutButton);

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
		HBox hbox = new HBox(10);
		//TODO
		bm = new BusinessMonitor(this);
		vbox = bm.vbox();

		vbox.setPadding(new Insets(20));
		vbox.setAlignment(Pos.TOP_CENTER);
		hbox.setPadding(new Insets(20, 20, 20, 20));
		hbox.getChildren().addAll(vbox1, vbox2);
		VBox mainContainer = new VBox();
		mainContainer.setAlignment(Pos.TOP_CENTER);
		mainContainer.getChildren().addAll(vbox, hbox);


		scene = new Scene(mainContainer, 650, 500);
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
			if(type!=null && toText.getValue()!=null && fromText.getValue()!=null && !weight.getText().isEmpty() && !volume.getText().isEmpty()){
				//TODO
				//need a confirm box showing the total cost and duration first before sending an event to the controller.
				

				mdEvent = new event.MailDelivery(timeNow, user, fromText.getValue(), toText.getValue(), Double.parseDouble(weight.getText()), Double.parseDouble(volume.getText()), type);
				System.out.println(mdEvent.toString());
				ConfirmDialog confirmDialog = new ConfirmDialog();
				double cost = 0;
				
				try{
					cost = controller.getRoute(mdEvent).getEffectiveCost();
					String message = "Total Cost for this delivery is: $" + cost;
					String title = "Mail Delivery";
					confirmDialog.display(title, message);
					if(confirmDialog.confirm){
						controller.handleEvent(mdEvent, this);
					}
				}
				catch(java.lang.NullPointerException e){
					AlertDialog.display("No Route Found", "No Route Found");
				}
				
				
				
				

			}
			else{
				AlertDialog.display("Invalid Input", "Please enter valid input");
			}


		}if(event.getSource() == backButton){
			controller.handleEvent(Controller.EVENTGUI);
		}if(event.getSource() == logoutButton){
			LogoutDialog logoutDialog = new LogoutDialog(controller);
			logoutDialog.display();
		}

	}
	public void showError(String errormsg) {

	}

	@Override
	public void displayRoute(Route route) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("Route: ");
		sb.append(route.toString());
		
		bm.display(route);
		this.vbox = bm.vbox();
		//AlertDialog.display("Route", sb.toString());

	}

	@Override
	public Controller getController() {
		// TODO Auto-generated method stub
		return this.controller;
	}

}