package environment;

public class Environment {

    private boolean dustStorm;
    private boolean commDisruption;

    public Environment() {
        this.dustStorm = false;//mentioned initial
        this.commDisruption = false;
    }

    public void triggerDustStorm() {
        dustStorm = true;
        System.out.println("Warning! Incoming Dust storm!");
    }

    public void triggerCommDisruption() {
        commDisruption = true;
        System.out.println("Warning! Communication is Disrupted!");
    }

    public boolean isDustStorm() {
        return dustStorm;
    }

    public boolean isCommDisruption() {
        return commDisruption;
    }

    public void clearDustStorm() {
        dustStorm = false;
        System.out.println("Dust storm has cleared.");

    }

    public void clearCommDisruption() {
        commDisruption = false;
        System.out.println("Communications restored.");
    }

}
