package gui;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import com.sun.javafx.tk.Toolkit;

import controller.Controller;
import event.Event;
import gui.base.DataEntryGUI;
import gui.dialogs.AlertDialog;
import gui.dialogs.LogoutDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Database;
import model.EventManager;
import model.Route;

public class DecisionSupport implements DataEntryGUI, EventHandler<ActionEvent>{
	BusinessMonitor bm;
	EventManager eventManager;
	Database fullEventDatabase;
	List<Route> history;
	List<Event> eventHistory;
	Controller controller;
	int counter = 0;
	Button forwardButton;
	Button backwardButton;
	Button backButton;
	private Scene scene = null;

	public DecisionSupport(Controller controller){
		BorderPane root = new BorderPane();

		this.fullEventDatabase = controller.getDatabase();
		System.out.println(fullEventDatabase.getEvent().isEmpty());
		this.controller = controller;

		forwardButton = new Button();
		forwardButton.setText("Forward");
		forwardButton.setOnAction(this);

		backwardButton = new Button();
		backwardButton.setText("Backward");
		backwardButton.setOnAction(this);

		backButton = new Button();
		backButton.setText("Exit");
		backButton.setOnAction(this);

		bm = new BusinessMonitor(this);
		VBox vbox = bm.vbox();
		HBox hbox = new HBox(10);
		hbox.getChildren().addAll(forwardButton, backwardButton, backButton);
		VBox mainContainer = new VBox();
		mainContainer.setAlignment(Pos.TOP_CENTER);
		mainContainer.getChildren().addAll(vbox);


		scene = new Scene(root, 600, 400);
		root.setCenter(mainContainer);
		root.setBottom(hbox);

		history = new ArrayList<>();
		eventHistory = new ArrayList<>();

		initialize();

	}

	@Override
	public void showError(String errmsg) {
		// TODO Auto-generated method stub

	}

	public void initialize(){
		Database db = new Database();
		ArrayList <Event> e = new ArrayList <Event> ();
		Event event = fullEventDatabase.getEvent().get(0);
		e.add(event);
		db.setEvent(e);


		eventManager = new EventManager(null,db);
		eventManager.getNewRoutes();

		Route route = eventManager.getRoute(event);
		if (route != null) {
			displayRoute(route);
			history.add(new Route(route));
		} else history.add(route);
		eventHistory.add(event);
		bm.displayEvent(event);
	}

	public void handle(ActionEvent event) {
		if(event.getSource() == backButton) {
			controller.handleEvent(Controller.EVENTGUI);
		} else if(event.getSource() == forwardButton){
			clickForward();
		}
		else{
			clickBackward();
		}

	}

	public void clickForward(){
		counter++;
		Route route;
		if(counter > fullEventDatabase.getEvent().size()-1){
			counter = fullEventDatabase.getEvent().size()-1;
			return;
		}
		Event event = fullEventDatabase.getEvent().get(counter);
		if (counter < history.size()) {
			route = history.get(counter);
		} else {
			eventManager.processEvent(event);
			route = eventManager.getRoute(event);
			if (route != null) history.add(new Route(route));
			else history.add(route);
		}
		if(route != null){
			displayRoute(route);

			System.out.format("Run: %s", event.getOrigin());
		}
		else{
			System.out.print("Route is null");
		}
		bm.displayEvent(event);
	}

	public void clickBackward(){
		counter--;
		if(counter < 0 ){
			counter = 0;
			return;
		}
		Event event = fullEventDatabase.getEvent().get(counter);
		Route route = history.get(counter);
		if(route != null){
			displayRoute(route);
		}
		bm.displayEvent(event);
	}

	public Scene scene() {
		return scene;
	}
	@Override
	public Controller getController() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void displayRoute(Route route) {
		bm.display(route);


	}
}
