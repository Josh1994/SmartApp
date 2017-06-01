package event;

import javax.xml.bind.annotation.XmlAttribute;  
import javax.xml.bind.annotation.XmlElement;  
import javax.xml.bind.annotation.XmlRootElement; 

@XmlRootElement
public class TransportDiscontinued extends Event {
	
	private static final String eventType = "transportDiscontinued";
	String firm;
	
	//For XML purposes don't remove
	public TransportDiscontinued(){}
	
	public TransportDiscontinued(String origin, String destination, String type, String firm) {
		super(origin, destination, type);
		this.firm = firm;
		// TODO Auto-generated constructor stub
	}
	
	@XmlAttribute
	public String getEventType(){
		return eventType;
	}
	
	@XmlElement
	public String getFirm(){
		return firm;
	}
}
