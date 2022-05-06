package model;

public class ResidentialRange {

    public final short VALUE_INDICATOR = 1000;
     private double rateStratum1;
     private double rateStratum2;
     private double rateStratum3;
     private double rateStratum4;
     private double rateStratum5;
     private double rateStratum6;

    public ResidentialRange(double rateStratum1, double rateStratum2, double rateStratum3, double rateStratum4, double rateStratum5, double rateStratum6) {
        this.rateStratum1 = rateStratum1;
        this.rateStratum2 = rateStratum2;
        this.rateStratum3 = rateStratum3;
        this.rateStratum4 = rateStratum4;
        this.rateStratum5 = rateStratum5;
        this.rateStratum6 = rateStratum6;
    }

    public ResidentialRange() {
    }

    public short getValueIndicator() {
        return VALUE_INDICATOR;
    }

    public double getRateStratum1() {
        return rateStratum1;
    }

    public void setRateStratum1(double rateStratum1) {
        this.rateStratum1 = rateStratum1;
    }

    public double getRateStratum2() {
        return rateStratum2;
    }

    public void setRateStratum2(double rateStratum2) {
        this.rateStratum2 = rateStratum2;
    }

    public double getRateStratum3() {
        return rateStratum3;
    }

    public void setRateStratum3(double rateStratum3) {
        this.rateStratum3 = rateStratum3;
    }

    public double getRateStratum4() {
        return rateStratum4;
    }

    public void setRateStratum4(double rateStratum4) {
        this.rateStratum4 = rateStratum4;
    }

    public double getRateStratum5() {
        return rateStratum5;
    }

    public void setRateStratum5(double rateStratum5) {
        this.rateStratum5 = rateStratum5;
    }

    public double getRateStratum6() {
        return rateStratum6;
    }

    public void setRateStratum6(double rateStratum6) {
        this.rateStratum6 = rateStratum6;
    }

    @Override
    public String toString() {
        return "ResidentialRange{" +
                "VALUE_INDICATOR=" + VALUE_INDICATOR +
                ", rateStratum1=" + rateStratum1 +
                ", rateStratum2=" + rateStratum2 +
                ", rateStratum3=" + rateStratum3 +
                ", rateStratum4=" + rateStratum4 +
                ", rateStratum5=" + rateStratum5 +
                ", rateStratum6=" + rateStratum6 +
                '}';
    }
}
