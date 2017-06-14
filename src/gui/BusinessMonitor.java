package gui;

import java.util.ArrayList;
import java.util.List;

import gui.base.DataEntryGUI;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Route;

public class BusinessMonitor{
	private ArrayList <String> critLabels = new ArrayList();
	String revenueLabel = "";
	String expLabel = "";
	String numEvLabel = "";
	String mailAmountLabel = "";
	String avgDelTimeLabel = "";
	String critLabel ="";
	TextField revenue;
	TextField expenditure;
	TextField numEvents;
	TextField mailAmount;
	TextField avgDelTime;
	TextField critRev;
	DataEntryGUI gui; //Responsible critical routes
	Label rev, expend, numE, mail, avg, crit;
	Label revVal, expendVal, numVal, mailVal, avgVal, critVal;

	HBox hbox = new HBox(10);
	VBox vbox;
	VBox disBox;

	public BusinessMonitor(DataEntryGUI gui){
		HBox revBox = new HBox(20);
		HBox expBox = new HBox(20);
		HBox evntBox = new HBox(20);
		HBox mailBox = new HBox(20);
		HBox avgBox = new HBox(20);
		HBox critBox = new HBox(20);

		vbox = new VBox();
		this.gui = gui;



		rev = new Label("Revenue");
		rev.setMinHeight(25);
		revVal = new Label("0");
		System.out.println(revVal.getText());
		revVal.setMinHeight(25);
		revBox.getChildren().addAll(rev, revVal);

		expend = new Label("Expenditure");
		expend.setMinHeight(25);
		expendVal = new Label("0");
		expendVal.setMinHeight(25);
		expBox.getChildren().addAll(expend, expendVal);

		numE = new Label("Number of Events");
		numE.setMinHeight(25);
		numVal = new Label("0");
		numVal.setMinHeight(25);
		evntBox.getChildren().addAll(numE, numVal);

		mail = new Label("Mail Amount");
		mail.setMinHeight(25);
		mailVal = new Label("0");
		mailVal.setMinHeight(25);
		mailBox.getChildren().addAll(mail, mailVal);

		avg = new Label("Average Delivery Time");
		avg.setMinHeight(25);
		avgVal = new Label("0");
		avgVal.setMinHeight(25);
		avgBox.getChildren().addAll(avg, avgVal);

		crit = new Label("Critical Routes");
		crit.setMinHeight(25);
//
//		hbox.getChildren().addAll(rev, expend, numE, mail, avg, crit);
//		vbox.getChildren().add(hbox);

		vbox.getChildren().addAll(revBox, expBox, evntBox, mailBox, avgBox, critBox);
	}
<<<<<<< HEAD
=======

>>>>>>> ac40eb0dd138871e785f546485d33068e4f8222e

	public void display(Route route){

		revVal.setText(Double.toString(route.getRevenue()));

		expendVal.setText(Double.toString(route.getExpenditure()));

		numVal.setText(Double.toString(route.getNumberOfEvents()));

		mailVal.setText(Double.toString(route.getAmountOfmail()));

		avgVal.setText(Double.toString(route.getAvgDeliveryTime()));

//		Critical Routes obtaining only 5 at most
//		ArrayList <Route> critRoutes = gui.getController().getModel().getEventManager().getCriticalRoutes().take(5) ;
//		for (Route crit : critRoutes){
//			Label o = new Label (route.Origin());
//			l.setMinHeight(25);
//			Label d = new Label (route.Destination());
//			l.setMinHeight(25);
//			Label p = new Label (route.priority());
//			l.setMinHeight(25);
//			critBox.getChildren().addAll(d,o,p);
//		}
		//vbox.getChildren().addAll(vbox);
		//disBox.getChildren().addAll(revBox, expBox, evntBox, mailBox, avgBox, critBox);
	}
<<<<<<< HEAD
=======


	
	public void setRevenue(String revenue){
		this.revenueLabel = revenue;
	}
	
	public void setExpenditure(String exp){
		this.expLabel = exp;
	}
	
	public void setCritLabels(ArrayList<String> critLabels){
		this.critLabels = critLabels;
	}
	
	public void setNumEvents(String numEv){
		this.numEvLabel = numEv;
	}
	
	public void setMailAmount(String amount){
		this.mailAmountLabel = amount;
	}
	
	public void setAveDelTime(String avgDelTime){
		this.avgDelTimeLabel = avgDelTime;
	}

>>>>>>> ac40eb0dd138871e785f546485d33068e4f8222e
	public VBox vbox(){

		return vbox;
	}
	public HBox hbox(){

		return hbox;
	}

	public String getRevenueLabel() {
		return revenueLabel;
	}

	public void setRevenueLabel(String revenueLabel) {
		this.revenueLabel = revenueLabel;
	}

	public String getExpLabel() {
		return expLabel;
	}

	public void setExpLabel(String expLabel) {
		this.expLabel = expLabel;
	}

	public String getNumEvLabel() {
		return numEvLabel;
	}

	public void setNumEvLabel(String numEvLabel) {
		this.numEvLabel = numEvLabel;
	}

	public String getMailAmountLabel() {
		return mailAmountLabel;
	}

	public void setMailAmountLabel(String mailAmountLabel) {
		this.mailAmountLabel = mailAmountLabel;
	}

	public String getAvgDelTimeLabel() {
		return avgDelTimeLabel;
	}

	public void setAvgDelTimeLabel(String avgDelTimeLabel) {
		this.avgDelTimeLabel = avgDelTimeLabel;
	}

	public String getCritLabel() {
		return critLabel;
	}

	public void setCritLabel(String critLabel) {
		this.critLabel = critLabel;
	}


}
