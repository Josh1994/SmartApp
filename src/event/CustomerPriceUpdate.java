package event;

import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;  
import javax.xml.bind.annotation.XmlElement;  
import javax.xml.bind.annotation.XmlRootElement; 

@XmlRootElement
public class CustomerPriceUpdate extends Event {
	
	private static final String eventType = "customerPriceUpdate";
	private double weightPrice;
	private double volumePrice;
	
	public CustomerPriceUpdate(String origin, String destination, String type, double weightPrice, double volumePrice) {
		super(origin, destination, type);
		// TODO Auto-generated constructor stub
		this.weightPrice = weightPrice;
		this.volumePrice = volumePrice;
	}
	
	@XmlAttribute
	public String getEventType() {
		return eventType;
	}

	@XmlElement
	public double getWeightPrice() {
		return weightPrice;
	}

	@XmlElement
	public double getVolumePrice() {
		return volumePrice;
	}
}
