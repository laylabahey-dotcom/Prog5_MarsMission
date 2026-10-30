package Personnel;

public class Engineer extends Person {

    public Engineer(String name, String id, String currentLocation) {
        super(name, id, currentLocation);
    }

    @Override
    public boolean canPerform(String task) {
        return task.equalsIgnoreCase("Repair") ||
                task.equalsIgnoreCase("Rescue");
    }
}
