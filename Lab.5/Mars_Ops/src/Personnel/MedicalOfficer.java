package Personnel;

public class MedicalOfficer extends Person {

    public MedicalOfficer(String name, String id, String currentLocation) {
        super(name, id, currentLocation);
    }

    @Override
    public boolean canPerform(String task) {
        return task.equalsIgnoreCase("MedicalEmergency");
    }
}
