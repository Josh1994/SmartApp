package gui;
import event.Event;
import java.time.ZonedDateTime;

import controller.Controller;
import gui.base.DataEntryGUI;
import gui.dialogs.AlertDialog;
import gui.dialogs.ConfirmDialog;
import gui.dialogs.LogoutDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Route;
import model.User;

public class TransportDiscontinued implements DataEntryGUI, EventHandler<ActionEvent>{

	Button eventButton;
	Button backButton;
	ComboBox<String> firm;
	TextField volume;
	String prio;
	ChoiceBox<String> priorityBox;
	TextField city;
	ComboBox<String> fromText;
	ComboBox<String> toText;
	Button logoutButton;
	Label messageLabel;
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

		Label firmLabel = new Label("Transport Firm");
		firmLabel.setMinHeight(25);
		firm = new ComboBox<String>();
		firm.getItems().addAll(controller.getModel().getEventManager().getFirmNames());
		firm.setEditable(false);
		firm.setMaxHeight(10);
		firm.setMaxWidth(200);
		
		Label priorityLabel = new Label("Transport Type");
		priorityLabel.setMinHeight(25);
		
		priorityBox = new ChoiceBox<String>();
		priorityBox.getItems().addAll("Land", "Air", "Sea", "Domestic");
		priorityBox.setValue("Land");
		
		Label cityLabel = new Label("City");
		cityLabel.setMinHeight(25);
		city = new TextField();
		city.setMaxHeight(10);
		city.setMaxWidth(200);

		messageLabel = new Label("Message");
		messageLabel.setMinHeight(25);


		VBox vbox1 = new VBox(10);
		vbox1.setPadding(new Insets(10, 10, 10, 10));
		vbox1.getChildren().add(fromLabel);
		vbox1.getChildren().add(toLabel);
		vbox1.getChildren().add(firmLabel);
		vbox1.getChildren().add(priorityLabel);
		vbox1.getChildren().add(cityLabel);
		vbox1.getChildren().add(backButton);
		vbox1.getChildren().add(logoutButton);

		VBox vbox2 = new VBox(10);
		vbox2.setPadding(new Insets(10, 10, 10, 10));
		vbox2.getChildren().add(fromText);
		vbox2.getChildren().add(toText);
		vbox2.getChildren().add(firm);
		vbox2.getChildren().add(priorityBox);
		vbox2.getChildren().add(city);
		vbox2.getChildren().add(eventButton);
		vbox2.getChildren().add(messageLabel);

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
			prio = priorityBox.getValue();
			System.out.println(fromText.getValue() +" "+ toText.getValue() +" "+ firm.getValue() +" "+ prio);
			if(fromText.getValue().isEmpty() || toText.getValue().isEmpty() || firm.getValue().isEmpty() || prio==null || city.getText().isEmpty() ){
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
				event.TransportDiscontinued td = new event.TransportDiscontinued(zdt, user.getUsername(), fromText.getValue(), toText.getValue(), firm.getValue(), prio, city.getText());
				System.out.println(td.toString());
				
				ConfirmDialog confirmDialog = new ConfirmDialog();
				confirmDialog.display("Discontinue Service", "Are you sure you would like to delete service for " + firm.getValue());
				if(confirmDialog.confirm){
					controller.handleEvent(td, this);
				}
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

	public void showMessage(String msg) {
		messageLabel.setText("Message:  " + msg );
	}

	@Override
	public void displayRoute(Route route) {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		sb.append("Route: ");
		sb.append(route.toString());
		AlertDialog.display("Route", sb.toString());
	}

	@Override
	public Controller getController() {
		// TODO Auto-generated method stub
		return this.controller;
	}
}