package tests;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.junit.*;

import event.CustomerPriceUpdate;
import event.MailDelivery;
import event.TransportCostUpdate;
import event.TransportDiscontinued;
import model.Database;
import model.XMLParser;
import event.Event;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;

/*
 * Created by Jonathan Young StudentID: 300358326 
 * Tests for the XMLParser class
 */
public class parserTests {
	String a = "a";
	ZonedDateTime date = null;
	List<String> s = new ArrayList<String>();
	List<DayOfWeek> days = new ArrayList<DayOfWeek>();
	TransportDiscontinued  td = new TransportDiscontinued(date, a, a, a, a, a);
	TransportCostUpdate tcu = new TransportCostUpdate(date, a, a, a, 0, 0, 0, 0, 0, 0, days, a, a);
	MailDelivery md = new MailDelivery(date, a, a, a, 0, 0, a);
	CustomerPriceUpdate cpu = new CustomerPriceUpdate(date, a, a, a, 0, 0, a);
	ArrayList<Event> events = new ArrayList<Event>();
	Database db = new Database();
	Database testdb = new Database();
	ArrayList<Event> testEvents = null;

	@Before
	public void setup(){
		s.add("S");s.add("s");
		days.add(DayOfWeek.FRIDAY);
		events.add(td);events.add(tcu);events.add(md);events.add(cpu);
		db.setEvent(events);
		try{
			JAXBContext contextObj = JAXBContext.newInstance(Database.class);  
			Marshaller marshallerObj = contextObj.createMarshaller();  
			marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
			marshallerObj.marshal(db, new FileOutputStream("test.xml"));  
			XMLParser parser = new XMLParser("test.xml");
			testEvents = parser.getEvents();
			testdb.setEvent(testEvents);
			parser.convertToXML(testdb, "reparseTest.xml");
		}catch(Exception e){
			e.printStackTrace();	
		}
	}

	@Test
	public void testGeneral(){
		assertTrue(testEvents!=null);
		System.out.println(testEvents.size());
		assertTrue(testEvents.size()==4);
	}

	@Test
	public void testTransportDiscontinued(){
		assertTrue(testEvents!=null);
		assertTrue(td.toString().equals(testEvents.get(0).toString()));

	}

	@Test
	public void testTransportCostUpdate(){
		assertTrue(testEvents!=null);
		assertTrue(tcu.toString().equals(testEvents.get(1).toString()));
	}

	@Test
	public void testMailDelivery(){
		assertTrue(testEvents!=null);
		assertTrue(md.toString().equals(testEvents.get(2).toString()));
	}

	@Test
	public void testCustomerPriceUpdate(){
		assertTrue(testEvents!=null);
		assertTrue(cpu.toString().equals(testEvents.get(3).toString()));
	}
}
