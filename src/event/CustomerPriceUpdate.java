package event;

import java.time.ZonedDateTime;
import javax.xml.bind.annotation.XmlAttribute;  
import javax.xml.bind.annotation.XmlElement;  
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CustomerPriceUpdate extends Event {
	private double weightCost;
	private double volumeCost;
	private static final String eventType = "customerPriceUpdate";
	

	public CustomerPriceUpdate(ZonedDateTime dateTime, String user, String origin, String destination, double weightCost, double volumeCost, String priority) {
		super(dateTime, user, origin, destination, priority);
		this.weightCost = weightCost;
		this.volumeCost = volumeCost;
	
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("TransportDiscontinued Event");
		sb.append("\nOrigin: " + this.origin);
		sb.append("\nDestination: " + this.destination);
		sb.append("\nPriority: " + this.priority);
		sb.append("\nUser: "+ this.user);
		
		return sb.toString();
	}
	
	//For XML purposes don't remove
	public CustomerPriceUpdate(){}
	
	@XmlAttribute
	public String getEventType(){
		return eventType;
	}
	
	@XmlElement
    public double getWeightCost() {
        return weightCost;
    }
	
	@XmlElement
    public double getVolumeCost() {
        return volumeCost;
    }
	
	@XmlElement
    public String getPriority() {
        return priority;
    }
}
