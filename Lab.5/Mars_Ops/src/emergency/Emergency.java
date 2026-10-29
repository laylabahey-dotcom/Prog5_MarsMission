package emergency;

import colony_facilities.*;
import Personnel.*;
import robots.*;
import main.*;
import supply.*;

public abstract class Emergency {

    protected String type;
    protected String location;
    protected boolean solved;
    protected MarsOps colony;

    public Emergency(String type, String location, MarsOps colony) {
        this.type = type;
        this.location = location;
        this.colony = colony;
        this.solved = false;
    }

    public boolean isSolved() {
        return solved;
    }
    
    public abstract String getType();
    
    public abstract void resolveEmergency();

    
}
