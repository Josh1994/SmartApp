package model;

import java.util.List;

import controller.Controller;
import model.Database;
import event.Event;

/**
 * Initiated by phil 5/25/2017.
 * For all the business logic
 * You can create another class from here and create a reference to it to do a specific job.
 */
public class BusinessModel {
    //create reference to controller
    private Controller controller;
    // create reference to Routefinder
    private EventManager eventManager;
    private Database db;
    XMLParser parser;

    public BusinessModel(Controller controller) {

        this.controller = controller;

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
        
        this.eventManager = new EventManager(this, db);
    }
    
    public boolean processEvent(Event event) {
    	eventManager.processSingleEvent(event, false);
    	return parser.convertToXML(db, "database.xml");
    }
    
    public EventManager getEventManager(){
    	return this.eventManager;
    }

	public Database getDatabase() {
		// TODO Auto-generated method stub
		return this.db;
	}
	
	public Route getRoute(Event ev){
		return eventManager.getRoute(ev);
	}

	
}
