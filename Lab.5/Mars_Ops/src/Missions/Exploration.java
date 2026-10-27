package Missions;
import robots.*;
import supply.*;
import Personnel.*;
import colony_facilities.*;
import environment.*;

public class Exploration extends Mission{
    private Robot r;
    private Equipment explorationEquipment[];
    private Communication_Stations comm;
    private Resources expRes[];
    private Person driver;
//    private Location l;
   
   
    public Exploration(int totalSeconds, main.MarsOps colony, Person driver, Robot r, Equipment explorationEquipment[], 
           Communication_Stations comm, Resources expRes[], Environment missionEnvironment/*, Location l */){ 

        super(totalSeconds,colony);  
        this.r = r;
        if (!(r instanceof ExplorationRover) && !(r instanceof Drone)) {
             throw new IllegalArgumentException("Mission must have an exploration rover");
         }
        if(r instanceof ExplorationRover){
            System.out.println("Conducting land exploration with Exploration Rover!");
        }
        if(r instanceof Drone){
            System.out.println("Conducting areial exploration with a drone!");
        }

        this.driver = driver;
        if (driver != null) {
         this.people.add(driver);
        }
        
        this.explorationEquipment = explorationEquipment;
        this.comm = comm;
        this.expRes = expRes;
       
        //l = this.l;
    }


    @Override
    public void personnelCapabilityCheck() {
        if(driver instanceof Engineer)
        {System.out.println("Driver is a qualified engineer!");
            driver.canPerform("Driving the robot in an exploration mission.");
        }
        else{
            this.unsuitableAgent();
            }
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
        }
        for(Equipment e: explorationEquipment){
            if(e.isDamaged()){ 
                System.out.println("Equipment is damaged!");
                equipmentFailure(); 
            }
        }
        System.out.println("Equipment is ready!");
    }

    @Override
    public void roboticsCheck() {
        if (r.getBatteryLevel() <= 20) {
            insufficientBattery();
        }
        if(r.getDamageStatus()){
            damagedRobot();
        }
        System.out.println("Robot battery is sufficient and is undamaged!");
//        r.canPerform(Mission);
        
    }

    @Override
    public void communicationCheck() {
        if (comm == null || !comm.checkSignal()) {
            communicationDisruption();
        }
        System.out.println("Communication signal is stable!");
    }

    @Override
    public void environmentCheck() {
//        if (missionEnvironment == null) {   // replace with a real check, e.g. !missionEnvironment.isSafe()
//            environmentalConditions();
//        }
//        System.out.println("Environmental conditions are suitable!");
    }
}
   

