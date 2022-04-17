import java.io.*;
import java.util.StringTokenizer;

public class PokemonGoGo {
    static PokemonGoGo.Kattio io = new PokemonGoGo.Kattio(System.in, System.out);

    public static void main(String[] args) {
        int stopCount = io.getInt();
        String[] pokemons = new String[stopCount];
        int[][] distances = new int[stopCount][stopCount];
        int[][] DP = new int[(int)Math.pow(2, stopCount)][stopCount];
        //fill subsets
        for(int i = 0; i < DP.length; i++){
            DP[i][0] = FlipBit(DP[i][0], i);
        }


        for (int i = 0; i < stopCount; i++) {
            distances[0][i] = io.getInt() + io.getInt();
            pokemons[i] = io.getWord();
        }


        io.close();
    }

    static int TestBit(int bits, int bit) {
        return bits >> bit & 1;
    }

    static int FlipBit(int bits, int bit) {
        return bits ^ (1 << bit);
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


