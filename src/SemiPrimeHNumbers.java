import java.io.*;
import java.util.*;

public class SemiPrimeHNumbers {
    static SemiPrimeHNumbers.Kattio io = new SemiPrimeHNumbers.Kattio(System.in, System.out);

    public static void main(String[] args) {
        int input = io.getInt();
        boolean[] sieve;
        while(input != 0){
            sieve = new boolean[(input-1)/4];
            for(int i = 1; i < sieve.length; i++){
                if(!sieve[i]){
                    int multiple = 2;
                    while(multiple*(4*i+1) < input){
                        System.out.println("setting non prime " + multiple*(4*i+1) + " index " + (multiple*(4*i+1)-1)/4);
                        sieve[(multiple*(4*i+1)-1)/4] = true;
                        multiple++;
                    }
                }
            }
            //sieve of eratosthenes complete







            input = io.getInt();
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

