import java.io.*;
import java.util.*;

public class Detour {
    static Detour.Kattio io = new Detour.Kattio(System.in, System.out);
    static Intersection[] intersections;
    static PriorityQueue<Intersection> intersectionQueue = new PriorityQueue(new IntersectionComparator());
    static boolean pathFound = false;
    static HashSet<Road> illegalRoads;

    public static void main(String[] args) {

        int intersectionCount = io.getInt();
        intersections = new Intersection[intersectionCount];
        for (int i = 0; i < intersectionCount; i++) {
            intersections[i] = new Intersection(i);
        }
        int roadCount = io.getInt();
        int source, destination, distance;
        for (int i = 0; i < roadCount; i++) {
            source = io.getInt();
            destination = io.getInt();
            distance = io.getInt();
            intersections[source].roads.add(new Road(destination, distance, source));
            intersections[destination].roads.add(new Road(source, distance, destination));
        }
        intersections[1].shortestDistance = 0;
        intersectionQueue.addAll(Arrays.asList(intersections));

        while (!intersectionQueue.isEmpty()) {
            Intersection currentIntersection = intersectionQueue.poll();
            //for each road from the current intersection, add up the total distance for the destination.
            for (Road road : currentIntersection.roads) {
                if (currentIntersection.shortestDistance + road.distance < intersections[road.destination].shortestDistance) {
                    intersections[road.destination].shortestDistance = currentIntersection.shortestDistance + road.distance;
                    intersections[road.destination].shortestPath = road;
                }
            }
            //todo add "if(changed)" here and in that case change "Queue.isEmpty" for the while loop
            if (!intersectionQueue.isEmpty()) {
                intersectionQueue.add(intersectionQueue.poll());
            }
        }


        Intersection cSection = intersections[0];
        //remove the shortest path from the graph
        illegalRoads = new HashSet();
        System.out.print("shortest path: ");
        while (cSection.id != 1) {
            illegalRoads.add(cSection.shortestPath);
            System.out.print(cSection.shortestPath.source + " ");
            cSection = intersections[cSection.shortestPath.source];
        }
        System.out.println("");

        LinkedList<Integer> altPath = new LinkedList<>();
        findAltPath(intersections[0], altPath);

        if (altPath.size() == 1) {
            io.print("impossible");
        } else {
            io.print(altPath.size() + " ");
            while (altPath.size() > 1) {
                io.print(altPath.pollLast() + " ");
            }
            io.print(altPath.pollLast());
        }


        io.close();
    }

    static void findAltPath(Intersection intersection, LinkedList<Integer> path) {
        if (pathFound || intersection.visited) {
            return;
        }
        if (intersection == intersections[1]) {
            pathFound = true;
            path.push(intersection.id);
            return;
        }
        path.push(intersection.id);
        intersection.visited = true;
        for (Road road : intersection.roads) {
            System.out.println("at intersection: " + intersection.id + " and road source: " + road.source + " dest: " + road.destination + " shortestPath source: " + intersection.shortestPath.source + " shortestPath destination: " + intersection.shortestPath.destination);
            if (intersection.shortestPath.source != road.destination || road.destination == intersections[1].id) {
                //if(pathFound){return;}
                findAltPath(intersections[road.destination], path);
            }
        }

    }

    static class Kattio extends PrintWriter {
        public Kattio(InputStream i) {
            super(new BufferedOutputStream(System.out));
            r = new BufferedReader(new InputStreamReader(i));
        }

        public Kattio(InputStream i, OutputStream o) {
            super(new BufferedOutputStream(o));
            r = new BufferedReader(new InputStreamReader(i));
        }

        public boolean hasMoreTokens() {
            return peekToken() != null;
        }

        public int getInt() {
            return Integer.parseInt(nextToken());
        }

        public double getDouble() {
            return Double.parseDouble(nextToken());
        }

        public long getLong() {
            return Long.parseLong(nextToken());
        }

        public String getWord() {
            return nextToken();
        }


        private BufferedReader r;
        private String line;
        private StringTokenizer st;
        private String token;

        private String peekToken() {
            if (token == null)
                try {
                    while (st == null || !st.hasMoreTokens()) {
                        line = r.readLine();
                        if (line == null) return null;
                        st = new StringTokenizer(line);
                    }
                    token = st.nextToken();
                } catch (IOException e) {
                }
            return token;
        }

        private String nextToken() {
            String ans = peekToken();
            token = null;
            return ans;
        }
    }
}


class Intersection {
    int id;
    LinkedList<Road> roads;
    boolean visited;
    int shortestDistance;
    Road shortestPath;

    public Intersection(int id) {
        this.id = id;
        shortestDistance = 500000;
        visited = false;
        roads = new LinkedList();
    }
}

class Road {
    int source;
    int destination;
    int distance;

    public Road(int destination, int distance, int source) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }
}

class IntersectionComparator implements Comparator<Intersection> {
    @Override
    public int compare(Intersection inter1, Intersection inter2) {
        return Integer.compare(inter1.shortestDistance, inter2.shortestDistance);
    }
}