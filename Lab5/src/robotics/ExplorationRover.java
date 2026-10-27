
package robotics;

public class ExplorationRover extends Robot {
    
    public ExplorationRover(int batteryLevel,String damageStatus, String location) {
        super(batteryLevel, damageStatus, location);
    }
    @Override
    public void performTask() {
        System.out.println("Exploring the Martian surface!");
    }
    
    
    
    
    
}
