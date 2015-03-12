package cs3524.mud;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Location implements Serializable {
    private String name;
    private String description;
    private List<String> playersNames;
    private List<Item> items;
    private Map<Direction, Location> destinations;

    public Location(String name) {
        this.name         = name;
        this.description  = "";
        this.items        = new ArrayList<Item>();
        this.playersNames = new ArrayList<String>();
        this.destinations = new HashMap<Direction, Location>();
    }

    public Location(String name, String description) {
        this.name         = name;
        this.description  = description;
        this.items        = new ArrayList<Item>();
        this.playersNames = new ArrayList<String>();
        this.destinations = new HashMap<Direction, Location>();
    }

    public Location(String name, String description, ArrayList<Item> items) {
        this.name         = name;
        this.description  = description;
        this.items        = items;
        this.playersNames = new ArrayList<String>();
        this.destinations = new HashMap<Direction, Location>();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Location)) {
            return false;
        }
        else if (obj == this) {
            return true;
        }
        else {
            Location l = (Location) obj;
            if (l.name.equals(this.name)) {
                return true;
            }
            else {
                return false;
            }
        }
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public Map<Direction, Location> getDestinations() {
        return this.destinations;
    }

    public void store(Item item) {
        this.items.add(item);
    }

    public List<Item> listItems() {
        return this.items;
    }

    public List<String> listPlayerNames() {
        return this.playersNames;
    }

    public void addPlayer(String p) {
        this.playersNames.add(p);
    }

    public void removePlayer(String p) {
        this.playersNames.remove(p);
    }

    public Location to_the(Direction d) {
        return destinations.get(d);
    }
}