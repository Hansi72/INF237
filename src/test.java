import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.StringTokenizer;

public class test {
    static test.Kattio io = new test.Kattio(System.in, System.out);

    public static void main(String[] args) {

        int test = 8;
        int test2 = ~test << 1;
        System.out.println(test2);




    }
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
    static int FlipBit(int bits, int bit) {
        return bits ^ (1 << bit);
    }

    static int TestBit(int bits, int bit) {
        return bits >> bit & 1;
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



/*
//Test possible solution subsets of bitlength = unique pokemons
        Set<String> pokeSet = new HashSet<String>();
        for (int i = 0; i < pokemons.length; i++) {
            pokeSet.add(pokemons[i]);
        }
        int uniquePokemons = pokeSet.size();
        int solutionSet = 0;
        for (int i = 0; i < uniquePokemons; i++) {
            solutionSet = FlipBit(solutionSet, i);
        }
        int max = FlipBit(0, pokestops.length-1);
        int min = 10000;
        //loop through solutions and take the minimum of valid solutions
        while (solutionSet < max) {
            if (ValidSolution(solutionSet, uniquePokemons)) {
                min = Math.min(min, DP[FlipBit(solutionSet >> 1, pokestops.length-1)][pokemons.length]);
            }
            solutionSet = NextPermutation(solutionSet, max);
        }
 */