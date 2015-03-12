package cs3524.mud;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Represents a location on a map.
 * A location can be considered as a vertex of a graph.
 *
 * @author Grzegorz 'Greg' Muszynski <g.muszynski.12@aberdeen.ac.uk>
 */

public class Location implements Serializable {
    /** The name of the location. */
    private String name;

    /** The description of a location */
    private String description;

    /**
     * The list of names of players, who are currently
     * at this location.
     */
    private List<String> playersNames;

    /**
     * The list of items stored in the location.
     */
    private List<Item> items;

    /**
     * The hash of destinations on the map.
     * A destination is a location reachable from this location.
     * Any location can have a maximum of four destinations.
     * If there is no location in the map definition in a given
     * direction, going from this location, then the map associates
     * a {@code null} with that direction.
     */
    private Map<Direction, Location> destinations;

    /**
     * Creates a location with just a name.
     * The rest defaults to empty strings, lists and maps.
     *
     * @param name      the name of the location
     */
     public Location(String name) {
        this.name         = name;
        this.description  = "";
        this.items        = new ArrayList<Item>();
        this.playersNames = new ArrayList<String>();
        this.destinations = new HashMap<Direction, Location>();
    }

    /**
     * Creates a location with a name and a description.
     * The rest defaults to empty lists and maps.
     *
     * @param name          the name of the location
     * @param description   the description of a location
     */
    public Location(String name, String description) {
        this.name         = name;
        this.description  = description;
        this.items        = new ArrayList<Item>();
        this.playersNames = new ArrayList<String>();
        this.destinations = new HashMap<Direction, Location>();
    }

    /**
     * Creates a location with a name, a description
     * and a number of specified items.
     * The rest defaults to an empty list and and an empty map.
     *
     * @param name          the name of the location
     * @param description   the description of a location
     * @param items         the list of items, which will be stored
     *                      at this location upon creation
     */
    public Location(String name, String description, ArrayList<Item> items) {
        this.name         = name;
        this.description  = description;
        this.items        = items;
        this.playersNames = new ArrayList<String>();
        this.destinations = new HashMap<Direction, Location>();
    }

    /**
     * Compares two instances of the {@link Location} class.
     *
     * For this project, it was assumed, that any two locations, which have the same
     * name are considered equal.
     * This is mainly to simplify searching through the list of locations in a {@link World}
     * using the standard methods like {@code .contains()}.
     *
     * @param obj   an object to compare to
     */
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

    /** Returns the name of the location. */
    public String getName() {
        return this.name;
    }

    /** Returns the description of the location. */
    public String getDescription() {
        return this.description;
    }

    /** Returns the map of destinations accessible from this location. */
    public Map<Direction, Location> getDestinations() {
        return this.destinations;
    }

    /**
     * Stores an item at the location.
     *
     * @param item  the item to be stored at this location
     */
    public void store(Item item) {
        this.items.add(item);
    }

    /** Returns the list of items stored at this location. */
    public List<Item> listItems() {
        return this.items;
    }

    /** Lists the names of all players currently at this location. */
    public List<String> listPlayerNames() {
        return this.playersNames;
    }

    /** Places a player at this location. */
    public void addPlayer(String p) {
        this.playersNames.add(p);
    }

    /** Removes a player from this location. */
    public void removePlayer(String p) {
        this.playersNames.remove(p);
    }

    /**
     * Returns the location which is accessible from
     * this location, when going in the specified direction.
     */
    public Location to_the(Direction d) {
        return destinations.get(d);
    }
}