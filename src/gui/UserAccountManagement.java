package gui;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import controller.Controller;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UserAccountManagement{
private Scene scene = null;
	/*Loads the image from the Resource folder and turns it to imageView to be used as an icon
	with the button*/

	 public UserAccountManagement(Controller controller) {

		BorderPane root = new BorderPane();

		//Load the images for the 4 buttons

		//Button Creation
		Button button = new Button ("Change Username", null);
		button.setMaxSize(150, 100);
		button.setWrapText(true);
		//Mouse event changing the Account Username
		button.addEventHandler(MouseEvent.MOUSE_CLICKED,
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			            System.out.println("Change Username button clicked");
			        }
			});

		Button button2 = new Button ("Change Password", null);
		button2.setMaxSize(150, 100);
		button2.setWrapText(true);
		//Mouse event handling for Changing Password
		button2.addEventHandler(MouseEvent.MOUSE_CLICKED,
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			            System.out.println("Change Password button clicked");
			        }
			});

		Button button3 = new Button ("New Account", null);
		button3.setMaxSize(150, 100);
		button3.setWrapText(true);
		//Mouse event handling for creating a New Account
		button3.addEventHandler(MouseEvent.MOUSE_CLICKED,
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			            System.out.println("New Account button clicked");
			            CreateAccount createAccount = new CreateAccount(controller);
						createAccount.display();
						controller.getUserDatabase().addUser(createAccount.getUsername(),
								 createAccount.getPassword(), false);
			        }
			});


		Button button4 = new Button ("Promote Account", null);
		button4.setMaxSize(150, 100);
		button4.setWrapText(true);
		//Mouse event handling for Promoting an Account, only appears if the
		//user has proper permissions
		button4.addEventHandler(MouseEvent.MOUSE_CLICKED,
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			            System.out.println("Promote Account button clicked");
			        }
			});
		
		
		Button button5 = new Button("Delete Account");
		button5.setMaxSize(150, 100);
		button5.setWrapText(true);
		//Mouse event handling for Deleting Account
		button5.addEventHandler(MouseEvent.MOUSE_CLICKED,
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			        	System.out.println("Delete Account button clicked");
			        }
			});
		
		
		Button button6 = new Button ("Back" , null);
		button6.setMaxSize(150, 100);
		button6.setWrapText(true);
		button6.addEventHandler(MouseEvent.MOUSE_CLICKED,
			    new EventHandler<MouseEvent>() {
			        @Override public void handle(MouseEvent e) {
			        	System.out.println("Back button clicked");
			        	controller.handleEvent(Controller.MAINSCREEN);
			        }
			});
		
		
		Button button7 = new Button ("Logout", null);
		button7.setMaxSize(150, 100);
		button7.setWrapText(true);
		//Mouse event handling for Logging Out
		button7.addEventHandler(MouseEvent.MOUSE_CLICKED,
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
		box.getChildren().addAll(button, button2, button3, button5);
		if(controller.getLoggedInUser().isManager())box.getChildren().add(button4);
		box.setAlignment(Pos.CENTER);

		//This adds spaces when items are being added (top,left, bottom, right)
		box.setMargin(button, new Insets(15,20,15,20));
		box.setMargin(button2, new Insets(15,20,15,20));
		box.setMargin(button3, new Insets(15,20,15,20));
		box.setMargin(button4, new Insets(15,20,15,20));
		box.setMargin(button5, new Insets(15,20,15,20));
		
		VBox box2 = new VBox();
		box2.getChildren().addAll(button6, button7);
		box2.setAlignment(Pos.BOTTOM_RIGHT);
		box2.setMargin(button6, new Insets(10,10,10,10));
		box2.setMargin(button7, new Insets(10,10,10,10));

		root.setCenter(box);
		root.setBottom(box2);

	}

	public Scene scene() {
			return scene;
	}


}
