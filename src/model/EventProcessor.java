package model;
import java.util.ArrayList;
import java.util.List;

import event.CustomerPriceUpdate;
import event.Event;
import event.MailDelivery;
import event.TransportCostUpdate;
import event.TransportDiscontinued;

public class EventProcessor {

	//from the list of all current tcu we can get a list of all routes.
	private List<TransportCostUpdate> currentTcu;
	private List<TransportDiscontinued> currentTd;
	
	public EventProcessor(){
		this.currentTcu = new ArrayList<TransportCostUpdate>();
		this.currentTd = new ArrayList<TransportDiscontinued>();
	}
	
	
	//Event proccessing
	public void processTCU(TransportCostUpdate tcu){
		//if tcu route is not in the list add it
		//otherwise just change it
		boolean updated = false;
		
		if(this.currentTcu.isEmpty()){
			this.currentTcu.add(tcu);
		}
		else{
			for(TransportCostUpdate transportCostUpdate : this.currentTcu){
				String origin = tcu.getOrigin();
				String destination = tcu.getDestination();
				String priority = tcu.getPriority();
				//contains the route then update
				if(origin.equals(transportCostUpdate.getOrigin()) && destination.equals(transportCostUpdate.getDestination()) && priority.equals(transportCostUpdate.getPriority())){
					transportCostUpdate = tcu;
					System.out.println("Replaced old tcu");
					updated = true;
					break;
				}
			}
			//if the tcu was not found in the current tcu list. add it to the end
			if(!updated){
				this.currentTcu.add(tcu);
			}
			
			
			
		}
	
	}
	public void processTD(TransportDiscontinued td){
		//It will put all the transport disc request in a list and delete all of them once this method is called.
		this.currentTd.add(td);
	
	}
	
	//Getters
	public List<TransportCostUpdate> getCurrentTCU(){
		return this.currentTcu;
	}
	public List<TransportDiscontinued> getCurrentTd(){
		return this.currentTd;
	}
	//Booleans
	public boolean containsRoute(TransportCostUpdate tcu){
		
		for(TransportCostUpdate transportCostUpdate : this.currentTcu){
			String origin = tcu.getOrigin();
			String destination = tcu.getDestination();
			String priority = tcu.getPriority();
			if(origin.equals(transportCostUpdate.getOrigin()) && destination.equals(transportCostUpdate.getDestination()) && priority.equals(transportCostUpdate.getPriority())){
				System.out.println("current Tcu list contains tcu");
				return true;
			}
		}
		return false;
	}
	
	

}
