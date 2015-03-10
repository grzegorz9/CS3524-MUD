package cs3524.mud;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface MUD extends Remote {
	public boolean join(Player p) throws RemoteException;
	public void leave(Player p) throws RemoteException;
	public Location getStartingLocation() throws RemoteException;
	public String manual() throws RemoteException;
	public String intro() throws RemoteException;
	public Map<Direction, Location> listAdjacentTo(Location l) throws RemoteException;
	public Location from(Location l, Direction d) throws RemoteException;
	public List<String> listPlayersAt(Location l) throws RemoteException;
	public void movePlayer(Player p, Location destination) throws RemoteException;
	public boolean isUniquePlayerName(String name) throws RemoteException;
	public boolean isValidPlayerName(String name) throws RemoteException;
	public List<Location> getLocations() throws RemoteException;
	public void removeItem(Location l, Item i) throws RemoteException;
}