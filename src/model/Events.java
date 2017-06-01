package model;
import java.util.ArrayList;
import java.util.List;

import event.CustomerPriceUpdate;
import event.Event;
import event.MailDelivery;
import event.TransportCostUpdate;
import event.TransportDiscontinued;

public class Events {

	private List<CustomerPriceUpdate> customerPriceUpdate;
	private List<MailDelivery> mailDelivery;
	private List<TransportCostUpdate> transportCostUpdate;
	private List<TransportDiscontinued> transportDiscontinued;
	private List<Event> allEvents;
	
	public Events(){
		this.customerPriceUpdate = new ArrayList<CustomerPriceUpdate>();
		this.mailDelivery = new ArrayList<MailDelivery>();
		this.transportCostUpdate = new ArrayList<TransportCostUpdate>();
		this.transportDiscontinued = new ArrayList<TransportDiscontinued>();
		this.allEvents = new ArrayList<Event>();
	}

	public List<CustomerPriceUpdate> getCustomerPriceUpdate() {
		return this.customerPriceUpdate;
	}

	public List<MailDelivery> getMailDelivery() {
		return this.mailDelivery;
	}

	public List<TransportCostUpdate> getTransportCostUpdate() {
		return this.transportCostUpdate;
	}

	public List<TransportDiscontinued> getTransportDiscontinued() {
		return this.transportDiscontinued;
	}
	
	public List<Event> getAllEvents() {
		return this.allEvents;
	}
	
	public void addEvent(Event e){
		if(e instanceof CustomerPriceUpdate){
			this.customerPriceUpdate.add((CustomerPriceUpdate)e);
		}
		else if(e instanceof MailDelivery){
			this.mailDelivery.add((MailDelivery)e);
		}
		else if(e instanceof TransportCostUpdate){
			this.transportCostUpdate.add((TransportCostUpdate)e);
		}
		else if(e instanceof TransportDiscontinued){
			this.transportDiscontinued.add((TransportDiscontinued)e);
		}
		this.allEvents.add(e);
		
	}
	
}
