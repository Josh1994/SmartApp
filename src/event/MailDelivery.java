package event;

import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;  
import javax.xml.bind.annotation.XmlElement;  
import javax.xml.bind.annotation.XmlRootElement; 

@XmlRootElement
public class MailDelivery extends Event {

	private static final String eventType = "mailDelivery";
	private double weight;
	private double volume;
	
	public MailDelivery(String origin, String destination, String type, double weight, double volume) {
		super(origin, destination, type);
		this.weight = weight;
		this.volume = volume;
		// TODO Auto-generated constructor stub
	}
	
	//For XML purposes don't remove
	public MailDelivery(){}
	
	@XmlAttribute
	public String getEventType() {
		return eventType;
	}

	@XmlElement
	public double getWeight() {
		return weight;
	}

	@XmlElement
	public double getVolume() {
		return volume;
	}

	public String toString(){
		String s=eventType+" "+getDestination()+" "+getType()+" "+weight+" "+volume;
		return s;
	}
}
