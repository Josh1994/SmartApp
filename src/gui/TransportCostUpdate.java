package gui;


import event.Event;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import gui.base.DataEntryGUI;
import gui.dialogs.AlertDialog;
import gui.dialogs.ConfirmDialog;
import gui.dialogs.LogoutDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Route;
import model.User;

public class TransportCostUpdate implements DataEntryGUI,EventHandler<ActionEvent>{

	Button eventButton;
	Button backButton;
	Button logoutButton;
	ComboBox<String> firm;
	//land sea air
	ToggleGroup type;
	RadioButton land;
	RadioButton sea;
	RadioButton air;
	RadioButton domestic;
	//sunday to saturday
	CheckBox sunday;
	CheckBox monday;
	CheckBox tuesday;
	CheckBox wednesday;
	CheckBox thursday;
	CheckBox friday;
	CheckBox saturday;
	List<CheckBox> daysOfWeek;
	ComboBox<String> origin;
	ComboBox<String> destination;
	TextField weightPrice;
	TextField volumePrice;
	TextField frequency;
	TextField duration;
	TextField maxWeight;
	TextField maxVolume;
	
	static Scene scene;
	Controller controller;


	
	public TransportCostUpdate(Controller controller){
		
		this.controller = controller;
		
		daysOfWeek = new ArrayList<CheckBox>();

		eventButton = new Button();
		eventButton.setText("Update Cost");
		eventButton.setOnAction(this);

		backButton = new Button();
		backButton.setText("Back");
		backButton.setOnAction(this);

        logoutButton = new Button();
		logoutButton.setText("Logout");
		logoutButton.setOnAction(this);
		
		Label fromLabel = new Label("Origin: ");
		fromLabel.setMinHeight(25);
		origin = new ComboBox<String>();
		origin.setMinWidth(200.0);
		origin.getItems().addAll(controller.getModel().getEventManager().getLocationNames());
		origin.setEditable(true);
		
		Label toLabel = new Label("Destination: ");
		toLabel.setMinHeight(25);
		destination = new ComboBox<String>();
		destination.setMinWidth(200.0);
		destination.getItems().addAll(controller.getModel().getEventManager().getLocationNames());
		destination.setEditable(true);
		
		Label weightLabel = new Label("Price per kg: ");
		weightLabel.setMinHeight(25);
		weightPrice = new TextField();
		weightPrice.setMaxHeight(10);
		weightPrice.setMaxWidth(200);
		
		Label volumeLabel = new Label("Price per cubic cm: ");
		volumeLabel.setMinHeight(25);
		volumePrice = new TextField();
		volumePrice.setMaxHeight(10);
		volumePrice.setMaxWidth(200);
		
		Label maxWeightLabel = new Label("Max Weight: ");
		maxWeightLabel.setMinHeight(25);
		maxWeight = new TextField();
		maxWeight.setMaxHeight(10);
		maxWeight.setMaxWidth(200);
		
		Label maxVolumeLabel = new Label("Max Volume: ");
		maxVolumeLabel.setMinHeight(25);
		maxVolume = new TextField();
		maxVolume.setMaxHeight(10);
		maxVolume.setMaxWidth(200);
		
		Label firmLabel = new Label("Firm: ");
		firmLabel.setMinHeight(25);
		firm = new ComboBox<String>();
		firm.setMinWidth(200);
		firm.getItems().addAll(controller.getModel().getEventManager().getFirmNames());
		firm.setEditable(true);
		firm.setMaxHeight(10);
		firm.setMaxWidth(200);
		
		Label daysLabel = new Label("Days of the week: ");
		daysLabel.setMinHeight(25);
		
		HBox days = new HBox();
		days.setMaxHeight(10);
		
		monday = new CheckBox("Monday");
		tuesday = new CheckBox("Tuesday");
		wednesday = new CheckBox("Wednesday");
		thursday = new CheckBox("Thursday");
		friday = new CheckBox("Friday");
		saturday = new CheckBox("Saturday");
		sunday = new CheckBox("Sunday");
		
		daysOfWeek.add(monday);
		daysOfWeek.add(tuesday);
		daysOfWeek.add(wednesday);
		daysOfWeek.add(thursday);
		daysOfWeek.add(friday);
		daysOfWeek.add(saturday);
		daysOfWeek.add(sunday);
		
		days.getChildren().addAll(sunday,monday,tuesday,wednesday,thursday,friday,saturday);
		
		Label frequencyLabel = new Label("Frequency: ");
		frequencyLabel.setMinHeight(25);
		frequency = new TextField();
		frequency.setMaxHeight(10);
		frequency.setMaxWidth(200);
		
		Label durationLabel = new Label("Duration: ");
		durationLabel.setMinHeight(25);
		duration = new TextField();
		duration.setMaxHeight(10);
		duration.setMaxWidth(200);
		
		Label typeLabel = new Label("Type: ");
		typeLabel.setMinHeight(25);
		type = new ToggleGroup();
		land = new RadioButton("Land");
		sea = new RadioButton("Sea");
		air = new RadioButton("Air");
		domestic = new RadioButton("Domestic");
		
		land.setToggleGroup(type);
		sea.setToggleGroup(type);
		air.setToggleGroup(type);
		domestic.setToggleGroup(type);
		
		
		HBox typeHBox = new HBox(land,sea,air, domestic);
		
		
		
		VBox vbox1 = new VBox(10);
		vbox1.setPadding(new Insets(10, 10, 10, 10));
		vbox1.getChildren().add(firmLabel);
		vbox1.getChildren().add(typeLabel);
		vbox1.getChildren().add(fromLabel);
		vbox1.getChildren().add(toLabel);
		vbox1.getChildren().add(weightLabel);
		vbox1.getChildren().add(volumeLabel);
		vbox1.getChildren().add(maxWeightLabel);
		vbox1.getChildren().add(maxVolumeLabel);
		vbox1.getChildren().add(daysLabel);
		vbox1.getChildren().add(frequencyLabel);
		vbox1.getChildren().add(durationLabel);
		vbox1.getChildren().add(backButton);
		vbox1.getChildren().add(logoutButton);
		
		VBox vbox2 = new VBox(10);
		vbox2.setPadding(new Insets(10, 10, 10, 10));
		vbox2.getChildren().add(firm);
		vbox2.getChildren().add(typeHBox);
		vbox2.getChildren().add(origin);
		vbox2.getChildren().add(destination);
		vbox2.getChildren().add(weightPrice);
		vbox2.getChildren().add(volumePrice);
		vbox2.getChildren().add(maxWeight);
		vbox2.getChildren().add(maxVolume);
		vbox2.getChildren().add(days);
		vbox2.getChildren().add(frequency);
		vbox2.getChildren().add(duration);
		vbox2.getChildren().add(eventButton);
		
		HBox hbox = new HBox(20);
		hbox.setPadding(new Insets(20, 20, 20, 20));
		hbox.getChildren().addAll(vbox1, vbox2);
		
		scene = new Scene(hbox, 800, 550);
	}
	
	public static Scene scene() {
		return scene;
	}
	
	@Override
	public void handle(ActionEvent event) {
		// TODO Auto-generated method stub
		if(event.getSource() == eventButton){
			
			int count = 0;
			for(CheckBox day : daysOfWeek){
				if(day.isSelected()){
					count = count + 1;
				}
			}
			
			if(firm.getValue() == null || type.getSelectedToggle() == null || origin.getValue()==null || destination.getValue()==null || weightPrice.getText().isEmpty() || volumePrice.getText().isEmpty() || frequency.getText().isEmpty() || duration.getText().isEmpty()){
				AlertDialog.display("Invalid Input", "Invalid Input Fields");
			}
			
			
			else if(count == 0){
				AlertDialog.display("Invalid Input", "Selected days can't be zero");
			}
			else{
				
				//Tell the controller that there is a price update
				ConfirmDialog confirmDialog = new ConfirmDialog();
				String message = "Are you sure you want to update transport cost for " + this.firm.getValue();
				String title = "Transport Cost Update Confirmation";
				confirmDialog.display(title, message);
				if(confirmDialog.confirm){
					String priority;
					event.TransportCostUpdate tcu;
					
					if(land.isSelected()){
						priority = Event.LAND;
					}
					else if(air.isSelected()){
						priority = Event.AIR;
					}
					else if(sea.isSelected()){
						priority = Event.SEA;
					}
					else{
						priority = Event.DOMESTIC;
					}
					List<DayOfWeek> days = new ArrayList<DayOfWeek>();
					for(CheckBox cbox : daysOfWeek){
						if(cbox.isSelected()){
							if(cbox.getText().equals("Monday")){
								days.add(DayOfWeek.MONDAY);
							}
							else if(cbox.getText().equals("Tuesday")){
								days.add(DayOfWeek.TUESDAY);
							}
							else if(cbox.getText().equals("Wednesday")){
								days.add(DayOfWeek.WEDNESDAY);
							}
							else if(cbox.getText().equals("Thursday")){
								days.add(DayOfWeek.THURSDAY);
							}
							else if(cbox.getText().equals("Friday")){
								days.add(DayOfWeek.FRIDAY);
							}
							else if(cbox.getText().equals("Saturday")){
								days.add(DayOfWeek.SATURDAY);
							}
							else{
								days.add(DayOfWeek.SUNDAY);
							}
						}
					}
					
					String from = origin.getValue();
					String to = destination.getValue();
					double weightPr = Double.parseDouble(weightPrice.getText());
					double volPr = Double.parseDouble(volumePrice.getText());
					double freq = Double.parseDouble(frequency.getText());
					double dur = Double.parseDouble(duration.getText());
					int maxWei = Integer.parseInt(maxWeight.getText());
					int maxVol = Integer.parseInt(maxVolume.getText());
					ZonedDateTime zdt = ZonedDateTime.now();
					User user = controller.getLoggedInUser();
					//make transport cost update event and send it to the controller
					tcu = new event.TransportCostUpdate(zdt, user.getUsername(), from, to, weightPr, volPr, maxWei, maxVol, freq, dur, days, firm.getValue(), priority);
					System.out.println(tcu.toString());
					
					controller.handleEvent(tcu, this);
					
				}
			}
		}
		if(event.getSource() == backButton){
			controller.handleEvent(Controller.EVENTGUI);
		}if(event.getSource() == logoutButton){
			LogoutDialog logoutDialog = new LogoutDialog(controller);
			logoutDialog.display();
		}
		
	}
	public void showError(String errormsg) {

	}

	@Override
	public void displayRoute(Route route) {
		// TODO Auto-generated method stub
		System.out.println("Getting route");
		StringBuilder sb = new StringBuilder();
		sb.append("Route: ");
		sb.append(route.toString());
		
		AlertDialog.display("Route", sb.toString());
		
	}

	@Override
	public Controller getController() {
		// TODO Auto-generated method stub
		return this.controller;
	}
}

