import event.Event;
import event.TransportCostUpdate;
import model.Route;
import model.RouteFinder;
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
                "WGTN", 10, 10, 0 , 0, 0, 0,
                null, null, Event.DOMESTIC));
        tcus.add( new TransportCostUpdate(null, null, "AKLD",
                "CHCH", 10, 10, 0 , 0, 0, 0,
                null, null, Event.DOMESTIC));
        tcus.add( new TransportCostUpdate(null, null, "WGTN",
                "AKLD", 10, 10, 0 , 0, 0, 0,
                null, null, Event.DOMESTIC));
        tcus.add( new TransportCostUpdate(null, null, "CHCH",
                "DNDN", 10, 10, 0 , 0, 0, 0,
                null, null, Event.DOMESTIC));

        tcus.add( new TransportCostUpdate(null, null, "AKLD",
                "SIDNEY", 10, 10, 0 , 0, 0, 0,
                null, null, Event.AIR));
        tcus.add( new TransportCostUpdate(null, null, "AKLD",
                "LA", 10, 10, 0 , 0, 0, 0,
                null, null, Event.AIR));
        tcus.add( new TransportCostUpdate(null, null, "LA",
                "LONDON", 10, 10, 0 , 0, 0, 0,
                null, null, Event.AIR));
        tcus.add( new TransportCostUpdate(null, null, "LA",
                "DUBLIN", 10, 10, 0 , 0, 0, 0,
                null, null, Event.AIR));
        // Intl Surface
        tcus.add( new TransportCostUpdate(null, null, "LA",
                "DUBLIN", 5, 5, 0 , 0, 0, 0,
                null, null, Event.SEA));


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
        assertEquals(nzCities.size(), 3);
        assertEquals(domestics.get("AKLD").size(), 3);

        HashSet<String> destinations = new HashSet<>();
        List<String> dests = new ArrayList<>(Arrays.asList("WGTN", "CHCH", "DNDN"));
        // Check the correct routes from AKLD are assigned to it
        for (Route route : domestics.get("AKLD")) {
            assertEquals(route.getOrigin(), "AKLD");
            assertTrue(dests.contains(route.getDestination()));
            for (int i = 0 ; i < dests.size() ; i++) {
               if (dests.get(i).equals(route.getDestination())) dests.remove(i) ;
            }
        }
        // Check dests is now empty
        assertTrue(dests.size() == 0);

        Set<Route> fromAuckland = domestics.get("AKLD") ;


        for (Route route : fromAuckland) {
            // Check route to Chch is one stage and cheapest
            if (route.getDestination().equals("CHCH")) {
                assertEquals(route.getWeightCost(), 10, 0.01);
                assertEquals(route.getVolumeCost(), 10, 0.01);
            }
            // Check route to Dndn is 2 stages only and cheapest
            if (route.getDestination().equals("DNDN")) {
                assertEquals(route.getWeightCost(), 20, 0.01);
                assertEquals(route.getVolumeCost(), 20, 0.01);
            }
        }
        // Test findRoute Surface
        Route route = finder.getRoute("WGTN", "DUBLIN", Event.SEA );
        assertEquals(route.getOrigin(), "WGTN");
        assertEquals(route.getDestination(), "DUBLIN");
        assertEquals(route.getVolumeCost(), 25.0, 0.01 );
        assertEquals(route.getWeightCost(), 25.0, 0.01 );

        // Test findRoute Domestic
        Route route1 = finder.getRoute("AKLD", "DNDN", Event.DOMESTIC );
        assertEquals(route1.getOrigin(), "AKLD");
        assertEquals(route1.getDestination(), "DNDN");
        assertEquals(route1.getVolumeCost(), 20.0, 0.01 );
        assertEquals(route1.getWeightCost(), 20.0, 0.01 );
    }
    @Test
    public void testInternationalAirRoutes() {
        RouteFinder finder = new RouteFinder(null);
        finder.initiateRouteFinder(new HashSet<TransportCostUpdate>(createTCUs()));
        Map<String, Set<Route>> domestics = finder.getDomesticRoutes();
        Map<String, Set<Route>> airs = finder.getAirRoutes();
        Map<String, Set<Route>> surfaces = finder.getSurfaceRoutes();

        //check all 4 routes exist
        assertEquals(airs.get("AKLD").size(), 4);

        HashSet<String> destinations = new HashSet<>();
        List<String> dests = Arrays.asList("SIDNEY", "DUBLIN", "LONDON", "LA");
        // Akld is the only origin
        assertEquals(airs.keySet().size(), 1);
        for (Route route : airs.get("AKLD")) {
            assertEquals(route.getOrigin(), "AKLD");
            assertTrue(dests.contains(route.getDestination()));
        }
        Set<Route> fromAuckland = airs.get("AKLD") ;
        // Check London and dublin routes exist from AKLD
        for (Route route : fromAuckland) {
            if (route.getDestination().equals("DUBLIN")) {
                assertEquals(route.getWeightCost(), 20, 0.01);
                assertEquals(route.getVolumeCost(), 20, 0.01);
            }
            if (route.getDestination().equals("LONDON")) {
                assertEquals(route.getWeightCost(), 20, 0.01);
                assertEquals(route.getVolumeCost(), 20, 0.01);
            }
        }
        // Test findRoute Air
        Route route1 = finder.getRoute("WGTN", "DUBLIN", Event.AIR );
        assertEquals(route1.getOrigin(), "WGTN");
        assertEquals(route1.getOrigin(), "WGTN");
        assertEquals(route1.getDestination(), "DUBLIN");
        assertEquals(route1.getVolumeCost(), 30.0, 0.01 );
        assertEquals(route1.getWeightCost(), 30.0, 0.01 );

    }

    @Test
    public void testInternationalSurfaceRoutes() {

        RouteFinder finder = new RouteFinder(null);
        finder.initiateRouteFinder(new HashSet<TransportCostUpdate>(createTCUs()));

        Map<String, Set<Route>> surfaces = finder.getSurfaceRoutes();
        // Auckl only origin
        assertEquals(surfaces.keySet().size(), 1);
        // All 4 routes exist
        assertEquals(surfaces.get("AKLD").size(), 4);

        HashSet<String> destinations = new HashSet<>();
        List<String> dests = Arrays.asList("SIDNEY", "DUBLIN", "LONDON", "LA");
        // Auckl only origin
        for (Route route : surfaces.get("AKLD")) {
            assertEquals(route.getOrigin(), "AKLD");
            assertTrue(dests.contains(route.getDestination()));
        }
        Set<Route> fromAuckland = surfaces.get("AKLD") ;
        Route dublin;
        Route london;
        // Check Dublin is cheapest route because of sea leg (15)
        // Check London route exists and is same as air cost
        for (Route route : fromAuckland) {
            if (route.getDestination().equals("DUBLIN")) {
                assertEquals(route.getWeightCost(), 15, 0.01);
                assertEquals(route.getVolumeCost(), 15, 0.01);
            }
            if (route.getDestination().equals("LONDON")) {
                assertEquals(route.getWeightCost(), 20, 0.01);
                assertEquals(route.getVolumeCost(), 20, 0.01);
            }
        }
        // Test findRoute Surface
        Route route = finder.getRoute("WGTN", "DUBLIN", Event.SEA );
        assertEquals(route.getOrigin(), "WGTN");
        assertEquals(route.getDestination(), "DUBLIN");
        assertEquals(route.getVolumeCost(), 25.0, 0.01 );
        assertEquals(route.getWeightCost(), 25.0, 0.01 );
    }

}
