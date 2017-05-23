package GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
	private Stage window;
	public static void display(String title, String message){
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(250);
		window.setMinHeight(100);
		
		Label label = new Label();
		label.setText(message);
		Button okayButton = new Button("Okay");
		okayButton.setOnAction(e -> window.close());
		
		
		VBox vbox = new VBox(15);
		vbox.getChildren().addAll(label, okayButton);
		vbox.setAlignment(Pos.CENTER);
		Scene scene = new Scene(vbox);
		window.setScene(scene);
		window.showAndWait();
		
	}

	
	
}
