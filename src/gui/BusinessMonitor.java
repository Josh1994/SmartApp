package gui;

import java.util.ArrayList;

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

	HBox hbox = new HBox(10);
	VBox vbox;
	VBox disBox;

	public BusinessMonitor(DataEntryGUI gui){
		vbox = new VBox();
		this.gui = gui;
		HBox revBox = new HBox(20);
		HBox expBox = new HBox(20);
		HBox evntBox = new HBox(20);
		HBox mailBox = new HBox(20);
		HBox avgBox = new HBox(20);
		HBox critBox = new HBox(20);


		Label rev = new Label("Revenue");
		rev.setMinHeight(25);

		Label expend = new Label("Expenditure");
		expend.setMinHeight(25);


		Label numE = new Label("Number of Events");
		numE.setMinHeight(25);

		Label mail = new Label("Mail Amount");
		mail.setMinHeight(25);

		Label avg = new Label("Average Delivery Time");
		avg.setMinHeight(25);

		Label crit = new Label("Critical Routes");
		crit.setMinHeight(25);

		vbox.getChildren().addAll(rev, expend, numE, mail, avg, crit);
		hbox.getChildren().add(vbox);
	}

	public void display(Route route){
		disBox = new VBox();
		HBox critBox = new HBox(20);

		Label revVal = new Label(Double.toString(route.getRevenue()));
		revVal.setMinHeight(25);

		Label expendVal = new Label(Double.toString(route.getExpenditure()));
		expendVal.setMinHeight(25);

		Label numVal = new Label(Double.toString(route.getNumberOfEvents()));
		numVal.setMinHeight(25);

		Label mailVal = new Label(Integer.toString(route.getAmountOfmail()));
		mailVal.setMinHeight(25);

		Label avgVal = new Label(Double.toString(route.getAvgDeliveryTime()));
		avgVal.setMinHeight(25);

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
//		vbox.getChildren().addAll(vBox);
//		hbox.getChildren().addAll(vbox,disBox);
	}

	public VBox vbox(){

		return vbox;
	}
	public HBox hbox(){

		return hbox;
	}


}
