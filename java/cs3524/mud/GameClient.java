package cs3524.mud;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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

        try 
        {
            String regURL = "rmi://" + hostname + ":" + registryport + "/GameServer";

            MUD gameService = (MUD)Naming.lookup(regURL);

            System.out.println("Welcome to the MUD. What is your name?");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            Player player = null;
            boolean registered = false;

            while (registered == false) {
                String username = in.readLine().trim();
                if (gameService.isValidPlayerName(username)) {
                    if (gameService.isUniquePlayerName(username)) {
                        System.out.print("Do you want to play as " + username + "? (yes/no) ");
                        String confirmation = in.readLine().trim();
                        Pattern positivePattern = Pattern.compile("^y(es){0,1}$", Pattern.CASE_INSENSITIVE);
                        Pattern negativePattern = Pattern.compile("^n(o){0,1}$", Pattern.CASE_INSENSITIVE);
                        Matcher positiveMatcher = positivePattern.matcher(confirmation);
                        Matcher negativeMatcher = negativePattern.matcher(confirmation);

                        if (confirmation.equals("") || positiveMatcher.matches()) {
                            player = new Player(username, 100.0);
                            player.currentLocation = gameService.getStartingLocation();
                            if (!gameService.join(player)) {
                                System.out.println("There was a problem signing you up. Please try again.");
                            }
                            else {
                                registered = true;
                            }
                        }
                        else if (negativeMatcher.matches()) {
                            System.out.print("What is your name? ");
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

            System.out.println("Welcome, " + player.name + ". Type a command to start.");
            System.out.println("Type 'help' or ?' if you need help.");
            String command = "";
            while (!command.equals("quit") && !command.equals("exit")) {

                Pattern goCommandFormat = Pattern.compile("^go \\p{Lower}{4,5}");
                Pattern goCommandNoParams = Pattern.compile("^go$");
                Pattern takeCmdWithoutParams = Pattern.compile("^take$");

                System.out.print("> ");
                command = in.readLine().trim();

                Matcher goMatcher = goCommandFormat.matcher(command);
                Matcher goWithoutParams = goCommandNoParams.matcher(command);
                Matcher takeWithoutParams = takeCmdWithoutParams.matcher(command);

                if (command.equals("help")) {
                    String response = gameService.manual();
                    System.out.println(response);
                }
                else if (command.equals("?")) {
                    String response = gameService.intro();
                    System.out.println(response);
                }
                else if (command.equals("look")) {
                    System.out.println(player.look());
                    List<String> playersHere = gameService.listPlayersAt(player.currentLocation);
                    if (playersHere.size() > 1) {
                        playersHere.remove(player.name);
                        System.out.println("Other players at this location:");
                        for (String playerName : playersHere) {
                            System.out.println(" -  " + playerName);
                        }
                    }
                }
                else if (command.equals("status")) {
                    System.out.println("You are " + player.name + ".");
                    if (player.equipment.isEmpty()) {
                        System.out.println("You have no items in the equipment.");
                    }
                    else {
                        System.out.println("Carrying:");
                        for (Item i : player.equipment) {
                            System.out.println(i.name);
                        }
                    }
                }
                else if (goMatcher.matches()) {
                    Direction direction = Direction.valueOf(command.substring(3).toUpperCase());
                    Location destination = gameService.from(player.currentLocation, direction);
                    if (destination != null) {
                        gameService.movePlayer(player, destination);
                        player.currentLocation = destination;
                    }
                    else {
                        System.out.println("You can't go there.");
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
                    System.out.println("Your current location is " + player.currentLocation.name);
                    Map<Direction, Location> possibleDestinations = gameService.listAdjacentTo(player.currentLocation);
                    if (possibleDestinations != null) {
                        System.out.println("You can go:");
                        List<Map.Entry<Direction, Location>> destinationsList =
                            new ArrayList<Map.Entry<Direction, Location>>(possibleDestinations.entrySet());
                        for (Map.Entry<Direction, Location> d : destinationsList) {
                            System.out.println(" -> " + d.getKey().toString().toLowerCase()
                                + " to " + d.getValue().name);
                        }                        
                    }
                }
                else if (command.equals("exit") || command.equals("quit")) {
                    gameService.leave(player);
                    System.out.println("Leaving MUD.");
                }
                else {
                    System.out.println("Command not recognised");
                }
            }
        }
        catch (java.io.IOException e) {
            System.err.println("I/O error.");
            System.err.println(e.getMessage());
        }
        catch (java.rmi.NotBoundException e) {
            System.err.println("Server not bound.");
            System.err.println(e.getMessage());
        }
    }
}