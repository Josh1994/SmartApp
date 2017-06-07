package model;

import controller.Controller;
import event.Event;

/**
 * Created by phoal on 5/25/2017.
 * For all the business logic
 * You can create another class from here and create a reference to it to do a specific job.
 */
public class BusinessModel {
    //create reference to controller
    private Controller controller;
    // create reference to Routefinder
    private RouteFinder routeFinder;
    private Database db;
    XMLParser parser;

    public BusinessModel(Controller controller) {

        this.controller = controller;

        this.routeFinder = new RouteFinder(this);
        
        try{
        	parser = new XMLParser("database.xml");
        	db = parser.getDatabase();
        }catch(Exception e){
        	e.printStackTrace();
        	try {
        		db = new Database();
				parser = new XMLParser();
				parser.convertToXML(db, "database.xml");
			} catch (Exception e1) {
				e1.printStackTrace();
			}  
        }
    }

    public void processEvent(Event event) {
    	db.addEvent(event);
    }
}
