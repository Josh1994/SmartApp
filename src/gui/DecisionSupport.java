package gui;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import event.Event;
import gui.base.DataEntryGUI;
import gui.dialogs.AlertDialog;
import gui.dialogs.LogoutDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Database;
import model.EventManager;
import model.Route;

public class DecisionSupport implements DataEntryGUI, EventHandler<ActionEvent>{
	BusinessMonitor bs;
	EventManager eventManager;
	Database fullEventDatabase;
	Database eventList;
	Controller controller;
	int counter = 0;
	Button forwardButton;
	Button backwardButton;

	public DecisionSupport(Controller controller){
		this.controller = controller;

		forwardButton = new Button();
		forwardButton.setText("Forward");
		forwardButton.setOnAction(this);

		backwardButton = new Button();
		backwardButton.setText("Backward");
		backwardButton.setOnAction(this);

		VBox vbox = new BusinessMonitor(this).vbox();
		HBox hbox = new HBox(10);
		hbox.getChildren().addAll(forwardButton, backwardButton);
		VBox mainContainer = new VBox();
		mainContainer.setAlignment(Pos.TOP_CENTER);
		mainContainer.getChildren().addAll(vbox);

		eventList = controller.getDatabase();
		//bs.display(route);

	}

	@Override
	public void showError(String errmsg) {
		// TODO Auto-generated method stub

	}

	public void initialize(){
		Database db = new Database();
		ArrayList <Event> e = new ArrayList <Event> ();
		e.add(fullEventDatabase.getEvent().get(0));
		db.setEvent(e);

		EventManager em = new EventManager(null,db);
	}

	public void handle(ActionEvent event) {

		if(event.getSource() == forwardButton){
			clickForward();
		}
		else{
			LogoutDialog logoutDialog = new LogoutDialog(controller);
			logoutDialog.display();
		}

	}

	public void clickForward(){
		counter++;
		if(counter > fullEventDatabase.getEvent().size()-1){ counter =  fullEventDatabase.getEvent().size()-1;};
		Event eve = fullEventDatabase.getEvent().get(counter);
		Route route = eventManager.getRoute(eve);
		displayRoute(route);
	}

	public void clickBackward(){
		counter--;
		if(counter < 0 ){counter = 0;};
		Event eve = fullEventDatabase.getEvent().get(counter);
		Route route = eventManager.getRoute(eve);
		displayRoute(route);
	}

	@Override
	public Controller getController() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void displayRoute(Route route) {
		// TODO Auto-generated method stub

	}
}
