package controller;

import gui.Login;
import gui.MailDelivery;
import javafx.stage.Stage;

public class Controller {
	public static final String MAIL = "MAIL";
	public static final String LOGIN = "LOGIN";

	Stage primaryStage;

	public Controller(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public void handleEvent(String event) {

		if (event.equals(MAIL)) {
			primaryStage.setScene(MailDelivery.scene());
		}
		if (event.equals(LOGIN)) {
			primaryStage.setScene(Login.scene());
		}

	}

}
