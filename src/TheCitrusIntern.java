import java.io.*;
import java.util.*;

public class TheCitrusIntern {
    static TheCitrusIntern.Kattio io = new TheCitrusIntern.Kattio(System.in, System.out);
    static long[] pick;
    static long[] skipBelow;  //cost of skipping current node if dominated from below
    static long[] skipAbove;  //cost of skipping current node if dominated from above
    static int[] costs;
    static int[][] subs;
    static long inf = Long.parseLong("8223372036854775807");

    public static void main(String[] args) {
        //setup
        int memberCount = io.getInt();
        costs = new int[memberCount];
        subs = new int[memberCount][];
        boolean[] isSub = new boolean[memberCount];
        int sub;
        for (int i = 0; i < memberCount; i++) {
            costs[i] = io.getInt();
            subs[i] = new int[io.getInt()];
            for (int j = 0; j < subs[i].length; j++) {
                sub = io.getInt();
                subs[i][j] = sub;
                isSub[sub] = true;
            }
        }
        //find sour excellence (root)
        int sourExcellence = -1;
        for (int i = 0; i < memberCount; i++) {
            if (!isSub[i]) {
                sourExcellence = i;
            }
        }
        //work
        pick = new long[memberCount];
        skipAbove = new long[memberCount];
        skipBelow = new long[memberCount];

        work(sourExcellence);
        io.println(Math.min(skipBelow[sourExcellence], pick[sourExcellence]));
        io.close();
    }

    static void work(int member) {
        pick[member] = costs[member];
        skipAbove[member] = 0;

        //if leaf node
        if (subs[member].length == 0) {
            skipBelow[member] = inf;
        } else {
            long sumSubDominatedFromAbove = 0;
            long sumSubDominatedFromBelow = 0;
            for (int sub : subs[member]) {
                work(sub);
                sumSubDominatedFromAbove = sumSubDominatedFromAbove + skipAbove[sub];
                sumSubDominatedFromBelow = sumSubDominatedFromBelow + Math.min(pick[sub], skipBelow[sub]);
            }
            pick[member] = pick[member] + sumSubDominatedFromAbove;
            skipAbove[member] = sumSubDominatedFromBelow;

            //pick the lowest cost of getting dominated from below
            long cost = 0;
            long promote = inf;
            boolean hasDominatorBelow = false;
            for (int sub : subs[member]) {
                cost = cost + Math.min(pick[sub], skipBelow[sub]);
                promote = Math.min(promote, Math.max(0, pick[sub] - skipBelow[sub]));
                if (pick[sub] < skipBelow[sub]) {
                    hasDominatorBelow = true;
                }
            }
            if (hasDominatorBelow) {
                skipBelow[member] = cost;
            } else {
                skipBelow[member] = cost + promote;
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


