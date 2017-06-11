package tests;


import event.*;
import model.EventManager;
import model.Route;
import model.RouteFinder;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * Created by phoal on 6/10/2017.
 */
public class BusinessFiguresTest {

    public static List<Event> event() {
        List<Event> events = new ArrayList<>();

        events.add(new MailDelivery(null,
                null, "AKLD", "DUBLIN", 10, 10, Event.SEA));

        events.add(new CustomerPriceUpdate(null, null, "LA",
                "DUBLIN", 10, 10, Event.SEA));

        events.add(new TransportDiscontinued(null, null, "LA",
                "DUBLIN", "A", Event.SEA));

        events.add( new TransportCostUpdate(null, null, "LA",
                "DUBLIN", 20, 20, 0 , 0, 10, 10,
                null, "A", Event.AIR));

        events.add(new CustomerPriceUpdate(null, null, "LA",
                "DUBLIN", 10, 10, Event.AIR));

        // Domestic 6 - 10

        events.add(new MailDelivery(null,
                null, "AKLD", "DNDN", 10, 10, Event.DOMESTIC));

        events.add(new CustomerPriceUpdate(null, null, "CHCH",
                "DNDN", 20, 20, Event.DOMESTIC));

        events.add(new TransportDiscontinued(null, null, "CHCH",
                "DNDN", "A", Event.DOMESTIC));

        events.add( new TransportCostUpdate(null, null, "AKLD",
                "CHCH", 20, 20, 0 , 0, 10, 10,
                null, "A", Event.DOMESTIC));

        events.add( new TransportCostUpdate(null, null, "AKLD",
                "NLSN", 10, 10, 0 , 0, 10, 10,
                null, "A", Event.DOMESTIC));

        events.add(new CustomerPriceUpdate(null, null, "CHCH",
                "DNDN", 10, 10, Event.DOMESTIC));

        return events;
    }

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

        RouteFinder finder = em.getRouteFinder();

        testDomesticRoutes(finder);

        testInternationalAirRoutes(finder);

        testInternationalSurfaceRoutes(finder);

        Route dublinSurface = em.getRouteFinder().getRoute("AKLD", "DUBLIN", Event.SEA);

        //System.out.format("3 : %s", dublinSurface.toString());

        // Test business figures
        Assert.assertEquals(dublinSurface.getAvgDeliveryTime(), 30, 0.1);
    }

    @Test
    public void testBusinessFigures_2() {
        EventManager em = eventManager();
        Assert.assertEquals(em.getTotalEvents(), 9);

        em.processSingleEvent(event().get(0), false);
        // Test business figures
        Route dublinSurface = em.getRouteFinder().getRoute("AKLD", "DUBLIN", Event.SEA);
        Assert.assertEquals(dublinSurface.getAvgDeliveryTime(), 30, 0.1);
        Assert.assertEquals(dublinSurface.getAmountOfmail(), 1);
        Assert.assertEquals(dublinSurface.getCustomerCost((MailDelivery) event().get(0),em.getCurrentCpus()),
                300, 0.1);
        Assert.assertEquals(dublinSurface.getExpenditure(), 300, 0.1);

        em.processSingleEvent(event().get(0), false);

        Assert.assertEquals(dublinSurface.getExpenditure(), 600, 0.1);

        em.processEvent(event().get(1));

        Assert.assertEquals(dublinSurface.getExpenditure(), 600, 0.1);

        em.processEvent(event().get(0));

        Assert.assertEquals(dublinSurface.getExpenditure(), 900, 0.1);

        Assert.assertEquals(dublinSurface.getRevenue(), 1000, 0.1);


    }

    @Test
    public void testBusinessFigures_Domestic() {
        EventManager em = eventManager();
        Assert.assertEquals(em.getTotalEvents(), 9);

        em.processSingleEvent(event().get(5), false);
        // Test business figures
        Route ak_dn = em.getRouteFinder().getRoute("AKLD", "DNDN", Event.DOMESTIC);
        Assert.assertEquals(ak_dn.getAvgDeliveryTime(), 30, 0.1);
        Assert.assertEquals(ak_dn.getAmountOfmail(), 1);
        Assert.assertEquals(ak_dn.getCustomerCost((MailDelivery) event().get(0),em.getCurrentCpus()),
                400, 0.1);
        Assert.assertEquals(ak_dn.getExpenditure(), 400, 0.1);

        em.processSingleEvent(event().get(5), false);

        Assert.assertEquals(ak_dn.getExpenditure(), 800, 0.1);

        em.processEvent(event().get(6));

        Assert.assertEquals(ak_dn.getExpenditure(), 800, 0.1);

        em.processEvent(event().get(5));

        Assert.assertEquals(ak_dn.getExpenditure(), 1200, 0.1);

        Assert.assertEquals(ak_dn.getRevenue(), 1400, 0.1);

        em.processEvent(event().get(8));

        Route ak_dn1 = em.getRouteFinder().getRoute("AKLD", "DNDN", Event.DOMESTIC);

        Assert.assertTrue(ak_dn.equals(ak_dn1));

        System.out.format("true : %b  %n", ak_dn.equals(ak_dn1));

        Assert.assertEquals(ak_dn1.getExpenditure(), 1200, 0.1);

        Assert.assertEquals(ak_dn1.getRevenue(), 1400, 0.1);

        em.processEvent(event().get(5));

        System.out.format("3 : %s", ak_dn.toString());

        Assert.assertEquals(ak_dn1.getExpenditure(), 1800, 0.1);

        Assert.assertEquals(ak_dn1.getRevenue(), 2200, 0.1);

        em.processEvent(event().get(9));

        Route ak_nn = em.getRouteFinder().getRoute("AKLD", "NLSN", Event.DOMESTIC);

        Assert.assertEquals(ak_nn.getRevenue(), 0, 0.1);

        em.processEvent(event().get(7));

        Route ak_dn3 = em.getRouteFinder().getRoute("AKLD", "DNDN", Event.DOMESTIC);

        Assert.assertEquals(ak_dn3, null);

    }

    @Test
    public void testBusinessFigures_4() {
        EventManager em = eventManager();
        Assert.assertEquals(em.getTotalEvents(), 9);

        em.processSingleEvent(event().get(0), false);
        // Test business figures
        Route dublinSurface = em.getRouteFinder().getRoute("AKLD", "DUBLIN", Event.SEA);
        Assert.assertEquals(dublinSurface.getAvgDeliveryTime(), 30, 0.1);
        Assert.assertEquals(dublinSurface.getAmountOfmail(), 1);
        Assert.assertEquals(dublinSurface.getCustomerCost((MailDelivery) event().get(0),em.getCurrentCpus()),
                300, 0.1);
        Assert.assertEquals(dublinSurface.getExpenditure(), 300, 0.1);

        em.processSingleEvent(event().get(0), false);

        Assert.assertEquals(dublinSurface.getExpenditure(), 600, 0.1);

        em.processEvent(event().get(1));

        Assert.assertEquals(dublinSurface.getExpenditure(), 600, 0.1);

        em.processEvent(event().get(0));

        Assert.assertEquals(dublinSurface.getExpenditure(), 900, 0.1);

        Assert.assertEquals(dublinSurface.getRevenue(), 1000, 0.1);

        em.processEvent(event().get(2));

        Assert.assertEquals(dublinSurface.getExpenditure(), 900, 0.1);

        Assert.assertEquals(dublinSurface.getRevenue(), 1000, 0.1);

        Route dublinSurface1 = em.getRouteFinder().getRoute("AKLD", "DUBLIN", Event.SEA);

        Assert.assertEquals(dublinSurface1.getExpenditure(), 900, 0.1);

        Assert.assertEquals(dublinSurface1.getRevenue(), 1000, 0.1);

        em.processEvent(event().get(0));

        //System.out.format("4 : %s", dublinSurface.toString());

        Assert.assertEquals(dublinSurface1.getExpenditure(), 1300, 0.1);

        Assert.assertEquals(dublinSurface1.getRevenue(), 1400, 0.1);

        em.processEvent(event().get(3));

        em.processEvent(event().get(0));

        Route ds2 = em.getRouteFinder().getRoute("AKLD", "DUBLIN", Event.SEA);

        Assert.assertEquals(ds2.getExpenditure(), 1900, 0.1);

        Assert.assertEquals(ds2.getRevenue(), 2000, 0.1);


    }


    //@Test
    public void testDomesticRoutes(RouteFinder finder) {

        Map<String, Set<Route>> domestics = finder.getDomesticRoutes();
        Map<String, Set<Route>> airs = finder.getAirRoutes();
        Map<String, Set<Route>> surfaces = finder.getSurfaceRoutes();

        Set<String> nzCities = domestics.keySet();
        // Critical set sizes
        Assert.assertEquals(nzCities.size(), 3);
        Set<Route> routes = domestics.get("AKLD");
        /**
        for (Route route : routes) {
            System.out.format("1 : %s  %s", route.getOrigin(), route.getDestination());
            if (route.getOrigin().equals("AKLD") && route.getDestination().equals("DNDN")) System.out.format("2 : %s", route.toString());
        }
         */

        Assert.assertEquals(domestics.get("AKLD").size(), 3);

        HashSet<String> destinations = new HashSet<>();
        List<String> dests = new ArrayList<>(Arrays.asList("WGTN", "CHCH", "DNDN"));
        // Check the correct routes from AKLD are assigned to it
        for (Route route : domestics.get("AKLD")) {
            Assert.assertEquals(route.getOrigin(), "AKLD");
            Assert.assertTrue(dests.contains(route.getDestination()));
            for (int i = 0 ; i < dests.size() ; i++) {
                if (dests.get(i).equals(route.getDestination())) dests.remove(i) ;
            }
        }
        // Check dests is now empty
        Assert.assertTrue(dests.size() == 0);

        Set<Route> fromAuckland = domestics.get("AKLD") ;


        for (Route route : fromAuckland) {
            // Check route to Chch is one stage and cheapest
            if (route.getDestination().equals("CHCH")) {
                Assert.assertEquals(route.getWeightCost(), 10, 0.01);
                Assert.assertEquals(route.getVolumeCost(), 10, 0.01);
            }
            // Check route to Dndn is 2 stages only and cheapest
            if (route.getDestination().equals("DNDN")) {
                Assert.assertEquals(route.getWeightCost(), 20, 0.01);
                Assert.assertEquals(route.getVolumeCost(), 20, 0.01);
            }
        }
        // Test findRoute Domestic
        Route route1 = finder.getRoute("AKLD", "DNDN", Event.DOMESTIC );
        Assert.assertEquals(route1.getOrigin(), "AKLD");
        Assert.assertEquals(route1.getDestination(), "DNDN");
        Assert.assertEquals(route1.getVolumeCost(), 20.0, 0.01 );
        Assert.assertEquals(route1.getWeightCost(), 20.0, 0.01 );
    }

    //@Test
    public  void testInternationalAirRoutes(RouteFinder finder) {

        Map<String, Set<Route>> domestics = finder.getDomesticRoutes();
        Map<String, Set<Route>> airs = finder.getAirRoutes();
        Map<String, Set<Route>> surfaces = finder.getSurfaceRoutes();

        //check all 4 routes exist
        Assert.assertEquals(airs.get("AKLD").size(), 4);

        HashSet<String> destinations = new HashSet<>();
        List<String> dests = Arrays.asList("SIDNEY", "DUBLIN", "LONDON", "LA");
        // Akld is the only origin
        Assert.assertEquals(airs.keySet().size(), 1);
        for (Route route : airs.get("AKLD")) {
            Assert.assertEquals(route.getOrigin(), "AKLD");
            Assert.assertTrue(dests.contains(route.getDestination()));
        }
        Set<Route> fromAuckland = airs.get("AKLD") ;
        // Check London and dublin routes exist from AKLD
        for (Route route : fromAuckland) {
            if (route.getDestination().equals("DUBLIN")) {
                Assert.assertEquals(route.getWeightCost(), 20, 0.01);
                Assert.assertEquals(route.getVolumeCost(), 20, 0.01);
            }
            if (route.getDestination().equals("LONDON")) {
                Assert.assertEquals(route.getWeightCost(), 20, 0.01);
                Assert.assertEquals(route.getVolumeCost(), 20, 0.01);
            }
        }
        // Test findRoute Air
        Route route1 = finder.getRoute("WGTN", "DUBLIN", Event.AIR );
        Assert.assertEquals(route1.getOrigin(), "WGTN");
        Assert.assertEquals(route1.getOrigin(), "WGTN");
        Assert.assertEquals(route1.getDestination(), "DUBLIN");
        Assert.assertEquals(route1.getVolumeCost(), 30.0, 0.01 );
        Assert.assertEquals(route1.getWeightCost(), 30.0, 0.01 );

    }

    //@Test
    public  void testInternationalSurfaceRoutes(RouteFinder finder) {

        Map<String, Set<Route>> surfaces = finder.getSurfaceRoutes();
        // Auckl only origin
        Assert.assertEquals(surfaces.keySet().size(), 1);
        // All 4 routes exist
        Assert.assertEquals(surfaces.get("AKLD").size(), 4);

        HashSet<String> destinations = new HashSet<>();
        List<String> dests = Arrays.asList("SIDNEY", "DUBLIN", "LONDON", "LA");
        // Auckl only origin
        for (Route route : surfaces.get("AKLD")) {
            Assert.assertEquals(route.getOrigin(), "AKLD");
            Assert.assertTrue(dests.contains(route.getDestination()));
        }
        Set<Route> fromAuckland = surfaces.get("AKLD") ;
        Route dublin;
        Route london;
        // Check Dublin is cheapest route because of sea leg (15)
        // Check London route exists and is same as air cost
        for (Route route : fromAuckland) {
            if (route.getDestination().equals("DUBLIN")) {
                Assert.assertEquals(route.getWeightCost(), 15, 0.01);
                Assert.assertEquals(route.getVolumeCost(), 15, 0.01);
            }
            if (route.getDestination().equals("LONDON")) {
                Assert.assertEquals(route.getWeightCost(), 20, 0.01);
                Assert.assertEquals(route.getVolumeCost(), 20, 0.01);
            }
        }
        // Test findRoute Surface
        Route route = finder.getRoute("WGTN", "DUBLIN", Event.SEA );
        Assert.assertEquals(route.getOrigin(), "WGTN");
        Assert.assertEquals(route.getDestination(), "DUBLIN");
        Assert.assertEquals(route.getVolumeCost(), 25.0, 0.01 );
        Assert.assertEquals(route.getWeightCost(), 25.0, 0.01 );

        // Check that the stages are right
        List<TransportCostUpdate> tcus = route.getStages();

        Assert.assertEquals(tcus.get(0).getDestination(), "AKLD");
        Assert.assertEquals(tcus.get(0).getWeightCost(),10, 0.1);

        Assert.assertEquals(tcus.get(1).getDestination(), "LA");
        Assert.assertEquals(tcus.get(1).getWeightCost(),10, 0.1);

        Assert.assertEquals(tcus.get(2).getDestination(), "DUBLIN");
        Assert.assertEquals(tcus.get(2).getWeightCost(),5, 0.1);
    }


}
