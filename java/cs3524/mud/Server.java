package cs3524.mud;

import java.net.InetAddress;
import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.server.UnicastRemoteObject;
import java.net.SocketPermission;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 * The program to start up an actual server.
 *
 * @author Grzegorz 'Greg' Muszynski <g.muszynski.12@aberdeen.ac.uk>
 */

public class Server {
    public static void main(String args[]) {
        if (args.length < 3) {
            System.err.println("Usage:\njava GameServer <registryport> <serverport> <maxNumOfMUDs>");
            return;
        }
        String hostname = "127.0.0.1";
        int registryport = Integer.parseInt(args[0]);
        int serviceport = Integer.parseInt(args[1]);
        int maxNumOfMUDs = Integer.parseInt(args[2]);
        if (maxNumOfMUDs <= 0) {
            System.err.println("The maximum number of MUD must be greater than zero.");
            return;
        }

        System.setProperty("java.security.policy", "mud.policy");
        System.setSecurityManager(new RMISecurityManager());

        GameServer gameServer = new GameServer(serviceport, maxNumOfMUDs);
        int mudCount = 0;
        System.out.println("You can now add new worlds");
        System.out.print("> ");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            String line = in.readLine().trim();
            while (!line.equals("end") && mudCount < gameServer.getMaxMUDs()) {
                StringTokenizer st = new StringTokenizer(line, " ");
                while (st.hasMoreTokens()) {
                    String worldName = st.nextToken().trim();
                    String mapFile   = st.nextToken().trim();
                    String itemFile  = st.nextToken().trim();

                    World newWorld = new World(worldName);
                    newWorld.generateMap(mapFile);
                    newWorld.placeItems(itemFile);
                    MUD mud = (MUD)UnicastRemoteObject.exportObject(newWorld, serviceport);
                    gameServer.addMUD(mud);
                    mudCount += 1;

                    System.out.println("Creating new world " + worldName + " from " + mapFile + ", " + itemFile);
                    if (mudCount == maxNumOfMUDs) { break; }
                    System.out.print("> ");
                    line = in.readLine().trim();
                }
            }

            GameSrvrIntfc gameSrvrStub =
                (GameSrvrIntfc)UnicastRemoteObject.exportObject(gameServer, serviceport);

            String regURL =
                "rmi://" + hostname + ":" + registryport + "/GameServer";

            System.out.println("Registering " + regURL);
            Naming.rebind(regURL, gameSrvrStub);
        }
        catch (java.io.IOException e) {
            System.err.println("Failed to register.");
            e.printStackTrace();
        }
    }
}