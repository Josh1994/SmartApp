package gui;

import java.util.List;

import controller.Controller;
import event.Event;
import gui.base.DataEntryGUI;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.EventManager;

public class DecisionSupport implements DataEntryGUI{
	BusinessMonitor bs;
	EventManager eventManager;
	List<Event> eventList;
	Controller controller;
	public DecisionSupport(Controller controller){
		this.controller = controller;
		bs = new BusinessMonitor(this);
		eventList = controller.get;
		//bs.display(route);

	}

	@Override
	public void showError(String errmsg) {
		// TODO Auto-generated method stub

	}

	public void initialize(){
		//EventManager em = new EventManager(null, controller.getEventList());
	}
}
