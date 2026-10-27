package colony_facilities;

import supply.Equipment;
import supply.Resources;

public class Storage extends Facilities {
    private double humidity;
    private double temp;
    String storageNum;

    public Storage(double humidity, double temp, String storageNum, String location, int capacity, boolean opStatus, Resources resource, Equipment equipment) {
        super(location, capacity, opStatus, resource, equipment);
        this.humidity = humidity;
        this.temp = temp;
        this.storageNum = storageNum;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getTemp() {
        return temp;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }
    
    public void storeResources(String name,int amount) {
        if(!opStatus) {
            System.out.println("Storage Unit "+storageNum+" is Inoperable!");
            return;
        }
        if(amount <= 0 || name == null || name.isBlank()) {
            System.out.println("Invalid Input!");
            return;
        }
        if(resource == null) {
            System.out.println("Storage Failed: resources cannot be found!");
            return;
        }
        int total = resource.getBattery_units_count() + resource.getFood_count() + resource.getFuel_count() + resource.getH2O_count() + resource.getMedicalSup_count() + resource.getO2_count() + resource.getSpare_count();
        if(total + amount > capacity) {
            System.out.println("Insufficient Storage Space!");
            return;
        } 
        addResources(name, amount);
    }
    
    public void retrieveResources(String name,int amount) {
        if(!opStatus) {
            System.out.println("Storage Unit "+storageNum+" is Inoperable!");
            return;
        }
        if(amount <= 0 || name == null || name.isBlank()) {
            System.out.println("Invalid Input!");
            return;
        }
        if(resource == null) {
            System.out.println("Retrieval Failed: resources cannot be found!");
            return;
        }
        removeResources(name, amount);
    }
    
    public void checkHumidity() {
        if(!opStatus) {
            System.out.println("Storage Unit "+storageNum+" is Inoperable!");
            return;
        }
        if(humidity > 70) {
            System.out.println("WARNING!! Humidity Level Too High!");
        } else if (humidity < 20) {
            System.out.println("WARNING!! Humidity Level Too Low!");
        } else {
            System.out.println("Humidity Level within Optimal Range.");
        }
    }
    
    public void checkTemp() {
        if(!opStatus) {
            System.out.println("Storage Unit "+storageNum+" is Inoperable!");
            return;
        }
        if(temp > 25) {
            System.out.println("WARNING!! Temperature Level Too High!");
        } else if (temp < -18) {
            System.out.println("WARNING!! Temperature Level Too Low!");
        } else {
            System.out.println("Temperature Level within Optimal Range.");
        }
    }
    
    public void checkInventory() {
        if(!opStatus) {
            System.out.println("Storage Unit "+storageNum+" is Inoperable!");
            return;
        }
        if(resource == null) {
            System.out.println("Inventory Check Failed! Resources Inaccessible.");
            return;
        }
        int total = resource.getBattery_units_count() + resource.getFood_count() + resource.getFuel_count() + resource.getH2O_count() + resource.getMedicalSup_count() + resource.getO2_count() + resource.getSpare_count();
        System.out.println("Inventory Status Report:");
        resource.displayResources();
        System.out.println("Total Stored Items: "+total);
        if(total > capacity) {
            System.out.println("Storage Unit is Overloaded!!");
        } else {
            System.out.println("Available Storage Space: "+(capacity - total));
        }
    }
    
}
