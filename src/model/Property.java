package model;

public class Property {
    private long cadastralNumber;
    private String Address;
    private double area;
    private Stratum stratum;
    private Use use;
    private double appraisal;

    public Property(long cadastralNumber, String address, double area, Stratum stratum, Use use, double appraisal) {
        this.cadastralNumber = cadastralNumber;
        Address = address;
        this.area = area;
        this.stratum = stratum;
        this.use = use;
        this.appraisal = appraisal;
    }

    public long getCadastralNumber() {
        return cadastralNumber;
    }

    public void setCadastralNumber(long cadastralNumber) {
        this.cadastralNumber = cadastralNumber;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public Stratum getStratum() {
        return stratum;
    }

    public void setStratum(Stratum stratum) {
        this.stratum = stratum;
    }

    public Use getUse() {
        return use;
    }

    public void setUse(Use use) {
        this.use = use;
    }

    public double getAppraisal() {
        return appraisal;
    }

    public void setAppraisal(double appraisal) {
        this.appraisal = appraisal;
    }

    @Override
    public String toString() {
        return "Property{" +
                "cadastralNumber=" + cadastralNumber +
                ", Address='" + Address + '\'' +
                ", area=" + area +
                ", stratum=" + stratum +
                ", use=" + use +
                ", appraisal=" + appraisal +
                '}';
    }
}
