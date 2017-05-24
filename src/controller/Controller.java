package controller;

import gui.Login;
import gui.MailDelivery;
import javafx.stage.Stage;

public class Controller {
	public static final String MAIL = "MAIL";
	public static final String LOGIN = "LOGIN";

	Stage primaryStage;

	// Global System Components
	private UserDatabase userDatabase;

	public Controller(Stage primaryStage) {
		this.primaryStage = primaryStage;
		this.userDatabase = new UserDatabase();
	}

	public void handleEvent(String event) {

		if (event.equals(MAIL)) {
			primaryStage.setScene(MailDelivery.scene());
		}
		if (event.equals(LOGIN)) {
			primaryStage.setScene(Login.scene());
		}

	}

	public UserDatabase getUserDatabase() {
		return userDatabase;
	}
}
