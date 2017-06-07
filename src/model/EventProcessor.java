package model;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import model.RouteFinder;
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
	//Add new tcu that didn't exist in the array list.
	public void addTCU(TransportCostUpdate tcu){
		this.currentTcu.add(tcu);
	}
	//Update existing tcu
	public void processTCU(TransportCostUpdate tcu){
		//if tcu route is not in the list add it
		//otherwise just change it
		boolean updated = false;
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
	public void processTD(TransportDiscontinued td){
		//It will put all the transport disc request in a list and delete all of them once this method is called.
		this.currentTd.add(td);
		for(TransportDiscontinued transportDiscontinued : this.currentTd){
			String origin = td.getOrigin();
			String destination = td.getDestination();
			String priority = td.getPriority();
			String city = td.getCity();
//			if (priority.equals("LAND")){
//				System.out.println("Going through LAND route map");
//			}
//			else if (priority.equals("AIR")){
//				System.out.println("Going through AIR route map");
//
//			}
//			else if (priority.equals("SEA")){
//				System.out.println("Going through SEA route map");
//
//			}
//			else{
//				System.out.println("Going through DOMESTIC route map");
//
//			}
			//After locating the route. Remove it in the tcu and td arraylists.
			for(TransportCostUpdate tc : this.currentTcu){
				if(transportDiscontinued.getOrigin().equals(tc.getOrigin()) && transportDiscontinued.getDestination().equals(tc.getDestination())
						&& transportDiscontinued.getPriority().equals(tc.getPriority())){
					System.out.println("Removing transport: "+ td);
					this.currentTcu.remove(tc);
					this.currentTd.remove(transportDiscontinued);
				}
			}

		}
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
