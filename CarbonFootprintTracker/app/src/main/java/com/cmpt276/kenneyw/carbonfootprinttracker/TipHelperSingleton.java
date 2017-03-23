package com.cmpt276.kenneyw.carbonfootprinttracker;

import android.util.Log;

public class TipHelperSingleton {

    private int[] repeatTracker;
    private int tipIndex;
    private int noCycleCounter;
    private static TipHelperSingleton helperInstance = null;
    private String currentMode;

    private TipHelperSingleton() {

        tipIndex = 0;
        currentMode = "Travel";
        repeatTracker = new int[16];
        noCycleCounter = 0;

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
        if (currentMode.equals("Travel"))  {
            this.tipIndex = 8;
            Log.i("TIPS", "what tips am I showing?"+tipIndex);
            currentMode = "Utility";
        }
        else {
            Log.i("TIPS", "Already showing utility tips");
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

    public int spiceMaker() {
        int spicyTip = 69;
        if (currentMode == "Travel") {
            spicyTip = 8;
        }
        else if (currentMode == "Utility") {
            spicyTip = 0;
        }
        return spicyTip;
    }

    public static TipHelperSingleton getInstance(){
        if(helperInstance == null){
            helperInstance = new TipHelperSingleton();
        }
        return helperInstance;
    }

}
