package cs3524.mud;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Location implements Serializable {
    public String name;
    public String description;
    public List<Player> players;
    public List<Item> items;
    public Map<Direction, Location> destinations;

    public Location(String name) {
        this.name         = name;
        this.description  = "";
        this.items        = new ArrayList<Item>();
        this.players      = new ArrayList<Player>();
        this.destinations = new HashMap<Direction, Location>();
    }

    public Location(String name, String description) {
        this.name         = name;
        this.description  = description;
        this.items        = new ArrayList<Item>();
        this.players      = new ArrayList<Player>();
        this.destinations = new HashMap<Direction, Location>();
    }

    public Location(String name, String description, ArrayList<Item> items) {
        this.name         = name;
        this.description  = description;
        this.items        = items;
        this.players      = new ArrayList<Player>();
        this.destinations = new HashMap<Direction, Location>();
    }

    public void store(Item item) {
        this.items.add(item);
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

    public void addPlayer(Player p) {
        this.players.add(p);
    }

    public void removePlayer(Player p) {
        this.players.remove(p);
    }

    public Location to_the(Direction d) {
        return destinations.get(d);
    }
}