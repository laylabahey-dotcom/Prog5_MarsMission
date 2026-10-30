package emergency;

import colony_facilities.*;
import Personnel.*;
import main.*;
import supply.*;

public class Fire extends Emergency { //changed a lot of stuff here -k

    private Laboratory targetLab;
    private int fireLevel;//o2 loss and resource cost
    private Resources reqResources;
    private FireFighter fireResponder;
    private MedicalOfficer medicalResponder;

    public Fire(String location, Laboratory targetLab, int fireLevel, Resources reqResources, FireFighter fireResponder, MedicalOfficer medicalResponder, MarsOps colony) {
        super("Fire", location, colony);

        if (fireLevel < 1 || fireLevel > 100) {
            throw new IllegalArgumentException("Fire severity must be between 1 and 100.");
        }
        this.targetLab = targetLab;
        this.fireLevel = fireLevel;
        this.reqResources = reqResources;
        this.fireResponder = fireResponder;
        this.medicalResponder = medicalResponder;
        
        if(fireResponder!=null) addResponder(fireResponder);
        if(medicalResponder!=null) addResponder(medicalResponder); //populating list
        applyInitialFireImpact();   
    }   
    
    private void applyInitialFireImpact() { //helper function
        if (targetLab != null) {
            targetLab.setOpStatus(false);//unavailable lab
            System.out.println("WARNING! Fire at " + location + "! Laboratory is unavailable.");

            //damaged equipment
            Equipment labEquipment = targetLab.getEquipment();
            if (labEquipment != null) {
                labEquipment.setDamaged(true);
            }
            //o2 increase
            if (targetLab.getResource() != null) {
                Resources resource = targetLab.getResource();
                int availableO2 = resource.getO2_count();

                int lossO2 = fireLevel * 2;//burns
                int actualSub = lossO2;
                if (availableO2 < lossO2) {
                    actualSub = availableO2; //insufficient o2 to subtract from

                }
                if (actualSub > 0) {
                    targetLab.removeResources("oxygen", actualSub);
                    System.out.println("WARNING: Fire consumed " + actualSub + " units of oxygen in " + location);
                }
            }
        }
    }

    public Fire(String location, Laboratory targetLab, int fireLevel, MarsOps colony) {
        super("Fire", location, colony);
        if (fireLevel < 1 || fireLevel > 100) {
            throw new IllegalArgumentException("Fire severity must be between 1 and 100.");
        }
        this.targetLab = targetLab;
        this.fireLevel = fireLevel;
        applyInitialFireImpact();
    }

    @Override
    public void resolveEmergency() { //changed stuff here -k
        //valid lab reference
        if (targetLab == null) {
            System.out.println("Error in resolving! Target laboratory does not exist.");
            return;
        }
        if (fireResponder == null || !fireResponder.available()) {
            for(Person p:colony.getAllCrewMembers()) {
                if(p.canPerform("Firefighting")&&p.available()) {
                    fireResponder=(FireFighter) p;
                    addResponder(fireResponder);
                    break;
                }
            }
        }
        
        if (medicalResponder == null || !medicalResponder.available()) {
            for(Person p:colony.getAllCrewMembers()) {
                if(p.canPerform("Medical")&&p.available()) {
                    medicalResponder=(MedicalOfficer) p;
                    addResponder(medicalResponder);
                    break;
                }
            }
        }
        
        if (fireResponder == null || !fireResponder.available() || medicalResponder == null || !medicalResponder.available()) {
            System.out.println("Emergency unresolved! Required responders are unavailable.");
            return;
        }
        
        //check colony resources
        int reqParts = 0;
        if (reqResources != null) {
            reqParts = reqResources.getSpare_count();

        }
        if (colony.getTotalResources("parts") < reqParts) {
            System.out.println("Error in resolving! Insufficient colony parts/supplies for fire suppression.");
            return;
        }
        boolean success = true;
        if (reqParts > 0) {
            success = colony.consumeResources("parts", reqParts);
        }
        if (success) {
            //dispatch responders to lab location
            fireResponder.setLocation(targetLab.getLocation());
            medicalResponder.setLocation(targetLab.getLocation());

            //repair
            Equipment labEquipment = targetLab.getEquipment();
            if (labEquipment != null && labEquipment.isDamaged()) {
                labEquipment.repairEquipment();
            }
            targetLab.setOpStatus(true);
            this.solved = true;
            System.out.println("Fire at " + location + " has been successfully resolved!");
        }
    }
    
    @Override
    public void checkUnresolved(){
        if(!solved && targetLab != null && targetLab.getResource() != null){
            Resources resource = targetLab.getResource();
            int availableO2 = resource.getO2_count();
            
            //Sub same leak amount again
            
            int addLossO2 = fireLevel * 2;//penalty
            int actualSub = addLossO2;
            if (availableO2 < addLossO2 ) {
                actualSub = availableO2;

            }
            if (actualSub > 0) {
                targetLab.removeResources("oxygen", actualSub);
                System.out.println("Emergency at " + location + " went unresolved! Additional " + actualSub + " units of oxygen lost.");

            }
            
        }
    }
    
}