package controller;

import event.Event;
import gui.EventGUI;
import gui.Login;
import gui.MailDeliveryGUI;
import gui.TransportDiscontinuedGUI;
import javafx.stage.Stage;
import model.BusinessModel;

public class Controller {
	public static final String MAIL = "MAIL";
	public static final String LOGIN = "LOGIN";
	public static final String EVENTGUI = "EVENTGUI";
	public static final String TRANSPORTDISC = "TRANSPORTDISC";
	public static final String TRANSCOSTUPDATE = "TRANSCOSTUPDATE";
	public static final String CUSTPRICEUPDATE = "CUSTPRICEUPDATE";

	Stage primaryStage;

	// Global Business Components
	private BusinessModel model;
	private UserDatabase userDatabase;

	public Controller(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.model = new BusinessModel(this);
		this.userDatabase = new UserDatabase();
	}

	public void handleEvent(Event entry) {
		// first check for accuracy
		if (validateEvent(entry)) {
			model.processEvent(entry);
		}
		model.processEvent(entry);
	}

	public void handleEvent(String nextScreen) {

		if (nextScreen.equals(MAIL)) {
			MailDeliveryGUI mailDeliveryGUI = new MailDeliveryGUI(this);
			primaryStage.setScene(mailDeliveryGUI.scene());
		}
		if (nextScreen.equals(EVENTGUI)) {
			EventGUI eventGUI = new EventGUI(this);
			primaryStage.setScene(eventGUI.scene());
		}
		if (nextScreen.equals(LOGIN)) {
			primaryStage.setScene(Login.scene());
		}
		if (nextScreen.equals(TRANSPORTDISC)) {
			TransportDiscontinuedGUI transdisc = new TransportDiscontinuedGUI(this);
			primaryStage.setScene(transdisc.scene());
		}

	}

	private boolean validateEvent(Event entry) {
		// if entry not valid call the source GUI e.g.
		// currentView.setError(errormsg)
		return true;
	}

	public UserDatabase getUserDatabase() {
		return userDatabase;
	}
}
