package cs3524.mud;

import java.util.List;
import java.util.ArrayList;
import java.util.Vector;
import java.rmi.RemoteException;

/**
 * The implementaion of a server, hosting multiple MUDs.
 *
 * @author Grzegorz 'Greg' Muszynski <g.muszynski.12@aberdeen.ac.uk>
 */

public class GameServer implements GameSrvrIntfc {
    private int serverPort;
    private final int maxMUDs;
    private Vector<MUD> worlds;

    /**
     * Creates a new server.
     *
     * @param serverPort    the port number for the server
     * @param maxMUDs       the maximum of MUDs allowed on the server
     */
    public GameServer(int serverPort, int maxMUDs) {
        this.serverPort = serverPort;
        this.maxMUDs = maxMUDs;
        this.worlds = new Vector<MUD>();
    }

    /**
     * Returns the maximum number of MUDs, which can be stored on the server
     * at any time.
     */
    public int getMaxMUDs() {
        return this.maxMUDs;
    }

    /**
     * Returns a list of MUDs stored on the server.
     */
    public List<MUD> listMUDs() throws RemoteException {
        List<MUD> allMUDs = new ArrayList<MUD>();
        for (MUD world : worlds) {
            allMUDs.add(world);
        }
        return allMUDs;
    }

    /**
     * Adds a new MUD onto the server.
     *
     * @param m     the MUD object to be added
     */
    public void addMUD(MUD m) {
        this.worlds.add(m);
    }

    /**
     * Fetches an MUD from a server, through searching by name.
     * If such MUD cannot be found, returns {@code null}.
     *
     * @param name      the name of the MUD to find
     */
    public MUD getMUD(String name) throws RemoteException {
        for (MUD world : worlds) {
            if (world.getName().equals(name)) {
                return world;
            }
        }
        return null;
    }

    /**
     * Removes an MUD from a server. Looks for an MUD with
     * a given name. Note, that this implementation makes
     * the method remove only the first occurrence of an MUD,
     * which has a given name.
     *
     * @param name      the name of the MUD to be removed
     */
    public void dropMUD(String name) throws RemoteException {
        for (MUD world : worlds) {
            if (world.getName().equals(name)) {
                worlds.remove(world);
            }
        }
    }
}