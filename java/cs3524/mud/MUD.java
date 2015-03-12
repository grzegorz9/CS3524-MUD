package cs3524.mud;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface MUD extends Remote {
    /* Returns the name of an MUD */
    public String getName() throws RemoteException;

    /*
     * Returns the starting location of an MUD.
     * It's the location, where every user starts their journey.
     */
    public Location getStartingLocation() throws RemoteException;

    /*
     * Checks if a name is already used by a player in an MUD.
     * Since, players are uniquely identified by their names in an MUD,
     * every player has to have a unique name.
     */
    public boolean isUniquePlayerName(String name) throws RemoteException;

    /* Checks if a name provided by the user, can be used in an MUD. */
    public boolean isValidPlayerName(String name) throws RemoteException;

    /* Adds a player to an MUD */
    public boolean join(Player p) throws RemoteException;

    /* Removes a player from an MUD */
    public void leave(Player p) throws RemoteException;

    /* Returns a list of commands and some technical info. */
    public String manual() throws RemoteException;

    /* Returns a short introduction to the game controls. */
    public String intro() throws RemoteException;

    /*
     * Lists locations adjacent to a given location.
     * It's used by the client application, to check if the user
     * can move in a particular direction.
     */
    public Map<Direction, Location> listAdjacentTo(Location l) throws RemoteException;

    /* Returns a location in a given direction from a given location. */
    public Location from(Location l, Direction d) throws RemoteException;

    /* Lists player names at a given location */
    public List<String> listPlayersAt(Location l) throws RemoteException;

    /* Moves a player to a given destination location */
    public void movePlayer(Player p, Location destination) throws RemoteException;

    /* Returns a list of all locations in an MUD. */
    public List<Location> getLocations() throws RemoteException;

    /* Removes an item from a given location */
    public void removeItem(Location l, Item i) throws RemoteException;
}