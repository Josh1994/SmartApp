package event;

import model.User;

import java.time.ZonedDateTime;

public class TransportDiscontinued extends Event {
	String firm;
	public TransportDiscontinued(ZonedDateTime dateTime, String user, String origin, String destination, String type, String firm) {
		super(dateTime, user, origin, destination, type);
		this.firm = firm;
		// TODO Auto-generated constructor stub
	}

}
