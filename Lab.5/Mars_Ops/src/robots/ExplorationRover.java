
package robots;

public class ExplorationRover extends Robot {

    public ExplorationRover(int batteryLevel, boolean damageStatus, String location, boolean available) {
        super(batteryLevel, damageStatus, location, available);
    }
    
    @Override
    public void performTask() {
        System.out.println("Exploring the Martian surface!");
    }
    
    public void breakDown() {
        this.damageStatus = true;
        this.available = false;
        System.out.println("Robot at "+location+" broke down!!");
    }
    
    
    
}
