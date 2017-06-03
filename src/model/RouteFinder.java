package model;

import event.Event;
import event.TransportCostUpdate;

import java.util.*;

/**
 * Created by phoal on 5/31/2017.
 * Use a variation of djistra's algorithm to find all routes from each NZ city
 * Routes are composed of directed paths (Transport Cost Updates)
 * Routes are separated into: Domestic, Intl Air, Intl Surface (Sea or Land or Air)
 * Each route must have the cheapest cost between Origin and Destination
 * Cost is calculated by Square(VolumeCost) + Square(WeightCost)
 *
 * Simplification: All incoming overseas mail is treated as domestic from point of arrival
 * The algorithm can easily find incoming Intl routes if necessary.
 */
public class RouteFinder {
    private BusinessModel model;

    private Set<TransportCostUpdate> stages;
    private Set<String> domesticOrigins;
    private Set<String> origins;
    private Set<String> externalOrigins;

    private Map<String , Set<Route>> domesticRoutes;

    private Map<String ,  Set<Route>> surfaceRoutes;

    private Map<String , Set<Route>> airRoutes;

    public RouteFinder(BusinessModel model, Set<TransportCostUpdate> stages) {
        this.model = model;
        this.stages = stages;
        getAllRoutes();
    }

    /**
     * The Following methods provide the lookup maps for all routes of a given Priority
     * from a given NZ city
     * @return
     */
    public Map<String, Set<Route>> getDomesticRoutes() {
        return domesticRoutes;
    }

    /**
     *  Key - NZ cities which are the start of International Air routes
     * @return
     */
    public Map<String, Set<Route>> getSurfaceRoutes() {
        return surfaceRoutes;
    }

    /**
     * Key - NZ cities which are the start of International Surface routes
     * @return
     */
    public Map<String, Set<Route>> getAirRoutes() {
        return airRoutes;
    }

    /**
     * Finds a route from any NZ city to any destination with a given priority
     *
     * @param origin Any N.Z city
     * @param destination Any destination
     * @param priority Event.DOMESTIC (N.Z. ONLY) , Event.AIR (International Air),
     *                 Event.SEA or Event.lAND (International Surface)
     * @return a Route if found OR null - check for null.
     */
    public Route getRoute(String origin, String destination, String priority) {

        // Domestic
        if (priority.equals(Event.DOMESTIC)) {
            for (Route route : domesticRoutes.get(origin)) {
                if (route.getDestination().equals(destination)) {
                    return route;
                }
            }
        } else { // get Domestic + Intl combined
            return getCombinedRoute(origin, destination, priority);
        }
        return null; // no match
    }

    /**
     * Takes the initial set of Transport Cost Updates and forms the necessary route look up maps
     */
    private void getAllRoutes() {
        // All NZ cities
        domesticOrigins = new HashSet<>();
        // NZ cities which are the start of Intl routes
        origins = new HashSet<>();
        externalOrigins = new HashSet<>();

        domesticRoutes = new HashMap<>();
        airRoutes = new HashMap<>();
        surfaceRoutes = new HashMap<>();

        for (TransportCostUpdate tcu : stages) {

            if (tcu.getPriority().equals(Event.DOMESTIC)) {
                domesticOrigins.add(tcu.getOrigin());

            } else {
                // Filter - we're only interested in routes from NZ cities
                if (domesticOrigins.contains(tcu.getOrigin())) {
                    origins.add(tcu.getOrigin());
                } else { // keep a record of all foreign locations
                    externalOrigins.add(tcu.getOrigin());
                }
            }
        }
        // Now form the lookup maps
        for (String start : domesticOrigins) {
            domesticRoutes.put(start, new HashSet<Route>());
            getAllRoutesFromOrigin(start, Event.DOMESTIC);
        }

        for (String start : origins) {
            airRoutes.put(start, new HashSet<Route>());
            getAllRoutesFromOrigin(start, Event.AIR);
        }

        for (String start : origins) {
            surfaceRoutes.put(start, new HashSet<Route>());
            getAllRoutesFromOrigin(start, Event.SEA);
        }
    }

    /**
     * Uses a priority queue to get the lowest cost route from a given city to all connected destinations
     * @param origin The NZ start city
     * @param priority
     */
    private void getAllRoutesFromOrigin(String origin, String priority) {

        PriorityQueue<Route> priorityQueue = new PriorityQueue<>();
        // Get the right start points for given priority
        Set<String> starts = priority.equals(Event.DOMESTIC) ? domesticOrigins : origins;
        // Get the right map to populate
        Map<String, Set<Route>> routes = priority.equals(Event.DOMESTIC) ? domesticRoutes : (
                (priority.equals(Event.AIR)) ? airRoutes : surfaceRoutes);
        // Start by converting each TCU from start into a route
        for (TransportCostUpdate tcu : stages) {
            if (findMatch(origin, priority,tcu)) {
                priorityQueue.add(new Route(tcu, priority));
            }
        }
        for (Route route : priorityQueue) {
            // TEST : System.out.format("pq1 %s  %s",route.getOrigin(), route.getDestination() );

    }
        while (!priorityQueue.isEmpty()) {
            Route newRoute = priorityQueue.poll();
            // If we've returned to start discard this route
            if (newRoute.getDestination().equals(origin)) continue;
            // If we've already visited destination make sure to compare the cost
            // Keep the lowest cost route and discard the other
            if (routeExists(routes.get(origin), newRoute)) continue;
            // otherwise it's a new route - add it to the lookup map
            routes.get(origin).add(newRoute);
            // TEST: System.out.format("%ncycle %s, %s ", newRoute.getOrigin(), newRoute.getDestination());

            // Now extend the route and carry on
            // MUST form an new instance of route - distinct from it's prior route paths
            for (TransportCostUpdate tcu : stages) {
                if (findMatch(newRoute.getDestination(), priority, tcu)) {

                    priorityQueue.add(new Route(newRoute, tcu));
                }

            }
            /**
             * Handy Test which lists all the routes

            for (Route route : routes.get(origin)) {
            System.out.format("%ncycle2 %s, %s ", route.getOrigin(), route.getDestination());}
             */
        }
    }

    /**
     * Checks a given route to find out if:
     * the next tcu can be used to extend it further
     *
     * @param nextOrigin
     * @param priority
     * @param tcu
     * @return
     */
    private boolean findMatch(String nextOrigin, String priority, TransportCostUpdate tcu){

            //if last point of route matches start of TCU;
            if (tcu.getOrigin().equals(nextOrigin) && tcu.getPriority().equals(priority)) {
                return true;
            }
            // if it's intl surface it can use any route priority available except DOMESTIC
            // The priority queue will always guarantee the cheapest cost
            else if (!(priority.equals(Event.AIR) || (priority.equals(Event.DOMESTIC)))
                    && !tcu.getPriority().equals(Event.DOMESTIC) && tcu.getOrigin().equals(nextOrigin)) {
                return true;
            }

            return false;
    }

    /**
     * Checks if a route already exists
     * If so it compares costs and discards the most expensive
     * @param routes
     * @param newRoute
     * @return true if a route already exists
     */
    private boolean routeExists(Set<Route> routes, Route newRoute) {
        for (Route route : routes) {
            if (route.getDestination().equals(newRoute.getDestination())) {
                if (newRoute.getEffectiveCost() < route.getEffectiveCost()) {
                    routes.remove(route);
                    routes.add(newRoute);
                }
                return true;
            }
        }
        return false;
    }

    /**
     * If there is no direct route from a N.Z. city to an overseas destination a temporary combination
     * Domestic + International Route will be created.
     * @param origin
     * @param destination
     * @param priority
     * @return
     */
    private Route getCombinedRoute(String origin, String destination, String priority) {
        Map<String, Set<Route>> routes;
        Route intlRoute;
        String intlStart;

        if (priority.equals(Event.AIR)) routes = airRoutes;
        else routes = surfaceRoutes;

        for (String s : routes.keySet()) {
            // direct route
            if (s.equals(origin)) {
                for (Route route : routes.get(origin)) {
                    if (route.getDestination().equals(destination)) return route;
                }
            } else { // otherwise find intl + domestic connection
                // Simplification : find first intl route available
                for (String s1 : routes.keySet()) {
                    for (Route route : routes.get(s1)) {
                        if (route.getDestination().equals(destination)) {
                            intlRoute = route;
                            intlStart = s1;
                            // Now find domestic connection
                            Route domestic1 = getRoute(origin, intlStart, Event.DOMESTIC);
                            if (domestic1 != null) return new Route(domestic1, intlRoute, priority);
                        }
                    }
                }
            }
        } return null; // no match found;
    }
}


