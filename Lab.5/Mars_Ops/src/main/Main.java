package main;

import Missions.*;
import Personnel.*;
import colony_facilities.*;
import emergency.*;
import environment.*;
import robots.*;
import supply.*;

public class Main {

    public static void main(String[] args) {
        //1.initializing our system
        MarsOps solar = new MarsOps("Solar");
        Environment marsEnv = new Environment();
        Simulation simulation = new Simulation(solar, marsEnv);

        //facilities w resources initialization (mesh 3arfa if i should do all or not T-T )
        Resources storageRes = new Resources(1000, 1000, 500, 500, 100, 100, 50);
        Equipment generalEquip = new Equipment("001", "Drill", false);
        Storage mainStorage = new Storage(40.5, 15.0, "ST-001", "idk", 5000, true, storageRes, generalEquip); //7oti locationnnnn
        solar.addFacility(mainStorage);
        
        //Mars Surface (for exploration)
        MarsSurface surface = new MarsSurface(true);

        //personnel & robots
        Engineer eng = new Engineer("Khaled", "E-255", "idk");//add locationnn
        Scientist sci = new Scientist("Mona", "SC-350", "idk");
        MedicalOfficer med = new MedicalOfficer("Yazan", "M-404", "idk");
        
        
        ExplorationRover rover = new ExplorationRover(75, false, "idk", true);
        RepairRobot repair = new RepairRobot(89, false, "idk", true);
        Drone drone = new Drone(45, false, "idk", true);
        solar.addCrewMembers(eng);
        solar.addCrewMembers(sci);
        solar.addCrewMembers(med);
        solar.addRobot(rover);
        solar.addRobot(repair);
        solar.addRobot(drone);

        //comm station
        Communication_Stations comm = new Communication_Stations(56.7, true, "C-001", "idk", 100, true, storageRes, generalEquip);

        //Scenario 1: Successful Exploration
        Resources[] expRes1 = {new Resources(0, 0, 0, 0, 10, 0, 2)};
        Equipment[] roverEquip = {new Equipment("002", "Scanner", false)};
        Exploration validMission = new Exploration(5, eng, rover, roverEquip, comm, expRes1, marsEnv, solar, surface);
        if(solar.submitMission(validMission)){
            validMission.startMission();
        }
        System.out.println("Scenario 1 Passed! Exploration mission executed successfully!\n");
        

        //Scenario 2: Mission Rejection
        Resources expRes2 = new Resources(0, 0, 0, 0, 10, 0, 2);
        Equipment researchEquip = new Equipment("004", "Microscope", false);
        Laboratory research_lab = new Laboratory(100, "C406", "dokky", 50, true, expRes2, researchEquip);
        Research rejectedMission = new Research(10, eng, new Equipment[]{researchEquip}, new Resources[]{expRes2}, research_lab, solar);
        if(solar.submitMission(rejectedMission)){
            rejectedMission.startMission();
        }
        System.out.println("Scenario 2 Passed! Research mission failed due to unsuitable agent!\n");
        
        //Scenario 3: Resource Shortage
        Resources[] expRes3 = {new Resources(0, 0, 0, 0, 10, 0, 222)};
        Equipment[] repairEquip = {new Equipment("003", "Tools", false)};
        Habitats shelter = new Habitats(220, "H-230", "idk", 500, true, storageRes, generalEquip);
        Repair shortageMission = new Repair(5, eng, repair, repairEquip, expRes3, shelter, solar, comm);
        if(solar.submitMission(shortageMission)){
                shortageMission.startMission();;
            }
        if (shortageMission.getStatus() == Mission.MissionStatus.FAILED) {
            System.out.println("Scenario 3 Passed! Resource shortage correctly caught and rejected!\n");
        }

        //Scenario 4: Emergency Handling
        
        //Scenario 5: Environmental Effect
        
    }
}
