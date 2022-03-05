import java.io.*;
import java.util.*;

public class ArmCoordination {
    static ArmCoordination.Kattio io = new ArmCoordination.Kattio(System.in, System.out);

    public static void main(String[] args) {
        long x = io.getInt();
        long y = io.getInt();
        long rad = io.getInt();

        x = x - rad;
        y = y + rad;
        io.println(x + " " + y);
        x = x + 2*rad;
        io.println(x + " " + y);
        y = y - 2*rad;
        io.println(x + " " + y);
        x = x - 2*rad;
        io.print(x + " " + y);

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


