package emergency;

import Personnel.*;
import robots.*;
import main.*;
import java.util.List;
import java.util.ArrayList;

public abstract class Emergency {

    protected String type;
    protected String location;
    protected boolean solved;
    protected MarsOps colony;
    protected final List<Person> responders=new ArrayList<>();
    protected final List<Robot> robotResponders=new ArrayList<>();

    public Emergency(String type, String location, MarsOps colony) {
        this.type = type;
        this.location = location;
        this.colony = colony;
        this.solved = false;
    }
    
    public void addResponder(Person p) {
        if(p!=null) responders.add(p);
    }
    
    public void addRobotResponder(Robot r) {
        if(r!=null) robotResponders.add(r);
    }

    public boolean isSolved() {
        return solved;
    }
    public String getType() {
        return type;
    }
    public String getLocation() {
        return location;
    }
    public MarsOps getColony() {
        return colony;
    }
    
    public abstract void checkUnresolved();
    public abstract void resolveEmergency();

    public void handleEmergency(){
        System.out.println("Processing hazard: "+type+" at "+location+"...");
        resolveEmergency();
        if(!solved) {
            checkUnresolved();
        }
    }
}
