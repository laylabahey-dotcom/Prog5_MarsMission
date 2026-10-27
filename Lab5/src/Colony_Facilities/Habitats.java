package Colony_Facilities;

import supply.Equipment;
import supply.Resources;

public class Habitats extends Facilities {
    private int residentNum;
    String habitatNum;

    public Habitats(int residentNum, String habitatNum, String location, int capacity, boolean opStatus, Resources resource, Equipment equipment) {
        super(location, capacity, opStatus, resource, equipment);
        this.habitatNum = habitatNum;
        if(residentNum > 0) {
            this.residentNum = residentNum;
        } else {
            this.residentNum = 0;
        }
    }

    public int getResidentNum() {
        return residentNum;
    }

    public void setResidentNum(int residentNum) {
        if(residentNum > 0) {
            this.residentNum = residentNum;
        } else {
            System.out.println("Invalid Number!");
        }
    }
    
    public void checkLifeSupport() {
        if(!opStatus) {
            System.out.println("Habitat "+habitatNum+" is Inoperable!");
            return;
        }
        if(resource == null) {
            System.out.println("Life Support Check Failed: No Resources Assigned");
            turnOff();
            return;
        }
        if(resource.getO2_count() > 0) {
            System.out.println("Enough Oxygen Units");
        } else {
            System.out.println("Insufficient Oxygen!!");
            turnOff();
            return;
        }
        if(resource.getH2O_count() > 0) {
            System.out.println("Enough Water Tanks");
        } else {
            System.out.println("Insufficient Water!!");
            turnOff();
            return;
        }
        if(resource.getFood_count() > 0) {
            System.out.println("Enough Food Supply");
        } else {
            System.out.println("Insufficient Food Supply!!");
            turnOff();
            return;
        }
        if(resource.getMedicalSup_count() > 0) {
            System.out.println("Enough Medical Supplies");
        } else {
            System.out.println("Insufficient Medical Supplies!!");
            turnOff();
        }
    }
    
    public void consumeOxygen(int amount) {
        if(removeResources("oxygen",amount)) {
            System.out.println(amount+" oxygen tanks were consumed.");
            System.out.println("Remaining Oxygen Units: "+resource.getO2_count());
        } else {
            System.out.println("Consumption Failed!");
            System.out.println("Current Oxygen Units: "+resource.getO2_count());
        }
    }
    
    public void consumeWater(int amount) {
        if(removeResources("water",amount)) {
            System.out.println(amount+" water tanks were consumed.");
            System.out.println("Remaining Water Tanks: "+resource.getH2O_count());
        } else {
            System.out.println("Consumption Failed!");
            System.out.println("Current Water Tanks: "+resource.getH2O_count());
        }
    }
    
    public void consumeFood(int amount) {
        if(removeResources("food",amount)) {
            System.out.println(amount+" food meal kits were consumed.");
            System.out.println("Remaining Food Kits: "+resource.getFood_count());
        } else {
            System.out.println("Consumption Failed!");
            System.out.println("Current Food Kits: "+resource.getFood_count());
        }
    }
    
    public void consumeMedicalSup(int amount) {
        if(removeResources("medicine",amount)) {
            System.out.println(amount+" medical kits were consumed.");
            System.out.println("Remaining Medical Kits: "+resource.getMedicalSup_count());
        } else {
            System.out.println("Consumption Failed!");
            System.out.println("Current Medical Kits: "+resource.getMedicalSup_count());
        }
    }
    
    public void checkOccupancy() {
        if(!opStatus) {
            System.out.println("Habitat "+habitatNum+" is Inoperable!");
            return;
        }
        if(residentNum > capacity) {
            System.out.println("WARNING: Habitat Overcrowded!!");
        } else if(residentNum == capacity) {
            System.out.println("Habitat "+habitatNum+" is at maximum capacity.");
        } else {
            System.out.println("Habitat "+habitatNum+" has room.");
            System.out.println("Available Capacity: "+(capacity - residentNum));
        }
    }
    
    public void addResidents(int amount) {
        if(!opStatus) {
            System.out.println("Habitat "+habitatNum+" is Inoperable!");
            return;
        }
        if(amount <= 0) {
            System.out.println("Invalid Amount!");
            return;
        }
        if(residentNum + amount > capacity) {
            System.out.println("Insufficient Capacity!");
            System.out.println("Available Capacity: "+(capacity - residentNum));
        } else {
            residentNum += amount;
            System.out.println(amount+" residents have been added successfully!");
            System.out.println("Available Capacity: "+(capacity - residentNum));
        }
    }
    
    public void removeResidents(int amount) {
        if(!opStatus) {
            System.out.println("Habitat "+habitatNum+" is Inoperable!");
            return;
        }
        if(amount <= 0) {
            System.out.println("Invalid Amount!");
            return;
        }
        if(residentNum - amount < 0) {
            System.out.println("Invalid Number!");
            System.out.println("Current Resident: "+residentNum);
        } else {
            residentNum -= amount;
            System.out.println(amount+" residents have been removed successfully!");
            System.out.println("Available Capacity: "+(capacity - residentNum));
        }
    }
    
//    public void checkSafety(){
//        
//    }
}