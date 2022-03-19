import com.sun.tools.javac.Main;

import java.io.*;
import java.util.*;

public class Detour implements Runnable{
    static Detour.Kattio io = new Detour.Kattio(System.in, System.out);
    static Intersection[] intersections;
    static PriorityQueue<Intersection> intersectionQueue = new PriorityQueue(new IntersectionComparator());

    public static void main(String[] args) throws Exception {
        new Thread(null, new Detour(), "Main", 1 << 26).start();
    }

public void run(){
        int intersectionCount = io.getInt();
        intersections = new Intersection[intersectionCount];
        for (int i = 0; i < intersectionCount; i++) {
            intersections[i] = new Intersection(i);
        }
        int roadCount = io.getInt();
        int source, destination;
        long distance;
        for (int i = 0; i < roadCount; i++) {
            source = io.getInt();
            destination = io.getInt();
            distance = io.getInt();
            intersections[source].roads.add(new Road(destination, distance, source));
            intersections[destination].roads.add(new Road(source, distance, destination));
        }
        intersections[1].shortestDistance = 0;
        intersectionQueue.add(intersections[1]);

        while (!intersectionQueue.isEmpty()) {
            Intersection currentIntersection = intersectionQueue.poll();
            //for each road from the current intersection, add up the total distance for the destination and add it to the queue.
            for (Road road : currentIntersection.roads) {
                if (currentIntersection.shortestDistance + road.distance < intersections[road.destination].shortestDistance) {
                    intersections[road.destination].shortestDistance = currentIntersection.shortestDistance + road.distance;
                    intersections[road.destination].shortestPath = road;
                    intersectionQueue.add(intersections[road.destination]);
                }
            }
        }

        //find alternative path and print answer
        LinkedList<Intersection> altPath = findAltPath(intersections[0]);
        if (altPath.isEmpty()) {
            io.print("impossible");
        } else {
            io.print(altPath.size());
            while (!altPath.isEmpty()) {
                io.print(" " + altPath.pollLast().id);
            }
        }
        io.close();
    }

    static LinkedList<Intersection> findAltPath(Intersection intersection) {
        LinkedList<Intersection> path = new LinkedList<>();
        Intersection currentIntersection;
        path.push(intersection);
        boolean foundPath;
        while (!path.isEmpty()) {
            currentIntersection = path.peek();
            currentIntersection.visited = true;
            foundPath = false;
            if (currentIntersection.id == 1) {
                return path;
            }
            for (Road road : currentIntersection.roads) {
                //ignores visited nodes and shortest paths found by dijkstras
                if (!intersections[road.destination].visited && currentIntersection.shortestPath.source != road.destination) {
                    path.push(intersections[road.destination]);
                    foundPath = true;
                    break;
                }
            }
            if (!foundPath) {
                path.pop();
            }
        }
        return path;
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
    long shortestDistance;
    Road shortestPath;

    public Intersection(int id) {
        this.id = id;
        shortestDistance = (long)(500000*Math.pow(10, 6));
        visited = false;
        roads = new LinkedList();
    }
}

class Road {
    int source;
    int destination;
    long distance;

    public Road(int destination, long distance, int source) {
        this.source = source;
        this.destination = destination;
        this.distance = distance;
    }
}

class IntersectionComparator implements Comparator<Intersection> {
    @Override
    public int compare(Intersection inter1, Intersection inter2) {
        return Long.compare(inter1.shortestDistance, inter2.shortestDistance);
    }
}