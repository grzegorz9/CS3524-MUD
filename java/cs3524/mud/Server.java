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

public class Server {
    public static void main(String args[]) {
        if (args.length < 2) {
            System.err.println("Usage:\njava GameServer <registryport> <serverport>");
            return;
        }
        String hostname = "127.0.0.1";
        int registryport = Integer.parseInt(args[0]);
        int serviceport = Integer.parseInt(args[1]);

        System.setProperty("java.security.policy", "mud.policy");
        System.setSecurityManager(new RMISecurityManager());

        GameServer gameServer = new GameServer(serviceport);
        System.out.println("You can now add new worlds");
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        try {
            String line = in.readLine().trim();
            while (!line.equals("end")) {
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

                    System.out.println("Creating a new world " + worldName + " from " + mapFile + ", " + itemFile);
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