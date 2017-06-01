package tests;

import static org.junit.Assert.*;

import java.io.FileOutputStream;
import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import org.junit.*;

import event.CustomerPriceUpdate;
import event.Database;
import event.MailDelivery;
import event.TransportCostUpdate;
import event.TransportDiscontinued;
import event.Event;

public class parserTests {
	@Before
	public void setup(){
		TransportDiscontinued  td = new TransportDiscontinued("a", "a", "a", "a");
		TransportCostUpdate tcu = new TransportCostUpdate("a", "a", "a", "a", 0, 0, null, 0, 0);
		MailDelivery md = new MailDelivery("a", "a", "a", 0 , 0);
		CustomerPriceUpdate cpu = new CustomerPriceUpdate("a", "a", "a", 0, 0);
		ArrayList<Event> events = new ArrayList<Event>();
		events.add(tcu);events.add(td);events.add(md);events.add(cpu);
		Database db = new Database();db.setEvent(events);
		try{
			JAXBContext contextObj = JAXBContext.newInstance(Database.class);  
			Marshaller marshallerObj = contextObj.createMarshaller();  
			marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);  
			marshallerObj.marshal(db, new FileOutputStream("test.xml"));  
		}catch(Exception e){
			e.printStackTrace();	
		}
	}

	@Test
	public void test(){

	}
}
