package event;

import java.time.ZonedDateTime;

public class CustomerPriceUpdate extends Event {
	double weightPrice;
	double volumePrice;
	public CustomerPriceUpdate(ZonedDateTime dateTime, String user, String origin, String destination, String type,
							   double weightPrice, double volumePrice) {
		super(dateTime,  user, origin, destination, type);
		// TODO Auto-generated constructor stub
		this.weightPrice = weightPrice;
		this.volumePrice = volumePrice;
	}
}
