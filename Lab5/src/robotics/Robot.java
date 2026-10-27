
package robotics;
import Missions.*;
import Emergencies.*;

public abstract class Robot {
    private int batteryLevel;
    private boolean damageStatus;
    private String location;
    private boolean available;
    
   public Robot(int batteryLevel, String damageStatus, String location){
       this.batteryLevel = batteryLevel;
       this.location = location;
       this.damageStatus = damageStatus;
       this.available = true;
   }
   
   public void recharge(){
       batteryLevel = 100;
   }
   //check whether a robot can perform a mission
   public boolean canPerform(Mission mission){
       return available && batteryLevel>20 && damageStatus;
   }
      //check whether a robot can respond to an emergency
   public boolean canHandle(Emergency emergency){
       return damageStatus && available && batteryLevel>20;

   }
   
   public abstract void performTask();//diff for every robot
    
   //getters
   public int getBatteryLevel(){
       return batteryLevel;         
   }
    
   public String getLocation(){
       return location;
   }
   public boolean isAvailable(){
       return available;
   }
   public boolean getDamageStatus(){
       return damageStatus;
   }
   public void setBatteryLevel(int batteryLevel) {
    if (batteryLevel >= 0 && batteryLevel <= 100) {
        this.batteryLevel = batteryLevel;
    }
}
    
   
}
