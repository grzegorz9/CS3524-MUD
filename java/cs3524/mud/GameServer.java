package cs3524.mud;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.server.UnicastRemoteObject;

public class GameServer {
    public static void main(String args[]) {
        if (args.length < 2) {
            System.err.println("Usage:\njava GameServer <registryport> <serverport> <map_file>");
            return;
        }
        try {
            String hostname = (InetAddress.getLocalHost()).getCanonicalHostName();
            int registryport = Integer.parseInt(args[0]);
            int serviceport = Integer.parseInt(args[1]);

            System.setProperty("java.security.policy", "mud.policy");
            System.setSecurityManager(new RMISecurityManager());

            World gameService = new World(args[2]);
            gameService.generateMap(args[3]);
            gameService.placeItems(args[4]);
            MUD stub =
                (MUD)UnicastRemoteObject.exportObject(gameService, serviceport);

            String regURL =
                "rmi://" + hostname + ":" + registryport + "/GameServer";

            System.out.println("Registering " + regURL);
            Naming.rebind(regURL, stub);
        }
        catch(java.net.UnknownHostException e) {
            System.err.println("Cannot get local host name.");
            System.err.println(e.getMessage());
        }
        catch (java.io.IOException e) {
            System.err.println("Failed to register.");
            e.printStackTrace();
        }
    }
}