package cs3524.mud;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameSrvrIntfc extends Remote {
	public String listMUDs() throws RemoteException;
    public MUD getMUD(String name) throws RemoteException;
    public void addMUD(MUD m) throws RemoteException;
    public void dropMUD(String name) throws RemoteException;
}
