package com.cmpt276.kenneyw.carbonfootprinttracker;

/**
 * Created by Kenney on 2017-03-20.
 */

public class TipHelperSingleton {

    private int[] repeatTracker;
    private int tipIndex;
    private static TipHelperSingleton helperinstance = null;

    private TipHelperSingleton() {

        tipIndex = 0;
        repeatTracker = new int[] {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};

    }

    public void setTipIndexGas(int tipIndex) {
        this.tipIndex = tipIndex-8;
    }

    public void setTipIndexUtil(int tipIndex) {
        this.tipIndex = tipIndex+8;
    }

    public int getTipIndex() {
        return tipIndex;
    }

    public void checkRepeatTracker(int tipIndex) {
        if (repeatTracker[tipIndex] == 0) {
            for (int i=0; i<=15; i++) {
                if (repeatTracker[i] > 0) {
                    repeatTracker[i] = repeatTracker[i]++;
                }
                if (repeatTracker[i] >= 7) {
                    repeatTracker[i] = 0;
                }
            }
            repeatTracker[tipIndex] = repeatTracker[tipIndex]++;
        }
        else if (repeatTracker[tipIndex] <= 7) {
            repeatTracker[tipIndex] = repeatTracker[tipIndex]++;
        }

    }

    public static TipHelperSingleton getInstance(){
        if(helperinstance == null){
            helperinstance = new TipHelperSingleton();
        }
        return helperinstance;
    }

}
