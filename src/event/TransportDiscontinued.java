package event;

import java.time.ZonedDateTime;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TransportDiscontinued extends Event {
	private String firm;
	String city;
//	String type;
	private static final String eventType = "transportDiscontinued";

	public TransportDiscontinued(ZonedDateTime dateTime, String user, String origin, String destination, String firm, String priority, String city) {
		super(dateTime, user, origin, destination, priority);
		this.firm = firm;
		this.city=city;
//		this.type
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("TransportDiscontinued Event");
		sb.append("\nFirm: " + this.firm);
		if(this.city != null){
		sb.append("\nCity: "+ this.city);
		}
		sb.append("\nOrigin: " + this.origin);
		sb.append("\nDestination: " + this.destination);
		sb.append("\nPriority: " + this.priority);
		sb.append("\nUser: "+ this.user);
		return sb.toString();
	}

	//For XML puposes don't remove
	public TransportDiscontinued(){}

	@XmlAttribute
	public String getEventType(){
		return eventType;
	}

	@XmlElement
	public String getFirm() {
		return firm;
	}

	@XmlElement
	public String getType() {
		return priority;
	}
	@XmlElement
	public String getCity() {
		return city;
	}
}