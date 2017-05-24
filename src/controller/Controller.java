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

	public Controller(Stage primaryStage) {
		this.primaryStage = primaryStage;
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

}
