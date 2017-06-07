package gui;

import java.time.ZonedDateTime;

import controller.Controller;
import event.Event;
import gui.base.DataEntryGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.User;

public class TransportDiscontinued implements DataEntryGUI, EventHandler<ActionEvent>{

	Button eventButton;
	Button backButton;
	TextField fromText;
	TextField toText;
	TextField firm;
	TextField volume;
	String prio;
	ChoiceBox<String> priorityBox;
	TextField city;
	
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
			System.out.println(fromText.getText() +" "+ toText.getText() +" "+ firm.getText() +" "+ prio);
			if(fromText.getText().isEmpty() || toText.getText().isEmpty() || firm.getText().isEmpty() || prio==null || city.getText().isEmpty() ){
				AlertBox.display("Invalid Input", "Invalid Input Fields");
			}
			else{
				
				if(prio=="Land"){
					prio = Event.LAND;
				}
				else if(prio=="Air"){
					prio = Event.AIR;
				}
				else if(prio=="Sea"){
					prio = Event.SEA;
				}
				else{
					prio = Event.DOMESTIC;
				}
				System.out.println(prio);
				ZonedDateTime zdt = ZonedDateTime.now();
				User user = controller.getLoggedInUser();
				event.TransportDiscontinued td = new event.TransportDiscontinued(zdt, user.getUsername(), fromText.getText(), toText.getText(), firm.getText(), prio, city.getText());
				System.out.println(td.toString());
				
				controller.handleEvent(td, this);
			}
		}
		if(event.getSource() == backButton){
			controller.handleEvent(Controller.EVENTGUI);
		}

	}
	public void showError(String errormsg) {

	}
}