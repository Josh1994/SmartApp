package event;

import java.util.*;
import javax.xml.bind.annotation.XmlAttribute;  
import javax.xml.bind.annotation.XmlElement;  
import javax.xml.bind.annotation.XmlRootElement; 

@XmlRootElement
public class Database {
	
	private List<Event> events = new ArrayList<Event>();
	
	public Database(){}
	
	public boolean addEvent(Event e){
		return events.add(e);
	}
	
	public void setEvent(ArrayList<Event> e){
		events = e;
	}
	
	@XmlElement
	public List<Event> getEvents(){
		return events;
	}
}
