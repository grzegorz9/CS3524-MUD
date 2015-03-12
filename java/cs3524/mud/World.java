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
import java.util.HashMap;

public class World implements MUD {
	private String name;
	private Location startingLocation;
    private List<Location> locations;   // i.e. vertices
    private List<Path> paths;    // i.e. edges
	private List<Player> activePlayers;

    public String getName() {
        return this.name;
    }

	private final String manual =
		"Commands:\n"         +
		" -  help\n"           +
		" -  look\n"    +
		" -  go <direction>\n" +
		" -  take <items>";
	private final String introTutorial =
		"Try typing 'look' to find out where you are.\n" +
		"If you feel adventureous, you can visit any of the destinations\n" +
		"by typing 'go <direction>', where <direction> can be any of:\n" +
		"north, east, south or west.";
    
    public World(String name) throws RemoteException {
    	this.name = name;
        this.locations = new ArrayList<Location>();
        this.paths = new ArrayList<Path>();
    	this.activePlayers = new ArrayList<Player>();
    }

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

    public Location getStartingLocation() throws RemoteException {
        return startingLocation;
    }

    public List<Location> getLocations() {
        return locations;
    }

    public String testMessage() throws RemoteException {
        return "Testing: 1, 2, 3.";
    }

    public boolean join(Player p) {
        if (!activePlayers.contains(p)) {
        	activePlayers.add(p);
            locations.get(locations.indexOf(startingLocation)).addPlayer(p.getName());
            return true;
        }
        return false;
    }

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
        if (name.isEmpty()) {
            return false;
        }
        return true;
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
