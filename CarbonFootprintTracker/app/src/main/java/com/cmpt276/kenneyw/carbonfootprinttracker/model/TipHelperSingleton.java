package com.cmpt276.kenneyw.carbonfootprinttracker.model;

/**
 * Creates a TipHelperSingleton. There can be only one.
 * The singleton will keep it's values somewhat persistently, which is useful for future uses of the
 * singleton
 */

import android.util.Log;

public class TipHelperSingleton {

    private int[] repeatTracker;
    private int tipIndex;
    private int noCycleCounter;
    private int moreThanFourUtil;
    private int moreThanFourElec;
    private static TipHelperSingleton helperInstance = null;
    private String currentMode;

    private TipHelperSingleton() {

        tipIndex = 0;
        currentMode = "Travel";
        repeatTracker = new int[16];
        noCycleCounter = 0;
        moreThanFourUtil = 0;
        moreThanFourElec = 0;

    }

    public void setTipIndexTravel() {
        if (currentMode.equals("Utility")) {
            this.tipIndex = 0;
            currentMode = "Travel";
        }
        else {
            Log.i("TIPS", "Already showing travel tips");
        }
    }

    public void setTipIndexUtil() {
        if (currentMode.equals("Travel") || currentMode.equals("Electric"))  {
            this.tipIndex = 8;
            currentMode = "Utility";
        }
        else {
            Log.i("TIPS", "Already showing utility tips");
        }
    }

    public void setTipIndexElec() {
        if (currentMode.equals("Travel") || currentMode.equals("Utility"))  {
            this.tipIndex = 13;
            currentMode = "Electric";
        }
        else {
            Log.i("TIPS", "Already showing electric tips");
        }
    }

    public int getTipIndex() {
        return tipIndex;
    }

    public int checkRepeatTracker(int tipIndex) {
        if (repeatTracker[tipIndex] == 0) {
            for (int i=0; i<16; i++) {
                if (repeatTracker[i] != 0) {
                    repeatTracker[i]++;
                }
                if (repeatTracker[i] > 9) {
                    repeatTracker[i] = 0;
                }
            }
            repeatTracker[tipIndex]++;
        }
        else if (repeatTracker[tipIndex] > 0) {
            tipIndex++;
            tipIndex = checkRepeatTracker(tipIndex);
        }
        return tipIndex;
    }

    public int spiceTimer() {
        if (noCycleCounter < 4) {
            noCycleCounter++;
        }
        if (noCycleCounter == 4) {
            noCycleCounter = 0;
            Log.i("Spicy!", ""+noCycleCounter);
            return 1;
        }
        Log.i("Spicy?", ""+noCycleCounter);
        return 0;
    }

    public int spiceTimerUtility() {
        moreThanFourUtil++;
        if (moreThanFourUtil >= 4) {
            return 1;
        }
        return 0;
    }

    public int spiceTimerElectric() {
        moreThanFourUtil++;
        if (moreThanFourElec >= 4) {
            return 1;
        }
        return 0;
    }

    public int spiceMaker() {
        int spicyTip = 69;
        /*if util or elec exist, use a different one.
            //spicyTip = 8;
        }
        else {
            spicyTip = 0;
        }*/
        return spicyTip;
    }

    public static TipHelperSingleton getInstance(){
        if(helperInstance == null){
            helperInstance = new TipHelperSingleton();
        }
        return helperInstance;
    }

}
