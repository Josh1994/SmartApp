package gui;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AlertBox {
	private Stage window;
	public static void display(String title, String message){
		Stage window = new Stage();
		
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);

		GridPane gridpane = new GridPane();
		gridpane.setPadding(new Insets(10));
		gridpane.setHgap(10);
		gridpane.setVgap(10);

		Scene scene = new Scene(gridpane);


		Label label = new Label(message);
		label.setMaxWidth(350);
		label.setWrapText(true);

		HBox hbLabel = new HBox(10);
		hbLabel.setAlignment(Pos.CENTER);
		hbLabel.getChildren().add(label);
		gridpane.add(hbLabel, 0, 0);

		Button okayButton = new Button("Okay");
		okayButton.setOnAction(e -> window.close());
		okayButton.setAlignment(Pos.CENTER);
		okayButton.setPadding(new Insets(5, 20, 5,20));

		HBox hbBtn = new HBox();
		hbBtn.setAlignment(Pos.CENTER);
		hbBtn.getChildren().add(okayButton);
		gridpane.add(hbBtn, 0, 1);

		window.setScene(scene);
		window.showAndWait();
		/*window.setOnShown(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) {
				window.sizeToScene();
			}
		});*/
	}

	
	
}
