import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.StringTokenizer;

public class test {
    static test.Kattio io = new test.Kattio(System.in, System.out);

    public static void main(String[] args) {

        int subset = 1;
        int subsetBitCount;
        int subsetMax;



        subsetBitCount = Integer.bitCount(subset);
        subsetMax = 0;
            subsetMax = FlipBit(0, 4-1);

        System.out.println("subsetMax: "+ subsetMax);
        while(subset < subsetMax){
            subset = NextPermutation(subset, subsetMax);
            System.out.println(subset);
        }




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



