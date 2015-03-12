package cs3524.mud;

import java.io.Serializable;

/**
 * Represents a path between two locations.
 * A path can be considered as an (undirected) edge between
 * two vertices in a graph (i.e. an MUD).
 * 
 * @author Grzegorz 'Greg' Muszynski <g.muszynski.12@aberdeen.ac.uk>
 */

public class Path implements Serializable {
    /** The starting location of a path. */
    public Location startLocation;

    /** The endpoint of a path. */
    public Location destination;

    /** The direction of a path. */
    public Direction direction;

    /** A brief description of the appearance of a path. */
    public String description;

    /**
     * Creates a {@link Path}, by connecting two locations.
     *
     * @param l         location, where the path starts
     * @param dest      location, where the path ends
     * @param dir       direction of a path, which is an instance of {@link Direction} (e.g. north)
     * @param descr     visual description of a path
     */
    public Path(Location l, Location dest, Direction dir, String descr) {
        this.startLocation = l;
        this.destination = dest;
        this.direction = dir;
        this.description = descr;
    }

    public String toString() {
        String response =  this.startLocation.getName() + " (" + this.direction + ")";
        if (this.direction.toString().length() == 4) {
            response += "  -> " + this.description + " to " + this.destination.getName();
        }
        else {
            response += " -> " + this.description + " to " + this.destination.getName();
        }
        return response;
    }

    /**
     * Compares two instances of the {@link Path} class.
     *
     * For this project, it was assumed, that any two paths, which have the same
     * start- and endpoints and the same direction are considered equal.
     * This is used mainly to allow searching through the list of all paths in a {@link World}
     * using the standard methods like {@code .contains()}.
     *
     * @param obj   an object to compare to
     */
    public boolean equals(Object obj) {
        if (!(obj instanceof Path)) {
            return false;
        }
        else if (obj == this) {
            return true;
        }
        else {
            Path other = (Path) obj;
            if (this.startLocation.equals(other.startLocation)
                && this.destination.equals(other.destination)
                && this.direction.equals(other.direction)) {
                return true;
            }
            else {
                return false;
            }
        }
    }
}