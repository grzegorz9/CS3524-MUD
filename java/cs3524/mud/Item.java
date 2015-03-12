package cs3524.mud;

import java.io.Serializable;

/**
 * Represents an item.
 * Any item can be picked up by players.
 *
 * @author Grzegorz 'Greg' Muszynski <g.muszynski.12@aberdeen.ac.uk>
 */

public class Item implements Serializable {
    /** The name of the item. */
    public String name;

    /** The weight of the item. */
    public double weight;

    /**
     * Creates an item with a given name and weight.
     * Any item is identified by name, so the {@code name} field
     * cannot be empty.
     *
     * @param name      the name of the item
     * @param weight    the weight of the item
     */
    public Item(String name, double weight) {
        if (name.equals("")) {
            return;
        }
        this.name   = name;
        this.weight = weight;
    }

    /**
     * Compares two instances of the {@link Item} class.
     *
     * For this project, it was assumed, that any two items, which have the same
     * name are considered equal.
     * This is mainly to simplify searching through the list of items at any {@link Location}
     * using the standard methods like {@code .contains()}.
     *
     * @param obj   an object to compare to
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof Item)) {
            return false;
        }
        else if (obj == this) {
            return true;
        }
        else {
            Item i = (Item) obj;
            if (i.name.equals(this.name)) {
                return true;
            }
            else {
                return false;
            }
        }
    }
}