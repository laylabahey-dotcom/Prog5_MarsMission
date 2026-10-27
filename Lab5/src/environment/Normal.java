package environment;

public class Normal extends Environment {

    @Override
    public boolean surfaceExpMission() {
        return true;
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
        return false;
    }

}
