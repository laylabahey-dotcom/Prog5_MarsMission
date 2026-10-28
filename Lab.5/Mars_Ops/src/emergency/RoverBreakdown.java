package emergency;

import colony_facilities.*;
import Personnel.*;
import main.*;
import supply.*;
import robots.*;
import Missions.*;

public class RoverBreakdown extends Emergency {

    private ExplorationRover targetRover;
    private Mission targetMission;
    private Resources reqResources;
    private Engineer engineer;
    private RepairRobot robot;

    public RoverBreakdown(String location, ExplorationRover targetRover, Mission targetMission, Resources reqResources, Engineer engineer, RepairRobot robot, MarsOps colony) {
        super("Rover Breakdown", location, colony);

        this.targetRover = targetRover;
        this.targetMission = targetMission;
        this.reqResources = reqResources;
        this.engineer = engineer;
        this.robot = robot;
        //immediate breakdown
        if (targetRover != null) {
            //op = false
            targetRover.setDamageStatus(false);
            System.out.println("WARNING! Rover breakdown at " + location);
        }
        if (targetMission != null) {
            // update mission status to failed
            targetMission.setStatus(Mission.MissionStatus.FAILED);
            System.out.println("WARNING: Mission has been suspended due to rover failure.");

        }

    }

    @Override
    public void resolveEmergency() {
        if (targetRover == null) {
            System.out.println("Error in resolving! Target rover does not exist.");
            return;
        }

        if ((engineer != null && !engineer.available()) || (robot != null && !robot.isAvailable())) {
            System.out.println("Emergency unresolved! Required repair personnel or robot are busy/unavailable.");
            return;
        }

        //check required resources
        int reqParts = 0;
        if (reqResources != null) {
            reqParts = reqResources.getSpare_count();
        }

        if (colony.getTotalResources("parts") < reqParts) {
            System.out.println("Error in resolving! Insufficient spare parts to fix rover.");
            return;
        }

        // Consume required parts from colony
        boolean success = true;
        if (reqParts > 0) {
            success = colony.consumeResources("parts", reqParts);
        }

        if (success) {
            // Dispatch responders 
            if (engineer != null) {
                engineer.setLocation(location);
            }
            if (robot != null) {
                robot.setLocation(location);
            }

            // Repair the rover and update its status
            targetRover.setAvailable(true);

            // Resume the mission
            if (targetMission != null) {
                targetMission.setStatus(Mission.MissionStatus.IN_PROGRESS);
                System.out.println("Mission has resumed operations.");
            }

            this.solved = true;
            System.out.println("Rover breakdown at " + location + " has been successfully repaired!");
        } else {
            System.out.println("Emergency unresolved! Resource consumption failed.");
        }
    
    
}
    @Override
    public String getType() {
        return this.type;
    }

}
