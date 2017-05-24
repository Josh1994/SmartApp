package gui;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MailDelivery implements EventHandler<ActionEvent>{

	Button eventButton;
	Button backButton;
	TextField fromText;
	TextField toText;
	TextField weight;
	TextField volume;
	TextField priority;
	
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
		weight = new PasswordField();
		weight.setMaxHeight(10);
		weight.setMaxWidth(200);
		
		Label volumeLabel = new Label("Volume");
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
			controller.handleEvent(Controller.LOGIN);
		}
		
	}

}
