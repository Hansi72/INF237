import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class IHateTheNumberNine {
    static IHateTheNumberNine.Kattio io = new IHateTheNumberNine.Kattio(System.in, System.out);

    public static void main(String[] args) {
        int testCases = io.getInt();
        long digits;
        BigInteger answer;
        BigInteger modulus = BigInteger.valueOf(1000000007);

        for (int i = 0; i < testCases; i++) {
            digits = io.getLong();
            answer = BigInteger.valueOf(9).modPow(BigInteger.valueOf(digits-1), modulus);
            answer = answer.multiply(BigInteger.valueOf(8)).mod(modulus);
            io.println(answer);
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


