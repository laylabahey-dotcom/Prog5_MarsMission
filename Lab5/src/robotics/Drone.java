
package robotics;

public class Drone extends Robot {
    public Drone(int batteryLevel,String damageStatus, String location) {
        super(batteryLevel, damageStatus, location);
    }

    @Override
    public void performTask() {
        System.out.println("Performing aerial reconnaissance!");
    }
}
