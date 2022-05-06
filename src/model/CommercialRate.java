package model;

public class CommercialRate {

    private final short VALUE_INDICATOR = 1000;
    private double initValue;
    private double finalValue;
    private double rate;

    public CommercialRate(double initValue, double finalValue, double rate) {
        this.initValue = initValue;
        this.finalValue = finalValue;
        this.rate = rate;
    }

    public  short getValueIndicator() {
        return VALUE_INDICATOR;
    }

    public double getInitValue() {
        return initValue;
    }

    public void setInitValue(double initValue) {
        this.initValue = initValue;
    }

    public double getFinalValue() {
        return finalValue;
    }

    public void setFinalValue(double finalValue) {
        this.finalValue = finalValue;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "CommercialRate{" +
                "VALUE_INDICATOR=" + VALUE_INDICATOR +
                ", initValue=" + initValue +
                ", finalValue=" + finalValue +
                ", rate=" + rate +
                '}';
    }
}
