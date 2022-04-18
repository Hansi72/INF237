import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class PokemonGoGo {
    static PokemonGoGo.Kattio io = new PokemonGoGo.Kattio(System.in, System.out);
    static pokestop[] pokestops;
    static String[] pokemons;

    public static void main(String[] args) {
        int stopCount = io.getInt();
        pokestops = new pokestop[stopCount];
        pokemons = new String[stopCount];
        int[][] DP = new int[1 << stopCount][stopCount];
        int[][] distances = new int[stopCount][stopCount];

        int x;
        int y;
        String pokemon;
        for (int i = 0; i < stopCount; i++) {
            x = io.getInt();
            y = io.getInt();
            pokemon = io.getWord();
            pokestops[i] = new pokestop(x, y);
            pokemons[i] = pokemon;
        }
        //fill array of the empty subset
        for (int i = 0; i < stopCount; i++) {
            DP[0][i] = Math.abs(pokestops[i].x) + Math.abs(pokestops[i].y);
        }
        //precompute distances
        for (int i = 0; i < distances.length; i++) {
            for (int j = 0; j < distances.length; j++) {
                distances[i][j] = distance(i, j);
            }
        }

        int subset = 1;
        int subsetMax = FlipBit(0, pokestops.length);
        int subSubset;
        int minimum;

        //loop through all subsets in increasing order of bits set.
        while (subset < subsetMax) {
            for (int j = 0; j < pokestops.length; j++) {

                minimum = 100000;
                for (int v = 0; v < pokestops.length; v++) {
                    if (TestBit(subset, v) == 1) {
                        subSubset = FlipBit(subset, v);
                        minimum = Math.min(minimum, DP[subSubset][v] + distances[v][j]);
                    }
                }
                DP[subset][j] = minimum;
            }
            subset = NextPermutation(subset, subsetMax);
        }

        //DP array complete
        //get unique pokemon count
        Set<String> pokeSet = new HashSet<String>();
        for (int i = 0; i < pokemons.length; i++) {
            pokeSet.add(pokemons[i]);
        }
        int uniquePokemons = pokeSet.size();

        minimum = 100000;
        int lowestSolution = 1 << uniquePokemons-1;
        //Loop through 'all' possible solutions and take the minimum of valid ones.
        for (int i = lowestSolution; i < DP.length; i++) {
            if (ValidSolution(i, uniquePokemons)) {
                for (int j = 0; j < pokestops.length; j++) {
                    minimum = Math.min(minimum, DP[i][j] + Math.abs(pokestops[j].x) + Math.abs(pokestops[j].y));
                }
            }
        }
        io.println(minimum);
        io.close();
    }

    static boolean ValidSolution(int subset, int uniqueCount) {
        HashSet<String> set = new HashSet();
        for (int i = 0; i < pokestops.length; i++) {
            if (TestBit(subset, i) == 1) {
                set.add(pokemons[i]);
            }
        }
        if (set.size() >= uniqueCount) {
            return true;
        } else {
            return false;
        }
    }

    static class pokestop {
        int x;
        int y;

        pokestop(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static int TestBit(int bits, int bit) {
        return bits >> bit & 1;
    }

    static int FlipBit(int bits, int bit) {
        return bits ^ (1 << bit);
    }

    static int distance(int stop1, int stop2) {
        return Math.abs(pokestops[stop1].x - pokestops[stop2].x) + Math.abs(pokestops[stop1].y - pokestops[stop2].y);
    }

    //'gospers hack'
    static int NextPermutation(int n, int max) {
        int c = n & -n;
        int r = n + c;
        int result = (((r ^ n) >>> 2) / c) | r;
        if (result >= max) {
            int bitCount = Integer.bitCount(n);
            result = 0;
            for (int i = 0; i < bitCount + 1; i++) {
                result = FlipBit(result, i);
            }
        }

        return result;
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


