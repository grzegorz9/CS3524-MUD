package cs3524.mud;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

class WorldGraph {
    public List<Location> locations;   // i.e. vertices
    public List<Path> paths;    // i.e. edges

    public static Direction oppositeTo(Direction d) {
        if (d == Direction.NORTH) {
            return Direction.SOUTH;
        }
        else if (d == Direction.SOUTH) {
            return Direction.NORTH;
        }
        else if (d == Direction.EAST) {
            return Direction.WEST;
        }
        else if (d == Direction.WEST) {
            return Direction.EAST;
        }
        return null;
    }  

    public static void main(String[] args) {
        WorldGraph g = new WorldGraph();

        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            for (String line; (line = br.readLine()) != null; ) {
                StringTokenizer st = new StringTokenizer(line, ";");
                while (st.hasMoreTokens()) {
                    String locName = st.nextToken().trim();
                    Direction dir = Direction.valueOf(st.nextToken().trim().toUpperCase());
                    String descr = st.nextToken().trim();
                    String dest = st.nextToken().trim();


                    Location l = new Location(locName);
                    Location d = null;
                    if (!g.locations.contains(l)) {
                        g.locations.add(l);
                    }
                    else {
                        l = g.locations.get(g.locations.indexOf(l));
                    }
                    d = new Location(dest);
                    if (!g.locations.contains(d)) {
                        g.locations.add(d);
                    }
                    else {
                        d = g.locations.get(g.locations.indexOf(d));
                    }
                    l.destinations.put(dir, d);
                    d.destinations.put((oppositeTo(dir)), l);
                    g.paths.add(new Path(l, d, dir, descr));
                }
            }
        }
        catch (FileNotFoundException fnf) {
            fnf.printStackTrace();
        }
        catch (IOException ioe) {
            ioe.printStackTrace();
        }

        for (Location l : g.locations) {
            System.out.println(l.name + ":");
            for (Direction dir : Direction.values()) {
                System.out.println(dir + " -> " + (l.to_the(dir) != null ? l.to_the(dir).name : "Nothing"));
            }
            System.out.println();
        }

        for (Path p : g.paths) {
            System.out.println(p.toString() + System.lineSeparator());
        }
    }

    public WorldGraph() {
        this.locations = new ArrayList<Location>();
        this.paths = new ArrayList<Path>();
    }

    public Map<Direction, Location> listAdjacentTo(String l) {
        Location searched = new Location(l);
        if (locations.contains(searched)) {
            return this.locations.get(this.locations.indexOf(searched)).destinations;
        }
        else {
            return null;
        }
    }
}