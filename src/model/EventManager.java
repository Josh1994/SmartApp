package model;

import event.*;

import java.util.*;

/**
 * Created by phoal on 5/25/2017.
 * Module to take interact with EventFile
 */
public class EventManager {
    private BusinessModel model;
    private List<Event> eventList;
    private List<TransportCostUpdate> currentTcus;
    private List<MailDelivery> currentMds;
    private List<CustomerPriceUpdate> currentCpus;
    private List<TransportDiscontinued> currentTds;
    private boolean newChange = false;

    private RouteFinder routeFinder;
    private int totalEvents = 0;
    private int totalMailDelivery = 0;

    public EventManager(BusinessModel model, List<Event>eventList) {
        this.model = model;

        this.currentTcus = new ArrayList<TransportCostUpdate>();
        this.currentMds = new ArrayList<MailDelivery>();
        this.currentCpus = new ArrayList<CustomerPriceUpdate>();
        this.currentTds = new ArrayList<TransportDiscontinued>();
        this.eventList = eventList;
        this.model = model;
        processAllEvents(eventList);
    }

    // Code for creating Business Figures

    public void processAllEvents(List<Event> events) {

        for (Event event : events) {
            processSingleEvent(event, true);
        }
    }

    private void processSingleEvent(Event event, boolean batch) {
        if (!batch) eventList.add(event);
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
            /**
            for (TransportCostUpdate tcu : currentTcus) {
                if (tcu.matchCustomerPriceUpdate(cpu)){
                    tcu.setWeightCost(cpu.getWeightCost());
                    tcu.setVolumeCost(cpu.getVolumeCost());
                }
            }
            if (cpu.getPriority().equals(Event.DOMESTIC))
                updateCpus(routeFinder.getDomesticRoutes(), cpu);
            else {
                updateCpus(routeFinder.getAirRoutes(), cpu);
                updateCpus(routeFinder.getSurfaceRoutes(), cpu);
            }
             */
        } else if (event instanceof MailDelivery) {
            if (newChange) getNewRoutes();
            newChange = false;
            totalMailDelivery++;
            MailDelivery md = (MailDelivery) event;
            Route route = routeFinder.getRoute(md.getOrigin(), md.getDestination(), md.getPriority()) ;
            route.update(md, currentCpus);
        }
    }
    /**
    private void updateCpus(Map<String, Set<Route>> map, CustomerPriceUpdate cpu ) {
        for (String origin : map.keySet()) {
            Set<Route> routes = map.get(origin);
            for (Route route : routes) {
                route.update(cpu);
            }
        }
    }
     */
    private void addTcu(List<TransportCostUpdate> tcus, TransportCostUpdate tcu) {
        if (tcus.size() == 0) tcus.add(tcu);
        else {
            for (int i = 0 ; i < tcus.size(); i++) {
                if (tcus.get(i).equals(tcu)) tcus.remove(i);
            }
        }
        tcus.add(tcu);
    }

    private void cancelTcu(List<TransportCostUpdate> tcus, TransportDiscontinued td) {
        for (int i = 0 ; i < tcus.size(); i++) {
            TransportCostUpdate tcu = tcus.get(i);
            if (tcu.getOrigin().equals(td.getOrigin()) &&
                    tcu.getDestination().equals(td.getDestination()) &&
                    tcu.getPriority().equals(td.getPriority()) &&
                    tcu.getFirm().equals(td.getFirm())) tcus.remove(i);
        }
    }

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
        }

    }

    private void mergeRouteMaps(Map<String , Set<Route>> oldMap, Map<String , Set<Route>> newMap){
        for (String origin : newMap.keySet()) {
            if (!oldMap.containsKey(origin)) oldMap.put(origin, newMap.get(origin));
            else {
                Set<Route> news = newMap.get(origin);
                Set<Route> olds = oldMap.get(origin);
                for (Route route : news) {
                    addRoute(olds, route);
                }
            }
        }
        for (String origin : oldMap.keySet()) {
            boolean contains = false;
            if (!newMap.containsKey(origin)) {
                routeFinder.getDiscontinuedRoutes().addAll(oldMap.get(origin));
                oldMap.remove(origin);
            }
            else {
                List<Route> news = new ArrayList<Route>(newMap.get(origin));
                List<Route> olds = new ArrayList<>(oldMap.get(origin));
                for (int i = 0; i < olds.size() ; i++) {
                    Route route = olds.get(i);
                    for (Route route1 : news) {
                       if (route1.equals(route)) contains = true;
                    }
                    if (!contains) {
                        routeFinder.getDiscontinuedRoutes().add(route);
                        olds.remove(route);
                    }
                }
                oldMap.put(origin, new HashSet<>(olds));
            }
        }
    }
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
}
