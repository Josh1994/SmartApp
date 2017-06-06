package event;

import java.time.ZonedDateTime;

public class TransportDiscontinued extends Event {
	String firm;

	public TransportDiscontinued(ZonedDateTime dateTime, String user, String origin, String destination, String firm, String priority) {
		super(dateTime, user, origin, destination, priority);
		this.firm = firm;
	} 

	public String getFirm() {
		return firm;
	}

}
