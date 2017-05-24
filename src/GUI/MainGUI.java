package GUI;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainGUI extends Application {
	
	
	
	
	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("KPSmart");
		
		//Login Scene
		Login login = new Login();
		
		primaryStage.setScene(login.scene);
		primaryStage.show();
		
	}
	
	static class Login implements EventHandler<ActionEvent>{
		Button loginButton;
		TextField usernameText;
		PasswordField passwordText;
		Scene scene;
		public Login(){
			loginButton = new Button();
			loginButton.setText("Login");
			loginButton.setOnAction(this);
			// Test
			Label usernameLabel = new Label("Username");
			usernameText = new TextField();
			usernameText.setMaxHeight(10);
			usernameText.setMaxWidth(200);
			
			Label passwordLabel = new Label("Password");
			passwordText = new PasswordField();
			
			passwordText.setMaxHeight(10);
			passwordText.setMaxWidth(200);
			
			
		
			VBox vbox = new VBox(10);
			vbox.setPadding(new Insets(50, 50, 50, 50));
			vbox.getChildren().add(usernameLabel);
			vbox.getChildren().add(usernameText);
			vbox.getChildren().add(passwordLabel);
			vbox.getChildren().add(passwordText);
			vbox.getChildren().add(loginButton);
			

			
			scene = new Scene(vbox, 600, 400);
		}
		@Override
		public void handle(ActionEvent event) {
			// TODO Auto-generated method stub
			if(event.getSource() == loginButton){
				System.out.println("LoginButton");
				System.out.println(usernameText.getText());
				System.out.println(passwordText.getText());
			}
			
		}
		
	}

	
	
}
