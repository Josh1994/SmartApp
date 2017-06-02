package event;

import java.time.ZonedDateTime;

public class MailDelivery extends Event {

	double weight;
	double volume;
	
	public MailDelivery(ZonedDateTime dateTime, String user, String origin, String destination, String type, double weight, double volume) {
		super( dateTime,  user, origin, destination, type);
		this.weight = weight;
		this.volume = volume;
		// TODO Auto-generated constructor stub
	}

}
