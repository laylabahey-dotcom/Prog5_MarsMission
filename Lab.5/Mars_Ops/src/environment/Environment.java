
package environment;
public class Environment {
    private boolean DustStorm;
    private boolean commDisruption;
    
    public Environment() {
        this.DustStorm = false;
        this.commDisruption = false;
    }
    
    public void triggerDustStorm(){
        DustStorm = true;
        System.out.println("Warning! Incoming Dust storm!");
    }
    
    public void triggerCommDisruption(){
        commDisruption = true;
        System.out.println("Warning! Communication Disrupted!");
    }

    public boolean isDustStorm() {
        return DustStorm;
    }

    public boolean isCommDisruption() {
        return commDisruption;
    }
    
}
