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
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Login implements EventHandler<ActionEvent> {
	private Controller controller;
	private static Scene scene;

	private TextField usernameText;
	private PasswordField passwordText;
	private Button loginButton;

	Login(Controller controller){

		this.controller = controller;

		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		ColumnConstraints column1 = new ColumnConstraints();
		ColumnConstraints column2 = new ColumnConstraints();
		column1.setPercentWidth(15);
		column2.setPercentWidth(50);
		//column2.setHgrow(Priority.ALWAYS);
		grid.getColumnConstraints().addAll(column1, column2);

		Text scenetitle = new Text("KPSmart Login");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Label usernameLabel = new Label("Username");
		grid.add(usernameLabel, 0, 1);

		usernameText = new TextField();
		grid.add(usernameText, 1, 1,2,1);

		Label passwordLabel = new Label("Password");
		grid.add(passwordLabel, 0, 2);

		passwordText = new PasswordField();
		grid.add(passwordText, 1, 2,2,1);


		loginButton = new Button();
		loginButton.setText("Login");
		loginButton.setPadding(new Insets(7,20,7,20));
		loginButton.setOnAction(this);

		HBox hbBtn = new HBox(10);
		hbBtn.setAlignment(Pos.BOTTOM_CENTER);
		hbBtn.getChildren().add(loginButton);
		grid.add(hbBtn, 1, 4);


		scene = new Scene(grid, 600, 400);

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

			boolean isSuccessfulLogin = controller.login(inputUsername, inputPassword);

			if(!isSuccessfulLogin) {
				// Display Error message here if user
				AlertBox.display("Incorrect Credentials",
						"Please input the correct credentials to login to KPSmart.");
			}
			
			usernameText.clear();
			passwordText.clear();
		}

	}

	public static Scene scene() {
		return scene;
	}

}

