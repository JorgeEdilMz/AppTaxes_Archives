package persistence;

import model.Property;
import model.Stratum;
import model.Use;

import java.io.*;
import java.util.ArrayList;

public class LoadArchives {

    public final String PATH_FILE_PROPERTIES = "src/resources/config.properties";
    public final String PATH_BOYACA = "src/data/BOYACA_REG1.txt";
    public final String PATH_TXT = "src/data/recordsLands";

    public ArrayList<Property> loadRecordsLands() throws IOException {
        ArrayList<Property> properties = new ArrayList<>();
        BufferedReader buffer = new BufferedReader(new FileReader(this.PATH_TXT));
            String line;
            while ((line = buffer.readLine())!= null){
                String[] lands = line.split("-");
                long cadastralNumber = Long.parseLong(lands[0]);
                String address = lands[1];
                double area = Double.parseDouble(lands[2]);
                Stratum stratum = showMenuStratum(Byte.parseByte(lands[3]));
                Use use = showMenuUse(Byte.parseByte(lands[4]));
                double appraisal = Double.parseDouble(lands[5]);
                properties.add(new Property(cadastralNumber,address,area,stratum,use,appraisal));
            }
            return properties;
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

    public void writeArchive(Object[] data) throws IOException {
        FileWriter source = new FileWriter(new File(this.PATH_BOYACA).getAbsoluteFile(),true);
        for (Object datum : data) {
            source.write(datum + "\t");
        }
        source.close();
    }

    public ArrayList<Property> loadLandsBoyaca() throws IOException {
        ArrayList<Property> properties = new ArrayList<>();
        String line;
        BufferedReader br = new BufferedReader(new FileReader(this.PATH_BOYACA));
        while ((line = br.readLine()) != null){
            String[] values = line.split("\t");
            boolean condition = values[4].equals("A") || values[4].equals("R") && values[0].equals("15") && values[1].equals("001");
            if(condition){
                long cadastralNumber = Long.parseLong(values[2]);
                String address = values[3];
                double area = Double.parseDouble(values[5]);
                Stratum stratum = this.getStratumRandom();
                Use use = this.getUseFile(String.valueOf(values[4]));
                double appraisal = area*10000;
                properties.add(new Property(cadastralNumber,address,area,stratum,use,appraisal));
            }
        }
        return properties;
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

    private Use getUseFile(String word){
        Use use = null;
        switch (word){
            case "R":
                use = Use.COMMERCIAL;
                break;
            case "A":
                use = Use.RESIDENTIAL;
                break;
        }
        return use;
    }

}
