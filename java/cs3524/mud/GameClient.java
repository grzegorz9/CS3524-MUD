package cs3524.mud;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.StringTokenizer;

import java.rmi.Naming;
import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;


public class GameClient
{
    public static void main(String args[]) throws RemoteException
    {
        if (args.length < 2) {
            System.err.println("Usage:\njava GameClient <host> <port>");
            return;
        }
        String hostname = args[0];
        int registryport = Integer.parseInt(args[1]);

        System.setProperty("java.security.policy", "mud.policy");
        System.setSecurityManager(new RMISecurityManager());

        try {
            String regURL = "rmi://" + hostname + ":" + registryport + "/GameServer";

            GameSrvrIntfc gameService = (GameSrvrIntfc)Naming.lookup(regURL);
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            MUD world = null;
            System.out.println("Choose which MUD you want to join:");
            while (world == null) {
                for (MUD wrld : gameService.listMUDs()) {
                    System.out.println(" -  " + wrld.getName());
                }

                System.out.print("> ");
                String choice = in.readLine().trim();
                world = gameService.getMUD(choice);
                if (world == null) {
                    System.out.println("Couldn't find it. Try again. Here are your options:");
                }
            }

            System.out.println("Welcome to the MUD. What is your name?");
            in = new BufferedReader(new InputStreamReader(System.in));
            Player player = null;
            boolean registered = false;

            while (registered == false) {
                System.out.print("> ");
                String username = in.readLine().trim();
                if (world.isValidPlayerName(username)) {
                    if (world.isUniquePlayerName(username)) {
                        System.out.print("Do you want to play as " + username + "? (yes/no) ");
                        String confirmation = in.readLine().trim();
                        Pattern positivePattern = Pattern.compile("^y(es){0,1}$", Pattern.CASE_INSENSITIVE);
                        Pattern negativePattern = Pattern.compile("^n(o){0,1}$", Pattern.CASE_INSENSITIVE);
                        Matcher positiveMatcher = positivePattern.matcher(confirmation);
                        Matcher negativeMatcher = negativePattern.matcher(confirmation);

                        if (confirmation.equals("") || positiveMatcher.matches()) {
                            player = new Player(username, 100.0);
                            player.setCurrentLocation(world.getStartingLocation());
                            if (!world.join(player)) {
                                System.out.println("There was a problem signing you up. Please try again.");
                            }
                            else {
                                registered = true;
                            }
                        }
                        else if (negativeMatcher.matches()) {
                            System.out.println("What is your name? ");
                        }
                    }
                    else {
                        System.out.println("This name is already taken. Please choose a different one.");
                    }
                }
                else {
                    System.out.println("This is not a valid username. Please choose a different one.");
                }
            }

            System.out.println("Welcome, " + player.getName() + ". Type a command to start.");
            System.out.println("Type 'help' or ?' if you need help.");
            String command = "";
            while (!command.equals("quit") && !command.equals("exit")) {

                Pattern goCommandFormat = Pattern.compile("^go (?:north|east|south|west)");
                Pattern takeCommandFormat = Pattern.compile("^take [\\w ]+$");

                Pattern goCommandNoParams = Pattern.compile("^go$");
                Pattern takeCmdWithoutParams = Pattern.compile("^take$");

                System.out.print("> ");
                command = in.readLine().trim();

                Matcher goMatcher = goCommandFormat.matcher(command);
                Matcher takeCmdMatcher = takeCommandFormat.matcher(command);
                Matcher goWithoutParams = goCommandNoParams.matcher(command);
                Matcher takeWithoutParams = takeCmdWithoutParams.matcher(command);

                List<Location> worldLocations = world.getLocations();
                Location playerLocation = worldLocations.get(worldLocations.indexOf(player.getCurrentLocation()));

                if (command.equals("help")) {
                    String response = world.manual();
                    System.out.println(response);
                }
                else if (command.equals("?")) {
                    String response = world.intro();
                    System.out.println(response);
                }
                else if (command.equals("look")) {
                    String availableItems = "";
                    if (!playerLocation.listItems().isEmpty()) {
                        for (Item i : playerLocation.listItems()) {
                            if (playerLocation.listItems().indexOf(i) == playerLocation.listItems().size() - 1) {
                                availableItems += " -  " + i.name;
                            }
                            else {
                                availableItems += " -  " + i.name + System.lineSeparator();
                            }
                        }
                        System.out.println(playerLocation.getName() + System.lineSeparator()
                            + playerLocation.getDescription() + System.lineSeparator()
                            + "Items:" + System.lineSeparator() + availableItems);
                    }
                    else {
                        System.out.println(playerLocation.getName() + System.lineSeparator()
                        + playerLocation.getDescription());
                    }
                    List<String> playersHere = world.listPlayersAt(player.getCurrentLocation());
                    if (playersHere.size() > 1) {
                        playersHere.remove(player.getName());
                        System.out.println("Other players at this location:");
                        for (String playerName : playersHere) {
                            System.out.println(" -  " + playerName);
                        }
                    }
                }
                else if (command.equals("status")) {
                    System.out.println("You are " + player.getName() + ".");
                    if (player.listEquipment().isEmpty()) {
                        System.out.println("You have no items in the equipment.");
                    }
                    else {
                        System.out.println("Carrying:");
                        for (Item i : player.listEquipment()) {
                            System.out.println(i.name);
                        }
                    }
                }
                else if (goMatcher.matches()) {
                    Direction direction = Direction.valueOf(command.substring(3).toUpperCase());
                    Location destination = world.from(player.getCurrentLocation(), direction);
                    if (destination != null) {
                        world.movePlayer(player, destination);
                        player.setCurrentLocation(destination);
                    }
                    else {
                        System.out.println("You can't go there.");
                    }
                }
                else if (takeCmdMatcher.matches()) {
                    StringTokenizer st = new StringTokenizer(command.substring(5), ",");
                    while (st.hasMoreTokens()) {
                        String itemSearchedFor = st.nextToken().trim();
                        for (Item i : playerLocation.listItems()) {
                            if (i.name.equals(itemSearchedFor)) {
                                player.addToEquipment(i);
                                world.removeItem(playerLocation, i);
                            }
                        }
                    }
                }
                else if (goWithoutParams.matches()) {
                    System.out.println("Usage:\ngo <direction>");
                    System.out.println("<direction> can be any of:");
                    System.out.println(" -  north\n -  east\n -  south\n -  west");
                }
                else if (takeWithoutParams.matches()) {
                    System.out.println("Usage:\ntake <item>");
                }
                else if (command.equals("where")) {
                    Map<Direction, Location> possibleDestinations = world.listAdjacentTo(player.getCurrentLocation());
                    if (possibleDestinations != null) {
                        List<Map.Entry<Direction, Location>> destinationsList =
                            new ArrayList<Map.Entry<Direction, Location>>(possibleDestinations.entrySet());

                        for (Map.Entry<Direction, Location> d : destinationsList) {
                            Path p = new Path(player.getCurrentLocation(), d.getValue(), d.getKey(), null);
                            System.out.println(world.listPaths().get(world.listPaths().indexOf(p)).toString());
                        }                        
                    }
                }
                else if (command.equals("exit") || command.equals("quit")) {
                    world.leave(player);
                    System.out.println("Leaving MUD.");
                }
                else {
                    System.out.println("Command not recognised");
                }
            }
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }
        catch (java.rmi.NotBoundException e) {
            System.err.println("Server not bound.");
            System.err.println(e.getMessage());
        }
    }
}