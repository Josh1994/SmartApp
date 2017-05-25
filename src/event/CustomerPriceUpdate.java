package event;

public class CustomerPriceUpdate extends Event {
	double weightPrice;
	double volumePrice;
	public CustomerPriceUpdate(String origin, String destination, String type, double weightPrice, double volumePrice) {
		super(origin, destination, type);
		// TODO Auto-generated constructor stub
		this.weightPrice = weightPrice;
		this.volumePrice = volumePrice;
	}
}
