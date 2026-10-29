package robots;

public abstract class Robot {

    protected int batteryLevel;
    protected boolean damageStatus;
    protected String location;
    protected boolean available;

    public Robot(int batteryLevel, boolean damageStatus, String location, boolean available) {
        if (batteryLevel < 0 || batteryLevel > 100) {
            throw new IllegalArgumentException("Battery level must be between 0 and 100.");
        }
        this.batteryLevel = batteryLevel;
        this.location = location;
        this.damageStatus = damageStatus;
        this.available = available;
    }

    public void setDamageStatus(boolean damageStatus) {
        this.damageStatus = damageStatus;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void recharge() {
        batteryLevel = 100;
    }

    //check whether a robot can perform a mission
    public boolean canPerform(Missions.Mission mission) {
        return available && batteryLevel > 20 && !damageStatus;
    }
    //check whether a robot can respond to an emergency

    public boolean canHandle(emergency.Emergency emergency) {
        return available && batteryLevel > 20 && !damageStatus;

    }

    public abstract boolean canPreform(String task);//diff for every robot

    //getters
    public int getBatteryLevel() {
        return batteryLevel;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;

    }

    public boolean isAvailable() {
        return available;
    }

    public boolean getDamageStatus() {
        return damageStatus;
    }

    public void setBatteryLevel(int batteryLevel) {
        if (batteryLevel >= 0 && batteryLevel <= 100) {
            this.batteryLevel = batteryLevel;
        }
    }

}
