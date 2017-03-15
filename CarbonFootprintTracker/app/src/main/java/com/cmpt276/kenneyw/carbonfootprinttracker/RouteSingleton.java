package com.cmpt276.kenneyw.carbonfootprinttracker;

public class RouteSingleton {

    private static RouteSingleton routeinstance = null;

    private RouteSingleton(){
        this.routeName= "";
        this.cityDistance = 0;
        this.highwayDistance = 0;
        this.totalDistance = cityDistance+highwayDistance;
    }

    public static RouteSingleton getInstance(){
        if(routeinstance == null){
            routeinstance = new RouteSingleton();
        }
        return routeinstance;
    }

    public String toString() {
        return routeName;
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
        this.highwayDistance=this.getCityDistance()+this.getHighwayDistance();
    }

    public void setHighwayDistance(int highwayDistance) {
        this.highwayDistance = highwayDistance;
        this.highwayDistance=this.getCityDistance()+this.getHighwayDistance();
    }

    // Variables Declaration
    private String routeName;
    private int highwayDistance;
    private int cityDistance;
    private int totalDistance;

}
