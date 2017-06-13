package model;
import java.io.*;
import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.*;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import event.CustomerPriceUpdate;
import model.Database;
import event.Event;
import event.MailDelivery;
import event.TransportCostUpdate;
import event.TransportDiscontinued;

/*
 * Created by Jonathan Young StudentID: 300358326
 * For converting event database objects into XML files and vice-versa
 */
public class XMLParser {

	private Database db = new Database();
	private ArrayList<Event> events = new ArrayList<Event>();
	private String origin = "";
	private String destination = "";
	private String firm = "";
	private String city = "";
	private double weightCost = 0;
	private double volumeCost = 0;
	private double frequency = 0;
	private double duration = 0;
	private String priority = "";
	private String user = "";
	private ZonedDateTime dateTime = ZonedDateTime.now();
	private int maxWeight;
	private int maxVolume;
	private List<DayOfWeek> day = new ArrayList<DayOfWeek>();

	public XMLParser(String xmlfile) throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		org.w3c.dom.Document document = builder.parse(new FileInputStream(xmlfile));

		NodeList nodeList = document.getDocumentElement().getChildNodes();

		for(int i=0;i<nodeList.getLength();i++){
			Node node = nodeList.item(i);
			if(node instanceof Element){
				Event temp = parseEvent(node);
				if(temp!=null)events.add(temp);
				resetField();
			}
		}

		db.setEvent(events);
	}

	public XMLParser(){}

	private Event parseEvent(Node node){
		Event e = null;
		switch(node.getAttributes().getNamedItem("eventType").getNodeValue()){
		case "customerPriceUpdate":e=customerPriceUpdateParser(node);break;
		case "mailDelivery":e=mailDeliveryPraser(node);break;
		case "transportCostUpdate":e=transportCostUpdateParser(node);break;
		case "transportDiscontinued":e=transportDiscontinuedParser(node);break;
		}
		return e;
	}

	private TransportDiscontinued transportDiscontinuedParser(Node node) {
		NodeList cNodes = node.getChildNodes();
		for(int i=0;i<cNodes.getLength();i++){
			Node cNode = cNodes.item(i);
			System.out.println(cNode);
			parseVariable(cNode);
		}
		return new TransportDiscontinued(dateTime, user, origin, destination, firm, priority, city);
	}

	private TransportCostUpdate transportCostUpdateParser(Node node) {
		NodeList cNodes = node.getChildNodes();
		for(int i=0;i<cNodes.getLength();i++){
			Node cNode = cNodes.item(i);
			System.out.println(cNode);
			parseVariable(cNode);
		}
		return new TransportCostUpdate(dateTime, user, origin, destination, weightCost, 
				volumeCost, maxWeight, maxVolume, frequency, duration, day, firm, priority);
	}

	private MailDelivery mailDeliveryPraser(Node node) {
		NodeList cNodes = node.getChildNodes();
		for(int i=0;i<cNodes.getLength();i++){
			Node cNode = cNodes.item(i);
			parseVariable(cNode);
		}
		return new MailDelivery(dateTime, user, origin, destination, maxWeight, maxVolume, priority);
	}

	private CustomerPriceUpdate customerPriceUpdateParser(Node node) {
		NodeList cNodes = node.getChildNodes();
		for(int i=0;i<cNodes.getLength();i++){
			Node cNode = cNodes.item(i);
			parseVariable(cNode);
		}
		return new CustomerPriceUpdate(dateTime, user, origin, destination, weightCost, volumeCost, priority);
	}

	private void parseVariable(Node node){
		if(node instanceof Element){
			String s = node.getLastChild().getTextContent().trim();
			switch(node.getNodeName()){
			case "origin":origin=s;break;
			case "destination":destination=s;break;
			case "firm":firm=s;break;
			case "city":city=s;break;
			case "weightCost":weightCost=Double.parseDouble(s);break;
			case "volumeCost":volumeCost=Double.parseDouble(s);break;
			case "frequency":frequency=Double.parseDouble(s);break;
			case "duration":duration=Double.parseDouble(s);break;
			case "priority":priority = s;break;
			case "user":user = s;break;
			case "dateTime": dateTime = ZonedDateTime.parse(s);break;
			case "maxWeight": maxWeight = Integer.parseInt(s);break;
			case "maxVolume": maxVolume = Integer.parseInt(s);break;
			case "day":day.add(DayOfWeek.valueOf(s));
			}
		}	
	}

	private void resetField(){
		origin="";
		destination="";
		firm="";
		city="";
		weightCost=0;
		volumeCost=0;
		frequency=0;
		duration=0;
		priority = "";
		user = "";
		dateTime = null;
		maxWeight = 0;
		maxVolume = 0;
		day = new ArrayList<DayOfWeek>();
	}

	public ArrayList<Event> getEvents(){
		return this.events;
	}

	public Database getDatabase(){
		return db;
	}

	public boolean convertToXML(Database db, String fileName){
		try{
			JAXBContext contextObj = JAXBContext.newInstance(Database.class); 
			Marshaller marshallerObj = contextObj.createMarshaller();
			marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
			marshallerObj.marshal(db, new FileOutputStream(fileName));  
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally{
			return false;
		}
	}
}