package event;

import java.time.DayOfWeek;
import java.time.ZonedDateTime;
import java.util.List;

public class TransportCostUpdate extends Event{
	double weightCost;
	double volumeCost;
	int maxWeight;
	int maxVolume;
	double frequency;
	double duration;
	DayOfWeek day;
	String firm;

	public TransportCostUpdate(ZonedDateTime dateTime, String user, String origin, String destination, double weightCost, double volumeCost, int maxWeight, int maxVolume, double frequency, double duration, DayOfWeek day, String firm) {
		super(dateTime, user, origin, destination);
		this.weightCost = weightCost;
		this.volumeCost = volumeCost;
		this.maxWeight = maxWeight;
		this.maxVolume = maxVolume;
		this.frequency = frequency;
		this.duration = duration;
		this.day = day;
		this.firm = firm;
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

	public double getFrequency() {
		return frequency;
	}

	public double getDuration() {
		return duration;
	}

	public DayOfWeek getDay() {
		return day;
	}

	public String getFirm() {
		return firm;
	}
}
