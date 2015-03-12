package cs3524.mud;

import java.util.Vector;
import java.rmi.RemoteException;

class GameServer implements GameSrvrIntfc {
    private int serverPort;
    private int maxMUDs;
    private Vector<MUD> worlds;

    public GameServer(int serverPort, int maxMUDs) {
        this.serverPort = serverPort;
        this.maxMUDs = maxMUDs;
        this.worlds = new Vector<MUD>();
    }

    public int getMaxMUDs() {
        return this.maxMUDs;
    }

    public String listMUDs() throws RemoteException {
        String response = "";
        for (MUD world : worlds) {
            response += " -  " + world.getName() + System.lineSeparator();
        }
        return response;
    }

    public void addMUD(MUD m) {
        this.worlds.add(m);
    }

    public MUD getMUD(String name) throws RemoteException {
        for (MUD world : worlds) {
            if (world.getName().equals(name)) {
                return world;
            }
        }
        return null;
    }

    public void dropMUD(String name) throws RemoteException {
        for (MUD world : worlds) {
            if (world.getName().equals(name)) {
                worlds.remove(world);
            }
        }
    }
}