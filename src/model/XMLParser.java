package model;
import java.io.*;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.*;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import event.CustomerPriceUpdate;
import event.Database;
import event.Event;
import event.MailDelivery;
import event.TransportCostUpdate;
import event.TransportDiscontinued;

/*
 * Created by Jonathan Young StudentID: 300358326
 * For converting event database objects into XML files and vice-versa
 */
public class XMLParser {

	private ArrayList<Event> events = new ArrayList<Event>();
	private String origin = "";
	private String destination = "";
	private String type = "";
	private String firm = "";
	private double weightPrice = 0;
	private double volumePrice = 0;
	private List<String> days = new ArrayList<String>();
	private double frequency = 0;
	private double duration = 0;
	private double weight = 0;
	private double volume = 0;

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
			parseVariable(cNode);
		}
		return new TransportDiscontinued(origin, destination, type, firm);
	}

	private TransportCostUpdate transportCostUpdateParser(Node node) {
		NodeList cNodes = node.getChildNodes();
		for(int i=0;i<cNodes.getLength();i++){
			Node cNode = cNodes.item(i);
			parseVariable(cNode);
		}
		return new TransportCostUpdate(origin, destination, type, firm, weightPrice, volumePrice, days, frequency, duration);
	}

	private MailDelivery mailDeliveryPraser(Node node) {
		NodeList cNodes = node.getChildNodes();
		for(int i=0;i<cNodes.getLength();i++){
			Node cNode = cNodes.item(i);
			parseVariable(cNode);
		}
		return new MailDelivery(origin, destination, type, weight, volume);
	}

	private CustomerPriceUpdate customerPriceUpdateParser(Node node) {
		NodeList cNodes = node.getChildNodes();
		for(int i=0;i<cNodes.getLength();i++){
			Node cNode = cNodes.item(i);
			parseVariable(cNode);
		}
		return new CustomerPriceUpdate(origin, destination, type, weightPrice, volumePrice);
	}
	
	private void parseVariable(Node node){
		if(node instanceof Element){
			String s = node.getLastChild().getTextContent().trim();
			switch(node.getNodeName()){
			case "origin":origin=s;break;
			case "destination":destination=s;break;
			case "type":type=s;break;
			case "firm":firm=s;break;
			case "weightPrice":weightPrice=Double.parseDouble(s);break;
			case "volumePrice":volumePrice=Double.parseDouble(s);break;
			case "days":days.add(s);break;
			case "frequency":frequency=Double.parseDouble(s);break;
			case "duration":duration=Double.parseDouble(s);break;
			case "weight":weight=Double.parseDouble(s);break;
			case "volume":volume=Double.parseDouble(s);break;
			}
		}	
	}

	private void resetField(){
		origin="";
		destination="";
		type="";
		firm="";
		weightPrice=0;
		volumePrice=0;
		days=new ArrayList<String>();
		frequency=0;
		duration=0;
		weight=0;
		volume=0;
	}
	
	public ArrayList<Event> getEvents(){
		return this.events;
	}
	
	public void convertToXML(Database db){
		try{
		JAXBContext contextObj = JAXBContext.newInstance(Database.class); 
		Marshaller marshallerObj = contextObj.createMarshaller();
		marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
		marshallerObj.marshal(db, new FileOutputStream("database.xml"));  
		XMLParser parser = new XMLParser("database.xml");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}