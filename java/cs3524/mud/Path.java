package cs3524.mud;

/**
 * @author Grzegorz 'Greg' Muszynski <g.muszynski.12@aberdeen.ac.uk>
 * 
 */
class Path {
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

    /**
     * Creates a string from a {@link Path}.
     */
    public String toString() {
        return this.startLocation.getName() + ":" + System.lineSeparator()
            + this.direction + " -> " + this.description + " to "
            + this.destination.getName();
    }
}