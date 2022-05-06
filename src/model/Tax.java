package model;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

public class Tax {

    private ArrayList<Property> listProperties;
    private ArrayList<CommercialRate> cRanges;
    private ArrayList<ResidentialRange> rRanges;
    private Properties properties;
    private double exempt;
    private double promptPayment;

    public Tax() throws IOException {
        listProperties = new ArrayList<>();
        cRanges = new ArrayList<>();
        rRanges = new ArrayList<>();
        properties = new Properties();
        properties.load(new FileReader("src/resources/config.properties"));
    }

    public ArrayList<CommercialRate> getcRanges() {
        return cRanges;
    }

    public void setcRanges(ArrayList<CommercialRate> cRanges) {
        this.cRanges = cRanges;
    }

    public ArrayList<ResidentialRange> getrRanges() {
        return rRanges;
    }

    public void setrRanges(ArrayList<ResidentialRange> rRanges) {
        this.rRanges = rRanges;
    }

    public double getExempt() {
        return exempt;
    }

    public void setExempt(double exempt) {
        this.exempt = exempt;
    }

    public double getPromptPayment() {
        return promptPayment;
    }

    public void setPromptPayment(double promptPayment) {
        this.promptPayment = promptPayment;
    }


    public void addProperty(Property property){
        listProperties.add(property);
    }

    public void addCommercialRange(CommercialRate cRange){
        cRanges.add(cRange);
    }

    public void modifyExemptRate(double newRate){
        this.setExempt(newRate);
    }

    public void modifyPromtPaymentRate(double newRate){
        this.setPromptPayment(newRate);
    }

    public void modifyCommercialRange(byte indicator,byte modify,double newValue){
        for (int i = 0; i < cRanges.size(); i++) {
            switch (modify){
                case 1:
                    cRanges.get(indicator+1).setInitValue(newValue);
                    break;
                case 2:
                    cRanges.get(indicator+1).setFinalValue(newValue);
                    break;
                case 3:
                    cRanges.get(indicator+1).setRate(newValue);
                    break;
            }
        }
    }

    public void modifyResidentialRate(Stratum stratum, double newRate){
        ResidentialRange residentialRange = new ResidentialRange();
        switch (stratum){
            case STRATUM1:
                residentialRange.setRateStratum1(newRate);
                break;
            case STRATUM2:
                residentialRange.setRateStratum2(newRate);
                break;
            case STRATUM3:
                residentialRange.setRateStratum3(newRate);
                break;
            case STRATUM4:
                residentialRange.setRateStratum4(newRate);
                break;
            case STRATUM5:
                residentialRange.setRateStratum5(newRate);
                break;
            case STRATUM6:
                residentialRange.setRateStratum6(newRate);
                break;
        }
    }

    public String showResidentialRates(){
        StringBuilder chain = new StringBuilder();
        for (ResidentialRange residentialRange : rRanges) {
            chain.append(residentialRange.toString()).append("\n");
        }
        return chain.toString();
    }

    public String showCommercialRanges(){
        StringBuilder chain = new StringBuilder();
        for (CommercialRate commercialRate : cRanges) {
            chain.append(commercialRate.toString()).append("\n");
        }
        return chain.toString();
    }

    public void addResidentialRange(ResidentialRange rRange){
        rRanges.add(rRange);
    }

    public Property searchProperty(long cadastralNumber){
        Property property = null;
        for (Property value : listProperties) {
            if (value.getCadastralNumber() == cadastralNumber) {
                property = value;
            }
        }
        return property;
    }

    public double consultPropertyTax(long cadastralNumber, boolean promptPayment, boolean exempt){
        double initSettlement = this.getInitialSettlement(cadastralNumber);
        return initSettlement-applyDiscountExempt(initSettlement,exempt)- applyDiscountPromptPayment(initSettlement,promptPayment);
    }

    private double getInitialSettlement(long cadastralNumber){
        double tax = 0;
        Property property = this.searchProperty(cadastralNumber);
        if(property != null){
            switch (property.getUse()){
                case COMMERCIAL:
                    tax= this.applyRateCommercial(cadastralNumber);
                    break;
                case RESIDENTIAL:
                    tax = this.applyRateResidential(cadastralNumber);
                    break;
            }
        }else{
            tax = -1;
        }
        return tax;
    }

    public double applyRateResidential(long cadastralNumber){
        double stratumRate = 0;
        Property property = this.searchProperty(cadastralNumber);
        switch (property.getStratum()){
            case STRATUM1:
                stratumRate = (property.getAppraisal()* rRanges.get(0).getRateStratum1())/rRanges.get(0).VALUE_INDICATOR;
                break;
            case STRATUM2:
                stratumRate = (property.getAppraisal()* rRanges.get(0).getRateStratum2())/rRanges.get(0).VALUE_INDICATOR;
                break;
            case STRATUM3:
                stratumRate = (property.getAppraisal()* rRanges.get(0).getRateStratum3())/rRanges.get(0).VALUE_INDICATOR;
                break;
            case STRATUM4:
                stratumRate = (property.getAppraisal()* rRanges.get(0).getRateStratum4())/rRanges.get(0).VALUE_INDICATOR;
                break;
            case STRATUM5:
                stratumRate = (property.getAppraisal()* rRanges.get(0).getRateStratum5())/rRanges.get(0).VALUE_INDICATOR;
                break;
            case STRATUM6:
                stratumRate = (property.getAppraisal()* rRanges.get(0).getRateStratum6())/rRanges.get(0).VALUE_INDICATOR;
                break;
        }
        return stratumRate;
    }


    public double applyRateCommercial(long cadastralNumber){
        double rangeRate = 0;
        Property property = this.searchProperty(cadastralNumber);
        for (int i = 0; i < rRanges.size(); i++) {
            if(property.getAppraisal() > cRanges.get(i).getInitValue() && property.getAppraisal() <= cRanges.get(i).getFinalValue()){
                rangeRate = (property.getAppraisal() * cRanges.get(i).getRate())/cRanges.get(i).getValueIndicator();
            }
        }
        return rangeRate;
    }


    public double applyDiscountExempt(double setInitialSettlement, boolean exempt){
        double discountExempt = 0;
        if(exempt){
            discountExempt = setInitialSettlement * Double.parseDouble(properties.getProperty("discountExempt")) / Double.parseDouble(properties.getProperty("percentage")) ;
        }
        return discountExempt;
    }

    public double applyDiscountPromptPayment(double setInitialSettlement, boolean promtPayment){
        double discountPromt = 0;
        if(promtPayment){
            discountPromt = setInitialSettlement * Double.parseDouble(properties.getProperty("discountPromptPayment")) / Double.parseDouble(properties.getProperty("percentage"));
        }
        return discountPromt;
    }
}
