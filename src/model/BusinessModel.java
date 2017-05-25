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

    public BusinessModel(Controller controller) {
        this.controller = controller;
    }

    public void processEvent(Event event) {

    }
}
