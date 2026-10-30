package main;

import java.util.*;
import Personnel.*;
import environment.*;
import robots.*;
import colony_facilities.*;
import emergency.*;
import Missions.*;
import supply.*;

public class Simulation {

    private MarsOps colony;
    private Environment environment;
    private int currentTime;
    private Random random;
    private Emergency emergency;

    public Simulation(MarsOps colony, Environment environment) {
        this.colony = colony;
        this.environment = environment;
        this.currentTime = 0;
        this.random = new Random();
    } 

    public void advanceTime(int timeUnits) {
    for (int i = 0; i < timeUnits; i++) {
        currentTime++;
        System.out.println("--- Time unit " + currentTime + " ---");

        updateEnvironment();
        consumeSupplies();
        updateFatigueAndBattery();
        checkForRandomEmergencies();

        Mission m = colony.getActiveMission();
        if (m != null) m.tick();   // last, so it sees this unit's changes
    }
}

    private void updateEnvironment() {
        if (!environment.isDustStorm() && random.nextDouble() < 0.2) {
            environment.triggerDustStorm();
        } else if (environment.isDustStorm() && random.nextDouble() < 0.3) {
            environment.clearDustStorm();
        }
        if (!environment.isCommDisruption() && random.nextDouble() < 0.15) {
            environment.triggerCommDisruption();
        } else if (environment.isCommDisruption() && random.nextDouble() < 0.3) {
            environment.clearCommDisruption();
        }
    }

    private void consumeSupplies() {
        for (Facilities f : colony.getFacilities()) {
            if (f.isOpStatus() && f instanceof Habitats) {
                Habitats shelter = (Habitats) f;
                if (shelter.getResidentNum() > 0) {
                    shelter.consumeFood(1);
                    shelter.consumeOxygen(5);
                    shelter.consumeWater(2);
                    System.out.println("Habitat " + shelter.getLocation() + " consumed life support supplies.");
                }
            }
        }
    }

    private void updateFatigueAndBattery() {
        for (Person p : colony.getAllCrewMembers()) {
            if (!p.available()) {
                p.setFatigueLevel(p.getFatigueLevel() + 5);
                System.out.println("Crew member " + p.getName() + " is active. Fatigue level increasing...");
            }
        }
        for (Robot r : colony.getRobots()) {
            if (!r.isAvailable()) {
                if (r.getBatteryLevel() > 0) {
                    r.setBatteryLevel(r.getBatteryLevel() - 2);
                    System.out.println("Robot Battery Decreased.");
                } else {
                    Mission m = colony.getActiveMission();
                    if (m != null) System.out.println("Robot battery depleted during mission.");
                }
            }
        }
    }
    
    private void checkForRandomEmergencies() {
        if (random.nextDouble() < 0.1) {
            for (Facilities f : colony.getFacilities()) {
                if (f.isOpStatus() && f instanceof Laboratory && random.nextBoolean()) {
                    Laboratory lab = (Laboratory) f;
                    System.out.println("A SUDDEN FIRE BROKE OUT AT LAB " + f.getLocation() + "!!");
                    Fire fire = new Fire(f.getLocation(), lab, 20, colony);
                    break;
                } else if (f.isOpStatus() && f instanceof Habitats && random.nextBoolean()) {
                    Habitats shelter = (Habitats) f;
                    System.out.println("OXYGEN LEAK DETECTED IN HABITAT " + f.getLocation() + "!!");
                    OxygenLeak leak = new OxygenLeak(f.getLocation(), shelter, 20, colony);
                    break;
                }
            }
        }
    }

    public int getCurrentTime() {
        return currentTime;
    }
    
}
    

