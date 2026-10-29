package robots;

public class ExplorationRover extends Robot {

    public ExplorationRover(int batteryLevel, boolean damageStatus, String location, boolean available) {
        super(batteryLevel, damageStatus, location, available);
    }

    @Override
    public boolean canPreform(String task) {
        return task.equalsIgnoreCase("Exploration") ||
                task.equalsIgnoreCase("Rescue");
    }

    public void breakDown() {
        this.damageStatus = true;
        this.available = false;
        System.out.println("Robot at " + location + " broke down!!");
    }

}
