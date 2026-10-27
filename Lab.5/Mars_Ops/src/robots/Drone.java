
package robots;

public class Drone extends Robot {
    public Drone(int batteryLevel,boolean damageStatus, String location, boolean available) {
        super(batteryLevel, damageStatus, location, available);
    }

    @Override
    public void performTask() {
        System.out.println("Performing aerial reconnaissance!");
    }
    
}
