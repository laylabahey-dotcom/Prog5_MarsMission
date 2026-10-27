/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Missions;
import robots.*;
import supply.*;
import Personnel.*;
import colony_facilities.*;
import environment.*;
import main.*;

public class Exploration extends Mission{
    private final Robot r;
    private Equipment explorationEquipment[];
    private Communication_Stations comm;
    private Resources expRes[];
    private Person driver;
    private Environment missionEnvironment;
    private MarsOps colony;
   
  
   
   public Exploration(int totalSeconds, Person driver, Robot r, Equipment explorationEquipment[], 
           Communication_Stations comm, Resources expRes[], Environment missionEnvironment, 
           MarsOps colony){ 
       
       super(totalSeconds, colony);  
       this.r = r;
       if (!(r instanceof ExplorationRover) && !(r instanceof Drone)) {
            throw new IllegalArgumentException("Mission must have an exploration rover or a drone!");
        }
       if(r instanceof ExplorationRover){
           System.out.println("Conducting land exploration with Exploration Rover!");
       }
       if(r instanceof Drone){
           System.out.println("Conducting areial exploration with a drone!");
       }
       
       this.missionEnvironment = missionEnvironment;
       this.driver = driver;
       people.add(driver);
       this.explorationEquipment = explorationEquipment;
       this.comm = comm;
       this.expRes = expRes;
      
   }
   
   //for initial checks
   @Override
    public String requiredSkill() {
        return "Exploration";
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
        if(driver.getHealth() > 50 && driver.getFatigueLevel() < 80){
            System.out.println("Engineer is healthy and rested!");
        }
        else{
            this.unsuitableAgent();
        }
    }

    @Override
    public void personnelavailabilityCheck() {
        if(driver.available()){
            System.out.println("Driver is available!");
        }
        else{
            this.unavailablePersonnel();
        }
    }

     @Override
    public void resourceCheck() {
        if (expRes == null || expRes.length == 0) {
            System.out.println("WARNING: No resources assigned to this exploration!");
            insufficientResources();
        }
        int reqParts = 0, reqBattery = 0;
        for(Resources resource : expRes) {
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
        System.out.println("Resources are sufficient!");
    }

    @Override
    public void equipmentCheck() {
        if (explorationEquipment == null || explorationEquipment.length == 0) {
            equipmentFailure();
            failMission("Equipment is insufficient!");
            return;
        }
        for(Equipment e: explorationEquipment){
            if(e.isDamaged()){ 
                equipmentFailure(); 
                failMission("Equipment is damaged!");
                return;
            }
        }
        System.out.println("Equipment is ready!");
    }

    @Override
    public void roboticsCheck() {
        if (r.getBatteryLevel() <= 20) {
            insufficientBattery();
            return;
        }
        if(r.getDamageStatus()){
            damagedRobot();
            return;
        }
        if(!(r.isAvailable())){
            robotUnavailable();
            return;
        }
        System.out.println("Robot battery is sufficient, available and undamaged!");  
    }

    @Override
    public void communicationCheck() {
        if (comm == null || !comm.checkSignal()) {
            communicationDisruption();
            return;
        }
        System.out.println("Communication signal is stable!");
    }

    @Override
    public void environmentCheck() {
        boolean canStart = (r instanceof Drone)
                ? missionEnvironment.isDustStorm()
                : missionEnvironment.isCommDisruption();

        if (!canStart) {
            throw new IllegalStateException("Environment prevents this mission from starting!");
        }
        System.out.println("Environmental conditions are suitable!");
    }

    //for during mission check
    @Override
    public void checkMissionContinues() {
        if (!driver.canPerform(requiredSkill()))
            failMission("Agent is unsuitable! Does not have required skill.");
        if (!(driver.getHealth() > 50 && driver.getFatigueLevel() < 80))
            failMission("Agent is unhealthy or fatigued!");
        if(!(driver.available()))
            failMission("Agent is unavailable.");
        for(Equipment e: explorationEquipment){
            if(e.isDamaged())
                failMission("Equipment is damaged!");
        }
        if(!(r.getBatteryLevel() <= 20))
            failMission("Bot's battery is running low!");
        if(!r.getDamageStatus())
            failMission("Bot sustained damage!");
        if (comm == null || !comm.checkSignal()) {
            failMission("Communication disruption during mission!");
        }
        boolean environmentSuitable = (r instanceof Drone)
        ? missionEnvironment.isDustStorm()
        : missionEnvironment.isCommDisruption();

        if (!environmentSuitable) {
            failMission("Environmental conditions are unsuitable!");
            return;
        }
}
 
 
}


