package event;

import java.time.ZonedDateTime;
import javax.xml.bind.annotation.XmlAttribute;  
import javax.xml.bind.annotation.XmlElement;  
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TransportDiscontinued extends Event {
	private String firm;
//	String type;
	private static final String eventType = "transportDiscontinued";

	public TransportDiscontinued(ZonedDateTime dateTime, String user, String origin, String destination, String firm, String priority) {
		super(dateTime, user, origin, destination, priority);
		this.firm = firm;
//		this.type 
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
}
