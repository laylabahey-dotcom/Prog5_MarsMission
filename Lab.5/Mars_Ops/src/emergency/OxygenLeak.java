
package emergency;

import colony_facilities.*;
import Personnel.*;
import robots.*;
import main.*;
import supply.*;

public class OxygenLeak extends Emergency {
    private Habitats targetHabitat;
    private int leakRate;
    private Resources reqResources;
    private Engineer engineer;
    private RepairRobot robot;
    
    public OxygenLeak(String location, Habitats targetHabitat, int leakRate, Resources reqResources,Engineer engineer, RepairRobot robot, MarsOps colony){
        super("Oxygen Leak", location, colony); //1st " " for type
        if(leakRate < 0 || leakRate > 100){
            throw new IllegalArgumentException("Leak rate must be between 0 and 100.");
   
        }
        this.leakRate = leakRate;
        this.targetHabitat = targetHabitat;
        this.reqResources = reqResources;
        this.engineer = engineer;
        this.robot = robot;
        
        //reduce o2 from target habitat
        if(targetHabitat != null && leakRate > 0 && targetHabitat.getResource() != null){
            Resources resource = targetHabitat.getResource();
            int availableO2 = resource.getO2_count();
            
            //get value after deduction so that it doesnt drop <0
            int afterO2Sub = leakRate;
            if(availableO2 < leakRate){
                afterO2Sub = availableO2;// -ve after Sub
            }
            if(afterO2Sub > 0){
                //subtract O2 from target habitat
                targetHabitat.removeResources("oxygen",afterO2Sub);
                System.out.println("WARNING! Oxygen leak triggered! " + afterO2Sub + " units lost from " + location);
                
            }
   
    }
        
    
}
    @Override
    public void resolveEmergency(){
        if(targetHabitat != null && !targetHabitat.isOpStatus()){
            System.out.println("Error in resolving! Habitat is inoperable!");
            return;
        }
        //check responders
        if(engineer == null  || !engineer.available() || !robot.isAvailable() || robot == null){
            System.out.println("Emergency unresoved! Required resolvers are unavailable.");
            return;
        }
        //check colony resources
        int reqParts = 0;
        if(reqResources != null){
            reqParts = reqResources.getSpare_count();// #parts needed to fix the leak
        }
        
        if(colony.getTotalResources("parts") < reqParts){
            System.out.println("Error in resolving! Insufficient spare parts.");
            return;
        }
        
        boolean success = true;
        if(reqParts >0){
            success = colony.consumeResources("parts", reqParts);//consumes parts from the colony
        }
        
        if(success){
            engineer.setLocation(targetHabitat.getLocation());
            
            //if habitat equipment is causing the O2 leak; repair using Equipment's method
            Equipment habitatEquipment = targetHabitat.getEquipment();
            if(habitatEquipment != null && habitatEquipment.isDamaged()){
                habitatEquipment.repairEquipment();
            }
            //update state
            targetHabitat.setOpStatus(true);
            this.solved = true;
            System.out.println("Oxygen leak at " + location + "is resolved successfully!"); 
        }
        else{
            System.out.println("Emergency unsolved! Resource allocation failed.");
        }      
    }
    public void checkUnresolved(){
        if(!solved && targetHabitat != null && targetHabitat.getResource() != null ){
            Resources resource = targetHabitat.getResource();
            int availableO2 = resource.getO2_count();
            
            //Sub same leak amount again
            int extraSub = leakRate;
            if(availableO2 < leakRate){
                extraSub = availableO2;
                
            }
            if(extraSub > 0 ){
                targetHabitat.removeResources("oxygen",extraSub);
                System.out.println("Emergency at " + location + " went unresolved! Additional " + extraSub + " units of oxygen lost.");
                
            }
        }
    }
    
    
    
    
}
