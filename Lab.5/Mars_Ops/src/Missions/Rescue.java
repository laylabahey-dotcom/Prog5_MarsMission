package Missions;

public class Rescue extends Mission{
    private robots.Drone drone;
    private Personnel.Engineer eng;
    private Personnel.MedicalOfficer med;
    private colony_facilities.Communication_Stations comm;

    public Rescue(robots.Drone drone, Personnel.Engineer eng, Personnel.MedicalOfficer med, colony_facilities.Communication_Stations comm, int totalSeconds, main.MarsOps colony) {
        super(totalSeconds,colony);
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
    }
    
    @Override
    public void personnelCapabilityCheck() {
    }

    @Override
    public void personnelPerformanceCheck() {
    }

    @Override
    public void personnelavailabilityCheck() {
    }

    @Override
    public void resourceCheck() {
    }

    @Override
    public void equipmentCheck() {
    }

    @Override
    public void roboticsCheck() {
    }

    @Override
    public void communicationCheck() {
    }

    @Override
    public void environmentCheck() {
    }
}
