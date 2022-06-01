import java.io.*;
import java.util.*;

public class PianoLessons {
    static PianoLessons.Kattio io = new PianoLessons.Kattio(System.in, System.out);
    static boolean[][] G;

    /*
    disse flow greiene.
    mens det finnes paths til slutten
    når man ender på slutten, lag en path baklengs tilbake til der man kom fra. (da kan andre bruke denne pathen til å komme seg til slutten)
     */

    public static void main(String[] args) {

        int studentCount = io.getInt();
        int timeSlotCount = io.getInt();
        //Graph G array contains source -> students -> timeslots -> target respectively
        G = new boolean[studentCount + timeSlotCount + 2][studentCount + timeSlotCount + 2];
        int studentTimeSlots;
        for (int i = 1; i < studentCount + 1; i++) {
            studentTimeSlots = io.getInt();
            for (int j = 0; j < studentTimeSlots; j++) {
                G[i][studentCount + io.getInt()] = true;
            }
        }
        //add paths to source and sink (first and last indices respectively)
        for (int i = 1; i < studentCount + 1; i++) {
            G[0][i] = true;
        }
        for (int i = studentCount+1; i < G.length-1; i++) {
            G[i][G.length - 1] = true;
        }

        //while there is a path to target(sink) in the 'residual network'.
        while (flow(0, new boolean[studentCount + timeSlotCount + 2])) {
        }

        //find answer from Graph.
        int count = 0;
        for (int i = 1; i < studentCount+1; i++) {
            if (G[i][0]) {
                count++;
            }
        }
        io.println(count);

        io.close();
    }

    static boolean flow(int node, boolean[] visited) {
        visited[node] = true;
        //return true if we hit target (sink)
        if (node == G.length - 1) {
            return true;
        }
        for (int i = 0; i < G.length; i++) {
            if (G[node][i] && !visited[i]) {
                //if we are returning from target(sink), remove current edge and put backwards edges while returning to Source.
                if (flow(i, visited)) {
                    G[node][i] = false;
                    G[i][node] = true;
                    return true;
                }
            }
        }
        return false;
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


