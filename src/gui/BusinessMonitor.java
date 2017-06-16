package gui;

import java.util.ArrayList;
import java.util.List;

import event.*;
import event.TransportCostUpdate;
import event.TransportDiscontinued;
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
	Label rev, expend, numE, mail, avg, crit, routeLabel;
	Label revVal, expendVal, numVal, mailVal, avgVal, critVal,
			origVal, destVal, prioVal, mailW, mailWVal, mailV, mailVVal, eventLabel;

	HBox hbox = new HBox(10);
	VBox vbox;
	VBox disBox;

	public BusinessMonitor(DataEntryGUI gui){
		HBox routeBox = new HBox(20);
		HBox revBox = new HBox(20);
		HBox expBox = new HBox(20);
		HBox evntBox = new HBox(20);
		HBox mailBox = new HBox(20);
		HBox avgBox = new HBox(20);
		HBox critBox = new HBox(20);

		vbox = new VBox();
		this.gui = gui;


		routeLabel = new Label("Overall Figures");
		routeLabel.setMinHeight(25);
		origVal = new Label(" ");
		origVal.setMinHeight(25);
		destVal = new Label(" ");
		destVal.setMinHeight(25);
		prioVal = new Label(" ");
		prioVal.setMinHeight(25);
		routeBox.getChildren().addAll(routeLabel, origVal, destVal, prioVal);

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

		mail = new Label("Mail Totals    Items:");
		mail.setMinHeight(25);
		mailVal = new Label("0");
		mailVal.setMinHeight(25);
		mailW = new Label("Weight:");
		mailW.setMinHeight(25);
		mailWVal = new Label("0");
		mailWVal.setMinHeight(25);
		mailV = new Label("Volume:");
		mailV.setMinHeight(25);
		mailVVal = new Label("0");
		mailVVal.setMinHeight(25);
		mailBox.getChildren().addAll(mail, mailVal, mailW, mailWVal, mailV, mailVVal);

		avg = new Label("Average Delivery Time");
		avg.setMinHeight(25);
		avgVal = new Label("0");
		avgVal.setMinHeight(25);
		avgBox.getChildren().addAll(avg, avgVal);

		crit = new Label(" ");
		crit.setMinHeight(25);

		eventLabel = new Label(" ");
		eventLabel.setMinHeight(25);


//
//		hbox.getChildren().addAll(rev, expend, numE, mail, avg, crit);
//		vbox.getChildren().add(hbox);

		vbox.getChildren().addAll(routeBox, revBox, expBox, evntBox, mailBox, avgBox, crit, eventLabel);
	}


	public void display(Route route){

		revVal.setText(Double.toString(route.getRevenue()));
		expendVal.setText(Double.toString(route.getExpenditure()));
		//numVal.setText(Double.toString(gui.getController().getModel().getEventManager().getTotalEvents()));
		mailVal.setText(Double.toString(route.getAmountOfmail()));
		mailWVal.setText(Double.toString(route.getWeightOfMail()));
		mailVVal.setText(Double.toString(route.getVolumeOfMail()));
		//avgVal.setText(Double.toString(gui.getController().getModel().getEventManager().getAvgDeliveryTime()));

		if (route.isCombinedRoute()) route = route.getInternational();
		if (route.getDestination().equals("AVG")){
			routeLabel.setText("Overall Figures");
			origVal.setText(" ");
			destVal.setText(" ");
			prioVal.setText(" ");
			numVal.setText(Double.toString(gui.getController().getModel().getEventManager().getTotalEvents()));
			avgVal.setText(Double.toString(gui.getController().getModel().getEventManager().getAvgDeliveryTime()));
		}
		else{
			routeLabel.setText("Route: ");
			origVal.setText(route.getOrigin());
			destVal.setText(route.getDestination());
			prioVal.setText(route.getPriority());
			numVal.setText(Double.toString(route.getNumberOfEvents()));
			avgVal.setText(Double.toString(route.getAvgDeliveryTime()));

			if (route.isCriticalRoute()) crit.setText("*** CRITICAL ROUTE ***");
			else crit.setText("Profitable Route.");
		}
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

	public void displayEvent(Event event) {
		/**
		String eventString = event.getOrigin() + " , " + event.getDestination() + " , " + event.getPriority();
		if (event instanceof event.TransportCostUpdate) {
			TransportCostUpdate tcu = (TransportCostUpdate) event;
			eventString = "TransportCostUpdate: " + eventString + " , " + tcu.getFirm();
		} else if (event instanceof event.MailDelivery) {
			eventString = "MailDelivery: " + eventString;
		}
		if (event instanceof event.CustomerPriceUpdate) {
			eventString = "CustomerPriceUpdate: " + eventString;
		} else if (event instanceof event.TransportDiscontinued) {
			TransportDiscontinued td = (TransportDiscontinued) event;
			eventString = "TransportDiscontinued: " + eventString + " , " + td.getFirm();
		}
		 */
		eventLabel.setText(event.toString());
	}

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
