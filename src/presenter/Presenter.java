package presenter;

import model.*;
import views.IoManager;

import java.io.*;
import java.util.Properties;

public class Presenter {

    public final String PATH_FILE_PROPERTIES = "src/resources/config.properties";
    public final String PATH_CSV = "src/data/propertyRecords.csv";
    public final String PATH_TXT = "src/data/recordsLands";

    private Tax tax;
    IoManager io;
    Properties properties;

    public Presenter() throws IOException {
        tax = new Tax();
        io = new IoManager();
        properties = new Properties();
        this.loadPropertiesFile();
        initMenu();
    }

    public void loadPropertiesFile() throws IOException {
        properties.load(new FileReader(this.PATH_FILE_PROPERTIES));
        this.loadProperties();
        this.loadRecordsLands();
    }

    public void loadLandsCSV() throws IOException {
        String line;
        BufferedReader br = new BufferedReader(new FileReader(this.PATH_CSV));
        while ((line = br.readLine()) != null){
            String[] values = line.split(",");
            if(values[0].equals(String.valueOf(15)) && values[1].equals(String.valueOf(1))){
                long cadastralNumber;
                if (values[5].matches("-?\\d+")){
                     cadastralNumber = Long.parseLong(values[5]);
                }else {
                    cadastralNumber = (long) (Math.random()*1000000+1);
                }
                String address = values[3];
                double area;
                if (values[4].matches("-?\\d+")){
                    area = Double.parseDouble(values[4])/10;
                }else {
                    area = (Math.random()*10000);
                }
                Stratum stratum = this.getStratumRandom();
                Use use = this.getUseFile(String.valueOf(values[4]));
                double appraisal = area*1000;
                tax.addProperty(new Property(cadastralNumber,address,area,stratum,use,appraisal));
            }
        }
    }

    public void loadRecordsLands() throws IOException {
        String line;
        BufferedReader buffer = new BufferedReader(new FileReader(this.PATH_TXT));
        while ((line = buffer.readLine())!= null){
            String[] lands = line.split("-");
            long cadastralNumber = Long.parseLong(lands[0]);
            String address = lands[1];
            double area = Double.parseDouble(lands[2]);
            Stratum stratum = showMenuStratum(Byte.parseByte(lands[3]));
            Use use = showMenuUse(Byte.parseByte(lands[4]));
            double appraisal = Double.parseDouble(lands[5]);
            tax.addProperty(new Property(cadastralNumber,address,area,stratum,use,appraisal));
        }
    }

    private Use getUseFile(String word){
        Use use;
        switch (word){
            case "D":
                use = Use.COMMERCIAL;
                break;
            default:
                use = Use.RESIDENTIAL;
        }
        return use;
    }

    private Stratum getStratumRandom(){
        Stratum stratum = null;
        int num = (int)(Math.random() * 6 + 1);
        switch (num){
            case 1:
                stratum = Stratum.STRATUM1;
                break;
            case 2:
                stratum = Stratum.STRATUM2;
                break;
            case 3:
                stratum = Stratum.STRATUM3;
                break;
            case 4:
                stratum = Stratum.STRATUM4;
                break;
            case 5:
                stratum = Stratum.STRATUM5;
                break;
            case 6:
                stratum = Stratum.STRATUM6;
                break;
        }

        return stratum;
    }

    public void loadProperties(){
        loadPropertiesCommercial();
        loadPropertiesResidential();
        tax.setExempt(Double.parseDouble(properties.getProperty("discountExempt")));
        tax.setPromptPayment(Double.parseDouble(properties.getProperty("discountPromptPayment")));
    }

    public void loadPropertiesResidential(){
        double rateStratum1 = Double.parseDouble(properties.getProperty("rateResidentialStratum1"));
        double rateStratum2 = Double.parseDouble(properties.getProperty("rateResidentialStratum2"));
        double rateStratum3 = Double.parseDouble(properties.getProperty("rateResidentialStratum3"));
        double rateStratum4 = Double.parseDouble(properties.getProperty("rateResidentialStratum4"));
        double rateStratum5 = Double.parseDouble(properties.getProperty("rateResidentialStratum5"));
        double rateStratum6 = Double.parseDouble(properties.getProperty("rateResidentialStratum6"));
        tax.addResidentialRange(new ResidentialRange(rateStratum1,rateStratum2,rateStratum3,rateStratum4,rateStratum5,rateStratum6));
    }

    public void loadPropertiesCommercial(){
        assert false;
        double commercialRange = Double.parseDouble(properties.getProperty("commercialRange"));
        double commercialRange1 = Double.parseDouble(properties.getProperty("commercialRange1"));
        double commercialRange2 = Double.parseDouble(properties.getProperty("commercialRange2"));
        double commercialRange3 = Double.parseDouble(properties.getProperty("commercialRange3"));
        double commercialRange4 = Double.parseDouble(properties.getProperty("commercialRange4"));
        double commercialRange5 = Double.parseDouble(properties.getProperty("commercialRange5"));
        double commercialRange6 = Double.parseDouble(properties.getProperty("commercialRange6"));
        double rateCommercialRange1 = Double.parseDouble(properties.getProperty("rateCommercialRange1"));
        double rateCommercialRange2 = Double.parseDouble(properties.getProperty("rateCommercialRange2"));
        double rateCommercialRange3 = Double.parseDouble(properties.getProperty("rateCommercialRange3"));
        double rateCommercialRange4 = Double.parseDouble(properties.getProperty("rateCommercialRange4"));
        double rateCommercialRange5 = Double.parseDouble(properties.getProperty("rateCommercialRange5"));
        double rateCommercialRange6 = Double.parseDouble(properties.getProperty("rateCommercialRange6"));
        tax.addCommercialRange(new CommercialRate(commercialRange,commercialRange1,rateCommercialRange1));
        tax.addCommercialRange(new CommercialRate(commercialRange1+1,commercialRange2,rateCommercialRange2));
        tax.addCommercialRange(new CommercialRate(commercialRange2+1,commercialRange3,rateCommercialRange3));
        tax.addCommercialRange(new CommercialRate(commercialRange3+1,commercialRange4,rateCommercialRange4));
        tax.addCommercialRange(new CommercialRate(commercialRange4+1,commercialRange5,rateCommercialRange5));
        tax.addCommercialRange(new CommercialRate(commercialRange5+1,commercialRange6,rateCommercialRange6));
    }

    public void initMenu() throws IOException {
        short menuOption;
        do {
            menuOption = io.showMainMenu();
            switch (menuOption){
                case 1:
                    this.addLand();
                    break;
                case 2:
                    this.addRangeCommercial();
                    break;
                case 3:
                    this.addRangeResidential();
                    break;
                case 4:
                    this.searchProperty();
                    break;
                case 5:
                    this.consultTax();
                    break;
                case 6:
                    this.modifyRanges();
                    break;
                case 7:
                    this.showRanges();
                    break;
                case 8:
                    this.refresh();
                    break;
                case 9:
                    io.showGraphicMessage(io.MESSAGE_EXIT);
                    System.exit(0);
                    break;
                default:
                    io.showGraphicMessage(io.MESSAGE_ERROR);
                    break;
            }
        }while (menuOption != 10);
    }

    public void addLand(){
        long cadastralNumber =  io.readLongGraphics("Enter the cadastral number: ");
        String address = io.readStringGraphics("Enter the address: ");
        double area = io.readDoubleGraphics("Enter the area: ");
        Stratum stratum = this.showMenuStratum();
        Use use = this.showMenuUse();
        double appraisal = io.readDoubleGraphics("Enter the appraisal: ");
        tax.addProperty(new Property(cadastralNumber,address,area,stratum,use,appraisal));
    }

    public Stratum showMenuStratum(){
        Stratum stratum = null;
        byte option =  io.showStratumMenu();
            switch (option) {
                case 1:
                    stratum = Stratum.STRATUM1;
                    break;
                case 2:
                    stratum = Stratum.STRATUM2;
                    break;
                case 3:
                    stratum = Stratum.STRATUM3;
                    break;
                case 4:
                    stratum = Stratum.STRATUM4;
                    break;
                case 5:
                    stratum = Stratum.STRATUM5;
                    break;
                case 6:
                    stratum = Stratum.STRATUM6;
                    break;
            }
        return stratum;
    }

    public Stratum showMenuStratum(byte numberStratum){
        Stratum stratum = null;
        switch (numberStratum){
            case 1:
                stratum = Stratum.STRATUM1;
                break;
            case 2:
                stratum = Stratum.STRATUM2;
                break;
            case 3:
                stratum = Stratum.STRATUM3;
                break;
            case 4:
                stratum = Stratum.STRATUM4;
                break;
            case 5:
                stratum = Stratum.STRATUM5;
                break;
            case 6:
                stratum = Stratum.STRATUM6;
                break;
        }
        return stratum;
    }

    public Use showMenuUse(){
        Use use = null;
        byte option =  io.showUseMenu();
        switch (option) {
            case 1:
                use = Use.COMMERCIAL;
                break;
            case 2:
                use = Use.RESIDENTIAL;
                break;
        }
        return use;
    }

    public Use showMenuUse(byte numberUse){
        Use use = null;
        switch (numberUse) {
            case 1:
                use = Use.COMMERCIAL;
                break;
            case 2:
                use = Use.RESIDENTIAL;
                break;
        }
        return use;
    }

    public boolean showMenuStatus(){
        boolean status = false;
        byte option =  io.showStatusMenu();
        switch (option) {
            case 1:
            case 2:
                status = true;
                break;
            case 3:
                status = false;
                break;
        }
        return status;
    }

    public boolean showMenuOnDate(){
        boolean onDate = false;
        byte option =  io.showOnDateMenu();
        switch (option) {
            case 1:
                onDate = true;
                break;
            case 3:
                onDate = false;
                break;
        }
        return onDate;
    }

    public void addRangeCommercial(){
        double initValue = io.readDoubleGraphics("Enter the initValue");
        double finalValue = io.readDoubleGraphics("Enter the finalValue");
        double rate = io.readDoubleGraphics("Enter the rate");
        tax.addCommercialRange(new CommercialRate(initValue,finalValue,rate));
    }

    public void addRangeResidential(){
        tax.getrRanges().clear();
        double rateStratumOne = io.readDoubleGraphics("Enter the rate stratum one: ");
        double rateStratumTwo = io.readDoubleGraphics("Enter the rate stratum two: ");
        double rateStratumThree = io.readDoubleGraphics("Enter the rate stratum three: ");
        double rateStratumFour = io.readDoubleGraphics("Enter the rate stratum four: ");
        double rateStratumFive = io.readDoubleGraphics("Enter the rate stratum five: ");
        double rateStratumSix = io.readDoubleGraphics("Enter the rate stratum six: ");
        tax.addResidentialRange(new ResidentialRange(rateStratumOne,rateStratumTwo,rateStratumThree,rateStratumFour,rateStratumFive,rateStratumSix));
    }

    public void searchProperty(){
        Long cadastral = io.readLongGraphics("Enter the cadastral number: ");
        io.showGraphicMessage(tax.searchProperty(cadastral).toString());
    }

    public void consultTax(){
        long cadastralNumber =  io.readLongGraphics("Enter the cadastral number: ");
        boolean exempt = this.showMenuStatus();
        boolean promptPayment = this.showMenuOnDate();
        double propertyTax = tax.consultPropertyTax(cadastralNumber,exempt,promptPayment);
        io.showGraphicMessage("The value payment is: "+propertyTax);
    }

    public void showRanges(){
        byte option = io.showTypeRangeMenu();
        switch (option){
            case 1:
                io.showGraphicMessage(tax.showResidentialRates());
                break;
            case 2:
                io.showGraphicMessage(tax.showCommercialRanges());
                break;
            case 3:
                io.showGraphicMessage(String.valueOf(tax.getExempt()));
                break;
            case 4:
                io.showGraphicMessage(String.valueOf(tax.getPromptPayment()));
                break;
            default:
                io.showGraphicMessage(io.MESSAGE_ERROR);
                break;
        }
    }

    public void modifyRanges() throws IOException {
        byte option = io.showTypeModifyRangeMenu();
        switch (option){
            case 1:
                this.modifyResidential();
                break;
            case 2:
                this.modifyRangeCommercial();
                break;
            case 3:
                double rateP = io.readIntGraphics("Enter new rate for prompt payment ");
                tax.setPromptPayment(rateP);
                io.showGraphicMessage("Successfully modified!");
                break;
            case 4:
                double rateE = io.readIntGraphics("Enter new Rate for exempt or excluded ");
                tax.setExempt(rateE);
                io.showGraphicMessage("Successfully modified!");
                break;
            default:
        }
    }

    private void modifyRangeCommercial() throws IOException {
        byte indicator = io.readByteGraphics("enter the indicator to modify");
        byte modifier = io.showModifyRangeMenu();
        switch (modifier){
            case 1:
                io.showGraphicMessage("Actually initial value: " + tax.getcRanges().get(indicator).getInitValue());
                break;
            case 2:
                io.showGraphicMessage("Actually final value: " + tax.getcRanges().get(indicator).getFinalValue());
                break;
            case 3:
                io.showGraphicMessage("Actually rate: " + tax.getcRanges().get(indicator).getRate());
                break;
        }
        double newValue = io.readIntGraphics("Enter new value for indicator " + indicator);

        switch (modifier){
            case 1:
                this.modifyInitValue(indicator,newValue);
                break;
            case 3:
                this.modifyRateRange(indicator,newValue);
                break;
        }
        io.showGraphicMessage("Successfully modified!");
    }

    public void modifyInitValue(byte indicator, double newInitValue) throws IOException {
        switch (indicator){
            case 1:
                properties.setProperty("commercialRange",String.valueOf(newInitValue));
                properties.store(new FileWriter(this.PATH_FILE_PROPERTIES),"Changed of init value " + indicator);
                break;
            case 2:
                properties.setProperty("rateCommercialRange1",String.valueOf(newInitValue));
                properties.store(new FileWriter(this.PATH_FILE_PROPERTIES),"Changed of init value " + indicator);
                break;
            case 3:
                properties.setProperty("rateCommercialRange2",String.valueOf(newInitValue));
                properties.store(new FileWriter(this.PATH_FILE_PROPERTIES),"Changed of init value " + indicator);
                break;
            case 4:
                properties.setProperty("rateCommercialRange3",String.valueOf(newInitValue));
                properties.store(new FileWriter(this.PATH_FILE_PROPERTIES),"Changed of init value " + indicator);
                break;
            case 5:
                properties.setProperty("rateCommercialRange4",String.valueOf(newInitValue));
                properties.store(new FileWriter(this.PATH_FILE_PROPERTIES),"Changed of init value " + indicator);
                break;
            case 6:
                properties.setProperty("rateCommercialRange5",String.valueOf(newInitValue));
                properties.store(new FileWriter(this.PATH_FILE_PROPERTIES),"Changed of init value " + indicator);
                break;
        }
    }

    public void modifyRateRange(byte indicator,double newRate) throws IOException {
        switch (indicator){
            case 1:
                properties.setProperty("rateCommercialRange1",String.valueOf(newRate));
                properties.store(new FileWriter(this.PATH_FILE_PROPERTIES),"changed of rate value " + indicator);
                break;
            case 2:
                properties.setProperty("rateCommercialRange2",String.valueOf(newRate));
                properties.store(new FileWriter(this.PATH_FILE_PROPERTIES),"changed of rate value " + indicator);
                break;
            case 3:
                properties.setProperty("rateCommercialRange3",String.valueOf(newRate));
                properties.store(new FileWriter(this.PATH_FILE_PROPERTIES),"changed of rate value " + indicator);
                break;
            case 4:
                properties.setProperty("rateCommercialRange4",String.valueOf(newRate));
                properties.store(new FileWriter(this.PATH_FILE_PROPERTIES),"changed of rate value " + indicator);
                break;
            case 5:
                properties.setProperty("rateCommercialRange5",String.valueOf(newRate));
                properties.store(new FileWriter(this.PATH_FILE_PROPERTIES),"changed of rate value " + indicator);
                break;
            case 6:
                properties.setProperty("commercialRange6",String.valueOf(newRate));
                properties.store(new FileWriter(this.PATH_FILE_PROPERTIES),"changed of rate value " + indicator);
                break;
        }
    }

    private void modifyResidential() throws IOException {
        Stratum stratum = this.showMenuStratum();
        switch (stratum){
            case STRATUM1:
                io.showGraphicMessage("Actually rate: " + tax.getrRanges().get(0).getRateStratum1());
                break;
            case STRATUM2:
                io.showGraphicMessage("Actually rate: " + tax.getrRanges().get(0).getRateStratum2());
                break;
            case STRATUM3:
                io.showGraphicMessage("Actually rate: " + tax.getrRanges().get(0).getRateStratum3());
                break;
            case STRATUM4:
                io.showGraphicMessage("Actually rate: " + tax.getrRanges().get(0).getRateStratum4());
                break;
            case STRATUM5:
                io.showGraphicMessage("Actually rate: " + tax.getrRanges().get(0).getRateStratum5());
                break;
            case STRATUM6:
                io.showGraphicMessage("Actually rate: " + tax.getrRanges().get(0).getRateStratum6());
                break;
        }
        double newRate = io.readIntGraphics("Enter new Rate for stratum " + stratum.getIndicator() );
        modifyRateResidential(stratum,newRate);
        io.showGraphicMessage("Successfully modified!");
    }

    private void modifyRateResidential(Stratum stratum, double newRate) throws IOException {
        switch (stratum){
            case STRATUM1:
                properties.setProperty("rateResidentialStratum1",String.valueOf(newRate));
                properties.store(new FileWriter(this.PATH_FILE_PROPERTIES),"changed of rate stratum "+ Stratum.STRATUM1.getIndicator());
                break;
            case STRATUM2:
                properties.setProperty("rateResidentialStratum2",String.valueOf(newRate));
                properties.store(new FileWriter(this.PATH_FILE_PROPERTIES),"changed of rate stratum " + Stratum.STRATUM2.getIndicator());
                break;
            case STRATUM3:
                properties.setProperty("rateResidentialStratum3",String.valueOf(newRate));
                properties.store(new FileWriter(this.PATH_FILE_PROPERTIES),"changed of rate stratum " + Stratum.STRATUM3.getIndicator());
                break;
            case STRATUM4:
                properties.setProperty("rateResidentialStratum4",String.valueOf(newRate));
                properties.store(new FileWriter(this.PATH_FILE_PROPERTIES),"changed of rate stratum " + Stratum.STRATUM4.getIndicator());
                break;
            case STRATUM5:
                properties.setProperty("rateResidentialStratum5",String.valueOf(newRate));
                properties.store(new FileWriter(this.PATH_FILE_PROPERTIES),"changed of rate stratum " + Stratum.STRATUM5.getIndicator());
                break;
            case STRATUM6:
                properties.setProperty("rateResidentialStratum6",String.valueOf(newRate));
                properties.store(new FileWriter(this.PATH_FILE_PROPERTIES),"changed of rate stratum " + Stratum.STRATUM6.getIndicator());
                break;
        }
    }

    public void refresh() {
        tax.getrRanges().clear();
        tax.getcRanges().clear();
        this.loadProperties();
    }
}
