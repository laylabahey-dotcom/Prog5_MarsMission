
package environment;


public class CommunicationDisruption extends Environment {
 
    @Override
    public boolean surfaceExpMission() {
        return true;
    }

    @Override
    public boolean controlRemoteDrones() {
        return false;
    }

    @Override
    public boolean startCommMission() {
        return false;
    }

    @Override
    public boolean activeRoverFail() {
        return false;
    }

    

    
}
