package com.totyrora.fishcalc4;

/**
 * Created by thomas on 15-07-09.
 *
 * FishCalc4 Estimation
 *
 */

class Fish {

    //TODO add a out-of-range state that can be used

    private double len;     // cm
    private final double lenMax = 120;
    private final double lenMin = 20;
    private final double inch = 2.54;
    private double weight;  // g
    private final double weightMax = 20000;
    private final double weightMin = 100;
    private final double pd = 2.2046;
    private double k;
    private final int[] kArray;
    private final int[] hArray;
    private int spices;
    private final int referenceSpices = 0;
    private final double sd = 0.15;


    public Fish(int startLen, int startSpices, int[] initKArr, int[] initHArr) {
        kArray = initKArr;
        hArray = initHArr;
        setLen(startLen, false);
        setSpices(startSpices);
        calcWeight();
    }

    private double lenConvert (boolean imperial) {
        if(imperial) {
            return inch;
        } else {
            return 1.0;
        }
    }

    private double weightConvert(boolean imperial){
        if(imperial) {
            return pd;
        } else {
            return 1.0;
        }
    }

 /*   private void calcLen () {
        //TODO later fix the reverse
        //boolean y = true;
    }
*/
    private void calcWeight () {
        weight = Math.pow(len, 3) * k / 100;
    }

    private void calcCondition(){
        if ((len == 0) || (weight == 0) ) {
            k = 0.0;       // outside normal or may divide by zero
        } else {
            k = 100 * weight / Math.pow(len, 3);
        }
    }

    public int calcConditionState(){
        int hState = 0;
        double tmpLimit;
        for (int i = 0; i < hArray.length; i ++) {
            tmpLimit = (hArray[i] / 100.0) * ((kArray[spices]/100.0)/(kArray[referenceSpices]/100.0));
            if (k >= tmpLimit)
                hState = i;
        }
        return hState;
    }

    public void setLen (double inLen, boolean imperial) {

        double tmpLen;
        tmpLen = inLen * lenConvert(imperial);

        if ((tmpLen < lenMin) || (tmpLen > lenMax)) {
            tmpLen = 0.0; // outside normal
        }

        len = tmpLen;
    }


    public void setSpices (int selSpices) {
        spices = selSpices;
        k = (double)kArray[selSpices]/100;
    }

    public void setWeight (double inWeight, boolean imperial) {

        double tmpWeight;
        tmpWeight = inWeight/weightConvert(imperial);

        if ((tmpWeight < weightMin) || (tmpWeight > weightMax)) {
            tmpWeight = 0.0; // outside normal
        }

        weight = tmpWeight;
    }

    public int getSpices () {return spices;}

    public double getWeight(boolean imperial) {
        calcWeight();
        return weight * weightConvert(imperial);
    }

    public double getWeightHi(boolean imperial) {return getWeight(imperial)*(1 + sd);}
    public double getWeightLo(boolean imperial) {return getWeight(imperial)*(1 - sd);}

    public double getLen(boolean imperial) {return len/lenConvert(imperial);}

    public double getCondition() {
        calcCondition();
        return k;
    }

}
