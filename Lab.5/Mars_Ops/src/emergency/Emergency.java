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
    public abstract void resolveEmergency();
    /*
    
    public void medEmergency(Person patient, MedicalOfficer med, Facilities facility, Resources reqResources, int HP) {
        if(facility != null && !facility.isOpStatus()) {
            System.out.println("Emergency Unresolved! Facility is Inoperational!");
            return;
        }
        if(med == null || !med.available()) {
            System.out.println("Medical Officier Unavailable!");
            return;
        }
        int reqMedKits = (reqResources != null) ? reqResources.getMedicalSup_count() : 0;
        if(colony.getTotalResources("medicine") < reqMedKits) {
            System.out.println("Insufficient Medical Supplies!");
            return;
        }
        boolean success = true;
        if(reqMedKits > 0) {
            success &= colony.consumeResources("medicine",reqMedKits);
        }
        if(success) {
            if(facility != null) {
                med.setLocation(facility.getLocation());
            }
            patient.heal(HP);
            this.solved = true;
            System.out.println("Medical Emergency successfully resolved!");
            System.out.println("Patient "+patient.getName()+" has been treated. Current Health: "+patient.getHealth());
        } else {
            System.out.println("Emergency Unsolved! Resource Allocation Failed!");
        }
    } */
}
