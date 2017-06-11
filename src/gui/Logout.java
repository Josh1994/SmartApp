package gui;

import controller.Controller;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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

    private Button logoutButton;
    private Button cancelButton;
	
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
        bodyLabel.setPadding(new Insets(10));

        logoutButton = new Button("Logout");
        logoutButton.setOnAction(this);
        logoutButton.setAlignment(Pos.CENTER);
        
        cancelButton = new Button("Cancel");
        cancelButton.setOnAction(this);
        cancelButton.setAlignment(Pos.CENTER);

        HBox hBox_Buttons = new HBox(logoutButton, cancelButton);
        hBox_Buttons.setAlignment(Pos.CENTER_RIGHT);
        hBox_Buttons.setSpacing(10);
        hBox_Buttons.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(bodyLabel);
        root.setBottom(hBox_Buttons);

        Scene scene = new Scene(root);
        window.setScene(scene);
        window.showAndWait();

    }
    
    @Override
    public void handle(ActionEvent event) {
    	if(event.getSource().equals(cancelButton)){
    		// System.out.println("Cancel Button clicked");
    		window.close();
    	}else if(event.getSource().equals(logoutButton)){
    		// System.out.println("Logout Button clicked");
    		controller.logout();
    		window.close();
    	}
	}

}
