import java.io.*;
import java.util.*;

public class Paintball {
    static Paintball.Kattio io = new Paintball.Kattio(System.in, System.out);
    static boolean[] visited;

    public static void main(String[] args) {

        int playerCount = io.getInt();
        int M = io.getInt();
        if (M < 1) { //todo
            io.println("Impossible");
        }
        boolean[][] G = new boolean[playerCount+2][playerCount+2];
        int[][] RG = new int[playerCount+2][playerCount+2];
        visited = new boolean[playerCount+2];
        int A;
        int B;
        for (int i = 0; i < M; i++) {
            A = io.getInt();
            B = io.getInt();
            G[A][B] = true;
            G[B][A] = true;
        }
        //add flow and paths to source and sink (last two indices respectively)
        for(int i = 0; i < playerCount-2; i++){
            RG[playerCount][i] = 1;
            G[playerCount][i] = true;
            G[i][playerCount+1] = true;
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


