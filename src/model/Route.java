package model;

import event.TransportCostUpdate;

import java.util.List;

public class Route implements Comparable<Route> {

	private double effectiveCost;
	private String origin;
	private String destination;

	private double weightCost;
	

	public List<TransportCostUpdate> getStages() {
		return stages;
	}

	public void setStages(List<TransportCostUpdate> stages) {
		this.stages = stages;
	}

	private double volumeCost;
	private int maxWeight;
	private int maxVolume;
	private String priority;

	private List<TransportCostUpdate> stages;

	public Route(TransportCostUpdate tcu) {
		this(tcu.getOrigin(), tcu.getDestination(), tcu.getWeightCost(), tcu.getVolumeCost(), tcu.getMaxWeight(),
				tcu.getMaxVolume() , tcu.getPriority());
	}

	public Route(TransportCostUpdate tcu, String priority) {
		this(tcu.getOrigin(), tcu.getDestination(), tcu.getWeightCost(), tcu.getVolumeCost(), tcu.getMaxWeight(),
				tcu.getMaxVolume() , priority);
	}

	public Route(Route route, TransportCostUpdate tcu) {
		this.origin = route.origin;
		this.destination = tcu.getDestination();
		this.weightCost = route.weightCost + tcu.getWeightCost();
		this.volumeCost = route.volumeCost + tcu.getVolumeCost();
		this.maxWeight = (route.maxWeight < tcu.getMaxWeight()) ? route.maxWeight : tcu.getMaxWeight();
		this.maxVolume = (route.maxVolume < tcu.getMaxVolume()) ? route.maxVolume : tcu.getMaxVolume();
		this.priority = route.priority;

		this.effectiveCost = (weightCost * weightCost) + (volumeCost * volumeCost);
	}
	// Combines 2 routes

	public Route(Route first, Route next) {
		this.origin = first.origin;
		this.destination = next.getDestination();
		this.weightCost = first.weightCost + next.getWeightCost();
		this.volumeCost = first.volumeCost + next.getVolumeCost();
		this.maxWeight = (first.maxWeight < next.getMaxWeight()) ? first.maxWeight : next.getMaxWeight();
		this.maxVolume = (first.maxVolume < next.getMaxVolume()) ? first.maxVolume : next.getMaxVolume();
		this.priority = first.priority;

		this.effectiveCost = (weightCost * weightCost) + (volumeCost * volumeCost);
	}

	public  Route(Route first, Route next, String priority) {
		this.origin = first.origin;
		this.destination = next.getDestination();
		this.weightCost = first.weightCost + next.getWeightCost();
		this.volumeCost = first.volumeCost + next.getVolumeCost();
		this.maxWeight = (first.maxWeight < next.getMaxWeight()) ? first.maxWeight : next.getMaxWeight();
		this.maxVolume = (first.maxVolume < next.getMaxVolume()) ? first.maxVolume : next.getMaxVolume();
		this.priority = priority;

		this.effectiveCost = (weightCost * weightCost) + (volumeCost * volumeCost);
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

		this.effectiveCost = (weightCost * weightCost) + (volumeCost * volumeCost);
	}

	public void addStage(TransportCostUpdate tcu) {
		this.destination = tcu.getDestination();
		this.weightCost = this.weightCost + tcu.getWeightCost();
		this.volumeCost = this.volumeCost + tcu.getVolumeCost();
		this.maxWeight = (this.maxWeight < tcu.getMaxWeight()) ? this.maxWeight : tcu.getMaxWeight();
		this.maxVolume = (this.maxVolume < tcu.getMaxVolume()) ? this.maxVolume : tcu.getMaxVolume();

		this.effectiveCost = (weightCost * weightCost) + (volumeCost * volumeCost);
	}
	@Override
	public int compareTo(Route other) {
		if (this.effectiveCost < other.effectiveCost) return -1;
		else if (this.effectiveCost == other.effectiveCost) return 0;
		else return 1;
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

}

