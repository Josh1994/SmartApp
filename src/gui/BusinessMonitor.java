package gui;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

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
	VBox vbox;

	public BusinessMonitor(ArrayList <String> critLabels){
		vbox = new VBox();
		this.critLabels = critLabels;
		HBox revBox = new HBox(20);
		HBox expBox = new HBox(20);
		HBox evntBox = new HBox(20);
		HBox mailBox = new HBox(20);
		HBox avgBox = new HBox(20);
		HBox critBox = new HBox(20);


		Label rev = new Label("Revenue");
		rev.setMinHeight(25);
		Label revVal = new Label(revenueLabel);
		revVal.setMinHeight(25);
		revBox.getChildren().addAll(rev, revVal);

		Label expend = new Label("Expenditure");
		expend.setMinHeight(25);
		Label expendVal = new Label(expLabel);
		expendVal.setMinHeight(25);
		expBox.getChildren().addAll(expend, expendVal);


		Label numE = new Label("Number of Events");
		numE.setMinHeight(25);
		Label numVal = new Label(numEvLabel);
		numVal.setMinHeight(25);
		evntBox.getChildren().addAll(numE, numVal);

		Label mail = new Label("Mail Amount");
		mail.setMinHeight(25);
		Label mailVal = new Label(mailAmountLabel);
		mailVal.setMinHeight(25);
		mailBox.getChildren().addAll(mail, mailVal);

		Label avg = new Label("Average Delivery Time");
		avg.setMinHeight(25);
		Label avgVal = new Label(avgDelTimeLabel);
		avgVal.setMinHeight(25);
		avgBox.getChildren().addAll(avg, avgVal);

		Label crit = new Label("Critical Routes");
		crit.setMinHeight(25);
		critBox.getChildren().addAll(rev);
		for (String label : critLabels){
			Label l = new Label (label);
			l.setMinHeight(25);
			critBox.getChildren().add(l);
		}

		vbox.getChildren().addAll(revBox, expBox, evntBox, mailBox, avgBox, critBox);
		
	}
	
	
	public void setRevenue(String revenue){
		this.revenueLabel = revenue;
	}
	
	
	public VBox vbox(){
		
		return vbox;
	}


}
