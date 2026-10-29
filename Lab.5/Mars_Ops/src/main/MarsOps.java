package main;

import java.util.*;
import Personnel.*;
import robots.*;
import colony_facilities.*;
import environment.*;
import Missions.*;
import emergency.Emergency;

public class MarsOps {

    private String colonyName;
    private Mission activeMission;
    
    private final Environment environment=new Environment();

    private List<Person> crewMembers;
    private List<Robot> robots;
    private List<Facilities> facilities;

    public MarsOps(String colonyName) {
        this.colonyName = colonyName;
        this.crewMembers = new ArrayList<>();
        this.robots = new ArrayList<>();
        this.facilities = new ArrayList<>();
    }

    public List<Person> getAllCrewMembers() {
        return this.crewMembers;
    }

    public List<Robot> getRobots() {
        return this.robots;
    }

    public List<Facilities> getFacilities() {
        return this.facilities;
    }

    public void addCrewMembers(Person person) {
        this.crewMembers.add(person);
    }

    public void addRobot(Robot robot) {
        this.robots.add(robot);
    }

    public void addFacility(Facilities facility) {
        this.facilities.add(facility);
    }

    public int getTotalResources(String name) {
        int total = 0;
        for (Facilities f : facilities) {
            if (f.isOpStatus() && f.getResource() != null) {
                switch (name.toLowerCase()) {
                    case "oxygen" ->
                        total += f.getResource().getO2_count();
                    case "water" ->
                        total += f.getResource().getH2O_count();
                    case "food" ->
                        total += f.getResource().getFood_count();
                    case "fuel" ->
                        total += f.getResource().getFuel_count();
                    case "battery" ->
                        total += f.getResource().getBattery_units_count();
                    case "medicine" ->
                        total += f.getResource().getMedicalSup_count();
                    case "parts" ->
                        total += f.getResource().getSpare_count();
                }
            }
        }
        return total;
    }

    public boolean consumeResources(String name, int amount) {
        if (getTotalResources(name) < amount) {
            return false;
        }
        int remainder = amount;
        for (Facilities f : facilities) {
            if (remainder <= 0) {
                break;
            }
            if (f.isOpStatus() && f.getResource() != null) {
                int available = getFacilityCount(f, name);
                if (available > 0) {
                    int taken = Math.min(remainder, available);
                    if (f.removeResources(name, taken)) {
                        remainder -= taken;
                    }
                }
            }
        }
        return remainder == 0;
    }
    
    public Environment getEnvironment(){
        return environment;
    }

    public Mission getActiveMission() {
        if(activeMission!=null && activeMission.isFinished()) {
            activeMission=null;
        } return activeMission;
    }
    
    public void submitMission(Mission newMission) {
        if(this.activeMission!=null &&this)
    }
    
    private int getFacilityCount(Facilities f, String name) {
        supply.Resources r = f.getResource();
        if (r == null) {
            return 0;
        }
        return switch (name.toLowerCase()) {
            case "oxygen" ->
                r.getO2_count();
            case "water" ->
                r.getH2O_count();
            case "food" ->
                r.getFood_count();
            case "fuel" ->
                r.getFuel_count();
            case "battery" ->
                r.getBattery_units_count();
            case "medicine" ->
                r.getMedicalSup_count();
            case "parts" ->
                r.getSpare_count();
            default ->
                0;
        };
    }
}
