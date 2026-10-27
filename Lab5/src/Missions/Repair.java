/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Missions;

import Colony_Facilities.*;
import environment.*;
import personnel.Person;
import robotics.*;
import supply.*;


public class Repair extends Mission{
    private final Robot repairBot;
    private Equipment explorationEquipment[];
    private Resources expRes[];
    private Person repairman;
    private Facilities repairTarget;
    
    public Repair(int totalSeconds, Person repairman, Robot repairBot, Equipment explorationEquipment[], 
            Resources expRes[], Facilities repairTarget){ 
       
       super(totalSeconds);  
       this.repairBot = repairBot;
       if (!(repairBot instanceof RepairRobot)) {
            throw new IllegalArgumentException("Mission must have a repair rover!");
        }
       else if(repairBot instanceof RepairRobot){
           System.out.println("Conducting reparations in facilities!");
       }
       
       this.repairman = repairman;
       people.add(repairman);
       this.explorationEquipment = explorationEquipment;
       this.expRes = expRes;
       this.repairTarget = repairTarget;
       if (repairTarget == null) {
            throw new IllegalArgumentException("A repair mission must have a target facility.");
        }
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
        if(repairman.getHealth() > 50 && repairman.getFatigueLevel() < 80){
            System.out.println("Engineer is healthy and rested!");
        }
        else{
            this.unsuitableAgent();
        }
    }

    @Override
    public void personnelavailabilityCheck() {
        if(repairman.available()){
            System.out.println("Repairman (Engineer) is available!");
        }
        else{
            this.unavailablePersonnel();
        }
    }

     @Override
    public void resourceCheck() {
        if (expRes == null || expRes.length == 0) {
            insufficientResources();
            return;
        }
        System.out.println("Resources are sufficient!");
    }

    @Override
    public void equipmentCheck() {
        if (explorationEquipment == null || explorationEquipment.length == 0) {
            equipmentFailure();
            return;
        }
        for(Equipment e: explorationEquipment){
            if(e.isDamaged()){ 
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
        if(repairBot.getDamageStatus()){
            damagedRobot();
            return;
        }
        if(!(repairBot.isAvailable())){
            robotUnavailable();
            return;
        }
        System.out.println("Robot battery is sufficient, available and undamaged!");
        
    }
    
    @Override
    public void communicationCheck() {
        throw new UnsupportedOperationException("Not supported."); 
    }

    @Override
    public void environmentCheck() {
        throw new UnsupportedOperationException("Not supported.");
    }
 
    
    // preform repair
    public boolean performRepair() {

        Equipment facilityEquipment = repairTarget.getEquipment();

        // check if there is anything to repair
        boolean facilityNeedsRepair = !repairTarget.isOpStatus();
        boolean equipmentNeedsRepair =
                facilityEquipment != null && facilityEquipment.isDamaged();

        if (!facilityNeedsRepair && !equipmentNeedsRepair) {
            System.out.println("The facility and its equipment do not need repairs.");
            return false;
        }

        // repair the equipment first
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


    //for during mission check
    @Override
    public void checkMissionContinues() {
        if (!repairman.canPerform(requiredSkill()))
            failMission("Agent is unsuitable! Does not have required skill.");
        if (!(repairman.getHealth() > 50 && repairman.getFatigueLevel() < 80))
            failMission("Agent is unhealthy or fatigued!");
        if(!(repairman.available()))
            failMission("Agent is unavailable.");
        for(Equipment e: explorationEquipment){
            if(e.isDamaged())
                failMission("Equipment is damaged!");
        }
        if(!(repairBot.getBatteryLevel() <= 20))
            failMission("Bot's battery is running low!");
        if(!repairBot.getDamageStatus())
            failMission("Bot sustained damage!");
}


}


