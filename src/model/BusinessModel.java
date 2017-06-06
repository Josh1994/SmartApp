package model;

import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import controller.Controller;
import event.Database;
import event.Event;

/**
 * Created by phoal on 5/25/2017.
 * For all the business logic
 * You can create another class from here and create a reference to it to do a specific job.
 */
public class BusinessModel {
    //create reference to controller
    private Controller controller;
    private Database db;

    public BusinessModel(Controller controller) {
        this.controller = controller;
        this.db = new Database();
        XMLParser parser;
        try {
			parser = new XMLParser("database.xml");
			db.setEvent(parser.getEvents());
		} catch (Exception e) {
			e.printStackTrace();
			try {
				parser = new XMLParser();
				parser.convertToXML(db);
			} catch (Exception e1) {
				e1.printStackTrace();
			}  
			
		}
    }

    public void processEvent(Event event) {

    }
}
