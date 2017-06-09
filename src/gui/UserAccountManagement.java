package gui;

import controller.Controller;
import controller.UserDatabase;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class UserAccountManagement implements EventHandler<MouseEvent> {
	private Controller controller;
	private Scene scene = null;
	/*Loads the image from the Resource folder and turns it to imageView to be used as an icon
	with the button*/

	 public UserAccountManagement(Controller controller) {
		this.controller = controller;

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
		newAccountButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this);


		Button promoteAccountButton = new Button ("Promote Account", null);
		promoteAccountButton.setMaxSize(150, 100);
		promoteAccountButton.setWrapText(true);
		//Mouse event handling for Promoting an Account, only appears if the
		//user has proper permissions
		promoteAccountButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
		
		
		Button deleteAccountButton = new Button("Delete Account");
		deleteAccountButton.setMaxSize(150, 100);
		deleteAccountButton.setWrapText(true);
		//Mouse event handling for Deleting Account
		deleteAccountButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
		
		
		Button backButton = new Button ("Back" , null);
		backButton.setMaxSize(150, 100);
		backButton.setWrapText(true);
		backButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
		
		
		Button logoutButton = new Button ("Logout", null);
		logoutButton.setMaxSize(150, 100);
		logoutButton.setWrapText(true);
		logoutButton.addEventHandler(MouseEvent.MOUSE_CLICKED, this);
		
		

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

		Button buttonClicked = (Button) event.getSource();
		switch (buttonClicked.getText()) {
			case "Change Username":
				break;
			case "Change Password":
				break;
			case "New Account":
				CreateAccount createAccount = new CreateAccount(controller);
				createAccount.display();
				try {
					controller.getUserDatabase().addUser(createAccount.getUsername(),
							createAccount.getPassword(), false);
				} catch (UserDatabase.UserDatabaseException e) {
					AlertBox.display("Create Account Error", e.getMessage());
				}
				break;
			case "Delete Account":
				ListUserAccountBox listUserAccountBox = new ListUserAccountBox(controller);
				listUserAccountBox.display("Delete Account", "delete");
				if (listUserAccountBox.getSelectedIndices() != null) {
					try {
						boolean rejectedAttemptToDeleteSelfUser = false;

						for (Object o : listUserAccountBox.getSelectedIndices()) {
							String username = (String) listUserAccountBox.getListItems().get((Integer) o);

							// Handling User trying to delete themselves
							if(username.equals(controller.getLoggedInUser().getUsername())) {
								// User is the only manager
								if(controller.getLoggedInUser().isManager() &&
										controller.getUserDatabase().getManagers().size() == 1) {
									// reject, user cannot delete themselves if they are the only manager
									AlertBox.display("User Deletion Error", "You cannot delete" +
											" yourself as you are the only manager on the system.");
									rejectedAttemptToDeleteSelfUser = true;
									// user is only user in database
									if(controller.getUserDatabase().getUsers().size() == 1) {
										return;
									}
								} else if(controller.getLoggedInUser().isManager() &&
										controller.getUserDatabase().getManagers().size() > 1) {
									// allow with confirmation, user can delete themselves as other managers exist
									ConfirmBox confirmBox = new ConfirmBox();
									confirmBox.display("User Deletion Confirmation", "Are you sure you " +
											"want to delete your own account?" + System.lineSeparator() + System.lineSeparator() +
											"If you choose okay, at the end of the deletion process, you" +
											"will be logged out and will no longer be able to use this system. If you cancel, all other deletions will continue but your own account will remain in the system.");

									// check user input
									if(confirmBox.confirm) {
										controller.getUserDatabase().deleteUser(username);
									} else {
										rejectedAttemptToDeleteSelfUser = true;
										continue;
									}
								}
							} else {
								controller.getUserDatabase().deleteUser(username);
							}
						}
						AlertBox.display("User Deletion Success", "All selected users" +(rejectedAttemptToDeleteSelfUser ? " (except the current user)" : "")+" were successfully deleted.");
					} catch (UserDatabase.UserDatabaseException e) {
						AlertBox.display("User Deletion Error", e.getMessage());
					}
				}
				break;
			case "Promote Account":
				break;
			case "Back":
				controller.handleEvent(Controller.MAINSCREEN);
				break;
			case "Logout":
				Logout logout = new Logout(controller);
				logout.display();
				break;
			default:
				throw new IllegalArgumentException("Event passed into handler method has not been handled correctly.");
		}
	}
}
