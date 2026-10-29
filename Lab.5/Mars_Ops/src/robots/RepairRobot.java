package robots;

public class RepairRobot extends Robot {

    public RepairRobot(int batteryLevel, boolean damageStatus, String location, boolean available) {
        super(batteryLevel, damageStatus, location, available);
    }

    @Override
    public boolean canPreform(String task) {
        return task.equalsIgnoreCase("Repair");
    }

}
