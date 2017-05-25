package controller;

import gui.EventGUI;
import gui.Login;
import gui.MailDelivery;
import gui.TransportDiscontinued;
import javafx.stage.Stage;

public class Controller {
	public static final String MAIL = "MAIL";
	public static final String LOGIN = "LOGIN";
	public static final String EVENTGUI = "EVENTGUI";
	public static final String TRANSPORTDISC = "TRANSPORTDISC";

	Stage primaryStage;

	// Global System Components
	private UserDatabase userDatabase;

	public Controller(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.userDatabase = new UserDatabase();
	}

	public void handleEvent(String event) {

		if (event.equals(MAIL)) {
			MailDelivery mailDelivery = new MailDelivery(this);
			primaryStage.setScene(mailDelivery.scene());
		}
		if (event.equals(EVENTGUI)) {
			EventGUI eventGUI = new EventGUI(this);
			primaryStage.setScene(eventGUI.scene());
		}
		if (event.equals(LOGIN)) {
			primaryStage.setScene(Login.scene());
		}
		if (event.equals(TRANSPORTDISC)) {
			TransportDiscontinued transdisc = new TransportDiscontinued(this);
			primaryStage.setScene(transdisc.scene());
		}

	}

	public UserDatabase getUserDatabase() {
		return userDatabase;
	}
}
