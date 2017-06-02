package event;

import javax.xml.bind.annotation.XmlAttribute;  
import javax.xml.bind.annotation.XmlElement;  
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso; 

@XmlRootElement
@XmlSeeAlso({CustomerPriceUpdate.class,MailDelivery.class,TransportCostUpdate.class,TransportDiscontinued.class})
public class Event {

	//Type or priority
	public static final String SEA = "SEA";
	public static final String AIR = "AIR";
	public static final String LAND = "LAND";
	
	private String origin;
	private String destination;
	private String type;
	private static final String eventType = "super";
	
	//For XML purposes don't remove
	public Event(){}
	
	public Event(String origin, String destination, String type){
		this.origin = origin;
		this.destination = destination;
		this.type = type;
	}
	
	public String getEventType(){
		return eventType;
	}
	
	@XmlElement
	public String getOrigin(){
		return origin;
	}
	
	@XmlElement
	public String getDestination(){
		return destination;
	}
	
	@XmlElement
	public String getType(){
		return type;
	}
	
	public String toString(){
		String s = eventType+" "+origin+" "+destination+" "+type;
		return s;
	}
}
