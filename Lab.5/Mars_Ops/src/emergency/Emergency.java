package emergency;

import colony_facilities.*;
import Personnel.*;
import robots.*;
import main.*;
import supply.*;

public class Emergency {
    private String type;
    private String location;
    private boolean solved;
    private MarsOps colony;

    public Emergency(String type, String location, MarsOps colony) {
        this.type = type;
        this.location = location;
        this.colony = colony;
        this.solved = false;
    }
    
    public boolean isSolved() {
        return solved;
    }
    
    public void roverBreakDown(ExplorationRover rover, Engineer eng, RepairRobot robot, Resources reqResources) {
        if(!rover.getDamageStatus()) {
            System.out.println("Emergency Failed! Rover is not damaged.");
            return;
        }
        if(eng == null || !eng.available() || robot == null || !robot.isAvailable()) {
            System.out.println("Emergency Unresolved! Required Responders (engineer and repair robot).");
            return;
        }
        int reqParts = (reqResources != null) ? reqResources.getSpare_count() : 0;
        int reqFuel = (reqResources != null) ? reqResources.getFuel_count() : 0;
        if(colony.getTotalResources("parts") < reqParts || colony.getTotalResources("fuel") < reqFuel) {
            System.out.println("Unsolved Emergency! Insufficient Resources.");
            return;
        }
        boolean success = true;
        if(reqParts > 0) {
            success &= colony.consumeResources("parts",reqParts);
        }
        if (reqFuel > 0) {
            success &= colony.consumeResources("fuel", reqFuel);
        }
        if(success) {
            eng.setLocation(rover.getLocation());
            rover.setDamageStatus(false);
            rover.recharge();
            this.solved = true;
            System.out.println("Rover breakdown successfully fixed!");
        } else {
            System.out.println("Emergency Unsolved! Resource allocation failed.");
        }
    }
    
    public void medEmergency(Person patient, MedicalOfficer med, Facilities facility, Resources reqResources, int HP) {
        if(facility != null && !facility.isOpStatus()) {
            System.out.println("Emergency Unresolved! Facility is Inoperational!");
            return;
        }
        if(med == null || !med.available()) {
            System.out.println("Medical Officier Unavailable!");
            return;
        }
        int reqMedKits = (reqResources != null) ? reqResources.getMedicalSup_count() : 0;
        if(colony.getTotalResources("medicine") < reqMedKits) {
            System.out.println("Insufficient Medical Supplies!");
            return;
        }
        boolean success = true;
        if(reqMedKits > 0) {
            success &= colony.consumeResources("medicine",reqMedKits);
        }
        if(success) {
            if(facility != null) {
                med.setLocation(facility.getLocation());
            }
            patient.heal(HP);
            this.solved = true;
            System.out.println("Medical Emergency successfully resolved!");
            System.out.println("Patient "+patient.getName()+" has been treated. Current Health: "+patient.getHealth());
        } else {
            System.out.println("Emergency Unsolved! Resource Allocation Failed!");
        }
    }
}
