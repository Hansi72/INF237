import java.io.*;
import java.util.*;

public class TheCitrusIntern {
    static TheCitrusIntern.Kattio io = new TheCitrusIntern.Kattio(System.in, System.out);

    public static void main(String[] args) {
        //setup
        int memberCount = io.getInt();
        member[] members = new member[memberCount];
        for (int i = 0; i < memberCount; i++) {
            members[i] = new member(io.getInt(), io.getInt()); //(price, subCount)
            for (int j = 0; j < members[i].subs.length; j++) {
                members[i].subs[j] = io.getInt();
            }
        }
        //work

    }

    static class member {
        int cost;
        int[] subs;

        member(int cost, int subs) {
            this.cost = cost;
            this.subs = new int[subs];
        }
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


