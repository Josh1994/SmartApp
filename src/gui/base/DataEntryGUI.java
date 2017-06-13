package gui.base;

import controller.Controller;
import model.Route;

/**
 * Created by clarkphil1 on 25/05/17.
 */
public interface DataEntryGUI {
    public void showError(String errmsg);
    public void displayRoute(Route route);
    public Controller getController();
}
