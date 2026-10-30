/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Missions;

import colony_facilities.*;
import Personnel.*;
import supply.*;
import main.*;

public class Research extends Mission {
    private Person scientist;
    private Facilities lab;
    private Equipment scientificEquipment[];
    private Resources scientificResources[];
    private MarsOps colony;

    public Research(int totalSeconds, Person scientist,
            Equipment scientificEquipment[],
            Resources scientificResources[], Facilities lab, MarsOps colony) {

        super(totalSeconds, colony);
        
        
        this.scientist = scientist;
        this.people.add(scientist);
        this.scientificEquipment = scientificEquipment;
        this.scientificResources = scientificResources;
        if(!(lab instanceof Laboratory)){
            throw new IllegalStateException("Research must be conducted in a laboratory!");
        }
        this.lab = lab;
        this.colony = colony;
    }

    // for initial checks
    @Override
    public String requiredSkill() {
        return "Research";
    }

    @Override
    public void personnelCapabilityCheck() {
        for (Person p : people) {
            if (!p.canPerform(requiredSkill())) {
                unsuitableAgent();
                return;
            }
        }
        System.out.println("All assigned scientists are qualified!");
    }

    @Override
    public void personnelPerformanceCheck() {
        for (Person p : people) {
            if (!(p.getHealth() > 50 && p.getFatigueLevel() < 80)) {
                unsuitableAgent();
                return;
            }
        }
        System.out.println("Scientists are healthy and rested!");
    }

    @Override
    public void personnelavailabilityCheck() {
        for (Person p : people) {
            if (!p.available()) {
                unavailablePersonnel();
                return;
            }
        }
        System.out.println("Scientists are available!");
    }

    @Override
    public void resourceCheck() {
        if (lab == null || !lab.isOpStatus()) {
            throw new IllegalStateException("Mission FAILED! Laboratory is not operational!");
        }
        if (scientificEquipment == null || scientificEquipment.length == 0) {
            equipmentFailure();
            return;
        }
        for (Equipment e : scientificEquipment) {
            if (e == null || e.isDamaged()) {
                equipmentFailure();
                return;
            }
        }
        int reqParts = 0, reqBattery = 0;
        for(Resources resource : scientificResources) {
            if(resource != null) {
                reqParts += resource.getSpare_count();
                reqBattery += resource.getBattery_units_count();
            }
        }
        if(colony.getTotalResources("parts") < reqParts || colony.getTotalResources("battery") < reqBattery) {
            insufficientResources();
        }
        boolean success = true;
        if(reqParts > 0) {
            success &= colony.consumeResources("parts",reqParts);
        }
        if(reqBattery > 0) {
            success &= colony.consumeResources("battery",reqBattery);
        }
        if(!success) {
            insufficientResources();
        }
        System.out.println("Scientific equipment is ready!");
}

    @Override
    public void equipmentCheck() {
        if (lab == null || !lab.isOpStatus()) {
            throw new IllegalStateException("Mission FAILED! Laboratory is not operational!");
        }
        if (scientificEquipment == null || scientificEquipment.length == 0) {
            equipmentFailure();
            return;
        }
        for (Equipment e : scientificEquipment) {
            if (e.isDamaged()) {
                equipmentFailure();
                return;
            }
        }
        System.out.println("Equipment is ready!");
    }

    @Override
    public void roboticsCheck() {
       //
    }

    @Override
    public void communicationCheck() {
       //
    }

    @Override
    public void environmentCheck() {
        //
    }

    // for during-mission checks
    @Override
    public void checkMissionContinues() {
        for (Person p : people) {
            if (!(p.getHealth() > 50 && p.getFatigueLevel() < 80)) {
                failMission("Scientist is unhealthy or fatigued!");
                return;
            }
        }
        if (lab == null || !lab.isOpStatus()) {
            failMission("Laboratory is no longer operational!");
            return;
        }
        for (Equipment e : scientificEquipment) {
            if (e == null || e.isDamaged()) {
                failMission("Equipment is damaged!");
                return;
    }
}
}

    @Override
    public void CheckForEmergency() {
        if (lab != null && !lab.isSafeForMission()) {
            if (lab.hasEmergency()) {
                emergency.Emergency active = lab.getActiveEmergency();
                String emergencyType = active != null ? active.getType() : "Unknown Surface Emergency";
                
                if (active instanceof emergency.Fire) {
                    throw new IllegalStateException(
                        "Research FAILED! Fire active in Lab. Research suspended."
                    );
                } else {
                    throw new IllegalStateException(
                        "Research FAILED! Lab hazard [" + emergencyType + "] active at " + lab.getLocation() + "."
                    );
                }
            }
            throw new IllegalStateException("Lab FAILED! Lab is currently not operational or damaged.");
        }
        System.out.println("Emergency check passed.");
   }
}
    