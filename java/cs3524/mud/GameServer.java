package cs3524.mud;

import java.util.Vector;
import java.rmi.RemoteException;

class GameServer implements GameSrvrIntfc {
    private int serverPort;
    private Vector<MUD> worlds;

    public GameServer(int serverPort) {
        this.serverPort = serverPort;
        this.worlds = new Vector<MUD>();
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