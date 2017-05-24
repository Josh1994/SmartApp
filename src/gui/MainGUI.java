package gui;

import controller.Controller;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainGUI extends Application {
	
	
	
	
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("KPSmart");
		
		Controller controller  = new Controller(primaryStage);
		
		// Scenes
		Login login = new Login(controller);
		MailDelivery mailDelivery = new MailDelivery(controller);
		
		primaryStage.setScene(login.scene());
		primaryStage.show();
		
	}
	

	
	
}
