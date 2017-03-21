package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class UtilitiesCollection extends AppCompatActivity{
    private List<Utility> utilities = new ArrayList<>();

    public void addUtility(Utility utility) {
        utilities.add(utility);
    }

    public void changeUtility(Utility utility, int indexOfUtilityToEdit) {
        validateIndexWithException(indexOfUtilityToEdit);
        utilities.remove(indexOfUtilityToEdit);
        utilities.add(indexOfUtilityToEdit, utility);
    }

    public void deleteUtility(int indexToDelete) {
        validateIndexWithException(indexToDelete);
        utilities.remove(indexToDelete);
    }

    public int countUtility() {
        return utilities.size();
    }

    public Utility getUtility(int index) {
        validateIndexWithException(index);
        return utilities.get(index);
    }

    public String[] getUtilitiesDescriptions() {
        String[] descriptions = new String[countUtility()];
        for (int i = 0; i < countUtility(); i++) {
            Utility utility = getUtility(i);
            descriptions[i] = ""; //TODO: CHANGE ME
        }
        return descriptions;
    }

    public String[] getUtilitiesNames() {
        String[] descriptions = new String[countUtility()];
        for (int i = 0; i < countUtility(); i++) {
            Utility utility = getUtility(i);
            descriptions[i] = utility.getName();
        }
        return descriptions;
    }

    public String[] getUtilitiesDescriptionsWithName() {
        String[] descriptions = new String[countUtility()];
        for (int i = 0; i < countUtility(); i++) {
            Utility utility = getUtility(i);
            descriptions[i] = ""; //TODO: CHANGE ME
        }
        return descriptions;
    }

    private void validateIndexWithException(int index) {
        if (index < 0 || index >= countUtility()) {
            throw new IllegalArgumentException();
        }
    }
}
