package cs3524.mud;

import java.rmi.RemoteException;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * The implementation of a world with locations, paths and players.
 * 
 * @author Grzegorz 'Greg' Muszynski <g.muszynski.12@aberdeen.ac.uk>
 */

public class World implements MUD {
    /** The name of the world. */
	private String name;

    /** The starting location for any new user created in this world. */
	private Location startingLocation;

    /** The complete list of locations on the map. */
    private List<Location> locations;

    /** The complete list of paths on the map. */
    private List<Path> paths;

    /** The list of all players registered within this world. */
	private List<Player> activePlayers;

    /** A short list of commands for the users. */
    private final String manual =
        "Commands:\n"         +
        " -  help\n"           +
        " -  look\n"    +
        " -  go <direction>\n" +
        " -  take <items>";
    
    /** A brief introduction for new users. */
    private final String introTutorial =
        "Try typing 'look' to find out where you are.\n" +
        "If you feel adventureous, you can visit any of the destinations\n" +
        "by typing 'go <direction>', where <direction> can be any of:\n" +
        "north, east, south or west.";
    
    /**
     * Creates a new world with a given name.
     *
     * @param name  the name of the new world
     */
    public World(String name) throws RemoteException {
        this.name = name;
        this.locations = new ArrayList<Location>();
        this.paths = new ArrayList<Path>();
        this.activePlayers = new ArrayList<Player>();
    }

    /** Returns the name of the world. */
    public String getName() {
        return this.name;
    }

    /**
     * Generates a map for the world.
     * This method requires a file, which map definitions
     * in the specified format. Based on the input, a number of
     * locations is generated. These locations are then connected
     * through paths.
     *
     * @param filename  the name of the file containing the map
     */
    public void generateMap(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            for (String line; (line = br.readLine()) != null; ) {
                StringTokenizer st = new StringTokenizer(line, ";");
                while (st.hasMoreTokens()) {
                    String locName = st.nextToken().trim();
                    Direction dir = Direction.valueOf(st.nextToken().trim().toUpperCase());
                    String descr = st.nextToken().trim();
                    String dest = st.nextToken().trim();

                    Location l = new Location(locName);
                    Location d = null;
                    if (!this.locations.contains(l)) {
                        this.locations.add(l);
                    }
                    else {
                        l = this.locations.get(this.locations.indexOf(l));
                    }
                    d = new Location(dest);
                    if (!this.locations.contains(d)) {
                        this.locations.add(d);
                    }
                    else {
                        d = this.locations.get(this.locations.indexOf(d));
                    }
                    l.getDestinations().put(dir, d);
                    d.getDestinations().put((oppositeTo(dir)), l);
                    this.paths.add(new Path(l, d, dir, descr));
                    this.paths.add(new Path(d, l, oppositeTo(dir), descr));
                }
            }
            this.startingLocation = this.locations.get(0);
        }
        catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /**
     * Populates the map with items.
     * This method requires a file, which contains item
     * definitions and their respective locations.
     *
     * @param filename  the name of the file containing the items
     */
    public void placeItems(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            for (String line; (line = br.readLine()) != null; ) {
                StringTokenizer st = new StringTokenizer(line, ";");
                while (st.hasMoreTokens()) {
                    String locName = st.nextToken().trim();
                    String itemName = st.nextToken().trim();
                    double itemWeight = Double.parseDouble(st.nextToken().trim());

                    Item newItem = new Item(itemName, itemWeight);
                    this.locations.get(this.locations.indexOf(new Location(locName))).listItems().add(newItem);
                }
            }
        }
        catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    /** Returns the starting point for every new player created in this world. */
    public Location getStartingLocation() throws RemoteException {
        return startingLocation;
    }

    /** Returns the list of all locations on the map of the world. */
    public List<Location> getLocations() {
        return locations;
    }

    /**
     * Adds a player to the world.
     *
     * @param p     the player to be added
     */
    public void join(Player p) {
        if (!activePlayers.contains(p)) {
            activePlayers.add(p);
            locations.get(locations.indexOf(startingLocation)).addPlayer(p.getName());
        }
    }


    /**
     * Removes a player to the world.
     *
     * @param p     the player to be removed
     */
    public void leave(Player p) {
        this.locations.get(this.locations.indexOf(p.getCurrentLocation())).removePlayer(p.getName());
        activePlayers.remove(p);
    }

    public String manual() throws RemoteException {
    	return manual;
    }

    public String intro() throws RemoteException {
    	return introTutorial;
    }

    public Map<Direction, Location> listAdjacentTo(Location l) {
        if (this.locations.contains(l)) {
            return this.locations.get(this.locations.indexOf(l)).getDestinations();
        }
        else {
            return null;
        }
    }

    public Location from(Location l, Direction d) {
        if (this.locations.contains(l)) {
            Location dest = listAdjacentTo(l).get(d);
            if (dest != null) {
                return dest;
            }
        }
        return null;
    }

    public List<Path> listPaths() {
        return this.paths;
    }

    public void movePlayer(Player p, Location l) {
        if (this.locations.contains(l)) {
            Player plr = activePlayers.get(activePlayers.indexOf(p));
            this.locations.get(this.locations.indexOf(plr.getCurrentLocation())).removePlayer(p.getName());
            this.locations.get(this.locations.indexOf(l)).addPlayer(p.getName());
        }
    }

    public List<String> listPlayersAt(Location l) {
        if (this.locations.contains(l)) {
            return this.locations.get(this.locations.indexOf(l)).listPlayerNames();
        }
        return null;
    }

    public boolean isValidPlayerName(String name) {
        Pattern correctPlayerName = Pattern.compile("^[\\w ]{3,}$");
        Pattern multipleSpaces = Pattern.compile(" +");
        Pattern digitsOnly = Pattern.compile("^\\d+$");
        Pattern startingWithSpace = Pattern.compile("^ ");
        Pattern endingWithSpace = Pattern.compile(" $");
        Matcher playerNameMatcher = correctPlayerName.matcher(name);
        Matcher multiSpaceMatcher = multipleSpaces.matcher(name);
        Matcher digitsOnlyMatcher = digitsOnly.matcher(name);
        Matcher wrongFirstChar = startingWithSpace.matcher(name);
        Matcher wrongLastChar = endingWithSpace.matcher(name);

        if (name.isEmpty()) {
            return false;
        }
        else if (playerNameMatcher.matches()
                && !multiSpaceMatcher.matches() && !wrongFirstChar.matches()
                && !wrongLastChar.matches() && !digitsOnlyMatcher.matches()) {
                return true;
        }
        return false;
    }

    public void removeItem(Location l, Item i) {
        this.locations.get(this.locations.indexOf(l)).listItems().remove(i);
    }

    public boolean isUniquePlayerName(String name) {
        if (activePlayers.contains(new Player(name))) {
            return false;
        }
        return true;
    }

    /**
     * A helper method to define directions opposite to each other.
     * Used by the map generator to create paths in both directions,
     * since a world map is represented by an undirected graph.
     */
    public static Direction oppositeTo(Direction d) {
        if (d == Direction.NORTH) {
            return Direction.SOUTH;
        }
        else if (d == Direction.SOUTH) {
            return Direction.NORTH;
        }
        else if (d == Direction.EAST) {
            return Direction.WEST;
        }
        else if (d == Direction.WEST) {
            return Direction.EAST;
        }
        return null;
    }
}
