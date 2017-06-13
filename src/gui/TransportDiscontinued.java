package gui;
import event.Event;
import java.time.ZonedDateTime;

import controller.Controller;
import gui.base.DataEntryGUI;
import gui.dialogs.AlertDialog;
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
import model.User;

public class TransportDiscontinued implements DataEntryGUI, EventHandler<ActionEvent>{

	Button eventButton;
	Button backButton;
	TextField firm;
	TextField volume;
	String prio;
	ChoiceBox<String> priorityBox;
	TextField city;
	ComboBox<String> fromText;
	ComboBox<String> toText;
	Button logoutButton;

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
		fromText.getItems().addAll(controller.getEventProcessor().getLocationNames());
		fromText.setEditable(false);

		Label toLabel = new Label("Destination");
		toLabel.setMinHeight(25);
		toText = new ComboBox<String>();
		toText.setMinWidth(200.0);
		toText.getItems().addAll(controller.getEventProcessor().getLocationNames());
		toText.setEditable(false);

		Label firmLabel = new Label("Transport Firm");
		firmLabel.setMinHeight(25);
		firm = new TextField();
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
			System.out.println(fromText.getValue() +" "+ toText.getValue() +" "+ firm.getText() +" "+ prio);
			if(fromText.getValue().isEmpty() || toText.getValue().isEmpty() || firm.getText().isEmpty() || prio==null || city.getText().isEmpty() ){
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
				event.TransportDiscontinued td = new event.TransportDiscontinued(zdt, user.getUsername(), fromText.getValue(), toText.getValue(), firm.getText(), prio, city.getText());
				System.out.println(td.toString());
				controller.handleEvent(td, this);
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