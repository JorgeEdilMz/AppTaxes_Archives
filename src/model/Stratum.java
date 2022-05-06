package model;

public enum Stratum {
    STRATUM1(1), STRATUM2(2), STRATUM3(3), STRATUM4(4), STRATUM5(5), STRATUM6(6);

    private int indicator;

    Stratum(int indicator) {
        this.indicator = indicator;
    }

    public int getIndicator() {
        return indicator;
    }

}
