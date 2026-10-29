package robots;

public class Drone extends Robot {

    public Drone(int batteryLevel, boolean damageStatus, String location, boolean available) {
        super(batteryLevel, damageStatus, location, available);
    }

    @Override
    public boolean canPreform(String task) {
        return task.equalsIgnoreCase("Exploration") ||
                task.equalsIgnoreCase("Rescue");
    }

}
