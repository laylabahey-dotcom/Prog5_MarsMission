package emergency;

import colony_facilities.*;
import Personnel.*;
import main.*;
import supply.*;

public class Medical extends Emergency {

    private MedicalOfficer doctor;
    private Person patient;
    private Facilities targetFacility;
    private Resources reqMedSupplies;

    public Medical(String location, Person patient, MedicalOfficer doctor, Facilities targetFacility, Resources reqMedSupplies, MarsOps colony) {

        super("Medical Emergency", location, colony);

        this.patient = patient;
        this.doctor = doctor;
        this.targetFacility = targetFacility;
        this.reqMedSupplies = reqMedSupplies;

        // Immediate consequence
        if (patient != null) {
            System.out.println("WARNING: Medical emergency reported for " + patient.getName());
        }
    }

    @Override
    public void resolveEmergency() {

        if (targetFacility != null && !targetFacility.isOpStatus()) {
            System.out.println("Error in resolving! Medical facility is inoperable.");
            return;
        }

        if (doctor == null || !doctor.available()) {
            System.out.println("Emergency unresolved! Required medical officer is unavailable.");
            return;
        }

        //check medical supplies
        int reqMeds = 0;
        if (reqMedSupplies != null) {
            reqMeds = reqMedSupplies.getMedicalSup_count();
        }

        if (colony.getTotalResources("medical") < reqMeds) {
            System.out.println("Error in resolving! Insufficient medical supplies in colony.");
            return;
        }

        //consume medical supplies from the colony
        boolean success = true;
        if (reqMeds > 0) {
            success = colony.consumeResources("medical", reqMeds);
        }

        if (success) {
            //dispatch 
            if (doctor != null) {
                doctor.setLocation(targetFacility != null ? targetFacility.getLocation() : location);
            }

            if (patient != null) {
                patient.heal(25); //increases health points
                System.out.println("Patient " + patient.getName() + " has been successfully treated and health has improved.");
            }

            // Update emergency state
            this.solved = true;
            System.out.println("Medical emergency at " + location + " is resolved successfully!");
        } else {
            System.out.println("Emergency unsolved! Medical supply allocation failed.");
        }
    
}
    @Override
    public String getType() {
        return this.type;
    }

}
