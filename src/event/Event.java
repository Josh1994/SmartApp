package event;

import java.time.ZonedDateTime;
import javax.xml.bind.annotation.XmlAttribute;  
import javax.xml.bind.annotation.XmlElement;  
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso; 

@XmlRootElement
@XmlSeeAlso({CustomerPriceUpdate.class,MailDelivery.class,
	TransportCostUpdate.class,TransportDiscontinued.class})
public class Event {

	//Type or priority
	public static final String SEA = "SEA";
	public static final String AIR = "AIR";
	public static final String LAND = "LAND";
	public static final String DOMESTIC = "DOMESTIC";
	private static final String eventType = "super";

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
	
	public ZonedDateTime getZonedDateTime(){
		return this.dateTime;
	}

	//For XML purposes don't remove
	public Event(){}

	public String getEventType(){
		return eventType;
	}

	@XmlElement
	public String getDateTime() {
		return dateTime.toString();
	}

	@XmlElement
	public String getUser() {
		return user;
	}

	@XmlElement
	public String getOrigin() {
		return origin;
	}

	@XmlElement
	public String getDestination() {
		return destination;
	}

	@XmlElement
	public String getPriority(){
		return this.priority;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Event event = (Event) o;

		if (origin != null ? !origin.equals(event.origin) : event.origin != null) return false;
		if (destination != null ? !destination.equals(event.destination) : event.destination != null) return false;
		return priority != null ? samePriority(priority, event.priority) : event.priority == null;
	}

	// create Surface equality
	public boolean samePriority(String priority1, String priority2) {
		if (priority1.equals(Event.SEA) || priority1.equals(Event.LAND)) {
			return (priority2.equals(Event.SEA) || priority2.equals(Event.LAND));
		} else return priority1.equals(priority2);
	}

	@Override
	public int hashCode() {
		int result = origin != null ? origin.hashCode() : 0;
		result = 31 * result + (destination != null ? destination.hashCode() : 0);
		result = 31 * result + (priority != null ? priority.hashCode() : 0);
		return result;
	}
}
