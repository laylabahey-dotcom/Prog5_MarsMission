
package environment;
public class DustStorm extends Environment{
    @Override
    public boolean surfaceExpMission() {
        return false;
    }

    @Override
    public boolean controlRemoteDrones() {
        return true;
    }

    @Override
    public boolean startCommMission() {
        return true;
    }

    @Override
    public boolean activeRoverFail() {
        return true;
    }


    
    
}
