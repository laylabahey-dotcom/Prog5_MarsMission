package Personnel;

public class FireFighter extends Person {

    public FireFighter(String name, String id, String currentLocation) {
        super(name, id, currentLocation);
    }

    @Override
    public boolean canPerform(String task) {
        return task.equalsIgnoreCase("Fire");
    }
}
