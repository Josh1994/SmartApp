package event;

import java.time.ZonedDateTime;

public class TransportDiscontinued extends Event {
	String firm;
//	String type;

	public TransportDiscontinued(ZonedDateTime dateTime, String user, String origin, String destination, String firm, String priority) {
		super(dateTime, user, origin, destination, priority);
		this.firm = firm;
//		this.type 
	}

	public String getFirm() {
		return firm;
	}

	public String getType() {
		return priority;
	}
}
