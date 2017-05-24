package event;

public class TransportDiscontinued extends Event {
	String firm;
	public TransportDiscontinued(String origin, String destination, String type, String firm) {
		super(origin, destination, type);
		this.firm = firm;
		// TODO Auto-generated constructor stub
	}

}
