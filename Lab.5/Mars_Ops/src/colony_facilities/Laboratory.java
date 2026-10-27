package colony_facilities;

import supply.Equipment;
import supply.Resources;

public class Laboratory extends Facilities {
    private int scientistNum;
    String labNum;

    public Laboratory(int scientistNum, String labNum, String location, int capacity, boolean opStatus, Resources resource, Equipment equipment) {
        super(location, capacity, opStatus, resource, equipment);
        if(scientistNum > 0) {
            this.scientistNum = scientistNum;
        } else {
            this.scientistNum = 0;
        }
        this.labNum = labNum;
    }

    public int getScientistNum() {
        return scientistNum;
    }

    public void setScientistNum(int scientistNum) {
        if(scientistNum > 0) {
            this.scientistNum = scientistNum;
        } else {
            System.out.println("Invalid Number!");
        }
    }
    
    public void startExp(String name, int amount) {
        if(!opStatus) {
            System.out.println("Lab "+labNum+" is Inoperable!");
            return;
        }
        if(scientistNum <= 0) {
            System.out.println("Start Failed! No scientists assigned to this lab!");
            return;
        }
        //idk (T-T)
    }
    
    public void addScientists(int num) {
        if(!opStatus) {
            System.out.println("Lab "+labNum+" is Inoperable!");
            return;
        }
        if(num <= 0) {
            System.out.println("Invalid Input!");
            return;
        }
        if(scientistNum + num > capacity) {
            System.out.println("Lab "+labNum+" cannot accept all scientist.");
            System.out.println("Available Capacity: "+(capacity - scientistNum));
            return;
        }
        scientistNum += num;
        System.out.println("Scientists Assigned Successfully!");
        System.out.println("Available Capacity: "+(capacity - scientistNum));
    }
    
    public void removeScientists(int num) {
        if(!opStatus) {
            System.out.println("Lab "+labNum+" is Inoperable!");
            return;
        }
        if(num <= 0) {
            System.out.println("Invalid Input!");
            return;
        }
        if(scientistNum - num < 0) {
            System.out.println("Lab "+labNum+" doesn't contain all these scientist.");
            System.out.println("Current Scientist Number: "+scientistNum);
            return;
        }
        scientistNum -= num;
        System.out.println("Scientists Removed Successfully!");
        System.out.println("Current Scientist Number: "+scientistNum);
    }
    
    public void stopExp() {
        if(!opStatus) {
            System.out.println("Lab "+labNum+" is Inoperable!");
            return;
        }
        System.out.println("Experiments halted in Lab "+labNum+". All active procedures safely terminated.");
    }
    
    public void checkEquipment() {
        if(!opStatus) {
            System.out.println("Lab "+labNum+" is Inoperable!");
            return;
        }
        if(equipment == null) {
            System.out.println("Check Failed! No equipments assigned to lab.");
            return;
        }
        System.out.println("Equipment Status Report:");
        //equipments display
    }
    
    public void checkResources() {
        if(!opStatus) {
            System.out.println("Lab "+labNum+" is Inoperable!");
            return;
        }
        if(resource == null) {
            System.out.println("Resources Check Failed! Resources Inaccessible.");
            return;
        }
        System.out.println("Resources Status Report:");
        System.out.println("1) Fuel = "+resource.getFuel_count()+" tanks.");
        System.out.println("2) Battery Units = "+resource.getBattery_units_count());
        System.out.println("3) Medical Supplies = "+resource.getMedicalSup_count()+" kits");
        System.out.println("4) Spare Parts = "+resource.getSpare_count()+" parts");
    }
    
    public void useFuel(int amount) {
        if(!opStatus) {
            System.out.println("Lab "+labNum+" is Inoperable!");
            return;
        }
        if(removeResources("fuel",amount)) {
            System.out.println(amount+" units of fuel used in lab "+labNum);
            System.out.println("Remaining Fuel Tanks: "+resource.getFuel_count());
        } else {
            System.out.println("Fuel Consumption Failed!");
            System.out.println("Current Fuel Tanks' Number: "+resource.getFuel_count());
        }
    }
    
    public void useBattery(int amount) {
        if(!opStatus) {
            System.out.println("Lab "+labNum+" is Inoperable!");
            return;
        }
        if(removeResources("battery",amount)) {
            System.out.println(amount+" battery units used in lab "+labNum);
            System.out.println("Remaining Battery Units: "+resource.getBattery_units_count());
        } else {
            System.out.println("Battery Usage Failed!");
            System.out.println("Current Battery Units' Number: "+resource.getBattery_units_count());
        }
    }
    
    public void useMed(int amount) {
        if(!opStatus) {
            System.out.println("Lab "+labNum+" is Inoperable!");
            return;
        }
        if(removeResources("medicine",amount)) {
            System.out.println(amount+" units of medical equipment used in lab "+labNum);
            System.out.println("Remaining Medical Kits: "+resource.getMedicalSup_count());
        } else {
            System.out.println("Medical Kits Consumption Failed!");
            System.out.println("Current Medical Kits' Number: "+resource.getMedicalSup_count());
        }
    }
    
    public void useSpare(int amount) {
        if(!opStatus) {
            System.out.println("Lab "+labNum+" is Inoperable!");
            return;
        }
        if(removeResources("parts",amount)) {
            System.out.println(amount+" spare parts used in lab "+labNum);
            System.out.println("Remaining Spare Parts: "+resource.getSpare_count());
        } else {
            System.out.println("Spare Part Usage Failed!");
            System.out.println("Current Spare Parts' Number: "+resource.getSpare_count());
        }
    }
    
    public void addFuel(int amount) {
        if(!opStatus) {
            System.out.println("Lab "+labNum+" is Inoperable!");
            return;
        }
        if(addResources("fuel",amount)) {
            System.out.println(amount+" fuel tanks added to lab "+labNum);
            System.out.println("Updated Fuel Tanks: "+resource.getFuel_count());
        } else {
            System.out.println("Fuel Tanks Addition Failed!");
            System.out.println("Current Fuel Tanks' Number: "+resource.getFuel_count());
        }
    }
    
    public void addBattery(int amount) {
        if(!opStatus) {
            System.out.println("Lab "+labNum+" is Inoperable!");
            return;
        }
        if(addResources("battery",amount)) {
            System.out.println(amount+" battery units added to lab "+labNum);
            System.out.println("Updated Battery Units: "+resource.getBattery_units_count());
        } else {
            System.out.println("Battery Units Addition Failed!");
            System.out.println("Current Battery Units' Number: "+resource.getBattery_units_count());
        }
    }
    
    public void addMed(int amount) {
        if(!opStatus) {
            System.out.println("Lab "+labNum+" is Inoperable!");
            return;
        }
        if(addResources("medicine",amount)) {
            System.out.println(amount+" medical kits added to lab "+labNum);
            System.out.println("Updated Medical Kits: "+resource.getMedicalSup_count());
        } else {
            System.out.println("Medical Kits Addition Failed!");
            System.out.println("Current Medical Kits' Number: "+resource.getMedicalSup_count());
        }
    }
    
    public void addSpare(int amount) {
        if(!opStatus) {
            System.out.println("Lab "+labNum+" is Inoperable!");
            return;
        }
        if(addResources("parts",amount)) {
            System.out.println(amount+" spare parts added to lab "+labNum);
            System.out.println("Updated Spare Parts: "+resource.getSpare_count());
        } else {
            System.out.println("Spare Parts Addition Failed!");
            System.out.println("Current Spare Parts' Number: "+resource.getSpare_count());
        }
    }
    
    public void checkSafety() {
        if(!opStatus) {
            System.out.println("Lab "+labNum+" is Inoperable!");
            return;
        }
        //when emergency class is made
    }
        
}
