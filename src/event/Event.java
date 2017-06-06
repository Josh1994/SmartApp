package event;

import java.time.ZonedDateTime;

public class Event {

	//Type or priority
	public static final String SEA = "SEA";
	public static final String AIR = "AIR";
	public static final String LAND = "LAND";
	public static final String DOMESTIC = "DOMESTIC";

	// General
	ZonedDateTime dateTime;
	String user;

	// Event specific
	String origin;
	String destination;
	String priority;


	public Event(ZonedDateTime dateTime, String user, String origin, String destination, String priority) {
		this.dateTime = dateTime;
		this.user = user;
		this.origin = origin;
		this.destination = destination;
		this.priority = priority;
	}

	public ZonedDateTime getDateTime() {
		return dateTime;
	}

	public String getUser() {
		return user;
	}

	public String getOrigin() {
		return origin;
	}

	public String getDestination() {
		return destination;
	}
	
	public String getPriority(){
		return this.priority;
	}
}
