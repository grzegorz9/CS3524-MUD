package cs3524.mud;

import java.io.Serializable;

/**
 * @author Grzegorz 'Greg' Muszynski <g.muszynski.12@aberdeen.ac.uk>
 * 
 */
class Path implements Serializable {
    public Location startLocation;
    public Location destination;
    public Direction direction;
    public String description;

    /**
     * Creates a {@link Path}, by connecting two locations.
     *
     * @param s         location, where the path starts
     * @param dest      location, where the path ends
     * @param dir       direction of a path (e.g. north)
     * @param descr     visual description of a path
     */
    public Path(Location s, Location dest, Direction dir, String descr) {
        this.startLocation = s;
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