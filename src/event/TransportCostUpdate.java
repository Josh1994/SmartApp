package event;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;  
import javax.xml.bind.annotation.XmlElement;  
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TransportCostUpdate extends Event{
	private double weightCost;
	private double volumeCost;
	private int maxWeight;
	private int maxVolume;
	private double frequency;
	private double duration;
	private List<DayOfWeek> days;
	private String firm;
	private static final String eventType = "transportCostUpdate";


	public TransportCostUpdate(ZonedDateTime dateTime, String user, String origin, String destination, double weightCost, double volumeCost, int maxWeight, int maxVolume, double frequency, double duration, List<DayOfWeek> day, String firm, String priority) {
		super(dateTime, user, origin, destination, priority);
		this.weightCost = weightCost;
		this.volumeCost = volumeCost;
		this.maxWeight = maxWeight;
		this.maxVolume = maxVolume;
		this.frequency = frequency;
		this.duration = duration;
		this.days = day;
		this.firm = firm;
	}

	//For XML purposes don't remove
	public TransportCostUpdate(){}

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
	public int getMaxWeight() {
		return maxWeight;
	}

	@XmlElement
	public int getMaxVolume() {
		return maxVolume;
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
	public List<DayOfWeek> getDay() {
		return days;
	}

	@XmlElement
	public String getFirm() {
		return firm;
	}
}
