package model;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
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
	//private List<CustomerPriceUpdate> currentCpu;
	
	public EventProcessor(){
		this.currentTcu = new ArrayList<TransportCostUpdate>();
		this.currentTd = new ArrayList<TransportDiscontinued>();
		//this.currentCpu = new ArrayList<CustomerPriceUpdate>();
	}


	//Event proccessing
	
	//Update Customer Price
	public void processCPU(CustomerPriceUpdate cpu){
		
		for(TransportCostUpdate transportCostUpdate : this.currentTcu){
			String origin = cpu.getOrigin();
			String destination = cpu.getDestination();
			String priority = cpu.getPriority();
			//contains the route then update
			if(origin.equals(transportCostUpdate.getOrigin()) && destination.equals(transportCostUpdate.getDestination()) && priority.equals(transportCostUpdate.getPriority())){
				System.out.println("Replacing old weight cost "+transportCostUpdate.getWeightCost() +" with new weight cost"+cpu.getWeightCost());
				System.out.println("Replacing old volume cost "+transportCostUpdate.getVolumeCost() +" with new volume cost"+cpu.getVolumeCost());
				transportCostUpdate.setWeightCost(cpu.getWeightCost());
				transportCostUpdate.setVolumeCost(cpu.getVolumeCost());
				System.out.println("Customer Price update finished");
			}
		}
	}

	public static List<TransportCostUpdate> createTCUs() {
		ArrayList<TransportCostUpdate> tcus = new ArrayList<>();

		tcus.add(new TransportCostUpdate(ZonedDateTime.now(), "admin", "AKLD",
				"WGTN", 10, 10, 0 , 0, 0, 0,
				null, "CourierPost", Event.DOMESTIC));
		tcus.add( new TransportCostUpdate(ZonedDateTime.now(), "admin", "AKLD",
				"CHCH", 10, 10, 0 , 0, 0, 0,
				null, "CourierPost", Event.DOMESTIC));
		tcus.add( new TransportCostUpdate(ZonedDateTime.now(), "admin", "WGTN",
				"AKLD", 10, 10, 0 , 0, 0, 0,
				null, "CourierPost", Event.DOMESTIC));
		tcus.add( new TransportCostUpdate(ZonedDateTime.now(), "admin", "CHCH",
				"DNDN", 10, 10, 0 , 0, 0, 0,
				null, "CourierPost", Event.DOMESTIC));

		tcus.add( new TransportCostUpdate(ZonedDateTime.now(), "admin", "AKLD",
				"SIDNEY", 10, 10, 0 , 0, 0, 0,
				null, "NZPost", Event.AIR));
		tcus.add( new TransportCostUpdate(ZonedDateTime.now(), "admin", "AKLD",
				"LA", 10, 10, 0 , 0, 0, 0,
				null, "PostHaste", Event.AIR));
		tcus.add( new TransportCostUpdate(ZonedDateTime.now(), "admin", "LA",
				"LONDON", 10, 10, 0 , 0, 0, 0,
				null, "PostMan", Event.AIR));
		tcus.add( new TransportCostUpdate(ZonedDateTime.now(), "admin", "LA",
				"DUBLIN", 10, 10, 0 , 0, 0, 0,
				null, "CouriersNZ", Event.AIR));
		// Intl Surface
		tcus.add( new TransportCostUpdate(ZonedDateTime.now(), "admin", "LA",
				"DUBLIN", 5, 5, 0 , 0, 0, 0,
				null, "KPFreights", Event.SEA));
		
		return tcus;
	}

	//Add new tcu that didn't exist in the array list.
	public void addTCU(TransportCostUpdate tcu){
		this.currentTcu.add(tcu);
	}
	//Process all the tcu requests added
	public void processTCU(TransportCostUpdate tcu){
		for(TransportCostUpdate transportCostUpdate : this.currentTcu){
			String origin = tcu.getOrigin();
			String destination = tcu.getDestination();
			String priority = tcu.getPriority();
			//contains the route then update
			if(origin.equals(transportCostUpdate.getOrigin()) && destination.equals(transportCostUpdate.getDestination()) && priority.equals(transportCostUpdate.getPriority())){
				transportCostUpdate = tcu;
				System.out.println("Replaced old tcu");
			}
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
			String firm = tcu.getFirm();
			if(firm.equals(transportCostUpdate.getFirm()) && origin.equals(transportCostUpdate.getOrigin()) && destination.equals(transportCostUpdate.getDestination()) && priority.equals(transportCostUpdate.getPriority())){
				System.out.println("current Tcu list contains tcu");
				return true;
			}
		}
		return false;
	}



	public Set<String> getLocationNames() {
		Set<String> locationNames = new HashSet<>();

		for(TransportCostUpdate tcu : currentTcu) {
			locationNames.add(tcu.getOrigin());
			locationNames.add(tcu.getDestination());
		}

		return locationNames;
	}
	
	public Set<String> getFirmNames(){
		Set<String> firmNames = new HashSet<>();
		for(TransportCostUpdate tcu : currentTcu){
			firmNames.add(tcu.getFirm());
		}
		return firmNames;
	}
}
