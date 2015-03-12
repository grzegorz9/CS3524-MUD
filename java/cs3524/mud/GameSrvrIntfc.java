package cs3524.mud;

import java.util.List;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Defines the behaviour of an MUD server.
 *
 * @author Grzegorz 'Greg' Muszynski <g.muszynski.12@aberdeen.ac.uk>
 */

public interface GameSrvrIntfc extends Remote {
	/**
	 * Lists all instances of MUD on a server.
	 */
	public List<MUD> listMUDs() throws RemoteException;

	/**
	 * Returns an MUD from the server, searching by name.
	 *
	 * @param name 	the name of an MUD to find
	 */
    public MUD getMUD(String name) throws RemoteException;

    /**
     * Adds a new MUD onto the server.
	 *
	 * @param m 	the instance of MUD to find
     */
    public void addMUD(MUD m) throws RemoteException;

    /**
     * Removes an MUD from the server.
	 *
	 * @param name 	the name of an MUD to remove from the server
     */
    public void dropMUD(String name) throws RemoteException;
}
