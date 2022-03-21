import java.io.*;
import java.util.*;

public class PointsOfSnow {
    static PointsOfSnow.Kattio io = new PointsOfSnow.Kattio(System.in, System.out);
    static long[] lineland;
    static int maxSizeofTree;
    static int linelandLength;

    public static void main(String[] args) {
        linelandLength = io.getInt();
        int reportCount = io.getInt();
        int queryCount = io.getInt();
        int HeightOfTree = (int) Math.ceil((Math.log(linelandLength) / Math.log(2)) + 1);
        maxSizeofTree = (int) Math.pow(2, HeightOfTree) - 1;
        lineland = new long[maxSizeofTree+1];
        if(linelandLength > 2) {
            for (int i = 0; i < reportCount + queryCount; i++) {
                if (io.getWord().equals("!")) {
                    int start = io.getInt();
                    int end = io.getInt();
                    int amount = io.getInt();
                    snow(1, start, end-1, amount, 0, linelandLength - 1);
                } else {
                    io.println(Query(io.getInt()-1));
                }
            }
        }else{
            long[] snow = new long[linelandLength+1];
            for (int i = 0; i < reportCount + queryCount; i++) {
                if (io.getWord().equals("!")) {
                    int start = io.getInt();
                    int end = io.getInt();
                    int amount = io.getInt();
                    for(int j = 0; j < end+1; j++) {
                        snow[j] = snow[j] + amount;
                    }
                } else {
                    io.println(snow[io.getInt()-1]);
                }
            }
        }
        io.close();
    }

    static long Query(int target) {
        long value = lineland[1];
        int rangeStart = 0;
        int index = 1;
        int rangeEnd = linelandLength-1;
        while(rangeStart != rangeEnd) {
            if ((rangeStart + rangeEnd)/2 >= target) {
                index = GetLeftChild(index);
                value = value + lineland[index];
                rangeEnd = (rangeStart + rangeEnd)/2;
            } else {
                index = GetRightChild(index);
                value = value + lineland[index];
                rangeStart =  (rangeStart + rangeEnd)/2+1;
            }
        }
        return value;
    }

        static void snow ( int index, int start, int end, int amount, int currRangeStart, int currRangeEnd){
            if (currRangeStart > end || currRangeEnd < start) {
                return;
            }
            if (currRangeStart == currRangeEnd) {
                lineland[index] = lineland[index] + amount;
                return;
            }
            if (start <= currRangeStart && end >= currRangeEnd) {
                lineland[index] = lineland[index] + amount;
                return;
            }
            //go left
            snow(GetLeftChild(index), start, end, amount, currRangeStart, (currRangeStart + currRangeEnd) / 2);
            //go right
            snow(GetRightChild(index), start, end, amount, (currRangeStart + currRangeEnd) / 2 + 1, currRangeEnd);
        }

        static int GetRightChild ( int i){
            return 2 * i + 1;
        }

        static int GetLeftChild ( int i){
            return 2 * i;
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



