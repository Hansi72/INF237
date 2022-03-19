import java.io.*;
import java.util.*;

public class PointsOfSnow {
    static PointsOfSnow.Kattio io = new PointsOfSnow.Kattio(System.in, System.out);
    static long[] lineland;

    public static void main(String[] args) {
        int linelandLength = io.getInt();
        int reportCount = io.getInt();
        int queryCount = io.getInt();

        lineland = new long[linelandLength * 2 - 1];
        for (int i = 0; i < reportCount + queryCount; i++) {
            if (io.getWord().equals("!")) {
                int start = io.getInt();
                int end = io.getInt();
                int amount = io.getInt();
                snow(0, start, end, amount, 0, linelandLength - 1);

            } else {
                io.println(Query(io.getInt() - 1));
            }
        }

        for (int i = 0; i < lineland.length; i++) {
            System.out.print(lineland[i] + ", ");
        }
        System.out.println("");



        for (int i = 0; i < lineland.length/2+1; i++) {
            System.out.print(Query(i) + ", ");
        }
        System.out.println("");

        io.close();
    }

    static long Query(int index) {
        long value = 0;
        index = index + lineland.length / 2;
        while (index > 0) {
            //System.out.println("query: index = " + index + " value at index = " + lineland[index]);
            value = value + lineland[index];
            index = GetParent(index);
        }
        return value + lineland[0];
    }

    static void snow(int index, int start, int end, int amount, int currRangeStart, int currRangeEnd) {
        System.out.println("snowing: index = " + index + " currRangeStart = " + currRangeStart + " currRangeEnd = " + currRangeEnd + " adding: " + amount);
        if (index >= lineland.length / 2) {
            lineland[index] = lineland[index] + amount;
            System.out.println("hit leaf node, returning-------");
            return;
        }
        if (start <= currRangeStart && end >= currRangeEnd) {
            lineland[index] = lineland[index] + amount;
            System.out.println("hit range hit....returning-------");
            return;
        }
        if (start <= Math.ceil((double)(currRangeEnd + currRangeStart) / 2)) {
            //go left
            snow(GetLeftChild(index), start, end, amount, currRangeStart, (int)Math.ceil((double)(currRangeStart + currRangeEnd) / 2));
        }
        if (end > Math.ceil((double)(currRangeEnd + currRangeStart) / 2)) {
            //go right
            snow(GetRightChild(index), start, end, amount, (int)Math.ceil((double)(currRangeStart + currRangeEnd)/ 2), currRangeEnd);
        }

    }

    static int GetParent(int i) {
        if (i == 1) {
            return 0;
        }
        return (int)Math.ceil(((double)i / 2 - 1));
    }

    static int GetRightChild(int i) {
        return 2 * i + 1 + 1;
    }

    static int GetLeftChild(int i) {
        return 2 * i + 1;
    }

    static int GetIndex(int i) {
        return lineland.length / 2 + i;
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


