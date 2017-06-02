package event;

import java.time.ZonedDateTime;

public class Event {

	//Type or priority
	public static final String SEA = "sea";
	public static final String AIR = "air";
	public static final String LAND = "land";
	public static final String DOMESTIC = "domestic";

	// General
	ZonedDateTime dateTime;
	String user;

	// Event specific
	String origin;
	String destination;
	String type;


	public Event(ZonedDateTime dateTime, String user, String origin, String destination, String type) {
		this.dateTime = dateTime;
		this.user = user;
		this.origin = origin;
		this.destination = destination;
		this.type = type;
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
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}
	
	

