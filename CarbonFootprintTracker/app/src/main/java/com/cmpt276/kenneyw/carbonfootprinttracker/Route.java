package com.cmpt276.kenneyw.carbonfootprinttracker;

/**
 * This Class makes an Individual Route and has it's getters and setters and contructor
 */

public class Route {
    public Route(String routeName, int cityDistance, int highwayDistance) {
        this.routeName=routeName;
        this.cityDistance = cityDistance;
        this.highwayDistance = highwayDistance;
        this.totalDistance = cityDistance+highwayDistance;
    }

    public String toString() {
        return routeName+" - "+cityDistance+" City, "+highwayDistance+" Highway";
    }

    public String getRouteName() {
        return routeName;
    }

    public int getCityDistance() {
        return cityDistance;
    }

    public int getHighwayDistance() {
        return highwayDistance;
    }

    public int getTotalDistance() {
        return totalDistance;
    }

    public void setRouteName(String routeName) {
        this.routeName = routeName;

    }

    public void setCityDistance(int cityDistance) {
        this.cityDistance = cityDistance;
        this.totalDistance=this.getCityDistance()+this.getHighwayDistance();
    }

    public void setHighwayDistance(int highwayDistance) {
        this.highwayDistance = highwayDistance;
        this.totalDistance=this.getCityDistance()+this.getHighwayDistance();
    }

    // Varaibles Declaration
    private String routeName;
    private int highwayDistance;
    private int cityDistance;
    private int totalDistance;

}