package gui;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
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
	Button createAccount;
	TextField usernameText;
	PasswordField passwordText;
	Controller controller;
	static Scene scene;

	public Login(Controller controller){

		this.controller = controller;
		loginButton = new Button();
		loginButton.setText("Login");
		loginButton.setOnAction(this);
		
		createAccount = new Button("New Account");
		createAccount.setOnAction(this);

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
		
		VBox vbox2 = new VBox(10);
		vbox2.setPadding(new Insets(50, 50, 50, 50));
		vbox2.setAlignment(Pos.BOTTOM_CENTER);
		vbox.getChildren().add(createAccount);


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
			String inputUsername = usernameText.getText();
			String inputPassword = passwordText.getText();

			controller.login(inputUsername, inputPassword);
			usernameText.clear();
			passwordText.clear();
		}
		
		if(event.getSource() == createAccount){
			CreateAccount createAccount = new CreateAccount(controller);
			createAccount.display();
			controller.getUserDatabase().addUser(createAccount.getUsername(),
					 createAccount.getPassword(), false);
		}

	}

	public static Scene scene() {
		return scene;
	}

}

