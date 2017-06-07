package event;

import java.time.ZonedDateTime;

public class TransportDiscontinued extends Event {
	String firm;
	String city;
	
	public TransportDiscontinued(ZonedDateTime dateTime, String user, String origin, String destination, String firm, String priority, String city) {
		super(dateTime, user, origin, destination, priority);
		this.firm = firm;
		this.city=city;
	} 

	public String getFirm() {
		return firm;
	}
	
	public String getCity() {
		return city;
	}

}
