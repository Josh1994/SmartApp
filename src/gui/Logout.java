package gui;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * This is a custom dialog box that is displayed when the the user clicks the logout button on any
 * of the screens. It offers the user with two options, logout and cancel, should the user cancel
 * it will take the user back to the previous screen and if the user clicks logout the user will
 * be logged out and returned to the login screen.
 */
public class Logout implements EventHandler<ActionEvent>{
	
	// Global Components
    private Stage window;
    private Controller controller;
    Button logoutButton;
    Button cancelButton;
	
    public Logout(Controller controller){
    	this.controller = controller;
    }
    
    public void display(){
        window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Logout");
        window.setMinWidth(250);
        window.setMinHeight(100);
        
        Label bodyLabel = new Label("Do you wish to logout?");

        logoutButton = new Button("Logout");
        logoutButton.setOnAction(this);
        logoutButton.setAlignment(Pos.CENTER);
        
        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(this);
        cancelButton.setAlignment(Pos.CENTER);
        
        VBox vBox = new VBox(bodyLabel, logoutButton, cancelButton, new Label());
        vBox.setSpacing(20);

        vBox.setAlignment(Pos.CENTER);
        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.showAndWait();

    }
    
    @Override
    public void handle(ActionEvent event) {
    	if(event.getSource().equals(cancelButton)){
    		System.out.println("Cancel Button clicked");
    		window.close();
    	}else if(event.getSource().equals(logoutButton)){
    		System.out.println("Logout Button clicked");
    		controller.logout();
    		window.close();
    	}
	}

}
