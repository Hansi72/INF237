import java.io.*;
import java.util.*;

public class IslandHopping {
    static IslandHopping.Kattio io = new IslandHopping.Kattio(System.in, System.out);
    static Island[] islands;

    public static void main(String[] args) {
        int tasks = io.getInt();
        for (int task = 0; task < tasks; task++) {
            int islandCount = io.getInt();

            islands = new Island[islandCount];
            for (int i = 0; i < islandCount; i++) {
                islands[i] = new Island(io.getDouble(), io.getDouble(), islandCount);
            }

            int antiSelfBridge;
            for (int island1 = 0; island1 < islandCount; island1++) {
                antiSelfBridge = 0;
                for (int island2 = 0; island2 < islandCount; island2++) {
                    if (island1 == island2) {
                        antiSelfBridge = 1;
                        continue;
                    }
                    islands[island1].bridges[island2 - antiSelfBridge] = new Bridge(islands[island1], islands[island2]);
                }
            }

            Island currentIsland = islands[0];
            Bridge[] mst = new Bridge[islandCount - 1];
            PriorityQueue<Bridge> prioQueue = new PriorityQueue(islandCount, new BridgeComparator());
            //while the last bridge has not been set
            int i = 0;
            while (mst[mst.length - 1] == null) {
                currentIsland.visited = true;
                //add neighbouring islands to queue
                for (Bridge bridge : currentIsland.bridges) {
                    if (!bridge.island2.visited) {
                        prioQueue.add(bridge);
                    }
                }
                //removes bridges to islands that have already been visited.
                while (prioQueue.peek().island2.visited) {
                    prioQueue.remove();
                }
                currentIsland = prioQueue.peek().island2;
                mst[i] = prioQueue.poll();
                i++;
            }

            double totLen = 0;
            for (int j = 0; j < mst.length; j++) {
                totLen = totLen + mst[j].length;
            }
            io.println(totLen);
        }
        io.close();
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

class Island {
    Bridge[] bridges;
    boolean visited = false;
    double x;
    double y;

    public Island(double x, double y, int islandCount) {
        this.x = x;
        this.y = y;
        bridges = new Bridge[islandCount - 1];
    }
}

class Bridge {
    public Bridge(Island island1, Island island2) {
        this.island1 = island1;
        this.island2 = island2;
        this.length = Math.sqrt(Math.pow(island1.x - island2.x, 2) + Math.pow((island1.y - island2.y), 2));
    }

    double length;
    Island island1;
    Island island2;
}

class BridgeComparator implements Comparator<Bridge> {
    @Override
    public int compare(Bridge b1, Bridge b2) {
        return Double.compare(b1.length, b2.length);
    }
}
