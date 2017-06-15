package model;

import event.*;

import java.util.*;

/**
 * Created by phil on 5/25/2017.
 * Module to manage how events are processed.
 * getLocationNames(), getFirmNames, getRoute() added by Julian 6/13/2017
 */
public class EventManager {
    private BusinessModel model;

    private Database db;
    private List<TransportCostUpdate> currentTcus;
    private List<MailDelivery> currentMds;
    private List<CustomerPriceUpdate> currentCpus;
    private List<TransportDiscontinued> currentTds;

    private boolean newChange = false;

    private RouteFinder routeFinder;
    // Business Figures
    private int totalEvents = 0;
    private int totalMailDelivery = 0;

    // Average Figures
    private Route averageRoute;
    private Route averageDomestic;
    private Route averageAir;
    private Route averageSurface;
    private List<Route> criticalRoutes;

    private List<Double> avgDeliveryTimes;
    private List<Double> avgDomesticDeliveryTimes;
    private List<Double> avgAirDeliveryTimes;;
    private List<Double> avgSurfaceDeliveryTimes;



    public EventManager(BusinessModel model, Database db) {
        this.model = model;

        this.currentTcus = new ArrayList<TransportCostUpdate>();
        this.currentMds = new ArrayList<MailDelivery>();
        this.currentCpus = new ArrayList<CustomerPriceUpdate>();
        this.currentTds = new ArrayList<TransportDiscontinued>();
        this.db = db;
        this.model = model;

        this.averageRoute = new Route("AVERAGE OVERALL", "AVG", 0, 0,
                0, 0, "AVG");
        this.averageDomestic = new Route("AVERAGE DOMESTIC", "AVG", 0, 0,
                0, 0, "AVG");
        this.averageAir = new Route("AVERAGE AIR", "AVG", 0, 0,
                0, 0, "AVG");
        this.averageSurface = new Route("AVERAGE SURFACE", "AVG", 0, 0,
                0, 0, "AVG");

        processAllEvents(db.getEvent());
    }

    // Code for creating Business Figures

    public void processAllEvents(List<Event> events) {

        for (Event event : events) {
            processSingleEvent(event, true);
        }
    }

    /**
     *
     * @param event
     */
    public void processEvent(Event event) {
        processSingleEvent(event, false);
    }

    public void processSingleEvent(Event event, boolean batch) {
        if (!batch) db.addEvent(event);
        totalEvents += 1;
        if (event instanceof TransportCostUpdate) {
            TransportCostUpdate tcu = (TransportCostUpdate) event;

            addTcu(currentTcus, tcu);

            if (!batch) getNewRoutes();
            else newChange = true;
        } else if (event instanceof TransportDiscontinued) {

            TransportDiscontinued td = (TransportDiscontinued) event;
            currentTds.add(td);
            cancelTcu(currentTcus, td);
            getNewRoutes();
            newChange = false;
        } else if (event instanceof CustomerPriceUpdate) {
            CustomerPriceUpdate cpu = (CustomerPriceUpdate) event;
            currentCpus.add(cpu);

        } else if (event instanceof MailDelivery) {

            if (newChange) getNewRoutes();
            newChange = false;
            totalMailDelivery++;
            MailDelivery md = (MailDelivery) event;
            processRoute(md);

        }
    }

    /**
     * Find the route which matches a MailDelivery and update all business figures
     * @param md
     */
    private void processRoute(MailDelivery md) {
        // These are returned from the matching route which is updated to add to the "Average Routes"
        double[] first = null;
        double[] second = null;
        Route route = routeFinder.getRoute(md.getOrigin(), md.getDestination(), md.getPriority()) ;
        if (route != null) {
            // Combined routes are comination of domestic and international
            // Process each separately
            if (route.isCombinedRoute()) {
                first = route.getDomestic().update(md, currentCpus);
                second = route.getInternational().update(md, currentCpus);
                updateAverageRoute(first, averageRoute);
                updateAverageRoute(first, averageDomestic);
                updateAverageRoute(second, averageRoute);
                if (md.getPriority().equals(Event.AIR)){
                    updateAverageRoute(second, averageAir);
                } else updateAverageRoute(second, averageSurface);
            } else { // just a single route find out its priority and process
                first = route.update(md, currentCpus);
                updateAverageRoute(first, averageRoute);
                if (md.getPriority().equals(Event.DOMESTIC)) {
                    updateAverageRoute(first, averageDomestic);
                } else if (md.getPriority().equals(Event.AIR)) {
                    updateAverageRoute(first, averageAir);
                } else updateAverageRoute(first, averageSurface);
            }

        }
    }

    /**
     * Take the business figures calculated from individual routes and add to average routes
     * @param costs
     * @param route
     */
    private void updateAverageRoute(double[] costs, Route route){
        route.setAmountOfMail(route.getAmountOfmail() + 1);
        route.setExpenditure(route.getExpenditure() + costs[0]);
        route.setRevenue(route.getRevenue() + costs[1]);
        route.setWeightOfMail(route.getWeightOfMail() + costs[2]);
        route.setVolumeOfMail(route.getVolumeOfMail() + costs[3]);

        String priority = route.getPriority();
        if (priority.equals("AVG")) {
            // Do nothing
        } else if (priority.equals(Event.DOMESTIC)) {
            avgDeliveryTimes.add(route.getAvgDeliveryTime());
            avgDomesticDeliveryTimes.add(route.getAvgDeliveryTime());
        } else if (priority.equals(Event.DOMESTIC)) {
            avgDeliveryTimes.add(route.getAvgDeliveryTime());
            avgAirDeliveryTimes.add(route.getAvgDeliveryTime());
        } else  {
            avgDeliveryTimes.add(route.getAvgDeliveryTime());
            avgSurfaceDeliveryTimes.add(route.getAvgDeliveryTime());
        }
    }
    // Helper - only add if not present
    private void includeIn(Route route, List<Route> routes) {
        for (Route route1 : routes) {
            if (route1.equals(route)) return;
        }
        routes.add(route);
    }
    // Add if not present OR remove old and add new
    private void addTcu(List<TransportCostUpdate> tcus, TransportCostUpdate tcu) {
        if (tcus.size() == 0) tcus.add(tcu);
        else {
            for (int i = 0 ; i < tcus.size(); i++) {
                if (tcus.get(i).equals(tcu)) tcus.remove(i);
            }
            tcus.add(tcu);
        }

    }
    // If a TCU is found which matches TD - remove it
    private void cancelTcu(List<TransportCostUpdate> tcus, TransportDiscontinued td) {
        for (int i = 0 ; i < tcus.size(); i++) {
            TransportCostUpdate tcu = tcus.get(i);
            if (tcu.getOrigin().equals(td.getOrigin()) &&
                    tcu.getDestination().equals(td.getDestination()) &&
                    tcu.getPriority().equals(td.getPriority()) &&
                    tcu.getFirm().equals(td.getFirm())) tcus.remove(i);
        }
    }

    /**
     * If a new TCU has been added or a TD been sent then recalculate all routes
     * Then transfer all business figures from old to new routes (merge)
     * Some routes may be discontinued - store them in the discontinued list
     */
    public void getNewRoutes() {
        RouteFinder local;
        if (routeFinder == null) {
            this.routeFinder = new RouteFinder(this);
            routeFinder.initiateRouteFinder(new HashSet<>(currentTcus));
        } else {

            local = new RouteFinder(null);
            local.initiateRouteFinder(new HashSet<TransportCostUpdate>(currentTcus));

            mergeRouteMaps(routeFinder.getDomesticRoutes(), local.getDomesticRoutes());
            mergeRouteMaps(routeFinder.getAirRoutes(), local.getAirRoutes());
            mergeRouteMaps(routeFinder.getSurfaceRoutes(), local.getSurfaceRoutes());

            routeFinder.setDomesticRoutes(local.getDomesticRoutes());
            routeFinder.setAirRoutes(local.getAirRoutes());
            routeFinder.setSurfaceRoutes(local.getSurfaceRoutes());
        }

    }

    /**
     * Transfer all business figures from old to new routes (merge)
     * Some routes may be discontinued - store them in the discontinued list
     * @param oldMap
     * @param newMap
     */
    private void mergeRouteMaps(Map<String , Set<Route>> oldMap, Map<String , Set<Route>> newMap){
        // Transfer all figures from the old to the new
        for (String origin : newMap.keySet()) {
            System.out.format("%s  %b  : ", origin, oldMap.containsKey(origin));
            if (oldMap.containsKey(origin)) {
                System.out.format("%s  %b  : ", origin, oldMap.containsKey(origin));
                for (Route route : newMap.get(origin)) {
                    for (Route route1 : oldMap.get(origin)) {
                        if (route.equals(route1)) {
                            route.update(route1);
                        }
                    }
                }
            }
        }
        // If an old route is not in the new map it is discontinued
        for (String origin : oldMap.keySet()) {
            boolean contains = false;
            if (!newMap.containsKey(origin)) routeFinder.getDiscontinuedRoutes().addAll(oldMap.get(origin));
            else {
                for (Route route : oldMap.get(origin)) {
                    contains = false;
                    for (Route route1 : newMap.get(origin) ) {
                        if (route.equals(route1)) contains = true;
                    }
                    if (!contains) routeFinder.getDiscontinuedRoutes().add(route);
                }
            }
        }
    }
    // helper function to add a route to a set or merge two equal routes
    private void addRoute(Set<Route> routes, Route route) {
        for (Route r : routes) {
            if (r.equals(route)) {
                r.update(route);
                return;
            }
        }
        routes.add(route);
    }

    public int getTotalEvents() {
        return totalEvents;
    }

    public int getTotalMailDelivery() {
        return totalMailDelivery;
    }

    public RouteFinder getRouteFinder() {
        return routeFinder;
    }

    public List<CustomerPriceUpdate> getCurrentCpus() {
        return currentCpus;
    }

    public Route getAverageRoute() {
        return averageRoute;
    }

    public Route getAverageDomestic() {
        return averageDomestic;
    }

    public Route getAverageAir() {
        return averageAir;
    }

    public Route getAverageSurface() {
        return averageSurface;
    }

    public List<Route> getCriticalRoutes() {
        return criticalRoutes;
    }

    public List<Double> getAvgDeliveryTimes() {
        return avgDeliveryTimes;
    }

    public List<Double> getAvgDomesticDeliveryTimes() {
        return avgDomesticDeliveryTimes;
    }

    public List<Double> getAvgAirDeliveryTimes() {
        return avgAirDeliveryTimes;
    }

    public List<Double> getAvgSurfaceDeliveryTimes() {
        return avgSurfaceDeliveryTimes;
    }
    
    public Set<String> getLocationNames() {
		Set<String> locationNames = new HashSet<>();

		for(TransportCostUpdate tcu : currentTcus) {
			locationNames.add(tcu.getOrigin());
			locationNames.add(tcu.getDestination());
		}

		return locationNames;
	}
	
	public Set<String> getFirmNames(){
		Set<String> firmNames = new HashSet<>();
		for(TransportCostUpdate tcu : currentTcus){
			firmNames.add(tcu.getFirm());
		}
		return firmNames;
	}
	
	public Route getRoute(Event ev){
		return routeFinder.getRoute(ev.getOrigin(), ev.getDestination(), ev.getPriority());
	}

}
