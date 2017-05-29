package gui;

import controller.Controller;
import gui.base.DataEntryGUI;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TransportDiscontinued implements DataEntryGUI, EventHandler<ActionEvent>{

	Button eventButton;
	Button backButton;
	Button logoutButton;
	TextField fromText;
	TextField toText;
	TextField weight;
	TextField volume;
	TextField priority;

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
		priority = new PasswordField();
		priority.setMaxHeight(10);
		priority.setMaxWidth(200);

		Label weightLabel = new Label("Transport Firm");
		weightLabel.setMinHeight(25);
		weight = new TextField();
		weight.setMaxHeight(10);
		weight.setMaxWidth(200);



		VBox vbox1 = new VBox(10);
		vbox1.setPadding(new Insets(10, 10, 10, 10));
		vbox1.getChildren().add(fromLabel);
		vbox1.getChildren().add(toLabel);
		vbox1.getChildren().add(weightLabel);
		vbox1.getChildren().add(priorityLabel);
		vbox1.getChildren().add(backButton);

		VBox vbox2 = new VBox(10);
		vbox2.setPadding(new Insets(10, 10, 10, 10));
		vbox2.getChildren().add(fromText);
		vbox2.getChildren().add(toText);
		vbox2.getChildren().add(weight);
		vbox2.getChildren().add(priority);
		vbox2.getChildren().add(eventButton);
		
		VBox vboxl = new VBox(10);
		vboxl.setPadding(new Insets(10, 10, 10, 10));
		vboxl.setAlignment(Pos.BOTTOM_RIGHT);
		vboxl.getChildren().add(logoutButton);

		HBox hbox = new HBox(20);
		hbox.setPadding(new Insets(20, 20, 20, 20));
		hbox.getChildren().addAll( vbox1, vbox2, vboxl);

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
			//controller.handleEvent(Controller.MAIL);
		}
		if(event.getSource() == backButton){
			controller.handleEvent(Controller.EVENTGUI);
		}
		if(event.getSource() == logoutButton){
			 Logout logout = new Logout(controller);
	         logout.display();
		}

	}
	public void showError(String errormsg) {

	}
}
