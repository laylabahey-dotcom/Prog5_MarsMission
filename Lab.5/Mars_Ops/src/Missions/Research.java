package Missions;

public class Research extends Mission { 
    private Personnel.Scientist scientist;
    private colony_facilities.Laboratory lab; //eequipment & resources are found in lab

    public Research(Personnel.Scientist scientist, colony_facilities.Laboratory lab, int totalSeconds, main.MarsOps colony) {
        super(totalSeconds,colony);
        this.scientist = scientist;
        if (scientist != null) {
        this.people.add(scientist);
        }
        this.lab = lab;
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
