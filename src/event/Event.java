package event;

public class Event {

	//Type or priority
	public static final String SEA = "SEA";
	public static final String AIR = "AIR";
	public static final String LAND = "LAND";
	
	String origin;
	String destination;
	String type;
	
	
	public Event(String origin, String destination, String type){
		this.origin = origin;
		this.destination = destination;
		this.type = type;
	}
	
	
}
