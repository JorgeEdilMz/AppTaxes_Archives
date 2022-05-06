package views;

import javax.swing.*;
import java.util.Scanner;

public class IoManager {

    private Scanner scanner;
    public final String MESSAGE_ERROR = "Option entered is invalid";
    public final String MESSAGE_EXIT = "thanks for visiting us, come back soon";


    public IoManager() {
        scanner = new Scanner(System.in);
    }

    public void showMessage(String message){
        System.out.println(message);
    }

    public int readInt(String message){
        this.showMessage(message);
        return scanner.nextInt();
    }

    public Short readShort(String message){
        this.showMessage(message);
        return scanner.nextShort();
    }

    public Byte readByte(String message){
        this.showMessage(message);
        return scanner.nextByte();
    }

    public Long readLong(String message){
        this.showMessage(message);
        return scanner.nextLong();
    }

    public String readString(String message){
        this.showMessage(message);
        return scanner.nextLine();
    }

    public void showGraphicMessage(String message){
        JOptionPane.showMessageDialog(null,message);
    }

    public int readIntGraphics(String message){
        return Integer.parseInt(JOptionPane.showInputDialog(message));
    }

    public String readStringGraphics(String message){
        return JOptionPane.showInputDialog(message);
    }

    public Byte readByteGraphics(String message){
        return Byte.parseByte(JOptionPane.showInputDialog(message));
    }

    public Short readShortGraphics(String message){
        return Short.parseShort(JOptionPane.showInputDialog(message));
    }

    public Double readDoubleGraphics(String message){
        return Double.parseDouble(JOptionPane.showInputDialog(message));
    }

    public Long readLongGraphics(String message){
        return Long.parseLong(JOptionPane.showInputDialog(message));
    }

    public short showMainMenu(){
        String menuText = "💵   Welcome to your favorite tax calculator   💵 \n"
                        + "1. Add land \n"
                        + "2. Add range commercial\n"
                        + "3. Add rate residential\n"
                        + "4. Search property\n"
                        + "5. Consult tax\n"
                        + "6. Modify Ranges\n"
                        + "7. Show Ranges/Rates\n"
                        + "8. Refresh\n"
                        + "9. Exit\n";
        return readShortGraphics(menuText);
    }

    public byte showModifyRangeMenu(){
        String menuText = "$  MODIFY RANGE RESIDENTIAL  $\n"
                + "1. Initial value\n"
                + "2. Final Value\n"
                + "3. Rate\n";
        return readByteGraphics(menuText);
    }

    public byte showTypeModifyRangeMenu(){
        String menuText = "$  MODIFY RANGE $\n"
                + "1. Residential Rate\n"
                + "2. Commercial Rate\n"
                + "3. Prompt Payment Rate\n"
                + "4. Exempt or excluded Rate\n";
        return readByteGraphics(menuText);
    }

    public byte showStratumMenu(){
        String menuText = "$   STRATUM   $\n"
                + "1. One\n"
                + "2. Two\n"
                + "3. Tree\n"
                + "4. four\n"
                + "5. Five\n"
                + "6. Six\n";
        return readByteGraphics(menuText);
    }

    public byte showUseMenu(){
        String menuText = "$   STRATUM   $\n"
                + "1. Commercial\n"
                + "2. Residential\n";
        return readByteGraphics(menuText);
    }

    public byte showStatusMenu(){
        String menuText = "$   STATUS   $\n"
                + "1. Exempt\n"
                + "2. Excluded\n"
                + "3. None\n";
        return readByteGraphics(menuText);
    }

    public byte showOnDateMenu(){
        String menuText = "$   PROMPT PAYMENT   $\n"
                + "1. Yes\n"
                + "2. No\n";
        return readByteGraphics(menuText);
    }

    public byte showTypeRangeMenu(){
        String menuText = "$   TYPE RANGE/RATE   $\n"
                + "1. Residential\n"
                + "2. Commercial\n"
                + "3. Exempt or excluded\n"
                + "4. Prompt Payment\n";
        return readByteGraphics(menuText);
    }

}
