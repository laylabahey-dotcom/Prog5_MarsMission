package Missions;

public class Repair extends Mission{

    private Personnel.Engineer e;

    public Repair(Personnel.Engineer e, int totalSeconds, main.MarsOps colony) {
        super(totalSeconds,colony);
        this.e = e;
        if (e != null) {
        this.people.add(e);
        }
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
