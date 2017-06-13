package model;

import java.util.*;
import javax.xml.bind.annotation.XmlAttribute;  
import javax.xml.bind.annotation.XmlElement;  
import javax.xml.bind.annotation.XmlRootElement;

import event.Event; 

@XmlRootElement
public class Database {
	
	private List<Event> event = new ArrayList<Event>();
	
	public Database(){}
	
	public boolean addEvent(Event e){
		return event.add(e);
	}
	
	public void setEvent(List<Event> events){
		event = events;
	}
	
	@XmlElement
	public List<Event> getEvent(){
		return event;
	}
	
	public List<Event> getEventList(){
		return event;
	}
}
