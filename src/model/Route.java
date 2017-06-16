package model;

import event.CustomerPriceUpdate;
import event.Event;
import event.MailDelivery;
import event.TransportCostUpdate;

import java.util.ArrayList;
import java.util.List;

/**
 * A class for representing a route comprised of Transport Cost Updates
 * and their associated business figures
 */
public class Route implements Comparable<Route> {

	private Route domestic;
	private Route international;
	private boolean isCombinedRoute = false;

	private double effectiveCost;
	private String origin;
	private String destination;

	private double weightCost;

	private double volumeCost;
	private int maxWeight;
	private int maxVolume;
	private String priority;

	private List<TransportCostUpdate> stages;

	// Business Figures
	private double revenue = 0;
	private double expenditure = 0;
	private int numberOfEvents = 0;
	private int amountOfMail = 0;
	private double weightOfMail = 0;
	private double volumeOfMail = 0;
	private double avgDeliveryTime = 0;

	public boolean isCriticalRoute() {
		return (revenue - expenditure) <= 0;
	}


	public Route(TransportCostUpdate tcu) {
		this.stages = new ArrayList<>();
		stages.add(tcu);

		this.origin = tcu.getOrigin();
		this.destination = tcu.getDestination();
		this.weightCost = this.weightCost + tcu.getWeightCost();
		this.volumeCost = this.volumeCost + tcu.getVolumeCost();
		this.maxWeight = this.maxWeight + tcu.getMaxWeight();
		this.maxVolume = this.maxVolume + tcu.getMaxVolume();
		this.priority = tcu.getPriority();

		this.effectiveCost = (this.weightCost * this.weightCost) + (this.volumeCost * this.volumeCost);
	}

	public Route(TransportCostUpdate tcu, String priority) {
		this.stages = new ArrayList<>();
		stages.add(tcu);

		this.origin = tcu.getOrigin();
		this.destination = tcu.getDestination();
		this.weightCost = this.weightCost + tcu.getWeightCost();
		this.volumeCost = this.volumeCost + tcu.getVolumeCost();
		this.maxWeight = this.maxWeight + tcu.getMaxWeight();
		this.maxVolume = this.maxVolume + tcu.getMaxVolume();
		this.priority = priority;

		this.effectiveCost = (this.weightCost * this.weightCost) + (this.volumeCost * this.volumeCost);
	}

	public Route(Route route, TransportCostUpdate tcu) {
		this.stages = new ArrayList<>();
		for (int i = 0 ; i < route.stages.size(); i++) {
			this.stages.add(route.stages.get(i));
		}
		this.stages.add(tcu);

		this.origin = route.origin;
		this.destination = tcu.getDestination();
		this.weightCost = route.weightCost + tcu.getWeightCost();
		this.volumeCost = route.volumeCost + tcu.getVolumeCost();
		this.maxWeight = (route.maxWeight < tcu.getMaxWeight()) ? route.maxWeight : tcu.getMaxWeight();
		this.maxVolume = (route.maxVolume < tcu.getMaxVolume()) ? route.maxVolume : tcu.getMaxVolume();
		this.priority = route.priority;

		this.effectiveCost = (this.weightCost * this.weightCost) + (this.volumeCost * this.volumeCost);
	}
	// Combines 2 routes

	public Route(Route first, Route next) {
		this.stages = new ArrayList<>();
		for (int i = 0 ; i < first.stages.size(); i++) {
			this.stages.add(first.stages.get(i));
		}
		for (int i = 0 ; i < next.stages.size(); i++) {
			this.stages.add(next.stages.get(i));
		}

		this.origin = first.origin;
		this.destination = next.getDestination();
		this.weightCost = first.weightCost + next.getWeightCost();
		this.volumeCost = first.volumeCost + next.getVolumeCost();
		this.maxWeight = (first.maxWeight < next.getMaxWeight()) ? first.maxWeight : next.getMaxWeight();
		this.maxVolume = (first.maxVolume < next.getMaxVolume()) ? first.maxVolume : next.getMaxVolume();
		this.priority = first.priority;

		this.effectiveCost = (this.weightCost * this.weightCost) + (this.volumeCost * this.volumeCost);

		domestic = first;
		international = next;
		isCombinedRoute = true;

	}

	public  Route(Route first, Route next, String priority) {
		this.stages = new ArrayList<>();
		for (int i = 0 ; i < first.stages.size(); i++) {
			this.stages.add(first.stages.get(i));
		}
		for (int i = 0 ; i < next.stages.size(); i++) {
			this.stages.add(next.stages.get(i));
		}

		this.origin = first.origin;
		this.destination = next.getDestination();
		this.weightCost = first.weightCost + next.getWeightCost();
		this.volumeCost = first.volumeCost + next.getVolumeCost();
		this.maxWeight = (first.maxWeight < next.getMaxWeight()) ? first.maxWeight : next.getMaxWeight();
		this.maxVolume = (first.maxVolume < next.getMaxVolume()) ? first.maxVolume : next.getMaxVolume();
		this.priority = priority;

		this.effectiveCost = (this.weightCost * this.weightCost) + (this.volumeCost * this.volumeCost);

		domestic = first;
		international = next;
		isCombinedRoute = true;
	}

	public Route(Route route) {
		this.stages = new ArrayList<>();
		for (int i = 0 ; i < route.stages.size(); i++) {
			this.stages.add(route.stages.get(i));
		}

		this.origin = route.origin;
		this.destination = route.getDestination();
		this.weightCost = route.weightCost;
		this.volumeCost = route.volumeCost;
		this.maxWeight = route.maxWeight ;
		this.maxVolume = route.maxVolume;
		this.priority = route.priority;

		this.revenue = route.revenue;
		this.expenditure = route.expenditure;
		this.numberOfEvents = route.numberOfEvents;
		this.amountOfMail = route.amountOfMail;
		this.weightOfMail = route.weightOfMail;
		this.volumeOfMail = route.volumeOfMail;
		this.avgDeliveryTime = route.avgDeliveryTime;

		this.effectiveCost = (this.weightCost * this.weightCost) + (this.volumeCost * this.volumeCost);
	}

	public Route(String origin, String destination, double weightCost, double volumeCost, int maxWeight,
				 int maxVolume, String priority) {
		this.origin = origin;
		this.destination = destination;
		this.weightCost = this.weightCost + weightCost;
		this.volumeCost = this.volumeCost + volumeCost;
		this.maxWeight = this.maxWeight + maxWeight;
		this.maxVolume = this.maxVolume + maxVolume;
		this.priority = priority;

		this.effectiveCost = (this.weightCost * this.weightCost) + (this.volumeCost * this.volumeCost);
	}

	public void addStage(TransportCostUpdate tcu) {
		this.stages.add(tcu);

		this.destination = tcu.getDestination();
		this.weightCost = this.weightCost + tcu.getWeightCost();
		this.volumeCost = this.volumeCost + tcu.getVolumeCost();
		this.maxWeight = (this.maxWeight < tcu.getMaxWeight()) ? this.maxWeight : tcu.getMaxWeight();
		this.maxVolume = (this.maxVolume < tcu.getMaxVolume()) ? this.maxVolume : tcu.getMaxVolume();

		this.effectiveCost = (this.weightCost * this.weightCost) + (this.volumeCost * this.volumeCost);
	}

	public String getOrigin() {
		return origin;
	}

	public String getDestination() {
		return destination;
	}

	public double getEffectiveCost() {
		return effectiveCost;
	}

	public double getWeightCost() {
		return weightCost;
	}

	public double getVolumeCost() {
		return volumeCost;
	}

	public int getMaxWeight() {
		return maxWeight;
	}

	public int getMaxVolume() {
		return maxVolume;
	}

	public String getPriority() {
		return priority;
	}

	public double getRevenue() {
		return revenue;
	}

	public double getExpenditure() {
		return expenditure;
	}

	public int getNumberOfEvents() {
		return numberOfEvents;
	}

	public int getAmountOfmail() {
		return amountOfMail;
	}

	public double getWeightOfMail() {
		return weightOfMail;
	}

	public double getVolumeOfMail() {
		return volumeOfMail;
	}

	public double getAvgDeliveryTime() {
		return deliveryTime(stages);
	}

	public List<TransportCostUpdate> getStages() {
		return stages;
	}



	public void setStages(List<TransportCostUpdate> stages) {
		this.stages = stages;
	}


	public void update(Route route) {

		revenue = route.revenue;
		expenditure = route.expenditure;
		numberOfEvents = route.numberOfEvents;
		amountOfMail = route.amountOfMail;
		weightOfMail = route.weightOfMail;
		volumeOfMail = route.volumeOfMail;

		avgDeliveryTime = route.avgDeliveryTime;

	}

	public double[] update(MailDelivery md, List<CustomerPriceUpdate> cpus) {
		amountOfMail += 1;
		numberOfEvents += 1;
		double costE = md.getVolume() * volumeCost + md.getWeight() * weightCost;
		expenditure += costE;
		double costC = getCustomerCost(md, cpus);
		revenue += costC;
		weightOfMail += md.getWeight();
		volumeOfMail += md.getVolume();

		double[] costs = {costE, costC, md.getWeight(), md.getVolume(), getAvgDeliveryTime()};
		return costs;
	}

	/**
	 *
	 * @param md
	 * @param cpus
	 * @return
	 */
	public double getCustomerCost(MailDelivery md, List<CustomerPriceUpdate> cpus) {
		double cost = 0;
		double weightCostC = 0;
		double volCostC = 0;

		for (TransportCostUpdate tcu : stages) {
			CustomerPriceUpdate cpu = matchCustomerPriceUpdate(tcu, cpus);
			weightCostC += cpu == null ? tcu.getWeightCost() : cpu.getWeightCost();

			volCostC += cpu == null ? tcu.getVolumeCost() : cpu.getVolumeCost();
		}
		cost += weightCostC * md.getWeight();
		cost += volCostC * md.getVolume();
		//System.out.format("cpu : %f  ", cost);
		return cost;

	}

	/**
	 * Matches a CPU to a TCU and returns it.
	 * @param tcu
	 * @param cpus
	 * @return
	 */
	public CustomerPriceUpdate matchCustomerPriceUpdate(TransportCostUpdate tcu,
														 List<CustomerPriceUpdate> cpus) {
		CustomerPriceUpdate latestCpu = null;
		for (CustomerPriceUpdate cpu : cpus) {
			if (tcu.getOrigin().equals(cpu.getOrigin()) &&
					tcu.getDestination().equals(cpu.getDestination()) &&
					tcu.getPriority().equals(cpu.getPriority())) {
				latestCpu = cpu;
			}
		}
		return latestCpu;
	}

	/**
	 * Simplification for now - just takes half the wait time for next delivery and the trip duration.
	 * @param tcus
	 * @return
	 */
	private double deliveryTime(List<TransportCostUpdate> tcus) {
		if (this.stages == null || this.stages.isEmpty()) return avgDeliveryTime;
		double hours = 0;
		for (TransportCostUpdate tcu : tcus) {
			hours += tcu.getFrequency() * 0.5 + tcu.getDuration();
		}
		return hours;

	}

	@Override
	public int compareTo(Route other) {
		if (this.effectiveCost < other.effectiveCost) return -1;
		else if (this.effectiveCost == other.effectiveCost) return 0;
		else return 1;
	}

	@Override
	public String toString() {
		return "Route{" +
				"effectiveCost=" + effectiveCost +
				", origin='" + origin + '\'' +
				", destination='" + destination + '\'' +
				", weightCost=" + weightCost +
				", volumeCost=" + volumeCost +
				", maxWeight=" + maxWeight +
				", maxVolume=" + maxVolume +
				", priority='" + priority + '\'' +
				", stages=" + stages +
				", revenue=" + revenue +
				", expenditure=" + expenditure +
				", numberOfEvents=" + numberOfEvents +
				", amountOfMail=" + amountOfMail +
				", avgDeliveryTime=" + avgDeliveryTime +
				'}';
	}


	public Route getDomestic() {
		return domestic;
	}

	public Route getInternational() {
		return international;
	}

	public boolean isCombinedRoute() {
		return isCombinedRoute;
	}

	public void setRevenue(double revenue) {
		this.revenue = revenue;
	}

	public void setExpenditure(double expenditure) {
		this.expenditure = expenditure;
	}

	public void setNumberOfEvents(int numberOfEvents) {
		this.numberOfEvents = numberOfEvents;
	}

	public void setAmountOfMail(int amountOfMail) {
		this.amountOfMail = amountOfMail;
	}

	public void setWeightOfMail(double weightOfMail) {
		this.weightOfMail = weightOfMail;
	}

	public void setVolumeOfMail(double volumeOfMail) {
		this.volumeOfMail = volumeOfMail;
	}

	public void setAvgDeliveryTime(double avgDeliveryTime) {
		this.avgDeliveryTime = avgDeliveryTime;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Route)) return false;

		Route route = (Route) o;

		if (origin != null ? !origin.equals(route.origin) : route.origin != null) return false;
		if (destination != null ? !destination.equals(route.destination) : route.destination != null) return false;
		return priority != null ? samePriority(priority, route.priority) : route.priority == null;
	}
	// create Surface equality
	public boolean samePriority(String priority1, String priority2) {
		if (priority1.equals(Event.SEA) || priority1.equals(Event.LAND)) {
			return (priority2.equals(Event.SEA) || priority2.equals(Event.LAND));
		} else return priority1.equals(priority2);
	}

	@Override
	public int hashCode() {
		int result = origin != null ? origin.hashCode() : 0;
		result = 31 * result + (destination != null ? destination.hashCode() : 0);
		result = 31 * result + (priority != null ? priority.hashCode() : 0);
		return result;
	}

}

