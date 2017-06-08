package gui;

import controller.Controller;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class UserAccountManagement implements EventHandler<MouseEvent> {
	private Scene scene = null;
	/*Loads the image from the Resource folder and turns it to imageView to be used as an icon
	with the button*/

	 public UserAccountManagement(Controller controller) {

		BorderPane root = new BorderPane();

		//Load the images for the 4 buttons

		//Button Creation
		Button changeUsernameButton = new Button ("Change Username", null);
		 changeUsernameButton.setMaxSize(150, 100);
		 changeUsernameButton.setWrapText(true);
		 changeUsernameButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this);

		Button changePasswordButton = new Button ("Change Password", null);
		changePasswordButton.setMaxSize(150, 100);
		changePasswordButton.setWrapText(true);
		 changePasswordButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this);

		Button newAccountButton = new Button ("New Account", null);
		newAccountButton.setMaxSize(150, 100);
		newAccountButton.setWrapText(true);
		//Mouse event handling for creating a New Account
		newAccountButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			            System.out.println("New Account button clicked");
			            CreateAccount createAccount = new CreateAccount(controller);
						createAccount.display();
						controller.getUserDatabase().addUser(createAccount.getUsername(),
								 createAccount.getPassword(), false);
			        }
			});


		Button promoteAccountButton = new Button ("Promote Account", null);
		promoteAccountButton.setMaxSize(150, 100);
		promoteAccountButton.setWrapText(true);
		//Mouse event handling for Promoting an Account, only appears if the
		//user has proper permissions
		promoteAccountButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			            System.out.println("Promote Account button clicked");
			        }
			});
		
		
		Button deleteAccountButton = new Button("Delete Account");
		deleteAccountButton.setMaxSize(150, 100);
		deleteAccountButton.setWrapText(true);
		//Mouse event handling for Deleting Account
		deleteAccountButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			        	System.out.println("Delete Account button clicked");
			        }
			});
		
		
		Button backButton = new Button ("Back" , null);
		backButton.setMaxSize(150, 100);
		backButton.setWrapText(true);
		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			        	System.out.println("Back button clicked");
			        	controller.handleEvent(Controller.MAINSCREEN);
			        }
			});
		
		
		Button logoutButton = new Button ("Logout", null);
		logoutButton.setMaxSize(150, 100);
		logoutButton.setWrapText(true);
		//Mouse event handling for Logging Out
		logoutButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			        	System.out.println("Logout button clicked");
			            Logout logout = new Logout(controller);
			            logout.display();
			        }
			});
		
		

		scene = new Scene(root, 600, 400);
		VBox box = new VBox();

		//Add all buttons to a container. Place the container on the center of the BorderPane
		box.getChildren().addAll(changeUsernameButton, changePasswordButton, newAccountButton, deleteAccountButton);
		if(controller.getLoggedInUser().isManager())box.getChildren().add(promoteAccountButton);
		box.setAlignment(Pos.CENTER);

		//This adds spaces when items are being added (top,left, bottom, right)
		box.setMargin(changeUsernameButton, new Insets(15,20,15,20));
		box.setMargin(changePasswordButton, new Insets(15,20,15,20));
		box.setMargin(newAccountButton, new Insets(15,20,15,20));
		box.setMargin(promoteAccountButton, new Insets(15,20,15,20));
		box.setMargin(deleteAccountButton, new Insets(15,20,15,20));
		
		VBox box2 = new VBox();
		box2.getChildren().addAll(backButton, logoutButton);
		box2.setAlignment(Pos.BOTTOM_RIGHT);
		box2.setMargin(backButton, new Insets(10,10,10,10));
		box2.setMargin(logoutButton, new Insets(10,10,10,10));

		root.setCenter(box);
		root.setBottom(box2);

	}

	public Scene scene() {
			return scene;
	}


	@Override
	public void handle(MouseEvent event) {
		System.out.println(event.toString());
	}
}
