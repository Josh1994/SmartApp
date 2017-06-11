package tests;

import event.Event;
import event.TransportCostUpdate;
import model.Route;
import model.RouteFinder;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by phoal on 6/1/2017.
 */
public class RouteFinderTest {

    public static List<TransportCostUpdate> createTCUs() {
        ArrayList<TransportCostUpdate> tcus = new ArrayList<>();

        tcus.add(new TransportCostUpdate(null, null, "AKLD",
                "WGTN", 10, 10, 0 , 0, 10, 10,
                null, "A", Event.DOMESTIC));
        tcus.add( new TransportCostUpdate(null, null, "AKLD",
                "CHCH", 10, 10, 0 , 0, 10, 10,
                null, "A", Event.DOMESTIC));
        tcus.add( new TransportCostUpdate(null, null, "WGTN",
                "AKLD", 10, 10, 0 , 0, 10, 10,
                null, "A", Event.DOMESTIC));
        tcus.add( new TransportCostUpdate(null, null, "CHCH",
                "DNDN", 10, 10, 0 , 0, 10, 10,
                null, "A", Event.DOMESTIC));

        tcus.add( new TransportCostUpdate(null, null, "AKLD",
                "SIDNEY", 10, 10, 0 , 0, 10, 10,
                null, "A", Event.AIR));
        tcus.add( new TransportCostUpdate(null, null, "AKLD",
                "LA", 10, 10, 0 , 0, 10, 10,
                null, "A", Event.AIR));
        tcus.add( new TransportCostUpdate(null, null, "LA",
                "LONDON", 10, 10, 0 , 0, 10, 10,
                null, "A", Event.AIR));
        tcus.add( new TransportCostUpdate(null, null, "LA",
                "DUBLIN", 10, 10, 0 , 0, 10, 10,
                null, "A", Event.AIR));
        // Intl Surface
        tcus.add( new TransportCostUpdate(null, null, "LA",
                "DUBLIN", 5, 5, 0 , 0, 10, 10,
                null, "A", Event.SEA));


        return tcus;
    }


    public static List<TransportCostUpdate> createTCUs_GenericGraph() {
        // Graph from: https://youtu.be/gdmfOwyQlcI?t=4m9s


        ArrayList<TransportCostUpdate> tcus = new ArrayList<>();

        // A -> B, C, E
        tcus.add(new TransportCostUpdate(null, null, "A",
                "B", 4, 4, 0, 0, 0, 0,
                null, null, Event.DOMESTIC));
        tcus.add(new TransportCostUpdate(null, null, "A",
                "C", 3, 3, 0, 0, 0, 0,
                null, null, Event.DOMESTIC));
        tcus.add(new TransportCostUpdate(null, null, "A",
                "E", 7, 7, 0, 0, 0, 0,
                null, null, Event.DOMESTIC));

        // B -> A, C, D
        tcus.add(new TransportCostUpdate(null, null, "B",
                "A", 4, 4, 0, 0, 0, 0,
                null, null, Event.DOMESTIC));
        tcus.add(new TransportCostUpdate(null, null, "B",
                "C", 6, 6, 0, 0, 0, 0,
                null, null, Event.DOMESTIC));
        tcus.add(new TransportCostUpdate(null, null, "B",
                "D", 5, 5, 0, 0, 0, 0,
                null, null, Event.DOMESTIC));

        // C -> A, B, D, E
        tcus.add(new TransportCostUpdate(null, null, "C",
                "A", 3, 3, 0, 0, 0, 0,
                null, null, Event.DOMESTIC));
        tcus.add(new TransportCostUpdate(null, null, "C",
                "B", 6, 6, 0, 0, 0, 0,
                null, null, Event.DOMESTIC));
        tcus.add(new TransportCostUpdate(null, null, "C",
                "D", 11, 11, 0, 0, 0, 0,
                null, null, Event.DOMESTIC));
        tcus.add(new TransportCostUpdate(null, null, "C",
                "E", 8, 8, 0, 0, 0, 0,
                null, null, Event.DOMESTIC));

        // D -> B, C, E, G, F
        tcus.add(new TransportCostUpdate(null, null, "D",
                "B", 5, 5, 0, 0, 0, 0,
                null, null, Event.DOMESTIC));
        tcus.add(new TransportCostUpdate(null, null, "D",
                "C", 11, 11, 0, 0, 0, 0,
                null, null, Event.DOMESTIC));
        tcus.add(new TransportCostUpdate(null, null, "D",
                "E", 2, 2, 0, 0, 0, 0,
                null, null, Event.DOMESTIC));
        tcus.add(new TransportCostUpdate(null, null, "D",
                "G", 10, 10, 0, 0, 0, 0,
                null, null, Event.DOMESTIC));
        tcus.add(new TransportCostUpdate(null, null, "D",
                "F", 2, 2, 0, 0, 0, 0,
                null, null, Event.DOMESTIC));

        // E -> A, C, D, G
        tcus.add(new TransportCostUpdate(null, null, "E",
                "A", 7, 7, 0, 0, 0, 0,
                null, null, Event.DOMESTIC));
        tcus.add(new TransportCostUpdate(null, null, "E",
                "C", 8, 8, 0, 0, 0, 0,
                null, null, Event.DOMESTIC));
        tcus.add(new TransportCostUpdate(null, null, "E",
                "D", 2, 2, 0, 0, 0, 0,
                null, null, Event.DOMESTIC));
        tcus.add(new TransportCostUpdate(null, null, "E",
                "G", 5, 5, 0, 0, 0, 0,
                null, null, Event.DOMESTIC));

        // F -> D, G
        tcus.add(new TransportCostUpdate(null, null, "F",
                "D", 2, 2, 0, 0, 0, 0,
                null, null, Event.DOMESTIC));
        tcus.add(new TransportCostUpdate(null, null, "F",
                "G", 3, 3, 0, 0, 0, 0,
                null, null, Event.DOMESTIC));

        // G -> D, E, F
        tcus.add(new TransportCostUpdate(null, null, "G",
                "D", 10, 10, 0, 0, 0, 0,
                null, null, Event.DOMESTIC));
        tcus.add(new TransportCostUpdate(null, null, "G",
                "E", 5, 5, 0, 0, 0, 0,
                null, null, Event.DOMESTIC));
        tcus.add(new TransportCostUpdate(null, null, "G",
                "F", 3, 3, 0, 0, 0, 0,
                null, null, Event.DOMESTIC));

        return tcus;

    }


    @Test
    public void testDomesticRoutes() {
        RouteFinder finder = new RouteFinder(null);
        finder.initiateRouteFinder(new HashSet<TransportCostUpdate>(createTCUs()));
        Map<String, Set<Route>> domestics = finder.getDomesticRoutes();
        Map<String, Set<Route>> airs = finder.getAirRoutes();
        Map<String, Set<Route>> surfaces = finder.getSurfaceRoutes();

        Set<String> nzCities = domestics.keySet();
        // Critical set sizes
        Assert.assertEquals(nzCities.size(), 3);
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

    /**
     * A -> F, Should be 11.0 for weight and volume cost of final route.
     */
    @Test
    public void testDomesticRoutes_Generic() {
        RouteFinder finder = new RouteFinder(null);
        finder.initiateRouteFinder(new HashSet<TransportCostUpdate>(createTCUs_GenericGraph()));
        Map<String, Set<Route>> domestics = finder.getDomesticRoutes();
        // Map<String, Set<Route>> airs = finder.getAirRoutes();
        // Map<String, Set<Route>> surfaces = finder.getSurfaceRoutes();

        Set<String> nodes = domestics.keySet();
        // Critical set sizes
        Assert.assertEquals(nodes.size(), 7);
        Assert.assertEquals(domestics.get("A").size(), 6);

        /*System.out.println("Route from A:");
        for (Route route : domestics.get("A")) {
            System.out.println("\t"+route.toString());
        }*/


        List<String> dests = new ArrayList<>(Arrays.asList("B", "C", "D", "E", "F", "G"));
        // Check the correct routes from A are assigned to it
        for (Route route : domestics.get("A")) {
            Assert.assertEquals(route.getOrigin(), "A");
            Assert.assertTrue(dests.contains(route.getDestination()));
            for (int i = 0 ; i < dests.size() ; i++) {
                if (dests.get(i).equals(route.getDestination())) dests.remove(i) ;
            }
        }
        // Check dests is now empty
        Assert.assertTrue(dests.size() == 0);

        Set<Route> fromA = domestics.get("A") ;


        for (Route route : fromA) {
            // Check route to B is one stage and cheapest
            if (route.getDestination().equals("B")) {
                Assert.assertEquals(route.getWeightCost(), 4, 0.01);
                Assert.assertEquals(route.getVolumeCost(), 4, 0.01);
            }
            // Check route to D is 2 stages only and cheapest
            if (route.getDestination().equals("D")) {
                Assert.assertEquals(route.getWeightCost(), 9, 0.01);
                Assert.assertEquals(route.getVolumeCost(), 9, 0.01);
            }
            // Check route to F is 3 stages only and cheapest
            if (route.getDestination().equals("F")) {
                Assert.assertEquals(route.getWeightCost(), 11, 0.01);
                Assert.assertEquals(route.getVolumeCost(), 11, 0.01);
            }
        }

        // Test findRoute Domestic
        Route route1 = finder.getRoute("A", "F", Event.DOMESTIC );
        Assert.assertEquals(route1.getOrigin(), "A");
        Assert.assertEquals(route1.getDestination(), "F");
        Assert.assertEquals(route1.getVolumeCost(), 11.0, 0.01 );
        Assert.assertEquals(route1.getWeightCost(), 11.0, 0.01 );
    }

    /**
     * A -> G, Should be 12.0 for weight and volume cost of final route.
     */
    @Test
    public void testDomesticRoutes_Generic2() {
        RouteFinder finder = new RouteFinder(null);
        finder.initiateRouteFinder(new HashSet<TransportCostUpdate>(createTCUs_GenericGraph()));
        Map<String, Set<Route>> domestics = finder.getDomesticRoutes();
        // Map<String, Set<Route>> airs = finder.getAirRoutes();
        // Map<String, Set<Route>> surfaces = finder.getSurfaceRoutes();

        Set<String> nodes = domestics.keySet();
        // Critical set sizes
        Assert.assertEquals(nodes.size(), 7);
        Assert.assertEquals(domestics.get("A").size(), 6);

        /*System.out.println("Route from A:");
        for (Route route : domestics.get("A")) {
            System.out.println("\t"+route.toString());
        }*/

        List<String> dests = new ArrayList<>(Arrays.asList("B", "C", "D", "E", "F", "G"));
        // Check the correct routes from A are assigned to it
        for (Route route : domestics.get("A")) {
            Assert.assertEquals(route.getOrigin(), "A");
            Assert.assertTrue(dests.contains(route.getDestination()));
            for (int i = 0 ; i < dests.size() ; i++) {
                if (dests.get(i).equals(route.getDestination())) dests.remove(i) ;
            }
        }
        // Check dests is now empty
        Assert.assertTrue(dests.size() == 0);

        Set<Route> fromA = domestics.get("A") ;


        for (Route route : fromA) {
            // Check route to B is one stage and cheapest
            if (route.getDestination().equals("E")) {
                Assert.assertEquals(route.getWeightCost(), 7, 0.01);
                Assert.assertEquals(route.getVolumeCost(), 7, 0.01);
            }
            // Check route to D is 2 stages only and cheapest
            if (route.getDestination().equals("G")) {
                Assert.assertEquals(route.getWeightCost(), 12, 0.01);
                Assert.assertEquals(route.getVolumeCost(), 12, 0.01);
            }
        }

        // Test findRoute Domestic
        Route route1 = finder.getRoute("A", "G", Event.DOMESTIC );
        Assert.assertEquals(route1.getOrigin(), "A");
        Assert.assertEquals(route1.getDestination(), "G");
        Assert.assertEquals(route1.getVolumeCost(), 12.0, 0.01 );
        Assert.assertEquals(route1.getWeightCost(), 12.0, 0.01 );
    }

    @Test
    public void testInternationalAirRoutes() {
        RouteFinder finder = new RouteFinder(null);
        finder.initiateRouteFinder(new HashSet<TransportCostUpdate>(createTCUs()));
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

    @Test
    public void testInternationalSurfaceRoutes() {

        RouteFinder finder = new RouteFinder(null);
        finder.initiateRouteFinder(new HashSet<TransportCostUpdate>(createTCUs()));

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
