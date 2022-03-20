import java.io.*;
import java.util.*;

public class PointsOfSnow {
    static PointsOfSnow.Kattio io = new PointsOfSnow.Kattio(System.in, System.out);
    static long[] lineland;
    static int offsetIndex;
    static int maxSizeofTree;

    public static void main(String[] args) {
        int linelandLength = io.getInt();
        int reportCount = io.getInt();
        int queryCount = io.getInt();
        int HeightOfTree = (int)Math.ceil((Math.log(linelandLength) / Math.log(2)) +1);
        maxSizeofTree = (int)Math.pow(2, HeightOfTree)-1;

        lineland = new long[linelandLength *2-1];

        offsetIndex = (maxSizeofTree+1)/2 - (maxSizeofTree - lineland.length) - 1;

        for (int i = 0; i < reportCount + queryCount; i++) {
            if (io.getWord().equals("!")) {
                int start = io.getInt();
                int end = io.getInt();
                int amount = io.getInt();
                snow(0, start, end, amount, 0, linelandLength-1);

            } else {
                io.println(Query(io.getInt() - 1));
            }
        }

        for (int i = 0; i < lineland.length; i++) {
            System.out.print(lineland[i] + ", ");
        }
        System.out.println("");


        for (int i = 0; i < 10; i++) {
            System.out.print(Query(i) + ", ");
        }
        System.out.println("");

        System.out.print(Query(7) + "end");


        io.close();
    }

    static long Query(int index) {
        long value = 0;
        if(index <= 3){
            index = index + maxSizeofTree / 2;
        }else{
            index = GetParent(index + maxSizeofTree / 2 +(index-offsetIndex));
        }
        //(int)Math.ceil((double)i / 2 - 1);
        while (index > 0) {
            System.out.println("query: index = " + index + " value at index = " + lineland[index]);
            value = value + lineland[index];
            index = GetParent(index);
        }
        return value + lineland[0];
    }

    static void snow(int index, int start, int end, int amount, int currRangeStart, int currRangeEnd) {
        if(currRangeStart > end || currRangeEnd < start){
            return;
        }
        System.out.println("snowing: index = " + index + " currRange = " + currRangeStart + " -> " + currRangeEnd + " adding: " + amount);
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
        //check for skewed tree
        if(currRangeStart <= offsetIndex && currRangeEnd > offsetIndex) {
            //go left
            snow(GetLeftChild(index), start, end, amount, currRangeStart, (currRangeStart + currRangeEnd) / 2 + 1);
            //go right
            snow(GetRightChild(index), start, end, amount, (currRangeStart + currRangeEnd) / 2 +2, currRangeEnd);
        }else{
        //go left
        snow(GetLeftChild(index), start, end, amount, currRangeStart, (currRangeStart + currRangeEnd) / 2);
        //go right
        snow(GetRightChild(index), start, end, amount, (currRangeStart + currRangeEnd) / 2 +1, currRangeEnd);
        }
    }

    static int GetParent(int i) {
        return (int)Math.ceil((double)i / 2 - 1);
    }

    static int GetRightChild(int i) {
        return 2 * i + 2;
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


