import java.io.*;
import java.util.*;

public class SemiPrimeHNumbers {
    static SemiPrimeHNumbers.Kattio io = new SemiPrimeHNumbers.Kattio(System.in, System.out);

    public static void main(String[] args) {
        boolean[] sieve;
        int[] input = new int[100000];
        int inputMax = 0;
        int currentInput = io.getInt();
        int index = 0;


        /*
        sieve of eratosthenes på H numbers
deretter finn to primes j + i og sett index j+i som semiprime
         */


        //gather all inputs to find max input.
        while (currentInput != 0) {
            input[index] = currentInput;
            inputMax = Math.max(inputMax, currentInput);
            currentInput = io.getInt();
            index++;
        }
        inputMax++;

        //sieve of eratosthenes on '4n+1 numbers'
        sieve = new boolean[inputMax];
        for (int i = 5; i < Math.sqrt(inputMax); i = i + 4) {
            if (!sieve[i]) {
                int multiple = 2;
                while (multiple * i < inputMax) {
                    sieve[multiple * i] = true;
                    multiple++;
                }
            }
        }

        //find all semiPrimes (5 is the first H-prime)
        int[] semiPrimePrefixSum = new int[sieve.length];
        for (int i = 5; i < inputMax / 5; i = i + 4) {
            if (!sieve[i]) {
                for (int j = i; j < inputMax / i + 1; j = j + 4) {
                    if (!sieve[j]) {
                        semiPrimePrefixSum[j * i] = 1;
                    }
                }
            }
        }
        //scan to complete prefix sum values.
        for (int i = 5; i < inputMax; i = i + 4) {
            semiPrimePrefixSum[i] = semiPrimePrefixSum[i] + semiPrimePrefixSum[i - 4];
        }

        //output
        int i = 0;
        while (input[i] != 0) {
            io.println(input[i] + " " + semiPrimePrefixSum[input[i]]);
            i++;
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


