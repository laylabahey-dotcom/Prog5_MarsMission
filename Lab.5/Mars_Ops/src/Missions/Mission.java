
package Missions;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import robots.*;
import Personnel.*;


public abstract class Mission {
    public static int activeMissionCount = 0;
    protected boolean activeStatus = false; //subclass and package
    protected main.MarsOps colony;
    
    protected final List<Personnel.Person> people = new ArrayList<>();
    
    private final ScheduledExecutorService scheduler;
    private int remainingSeconds;
    
    public Mission(int totalSeconds, main.MarsOps colony){
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.remainingSeconds = totalSeconds;
        this.colony = colony;
    }
    
    private void runChecks() {
        personnelCapabilityCheck();
        personnelPerformanceCheck();
        personnelavailabilityCheck();
        resourceCheck();
        equipmentCheck();
        roboticsCheck();
        communicationCheck();
        environmentCheck();
    }
    
    // start a mission -> check for activity of the mission
    public void startMission(){
        if(activeStatus){
            throw new IllegalStateException("This mission is already assigned.");
        }
        if(activeMissionCount >= 1){
            throw new IllegalStateException("Mission FAILED. Already have an assigned mission. This mission cannot be authorised.");
        }
        for (Personnel.Person p : people) {
                if (!p.available()) {
                    throw new IllegalStateException(p.getName() + " is not available.");
                }
            }
        
        runChecks();
        
        System.out.println("Mission authorised.");
        activeMissionCount++;
        activeStatus = true; 
        for (Personnel.Person p : people) {
            p.setAvailable(false);
    }
        startTimer();
    }
    
    
    private void startTimer(){
        scheduler.scheduleAtFixedRate(() -> {
            if (remainingSeconds > 0) {
                System.out.println("Time remaining: " + remainingSeconds + "s");
                remainingSeconds--;
            } else {
                endMission();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }
    
    
    public void endMission() {
        if (!activeStatus) {
            return;
        }
        System.out.println("Mission completed!");
        cleanUp();
}
    
    private void cleanUp() {        
        for (Personnel.Person p : people){ 
            p.setAvailable(true);
        }   //have a setAvaiable for all agents after mission is over 
            // --> method in personnel, set agents singularly as active in respective missions.
            //no agents are reserved -- add in human personnel
        activeMissionCount--;
        activeStatus = false;
        scheduler.shutdownNow();
    }

    // human personnel needed as a parameter !!
    public abstract void personnelCapabilityCheck();
    public abstract void personnelPerformanceCheck();
    public abstract void personnelavailabilityCheck();
    public abstract void resourceCheck();
    public abstract void equipmentCheck();
    public abstract void roboticsCheck();
    public abstract void communicationCheck();
    public abstract void environmentCheck();

    public void roverBreak(ExplorationRover rover) {
        rover.breakDown();
        this.activeStatus = false;
        Mission.activeMissionCount -= 1;
        for(Person p : people) {
            p.setAvailable(true);
        }
        System.out.println("Mission Failed due to rover breakdown at "+rover.getLocation());
        System.out.println("Repair Operation Required.");
    }
    
    
    //Mission Failure Warnings
    public void insufficientResources(){
        throw new IllegalStateException("Mission FAILED! Insufficient Resources!");
    }
    
    public void equipmentFailure(){
        throw new IllegalStateException("Mission FAILED! Equipment failure!");
    }
    
    public void unsuitableAgent(){
        throw new IllegalStateException("Mission FAILED! Unsuitable agent!");
    }
    
    public void communicationDisruption(){
        throw new IllegalStateException("Mission FAILED! Environmental conditions unsuitable!");
    }
    
    public void insufficientBattery(){
        throw new IllegalStateException("Mission FAILED! Insufficient battery!");
    }
    
    public void damagedRobot(){
        throw new IllegalStateException("Mission FAILED! Robot is damaged!");
    }
    
    public void unavailablePersonnel(){
        throw new IllegalStateException("Mission FAILED! Unavailable personnel!");
    }
    
    
}
