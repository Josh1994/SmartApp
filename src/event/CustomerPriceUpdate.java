package event;

import java.time.ZonedDateTime;

public class CustomerPriceUpdate extends Event {
	private double weightCost;
	private double volumeCost;
	

	public CustomerPriceUpdate(ZonedDateTime dateTime, String user, String origin, String destination, double weightCost, double volumeCost, String priority) {
		super(dateTime, user, origin, destination, priority);
		this.weightCost = weightCost;
		this.volumeCost = volumeCost;
	
	}

    public double getWeightCost() {
        return weightCost;
    }

    public double getVolumeCost() {
        return volumeCost;
    }

    public String getPriority() {
        return priority;
    }
}
