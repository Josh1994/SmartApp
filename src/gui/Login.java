package gui;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import model.User;

import java.util.List;


public class Login implements EventHandler<ActionEvent>{
	Button loginButton;
	TextField usernameText;
	PasswordField passwordText;
	Controller controller;
	static Scene scene;

	public Login(Controller controller){

		this.controller = controller;
		loginButton = new Button();
		loginButton.setText("Login");
		loginButton.setOnAction(this);

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

		if(controller.getUserDatabase().getUsers().size() == 0) {
            LoginFirstTimeInputDialog loginFirstTimeInputDialog = new LoginFirstTimeInputDialog(controller);
            loginFirstTimeInputDialog.display();
            controller.getUserDatabase().addUser(loginFirstTimeInputDialog.getUsername(),
                    loginFirstTimeInputDialog.getPassword(), true);
        }
	}

	@Override
	public void handle(ActionEvent event) {
		if(event.getSource() == loginButton){
			//System.out.println("LoginButton");
			//System.out.println(usernameText.getText());
			//System.out.println(passwordText.getText());
			String input_Username = usernameText.getText();
			String input_password = passwordText.getText();

			List<User> users = controller.getUserDatabase().getUsers();
			for(User user : users) {
				if(user.getUsername().equals(input_Username)
				&& user.getPassword().equals(input_password)) {
				    controller.getUserDatabase().setLoggedInUser(user);
					controller.handleEvent(Controller.EVENTGUI);
					return;
				}
			}

			// Display Error message here
            AlertBox.display("Incorrect Login Information",
                    "Please input the correct credentials to login to KPSmart");

		}

	}

	public static Scene scene() {
		return scene;
	}

}

