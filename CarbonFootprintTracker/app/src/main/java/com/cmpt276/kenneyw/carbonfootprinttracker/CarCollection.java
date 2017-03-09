package com.cmpt276.kenneyw.carbonfootprinttracker;

import java.util.ArrayList;
import java.util.List;
import com.cmpt276.kenneyw.carbonfootprinttracker.Car;

public class CarCollection {
    private List<Car> cars = new ArrayList<>();

    public void addCar(Car car){
        cars.add(car);
    }

    public void changeCar(Car car, int indexOfCarToEdit) {
        validateIndexWithException(indexOfCarToEdit);
        cars.remove(indexOfCarToEdit);
        cars.add(indexOfCarToEdit, car);
    }

    public void deleteCar(int indexToDelete){
        validateIndexWithException(indexToDelete);
        cars.remove(indexToDelete);
    }

    public int countCars() {
        return cars.size();
    }

    public Car getCar(int index) {
        validateIndexWithException(index);
        return cars.get(index);
    }

    public String[] getCarsDescriptions() {
        String[] descriptions = new String[countCars()];
        for (int i = 0; i < countCars(); i++) {
            Car car = getCar(i);
            descriptions[i] = car.getMake() + " - " +
                    car.getModel() + " - " +
                    car.getYear() + " - " +
                    car.getTransmission() + " - " +
                    car.getLiterEngine();
        }
        return descriptions;
    }

    public String[] getCarsDescriptionsWithName() {
        String[] descriptions = new String[countCars()];
        for (int i = 0; i < countCars(); i++) {
            Car car = getCar(i);
            descriptions[i] = car.getName() + " - " +
                    car.getMake() + " - " +
                    car.getModel() + " - " +
                    car.getYear() + " - " +
                    car.getTransmission() + " - " +
                    car.getLiterEngine();
        }
        return descriptions;
    }

    private void validateIndexWithException(int index) {
        if (index < 0 || index >= countCars()) {
            throw new IllegalArgumentException();
        }
    }

}
