package com.cmpt276.kenneyw.carbonfootprinttracker;


import java.util.ArrayList;
import java.util.List;

/**
 * DESCRIPTION:
 *  - Create a list of car and functions to access to cars in the list
 */

public class RouteCollection {
    private List<Route> routes = new ArrayList<>();

    public void addRoute(Route route){
        routes.add(route);
    }

    public void changeRoute(Route route, int indexOfRouteToEdit) {
        validateIndexWithException(indexOfRouteToEdit);
        routes.remove(indexOfRouteToEdit);
        routes.add(indexOfRouteToEdit, route);
    }

    public void deleteRoute(int indexToDelete){
        validateIndexWithException(indexToDelete);
        routes.remove(indexToDelete);
    }

    public int countRoutes() {
        return routes.size();
    }

    public Route getRoute(int index) {
        validateIndexWithException(index);
        return routes.get(index);
    }

    public String[] getRoutesDescriptions() {
        String[] descriptions = new String[countRoutes()];
        for (int i = 0; i < countRoutes(); i++) {
            Route route = getRoute(i);
            /*descriptions[i] = car.getMake() + " - " + TODO: CHANGE THIS DESCRIPTION
                    car.getModel() + " - " +
                    car.getYear() + " - " +
                    car.getTransmission() + " - " +
                    car.getLiterEngine() + "L";*/
        }
        return descriptions;
    }

    public String[] getRoutesNames() {
        String[] descriptions = new String[countRoutes()];
        for (int i = 0; i < countRoutes(); i++) {
            Route route = getRoute(i);
            descriptions[i] = route.getRouteName();
        }
        return descriptions;
    }

    public String[] getCarsDescriptionsWithName() {
        String[] descriptions = new String[countRoutes()];
        for (int i = 0; i < countRoutes(); i++) {
            Route car = getRoute(i);
            /*descriptions[i] = car.getName() + " - " + TODO: CHANGE THIS DESCRIPTION
                    car.getMake() + " - " +
                    car.getModel() + " - " +
                    car.getYear() + " - " +
                    car.getTransmission() + " - " +
                    car.getLiterEngine() + "L";*/
        }
        return descriptions;
    }

    private void validateIndexWithException(int index) {
        if (index < 0 || index >= countRoutes()) {
            throw new IllegalArgumentException();
        }
    }

}
