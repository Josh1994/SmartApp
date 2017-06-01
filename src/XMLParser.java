import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.*;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import event.CustomerPriceUpdate;
import event.Event;
import event.MailDelivery;
import event.TransportCostUpdate;
import event.TransportDiscontinued;
public class XMLParser {

	private ArrayList<Event> events = new ArrayList<Event>();
	private String origin = "";
	private String destination = "";
	private String type = "";
	private String firm = "";
	private double weightPrice = 0;
	private double volumePrice = 0;
	private List<String> days = null;
	private double frequency = 0;
	private double duration = 0;
	private double weight = 0;
	private double volume = 0;

	public XMLParser(String xmlfile) throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance("", null);
		DocumentBuilder builder = factory.newDocumentBuilder();
		org.w3c.dom.Document document = builder.parse(ClassLoader.getSystemResourceAsStream(xmlfile));

		NodeList nodeList = document.getDocumentElement().getChildNodes();

		for(int i=0;i<nodeList.getLength();i++){
			Node node = nodeList.item(i);
			if(node instanceof Element){
				Event temp = parseEvent(node);
				if(temp!=null)events.add(temp);
			}
		}
	}

	private Event parseEvent(Node node){
		Event e = null;
		switch(node.getAttributes().getNamedItem("eventType").getNodeValue()){
		case "CustomerPriceUpdate":e=customerPriceUpdateParser(node);break;
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
			case "days":days=null;break;
			case "frequency":frequency=Double.parseDouble(s);break;
			case "duration":duration=Double.parseDouble(s);break;
			case "weight":weight=Double.parseDouble(s);break;
			case "volume":volume=Double.parseDouble(s);break;
			}
		}	
	}

	public ArrayList<Event> getEvents(){
		return this.events;
	}
}