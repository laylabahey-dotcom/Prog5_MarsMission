/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Missions;

import Colony_Facilities.*;
import personnel.*;
import supply.*;

public class Research extends Mission {
    private Person scientist[];
    private Facilities lab;
    private Equipment scientificEquipment[];
    private Resources scientificResources[];

    public Research(int totalSeconds, Person scientist[],
            Equipment scientificEquipment[],
            Resources scientificResources[], Facilities lab) {

        super(totalSeconds);

        this.scientist = scientist;
        for (Person s : scientist) {
            people.add(s);
        }
        this.scientificEquipment = scientificEquipment;
        this.scientificResources = scientificResources;
        this.lab = lab;
    }

    // for initial checks
    @Override
    public String requiredSkill() {
        return "Research";
    }

    @Override
    public void personnelCapabilityCheck() {
        for (Person p : people) {
            if (!p.canPerform(requiredSkill())) {
                unsuitableAgent();
                return;
            }
        }
        System.out.println("All assigned scientists are qualified!");
    }

    @Override
    public void personnelPerformanceCheck() {
        for (Person p : people) {
            if (!(p.getHealth() > 50 && p.getFatigueLevel() < 80)) {
                unsuitableAgent();
                return;
            }
        }
        System.out.println("Scientists are healthy and rested!");
    }

    @Override
    public void personnelavailabilityCheck() {
        for (Person p : people) {
            if (!p.available()) {
                unavailablePersonnel();
                return;
            }
        }
        System.out.println("Scientists are available!");
    }

    @Override
    public void resourceCheck() {
        if (lab == null || !lab.isOpStatus()) {
            throw new IllegalStateException("Mission FAILED! Laboratory is not operational!");
        }
        if (scientificEquipment == null || scientificEquipment.length == 0) {
            equipmentFailure();
            return;
        }
        for (Equipment e : scientificEquipment) {
            if (e == null || e.isDamaged()) {
                equipmentFailure();
                return;
            }
        }
        Equipment labEquipment = lab.getEquipment();
        if (labEquipment != null && labEquipment.isDamaged()) {
            equipmentFailure();
            return;
        }
        System.out.println("Scientific equipment is ready!");
}

    @Override
    public void equipmentCheck() {
        if (lab == null || !lab.isOpStatus()) {
            throw new IllegalStateException("Mission FAILED! Laboratory is not operational!");
        }
        if (scientificEquipment == null || scientificEquipment.length == 0) {
            equipmentFailure();
            return;
        }
        for (Equipment e : scientificEquipment) {
            if (e.isDamaged()) {
                equipmentFailure();
                return;
            }
        }
        System.out.println("Equipment is ready!");
    }

    @Override
    public void roboticsCheck() {
        // Research uses no robot
    }

    @Override
    public void communicationCheck() {
        // Research does not depend on communication
    }

    @Override
    public void environmentCheck() {
        // Research happens inside the lab, so no environment restriction
    }

    // for during-mission checks
    @Override
    public void checkMissionContinues() {
        for (Person p : people) {
            if (!(p.getHealth() > 50 && p.getFatigueLevel() < 80)) {
                failMission("Scientist is unhealthy or fatigued!");
                return;
            }
        }
        if (lab == null || !lab.isOpStatus()) {
            failMission("Laboratory is no longer operational!");
            return;
        }
        for (Equipment e : scientificEquipment) {
            if (e == null || e.isDamaged()) {
                failMission("Equipment is damaged!");
                return;
    }
}
}
}
    
