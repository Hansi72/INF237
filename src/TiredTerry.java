import java.io.*;
import java.util.BitSet;
import java.util.StringTokenizer;

public class TiredTerry {
    public static void main(String[] args) {
        TiredTerry.Kattio io = new TiredTerry.Kattio(System.in, System.out);

        int length = io.getInt();
        int p = io.getInt();
        int d = io.getInt();

        String input = io.getWord();
        int[] cycle = new int[length];

        for (int i = 0; i < length; i++) {
            if (input.charAt(i) == 'W') {
                cycle[i] = 0;
            } else {
                cycle[i] = 1;
            }
        }

        int tired = 0;
        int current = 0;
        for (int i = 0; i < p; i++) {
            current = current + cycle[(length + i - p + 1) % length];
        }
        if (current < d) {
            tired++;
        }

        for (int i = 1; i < length; i++) {
            current = current + cycle[i];
            current = current - cycle[(length + i - p) % length];
            if (current < d) {
                tired++;
            }
        }
        io.println(tired);
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
