/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Missions;

import colony_facilities.*;
import environment.*;
import Personnel.Person;
import robots.*;
import supply.*;
import main.*;

public class Repair extends Mission {

    private final Robot repairBot;
    private Equipment explorationEquipment[];
    private Resources expRes[];
    private Person repairman;
    private Facilities repairTarget; // lab or power station
    private MarsOps colony;
    private Communication_Stations comm;
    
    public Repair(int totalTime, Person repairman, Robot repairBot, Equipment repairEquipment[], 
            Resources expRes[], Facilities repairTarget, MarsOps colony, Communication_Stations comm){ 
        
       super(totalTime, colony);  
       this.repairBot = repairBot;
       if (!(repairBot instanceof RepairRobot)) {
            throw new IllegalArgumentException("Mission must have a repair rover!");
       } else if (repairBot instanceof RepairRobot) {
            System.out.println("Conducting reparations in facilities!");
       }

       this.repairman = repairman;
       people.add(repairman);
       this.explorationEquipment = repairEquipment;
       this.expRes = expRes;
       this.repairTarget = repairTarget;
       if (repairTarget == null) {
            throw new IllegalArgumentException("A repair mission must have a target facility.");
       }
       if(repairTarget instanceof MarsSurface){
           throw new IllegalArgumentException("Repairs cannot be conducted on martian Surface!");
       }
       this.comm = comm;
   }
   
   //for initial checks
   @Override
    public String requiredSkill() {
        return "Repair";
    }

    @Override
    public void personnelCapabilityCheck() {
        for (Person p : people) {
            if (!p.canPerform(requiredSkill())) {
                unsuitableAgent();
                return;
            }
        }
        System.out.println("All assigned personnel are qualified!");
    }

    @Override
    public void personnelPerformanceCheck() {
        if (repairman.getHealth() > 50 && repairman.getFatigueLevel() < 80) {
            System.out.println("Engineer is healthy and rested!");
        } else {
            this.unsuitableAgent();
        }
    }

    @Override
    public void personnelavailabilityCheck() {
        if (repairman.available()) {
            System.out.println("Repairman (Engineer) is available!");
        } else {
            this.unavailablePersonnel();
        }
    }

    @Override
    public void resourceCheck() {
        if (expRes == null || expRes.length == 0) {
            insufficientResources();
            return;
        }
        int reqParts = 0, reqBattery = 0;
        for (Resources resource : expRes) {
            if (resource != null) {
                reqParts += resource.getSpare_count();
                reqBattery += resource.getBattery_units_count();
            }
        }
        if (colony.getTotalResources("parts") < reqParts || colony.getTotalResources("battery") < reqBattery) {
            insufficientResources();
        }
        boolean success = true;
        if (reqParts > 0) {
            success &= colony.consumeResources("parts", reqParts);
        }
        if (reqBattery > 0) {
            success &= colony.consumeResources("battery", reqBattery);
        }
        if (!success) {
            insufficientResources();
        }
        System.out.println("Resources are sufficient!");
    }

    @Override
    public void equipmentCheck() {
        if (explorationEquipment == null || explorationEquipment.length == 0) {
            equipmentFailure();
            return;
        }
        for (Equipment e : explorationEquipment) {
            if (e.isDamaged()) {
                equipmentFailure();
                return;
            }
        }
        System.out.println("Equipment is ready!");
    }

    @Override
    public void roboticsCheck() {
        if (repairBot.getBatteryLevel() <= 20) {
            insufficientBattery();
            return;
        }
        if (repairBot.getDamageStatus()) {
            damagedRobot();
            return;
        }
        if (!(repairBot.isAvailable())) {
            robotUnavailable();
            return;
        }
        System.out.println("Robot battery is sufficient, available and undamaged!");
    }

    @Override
    public void communicationCheck() {
        if (comm != null && !comm.checkSignal()) {
            communicationDisruption();
            return;
        }
        System.out.println("Communication signal is stable!");
    }

    @Override
    public void environmentCheck() {
        System.out.println("Environmental conditions are suitable!");
    }

    // perform repair
    public boolean performRepair() {
        Equipment facilityEquipment = repairTarget.getEquipment();

        boolean facilityNeedsRepair = !repairTarget.isOpStatus();
        boolean equipmentNeedsRepair = facilityEquipment != null && facilityEquipment.isDamaged();

        if (!facilityNeedsRepair && !equipmentNeedsRepair) {
            System.out.println("The facility and its equipment do not need repairs.");
            return false;
        }

        if (equipmentNeedsRepair) {
            System.out.println("Repairing the facility's equipment...");
            facilityEquipment.repairEquipment();
        }

        if (facilityNeedsRepair) {
            System.out.println("Restoring the facility...");
            repairTarget.turnOn();

            if (!repairTarget.isOpStatus()) {
                System.out.println("Facility repair failed.");
                return false;
            }
        }

        System.out.println("Repair operation completed successfully!");
        return true;
    }

    // for during mission check
    @Override
    public void checkMissionContinues() {
        if (!repairman.canPerform(requiredSkill())) {
            failMission("Agent is unsuitable! Does not have required skill.");
        }
        if (!(repairman.getHealth() > 50 && repairman.getFatigueLevel() < 80)) {
            failMission("Agent is unhealthy or fatigued!");
        }
        if (!(repairman.available())) {
            failMission("Agent is unavailable.");
        }
        for (Equipment e : explorationEquipment) {
            if (e.isDamaged()) {
                failMission("Equipment is damaged!");
            }
        }
        if (!(repairBot.getBatteryLevel() <= 20)) {
            failMission("Bot's battery is running low!");
        }
        if (!repairBot.getDamageStatus()) {
            failMission("Bot sustained damage!");
        }
    } 

    @Override
    public void CheckForEmergency() {
        if (repairTarget != null && !repairTarget.isSafeForMission()) {
            if (repairTarget.hasEmergency()) {
                emergency.Emergency emergencyObj = repairTarget.getActiveEmergency();
                String emergencyType = emergencyObj != null ? emergencyObj.getType() : "Active Facility Emergency";
                
                if (emergencyObj instanceof emergency.Fire) {
                    throw new IllegalStateException(
                        "Repair Mission FAILED! Fire emergency detected at target facility [" + repairTarget.getLocation() + "]."
                    );
                } else if (emergencyObj instanceof emergency.OxygenLeak) {
                    throw new IllegalStateException(
                        "Repair Mission FAILED! Oxygen Leak active at habitat [" + repairTarget.getLocation() + "]."
                    );
                } else {
                    throw new IllegalStateException(
                        "Repair Mission FAILED! Target facility [" + repairTarget.getLocation() + "] is unsafe due to: " + emergencyType
                    );
                }
            } else {
                throw new IllegalStateException(
                    "Repair Mission FAILED! Target facility [" + repairTarget.getLocation() + "] is inoperable or damaged."
                );
            }
        }
        
        if (comm != null && !comm.isSafeForMission()) {
            throw new IllegalStateException("Repair Mission FAILED! Communication stations are disrupted.");
        }
        
        System.out.println("Repair emergency check passed: Target facility status is verified.");
    }
}