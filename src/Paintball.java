import java.io.*;
import java.util.*;

public class Paintball {
    static Paintball.Kattio io = new Paintball.Kattio(System.in, System.out);
    static boolean[][] G;

    public static void main(String[] args) {

        int playerCount = io.getInt();
        int M = io.getInt();
        G = new boolean[playerCount * 2 + 2][playerCount * 2 + 2];
        int A;
        int B;
        for (int i = 0; i < M; i++) {
            A = io.getInt();
            B = io.getInt();
            G[A][B + playerCount] = true;
            G[B][A + playerCount] = true;
        }
        //add paths to source and sink (first and last indices respectively)
        for (int i = 1; i < playerCount+1; i++) {
            G[0][i] = true;
            G[playerCount + i][G.length - 1] = true;
        }

        //while there is a path to target(sink) in the residual network.
        while (flow(0, new boolean[playerCount * 2 + 2])) {
        }

        //find answer from Graph and output.
        LinkedList<Integer> answer = new LinkedList<>();
        for (int j = playerCount; j < G.length - 1; j++) {
            for (int i = 1; i < playerCount + 1; i++) {
                if (G[j][i]) {
                    answer.push(i);
                }
            }
        }

        if (answer.size() < playerCount) {
            io.println("Impossible");
        } else {
            Iterator answerIter = answer.descendingIterator();
            while (answerIter.hasNext()) {
                io.println(answerIter.next());
            }
        }
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


