package personnel;

public abstract class Person {
    private String name;
    private String id;
    private int health;
    private String currentLocation;
    private int fatigueLevel;
    private boolean isAvailable;
    
    public Person(String name, String id, String currentLocation) {
        this.name=name;
        this.id=id;
        this.currentLocation=currentLocation;
        this.health=100;
        this.fatigueLevel=0;
        this.isAvailable=true;
        
    }
    
    public abstract boolean canPerform(String task); //yes or no question
    
    public boolean available() {
        return isAvailable;
    }
    public void setAvailable(boolean available) {
        isAvailable=available;
    }
    public String getLocation() {
        return currentLocation;
    }
    public void setLocation(String location) {
        this.currentLocation=location;
    }
    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }
    public int getHealth() {
        return health;
    }
    public int getFatigueLevel() {
        return fatigueLevel;
    }
    
}
