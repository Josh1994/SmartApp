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

	//For XML purposes don't remove
	public Event(){}

	public String getEventType(){
		return eventType;
	}

	@XmlElement
	public ZonedDateTime getDateTime() {
		return dateTime;
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
}
