
package Missions;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import Personnel.*;
import main.*;


public abstract class Mission {
    public enum MissionStatus { PENDING, ASSIGNED, IN_PROGRESS, COMPLETED, FAILED }
    
    public static int activeMissionCount = 0;
    private boolean activeSlotClaimed = false;
    private int totalSeconds;
    
    private MissionStatus status = MissionStatus.PENDING;
    private String failureReason;

    protected final List<Person> people = new ArrayList<>();
    
    private final ScheduledExecutorService scheduler;
    private int remainingSeconds;
    private MarsOps colony;
    
    public Mission(int totalSeconds, MarsOps colony){
        if (totalSeconds < 0) {
        throw new IllegalArgumentException(
            "Mission duration cannot be negative."
        );
    }
        this.scheduler = Executors.newScheduledThreadPool(1);
        this.remainingSeconds = totalSeconds;
        this.colony = colony;
        this.totalSeconds = totalSeconds;
        
    }
    
    //mission will handle personnel, robots, facilities(locations), resources and equipment 
    //environment and emergencies handled in facilities and then flagged in individual mission types
    
    //Status
    public MissionStatus getStatus() { return status; }
    public String getFailureReason() { return failureReason; }
    
    //personnel skill
    public abstract String requiredSkill();
    
    private void runChecks() {
        personnelCapabilityCheck();
        personnelPerformanceCheck();
        personnelavailabilityCheck();
        resourceCheck();
        equipmentCheck();
        roboticsCheck();
        communicationCheck();
        environmentCheck();
        CheckForEmergency();
    } 
     public void setStatus(MissionStatus status){
        this.status = status;
                 }
    // start a mission -> check for activity of the mission
    public void startMission(){
        if(status != MissionStatus.PENDING){
            throw new IllegalStateException("This mission is already assigned.");
        }
        if(activeMissionCount >= 1){
            fail("Mission FAILED. Already have an assigned mission. This mission cannot be authorised.");
            return;
        }
        for (Person p : people) {
            if (!p.available()) {
                fail(p.getName() + " is not available.");
                return;
            }
        }

        try {
            runChecks();
        } catch (IllegalStateException e) {
            fail(e.getMessage());
            return;
        }

        System.out.println("Mission authorised.");
        activeMissionCount++;
        activeSlotClaimed = true;
        status = MissionStatus.ASSIGNED;
        for (Person p : people) {
            p.setAvailable(false);
        }
        startTimer();
    }

    //get the failure reason
    private void fail(String reason) {
    if (status == MissionStatus.COMPLETED ||
        status == MissionStatus.FAILED) {
        return;
    }
    status = MissionStatus.FAILED;
    failureReason = reason;
    System.out.println(reason);
    cleanUp();
}
    
    protected void failMission(String reason) {
        fail(reason);
    }
    
    
    private void startTimer(){
        status = MissionStatus.IN_PROGRESS;
        if (remainingSeconds == 0) {
            endMission();
            return;
        }
        scheduler.scheduleAtFixedRate(() -> {
        if (status != MissionStatus.IN_PROGRESS) {
            scheduler.shutdown();
            return;
        }
        try {
                checkMissionContinues();
            } catch (IllegalStateException e) {
                fail(e.getMessage());    
                return;
            }

        if (remainingSeconds > 0) {
            remainingSeconds--;
            System.out.println(
                "Time remaining: " + remainingSeconds + "s"
            );
        }

        if (remainingSeconds == 0) {
            endMission();
        }
    }, 1, 1, TimeUnit.SECONDS);
}
    
    
    public void endMission() {
        if (status != MissionStatus.IN_PROGRESS) {
            return;
        }
        status = MissionStatus.COMPLETED;
        System.out.println("Mission completed!");
        cleanUp();
}
    
    private void cleanUp() {
        if (activeSlotClaimed) {
            for (Person p : people) {
                p.setAvailable(true);
            }
            activeMissionCount--;
            activeSlotClaimed = false;
        }
        scheduler.shutdownNow();
}

    // initial checks
    public abstract void personnelCapabilityCheck();
    public abstract void personnelPerformanceCheck();
    public abstract void personnelavailabilityCheck();
    public abstract void resourceCheck();
    public abstract void equipmentCheck();
    public abstract void roboticsCheck();
    public abstract void communicationCheck();
    public abstract void environmentCheck();

    //during mission checks
    public abstract void checkMissionContinues();
    
    
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
    
    public void communicationDisruption() {
        throw new IllegalStateException("Mission FAILED! Communication disrupted!");
}
    
    public void insufficientBattery(){
        throw new IllegalStateException("Mission FAILED! Insufficient battery!");
    }
    
    public void damagedRobot(){
        throw new IllegalStateException("Mission FAILED! Robot is damaged!");
    }
    
    public void robotUnavailable(){
        throw new IllegalStateException("Mission FAILED! Robot is unavailable!");
    }
    
    public void unavailablePersonnel(){
        throw new IllegalStateException("Mission FAILED! Unavailable personnel!");
    }
    
    // emergency handling
    public abstract void CheckForEmergency();
}
