package tests;


import event.Event;
import model.EventManager;
import model.Route;
import model.RouteFinder;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phoal on 6/10/2017.
 */
public class BusinessFiguresTest {



    public static EventManager eventManager() {
        List<Event> events = new ArrayList<>();
        for (Event e : RouteFinderTest.createTCUs()) {
            events.add(e) ;
        }
        EventManager em = new EventManager(null,
                (events));
        return em;
    }
    @Test
    public void testBusinessFigures_1(){
        EventManager em = eventManager();
        Assert.assertEquals(em.getTotalEvents(), 9);
        em.getNewRoutes();

        RouteFinderTest.testDomesticRoutes();

        RouteFinderTest.testInternationalAirRoutes();

        RouteFinderTest.testInternationalSurfaceRoutes();

        Route dublinSurface = em.getRouteFinder().getRoute("AKLD", "DUBLIN", Event.SEA);

        Assert.assertEquals(dublinSurface.getAvgDeliveryTime(), 30, 0.1);
    }

}
