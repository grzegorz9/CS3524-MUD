package cs3524.mud;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Player implements Serializable {
    private String name;
    private double maxCarryWeight;
    private List<Item> equipment;
    private Location currentLocation;

    public Player(String name) {
        this.name           = name;
        this.maxCarryWeight = 0;
        this.equipment      = new ArrayList<Item>();
    }

    public Player(String name, double maxCarryWeight) {
        this.name           = name;
        this.maxCarryWeight = maxCarryWeight;
        this.equipment      = new ArrayList<Item>();
    }

    public Player(String name, double maxCarryWeight, ArrayList<Item> equipment) {
        this.name           = name;
        this.maxCarryWeight = maxCarryWeight;
        this.equipment      = equipment;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Player)) {
            return false;
        }
        else if (obj == this) {
            return true;
        }
        else {
            Player p = (Player) obj;
            if (p.name.equals(this.name)) {
                return true;
            }
            else {
                return false;
            }
        }
    }

    public void take(Item item) {
        if (this.currentLocation.listItems().contains(item)) {
            double totalEquipmentWeight = 0;
            for (Item i : this.equipment) {
                totalEquipmentWeight += i.weight;
            }
            if (item.weight + totalEquipmentWeight <= this.maxCarryWeight) {
                this.equipment.add(item);
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public Location getCurrentLocation() {
        return this.currentLocation;
    }

    public void setCurrentLocation(Location l) {
        this.currentLocation = l;
    }

    public void addToEquipment(Item i) {
        this.equipment.add(i);
    }

    public List<Item> listEquipment() {
        return this.equipment;
    }
}