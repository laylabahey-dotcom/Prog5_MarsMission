package colony_facilities;

public class MarsSurface extends Facilities {
    private boolean surfaceStatus;
    
    public MarsSurface(boolean surfaceStatus) {
        // Pass default or specific values to the Facilities constructor
        super("Mars Surface", 100, true, null, null); 
        this.surfaceStatus = surfaceStatus;    
    }
    
    public boolean getSurfaceStatus() {
        return surfaceStatus;
    }
    
    public void setSurfaceStatus(boolean status) {
        this.surfaceStatus = status;
    }
    
    @Override
    public boolean isSafeForMission() {
        // Also ensure it checks surfaceStatus as well as inherited facility rules if needed
        return surfaceStatus && super.isSafeForMission(); 
    }
}
