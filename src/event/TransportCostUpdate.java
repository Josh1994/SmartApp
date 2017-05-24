package event;

import java.util.List;

public class TransportCostUpdate extends Event{
	double weightPrice;
	double volumePrice;
	double frequency;
	double duration;
	List<String> days;
	String firm;
	
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

}
