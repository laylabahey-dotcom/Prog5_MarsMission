
package Missions;
import java.util.*;
import Personnel.*;
import robots.*;
import main.*;



public abstract class Mission {
    public enum MissionStatus { PENDING, ASSIGNED, IN_PROGRESS, COMPLETED, FAILED }
   
    private boolean activeSlotClaimed = false;
    private int totalTime;
    
    private MissionStatus status = MissionStatus.PENDING;
    private String failureReason;

    protected final List<Person> people = new ArrayList<>();
    protected final List<Robot> robots = new ArrayList<>();
    
    private int remainingTime;
    private MarsOps colony;
    
    public Mission(int totalTime, MarsOps colony){
        if (totalTime < 0) {
        throw new IllegalArgumentException(
            "Mission duration cannot be negative."
        );
    }
        this.remainingTime = totalTime;
        this.colony = colony;
        this.totalTime = totalTime;
        
    }
    
    //mission will handle personnel, robots, facilities(locations), resources and equipment 
    //environment and emergencies handled in facilities and then flagged in individual mission types
    
    //Status
    public MissionStatus getStatus() { return status; }
    public String getFailureReason() { return failureReason; }
    public void setStatus(MissionStatus status) { this.status = status; }

    
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

    // start a mission -> check for activity of the mission
    public void startMission() {
        if (status != MissionStatus.PENDING) {
            throw new IllegalStateException("This mission is already assigned.");
        }
        if (colony.getActiveMission() != null) {
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
        colony.setActiveMission(this);
        activeSlotClaimed = true;
        status = MissionStatus.IN_PROGRESS;   // duration counting starts here
        if (remainingTime == 0) {
            endMission();
        }
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
    public void reject(String reason) {
        fail(reason);
    }

    
    
    public void tick() {
        if (status != MissionStatus.IN_PROGRESS) {
            return;
        }
        try {
            checkMissionContinues();
            if (status != MissionStatus.IN_PROGRESS) {
                return;   // failMission() ended it
            }
            CheckForEmergency();
        } catch (IllegalStateException e) {
            fail(e.getMessage());
            return;
        }
        if (status != MissionStatus.IN_PROGRESS) {
            return;
        }
        remainingTime--;
        System.out.println("Time remaining: " + remainingTime);
        if (remainingTime <= 0) {
            endMission();
        }
    }

    
    
    public void advanceTime(int units) {
        if (status != MissionStatus.IN_PROGRESS) {
            return;
        }

        for (int i = 0; i < units; i++) {
            if (status != MissionStatus.IN_PROGRESS) {
                break;
            }

            try {
                checkMissionContinues();
            } catch (IllegalStateException e) {
                fail(e.getMessage());
                return;
            }

            if (remainingTime > 0) {
                remainingTime--;
                System.out.println("Time remaining: " + remainingTime + "s");
            }

            if (remainingTime == 0) {
                endMission();
                break;
            }
        }
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
