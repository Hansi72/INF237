import java.io.*;
import java.util.LinkedList;
import java.util.Stack;
import java.util.StringTokenizer;

public class FaultyRobot {
    static LinkedList<Integer>[] possibleMoves;
    static boolean[] visited;
    static long count = 0;

    public static void main(String[] args) {
        FaultyRobot.Kattio io = new FaultyRobot.Kattio(System.in, System.out);

        int n = io.getInt();
        int m = io.getInt();
        possibleMoves = new LinkedList[n + 1];
        visited = new boolean[n + 1];

        for (int i = 1; i < n + 1; i++) {
            possibleMoves[i] = new LinkedList();
            visited[i] = false;
        }
        int inValue;
        for (int i = 0; i < m; i++) {
            inValue = io.getInt();
            if (inValue < 0) {
                possibleMoves[Math.abs(inValue)].add(-io.getInt());
            } else {
                possibleMoves[inValue].add(io.getInt());
            }
        }

        botMove(1, false);
        io.println(count);
        io.close();
    }

    static void botMove(int node, boolean buggyMove) {
        visited[node] = true;

        Stack<Integer> forcedMoves = new Stack();
        Stack<Integer> nonForcedMoves = new Stack();
        for (int move : possibleMoves[node]) {
            if (move < 0) {
                forcedMoves.push(move);
            } else {
                if (!buggyMove) {
                    nonForcedMoves.push(move);
                }
            }
        }

        if (forcedMoves.empty()) {
            count++;
        }

        while (!forcedMoves.empty()) {
            if (!visited[Math.abs(forcedMoves.peek())]) {
                botMove(Math.abs(forcedMoves.pop()), buggyMove);
            } else {
                forcedMoves.pop();
            }
        }

        while (!nonForcedMoves.empty()) {
            if (!visited[nonForcedMoves.peek()]) {
                botMove(nonForcedMoves.pop(), true);
            } else {
                nonForcedMoves.pop();
            }
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
            if (token == null) try {
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
