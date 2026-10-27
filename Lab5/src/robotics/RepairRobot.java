
package robotics;

public class RepairRobot extends Robot {
     public RepairRobot(int batteryLevel,String damageStatus, String location) {
        super(batteryLevel, damageStatus, location);
    }

    @Override
    public void performTask() {
        System.out.println("Repairing damaged equipment!");
    }
    
}
