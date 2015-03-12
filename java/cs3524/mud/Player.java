package cs3524.mud;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Represents a player in an MUD world.
 * 
 * @author Grzegorz 'Greg' Muszynski <g.muszynski.12@aberdeen.ac.uk>
 */

public class Player implements Serializable {
    private String name;
    private double maxCarryWeight;
    private List<Item> equipment;
    private Location currentLocation;

    /**
     * Creates a player with a given name.
     * This player will not be able to pick up any items, since
     * the value of {@code maxCarryWeight} is set to zero.
     * This player will also have no items in the equipment.
     *
     * @param name  the name of the new player
     */
    public Player(String name) {
        this.name           = name;
        this.maxCarryWeight = 0;
        this.equipment      = new ArrayList<Item>();
    }

    /**
     * Creates a player with a given name and the ability to carry
     * items of a total weight specified.
     * This player will also have no items in the equipment.
     *
     * @param name              the name of the new player
     * @param maxCarryWeight    the total weight of items the player can carry
     */
    public Player(String name, double maxCarryWeight) {
        this.name           = name;
        this.maxCarryWeight = maxCarryWeight;
        this.equipment      = new ArrayList<Item>();
    }

    /**
     * Creates a player with a given name and the ability to carry
     * items of a total weight specified. This player will be carrying
     * items from the list provided.
     *
     * @param name              the name of the new player
     * @param maxCarryWeight    the total weight of items the player can carry
     * @param equipment         the list of item the player will be equipped with
     */
    public Player(String name, double maxCarryWeight, ArrayList<Item> equipment) {
        this.name           = name;
        this.maxCarryWeight = maxCarryWeight;
        this.equipment      = equipment;
    }

    /**
     * Compares two players.
     * For this project, it was assumed, that a player is identified by their
     * name, so no two players can have the same name (within one MUD). Therefore,
     * if two players have exactly the same name, they are considered the same.
     *
     * @param obj       the object to compare to
     */
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

    /**
     * Makes the player pick an item up.
     * Only an item from the player's current location can be picked up.
     *
     * @param item      the item to be picked up
     */
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

    /**
     * Returns the player's name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the player's current location.
     */
    public Location getCurrentLocation() {
        return this.currentLocation;
    }

    /**
     * Sets the player's current location to a specified one.
     *
     * @param l     the new location
     */
    public void setCurrentLocation(Location l) {
        this.currentLocation = l;
    }

    /**
     * Adds an item to player's equipment list.
     *
     * @param i     the item to be added to the player's equipment
     */
    public void addToEquipment(Item i) {
        this.equipment.add(i);
    }

    /**
     * Returns a list of items, currently carried by the player.
     */
    public List<Item> listEquipment() {
        return this.equipment;
    }
}