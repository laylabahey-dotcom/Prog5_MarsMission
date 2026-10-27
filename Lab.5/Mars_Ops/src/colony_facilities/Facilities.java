package colony_facilities;

import supply.Equipment;
import supply.Resources;

public class Facilities {
    protected String location;
    protected int capacity;
    protected boolean opStatus;
    protected Resources resource;
    protected Equipment equipment;

    public Facilities(String location, int capacity, boolean opStatus, Resources resource, Equipment equipment) {
        this.location = location;
        this.capacity = capacity;
        this.opStatus = opStatus;
        this.resource = resource;
        this.equipment = equipment;
    }

    public String getLocation() {
        return location;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isOpStatus() {
        return opStatus;
    }
    public void setOpStatus(boolean status){
        this.opStatus = status;
        
    }
    public Resources getResource() {
        return resource;
    }

    public Equipment getEquipment() {
        return equipment;
    }
    
    public boolean addResources(String name, int quantity) {
        if(opStatus) {
            if(quantity <= 0 || name == null || name.isBlank()) {
                System.out.println("Invalid Input!");
                return false;
            } else if(name.equalsIgnoreCase("oxygen")) {
                if(resource.oxygenAddition(quantity)) {
                    System.out.println("Oxygen supply increased successfully!");
                    return true;
                } else {
                    System.out.println("Invalid amount! Maximum limit reached.");
                    return false;
                }
            } else if(name.equalsIgnoreCase("water")) {
                if(resource.waterAddition(quantity)) {
                    System.out.println("Water supply increased successfully!");
                    return true;
                } else {
                    System.out.println("Invalid amount! Maximum limit reached.");
                    return false;
                }
            } else if(name.equalsIgnoreCase("food")) {
                if(resource.foodAddition(quantity)) {
                    System.out.println("Food supply increased successfully!");
                    return true;
                } else {
                    System.out.println("Invalid amount! Maximum limit reached.");
                    return false;
                }
            } else if(name.equalsIgnoreCase("fuel")) {
                if(resource.fuelAddition(quantity)) {
                    System.out.println("Fuel supply increased successfully!");
                    return true;
                } else {
                    System.out.println("Invalid amount! Maximum limit reached.");
                    return false;
                }
            } else if(name.equalsIgnoreCase("battery")) {
                if(resource.batteryAddition(quantity)) {
                    System.out.println("Battery supply increased successfully!");
                    return true;
                } else {
                    System.out.println("Invalid amount! Maximum limit reached.");
                    return false;
                }
            } else if(name.equalsIgnoreCase("medicine")) {
                if(resource.medAddition(quantity)) {
                    System.out.println("Medical supply increased successfully!");
                    return true;
                } else {
                    System.out.println("Invalid amount! Maximum limit reached.");
                    return false;
                }
            } else if(name.equalsIgnoreCase("parts")) {
                if(resource.spareAddition(quantity)) {
                    System.out.println("Spare parts supply increased successfully!");
                    return true;
                } else {
                    System.out.println("Invalid amount! Maximum limit reached.");
                    return false;
                }
            } else {
                System.out.println("Invalid Input! Resource Not Found.");
                return false;
            }
        } else {
            System.out.println("Addition Failed! Facility is out of operation.");
            return false;
        }
    }
    
    public boolean removeResources(String name, int quantity) {
        if(opStatus) {
            if(quantity <= 0 || name == null || name.isBlank()) {
                System.out.println("Invalid Input!");
                return false;
            } else if(name.equalsIgnoreCase("oxygen")) {
                if(resource.oxygenSub(quantity)) {
                    System.out.println("Oxygen supply decreased successfully!");
                    return true;
                } else {
                    System.out.println("Invalid amount! Insufficient Oxygen.");
                    return false;
                }
            } else if(name.equalsIgnoreCase("water")) {
                if(resource.waterSub(quantity)) {
                    System.out.println("Water supply decreased successfully!");
                    return true;
                } else {
                    System.out.println("Invalid amount! Insufficient Water.");
                    return false;
                }
            } else if(name.equalsIgnoreCase("food")) {
                if(resource.foodSub(quantity)) {
                    System.out.println("Food supply decreased successfully!");
                    return true;
                } else {
                    System.out.println("Invalid amount! Insufficient Food.");
                    return false;
                }
            } else if(name.equalsIgnoreCase("fuel")) {
                if(resource.fuelSub(quantity)) {
                    System.out.println("Fuel supply decreased successfully!");
                    return true;
                } else {
                    System.out.println("Invalid amount! Insufficient Fuel.");
                    return false;
                }
            } else if(name.equalsIgnoreCase("battery")) {
                if(resource.batterySub(quantity)) {
                    System.out.println("Battery supply decreased successfully!");
                    return true;
                } else {
                    System.out.println("Invalid amount! Insufficient Battery Units.");
                    return false;
                }
            } else if(name.equalsIgnoreCase("medicine")) {
                if(resource.medSub(quantity)) {
                    System.out.println("Medical supply decreased successfully!");
                    return true;
                } else {
                    System.out.println("Invalid amount! Insufficient Medical Kits.");
                    return false;
                }
            } else if(name.equalsIgnoreCase("parts")) {
                if(resource.spareSub(quantity)) {
                    System.out.println("Spare parts supply decreased successfully!");
                    return true;
                } else {
                    System.out.println("Invalid amount! Insufficient Spare Parts.");
                    return false;
                }
            } else {
                System.out.println("Invalid Input! Resource Not Found.");
                return false;
            }
        } else {
            System.out.println("Subtraction Failed! Facility is out of operation.");
            return false;
        }
    }
    
    public void turnOn() {
        if(!opStatus) {
            if(capacity >= 0 && resource != null && equipment != null) {
                opStatus = true;
                System.out.println("Facility turned on successfully!");
            } else {
                System.out.println("Cannot turn on facility!");
            }
        } else {
            System.out.println("Facility is already operational.");
        }
    }
    
    public void turnOff() {
        if(opStatus) {
            opStatus = false;
            System.out.println("Facility Shut Down Successfully!");
        } else {
            System.out.println("Facility is already inoperable!");
        }
    }
}
