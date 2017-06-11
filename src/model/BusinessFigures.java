package model;

import java.util.List;

/**
 * Created by phoal on 6/12/2017.
 */
public class BusinessFigures implements BusinessMonitorFigures {
    private double totalRevenue;
    private double totalExpenditure;
    private int totalEvents;
    private int amountOfMail;
    private double volumeOfMail;
    private  double weightOfMail;
    private double deliveryTime;
    private String origin;
    private String destination;
    private String priority;
    private List<Route> critical;

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public double getTotalExpenditure() {
        return totalExpenditure;
    }

    public void setTotalExpenditure(double totalExpenditure) {
        this.totalExpenditure = totalExpenditure;
    }

    public int getTotalEvents() {
        return totalEvents;
    }

    public void setTotalEvents(int totalEvents) {
        this.totalEvents = totalEvents;
    }

    public int getAmountOfMail() {
        return amountOfMail;
    }

    public void setAmountOfMail(int amountOfMail) {
        this.amountOfMail = amountOfMail;
    }

    public double getVolumeOfMail() {
        return volumeOfMail;
    }

    public void setVolumeOfMail(double volumeOfMail) {
        this.volumeOfMail = volumeOfMail;
    }

    public double getWeightOfMail() {
        return weightOfMail;
    }

    public void setWeightOfMail(double weightOfMail) {
        this.weightOfMail = weightOfMail;
    }

    public double getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(double deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public List<Route> getCritical() {
        return critical;
    }

    public void setCritical(List<Route> critical) {
        this.critical = critical;
    }

}
