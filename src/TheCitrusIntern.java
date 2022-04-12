import java.io.*;
import java.util.*;

public class TheCitrusIntern {
    static TheCitrusIntern.Kattio io = new TheCitrusIntern.Kattio(System.in, System.out);
    static long[] pick;
    static long[] skip;
    static int[] costs;
    static int[][] subs;

    public static void main(String[] args) {
        //setup
        int memberCount = io.getInt();
        costs = new int[memberCount];
        subs = new int[memberCount][];
        for (int i = 0; i < memberCount; i++) {
            costs[i] = io.getInt();;
            subs[i] = new int[io.getInt()];
            for (int j = 0; j < subs[i].length; j++) {
                subs[i][j] = io.getInt();
            }
        }
        //work
        pick = new long[memberCount];
        skip = new long[memberCount];

        work(5);

        io.println(pick[5]);
        io.println(skip[5]);


        io.close();



    }

    static void work(int member){
        System.out.println("currently at " + member);
        //if leaf node
        if(subs[member].length == 0){
            pick[member] = costs[member];
            skip[member] = 0;
        }else{
            long sum = 0;
            for(int sub : subs[member]){
                work(sub);
                sum = sum + skip[sub];
            }
            pick[member] = sum + costs[member];
            skip[member] = Math.max(pick[member], sum);
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


