package model;

import event.CustomerPriceUpdate;
import event.MailDelivery;
import event.TransportCostUpdate;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Route implements Comparable<Route> {

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
	private double avgDeliveryTime = 0;


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

	public double getAvgDeliveryTime() {
		return deliveryTime(stages);
	}

	public List<TransportCostUpdate> getStages() {
		return stages;
	}

	public boolean isCriticalRoute() {
		return (revenue - expenditure) <= 0;
	}

	public void setStages(List<TransportCostUpdate> stages) {
		this.stages = stages;
	}

	public void update(Route route) {
		weightCost = route.weightCost;
		volumeCost = route.volumeCost;
		maxVolume = route.maxVolume;
		maxWeight = route.maxWeight;
		stages = route.stages;
	}
	/**
	 public void update(CustomerPriceUpdate cpu) {
	 for (TransportCostUpdate tcu : stages) {
	 if (tcu.matchCustomerPriceUpdate(cpu)) {
	 weightCost += (cpu.getWeightCost() - tcu.getWeightCost());
	 volumeCost += (cpu.getVolumeCost() - tcu.getVolumeCost());
	 tcu.setWeightCost(cpu.getWeightCost());
	 tcu.setVolumeCost(cpu.getVolumeCost());
	 }
	 }

	 }
	 */
	public void update(MailDelivery md, List<CustomerPriceUpdate> cpus) {
		amountOfMail += 1;
		numberOfEvents += 1;
		expenditure += md.getVolume() * volumeCost + md.getWeight() * weightCost;
		revenue += getCustomerCost(md, cpus);
	}

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
		cost += volCostC + md.getVolume();
		return cost;
	}

	private CustomerPriceUpdate matchCustomerPriceUpdate(TransportCostUpdate tcu,
														 List<CustomerPriceUpdate> cpus) {
		for (CustomerPriceUpdate cpu : cpus) {
			if (tcu.getOrigin().equals(cpu.getOrigin()) &&
					tcu.getDestination().equals(cpu.getDestination()) &&
					tcu.getPriority().equals(cpu.getPriority())) {
				return cpu;
			} else return null;
		}
		return null;
	}

	public double deliveryTime(List<TransportCostUpdate> tcus) {
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Route)) return false;

		Route route = (Route) o;

		if (amountOfMail != route.amountOfMail) return false;
		if (Double.compare(route.avgDeliveryTime, avgDeliveryTime) != 0) return false;
		if (origin != null ? !origin.equals(route.origin) : route.origin != null) return false;
		if (destination != null ? !destination.equals(route.destination) : route.destination != null) return false;
		return priority != null ? priority.equals(route.priority) : route.priority == null;
	}

	@Override
	public int hashCode() {
		int result = origin != null ? origin.hashCode() : 0;
		result = 31 * result + (destination != null ? destination.hashCode() : 0);
		result = 31 * result + (priority != null ? priority.hashCode() : 0);
		return result;
	}
}

