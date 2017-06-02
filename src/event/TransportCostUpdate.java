package event;

import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;  
import javax.xml.bind.annotation.XmlElement;  
import javax.xml.bind.annotation.XmlRootElement; 

@XmlRootElement
public class TransportCostUpdate extends Event{

	private double weightPrice;
	private double volumePrice;
	private double frequency;
	private double duration;
	private List<String> days;
	private String firm;
	private static final String eventType = "transportCostUpdate";

	public TransportCostUpdate(String origin, String destination, String type, String firm, double weightPrice, double volumePrice, List<String> days, double frequency, double duration) {
		super(origin, destination, type);		
		this.weightPrice = weightPrice;
		this.volumePrice = volumePrice;
		this.frequency = frequency;
		this.duration = duration;
		this.days = days;
		this.firm = firm;
		// TODO Auto-generated constructor stub
	}

	//For XML purposes don't remove
	public TransportCostUpdate(){}

	@XmlAttribute
	public String getEventType(){
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

	@XmlElement
	public double getFrequency() {
		return frequency;
	}

	@XmlElement
	public double getDuration() {
		return duration;
	}

	@XmlElement
	public List<String> getDays() {
		return days;
	}

	@XmlElement
	public String getFirm() {
		return firm;
	}

	public String toString(){
		String d = "";
		for(String s:days)d+=s+" ";
		String s = eventType+" "+getDestination()+" "+getType()+" "+weightPrice+" "
				+volumePrice+" "+frequency+" "+d+" "+firm;
		return s;
	}
}
