import java.io.*;
import java.util.*;

public class Spiderman {
    static Spiderman.Kattio io = new Spiderman.Kattio(System.in, System.out);
    static HashMap<LinkedList<Integer>, int[]> memoized = new HashMap();
    static int minToZero = 10000;

    public static void main(String[] args) {

        int tasks = io.getInt();
        for (int i = 0; i < tasks; i++) {
            minToZero = 10000;
            memoized.clear();
            int climbs = io.getInt();
            LinkedList<Integer> heights = new LinkedList();
            for (int k = 0; k < climbs; k++) {
                heights.offerLast(io.getInt());
            }
                printAnswer(climb(heights, heights.peek()));
        }
        io.close();
    }

    static int[] climb(LinkedList<Integer> heights, int maxSoFar) {
        if (heights.size() == 1) {
            if (heights.get(0) == 0) {
                if (maxSoFar < minToZero) {
                    minToZero = maxSoFar;
                }
                return new int[2];
            } else {
                return null;
            }
        }
        if (heights.peek() < 0 || !(maxSoFar <= minToZero)) {
            return null;
        }
        if (memoized.containsKey(heights)) {
            return memoized.get(heights);
        }

        LinkedList<Integer> up = new LinkedList();
        LinkedList<Integer> down = new LinkedList();

        ListIterator iterator = heights.listIterator();
        while(iterator.hasNext()){
            int current = (int)iterator.next();
            up.offerLast(current);
            down.offerLast(current);
        }
        up.push(up.pop() + up.pop());
        down.push(down.pop() - down.pop());

        int[] result = leastHeighted(climb(down, Math.max(maxSoFar, down.peek())), climb(up, Math.max(maxSoFar, up.peek())));

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


