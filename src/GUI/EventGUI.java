package GUI;

import java.io.FileInputStream;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EventGUI extends Application {
	
	/*Loads the image from the Resource folder and turns it to imageView to be used as an icon
	with the button*/
	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("KPSmart Events");
		BorderPane root = new BorderPane();
		
		//Load the images for the 4 buttons
		FileInputStream transportInput = new FileInputStream("images/TCU.png");
		FileInputStream mailInput = new FileInputStream("images/MD.png");
		FileInputStream customerInput = new FileInputStream("images/CPU.png");
		FileInputStream tdInput = new FileInputStream("images/TD.png");
		
		Image transportImage = new Image(transportInput, 30, 30, false, false);
		Image mailImage = new Image(mailInput, 30, 30, false, false);
		Image customerImage = new Image(customerInput, 30, 30, false, false);
		Image tdImage = new Image(tdInput, 30, 30, false, false);
		
		ImageView transportView = new ImageView(transportImage);
		ImageView mailView = new ImageView(mailImage);
		ImageView customerView = new ImageView(customerImage);
		ImageView tdView = new ImageView(tdImage);
		
		//Button Creation
		Button button = new Button ("Transport Cost Update", transportView);
		button.setMaxSize(150, 100);
		button.setWrapText(true);
		
		Button button2 = new Button ("Mail Delivery", mailView);
		button2.setMaxSize(150, 100);
		button2.setWrapText(true);
		
		Button button3 = new Button ("Customer Price Update", customerView);
		button3.setMaxSize(150, 100);
		button3.setWrapText(true);
		
		Button button4 = new Button ("Transport Discontinued", tdView);
		button4.setMaxSize(150, 100);
		button4.setWrapText(true);
		
		Scene scene = new Scene(root, 600, 400);
		VBox box = new VBox();
		
		//Add all buttons to a container. Place the container on the center of the BorderPane
		box.getChildren().addAll(button, button2, button3, button4);
		box.setAlignment(Pos.CENTER);
		
		//This adds spaces when items are being added (top,left, bottom, right)
		box.setMargin(button, new Insets(20,20,20,20));
		box.setMargin(button2, new Insets(20,20,20,20));
		box.setMargin(button3, new Insets(20,20,20,20));
		box.setMargin(button4, new Insets(20,20,20,20));
		
		root.setCenter(box);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
    public static void main(String[] args) {
        Application.launch(args);
    }

}
