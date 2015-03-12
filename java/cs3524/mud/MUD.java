package cs3524.mud;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * Defines the behaviour of a world (AKA multi-user domain),
 * within which players can perform simple actions, like traveling
 * between different locations, finding items and seeing other players.
 *
 * @author Grzegorz 'Greg' Muszynski <g.muszynski.12@aberdeen.ac.uk>
 */

public interface MUD extends Remote {
    /**
     * Returns the name of an MUD.
     */
    public String getName() throws RemoteException;

    /**
     * Returns the starting location of an MUD.
     * It's the location, where every user starts their journey.
     */
    public Location getStartingLocation() throws RemoteException;

    /**
     * Checks if a name is already used by a player in an MUD.
     * Since, players are identified within an MUD by their names,
     * every player has to have a unique name.
     *
     * @param name  the name to be checked for uniqueness
     */
    public boolean isUniquePlayerName(String name) throws RemoteException;

    /**
     * Checks if a name provided by the user, can be used in an MUD.
     *
     * @param name  the name to be checked for correctness
     */
    public boolean isValidPlayerName(String name) throws RemoteException;

    /**
     * Adds a player to an MUD.
     *
     * @param p     player to be added to an MUD
     */
    public void join(Player p) throws RemoteException;

    /**
     * Removes a player from an MUD.
     *
     * @param p     player to be removed from an MUD
     */
    public void leave(Player p) throws RemoteException;

    /*
     * Returns a list of commands and some technical info.
     */
    public String manual() throws RemoteException;

    /*
     * Returns a short introduction to the game controls.*/
    public String intro() throws RemoteException;

    /**
     * Lists locations adjacent to a given location.
     * It's used by the client application, to check if the user
     * can move in a particular direction.
     */
    public Map<Direction, Location> listAdjacentTo(Location l) throws RemoteException;

    /**
	 * Returns a location accessible from a given location, when going in a particular
     * direction.
     *
     * @param l     the target location
     * @param d     the direction
     */
    public Location from(Location l, Direction d) throws RemoteException;

    /**
	 * Lists player names at a given location.
     *
     * @param l     the target location
     */
    public List<String> listPlayersAt(Location l) throws RemoteException;

    /**
	 * Lists all paths in an MUD.
     */
    public List<Path> listPaths() throws RemoteException;

    /**
	 * Moves a player to a given destination location.
     *
     * @param p             the player to be moved
     * @param destination   the destination, where the player will be moved to
     */
    public void movePlayer(Player p, Location destination) throws RemoteException;

    /**
	 * Returns a list of all locations in an MUD.
     */
    public List<Location> getLocations() throws RemoteException;

    /**
	 * Removes an item from a given location.
     *
     * @param l     the target location
     * @param i     the target item to be removed
     */
    public void removeItem(Location l, Item i) throws RemoteException;
}