package event;

public class MailDelivery extends Event {

	double weight;
	double volume;
	
	public MailDelivery(String origin, String destination, String type, double weight, double volume) {
		super(origin, destination, type);
		this.weight = weight;
		this.volume = volume;
		// TODO Auto-generated constructor stub
	}

}
