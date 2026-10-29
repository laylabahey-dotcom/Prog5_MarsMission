package Missions;

import robots.*;
import supply.*;
import Personnel.*;
import colony_facilities.*;
import environment.*;
import main.*;

public class Rescue extends Mission {
    private robots.Drone drone;
    private Personnel.Engineer eng;
    private Personnel.MedicalOfficer med;
    private colony_facilities.Communication_Stations comm;  
    private Equipment rescueEquipment[];
    private Resources rescueRes[];
    private MarsSurface surface;
    private Environment missionEnvironment;
    private MarsOps colony;

    public Rescue(robots.Drone drone, Personnel.Engineer eng, Personnel.MedicalOfficer med, 
                  Communication_Stations comm, Equipment rescueEquipment[], Resources rescueRes[], 
                  MarsSurface surface, Environment missionEnvironment, int totalSeconds, MarsOps colony) {
        super(totalSeconds, colony);
        this.drone = drone;
        this.eng = eng;
        if (eng != null) {
            this.people.add(eng);
        }
        this.med = med;
        if (med != null) {
            this.people.add(med);
        }
        this.comm = comm;
        this.rescueEquipment = rescueEquipment;
        this.rescueRes = rescueRes;
        this.surface = surface;
        this.missionEnvironment = missionEnvironment;
        this.colony = colony;
        
        System.out.println("Initiating rescue mission deployment!");
    }

    @Override
    public String requiredSkill() {
        return "Rescue";
    }

    @Override
    public void personnelCapabilityCheck() {
        for (Person p : people) {
            if (!p.canPerform(requiredSkill())) {
                unsuitableAgent();
                return;
            }
        }
        System.out.println("All rescue personnel possess the required capabilities!");
    }

    @Override
    public void personnelPerformanceCheck() {
        for (Person p : people) {
            if (p.getHealth() <= 50 || p.getFatigueLevel() >= 80) {
                unsuitableAgent();
                return;
            }
        }
        System.out.println("All rescue personnel are healthy and fit!");
    }

    @Override
    public void personnelavailabilityCheck() {
        for (Person p : people) {
            if (!p.available()) {
                unavailablePersonnel();
                return;
            }
        }
        System.out.println("All rescue personnel are available!");
    }

    @Override
    public void resourceCheck() {
        if (rescueRes == null || rescueRes.length == 0) {
            System.out.println("WARNING: No resources assigned to this rescue mission!");
            insufficientResources();
        }
        int reqParts = 0, reqBattery = 0;
        for (Resources resource : rescueRes) {
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
        System.out.println("Rescue resources are sufficient!");
    }

    @Override
    public void equipmentCheck() {
        if (rescueEquipment == null || rescueEquipment.length == 0) {
            equipmentFailure();
            failMission("Rescue equipment is insufficient!");
            return;
        }
        for (Equipment e : rescueEquipment) {
            if (e.isDamaged()) {
                equipmentFailure();
                failMission("Rescue equipment is damaged!");
                return;
            }
        }
        System.out.println("Rescue equipment is ready!");
    }

    @Override
    public void roboticsCheck() {
        if(!(drone.canPreform(requiredSkill()))){
            unsuitableRobot();
            return;
        }
        if (drone == null) {
            robotUnavailable();
            return;
        }
        if (drone.getBatteryLevel() <= 20) {
            insufficientBattery();
            return;
        }
        if (drone.getDamageStatus()) {
            damagedRobot();
            return;
        }
        if (!drone.isAvailable()) {
            robotUnavailable();
            return;
        }
        System.out.println("Rescue drone battery is sufficient, available, and undamaged!");
    }

    @Override
    public void communicationCheck() {
        if (comm == null || !comm.checkSignal()) {
            communicationDisruption();
            return;
        }
        System.out.println("Communication signal for rescue operation is stable!");
    }

    @Override
    public void environmentCheck() {
        if (missionEnvironment != null && missionEnvironment.isDustStorm()) {
            throw new IllegalStateException("Dust storm prevents rescue drone from launching!");
        }
        if (missionEnvironment != null && missionEnvironment.isCommDisruption()) {
            throw new IllegalStateException("Communication disruption prevents rescue drone from continuing!");
        }
        System.out.println("Environmental conditions are suitable for rescue!");
    }

    @Override
    public void checkMissionContinues() {
        for (Person p : people) {
            if (!p.canPerform(requiredSkill()) || !(p.getHealth() > 50 && p.getFatigueLevel() < 80) || !p.available()) {
                failMission("Rescue personnel status degraded during mission!");
                return;
            }
        }
        for (Equipment e : rescueEquipment) {
            if (e.isDamaged()) {
                failMission("Rescue equipment damaged during operation!");
                return;
            }
        }
        if (drone.getBatteryLevel() <= 20 || drone.getDamageStatus()) {
            failMission("Rescue drone compromised during operation!");
            return;
        }
        if (comm == null || !comm.checkSignal()) {
            failMission("Communication lost during rescue mission!");
            return;
        }
    }

    @Override
    public void CheckForEmergency() {
        if (surface != null && !surface.isSafeForMission()) {
            if (surface.hasEmergency()) {
                emergency.Emergency active = surface.getActiveEmergency();
                String emergencyType = active != null ? active.getType() : "Unknown Surface Emergency";
                
                if (active instanceof emergency.Medical) {
                    throw new IllegalStateException(
                        "Rescue FAILED! Medical Emergency active on Mars Surface. Rescue suspended."
                    );
                } else {
                    throw new IllegalStateException(
                        "Rescue FAILED! Surface hazard [" + emergencyType + "] active at " + surface.getLocation() + "."
                    );
                }
            }
            throw new IllegalStateException("Rescue FAILED! Habitat is currently not operational or damaged.");
        }

        if (comm != null && !comm.isSafeForMission()) {
            throw new IllegalStateException(
                "Rescue FAILED! Communication disruption at station [" + comm.getLocation() + "]. Transmission lines down."
            );
        }

        System.out.println("Emergency check passed: All surface telemetry and comms are fully clear.");
   }
}
