import java.io.*;
import java.util.*;

public class Spiderman {
    static Spiderman.Kattio io = new Spiderman.Kattio(System.in, System.out);
    static HashMap<LinkedList<Integer>, int[]> memoized = new HashMap();

    public static void main(String[] args) {

        int tasks = io.getInt();
        for (int i = 0; i < tasks; i++) {
            int climbs = io.getInt();
            LinkedList<Integer> heights = new LinkedList();
            for (int k = 0; k < climbs; k++) {
                heights.push(io.getInt());
            }
            printAnswer(climb(heights));
        }
        io.close();
    }

    static void printAnswer(int[] result) {
        if (result == null) {
            io.println("IMPOSSIBLE");
        } else {
            io.print("U");
            for (int i = 1; i < result.length - 1; i++) {
                if (result[i - 1] > result[i]) {
                    io.print("D");
                } else {
                    io.print("U");
                }
            }
            io.println("");
        }

    }

    static int[] climb(LinkedList<Integer> heights) {
        if (heights.size() == 1) {
            if (heights.get(0) == 0) {
                int[] returnList = new int[2];
                returnList[0] = 0;
                returnList[0] = 0;
                return returnList;
            } else {
                return null;
            }
        }
        if (heights.peek() < 0) {
            return null;
        }

        if (memoized.containsKey(heights)) {
            return memoized.get(heights);
        }

        LinkedList<Integer> up = new LinkedList((LinkedList) heights.clone());
        LinkedList<Integer> down = new LinkedList((LinkedList) heights.clone());
        up.push(up.pop() + up.pop());
        down.push(down.pop() - down.pop());

        int[] result = leastHeighted(climb(down), climb(up));

        if (result == null) {
            memoized.put(heights, null);
            return null;
        }

        memoized.put(heights, new int[result.length + 1]);
        memoized.get(heights)[0] = heights.peek();
        for (int i = 0; i < result.length - 1; i++) {
            memoized.get(heights)[i + 1] = result[i];
        }
        memoized.get(heights)[memoized.get(heights).length - 1] = Math.max(heights.peek(), result[result.length - 1]);

        return memoized.get(heights);
    }

    static int[] leastHeighted(int[] stack1, int[] stack2) {
        if (stack1 == null) {
            return stack2;
        }
        if (stack2 == null) {
            return stack1;
        }

        if (stack1[stack1.length - 1] < stack2[stack2.length - 1]) {
            return stack1;
        } else {
            return stack2;
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


