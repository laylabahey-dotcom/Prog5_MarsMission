package supply;

public class Resources {

    private int O2_count;
    private int H2O_count;
    private int food_count;
    private int fuel_count;
    private int battery_units_count;
    private int medicalSup_count;
    private int spare_count;

    public Resources(int O2_count, int H2O_count, int food_count, int fuel_count, int battery_units_count, int medicalSup_count, int spare_count) {
        if (O2_count < 0) {
            System.out.println("Invalid Quantity!");
        } else {
            this.O2_count = O2_count;
        }
        if (H2O_count < 0) {
            System.out.println("Invalid Quantity!");
        } else {
            this.H2O_count = H2O_count;
        }
        if (food_count < 0) {
            System.out.println("Invalid Quantity!");
        } else {
            this.food_count = food_count;
        }
        if (fuel_count < 0) {
            System.out.println("Invalid Quantity!");
        } else {
            this.fuel_count = fuel_count;

        }
        if (battery_units_count < 0) {
            System.out.println("Invalid Quantity!");
        } else {
            this.battery_units_count = battery_units_count;
        }
        if (medicalSup_count < 0) {
            System.out.println("Invalid Quantity!");
        } else {
            this.medicalSup_count = medicalSup_count;
        }
        if (spare_count < 0) {
            System.out.println("Invalid Quantity!");
        } else {
            this.spare_count = spare_count;
        }
    }

    public int getO2_count() {
        return O2_count;
    }

    public int getH2O_count() {
        return H2O_count;
    }

    public int getFood_count() {
        return food_count;
    }

    public int getFuel_count() {
        return fuel_count;
    }

    public int getBattery_units_count() {
        return battery_units_count;
    }

    public int getMedicalSup_count() {
        return medicalSup_count;
    }

    public int getSpare_count() {
        return spare_count;
    }

    public void setO2_count(int O2_count) {
        if (O2_count < 0) {
            System.out.println("Invalid Input!");
            return;
        }
        this.O2_count = O2_count;
    }

    public void setH2O_count(int H2O_count) {
        if (H2O_count < 0) {
            System.out.println("Invalid Input!");
            return;
        }
        this.H2O_count = H2O_count;
    }

    public void setFood_count(int food_count) {
        if (food_count < 0) {
            System.out.println("Invalid Input!");
            return;
        }
        this.food_count = food_count;
    }

    public void setFuel_count(int fuel_count) {
        if (fuel_count < 0) {
            System.out.println("Invalid Input!");
            return;
        }
        this.fuel_count = fuel_count;
    }

    public void setBattery_units_count(int battery_units_count) {
        if (battery_units_count < 0) {
            System.out.println("Invalid Input!");
            return;
        }
        this.battery_units_count = battery_units_count;
    }

    public void setMedicalSup_count(int medicalSup_count) {
        if (medicalSup_count < 0) {
            System.out.println("Invalid Input!");
            return;
        }
        this.medicalSup_count = medicalSup_count;
    }

    public void setSpare_count(int spare_count) {
        if (spare_count < 0) {
            System.out.println("Invalid Input!");
            return;
        }
        this.spare_count = spare_count;
    }

    public boolean oxygenAddition(int quantity) {
        if (quantity <= 0) {
            System.out.println("Invalid Amount!");
            return false;
        }
        if (O2_count > Integer.MAX_VALUE - quantity) {
            return false;
        }
        O2_count += quantity;
        return true;
    }

    public boolean waterAddition(int quantity) {
        if (quantity <= 0) {
            System.out.println("Invalid Amount!");
            return false;
        }
        if (H2O_count > Integer.MAX_VALUE - quantity) {
            return false;
        }
        H2O_count += quantity;
        return true;
    }

    public boolean foodAddition(int quantity) {
        if (quantity <= 0) {
            System.out.println("Invalid Amount!");
            return false;
        }
        if (food_count > Integer.MAX_VALUE - quantity) {
            return false;
        }
        food_count += quantity;
        return true;
    }

    public boolean fuelAddition(int quantity) {
        if (quantity <= 0) {
            System.out.println("Invalid Amount!");
            return false;
        }
        if (fuel_count > Integer.MAX_VALUE - quantity) {
            return false;
        }
        fuel_count += quantity;
        return true;
    }

    public boolean batteryAddition(int quantity) {
        if (quantity <= 0) {
            System.out.println("Invalid Amount!");
            return false;
        }
        if (battery_units_count > Integer.MAX_VALUE - quantity) {
            return false;
        }
        battery_units_count += quantity;
        return true;
    }

    public boolean medAddition(int quantity) {
        if (quantity <= 0) {
            System.out.println("Invalid Amount!");
            return false;
        }
        if (medicalSup_count > Integer.MAX_VALUE - quantity) {
            return false;
        }
        medicalSup_count += quantity;
        return true;
    }

    public boolean spareAddition(int quantity) {
        if (quantity <= 0) {
            System.out.println("Invalid Amount!");
            return false;
        }
        if (spare_count > Integer.MAX_VALUE - quantity) {
            return false;
        }
        spare_count += quantity;
        return true;
    }

    public boolean oxygenSub(int quantity) {
        if (quantity <= 0) {
            System.out.println("Invalid Amount!");
            return false;
        }
        if (O2_count - quantity < 0) {
            System.out.println("Insufficient Amount!");
            return false;
        }
        O2_count -= quantity;
        return true;
    }

    public boolean waterSub(int quantity) {
        if (quantity <= 0) {
            System.out.println("Invalid Amount!");
            return false;
        }
        if (H2O_count - quantity < 0) {
            System.out.println("Insufficient Amount!");
            return false;
        }
        H2O_count -= quantity;
        return true;
    }

    public boolean foodSub(int quantity) {
        if (quantity <= 0) {
            System.out.println("Invalid Amount!");
            return false;
        }
        if (food_count - quantity < 0) {
            System.out.println("Insufficient Amount!");
            return false;
        }
        food_count -= quantity;
        return true;
    }

    public boolean fuelSub(int quantity) {
        if (quantity <= 0) {
            System.out.println("Invalid Amount!");
            return false;
        }
        if (fuel_count - quantity < 0) {
            System.out.println("Insufficient Amount!");
            return false;
        }
        fuel_count -= quantity;
        return true;
    }

    public boolean batterySub(int quantity) {
        if (quantity <= 0) {
            System.out.println("Invalid Amount!");
            return false;
        }
        if (battery_units_count - quantity < 0) {
            System.out.println("Insufficient Amount!");
            return false;
        }
        battery_units_count -= quantity;
        return true;
    }

    public boolean medSub(int quantity) {
        if (quantity <= 0) {
            System.out.println("Invalid Amount!");
            return false;
        }
        if (medicalSup_count - quantity < 0) {
            System.out.println("Insufficient Amount!");
            return false;
        }
        medicalSup_count -= quantity;
        return true;
    }

    public boolean spareSub(int quantity) {
        if (quantity <= 0) {
            System.out.println("Invalid Amount!");
            return false;
        }
        if (spare_count - quantity < 0) {
            System.out.println("Insufficient Amount!");
            return false;
        }
        spare_count -= quantity;
        return true;
    }

    public void displayResources() {
        System.out.println("Current Resources:");
        System.out.println("1) Oxygen = " + O2_count + " units.");
        System.out.println("2) Water = " + H2O_count + " units.");
        System.out.println("3) Food = " + food_count + " meals.");
        System.out.println("4) Fuel = " + fuel_count + " tanks.");
        System.out.println("5) Battery Units = " + battery_units_count);
        System.out.println("6) Medical Supplies = " + medicalSup_count + " kits");
        System.out.println("7) Spare Parts = " + spare_count + " parts");
    }
}
