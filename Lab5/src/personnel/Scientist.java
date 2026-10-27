package personnel;

public class Scientist extends Person {
    public Scientist(String name, String id, String currentLocation) {
        super(name,id,currentLocation);
    }
    @Override
    public boolean canPerform(String task) {
        return task.equalsIgnoreCase("Research");
    }
}
