package event;

import java.time.ZonedDateTime;

public class TransportDiscontinued extends Event {
	String firm;
	String type;

	public TransportDiscontinued(ZonedDateTime dateTime, String user, String origin, String destination, String firm, String type) {
		super(dateTime, user, origin, destination);
		this.firm = firm;
		this.type = type;
	}

	public String getFirm() {
		return firm;
	}

	public String getType() {
		return type;
	}
}
