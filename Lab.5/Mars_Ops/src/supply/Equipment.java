package supply;

public class Equipment {

    private String id;
    private String name;
    private boolean damaged;

    public Equipment(String id, String name, boolean damaged) {
        this.id = id;
        this.name = name;
        this.damaged = damaged;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isDamaged() {
        return damaged;
    }

    public void setDamaged(boolean damaged) {
        this.damaged = damaged;
    }

    public void repairEquipment() {
        if (damaged) {
            damaged = false;
            System.out.println("Equipment " + name + " (" + id + ") has been repaired successfully!");
        } else {
            System.out.println("Equipment " + name + " (" + id + ") does not require repairing.");
        }
    }
}
