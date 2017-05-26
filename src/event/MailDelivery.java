package event;

import java.time.ZonedDateTime;

public class MailDelivery extends Event {
	double weight;
	double volume;
	String priority;

	public MailDelivery(ZonedDateTime dateTime, String user, String origin, String destination, double weight, double volume, String priority) {
		super(dateTime, user, origin, destination);
		this.weight = weight;
		this.volume = volume;
		this.priority = priority;
	}

	public double getWeight() {
		return weight;
	}

	public double getVolume() {
		return volume;
	}

	public String getPriority() {
		return priority;
	}
}
