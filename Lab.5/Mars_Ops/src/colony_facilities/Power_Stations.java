package colony_facilities;

import supply.Equipment;
import supply.Resources;

public class Power_Stations extends Facilities {
    private int currentPower;
    private int maxPower;
    String stationNum;

    public Power_Stations(int currentPower, int maxPower, String stationNum, String location, int capacity, boolean opStatus, Resources resource, Equipment equipment) {
        super(location, capacity, opStatus, resource, equipment);
        this.stationNum = stationNum;
        if(maxPower > 0) {
            this.maxPower = maxPower;
        } else {
            this.maxPower = 1000;
        }
        if(currentPower > 0 && currentPower <= maxPower) {
            this.currentPower = currentPower;
        } else {
            this.currentPower = 0;
        }
    }

    public int getCurrentPower() {
        return currentPower;
    }

    public int getMaxPower() {
        return maxPower;
    }
    
    public void generatePower(int amount) {
        if(!opStatus) {
            System.out.println("Power Station "+stationNum+" is Inoperable!");
            return;
        }
        if(amount <= 0) {
            System.out.println("Invalid Amount!");
            return;
        }
        if(resource != null && resource.getFuel_count() > 0) {
            removeResources("fuel",1);
        }
        if(currentPower + amount > maxPower) {
            currentPower = maxPower;
            System.out.println("Station Reached Maximum Power!");
        } else {
            currentPower += amount;
            System.out.println(amount+" Watts generated successfully!");
        }
        System.out.println("Current Power Level: "+currentPower+" / "+maxPower);
    }
    
    public void consumePower(int amount) {
        if(!opStatus) {
            System.out.println("Power Station "+stationNum+" is Inoperable!");
            return;
        }
        if(amount <= 0) {
            System.out.println("Invalid Amount!");
            return;
        }
        if(currentPower - amount < 0) {
            System.out.println("Station Has Zero Power!");
            currentPower = 0;
            checkPowerFail();
        } else {
            currentPower -= amount;
            System.out.println(amount+" Watts consumed successfully!");
        }
        System.out.println("Remaining Power Level: "+currentPower+" / "+maxPower);
    }
    
    public void checkPowerLevel() {
        if(!opStatus) {
            System.out.println("Power Station "+stationNum+" is Inoperable!");
            return;
        }
        System.out.println("Power Level Report:");
        System.out.println("Current Power: "+currentPower+" / "+maxPower);
        if(currentPower == 0) {
            System.out.println("WARNING!! Power Outage Detected!");
        } else if(currentPower < 100) {
            System.out.println("WARNING!! Power Level Too Low!");
        } else {
            System.out.println("Power Level Within Stable Range.");
        }
    }
    
    public void chargeBattery(int amount) {
        if(!opStatus) {
            System.out.println("Power Station "+stationNum+" is Inoperable!");
            return;
        }
        if(resource == null) {
            System.out.println("Battery Charging Failed! No resources Found!");
            return;
        }
        int cost = amount * 10;
        if(currentPower < cost) {
            System.out.println("Insufficient power to charge battery!");
            System.out.println("Required Power: "+cost+" | Available Power: "+currentPower);
            return;
        } 
        if(addResources("battery",amount)) {
            currentPower -= cost;
            System.out.println(amount+" battery units successfully charged and stored.");
            System.out.println("Power Remaining: "+currentPower);
        } else {
            System.out.println("Battery Charging Failed due to Inventory Limits.");
        }
    }
    
    public void checkPowerFail() {
        if(!opStatus) {
            System.out.println("Power Station "+stationNum+" is Inoperable!");
            return;
        }
        if(currentPower <= 0) {
            System.out.println("CRITICAL: Station "+stationNum+" Power Failed!");
        } else {
            System.out.println("Power Operating Normally.");
        }
    }
    
    public void restorePower(int amount) {
        if(!opStatus) {
            System.out.println("Power Station "+stationNum+" is Inoperable!");
            return;
        }
        if(amount <= 0) {
            System.out.println("Invalid Amount!");
            return;
        }
        currentPower += amount;
        if(currentPower > maxPower) {
            currentPower = maxPower;
        }
        System.out.println("Power Restored Successfully!");
        System.out.println("Power Level: "+currentPower);
    }
}
