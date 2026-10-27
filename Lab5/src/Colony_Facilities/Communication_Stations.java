package Colony_Facilities;

import supply.Equipment;
import supply.Resources;

public class Communication_Stations extends Facilities {
    private double signalStrength;
    private boolean connection;
    String stationNum;

    public Communication_Stations(double signalStrength, boolean connection, String stationNum, String location, int capacity, boolean opStatus, Resources resource, Equipment equipment) {
        super(location, capacity, opStatus, resource, equipment);        
        this.stationNum = stationNum;
        if(signalStrength >= 0.0 && signalStrength <= 100.0) {
            this.signalStrength = signalStrength;
        } else {
            this.signalStrength = 100;
        }
        this.connection = connection;
    }

    public double getSignalStrength() {
        return signalStrength;
    }

    public boolean isConnection() {
        return connection;
    }

    public void setSignalStrength(double signalStrength) {
        if(signalStrength >= 0.0 && signalStrength <= 100.0) {
            this.signalStrength = signalStrength;
        } else {
            System.out.println("Invalid Signal Strength!");
        }
    }
    
    public void sendMessage(String reciever, String message) {
        if(!opStatus) {
            System.out.println("Communication Station "+stationNum+" is Inoperable!");
            return;
        }
        if(!connection || signalStrength <= 0) {
            System.out.println("Connection Failed!");
            return;
        }
        if(message == null || message.isBlank() || reciever == null || reciever.isBlank()) {
            System.out.println("Invalid Input!");
            return;
        }
        System.out.println("Station "+stationNum+" sent message to "+reciever+": \""+message+"\"");
    }
    
    public void recieveMessage(String sender, String message) {
        if(!opStatus) {
            System.out.println("Communication Station "+stationNum+" is Inoperable!");
            return;
        }
        if(!connection || signalStrength <= 0) {
            System.out.println("Connection Failed!");
            return;
        }
        if(message == null || message.isBlank() || sender == null || sender.isBlank()) {
            System.out.println("Invalid Input!");
            return;
        }
        System.out.println("Station "+stationNum+" recieved message from "+sender+": \""+message+"\"");
    }
    
    public boolean checkSignal() {
        if(!opStatus) {
            System.out.println("Communication Station "+stationNum+" is Inoperable!");
            return false;
        }
        System.out.println("Signal Status Report:");
        System.out.println("Signal Strength: "+signalStrength+"%");
        if(signalStrength < 25) {
            System.out.println("WARNING: Signal Strength Too Weak!!");
            return false;
        } else {
            System.out.println("Signal Stregth Normal!");
            return true;
        }
    }
    
    public void restoreSignal(double amount) {
        if(!opStatus) {
            System.out.println("Communication Station "+stationNum+" is Inoperable!");
            return;
        }
        if(amount <= 0){
            System.out.println("Invalid Input!");
            return;
        }
        signalStrength += amount;
        if(signalStrength > 100) {
            signalStrength = 100;
        }
        connection = true;
        System.out.println("Connection Restored Successfully!");
        System.out.println("Updated Signal Strength: "+signalStrength);
    }
    
    public void checkCommStatus() {
        if(!opStatus) {
            System.out.println("Communication Station "+stationNum+" is Inoperable!");
            return;
        }
        System.out.println("Communication Status Report: ");
        System.out.println("Connection: "+(connection ? "Connected": "Not Connected"));
        System.out.println("Signal Strength: "+signalStrength);
        if(connection && signalStrength >= 25) {
            System.out.println("Fully Operational!");
        } else {
            System.out.println("Distruptions Detected! Operations may fail.");
        }
    }
}
