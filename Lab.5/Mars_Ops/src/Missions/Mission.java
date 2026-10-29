package Missions;

import java.util.*;
import Personnel.*;
import main.*;
import robots.*; //k

//IM HESITANT TO DELETE THINGS, I HAVE A ZIP FILE OF THE CODE BEFORE MOD 
//BUT I WILL KEEP DELETING STUFF FROM UR CLASSES TO A MINIMUM, COMMENTS INSTEAD

public abstract class Mission {

    public enum MissionState {
        PENDING, ASSIGNED, IN_PROGRESS, COMPLETED, FAILED
    }
    
    private int duration;
    private int timeElapsed;
    private MissionState state;
    private String failureReason;
    

    // no need for public static int activeMissionCount = 0; tracked by MarsOps
    /*private boolean activeSlotClaimed = false;
    private int totalSeconds;

    private MissionStatus status = MissionStatus.PENDING;
    private String failureReason;*/

    protected final List<Person> people = new ArrayList<>();
    protected final List<Robot> robots = new ArrayList<>();

    /*private final ScheduledExecutorService scheduler;
    private int remainingSeconds;*/ //dont need this either
    
    private MarsOps colony;
    
    public Mission(int duration, MarsOps colony){
        if (duration < 0) {
            throw new IllegalArgumentException(
                    "Mission duration cannot be negative."
            );
        }
        this.duration=duration;
        this.timeElapsed=0;
        this.colony=colony;
        this.state=MissionState.FAILED;
        
        /*this.scheduler = Executors.newScheduledThreadPool(1);
        this.remainingSeconds = totalSeconds;
        this.colony = colony;
        this.totalSeconds = totalSeconds; eb2o delete this stuff im just scared to  */
        
    }
    
    //mission will handle personnel, robots, facilities(locations), resources and equipment 
    //environment and emergencies handled in facilities and then flagged in individual mission types
    
    //Status
    public MissionState getStatus() {
        return state;
    }
    
    public void setState(MissionState state) {
        this.state=state;
    }

    public String getFailureReason() {
        return failureReason;
    }
    
    public void setFailureReason(String reason) {
        this.failureReason=reason;
    }
    
    public int getDuration() {
        return duration;
    }
    
    public int getTimeElapsed() {
        return timeElapsed;
    }
    
    public void incrementTimeElapsed(int amount) {
        this.timeElapsed+=amount;
    }
    
    public boolean isFinished(){
        return state==MissionState.COMPLETED||state==MissionState.FAILED;
    }
    
    public void addPerson(Person p) {
        people.add(p); //add new element to array list
    }
    
    public void addRobot(Robot r) {
        robots.add(r);
    }

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
    /*public void setStatus(MissionStatus status){
        this.status = status;
    } dont need anymore*/

    // DO NOT DELETE start a mission -> check for activity of the missionDO NOT DELETE
    /*public void startMission() {
        if (state != MissionState.PENDING) {
            throw new IllegalStateException("This mission is already assigned.");
        }
        if (activeMissionCount >= 1) {
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
    }NEEED THIS LATER DO NOT DELETE DO NOT DELETE DO NOT DELETE*/

    //get the failure reason
    /*private void fail(String reason) {
        if (state == MissionState.COMPLETED
                || state == MissionState.FAILED) {
            return;
        }
        state = MissionState.FAILED;
        failureReason = reason;
        System.out.println(reason);
        cleanUp();
    } no longer nead this.. handled in failMission and isFinished*/

    protected void failMission(String reason) {
        if(isFinished()) return;
        state=MissionState.FAILED;
        failureReason=reason;
        System.out.println(reason);
        cleanUp();
    }

    /*private void startTimer() {
        state = MissionState.IN_PROGRESS;
        if (remainingSeconds == 0) {
            endMission();
            return;
        }
        scheduler.scheduleAtFixedRate(() -> {
            if (state != MissionState.IN_PROGRESS) {
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
    }replaced with incrementTimeElapsed*/

    public void endMission() {
        if (state != MissionState.IN_PROGRESS) {
            return;
        }
        state = MissionState.COMPLETED;
        System.out.println("Mission completed!");
        cleanUp();
    }

    private void cleanUp() {
        for (Person p : people) {
            p.setAvailable(true);
        }
        for(Robot r:robots) {
            r.setAvailable(true); //added robots loop
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
    public void insufficientResources() {
        throw new IllegalStateException("Mission FAILED! Insufficient Resources!");
    }

    public void equipmentFailure() {
        throw new IllegalStateException("Mission FAILED! Equipment failure!");
    }

    public void unsuitableAgent() {
        throw new IllegalStateException("Mission FAILED! Unsuitable agent!");
    }

    public void communicationDisruption() {
        throw new IllegalStateException("Mission FAILED! Communication disrupted!");
    }

    public void insufficientBattery() {
        throw new IllegalStateException("Mission FAILED! Insufficient battery!");
    }

    public void damagedRobot() {
        throw new IllegalStateException("Mission FAILED! Robot is damaged!");
    }

    public void robotUnavailable() {
        throw new IllegalStateException("Mission FAILED! Robot is unavailable!");
    }

    public void unavailablePersonnel() {
        throw new IllegalStateException("Mission FAILED! Unavailable personnel!");
    }
    
    // emergency handling
    public abstract void CheckForEmergency();
}
//theres still stuff to change im tired tho